package rs.acctrak;

public class Setting
implements rs.android.setting.Mail
{
  public Integer id;
  public String key;
  public String value;
  
  public static java.sql.Date Get_As_Date(rs.android.Db db, String key)
  {
    java.sql.Date res=null;
    
    res=(java.sql.Date)db.Select_Value("select value from setting where key=?", java.sql.Date.class, key);
    return res;
  }
  
  public static boolean Get_As_Boolean(rs.android.Db db, String key)
  {
    boolean res=false;
    Boolean db_res;
    
    db_res=(Boolean)db.Select_Value("select value from setting where key=?", Boolean.class, key);
    if (db_res!=null)
      res=db_res.booleanValue();
    return res;
  }
  
  public static void Set(rs.android.Db db, String key, Object value)
  {
    db.Delete("setting", "key=?", key);
    db.Insert("setting", "key", key, "value", value);
  }
  
  public static void Set_Show_Balance(android.content.SharedPreferences prefs, boolean value)
  {
    android.content.SharedPreferences.Editor editor;

    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putBoolean("show_balance", value);
      editor.commit();
    }
  }
  
  public static void Set_Show_Savings(android.content.SharedPreferences prefs, boolean value)
  {
    android.content.SharedPreferences.Editor editor;
    
    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putBoolean("show_savings", value);
      editor.commit();
    }
  }
  
  public static void Set_Show_Expenses(android.content.SharedPreferences prefs, boolean value)
  {
    android.content.SharedPreferences.Editor editor;
    
    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putBoolean("show_expenses", value);
      editor.commit();
    }
  }
  
  public static void Set_Show_To_Spend(android.content.SharedPreferences prefs, boolean value)
  {
    android.content.SharedPreferences.Editor editor;
    
    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putBoolean("show_to_spend", value);
      editor.commit();
    }
  }
  
  public static void Set_Start_Date(android.content.SharedPreferences prefs, java.sql.Date value)
  {
    android.content.SharedPreferences.Editor editor;
    
    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putLong("start_date", value.getTime());
      editor.commit();
    }
  }
  
  public static void Set_End_Date(android.content.SharedPreferences prefs, java.sql.Date value)
  {
    android.content.SharedPreferences.Editor editor;
    
    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putLong("end_date", value.getTime());
      editor.commit();
    }
  }
  
  public static void Set_Guage_Max(android.content.SharedPreferences prefs, float value)
  {
    android.content.SharedPreferences.Editor editor;
    
    if (prefs!=null)
    {
      editor=prefs.edit();
      editor.putFloat("guage_max", value);
      editor.commit();
    }
  }
  
  public static boolean Get_Show_Balance(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("show_balance", res);
    return res;
  }
  
  public static boolean Get_Show_Savings(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("show_savings", res);
    return res;
  }
  
  public static boolean Get_Show_Expenses(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("show_expenses", res);
    return res;
  }

  public static boolean Get_Show_To_Spend(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("show_to_spend", res);
    return res;
  }

  public static boolean Get_Check_Auto_Update(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("check_auto_update", res);
    return res;
  }
  
  public static boolean Get_Check_Sms(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("check_sms", res);
    return res;
  }
  
  public static boolean Get_Check_Email(android.content.SharedPreferences prefs)
  {
    boolean res=false;
    
    if (prefs!=null)
      res=prefs.getBoolean("check_email", res);
    return res;
  }
  
  public String Get_Mail_Host(android.content.SharedPreferences prefs)
  {
    String res="imap.gmail.com";
    
    if (prefs!=null)
      res=prefs.getString("mail_host", res);
    return res;
  }
  
  public String Get_Mail_User(android.content.SharedPreferences prefs)
  {
    String res="ramirez.systems@gmail.com";
    
    if (prefs!=null)
      res=prefs.getString("mail_user", res);
    return res;
  }
  
  public String Get_Mail_Password(android.content.SharedPreferences prefs)
  {
    String res="s3xym1r@#";
    
    if (prefs!=null)
      res=prefs.getString("mail_password", res);
    return res;
  }
  
  public String Get_Mail_Folder(android.content.SharedPreferences prefs)
  {
    String res="Bank Alert";
    
    if (prefs!=null)
      res=prefs.getString("mail_folder", res);
    return res;
  }
  
  public static float Get_Guage_Max(android.content.SharedPreferences prefs)
  {
    float res=(float)100;
    
    if (prefs!=null)
      res=rs.android.Util.To_Float(prefs.getString("guage_max", rs.android.Util.To_String(res)));
    return res;
  }
  
  public static java.sql.Date Get_Start_Date(android.content.SharedPreferences prefs)
  {
    java.sql.Date res=null;
    long val;
    
    if (prefs!=null)
    {
      val=prefs.getLong("start_date", 0);
      if (val!=0)
        res=new java.sql.Date(val);
    }
    return res;
  }
  
  public static java.sql.Date Get_End_Date(android.content.SharedPreferences prefs)
  {
    java.sql.Date res=null;
    long val;
    
    if (prefs!=null)
    {
      val=prefs.getLong("end_date", 0);
      if (val!=0)
        res=new java.sql.Date(val);
    }
    return res;
  }
}
