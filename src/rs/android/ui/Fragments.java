package rs.android.ui;

public class Fragments 
  extends android.support.v4.app.FragmentPagerAdapter
{
  public java.util.ArrayList<android.support.v4.app.Fragment> pages;
  
  public Fragments(android.support.v4.app.FragmentManager fm)
  {
    super(fm);
    this.pages=new java.util.ArrayList<android.support.v4.app.Fragment>();
  }

  @Override
  public android.support.v4.app.Fragment getItem(int id)
  {
    android.support.v4.app.Fragment res=null;
    
    res=this.pages.get(id);
    return res;
  }

  @Override
  public int getCount()
  {
    int res=0;
    
    res=this.pages.size();
    return res;
  }

  @Override
  public CharSequence getPageTitle (int position)
  {
    String res=null;
    
    switch (position)
    {
      case 0: res="Acctrak - Balance"; break;
      case 1: res="Acctrak - History"; break;
    }
    return res;
  }
}
