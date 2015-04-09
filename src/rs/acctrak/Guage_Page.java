package rs.acctrak;

public class Guage_Page 
  extends android.support.v4.app.Fragment
  implements 
    android.view.View.OnClickListener,
    android.content.DialogInterface.OnClickListener, 
    android.app.DatePickerDialog.OnDateSetListener,
    com.nineoldandroids.animation.Animator.AnimatorListener
{
  public static final int DLG_INPUT_BAL=1;
  public static final int DLG_INPUT_SAV=2;
  public static final int DLG_INPUT_EXP=3;
  public static final int DLG_INPUT_PAY=4;
  
  public android.widget.TextView pay_view;
  public android.widget.TextView balance_view;
  public android.widget.TextView savings_view;
  public android.widget.TextView expenses_view;
  public android.widget.TextView spend_view;
  public android.widget.Button upd_bal_button;
  public Guage_View guage_view;
  public android.app.Dialog bal_dlg;
  public android.app.Dialog sav_dlg;
  public android.app.Dialog exp_dlg;
  public android.app.DatePickerDialog pay_dlg;
  public Main_Activity activity;
  
  public rs.acctrak.db.Db db;
  public float savings, expenses, balance, avail_per_day, avail_days;
  public boolean savings_changed, expenses_changed;
  public java.sql.Date next_pay;
  
  public android.media.SoundPool sounds;
  public int sfx_id_coin_up, sfx_id_coin_down, sfx_id_coin;

  @Override
  public android.view.View onCreateView
  (
    android.view.LayoutInflater inflater, 
    android.view.ViewGroup container,
    android.os.Bundle savedInstanceState
  ) 
  {
    //android.widget.TextView title;
    int col_padding=4;
    android.widget.LinearLayout l1, l2, l3, l4, l5, l6;
    android.util.DisplayMetrics dm;
    float wr, hr;
    
    this.activity=(Main_Activity)this.getActivity();
    this.db=new rs.acctrak.db.Db(this.getActivity());
    com.nineoldandroids.animation.ObjectAnimator.setFrameDelay(1);
    this.savings_changed=false;
    this.expenses_changed=false;
    dm=this.getResources().getDisplayMetrics();
    hr=dm.heightPixels/(float)100;
    wr=dm.widthPixels/(float)100;
    
    this.sounds=new android.media.SoundPool(1, android.media.AudioManager.STREAM_MUSIC, 0);
    this.sfx_id_coin_up=sounds.load(this.getActivity(), R.raw.coins2up, 0);
    this.sfx_id_coin_down=sounds.load(this.getActivity(), R.raw.coins2down, 0);

    guage_view=new Guage_View(this.getActivity());

    balance_view=new android.widget.TextView(this.getActivity());
    balance_view.setTextColor(android.graphics.Color.GREEN);
    balance_view.setOnClickListener(this);

    savings_view=new android.widget.TextView(this.getActivity());
    savings_view.setTextColor(android.graphics.Color.YELLOW);
    savings_view.setOnClickListener(this);

    expenses_view=new android.widget.TextView(this.getActivity());
    expenses_view.setTextColor(android.graphics.Color.RED);
    expenses_view.setOnClickListener(this);

    spend_view=new android.widget.TextView(this.getActivity());
    spend_view.setTextColor(android.graphics.Color.WHITE);

    pay_view=new android.widget.TextView(this.getActivity());
    pay_view.setTextColor(android.graphics.Color.WHITE);
    pay_view.setOnClickListener(this);

    if (rs.android.ui.Util.Is_Landscape_Mode(this))
    {
      balance_view.setPadding(0, (int)((float)4*hr), 0, 0);
      savings_view.setPadding(0, (int)((float)4*hr), 0, 0);
      expenses_view.setPadding(0, (int)((float)4*hr), 0, 0);
      spend_view.setPadding(0, (int)((float)4*hr), 0, 0);
      pay_view.setPadding(0, (int)((float)4*hr), 0, 0);
      
      balance_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      savings_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      expenses_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);
      spend_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)7.5*hr);
      pay_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5.5*hr);

      // left column layout
      l2=new android.widget.LinearLayout(this.getActivity());
      l2.setOrientation(android.widget.LinearLayout.VERTICAL);
      // balance
      l2.addView(this.balance_view);
      l2.addView(this.activity.New_Label("Current Balance"));
      // savings
      l2.addView(this.savings_view);
      l2.addView(this.activity.New_Label("Reserved for Savings"));
      // expenses
      l2.addView(this.expenses_view);
      l2.addView(this.activity.New_Label("Reserved for Expenses"));
      
      // banner left column layout
      l5=new android.widget.LinearLayout(this.getActivity());
      l5.setOrientation(android.widget.LinearLayout.VERTICAL);
      // next pay due
      l5.addView(this.pay_view);
      l5.addView(this.activity.New_Label("Next Pay Due Date"));

      // banner right column layout
      l6=new android.widget.LinearLayout(this.getActivity());
      l6.setOrientation(android.widget.LinearLayout.VERTICAL);
      l6.setPadding((int)((float)col_padding*wr), 0, 0, 0);
      // avail per day
      l6.addView(this.spend_view);
      l6.addView(this.activity.New_Label("Funds Available per Day"));

      // right figures banner layout
      l4=new android.widget.LinearLayout(this.getActivity());
      l4.setOrientation(android.widget.LinearLayout.HORIZONTAL);
      l4.setPadding(0, 0, 0, (int)((float)4*hr));
      // guage
      l4.addView(l5);
      l4.addView(l6);
      
      // right column layout
      l3=new android.widget.LinearLayout(this.getActivity());
      l3.setOrientation(android.widget.LinearLayout.VERTICAL);
      l3.setPadding((int)((float)col_padding*wr), 0, 0, 0);
      // guage
      l3.addView(l4);
      l3.addView(this.guage_view);
  
      // main layout
      l1=new android.widget.LinearLayout(this.getActivity());
      l1.setOrientation(android.widget.LinearLayout.HORIZONTAL);
      l1.addView(l2);
      l1.addView(l3);
    }
    else // portrait view
    {
      balance_view.setPadding(0, (int)((float)4*hr), 0, 0);
      savings_view.setPadding(0, (int)((float)4*hr), 0, 0);
      expenses_view.setPadding(0, (int)((float)4*hr), 0, 0);
      spend_view.setPadding(0, (int)((float)4*hr), 0, 0);
      pay_view.setPadding(0, (int)((float)4*hr), 0, 0);
      
      balance_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      savings_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      expenses_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      pay_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)3.5*hr);
      spend_view.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)5*hr);

      // left column
      l3=new android.widget.LinearLayout(this.getActivity());
      l3.setOrientation(android.widget.LinearLayout.VERTICAL);
      // balance
      l3.addView(this.balance_view);
      l3.addView(this.activity.New_Label("Current Balance"));
      // savings
      l3.addView(this.savings_view);
      l3.addView(this.activity.New_Label("Reserved for Savings"));
      // expenses
      l3.addView(this.expenses_view);
      l3.addView(this.activity.New_Label("Reserved for Expenses"));
  
      // right column
      l4=new android.widget.LinearLayout(this.getActivity());
      l4.setOrientation(android.widget.LinearLayout.VERTICAL);
      l4.setPadding((int)((float)4*wr), 0, 0, 0);
      // next pay due
      l4.addView(this.pay_view); 
      l4.addView(this.activity.New_Label("Next Pay Due Date"));
      // avail per day
      l4.addView(this.spend_view);
      l4.addView(this.activity.New_Label("Funds Available per Day"));
  
      l2=new android.widget.LinearLayout(this.getActivity());
      l2.setOrientation(android.widget.LinearLayout.HORIZONTAL);
      l2.addView(l3);
      l2.addView(l4);
  
      l1=new android.widget.LinearLayout(this.getActivity());
      l1.setOrientation(android.widget.LinearLayout.VERTICAL);
      l1.addView(l2);
      l1.addView(this.guage_view);
    }
    
    return l1;
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    
    if (this.db==null)
      this.db=new rs.acctrak.db.Db(this.getActivity());
    
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

  public void onClick(android.view.View v)
  {
    Alert_Update_Process process;

    if (v==this.balance_view)
    {
      this.getActivity().showDialog(DLG_INPUT_BAL);
    }
    else if (v==this.savings_view)
    {
      this.getActivity().showDialog(DLG_INPUT_SAV);
    }
    else if (v==this.expenses_view)
    {
      this.getActivity().showDialog(DLG_INPUT_EXP);
    }
    else if (v==this.pay_view)
    {
      this.getActivity().showDialog(DLG_INPUT_PAY);
    }
    else if (v==this.upd_bal_button)
    {
      process=new Alert_Update_Process();
      process.main_activity=(Main_Activity)this.getActivity();
      process.execute((Void[])null);
    }
  }
  
  public void Update_UI(rs.acctrak.db.Db db)
  {
    rs.acctrak.db.Financial_Action fa;
    
    fa=rs.acctrak.db.Financial_Action.Get_Max(db);
    if (fa!=null && fa.amount!=null)
      this.guage_view.max_value=fa.amount.floatValue();
    else
      this.guage_view.max_value=1000;
    
    fa=rs.acctrak.db.Financial_Action.Get_Latest(db, "income");
    if (fa!=null)
      this.setNextPay(fa.action_date);
    else
      this.setNextPay(null);

    fa=rs.acctrak.db.Financial_Action.Get_Latest(db, "balance");
    if (fa!=null && fa.amount!=null)
      this.setBalance(fa.amount.floatValue());
    else
      this.setBalance(0);

    fa=rs.acctrak.db.Financial_Action.Get_Latest(db, "savings");
    if (fa!=null && fa.amount!=null)
      this.setSavings(fa.amount.floatValue());
    else
      this.setSavings(0);
    
    fa=rs.acctrak.db.Financial_Action.Get_Latest(db, "expenses");
    if (fa!=null && fa.amount!=null)
      this.setExpenses(fa.amount.floatValue());
    else
      this.setExpenses(0);
    
    this.setAvailPerDay();
    this.setLastUpdatedAt();
  }
  
  // Property Functions =============================================================================================================================================================

  public void setLastUpdatedAt()
  {
    java.sql.Date sql_res;
    String sql;
    
    sql="select max(start_date) from Alert_Update where status='completed'";
    sql_res=(java.sql.Date)db.Select_Value(java.sql.Date.class, sql);
    if (rs.android.Util.NotEmpty(sql_res))
      this.guage_view.setLastUpdatedAt(sql_res);
  }
  
  public float getSavings()
  {
    return this.savings;
  }
  
  public void setSavings(float savings)
  {
    this.savings=savings;
    this.savings_view.setText(rs.android.util.Type.To_String(savings, "n/a", "$#,##0.00"));
    this.guage_view.setSavings(savings);
    this.setAvailPerDay();
  }

  public float getExpenses()
  {
    return this.expenses;
  }
  
  public void setExpenses(float expenses)
  {
    this.expenses=expenses;
    this.expenses_view.setText(rs.android.util.Type.To_String(expenses, "n/a", "$#,##0.00"));
    this.guage_view.setExpenses(expenses);
    this.setAvailPerDay();
  }

  public float getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(float balance)
  {
    if (balance<this.expenses)
    {
      this.setSavings(0);
      this.setExpenses(balance);
      this.savings_changed=true;
      this.expenses_changed=true;
    }
    else if (balance<this.expenses+this.savings)
    {
      this.setSavings(balance-this.expenses);
      this.savings_changed=true;
    }
    
    this.balance=balance;
    this.balance_view.setText(rs.android.util.Type.To_String(balance, "n/a", "$#,##0.00"));
    this.guage_view.setBalance(balance);
    this.setAvailPerDay();
  }

  public java.sql.Date getNextPay()
  {
    return this.next_pay;
  }
  
  public void setNextPay(java.sql.Date next_pay)
  {
    java.util.Date today;

    if (next_pay!=null)
    {
      today=(java.sql.Date)rs.android.Util.Round(rs.android.util.Date.Now(), 
			  rs.android.util.Date.ROUND_DATE_DAY);
      this.avail_days=(next_pay.getTime()-today.getTime())/(1000*60*60*24);
    }
    else
      this.avail_days=0;
    
    this.next_pay=next_pay;
    this.pay_view.setText(rs.android.util.Type.To_String(next_pay, "n/a", "dd/MM/yyyy"));
    this.setAvailPerDay();
  }

  public float getAvailPerDay()
  {
    return this.avail_per_day;
  }
  
  public void setAvailPerDay()
  {
    if (this.avail_days>0)
      this.avail_per_day=(this.balance-this.expenses-this.savings)/this.avail_days;
    else
      this.avail_per_day=0;
    this.spend_view.setText(rs.android.util.Type.To_String(this.avail_per_day, "n/a", "$#,##0.00"));
  }
  
  // Dialog Functions =============================================================================================================================================================

  public void onAnimationCancel(com.nineoldandroids.animation.Animator arg0)
  {
  }

  public void onAnimationEnd(com.nineoldandroids.animation.Animator arg0)
  {
    if (this.savings_changed)
    {
      rs.acctrak.db.Financial_Action.Save_Savings(this.db, (double)this.savings);
      this.savings_changed=false;
    }
    if (this.expenses_changed)
    {
      rs.acctrak.db.Financial_Action.Save_Expenses(this.db, (double)this.expenses);
      this.expenses_changed=false;
    }
  }

  public void onAnimationRepeat(com.nineoldandroids.animation.Animator arg0)
  {
  }

  public void onAnimationStart(com.nineoldandroids.animation.Animator a)
  {
    this.sounds.play(this.sfx_id_coin, 1, 1, 0, 0, 1);
  }

  public android.app.Dialog onCreateDialog(int id)
  {
    android.app.Dialog res=null;
    android.app.AlertDialog.Builder dlg_builder;
    android.widget.EditText input_view;
    android.widget.LinearLayout layout;
    android.view.ViewGroup.LayoutParams params;
    java.sql.Date now;
    
    if (this.getActivity()!=null)
    {
      if (id==Guage_Page.DLG_INPUT_BAL)
      {
        params=new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
        input_view=new android.widget.EditText(this.getActivity());
        input_view.setRawInputType(android.text.InputType.TYPE_CLASS_NUMBER|android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input_view.setId(Guage_Page.DLG_INPUT_BAL);
        input_view.setTransformationMethod(new android.text.method.SingleLineTransformationMethod());
        input_view.setLayoutParams(params);
        
        layout=new android.widget.LinearLayout(this.getActivity());
        layout.setPadding(25, 0, 25, 0);
        layout.addView(input_view);
        
        dlg_builder=new android.app.AlertDialog.Builder(this.getActivity());
        dlg_builder.setTitle("How much money do you have?");
        dlg_builder.setPositiveButton("Ok", this);
        dlg_builder.setNegativeButton("Cancel", this);
        dlg_builder.setView(layout);
        
        this.bal_dlg=dlg_builder.create();
        res=this.bal_dlg;
      }
      else if (id==Guage_Page.DLG_INPUT_SAV)
      {
        params=new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
        input_view=new android.widget.EditText(this.getActivity());
        input_view.setRawInputType(android.text.InputType.TYPE_CLASS_NUMBER|android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input_view.setId(Guage_Page.DLG_INPUT_SAV);
        input_view.setTransformationMethod(new android.text.method.SingleLineTransformationMethod());
        input_view.setLayoutParams(params);
        
        layout=new android.widget.LinearLayout(this.getActivity());
        layout.setPadding(25, 0, 25, 0);
        layout.addView(input_view);
  
        dlg_builder=new android.app.AlertDialog.Builder(this.getActivity());
        dlg_builder.setTitle("How much do you intend to save?");
        dlg_builder.setPositiveButton("Ok", this);
        dlg_builder.setNegativeButton("Cancel", this);
        dlg_builder.setView(layout);
        
        this.sav_dlg=dlg_builder.create();
        res=this.sav_dlg;
      }
      else if (id==Guage_Page.DLG_INPUT_EXP)
      {
        params=new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
        input_view=new android.widget.EditText(this.getActivity());
        input_view.setRawInputType(android.text.InputType.TYPE_CLASS_NUMBER|android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input_view.setId(Guage_Page.DLG_INPUT_EXP);
        input_view.setTransformationMethod(new android.text.method.SingleLineTransformationMethod());
        input_view.setLayoutParams(params);
        
        layout=new android.widget.LinearLayout(this.getActivity());
        layout.setPadding(25, 0, 25, 0);
        layout.addView(input_view);
  
        dlg_builder=new android.app.AlertDialog.Builder(this.getActivity());
        dlg_builder.setTitle("How much do you need to spend on expenses?");
        dlg_builder.setPositiveButton("Ok", this);
        dlg_builder.setNegativeButton("Cancel", this);
        dlg_builder.setView(layout);
        
        this.exp_dlg=dlg_builder.create();
        res=this.exp_dlg;
      }
      else if (id==Guage_Page.DLG_INPUT_PAY)
      {
        now=rs.android.util.Date.Now();
        res=new android.app.DatePickerDialog(this.getActivity(), this, 
            rs.android.util.Date.Date_Get_Year(now), rs.android.util.Date.Date_Get_Month(now), 
						rs.android.util.Date.Date_Get_Day(now));
      }
    }
    return res;
  }

  public void onPrepareDialog(int id, android.app.Dialog dialog)
  {
    android.widget.EditText input_view;
    rs.acctrak.db.Financial_Action fa;
    Double val=null;
    java.sql.Date next_pay=null;
    android.app.DatePickerDialog date_dlg;

    if (id==Guage_Page.DLG_INPUT_BAL)
    {
      fa=rs.acctrak.db.Financial_Action.Get_Latest(this.db, "balance");
      if (fa!=null)
        val=fa.amount;

      input_view=(android.widget.EditText)dialog.findViewById(id);
      input_view.setText(rs.android.util.Type.To_String(val));
      rs.android.ui.Touch_Fn.Focus(input_view);
    }
    else if (id==Guage_Page.DLG_INPUT_SAV)
    {
      fa=rs.acctrak.db.Financial_Action.Get_Latest(db, "savings");
      if (fa!=null)
        val=fa.amount;

      input_view=(android.widget.EditText)dialog.findViewById(id);
      input_view.setText(rs.android.util.Type.To_String(val));
      rs.android.ui.Touch_Fn.Focus(input_view);
    }
    else if (id==Guage_Page.DLG_INPUT_EXP)
    {
      fa=rs.acctrak.db.Financial_Action.Get_Latest(this.db, "expenses");
      if (fa!=null)
        val=fa.amount;

      input_view=(android.widget.EditText)dialog.findViewById(id);
      input_view.setText(rs.android.util.Type.To_String(val));
      rs.android.ui.Touch_Fn.Focus(input_view);
    }
    if (id==Guage_Page.DLG_INPUT_PAY)
    {
      fa=rs.acctrak.db.Financial_Action.Get_Latest(this.db, "income");
      if (fa!=null)
        next_pay=fa.action_date;
      else
        next_pay=rs.android.util.Date.Now();

      date_dlg=(android.app.DatePickerDialog)dialog;
      date_dlg.updateDate(rs.android.util.Date.Date_Get_Year(next_pay), 
			  rs.android.util.Date.Date_Get_Month(next_pay), 
				rs.android.util.Date.Date_Get_Day(next_pay));
    }
  }
    
  public void onClick(android.content.DialogInterface dialog, int which)
  {
    android.widget.EditText input_view;
    String input_str;
    rs.acctrak.db.Financial_Action fa;
    Double balance;
    Double savings;
    Double expenses;
    com.nineoldandroids.animation.ObjectAnimator anim;
    Main_Activity a;

    if (dialog==this.bal_dlg)
    {
      if (which==android.content.DialogInterface.BUTTON_POSITIVE)
      {
        input_view=(android.widget.EditText)this.bal_dlg.findViewById(Guage_Page.DLG_INPUT_BAL);
        input_str=input_view.getText().toString();
        balance=rs.android.util.Type.ToDouble(input_str);
        
        if (balance!=null)
        {
          if (balance<0)
            balance=(double)0;
          
          fa=new rs.acctrak.db.Financial_Action();
          fa.action_date=rs.android.util.Date.Now();
          fa.action_type="balance";
          fa.amount=balance;
          fa.Save(this.db);
          
          if (balance>this.guage_view.max_value)
            this.guage_view.max_value=balance.floatValue();
          
          a=(Main_Activity)this.getActivity();
          if (a!=null && a.chart_page!=null)
            a.chart_page.Update_UI(this.db);
          
          if (this.getBalance()>balance.floatValue())
            this.sfx_id_coin=this.sfx_id_coin_down;
          else
            this.sfx_id_coin=this.sfx_id_coin_up;
          anim=com.nineoldandroids.animation.ObjectAnimator.ofFloat(this, "balance", balance.floatValue()); 
          anim.setDuration(1000);
          anim.addListener(this);
          anim.start();
        }
      }
    }
    else if (dialog==this.sav_dlg)
    {
      if (which==android.content.DialogInterface.BUTTON_POSITIVE)
      {
        input_view=(android.widget.EditText)this.sav_dlg.findViewById(Guage_Page.DLG_INPUT_SAV);
        input_str=input_view.getText().toString();
        savings=rs.android.util.Type.ToDouble(input_str);
        
        if (savings!=null)
        {
          if (savings<0)
            savings=(double)0;
          if (savings>this.getBalance()-this.getExpenses())
            savings=(double)this.getBalance()-(double)this.getExpenses();
          
          rs.acctrak.db.Financial_Action.Save_Savings(this.db, savings);
          
          a=(Main_Activity)this.getActivity();
          if (a!=null && a.chart_page!=null)
            a.chart_page.Update_UI(this.db);

          anim=com.nineoldandroids.animation.ObjectAnimator.ofFloat(this, "savings", savings.floatValue()); 
          anim.setDuration(1000);
          anim.addListener(this);
          anim.start();
        }
      }
    }
    else if (dialog==this.exp_dlg)
    {
      if (which==android.content.DialogInterface.BUTTON_POSITIVE)
      {
        input_view=(android.widget.EditText)this.exp_dlg.findViewById(Guage_Page.DLG_INPUT_EXP);
        input_str=input_view.getText().toString();
        expenses=rs.android.util.Type.ToDouble(input_str);
        
        if (expenses<0)
          expenses=(double)0;
        if (expenses>this.getBalance()-this.getSavings())
          expenses=(double)this.getBalance()-(double)this.getSavings();

        rs.acctrak.db.Financial_Action.Save_Expenses(this.db, expenses);
        
        a=(Main_Activity)this.getActivity();
        if (a!=null && a.chart_page!=null)
          a.chart_page.Update_UI(this.db);

        anim=com.nineoldandroids.animation.ObjectAnimator.ofFloat(this, "expenses", expenses.floatValue()); 
        anim.setDuration(1000);
        anim.addListener(this);
        anim.start();
      }
    }
  }
//
  public void onDateSet(android.widget.DatePicker view, int year, int month, int day)
  {
    rs.acctrak.db.Financial_Action fa;
    java.sql.Date next_pay, now;

    now=rs.android.util.Date.Now();
    this.db.Delete("Financial_Action", "action_type='income' and action_date>?", 
        ((java.sql.Date)rs.android.Util.Round(now, 
				rs.android.util.Date.ROUND_DATE_DAY)).getTime());

    next_pay=rs.android.util.Date.New_Date(year, month+1, day);
    
    if (next_pay.before(now))
      next_pay=null;
    else
    {
      fa=new rs.acctrak.db.Financial_Action();
      fa.action_date=rs.android.util.Date.Now();
      fa.action_date=next_pay;
      fa.action_type="income";
      fa.Save(this.db);
    }
    
    this.setNextPay(next_pay);
  }
  
  // Guage widget =================================================================================================================================================================
  
  public class Guage_View 
    extends android.view.View 
  {
    public android.graphics.LinearGradient bk_gradient_2;
    public android.graphics.RadialGradient frame_gradient;
    public android.graphics.SweepGradient bk_gradient_1;
    public android.graphics.Paint bk_paint, frame_paint, bal_paint, sav_paint, exp_paint, val4_paint, upd_paint;
    public Float cx;
    public Float cy;
    public Float r;
    public android.graphics.RectF frame_rect, val_rect;
    public boolean new_shaders;
    public float max_value;
    public String last_upd_text;
    public android.graphics.Path upd_path;
    public float s;
    public int width, height;
    
    public float balance, bal_start_angle, bal_d_angle;
    public float savings, sav_start_angle, sav_d_angle;
    public float expenses, exp_start_angle, exp_d_angle;
    public int[] bal_cols, sav_cols, exp_cols;
    public float[] bal_pos, sav_pos, exp_pos;

    public Guage_View(android.content.Context context)
    {
      super(context);
      this.new_shaders=false;
    }
    
    public void Init(int w, int h)
    {
      int[] cols=
      {
        android.graphics.Color.GRAY, 
        android.graphics.Color.LTGRAY, 
        android.graphics.Color.GRAY, 
        android.graphics.Color.DKGRAY, 
        
        android.graphics.Color.GRAY, 
        android.graphics.Color.LTGRAY, 
        android.graphics.Color.GRAY, 
        android.graphics.Color.DKGRAY, 
        
        android.graphics.Color.GRAY
      };
      float[] pos={(float)0.0, (float)0.125, (float)0.25, (float)0.375, (float)0.5, 
          (float)0.625, (float)0.75, (float)0.875, (float)1.0};
      rs.android.ui.Util ui;

      this.width=w;
      this.height=h;
      if (this.cx==null && w>0 && h>0)
      {
        ui=new rs.android.ui.Util(this.getContext());
        
        this.cx=(float)w/(float)2;
        this.cy=(float)h-ui.To_Canvas_Y(4);
        if (this.cx<this.cy)
        {
          this.r=this.cx;
          s=ui.To_Canvas_X(1);
        }
        else
        {
          this.r=this.cy;
          s=ui.To_Canvas_Y(1);
        }
            
        this.bk_gradient_2=new android.graphics.LinearGradient(
            0, this.cy-this.r, 
            0, this.cy+this.r, 
            android.graphics.Color.BLACK, 
            android.graphics.Color.GRAY, 
            android.graphics.Shader.TileMode.CLAMP);
        this.bk_gradient_1=new android.graphics.SweepGradient(
            this.cx, 
            this.cy, 
            cols,
            pos
            );
        this.bk_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.bk_paint.setShader(bk_gradient_1);

        this.frame_gradient=new android.graphics.RadialGradient(
            this.cx, 
            this.cy, 
            this.r, 
            android.graphics.Color.rgb(0, 0, 255), 
            android.graphics.Color.rgb(0, 0, 0), 
            android.graphics.Shader.TileMode.CLAMP);
        this.frame_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.frame_paint.setColor(android.graphics.Color.BLACK);
        this.frame_paint.setStrokeWidth(s*(float)15);
        this.frame_paint.setStyle(android.graphics.Paint.Style.STROKE);
        this.frame_paint.setShader(frame_gradient);
        
        this.frame_rect=new android.graphics.RectF();
        this.frame_rect.top=this.cy-this.r+(s*(float)10);
        this.frame_rect.bottom=this.cy+this.r-(s*(float)10);
        this.frame_rect.left=this.cx-this.r+(s*(float)10);
        this.frame_rect.right=this.cx+this.r-(s*(float)10);

        this.bal_cols=new int[2];
        this.bal_cols[0]=android.graphics.Color.rgb(0, 192, 0);
        this.bal_cols[1]=android.graphics.Color.rgb(64, 255, 64);
        this.bal_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.bal_paint.setColor(android.graphics.Color.GREEN);
        this.bal_paint.setStrokeWidth(s*(float)10);
        this.bal_paint.setStyle(android.graphics.Paint.Style.STROKE);
        this.bal_paint.setMaskFilter(new android.graphics.BlurMaskFilter(20, android.graphics.BlurMaskFilter.Blur.SOLID));

        this.sav_cols=new int[2];
        this.sav_cols[0]=android.graphics.Color.rgb(192, 192, 0);
        this.sav_cols[1]=android.graphics.Color.rgb(255, 255, 64);
        this.sav_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.sav_paint.setColor(android.graphics.Color.YELLOW);
        this.sav_paint.setStrokeWidth(s*(float)10);
        this.sav_paint.setStyle(android.graphics.Paint.Style.STROKE);
        this.sav_paint.setMaskFilter(new android.graphics.BlurMaskFilter(20, android.graphics.BlurMaskFilter.Blur.SOLID));
        this.sav_paint.setShader(new android.graphics.SweepGradient(this.cx, this.cy, this.sav_cols, this.sav_pos));

        this.exp_cols=new int[2];
        this.exp_cols[0]=android.graphics.Color.rgb(192, 0, 0);
        this.exp_cols[1]=android.graphics.Color.rgb(255, 64, 64);
        this.exp_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.exp_paint.setColor(android.graphics.Color.RED);
        this.exp_paint.setStrokeWidth(s*(float)10);
        this.exp_paint.setStyle(android.graphics.Paint.Style.STROKE);
        this.exp_paint.setMaskFilter(new android.graphics.BlurMaskFilter(20, android.graphics.BlurMaskFilter.Blur.SOLID));
        this.exp_paint.setShader(new android.graphics.SweepGradient(this.cx, this.cy, this.exp_cols, this.exp_pos));

        this.val4_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.val4_paint.setColor(android.graphics.Color.BLUE);
        this.val4_paint.setStrokeWidth(s*(float)10);
        this.val4_paint.setStyle(android.graphics.Paint.Style.STROKE);
        this.val4_paint.setMaskFilter(new android.graphics.BlurMaskFilter(20, android.graphics.BlurMaskFilter.Blur.SOLID));

        this.upd_paint=new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG|android.graphics.Paint.DITHER_FLAG);
        this.upd_paint.setColor(android.graphics.Color.BLACK);
        this.upd_paint.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.upd_paint.setTextSize(s*(float)3.5);
        this.upd_paint.setShadowLayer((float)0.5, 1, 1, android.graphics.Color.WHITE);
        //this.val4_paint.setStrokeWidth(s*(float)10);
        //this.val4_paint.setStyle(android.graphics.Paint.Style.STROKE);
        //this.upd_paint.setMaskFilter(new android.graphics.BlurMaskFilter(20, android.graphics.BlurMaskFilter.Blur.SOLID));

        this.upd_path=new android.graphics.Path();
        this.upd_path.addArc(this.frame_rect, 180, 180);
      }
    }
    
    public void onDraw(android.graphics.Canvas canvas) 
    {
      if (this.new_shaders)
        this.Prep_New_Shaders();
      
      // gauge face
      canvas.drawCircle(this.cx, this.cy, this.r, this.bk_paint);
      
      // gauge interior
      canvas.drawArc(this.frame_rect, 180, 180, false, this.frame_paint);

      // expenses
      if (this.exp_d_angle>0)
        canvas.drawArc(this.frame_rect, exp_start_angle, exp_d_angle, false, this.exp_paint);
      
      // savings
      if (this.sav_d_angle>0)
        canvas.drawArc(this.frame_rect, sav_start_angle, sav_d_angle, false, this.sav_paint);
      
      // balance
      if (this.bal_d_angle>0)
        canvas.drawArc(this.frame_rect, bal_start_angle, bal_d_angle, false, this.bal_paint);
      
      if (this.last_upd_text!=null)
        //canvas.drawTextOnPath(this.last_upd_text, this.upd_path, 0, this.s*(float)10, this.upd_paint);
        canvas.drawText(this.last_upd_text, this.cx, this.height-(s*(float)1.6), this.upd_paint);
    }
    
    public void onSizeChanged(int w, int h, int oldw, int oldh)
    {
      Init(w, h);
    }
    
    public void Prep_New_Shaders()
    {
      this.bal_pos=new float[2];
      this.bal_pos[0]=this.bal_start_angle/360;
      this.bal_pos[1]=(this.bal_start_angle+this.bal_d_angle)/360;
      this.bal_paint.setShader(new android.graphics.SweepGradient(this.cx, this.cy, this.bal_cols, this.bal_pos));

      this.sav_pos=new float[2];
      this.sav_pos[0]=this.sav_start_angle/360;
      this.sav_pos[1]=(this.sav_start_angle+this.sav_d_angle)/360;
      this.sav_paint.setShader(new android.graphics.SweepGradient(this.cx, this.cy, this.sav_cols, this.sav_pos));

      this.exp_pos=new float[2];
      this.exp_pos[0]=this.exp_start_angle/360;
      this.exp_pos[1]=(this.exp_start_angle+this.exp_d_angle)/360;
      this.exp_paint.setShader(new android.graphics.SweepGradient(this.cx, this.cy, this.exp_cols, this.exp_pos));
    }
    
    public void Adjust_Segments()
    {
      this.exp_start_angle=180;
      this.exp_d_angle=this.expenses/this.max_value*(float)180;
      
      this.sav_start_angle=this.exp_start_angle+this.exp_d_angle;
      this.sav_d_angle=this.savings/this.max_value*(float)180;

      this.bal_start_angle=this.sav_start_angle+this.sav_d_angle;
      this.bal_d_angle=(this.balance-this.savings-this.expenses)/this.max_value*(float)180;
      
      this.new_shaders=true;
      this.invalidate();
    }
    
    public float getBalance()
    {
      return this.balance;
    }
    
    public void setBalance(float balance)
    {
      if (balance>=0 && balance!=this.balance)
      {
        this.balance=balance;
        this.Adjust_Segments();
      }
    }
    
    public float getSavings()
    {
      return this.savings;
    }
    
    public void setSavings(float savings)
    {
      if (savings>=0 && savings!=this.savings)
      {
        this.savings=savings;
        this.Adjust_Segments();
      }
    }
    
    public float getExpenses()
    {
      return this.expenses;
    }
    
    public void setExpenses(float expenses)
    {
      if (expenses>=0 && expenses!=this.expenses)
      {
        this.expenses=expenses;
        this.Adjust_Segments();
      }
    }
    
    public void setLastUpdatedAt(java.sql.Date upd_date)
    {
      if (rs.android.Util.NotEmpty(upd_date))
        this.last_upd_text="Last Updated at: "+
				  rs.android.util.Type.To_String(upd_date, null, "dd/MM/yyyy hh:mm:ss a");
      else
        this.last_upd_text=null;
      this.invalidate();
    }
  }
}
