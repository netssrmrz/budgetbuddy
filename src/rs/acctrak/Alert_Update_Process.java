package rs.acctrak;

public class Alert_Update_Process 
  extends android.os.AsyncTask<Void, Integer, Void>
{
  public Main_Activity main_activity;
  public android.app.ProgressDialog prog_dialog;
  public int prog_progress;
  public rs.android.Mail mail;
  public boolean check_email;
  public boolean check_sms;
  public rs.acctrak.Db db;

  protected void onPreExecute ()
  {
    int msgCount=0;
    android.content.SharedPreferences sp;
    //String host, user, password, folder;
    rs.acctrak.Setting settings;
    
    settings=new rs.acctrak.Setting();
    this.db=new rs.acctrak.Db(this.main_activity);

    sp = android.preference.PreferenceManager.getDefaultSharedPreferences(this.main_activity);
    this.check_email = Setting.Get_Check_Email(sp);
    this.check_sms = Setting.Get_Check_Sms(sp);
    
    if (check_email)
    {
      //host = Setting.Get_Mail_Host(sp);
      //user = Setting.Get_Mail_User(sp);
      //password = Setting.Get_Mail_Password(sp);
      //folder = Setting.Get_Mail_Folder(sp);
      //mail=new rs.android.Mail(host, user, password, folder);
      mail=new rs.android.Mail(this.main_activity, settings);
      if (mail!=null)
      {
        msgCount+=mail.Get_Msg_Count();
      }
    }
    if (check_sms)
    {
      msgCount+=Alert.Get_Sms_Count(this.main_activity);
    }

    if (rs.android.Util.NotEmpty(msgCount) && msgCount>0)
    {
      this.prog_progress=0;
      this.prog_dialog = new android.app.ProgressDialog(this.main_activity);
      this.prog_dialog.setIcon(R.drawable.ic_launcher);
      this.prog_dialog.setTitle("Updating...");
      this.prog_dialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
      this.prog_dialog.setMax(msgCount);
      this.prog_dialog.show();
    }
  }
  
  @Override
  protected Void doInBackground(Void... params)
  {
    this.prog_dialog.setOwnerActivity(this.main_activity);
    //Alert_Update.Do(this.db, this.main_activity, this.mail, check_email, check_sms, this, "user");
    return null;
  }
  
  public void Inc_Progress()
  {
    this.prog_dialog.setOwnerActivity(this.main_activity);
    this.prog_progress=this.prog_progress+1;
    this.publishProgress(this.prog_progress);
  }
  
  public void Max_Progress()
  {
    this.prog_dialog.setOwnerActivity(this.main_activity);
    this.prog_progress=this.prog_dialog.getMax();
    this.publishProgress(this.prog_progress);
  }
  
  protected void onProgressUpdate(Integer... progress) 
  {
    this.prog_dialog.setOwnerActivity(this.main_activity);
    this.prog_dialog.setProgress(progress[0]);
  }

  protected void onPostExecute(Void result) 
  {
    if (mail!=null)
      mail.Close();
    if (this.prog_dialog!=null)
    {
      this.prog_dialog.setOwnerActivity(this.main_activity);
      this.prog_dialog.dismiss();
    }
    
    this.main_activity.Update_UI(this.db);
    
    this.db.Close();
  }
}
