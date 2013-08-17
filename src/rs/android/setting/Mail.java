package rs.android.setting;

public interface Mail
{
  public String Get_Mail_Host(android.content.SharedPreferences prefs);
  
  public String Get_Mail_User(android.content.SharedPreferences prefs);
  
  public String Get_Mail_Password(android.content.SharedPreferences prefs);
  
  public String Get_Mail_Folder(android.content.SharedPreferences prefs);
}
