package rs.acctrak;

import rs.android.Util;

public class Financial_Action
{
  public Integer id;
  public Double amount;
  public java.sql.Date action_date;
  public String action_type;
  
  public Financial_Action()
  {
    //this.action_date=(java.sql.Date)rs.android.Util.Round(rs.android.Util.Now(), rs.android.Util.ROUND_DATE_DAY);
  }
  
  public static Financial_Action Get_Max(rs.android.Db db)
  {
    Financial_Action res=null;
    String sql;
    Integer id;
    
    sql="select id from Financial_Action order by amount desc";
    id=(Integer)db.Select_Value(Integer.class, sql);
    if (rs.android.Util.NotEmpty(id))
    {
      res=(Financial_Action)db.SelectObj(Financial_Action.class, id);
    }
    return res;
  }
  
  public void Save(rs.android.Db db)
  {
    String sql;
    Financial_Action fa;
    java.sql.Date start, end;
    
    start=(java.sql.Date)rs.android.Util.Round(this.action_date, 
		  rs.android.util.Date.ROUND_DATE_DAY);
    end=rs.android.util.Date.Add_Days(start, 1);
    
    sql=
      "select * "+
      "from Financial_Action "+
      "where action_date>=? and action_date<? and action_type=?";
    fa=(Financial_Action)db.SelectObj(Financial_Action.class, sql, 
        start, end, this.action_type);
    if (fa!=null && this.action_date.after(fa.action_date))
    {
      this.id=fa.id;
      db.Save(this);
    }
    else if (fa==null)
      db.Save(this);
  }

  public static Financial_Action Get_Latest(rs.android.Db db, String action_type)
  {
    Financial_Action res=null;
    rs.android.SQL sql;
    Integer id;
    
    sql=new rs.android.SQL("id", "Financial_Action", null, "action_date desc");
    if (rs.android.Util.NotEmpty(action_type))
      sql.Append_Filter("action_type=?", action_type);
    id=(Integer)db.Select_Value(Integer.class, sql.ToString(), sql.Get_Param_Vals());
    if (rs.android.Util.NotEmpty(id))
      res=(Financial_Action)db.SelectObj(Financial_Action.class, id);
    return res;
  }
  
  public static Integer Insert(rs.android.Db db, Double amount, java.sql.Date date, String type)
  {
    Integer res=null;
    Financial_Action fa;    
    
    fa=new Financial_Action();
    fa.action_date=date;
    fa.action_type=type;
    fa.amount=amount;
    fa.Save(db);
    res=fa.id;
    
    return res;
  }
  
  public static java.sql.Date Get_First_Date(rs.android.Db db)
  {
    java.sql.Date res=null;
    
    if (rs.android.Util.NotEmpty(db) & db.Rows_Exist("financial_action", "action_date is not null"))
      res=(java.sql.Date)db.Select_Value(java.sql.Date.class, "select min(action_date) from financial_action");
    return res;
  }
  
  public static java.sql.Date Get_Last_Date(rs.android.Db db)
  {
    java.sql.Date res=null;
    
    if (rs.android.Util.NotEmpty(db) & db.Rows_Exist("financial_action", "action_date is not null"))
      res=(java.sql.Date)db.Select_Value(java.sql.Date.class, "select max(action_date) from financial_action");
    return res;
  }

  public static java.util.ArrayList<Financial_Action> SelectObjs(rs.android.Db db, Integer top, String where, String order_by, Object ... params)
  {
    java.util.ArrayList<Financial_Action> res=null;
    android.database.Cursor query_res;
    Financial_Action obj;
    int c=0;
    String sql;
    
    sql = db.Build_SQL_Str("id, amount, action_date, action_type", "Financial_Action", where, order_by);
    query_res=db.Execute_SQL(sql, params);
    if (query_res!=null && query_res.moveToFirst())
    {
      res=new java.util.ArrayList<Financial_Action>();
      do
      {
        obj=new Financial_Action();
        obj.id=query_res.getInt(0);
        obj.amount=query_res.getDouble(1);
        obj.action_date=new java.sql.Date(query_res.getLong(2));
        obj.action_type=query_res.getString(3);
            
        res.add(obj);
        if (top!=null)
        {
          c++;
          if (c==top)
            break;
        }
      }
      while (query_res.moveToNext());
    }
    if (query_res!=null)
      query_res.close();
    
    return res;
  }
  
  public static void Save_Savings(rs.android.Db db, Double savings)
  {
    Financial_Action fa;
    
    fa=new Financial_Action();
    fa.action_date=rs.android.util.Date.Now();
    fa.action_type="savings";
    fa.amount=savings;
    fa.Save(db);
  }
  
  public static void Save_Expenses(rs.android.Db db, Double expenses)
  {
    Financial_Action fa;
    
    fa=new Financial_Action();
    fa.action_date=rs.android.util.Date.Now();
    fa.action_type="expenses";
    fa.amount=expenses;
    fa.Save(db);
  }
}
