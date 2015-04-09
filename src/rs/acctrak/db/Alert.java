	package rs.acctrak.db;

	import rs.android.Db;

	public class Alert
	{
		public Integer id;
		public String from_address;
		public String subject;
		public java.sql.Date rec_date;
		public Double balance;
		public String ext_id;
		public String source;

		public Alert()
		{

		}

		public boolean Is_Bank_Alert()
		{
			boolean res=false;

			if (rs.android.Util.NotEmpty(this.source))
			{
				if (this.source.equals("sms"))
				{
					if (rs.android.Util.NotEmpty(this.from_address) && 
            (this.from_address.contains("0421267484") || this.from_address.contains("0421267479")))
						res=true;
				}
				else if (this.source.equals("email"))
				{
					if (rs.android.Util.NotEmpty(this.from_address) && this.from_address.contains("emailalertsstg@stgeorge.com.au"))
						res=true;
				}
			}
			return res;
		}

		public static Double Extract_Email_Balance(String msg)
		{
			Double res=null;
			String bal_str;

			bal_str=rs.android.Util.Extract_Str("AvailableBalance</strong></td><td>", "</td>", msg);
			res=rs.android.util.Type.ToDouble(bal_str);

			return res;
		}

		public static Double Extract_SMS_Balance(String msg)
		{
			Double res=null;
			String bal_str;

			bal_str=rs.android.Util.Extract_Str("Availablebal$", null, msg);
			res=rs.android.util.Type.ToDouble(bal_str);

			return res;
		}

		public boolean Exists(rs.android.Db db)
		{
			boolean res=false;
			String sql;
			Integer sql_res;

			sql="select id from alert where ext_id=? and source=? and from_address=?";
			sql_res=(Integer)db.Select_Value(Integer.class, sql, this.ext_id, this.source, this.from_address);
			if (sql_res!=null)
				res=true;
			return res;
		}

		public static Alert Get_Latest(rs.android.Db db)
		{
			Alert res=null;
			String sql;
			Integer id;

			sql="select id from alert order by rec_date desc";
			id=(Integer)db.Select_Value(Integer.class, sql);
			if (rs.android.Util.NotEmpty(id))
			{
				res=(Alert)db.SelectObj(Alert.class, id);
			}
			return res;
		}

		public static Alert Get_Max(rs.android.Db db)
		{
			Alert res=null;
			String sql;
			Integer id;

			sql="select id from alert order by balance desc";
			id=(Integer)db.Select_Value(Integer.class, sql);
			if (rs.android.Util.NotEmpty(id))
			{
				res=(Alert)db.SelectObj(Alert.class, id);
			}
			return res;
		}

		public static Integer Get_Sms_Count(android.content.Context context)
		{
			String sql, where;
			Db db;
			java.sql.Date last_date;
			android.net.Uri from;
			rs.android.Content android_db;
			Integer res=null;

			db=new rs.acctrak.db.Db(context);
			android_db=new rs.android.Content(context);
			if (rs.android.Util.NotEmpty(db) && rs.android.Util.NotEmpty(db))
			{
				sql="select max(rec_date) from alert where source='sms'";
				last_date=(java.sql.Date)db.Select_Value(java.sql.Date.class, sql);

				where="protocol is not null";
				from=android.net.Uri.parse("content://sms/");
				if (rs.android.Util.NotEmpty(last_date))
				{
					where=rs.android.Db.AppendFilter(where, "date>?");
					res=(Integer)android_db.Select_Value(Integer.class, "count(*)", from, where, "date desc", last_date);
				}
				else
				{
					res=(Integer)android_db.Select_Value(Integer.class, "count(*)", from, where, "date desc");
				}
				db.Close();
			}
			return res;
		}

		public static void Update_From_SMS(android.content.Context context, rs.acctrak.Update_Function process)
		{
			Db db;

			db=new rs.acctrak.db.Db(context);
			Update_From_SMS(db, context, process);
			db.Close();
		}

		public static int Update_From_SMS(Db db, android.content.Context context, rs.acctrak.Update_Function process)
		{
			Object[] ids=null;
			rs.android.Sms sms;
			rs.android.Content android_db;
			String where=null, sql, id;
			java.sql.Date last_date;
			int c, res=0;
			android.net.Uri from;
			Alert alert;
			Financial_Action fa;

			android_db=new rs.android.Content(context);
			if (rs.android.Util.NotEmpty(db) && rs.android.Util.NotEmpty(db))
			{
				sql="select max(rec_date) from alert where source='sms'";
				last_date=(java.sql.Date)db.Select_Value(java.sql.Date.class, sql);

				where="protocol is not null";
				from=android.net.Uri.parse("content://sms/");
				if (rs.android.Util.NotEmpty(last_date))
				{
					where=rs.android.Db.AppendFilter(where, "date>?");
					ids=android_db.Select_Column(null, String.class, "_id", from, where, "date desc", last_date);
				}
				else
				{
					ids=android_db.Select_Column(null, String.class, "_id", from, where, "date desc");
				}

				if (rs.android.Util.NotEmpty(ids))
				{
					where="_id=?";
					for (c=0; c<ids.length; c++)
					{
						id=rs.android.util.Type.To_String(ids[c]);
						sms=(rs.android.Sms)android_db.Select_Obj(rs.android.Sms.class, null, from, where, null, id);

						alert=new Alert();
						alert.source="sms";
						alert.from_address=sms.address;
						if (alert.Is_Bank_Alert())
						{
							alert.ext_id=id;
							if (!alert.Exists(db))
							{
								alert.subject=sms.subject;
								alert.rec_date=sms.date; 
								alert.balance=Extract_SMS_Balance(sms.body);
								db.Save(alert);

								fa=new Financial_Action();
								fa.action_date=alert.rec_date;
								fa.action_type="balance";
								fa.amount=alert.balance;
								fa.Save(db);

								res++;
							}
						}
						if (process!=null)
							process.Inc_Progress();
					}
				}
			}
			return res;
		}

		public static void Update_From_Emails(android.content.Context context, rs.android.Mail mail, rs.acctrak.Update_Function process)
		{
			Db db;

			db=new rs.acctrak.db.Db(context);
			Update_From_Emails(db, context, mail, process);
			db.Close();
		}

		public static int Update_From_Emails(Db db, android.content.Context context, rs.android.Mail mail, rs.acctrak.Update_Function process)
		{
			int i, msgCount, res=0;
			Alert alert;
			javax.mail.Message msg;
			long uid;
			Long last_uid;
			//String host, user, password, folder;
			String sql, body;
			Financial_Action fa;
			//android.content.SharedPreferences sp;
			rs.acctrak.db.Setting settings;

			settings=new rs.acctrak.db.Setting();
			if (rs.android.Util.NotEmpty(db))
			{
				if (mail==null)
				{
					//sp = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
					//host = Setting.Get_Mail_Host(sp);
					//user = Setting.Get_Mail_User(sp);
					//password = Setting.Get_Mail_Password(sp);
					//folder = Setting.Get_Mail_Folder(sp);
					//mail=new rs.android.Mail(host, user, password, folder);
					mail=new rs.android.Mail(context, settings);
				}
				if (rs.android.Util.NotEmpty(mail) && rs.android.Util.NotEmpty(db) && mail.inbox!=null)
				{
					sql="select max(cast(ext_id as integer)) from alert where source='email'";
					last_uid=(Long)db.Select_Value(Long.class, sql);
					msgCount=mail.Get_Msg_Count();

					for(i = msgCount; i >= 1; i--)
					{
						try 
						{ 
							msg=mail.inbox.getMessage(i); 
							uid=mail.inbox.getUID(msg);
						} 
						catch (javax.mail.MessagingException e) 
						{ 
							msg=null; 
							uid=0;
						}

						if (last_uid==null || uid>last_uid)
						{
							alert=new Alert();
							alert.from_address=rs.android.Mail.Get_From_As_String(msg);
							alert.source="email";
							if (alert.Is_Bank_Alert())
							{
								alert.ext_id=rs.android.util.Type.To_String(uid);
								if (!alert.Exists(db))
								{
									alert.subject=rs.android.Mail.Get_Subject(msg);
									alert.rec_date=new java.sql.Date(rs.android.Mail.Get_Received_Date(msg).getTime()); 
									body=rs.android.Mail.Get_Body_As_String(msg);
									alert.balance=Extract_Email_Balance(body);
									db.Save(alert);

									fa=new Financial_Action();
									fa.action_date=alert.rec_date;
									fa.action_type="balance";
									fa.amount=alert.balance;
									fa.Save(db);

									res++;
								}
							}
							if (process!=null)
								process.Inc_Progress();
						}
						else
						{
							if (process!=null)
								process.Max_Progress();
							break;
						}
					}
				}
			}
			return res;
		}
	}
