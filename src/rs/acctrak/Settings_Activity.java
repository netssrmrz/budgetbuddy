package rs.acctrak;

import rs.android.Util;

public class Settings_Activity 
extends android.preference.PreferenceActivity 
implements android.content.SharedPreferences.OnSharedPreferenceChangeListener
{
  public android.preference.PreferenceScreen ps;
  
  @Override
  public void onCreate(android.os.Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    
    android.preference.PreferenceManager pm;
    android.preference.EditTextPreference text_pref;
    android.preference.CheckBoxPreference check_pref;
    
    pm=this.getPreferenceManager();
    ps=pm.createPreferenceScreen(this);
    
    check_pref=new android.preference.CheckBoxPreference(this);
    check_pref.setKey("check_auto_update");
    check_pref.setTitle("Automatically Update");
    ps.addPreference(check_pref);
    
    check_pref=new android.preference.CheckBoxPreference(this);
    check_pref.setKey("check_sms");
    check_pref.setTitle("Check SMS");
    ps.addPreference(check_pref);
    
    check_pref=new android.preference.CheckBoxPreference(this);
    check_pref.setKey("check_email");
    check_pref.setTitle("Check E-mails");
    ps.addPreference(check_pref);
    
    text_pref=new android.preference.EditTextPreference(this);
    text_pref.setKey("mail_host");
    text_pref.setTitle("IMAP Server");
    text_pref.getEditText().setRawInputType(android.text.InputType.TYPE_CLASS_TEXT);
    ps.addPreference(text_pref);
    
    text_pref=new android.preference.EditTextPreference(this);
    text_pref.setKey("mail_user");
    text_pref.setTitle("User");
    text_pref.getEditText().setRawInputType(android.text.InputType.TYPE_CLASS_TEXT);
    ps.addPreference(text_pref);
    
    text_pref=new android.preference.EditTextPreference(this);
    text_pref.setKey("mail_password");
    text_pref.setTitle("Password");
    text_pref.getEditText().setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
    text_pref.getEditText().setRawInputType(android.text.InputType.TYPE_CLASS_TEXT);
    ps.addPreference(text_pref);
    
    text_pref=new android.preference.EditTextPreference(this);
    text_pref.setKey("mail_folder");
    text_pref.setTitle("Folder");
    text_pref.getEditText().setRawInputType(android.text.InputType.TYPE_CLASS_TEXT);
    ps.addPreference(text_pref);
    
    text_pref=new android.preference.EditTextPreference(this);
    text_pref.setKey("guage_max");
    text_pref.setTitle("Guage Maximum");
    text_pref.getEditText().setRawInputType(android.text.InputType.TYPE_CLASS_NUMBER|android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
    ps.addPreference(text_pref);
    
    this.setPreferenceScreen(ps);
    //this.Update_UI();
  }
  
  public void Update_UI()
  {
    android.content.SharedPreferences prefs;
    android.preference.Preference pref;
    rs.acctrak.db.Setting settings;

    settings=new rs.acctrak.db.Setting();
    prefs=ps.getSharedPreferences();
    
    pref=ps.findPreference("check_auto_update");
    pref.setSummary(rs.android.util.Type.To_String(
		  rs.acctrak.db.Setting.Get_Check_Auto_Update(prefs), null, "On,Off"));
    
    pref=ps.findPreference("check_sms");
    pref.setSummary(rs.android.util.Type.To_String(
		  rs.acctrak.db.Setting.Get_Check_Sms(prefs), null, "On,Off"));
    
    pref=ps.findPreference("check_email");
    pref.setSummary(rs.android.util.Type.To_String(
		  rs.acctrak.db.Setting.Get_Check_Email(prefs), null, "On,Off"));
    
    pref=ps.findPreference("mail_host");
    pref.setSummary(settings.Get_Mail_Host(prefs));
    
    pref=ps.findPreference("mail_user");
    pref.setSummary(settings.Get_Mail_User(prefs));
    
    pref=ps.findPreference("mail_password");
    //pref.setSummary(Setting.Get_Mail_Password(prefs));
    
    pref=ps.findPreference("mail_folder");
    pref.setSummary(settings.Get_Mail_Folder(prefs));
    
    pref=ps.findPreference("guage_max");
    pref.setSummary(rs.android.util.Type.To_String(
		  rs.acctrak.db.Setting.Get_Guage_Max(prefs)));
  }
  
  public void onSharedPreferenceChanged(android.content.SharedPreferences prefs, String key) 
  {
    android.preference.Preference pref;
    float guage_max;
    
    pref = findPreference(key);
    
    if (key.equals("check_auto_update"))
    {
      if (prefs.getBoolean(key, false))
      {
        startService(new android.content.Intent(getBaseContext(), Alert_Update_Service.class));
        pref.setSummary("On");
      }
      else
      {
        stopService(new android.content.Intent(getBaseContext(), Alert_Update_Service.class));
        pref.setSummary("Off");
      }
    }
    else if (key.equals("guage_max"))
    {
      guage_max=rs.android.util.Type.To_Float(prefs.getString("guage_max", "100"));
      if (guage_max<100)
      {
        prefs=ps.getSharedPreferences();
        rs.acctrak.db.Setting.Set_Guage_Max(prefs, (float)100.0);
      }
    }
    Update_UI();
  }
  
  @Override
  protected void onResume() 
  {
    super.onResume();
    this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
}
