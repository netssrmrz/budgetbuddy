package rs.acctrak;

public class Alert_Update_Service extends android.app.Service
{
  public class Timer_Task extends java.util.TimerTask
  {
    public int counter=0;
    public android.content.Context ctx;

    @Override
    public void run()
    {
      Alert_Update upd;
      
      upd=new Alert_Update();
      upd.Do(ctx, null, "service");
    }
  }
  
  public java.util.Timer timer;
  
  @Override
  public void onDestroy() 
  {
    super.onDestroy();
    if (this.timer != null)
    {
      this.timer.cancel();
    }
  }  
  @Override
  public void onCreate() 
  {
    Timer_Task timer_task;

    timer_task=new Timer_Task();
    timer_task.ctx=this.getBaseContext();

    this.timer=new java.util.Timer();
    this.timer.scheduleAtFixedRate(timer_task, (long)0, (long)300000); // 1000ms x 60s x 5min = 300,000ms
  }
  
  @Override
  public int onStartCommand(android.content.Intent intent, int flags, int startId) 
  {
    return START_STICKY;
  }
  
  @Override
  public android.os.IBinder onBind(android.content.Intent arg0)
  {
    return null;
  }
}
