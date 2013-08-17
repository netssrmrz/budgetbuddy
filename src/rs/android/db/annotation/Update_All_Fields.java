package rs.android.db.annotation;

@java.lang.annotation.Target(value={java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Update_All_Fields
{
  public boolean value() default true;
}
