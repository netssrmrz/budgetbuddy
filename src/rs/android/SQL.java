package rs.android;

public class SQL
{
  public String select;
  public String from;
  public String where;
  public String order_by;
  public java.util.ArrayList<Object> param_vals;
  
  public SQL(String select, String from, String where, String order_by)
  {
    this.select=select;
    this.from=from;
    this.where=where;
    this.order_by=order_by;
  }
  
  public void Append_Filter(String filter, Object param_val)
  {
    if (rs.android.Util.NotEmpty(filter))
    {
      this.where=rs.android.Db.AppendFilter(this.where, filter);
      if (this.param_vals==null && param_val!=null)
        this.param_vals=new java.util.ArrayList<Object>();
      if (param_val!=null)
        this.param_vals.add(param_val);
    }
  }
  
  public String ToString()
  {
    String res=null;
    
    if (rs.android.Util.NotEmpty(this.select) && rs.android.Util.NotEmpty(this.from))
    {
      res="select "+this.select+" from "+from;
      if (rs.android.Util.NotEmpty(this.where))
        res+=" where "+this.where;
      if (rs.android.Util.NotEmpty(this.order_by))
        res+=" order by "+this.order_by;
    }
    return res;
  }
  
  public Object[] Get_Param_Vals()
  {
    Object[] res=null;
    
    if (rs.android.Util.NotEmpty(this.param_vals))
    {
      res=this.param_vals.toArray();
    }
    return res;
  }
}
