package pt.inescporto.template.client.util;

import pt.inescporto.template.client.design.TmplGetter;

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
public class DataSourceException extends Exception {
  public static int NOT_DEFINED = -1;
  public static int SUCCESS = 0;
  public static int FIRST_RECORD = 1;
  public static int LAST_RECORD = 2;
  public static int NOT_FOUND = 3;

  private int statusCode = -1;
  private Throwable exception = null;
  private TmplGetter component = null;

  public DataSourceException() {
    super();
  }

  public DataSourceException(int statusCode) {
    super();
    this.statusCode = statusCode;
  }

  public DataSourceException(int statusCode, Throwable exception) {
    super();
    this.statusCode = statusCode;
    this.exception = exception;
  }

  public DataSourceException(String message) {
    super(message);
  }

  public DataSourceException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataSourceException(Throwable cause) {
    super(cause);
  }

  public DataSourceException(TmplGetter component) {
    super();
    this.component = component;
  }

  public Throwable getException() {
    return exception;
  }

  public TmplGetter getFailedComponent() {
    return component;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
