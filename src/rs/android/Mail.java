package rs.android;

public class Mail
{
  public static final int STATUS_CONNECTED_OK=1;
  public static final int STATUS_UNABLE_TO_CONNECT=2;
  public static final int STATUS_AUTH_FAILED=3;
  public static final int STATUS_HOST_UNK=4;
  public static final int STATUS_FOLDER_UNK=5;
  
  public javax.mail.Store store;
  public com.sun.mail.imap.IMAPFolder inbox;
  public int status;

  public Mail(android.content.Context ctx, rs.android.setting.Mail mail_settings)
  {
    String host, user, password, folder;
    android.content.SharedPreferences sp;

    sp = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
    host = mail_settings.Get_Mail_Host(sp);
    user = mail_settings.Get_Mail_User(sp);
    password = mail_settings.Get_Mail_Password(sp);
    folder = mail_settings.Get_Mail_Folder(sp);
    this.Open(host, user, password, folder);
  }
  
  public Mail(String host, String user, String password, String folder)
  {
    this.Open(host, user, password, folder);
  }
  
  public void Open(String host, String user, String password, String folder)
  {
    java.util.Properties props;
    javax.mail.Session session;
    Throwable cause_ex;
    
    this.status=STATUS_CONNECTED_OK;

    props = new java.util.Properties(); 
    props.setProperty("mail.store.protocol", "imap");
    props.setProperty("mail.imap.host", "imap.gmail.com");
    props.setProperty("mail.imap.port", "993");
    props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.setProperty("mail.imap.socketFactory.fallback", "false");
    session = javax.mail.Session.getInstance(props);

    try 
    { 
      this.store = session.getStore("imap"); 
      this.store.connect(host, user, password);
    } 
    catch (javax.mail.AuthenticationFailedException e)
    {
      this.status=STATUS_AUTH_FAILED;
      this.store=null; 
    }
    catch (javax.mail.MessagingException e)
    {
      this.status=STATUS_UNABLE_TO_CONNECT;
      
      cause_ex=e.getCause();
      if (cause_ex!=null && cause_ex instanceof java.net.UnknownHostException)
        this.status=STATUS_HOST_UNK;
      this.store=null; 
    }
    catch (Exception e) 
    { 
      this.status=STATUS_UNABLE_TO_CONNECT;
      this.store=null; 
    }
    
    if (this.store!=null)
    {
      try
      {
        this.inbox = (com.sun.mail.imap.IMAPFolder)this.store.getFolder(folder);
        this.inbox.open(javax.mail.Folder.READ_ONLY);
      }
      catch (Exception e) 
      { 
        this.inbox=null; 
        this.status=STATUS_FOLDER_UNK;
      }
    }
  }
  
  public void Close()
  {
    if (this.inbox!=null)
      try { inbox.close(false); } 
      catch (javax.mail.MessagingException e) { this.inbox=null; }
    if (this.store!=null)
      try { store.close(); } 
      catch (javax.mail.MessagingException e) { this.store=null; }
  }
  
  public javax.mail.Message[] Get_Messages()
  {
    javax.mail.Message[] res=null;
    
    if (this.inbox!=null)
      try { res=this.inbox.getMessages(); } 
      catch (javax.mail.MessagingException e) { res=null; }
    return res;
  }
  
  public static boolean From_Includes(javax.mail.Message msg, String from)
  {
    boolean res=false;
    javax.mail.Address[] msg_from;
    String msg_from_str;
    int c;
    
    if (rs.android.Util.NotEmpty(msg))
    {
      try { msg_from=msg.getFrom(); } 
      catch (javax.mail.MessagingException e) { msg_from=null; }
      if (rs.android.Util.NotEmpty(msg_from))
      {
        for (c=0; c<msg_from.length; c++)
        {
          msg_from_str=msg_from[c].toString();
          if (rs.android.Util.NotEmpty(msg_from_str) && 
              rs.android.Util.NotEmpty(from) && 
              msg_from_str.contains(from))
          {
            res=true;
            break;
          }
        }
      }
    }
    return res;
  }
  
  public static String Get_Body_As_String(javax.mail.Message msg) 
  {
    String res=null;
    Object content;
    
    if (rs.android.Util.NotEmpty(msg))
    {
      try { content=msg.getContent(); } 
      catch (Exception e) { content=null; } 
      if (content instanceof String && rs.android.Util.NotEmpty(content))
        res=(String)content;
    }
    return res;
  }
  
  public static String Get_From_As_String(javax.mail.Message msg) 
  {
    String res=null;
    javax.mail.Address[] froms;
    int c;
    
    try { froms=msg.getFrom(); } 
    catch (javax.mail.MessagingException e) { froms=null; }
    if (rs.android.Util.NotEmpty(froms))
    {
      res="";
      for (c=0; c<froms.length; c++)
      {
        res=rs.android.Util.AppendStr(res, froms[c].toString(), "; ");
      }
    }
    return res;
  }
  
  public int Get_Msg_Count()
  {
    int res=0;
    
    if (this.inbox!=null)
    {
      try { res=this.inbox.getMessageCount(); } 
      catch (javax.mail.MessagingException e) { res=0; }
    }
    return res;
  }
  
  public static java.util.Date Get_Received_Date(javax.mail.Message msg)
  {
    java.util.Date res=null;
    
    try 
    { 
      res=new java.sql.Date(msg.getReceivedDate().getTime()); 
    } 
    catch (javax.mail.MessagingException e) 
    { 
      res=null; 
    }
    return res;
  }
  
  public static String Get_Subject(javax.mail.Message msg)
  {
    String res=null;
    
    try 
    { 
      res=msg.getSubject();
    } 
    catch (javax.mail.MessagingException e) 
    { 
      res=null; 
    }
    return res;
  }
}
