package rs.acctrak; 

//import android.net.Uri;
//import android.view.View;

public class Main_Activity 
  extends android.support.v4.app.FragmentActivity 
{  
  public class Update_Receiver
  extends android.content.BroadcastReceiver
  {
    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent)
    {
      Update_UI(db);
    }
  }
  
  public class Acctrak_Pager_Title_Strip
  extends android.support.v4.view.PagerTitleStrip
  {
    public boolean first_time;
    public int border_width=4;
    android.content.Context ctx;
    
    public Acctrak_Pager_Title_Strip(android.content.Context context)
    {
      super(context);
      android.graphics.drawable.Drawable[] bk_contents;
      android.graphics.drawable.GradientDrawable bk_border;
      android.graphics.drawable.LayerDrawable bk;
      android.util.DisplayMetrics dm;
      float wr, hr;

      this.ctx=context;
      this.setWillNotDraw(false);
      dm=this.getResources().getDisplayMetrics();
      hr=dm.heightPixels/100;
      wr=dm.widthPixels/100;

      bk_border=new android.graphics.drawable.GradientDrawable();
      bk_border.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
      bk_border.setStroke(border_width, android.graphics.Color.rgb(40, 122, 169));
      bk_contents=new android.graphics.drawable.Drawable[1];
      bk_contents[0]=bk_border;
      bk=new android.graphics.drawable.LayerDrawable(bk_contents);

      this.first_time=true;
      this.setBackgroundDrawable(bk);
      this.setPadding(0, 0, 0, 8);
      
      if (rs.android.ui.Util.Is_Landscape_Mode(context))
        this.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, hr*8);
      else
        this.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, hr*6);
    }
    
    @Override
    public void onDraw(android.graphics.Canvas canvas) 
    {
      android.graphics.Rect b;
      
      if (first_time)
      {
        b=this.getBackground().getBounds();
        this.getBackground().setBounds(b.left-border_width, b.top-border_width, b.right+border_width, b.bottom);
        this.invalidate();
        first_time=false;
      }
      super.onDraw(canvas);
    }
  }
  
  public rs.acctrak.Guage_Page guage_page;
  public rs.acctrak.Chart_Page chart_page;
  public Acctrak_Pager_Title_Strip pager_title_strip;
  public android.support.v4.view.ViewPager pager;
  public float wr, hr;

  public Update_Receiver upd_receiver;
  public rs.acctrak.Db db;
  public Update_Function update_fn;

  @Override
  public void onCreate(android.os.Bundle savedInstanceState) 
  {
    rs.android.ui.Fragments pages;
    android.support.v4.view.ViewPager.LayoutParams layout;
    android.content.SharedPreferences sp;
    android.util.DisplayMetrics dm;

    super.onCreate(null);
    this.db=new rs.acctrak.Db(this);
    dm=this.getResources().getDisplayMetrics();
    hr=dm.heightPixels/(float)100;
    wr=dm.widthPixels/(float)100;

    this.guage_page=new rs.acctrak.Guage_Page();
    this.chart_page=new rs.acctrak.Chart_Page();
    pages = new rs.android.ui.Fragments(super.getSupportFragmentManager());
    pages.pages.add(this.guage_page);
    pages.pages.add(this.chart_page);
    
    this.pager_title_strip=new Acctrak_Pager_Title_Strip(this);
    layout=new android.support.v4.view.ViewPager.LayoutParams();
    layout.width=android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT;
    layout.height=android.support.v4.view.ViewPager.LayoutParams.WRAP_CONTENT;
    layout.gravity=android.view.Gravity.TOP;
    
    this.pager=new android.support.v4.view.ViewPager(this);
    this.pager.setId(1);
    this.pager.addView(this.pager_title_strip, layout);
    this.pager.setAdapter(pages);
    this.pager.setPadding(8, 8, 8, 8);
    
    this.setContentView(this.pager);
    
    this.update_fn=(Update_Function)this.getLastCustomNonConfigurationInstance();
    if (this.update_fn!=null)
    {
      this.update_fn.Set_Activity(this);
    }
    
    sp = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
    if (Setting.Get_Check_Auto_Update(sp))
      startService(new android.content.Intent(this, Alert_Update_Service.class));
  }
  
  @Override
  public void onResume ()
  {
    android.content.IntentFilter filter;

    super.onResume();
    
    if (this.db==null)
      this.db=new rs.acctrak.Db(this);

    this.upd_receiver=new Update_Receiver();
    filter = new android.content.IntentFilter();
    filter.addAction("TRAN_UPDATE_ACTION");
    this.registerReceiver(this.upd_receiver, filter);
  }
  
  @Override
  public Object onRetainCustomNonConfigurationInstance() 
  {
    if (this.update_fn!=null)
    {
      this.update_fn.Set_Activity(null);
    }
    return this.update_fn;
  }
  
  @Override
  public void onPause()
  {
    super.onPause();
    
    this.unregisterReceiver(this.upd_receiver);
    
    if (this.db!=null)
    {
      this.db.Close();
      this.db=null;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(android.view.Menu menu) 
  {
    menu.add(1, 5, 1, "Refresh").setIcon(R.drawable.refresh);
    menu.add(1, 2, 2, "Export").setIcon(R.drawable.download);
    //menu.add(1, 1, 3, "Clear Data");
    menu.add(1, 4, 4, "Settings").setIcon(R.drawable.gear);
    //menu.add(1, 3, 5, "Insert Data");
    //menu.add(1, 6, 6, "Insert Data 2");
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected (android.view.MenuItem item)
  {
    boolean res=false;
    android.content.Intent intent;
    
    if (item.getItemId()==1) // Clear Data
    {
      this.db.Delete("Financial_Action", null);
      this.db.Delete("Alert", null);
      this.db.Delete("Setting", null);
      this.db.Delete("Alert_Update", null);

      this.Update_UI(this.db);
      res=true;
    }
    else if (item.getItemId()==2) // Export Data
    {
      this.Export_Data(this.db);
      
      res=true;
    }
    else if (item.getItemId()==3)
    {
      //this.db.Insert_Test_Data();

      this.Update_UI(this.db);
      res=true;
    }
    else if (item.getItemId()==4) // Settings
    {
      intent =new android.content.Intent(this, Settings_Activity.class);
      this.startActivity(intent);
      res=true;
    }
    else if (item.getItemId()==5) // Update Data
    {
      this.update_fn=new Update_Function(this);
    }
    else if (item.getItemId()==6) // Update Data
    {
      this.update_fn=new Update_Function(this);
    }
    return res;
  }
  
  public void Update_UI(rs.acctrak.Db db)
  {
    if (rs.android.Util.NotEmpty(this.guage_page))
      this.guage_page.Update_UI(db);
    if (rs.android.Util.NotEmpty(this.chart_page))
      this.chart_page.Update_UI(db);
  }
  
  @Override
  public android.app.Dialog onCreateDialog(int id)
  {
    android.app.Dialog res=null;
    
    if (res==null && rs.android.Util.NotEmpty(this.guage_page))
    {
      res=this.guage_page.onCreateDialog(id);
    }
    if (res==null && rs.android.Util.NotEmpty(this.chart_page))
    {
      res=this.chart_page.onCreateDialog(id);
    }
    return res;
  }
  
  @Override
  public void onPrepareDialog(int id, android.app.Dialog dialog)
  {
    if (rs.android.Util.NotEmpty(this.guage_page))
    {
      this.guage_page.onPrepareDialog(id, dialog);
    }
    if (rs.android.Util.NotEmpty(this.chart_page))
    {
      this.chart_page.onPrepareDialog(id, dialog);
    }
  }

  public void Export_Data(rs.android.Db db)
  {
    String state, csv;
    java.io.File sd_dir, csv_file;
    java.io.FileOutputStream csv_stream;
    java.util.ArrayList<android.net.Uri> file_uris;
    // /Android/data/<package_name>/files/
    
    state = android.os.Environment.getExternalStorageState();
    if (android.os.Environment.MEDIA_MOUNTED.equals(state))
    {
      file_uris = new java.util.ArrayList<android.net.Uri>();
      sd_dir=android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS);
      sd_dir.mkdirs();
      
      try
      {
        csv=db.Dump_Table_To_CSV("Financial_Action", "action_date", java.sql.Date.class);
        if (rs.android.Util.NotEmpty(csv))
        {
          csv_file = new java.io.File(sd_dir, "Financial_Action.csv");
          csv_stream = new java.io.FileOutputStream(csv_file);
          csv_stream.write(csv.getBytes());
          csv_stream.close();
          file_uris.add(android.net.Uri.fromFile(csv_file));
        }
        
        csv=db.Dump_Table_To_CSV("Alert", "rec_date", java.sql.Date.class);
        if (rs.android.Util.NotEmpty(csv))
        {
          csv_file = new java.io.File(sd_dir, "Alert.csv");
          csv_stream = new java.io.FileOutputStream(csv_file);
          csv_stream.write(csv.getBytes());
          csv_stream.close();
          file_uris.add(android.net.Uri.fromFile(csv_file));
        }
        
        csv=db.Dump_Table_To_CSV("Alert_Update", "start_date", java.sql.Date.class, "last_update_date", java.sql.Date.class);
        if (rs.android.Util.NotEmpty(csv))
        {
          csv_file = new java.io.File(sd_dir, "Alert_Update.csv");
          csv_stream = new java.io.FileOutputStream(csv_file);
          csv_stream.write(csv.getBytes());
          csv_stream.close();
          file_uris.add(android.net.Uri.fromFile(csv_file));
        }
        
        if (rs.android.Util.NotEmpty(file_uris))
        {
          android.content.Intent intent=new android.content.Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
          intent.setType("text/csv");
          intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
          intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, file_uris);
          startActivity(android.content.Intent.createChooser(intent, "How do you want to export?"));
        }
      } 
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public android.widget.TextView New_Label(String text)
  {
    android.widget.TextView res=null;
    
    res=new android.widget.TextView(this);
    if (rs.android.ui.Util.Is_Landscape_Mode(this))
    {
      res.setTextColor(android.graphics.Color.GRAY);
      //res.setOnClickListener(this);
      res.setPadding(0, 0, 0, 0);
      res.setText(text);
      res.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, this.hr*(float)4);
      res.setIncludeFontPadding(false);
    }
    else
    {
      res.setTextColor(android.graphics.Color.GRAY);
      //res.setOnClickListener(this);
      res.setPadding(0, 0, 0, 0);
      res.setText(text);
      res.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, this.hr*(float)2.5);
      res.setIncludeFontPadding(false);
    }
    return res;
  }

  public android.widget.TextView New_Text(String text, int col, int size, 
	  android.view.View.OnClickListener click)
  {
    android.widget.TextView res=null;
    
    res=new android.widget.TextView(this);
    if (rs.android.ui.Util.Is_Landscape_Mode(this))
    {
      res.setTextColor(col);
      res.setOnClickListener(click);
      res.setPadding(0, (int)((float)4*hr), 0, 0);
      res.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
    }
    else
    {
      
    }
    return res;
  }
}
