package pt.inescporto.template.client.util;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class TemplateException extends Exception {
  public TemplateException() {
    super();
  }

  public TemplateException(String message) {
    super(message);
  }

  public TemplateException(String message, Throwable cause) {
    super(message, cause);
  }

  public TemplateException(Throwable cause) {
    super(cause);
  }
}
