package pt.inescporto.template.email.ejb.session;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultEmailTags {
  public static String fromEmail = "\\$\\{fromEmail\\}";
  public static String fromName = "\\$\\{fromName\\}";
  public static String fromUserName = "\\$\\{fromUserName\\}";
  public static String toEmail = "\\$\\{toEmail\\}";
  public static String toName = "\\$\\{toName\\}";
  public static String toUserName = "\\$\\{toUserName\\}";
}
