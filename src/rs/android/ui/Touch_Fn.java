package rs.android.ui;

public class Touch_Fn implements Runnable
{
  public android.view.View view;
  
  public static void Focus(android.view.View view)
  {
    Touch_Fn touch_fn;
    
    touch_fn=new Touch_Fn();
    touch_fn.view=view;
    view.postDelayed(touch_fn, 100);
  }
  
  public void run()
  {
    view.dispatchTouchEvent(android.view.MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), android.view.MotionEvent.ACTION_DOWN , 0, 0, 0));
    view.dispatchTouchEvent(android.view.MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), android.view.MotionEvent.ACTION_UP , 0, 0, 0));                       

    if (view instanceof android.widget.EditText)
      ((android.widget.EditText)view).selectAll();
  }
}
