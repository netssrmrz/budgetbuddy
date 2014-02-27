package rs.acctrak;

public class Chart_Page 
  extends android.support.v4.app.Fragment
  implements 
    android.view.View.OnClickListener,
    android.app.DatePickerDialog.OnDateSetListener,
    android.widget.CompoundButton.OnCheckedChangeListener
{
  public static final int DLG_INPUT_START_DATE=5;
  public static final int DLG_INPUT_END_DATE=6;
  public static final int TEXTVIEW_START_DATE=7;
  public static final int TEXTVIEW_END_DATE=8;

  public android.widget.CheckBox balance_check;
  public android.widget.CheckBox savings_check;
  public android.widget.CheckBox expenses_check;
  public Chart_View chart_view;
  public android.widget.TextView start_date_view;
  public android.widget.TextView end_date_view;
  
  public int date_dlg_id;
  public rs.acctrak.Db db;
  public android.content.SharedPreferences sp;

  @Override
  public android.view.View onCreateView
  (
    android.view.LayoutInflater inflater, 
    android.view.ViewGroup container,
    android.os.Bundle savedInstanceState
  ) 
  {
    android.widget.TextView title;
    android.widget.LinearLayout l1, l2, l3, l4, l5, l6;
    android.util.DisplayMetrics dm;
    float wr, hr;

    this.db=new rs.acctrak.Db(this.getActivity());
    dm=this.getResources().getDisplayMetrics();
    hr=dm.heightPixels/(float)100;
    wr=dm.widthPixels/(float)100;
    
    this.chart_view=new Chart_View(getActivity());

    this.balance_check=new android.widget.CheckBox(this.getActivity());
    this.balance_check.setText("Balance");
    this.balance_check.setChecked(false);
    this.balance_check.setTextColor(android.graphics.Color.WHITE);
    this.balance_check.setBackgroundColor(android.graphics.Color.BLACK);
    this.balance_check.setOnCheckedChangeListener(this);
    
    this.savings_check=new android.widget.CheckBox(this.getActivity());
    this.savings_check.setText("Savings");
    this.savings_check.setChecked(false);
    this.savings_check.setTextColor(android.graphics.Color.WHITE);
    this.savings_check.setBackgroundColor(android.graphics.Color.BLACK);
    this.savings_check.setOnCheckedChangeListener(this);
    
    this.expenses_check=new android.widget.CheckBox(this.getActivity());
    this.expenses_check.setText("Expenses");
    this.expenses_check.setChecked(false);
    this.expenses_check.setTextColor(android.graphics.Color.WHITE);
    this.expenses_check.setBackgroundColor(android.graphics.Color.BLACK);
    this.expenses_check.setOnCheckedChangeListener(this);

    this.start_date_view=new android.widget.TextView(getActivity());
    this.start_date_view.setTextColor(android.graphics.Color.WHITE);
    this.start_date_view.setOnClickListener(this);
    this.start_date_view.setId(TEXTVIEW_START_DATE);

    this.end_date_view=new android.widget.TextView(getActivity());
    this.end_date_view.setTextColor(android.graphics.Color.WHITE);
    this.end_date_view.setOnClickListener(this);
    this.end_date_view.setId(TEXTVIEW_END_DATE);

    if (rs.android.ui.Util.Is_Landscape_Mode(this))
    {
      this.start_date_view.setPadding(0, (int)((float)3*hr), 0, 0);
      this.end_date_view.setPadding(0, (int)((float)3*hr), 0, 0);
      //this.balance_check.setPadding(0, 0, 0, 0);
      //this.savings_check.setPadding(0, 0, 0, 0);
      //this.expenses_check.setPadding(0, 0, 0, 0);
      this.chart_view.setPadding((int)((float)3*hr), (int)((float)3*hr), 0, 0);

      this.balance_check.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      this.savings_check.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      this.expenses_check.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      this.start_date_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      this.end_date_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);

      l2=new android.widget.LinearLayout(this.getActivity());
      l2.setOrientation(android.widget.LinearLayout.VERTICAL);
      l2.setPadding(0, (int)((float)3*hr), 0, 0);
      l2.addView(this.balance_check);
      l2.addView(this.savings_check);
      l2.addView(this.expenses_check);

      // dates layout
      l4=new android.widget.LinearLayout(this.getActivity());
      l4.setOrientation(android.widget.LinearLayout.VERTICAL);
      // start date
      title=new android.widget.TextView(getActivity());
      title.setText("Start Date");
      title.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, hr*(float)4);
      title.setPadding(0, 0, 0, 0);
      title.setIncludeFontPadding(false);
      l4.addView(this.start_date_view);
      l4.addView(title);
      // end date
      title=new android.widget.TextView(getActivity());
      title.setPadding(0, 0, 0, 0);
      title.setText("End Date");
      title.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, hr*(float)4);
      title.setIncludeFontPadding(false);
      l4.addView(this.end_date_view);
      l4.addView(title);
      l4.addView(l2);

      // main layout
      l1=new android.widget.LinearLayout(this.getActivity());
      l1.setOrientation(android.widget.LinearLayout.HORIZONTAL);
      l1.addView(l4);
      l1.addView(this.chart_view);
    }
    else
    {
      this.start_date_view.setPadding(0, (int)((float)3*hr), 0, 0);
      this.end_date_view.setPadding(0, (int)((float)3*hr), 0, 0);
      //this.balance_check.setPadding(0, 0, 0, 0);
      //this.savings_check.setPadding(0, 0, 0, 0);
      //this.expenses_check.setPadding(0, 0, 0, 0);
      this.chart_view.setPadding(0, (int)((float)3*hr), (int)((float)3*hr), 0);

      this.balance_check.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      this.savings_check.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      this.expenses_check.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      this.start_date_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      this.end_date_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);

      // checkboxes layout
      l4=new android.widget.LinearLayout(this.getActivity());
      l4.setOrientation(android.widget.LinearLayout.VERTICAL);
      l4.setPadding((int)((float)4*wr), (int)((float)2*hr), 0, 0);
      l4.addView(this.balance_check);
      l4.addView(this.savings_check);
      l4.addView(this.expenses_check);

      // dates layout
      l3=new android.widget.LinearLayout(this.getActivity());
      l3.setOrientation(android.widget.LinearLayout.VERTICAL);
      l3.setPadding((int)((float)6*wr), 0, 0, 0);
      // start date
      title=new android.widget.TextView(getActivity());
      title.setText("Start Date");
      title.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, hr*(float)2.5);
      title.setPadding(0, 0, 0, 0);
      title.setIncludeFontPadding(false);
      l3.addView(this.start_date_view);
      l3.addView(title);
      // end date
      title=new android.widget.TextView(getActivity());
      title.setPadding(0, 0, 0, 0);
      title.setText("End Date");
      title.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, hr*(float)2.5);
      title.setIncludeFontPadding(false);
      l3.addView(this.end_date_view);
      l3.addView(title);

      // figures banner
      l2=new android.widget.LinearLayout(this.getActivity());
      l2.setOrientation(android.widget.LinearLayout.HORIZONTAL);
      l2.addView(l4);
      l2.addView(l3);

      // main layout
      l1=new android.widget.LinearLayout(this.getActivity());
      l1.setOrientation(android.widget.LinearLayout.VERTICAL);
      l1.addView(l2);
      l1.addView(this.chart_view);
    }
    
    return l1;
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    
    if (this.db==null)
      this.db=new rs.acctrak.Db(this.getActivity());
    
    if (this.sp==null)
      this.sp = android.preference.PreferenceManager.getDefaultSharedPreferences(this.getActivity());
    this.Update_UI(this.db);
  }
  
  @Override
  public void onPause()
  {
    super.onPause();
    
    if (this.db!=null)
    {
      this.db.Close();
      this.db=null;
    }
  }
  
  public void onCheckedChanged(android.widget.CompoundButton check, boolean is_checked)
  {
    if (check==this.balance_check)
      Setting.Set_Show_Balance(this.sp, is_checked);
    if (check==this.savings_check)
      Setting.Set_Show_Savings(this.sp, is_checked);
    if (check==this.expenses_check)
      Setting.Set_Show_Expenses(this.sp, is_checked);
    //if (check==this.spend_check)
      //Setting.Set_Show_To_Spend(this.sp, is_checked);
    
    this.Update_UI(this.db);
  }

  public void onClick(android.view.View v)
  {
    if (v.getId()==TEXTVIEW_START_DATE)
    {
      this.date_dlg_id=DLG_INPUT_START_DATE;
      this.getActivity().showDialog(this.date_dlg_id);
    }
    else if (v.getId()==TEXTVIEW_END_DATE)
    {
      this.date_dlg_id=DLG_INPUT_END_DATE;
      this.getActivity().showDialog(this.date_dlg_id);
    }
  }
  
  public void Update_UI(rs.acctrak.Db db)
  {
    boolean show_to_spend, show_expenses, show_savings, show_balance;
    java.sql.Date start_date, end_date;
    java.util.ArrayList<?> bal_data=null, sav_data=null, exp_data=null, spnd_data=null;
    Double max_amount;
    
    // read checkbox setings
    show_balance=Setting.Get_Show_Balance(this.sp);
    show_savings=Setting.Get_Show_Savings(this.sp);
    show_expenses=Setting.Get_Show_Expenses(this.sp);
    show_to_spend=Setting.Get_Show_To_Spend(this.sp);
    
    // read date settings    
    start_date=Setting.Get_Start_Date(this.sp);
    end_date=Setting.Get_End_Date(this.sp);

    // read chart data
    if (show_balance)
      bal_data=Financial_Action.SelectObjs(db, null, "action_type='balance'", "action_date asc");
    if (show_savings)
      sav_data=Financial_Action.SelectObjs(db, null, "action_type='savings'", "action_date asc");
    if (show_expenses)
      exp_data=Financial_Action.SelectObjs(db, null, "action_type='expenses'", "action_date asc");
    if (show_to_spend)
      spnd_data=Financial_Action.SelectObjs(db, null, "action_type='to_spend'", "action_date asc");

    // set ui checkboxes
    this.balance_check.setOnCheckedChangeListener(null);
    if (show_balance)
      this.balance_check.setChecked(true);
    else
      this.balance_check.setChecked(false);
    this.balance_check.setOnCheckedChangeListener(this);
    
    this.savings_check.setOnCheckedChangeListener(null);
    if (show_savings)
      this.savings_check.setChecked(true);
    else
      this.savings_check.setChecked(false);
    this.savings_check.setOnCheckedChangeListener(this);
    
    this.expenses_check.setOnCheckedChangeListener(null);
    if (show_expenses)
      this.expenses_check.setChecked(true);
    else
      this.expenses_check.setChecked(false);
    this.expenses_check.setOnCheckedChangeListener(this);

    //this.spend_check.setOnCheckedChangeListener(null);
    //if (show_to_spend)
    //  this.spend_check.setChecked(true);
    //else
    //  this.spend_check.setChecked(false);
    //this.spend_check.setOnCheckedChangeListener(this);
    
    // set ui dates
    this.start_date_view.setText(rs.android.util.Type.To_String(start_date, "n/a", "dd/MM/yyyy"));
    this.end_date_view.setText(rs.android.util.Type.To_String(end_date, "n/a", "dd/MM/yyyy"));

    // set chart data and invalidate
    max_amount=(Double)db.Select_Value(Double.class, "select max(amount) from financial_action");
		if (max_amount==null)
			max_amount=new Double(100);
    this.chart_view.Clear_Series();
    this.chart_view.Add_Series("balance", bal_data);
    this.chart_view.Add_Series("savings", sav_data);
    this.chart_view.Add_Series("expenses", exp_data);
    this.chart_view.Add_Series("to_spend", spnd_data);
    this.chart_view.Set_Bound_Rect(start_date, Double.valueOf(0), end_date, 
		  max_amount+300);
    this.chart_view.invalidate();
  }
  
  // Dialog Functions =============================================================================================================================================================

  public android.app.Dialog onCreateDialog(int id)
  {
    android.app.Dialog res=null;
    java.sql.Date now;
    
    if (this.getActivity()!=null)
    {
      now=rs.android.util.Date.Now();
      res=new android.app.DatePickerDialog(this.getActivity(), this, 
          rs.android.util.Date.Date_Get_Year(now), 
					rs.android.util.Date.Date_Get_Month(now), 
					rs.android.util.Date.Date_Get_Day(now));
    }
    return res;
  }

  public void onPrepareDialog(int id, android.app.Dialog dialog)
  {
    java.sql.Date date=null;
    android.app.DatePickerDialog date_dlg;

    if (id==Chart_Page.DLG_INPUT_START_DATE)
    {
      date=Setting.Get_Start_Date(this.sp);
      if (date==null)
        date=rs.android.util.Date.Add_Months(rs.android.util.Date.Now(), -1);

      date_dlg=(android.app.DatePickerDialog)dialog;
      date_dlg.updateDate(rs.android.util.Date.Date_Get_Year(date), 
			  rs.android.util.Date.Date_Get_Month(date), 
				rs.android.util.Date.Date_Get_Day(date));
    }
    else if (id==Chart_Page.DLG_INPUT_END_DATE)
    {
      date=Setting.Get_End_Date(this.sp);
      if (date==null)
        date=rs.android.util.Date.Now();

      date_dlg=(android.app.DatePickerDialog)dialog;
      date_dlg.updateDate(rs.android.util.Date.Date_Get_Year(date), 
			  rs.android.util.Date.Date_Get_Month(date), 
				rs.android.util.Date.Date_Get_Day(date));
    }
  }

  public void onDateSet(android.widget.DatePicker view, int year, int month, int day)
  {
    java.sql.Date start_date, end_date;
    
    if (this.date_dlg_id==Chart_Page.DLG_INPUT_START_DATE)
    {
      start_date=rs.android.util.Date.New_Date(year, month+1, day);
      Setting.Set_Start_Date(this.sp, start_date);
    }
    else if (this.date_dlg_id==Chart_Page.DLG_INPUT_END_DATE)
    {
      end_date=rs.android.util.Date.New_Date(year, month+1, day);
      Setting.Set_End_Date(this.sp, end_date);
    }
    
    if (this.date_dlg_id!=0)
    {
      this.Update_UI(this.db);
      this.date_dlg_id=0;
    }
  }

  // Chart widget =================================================================================================================================================================
  
  public class Chart_View 
    extends android.view.View 
  {
    public java.util.HashMap<String, java.util.ArrayList<?>> data;
    public float wr;
    public float hr;
    public android.graphics.Paint axis_paint;
    public android.graphics.Paint bk_paint;
    public android.graphics.Paint text_paint;
    public android.graphics.Paint bal_paint, sav_paint, exp_paint, spd_paint;
    public android.graphics.RectF world_window;
    public android.graphics.RectF canvas_window;
    public android.graphics.LinearGradient gradient;
    public java.sql.Date data_x_min;
    public java.sql.Date data_x_max;
    public Double data_y_min;
    public Double data_y_max;
    public float text_size;

    public void Clear_Series()
    {
      this.data=null;
    }
    
    public void Del_Series(String id)
    {
      if (rs.android.Util.NotEmpty(id) && rs.android.Util.NotEmpty(this.data))
      {
        this.data.remove(id);
      }
    }
    
    public void Add_Series(String id, java.util.ArrayList<?> series)
    {
      if (rs.android.Util.NotEmpty(series))
      {
        if (this.data==null)
          this.data=new java.util.HashMap<String, java.util.ArrayList<?>>();
        this.data.put(id, series);
      }
    }
    
    public Chart_View(android.content.Context context)
    {
      super(context);
      this.setBackgroundColor(android.graphics.Color.BLACK);
    }
    
    public void Init(int w, int h)
    {
      android.util.DisplayMetrics dm;

      dm=this.getResources().getDisplayMetrics();
      hr=(float)dm.heightPixels/(float)100;
      wr=(float)dm.widthPixels/(float)100;

      if (this.axis_paint==null)
      {
        this.canvas_window=new android.graphics.RectF();
        this.canvas_window.top=this.getPaddingTop();
        this.canvas_window.left=this.getPaddingLeft();
        this.canvas_window.right=w-this.getPaddingRight();
        this.canvas_window.bottom=h-this.getPaddingBottom();
        
        if (hr<wr)
        {
          this.canvas_window.left+=hr*(float)20;
          this.canvas_window.bottom-=hr*(float)22;
          text_size=hr*(float)3;
        }
        else
        {
          this.canvas_window.left+=wr*(float)20;
          this.canvas_window.bottom-=wr*(float)22;
          text_size=wr*(float)3;
        }
        
        this.gradient=new android.graphics.LinearGradient(
            0, this.canvas_window.top, 
            0, this.canvas_window.bottom, 
            android.graphics.Color.BLACK, 
            android.graphics.Color.DKGRAY, 
            android.graphics.Shader.TileMode.CLAMP);
        this.bk_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.bk_paint.setShader(gradient);

        this.axis_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.axis_paint.setColor(android.graphics.Color.DKGRAY);
        this.axis_paint.setStrokeWidth(1);
        this.axis_paint.setStyle(android.graphics.Paint.Style.STROKE);

        this.bal_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.bal_paint.setColor(android.graphics.Color.GREEN);
        this.bal_paint.setStrokeWidth(3);
        this.bal_paint.setMaskFilter(new android.graphics.BlurMaskFilter(10, android.graphics.BlurMaskFilter.Blur.SOLID));
        this.bal_paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);

        this.sav_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.sav_paint.setColor(android.graphics.Color.YELLOW);
        this.sav_paint.setStrokeWidth(3);
        this.sav_paint.setMaskFilter(new android.graphics.BlurMaskFilter(10, android.graphics.BlurMaskFilter.Blur.SOLID));
        this.sav_paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);

        this.exp_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.exp_paint.setColor(android.graphics.Color.RED);
        this.exp_paint.setStrokeWidth(3);
        this.exp_paint.setMaskFilter(new android.graphics.BlurMaskFilter(10, android.graphics.BlurMaskFilter.Blur.SOLID));
        this.exp_paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);

        this.spd_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.spd_paint.setColor(android.graphics.Color.BLUE);
        this.spd_paint.setStrokeWidth(3);
        this.spd_paint.setMaskFilter(new android.graphics.BlurMaskFilter(10, android.graphics.BlurMaskFilter.Blur.SOLID));
        this.spd_paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);

        this.text_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.text_paint.setColor(android.graphics.Color.GRAY);
        this.text_paint.setTextSize(text_size);
        this.text_paint.setStyle(android.graphics.Paint.Style.FILL);
        this.text_paint.setTextAlign(android.graphics.Paint.Align.LEFT);
        this.text_paint.setStyle(android.graphics.Paint.Style.STROKE);
      }
    }
    
    public void onSizeChanged(int w, int h, int oldw, int oldh)
    {
      Init(w, h);
    }

    @SuppressWarnings("unchecked")
    public java.sql.Date Get_Data_X_Min()
    {
      java.sql.Date res=null, series_min;
      java.util.ArrayList<Financial_Action> fa_series;
      
      if (rs.android.Util.NotEmpty(this.data))
      {
        for (String key: this.data.keySet())
        {
          fa_series=(java.util.ArrayList<Financial_Action>)this.data.get(key);
          series_min=(java.sql.Date)rs.android.Util.Min(fa_series, "action_date", null, null);
          if (res==null || (series_min!=null && series_min.getTime()<res.getTime()))
            res=series_min;
        }
      }
      return res;
    }
    
    @SuppressWarnings("unchecked")
    public java.sql.Date Get_Data_X_Max()
    {
      java.sql.Date res=null, series_max;
      java.util.ArrayList<Financial_Action> fa_series;
      
      if (rs.android.Util.NotEmpty(this.data))
      {
        for (String key: this.data.keySet())
        {
          fa_series=(java.util.ArrayList<Financial_Action>)this.data.get(key);
          series_max=(java.sql.Date)rs.android.Util.Max(fa_series, "action_date", null, null);
          if (res==null || (series_max!=null && series_max.getTime()>res.getTime()))
            res=series_max;
        }
      }
      return res;
    }
    
    public void Set_Bound_Rect(java.sql.Date data_x_min, Double data_y_min, 
        java.sql.Date data_x_max, Double data_y_max)
    {
      this.data_x_min=data_x_min;
      this.data_x_max=data_x_max;
      this.data_y_min=data_y_min;
      this.data_y_max=data_y_max;
      
      // if min not set and data avail then set to data min
      if (!rs.android.Util.NotEmpty(this.data_x_min) && rs.android.Util.NotEmpty(this.data))
        this.data_x_min=this.Get_Data_X_Min();
      if (!rs.android.Util.NotEmpty(this.data_x_max) && rs.android.Util.NotEmpty(this.data))
        this.data_x_max=this.Get_Data_X_Max();

      this.world_window=new android.graphics.RectF();
      if (this.data_x_min!=null)
        this.world_window.left=rs.android.util.Type.To_Float(this.data_x_min);
      if (this.data_x_max!=null)
        this.world_window.right=rs.android.util.Type.To_Float(this.data_x_max);
      if (this.data_y_max!=null)
        this.world_window.top=rs.android.util.Type.To_Float(this.data_y_max);
      this.world_window.bottom=0;
    }
    
    public void Draw_Axis(android.graphics.Canvas canvas)
    {
      long data_x_inc;
      android.graphics.PointF canvas_pt;
      java.sql.Date x;
      Double data_y_inc, y;
      float tx, ty;
      
      // draw bk
      canvas.drawRect(this.canvas_window, this.bk_paint);
      
      // draw line
      canvas.drawLine(this.canvas_window.left, this.canvas_window.bottom, this.canvas_window.right, this.canvas_window.bottom, this.axis_paint);

      // draw x axis
      if (data_x_min!=null && data_x_max!=null)
      {
        //data_x_inc=1000*60*60*24*7;
        data_x_inc=(long)(((double)data_x_max.getTime()-(double)data_x_min.getTime())/(double)10);
        for (x=data_x_min; x.before(data_x_max); x=(java.sql.Date)rs.android.Util.Add(x, data_x_inc))
        {
          canvas_pt=rs.android.ui.Util.To_Canvas_Pt(this.world_window, this.canvas_window, x.getTime(), (float)0);
          canvas.drawLine(canvas_pt.x, canvas_pt.y, canvas_pt.x, canvas_pt.y+10, this.axis_paint);
  
          this.text_paint.setTextAlign(android.graphics.Paint.Align.RIGHT);
          canvas.save();
          canvas.rotate(-45, canvas_pt.x+10, canvas_pt.y+10);
          canvas.drawText(rs.android.util.Type.To_String(x, null, "dd/MM/yyyy"), canvas_pt.x, canvas_pt.y+10, this.text_paint);
          canvas.restore();
        }
      }
      
      // draw x axis label
      this.text_paint.setTextAlign(android.graphics.Paint.Align.CENTER);
      tx=(this.canvas_window.left+this.canvas_window.right)/2;
      ty=this.canvas_window.bottom+((float)6*this.text_size);
      canvas.drawText("Date", tx, ty, this.text_paint);

      // draw y axis
      if (data_y_min!=null && data_y_max!=null)
      {
        data_y_inc=Double.valueOf(500);
        for (y=data_y_min; y<data_y_max; y+=data_y_inc)
        {
          canvas_pt=rs.android.ui.Util.To_Canvas_Pt(this.world_window, this.canvas_window, 0, y.floatValue());
          canvas.drawLine(this.canvas_window.left, canvas_pt.y, this.canvas_window.right, canvas_pt.y, this.axis_paint);
  
          this.text_paint.setTextAlign(android.graphics.Paint.Align.RIGHT);
          canvas.drawText(rs.android.util.Type.To_String(y), this.canvas_window.left-5, canvas_pt.y, this.text_paint);
        }
      }
      
      // draw y axis label
      this.text_paint.setTextAlign(android.graphics.Paint.Align.CENTER);
      tx=this.canvas_window.left-((float)5*this.text_size);
      ty=(this.canvas_window.top+this.canvas_window.bottom)/2;
      canvas.save();
      canvas.rotate(-90, tx, ty);
      canvas.drawText("Amount ($)", tx, ty, this.text_paint);
      canvas.restore();
    }
    
    @SuppressWarnings("unchecked")
    public void onDraw(android.graphics.Canvas canvas) 
    {
      android.graphics.PointF canvas_pt, last_pt=null;
      java.util.ArrayList<Financial_Action> fa_series;
      android.graphics.Paint data_paint;
      int c;
      Financial_Action fa;

      Draw_Axis(canvas);
      
      if (rs.android.Util.NotEmpty(this.data))
      {
        canvas.save();
        canvas.clipRect(this.canvas_window, android.graphics.Region.Op.REPLACE);
        
        for (String key: this.data.keySet())
        {
          if (key.equals("balance"))
            data_paint=this.bal_paint;
          else if (key.equals("savings"))
            data_paint=this.sav_paint;
          else if (key.equals("expenses"))
            data_paint=this.exp_paint;
          else
            data_paint=this.axis_paint;
          
          fa_series=(java.util.ArrayList<Financial_Action>)this.data.get(key);
          if (fa_series.size()==1)
          {
            fa=fa_series.get(0);
            canvas_pt=rs.android.ui.Util.To_Canvas_Pt(this.world_window, this.canvas_window, 
                fa.action_date.getTime(), fa.amount.floatValue());
            
            canvas.drawLine(this.canvas_window.left, this.canvas_window.bottom, canvas_pt.x, this.canvas_window.bottom, data_paint);
            canvas.drawLine(canvas_pt.x, this.canvas_window.bottom, canvas_pt.x, canvas_pt.y, data_paint);
            canvas.drawLine(canvas_pt.x, canvas_pt.y, this.canvas_window.right, canvas_pt.y, data_paint);
          }
          else
          {
            last_pt=null;
            for (c=0; c<fa_series.size(); c++)
            {
              fa=fa_series.get(c);
              //rs.android.Util.Dump_To_Log(fa);
              
              canvas_pt=rs.android.ui.Util.To_Canvas_Pt(this.world_window, this.canvas_window, 
                fa.action_date.getTime(), fa.amount.floatValue());
              
              if (last_pt!=null)
                canvas.drawLine(last_pt.x, last_pt.y, canvas_pt.x, canvas_pt.y, data_paint);
              
              // chart starts inside drawing area...
              if (c==0 && canvas_pt.x>this.canvas_window.left)
              {
                canvas.drawLine(this.canvas_window.left, this.canvas_window.bottom, canvas_pt.x, this.canvas_window.bottom, data_paint);
                canvas.drawLine(canvas_pt.x, this.canvas_window.bottom, canvas_pt.x, canvas_pt.y, data_paint);
              }
              
              // chart ends inside drawing area...
              if (c==fa_series.size()-1 && canvas_pt.x<this.canvas_window.right)
                canvas.drawLine(canvas_pt.x, canvas_pt.y, this.canvas_window.right, canvas_pt.y, data_paint);
              
              last_pt=canvas_pt;
            }
          }
        }
        
        canvas.restore();
      }
    }
  }
}
