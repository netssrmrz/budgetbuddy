package rs.acctrak;

public class Update_Function
implements Runnable
{
  public rs.acctrak.Main_Activity activity;
  public android.app.ProgressDialog prog_dialog;
  public android.os.Handler ui_handler;
  public Thread thread;
  public int max;
  public int progress;
  
  // Update Thread Functions ==================================================================================================
  
  public void run()
  {
    Dismiss_Progress_Dialog del_dlg_fn;
    Show_Msg show_msg_fn;
    Alert_Update upd;
    int res;

    upd=new Alert_Update();
    res=upd.Do(this.activity, this, "user");
    
    if (this.activity!=null)
    {
      if (res<0)
      {
        show_msg_fn=new Show_Msg();
        show_msg_fn.activity=this.activity;
        
        if (res==Alert_Update.UPD_ERR_UPD_IN_PROGRESS)
          show_msg_fn.text="An update is currently in progress. Please wait before trying again.";
        else if (res==Alert_Update.UPD_ERR_EMAIL_AUTH_FAILED)
          show_msg_fn.text="Unable to update via e-mail due to incorrect login details.";
        else if (res==Alert_Update.UPD_ERR_EMAIL_CONNECT_FAILED)
          show_msg_fn.text="There was a problem whilst trying to update via e-mail.";
        else if (res==Alert_Update.UPD_ERR_EMAIL_FOLDER_UNK)
          show_msg_fn.text="The requested e-mail folder could not be found.";
        else if (res==Alert_Update.UPD_ERR_EMAIL_HOST_UNK)
          show_msg_fn.text="The given e-mail host could not be found.";
        else 
          show_msg_fn.text="There was a problem whilst trying to update.";
        
        this.ui_handler.post(show_msg_fn);
      }
        
      del_dlg_fn=new Dismiss_Progress_Dialog();
      this.ui_handler.post(del_dlg_fn);
    }
  }
  
  public void Inc_Progress()
  {
    Set_Progress_Dialog dlg_fn;

    if (this.activity!=null)
    {
      this.progress++;
      dlg_fn=new Set_Progress_Dialog();
      dlg_fn.progress=this.progress;
      this.ui_handler.post(dlg_fn);
    }
  }
  
  public void Max_Progress()
  {
    Max_Progress_Dialog dlg_fn;

    if (this.activity!=null)
    {
      this.progress=this.max;
      dlg_fn=new Max_Progress_Dialog();
      this.ui_handler.post(dlg_fn);
    }
  }
  
  public void Set_Max_Progress(int max)
  {
    Set_Max_Progress_Dialog dlg_fn;

    if (this.activity!=null)
    {
      this.max=max;
      dlg_fn=new Set_Max_Progress_Dialog();
      dlg_fn.max=max;
      this.ui_handler.post(dlg_fn);
    }
  }
  
  // UI Thread Functions ==================================================================================================

  public Update_Function(rs.acctrak.Main_Activity activity)
  {
    this.Set_Activity(activity);
    
    thread=new Thread(this, "rs.acctrak.Update_Function");
    thread.start();
  }
  
  public void Set_Activity(rs.acctrak.Main_Activity activity)
  {
    this.activity=null;
    if (activity!=null)
    {
      prog_dialog = new android.app.ProgressDialog(activity);
      prog_dialog.setIcon(R.drawable.ic_launcher);
      prog_dialog.setTitle("Updating...");
      prog_dialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
      prog_dialog.setMax(this.max);
      prog_dialog.setProgress(this.progress);
      this.ui_handler=new android.os.Handler(activity.getMainLooper());
      this.activity=activity;
    }
    else if (this.prog_dialog!=null)
    {
      this.prog_dialog.dismiss();
      this.prog_dialog=null;
    }
  }
  
  public class Set_Progress_Dialog
  implements Runnable
  {
    public int progress;
    
    public void run()
    {
      if (prog_dialog!=null)
      {
        prog_dialog.setProgress(this.progress);
        if (!prog_dialog.isShowing())
          prog_dialog.show();
      }
    }
  }
  
  public class Max_Progress_Dialog
  implements Runnable
  {
    public void run()
    {
      if (prog_dialog!=null)
      {
        prog_dialog.setProgress(prog_dialog.getMax());
        if (!prog_dialog.isShowing())
          prog_dialog.show();
      }
    }
  }
  
  public class Set_Max_Progress_Dialog
  implements Runnable
  {
    public int max;
    
    public void run()
    {
      if (prog_dialog!=null)
      {
        prog_dialog.setMax(this.max);
        if (!prog_dialog.isShowing())
          prog_dialog.show();
      }
    }
  }
  
  public class Dismiss_Progress_Dialog
  implements Runnable
  {
    public void run()
    {
      if (prog_dialog!=null)
        prog_dialog.dismiss();
    }
  }
  
  public class Show_Msg
  implements Runnable
  {
    public String text;
    public rs.acctrak.Main_Activity activity;

    public void run()
    {
      android.widget.Toast msg;
      
      if (this.activity!=null)
      {
        msg = android.widget.Toast.makeText(this.activity, this.text, android.widget.Toast.LENGTH_SHORT);
        msg.show();
      }
    }
  }
}
