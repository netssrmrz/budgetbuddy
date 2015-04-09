	package rs.acctrak.db;

	import rs.android.Mail;

	public class Alert_Update
	{
		public static final int UPD_ERR_UPD_IN_PROGRESS=-1;
		public static final int UPD_ERR_EMAIL_AUTH_FAILED=-2;
		public static final int UPD_ERR_EMAIL_FOLDER_UNK=-3;
		public static final int UPD_ERR_EMAIL_HOST_UNK=-4;
		public static final int UPD_ERR_EMAIL_CONNECT_FAILED=-5;

		public Integer id;
		public String origin;
		public java.sql.Date start_date;
		public java.sql.Date last_update_date;
		public Integer new_emails;
		public Integer new_sms;
		public String status;

		public static boolean Underway(Db db)
		{
			boolean res=false;
			Integer sql_res;
			String sql;

			sql="select id from Alert_Update where status like 'started%'";
			sql_res=(Integer)db.Select_Value(Integer.class, sql);
			if (rs.android.Util.NotEmpty(sql_res))
				res=true;

			return res;
		}

		public int Do(android.content.Context ctx, rs.acctrak.Update_Function proc, String origin)
		{
			int res=0, max=0;
			android.content.SharedPreferences sp;
			boolean check_email, check_sms;
			rs.acctrak.db.Db db;
			rs.android.Mail mail=null;
			android.content.Intent intent;
			rs.acctrak.db.Setting settings;

			settings=new rs.acctrak.db.Setting();
			db=new rs.acctrak.db.Db(ctx);
			if (rs.android.Util.NotEmpty(db))
			{
				db.Delete("Alert_Update", "last_update_date<?", 
					rs.android.util.Date.Add_Days(rs.android.util.Date.Now(), -7));

				if (!Alert_Update.Underway(db))
				{
					this.origin=origin;
					this.start_date=rs.android.util.Date.Now();
					this.last_update_date=this.start_date;
					this.new_emails=null;
					this.new_sms=null;
					this.status="started, 0";
					db.Save(this);

					sp = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
					check_email = Setting.Get_Check_Email(sp);
					check_sms = Setting.Get_Check_Sms(sp);

					if (check_email)
					{
						mail=new rs.android.Mail(ctx, settings);
						if (mail.status==Mail.STATUS_AUTH_FAILED)
							res=UPD_ERR_EMAIL_AUTH_FAILED;
						else if (mail.status==Mail.STATUS_FOLDER_UNK)
							res=UPD_ERR_EMAIL_FOLDER_UNK;
						else if (mail.status==Mail.STATUS_HOST_UNK)
							res=UPD_ERR_EMAIL_HOST_UNK;
						else if (mail.status==Mail.STATUS_UNABLE_TO_CONNECT)
							res=UPD_ERR_EMAIL_CONNECT_FAILED;
					}

					if (proc!=null)
					{
						if (check_email && mail!=null && mail.inbox!=null)
							max+=mail.Get_Msg_Count();
						if (check_sms)
							max+=Alert.Get_Sms_Count(ctx);
						if (max>0)
							proc.Set_Max_Progress(max);
					}

					if (check_email && mail!=null && mail.inbox!=null)
						this.new_emails=Alert.Update_From_Emails(db, ctx, mail, proc);
					if (mail!=null)
						mail.Close();

					this.last_update_date=rs.android.util.Date.Now();
					this.status="started, 0.5";
					db.Save(this);

					if (check_sms)
						this.new_sms=Alert.Update_From_SMS(db, ctx, proc);

					this.last_update_date=rs.android.util.Date.Now();
					this.status="completed";
					db.Save(this);

					if (rs.android.Util.NotEmpty(this.new_emails))
						res+=this.new_emails;
					if (rs.android.Util.NotEmpty(this.new_sms))
						res+=this.new_sms;
				}
				else
				{
					res=UPD_ERR_UPD_IN_PROGRESS;
				}
			}
			if (db!=null)
				db.Close();

			intent = new android.content.Intent();
			intent.setAction("TRAN_UPDATE_ACTION");
			ctx.sendBroadcast(intent);   

			return res;
		}
	}
