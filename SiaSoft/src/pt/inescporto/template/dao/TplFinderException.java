package pt.inescporto.template.dao;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TplFinderException extends java.rmi.RemoteException {
  public TplFinderException() {
    super();
  }

  public TplFinderException(String msg) {
    super(msg);
  }
}
