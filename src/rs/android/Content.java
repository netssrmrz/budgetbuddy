package rs.android;

public class Content
{
  public android.content.ContentResolver conn;
  public android.content.Context context;
  
  public Content()
  {    
  }

  public Content(android.content.Context context)
  {
    this.context=context;
    this.conn=context.getContentResolver();
  }
  
  public android.database.Cursor Execute_Query
    (String select, android.net.Uri from, String where, String order_by, Object ... params)
  {
    android.database.Cursor query_res=null;
    String[] param_vals=null, cols=null;
    int c;
    
    if (Util.NotEmpty(from))
    {
      if (Util.NotEmpty(select))
        cols=select.split(",");

      if (Util.NotEmpty(params))
      {
        param_vals=new String[params.length];
        for (c=0; c<params.length; c++)
        {
          if (params[c] instanceof java.util.Date) 
            params[c]=((java.util.Date)params[c]).getTime();
          param_vals[c]=Util.To_String(params[c]);
        }
      }
        
      query_res=this.conn.query(from, cols, where, param_vals, order_by);
    }
    return query_res;
  }
  
  public Object Select_Value(Class<? extends Object> val_class, 
      String select, android.net.Uri from, String where, String order_by, Object ... params)
  {
    Object res=null;
    android.database.Cursor query_res;
    
    if (Util.NotEmpty(select) && Util.NotEmpty(from))
    {
      query_res=Execute_Query(select, from, where, order_by, params);
      if (Util.NotEmpty(query_res))
      {
        query_res.moveToNext();
        res=Db.Get_Value_As(val_class, query_res, 0);
        query_res.close();
      }
    }
    return res;
  }

  public Object[] Select_Column(Integer top, Class<? extends Object> val_class, 
      String select, android.net.Uri from, String where, String order_by, Object ... params)
  {
    Object[] res=null;
    Object val;
    android.database.Cursor query_res=null;
    java.util.ArrayList<Object> col;
    int c=0;
    
    if (top==null || top>0)
    {
      query_res=Execute_Query(select, from, where, order_by, params);
      if (Util.NotEmpty(query_res))
      {
        //String code=Db.BuildClassFromCursor(query_res);
        col=new java.util.ArrayList<Object>();
        while (query_res.moveToNext())
        {
          val=Db.Get_Value_As(val_class, query_res, 0);
          col.add(val);
          if (top!=null)
          {
            c++;
            if (c==top)
              break;
          }
        }
        res=col.toArray();
      }
    }
    if (query_res!=null)
      query_res.close();
    return res;
  }
  
  public Object Select_Obj(Class<? extends Object> obj_class, 
      String select, android.net.Uri from, String where, String order_by, Object ... params)
  {
    Object res=null;
    android.database.Cursor query_res;
    rs.android.Db db;
    
    query_res=Execute_Query(select, from, where, order_by, params);
    if (Util.NotEmpty(query_res))
    {
      db=new rs.android.Db();
      query_res.moveToNext();
      res=db.NewObjFromCursor(obj_class, query_res);
    }
    if (query_res!=null)
      query_res.close();
    return res;
  }

  public Object[] Select_Objs(Integer top, Class<? extends Object> obj_class, 
      String select, android.net.Uri from, String where, String order_by, Object ... params)
  {
    java.util.ArrayList<Object> objs;
    Object[] res=null;
    android.database.Cursor query_res=null;
    Object obj;
    int c=0;
    rs.android.Db db;

    if (top==null || top>0)
    {
      query_res=Execute_Query(select, from, where, order_by, params);
      if (Util.NotEmpty(query_res))
      {
        db=new rs.android.Db();

        objs=new java.util.ArrayList<Object>();
        while (query_res.moveToNext())
        {
          obj=db.NewObjFromCursor(obj_class, query_res);
          objs.add(obj);
          if (top!=null)
          {
            c++;
            if (c==top)
              break;
          }
        }
        res=objs.toArray();
      }
    }
    if (query_res!=null)
      query_res.close();
    
    return res;
  }
}
