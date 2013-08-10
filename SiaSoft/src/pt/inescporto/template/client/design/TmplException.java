package pt.inescporto.template.client.design;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplException extends Exception {
  Throwable detail = null;
  int errorCode = -1;

  public TmplException() {
    super();
  }

  public TmplException(String message) {
    super(message);
  }

  public TmplException(int errorCode) {
    super();
    this.errorCode = errorCode;
  }

  public TmplException(int errorCode, Throwable detail) {
    super();
    this.errorCode = errorCode;
    this.detail = detail;
  }

  public TmplException(String message, int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public void setDetail(Throwable detail) {
    this.detail = detail;
  }

  public Throwable getDetail() {
    return detail;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
