package rs.acctrak;

public class Db 
  extends rs.android.Db
{
  Db(android.content.Context context)
  {
    OpenHelper open_helper;
    String[][] tables=
    {
      { "Alert", "CREATE TABLE Alert (" +
      		"id INTEGER PRIMARY KEY, " +
      		"from_address TEXT, " +
      		"subject TEXT, " +
      		"rec_date INTEGER, " +
      		"balance NUMERIC, " +
      		"ext_id TEXT, "+
      		"source TEXT)" },
      { "Financial_Action", "CREATE TABLE Financial_Action (" +
          "id INTEGER PRIMARY KEY, " +
          "amount NUMERIC, " +
          "action_date INTEGER, " +
          "action_type TEXT)" },
      { "Setting", "CREATE TABLE Setting (" +
          "id INTEGER PRIMARY KEY, " +
          "key TEXT, " +
          "value TEXT)" },
      { "Alert_Update", "CREATE TABLE Alert_Update (" +
          "id INTEGER PRIMARY KEY, " +
          "origin TEXT, " +
          "start_date INTEGER, " +
          "last_update_date INTEGER, " +
          "new_emails INTEGER, " +
          "new_sms INTEGER, " +
          "status TEXT)" }
    };

    this.context=context;
    this.db_name="AcctrakDb";
    this.db_version=12;
    this.tables=tables;
    open_helper=new OpenHelper();
    if (open_helper!=null)
      this.conn=open_helper.getWritableDatabase();
  }
  
  public void Insert_Test_Data()
  {
    java.sql.Date date;

    date=rs.android.Util.New_Date(2012, 11, 1);
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 314.15, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 234.56, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 200.00, date, "expenses");

    date=rs.android.Util.New_Date(2012, 11, 1);
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 500.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 505.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 510.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 515.15, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 600.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 700.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 750.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 800.56, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 850.00, date, "savings");
    date=rs.android.Util.Add_Days(date, 14); Financial_Action.Insert(this, 900.00, date, "savings");

    date=rs.android.Util.New_Date(2012, 11, 1);
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2412.76, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2412.76, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2010.03, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 5219.82, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2538.24, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2010.03, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2816.44, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2134.53, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2010.03, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2186.88, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2392.54, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2010.03, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 5245.62, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2363.54, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2631.82, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2186.88, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2538.24, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2546.44, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2238.88, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2186.88, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2010.03, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2812.11, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2742.02, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2812.11, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2872.11, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2812.11, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 4519.82, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3024.98, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3024.98, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2521.24, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2522.49, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2522.49, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2742.02, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3081.33, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3418.18, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3141.83, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3302.82, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 4118.18, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3110.83, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2778.75, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3242.82, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3162.83, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3046.33, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3380.22, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3418.18, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3302.82, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 4291.91, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2977.53, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 3418.18, date, "balance");
    date=rs.android.Util.Add_Days(date, 1); Financial_Action.Insert(this, 2476.76, date, "balance");
  }
}
