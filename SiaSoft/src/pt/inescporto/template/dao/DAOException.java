package pt.inescporto.template.dao;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class DAOException extends Exception {
  public DAOException() {
    super();
  }

  public DAOException(Exception ex) {
    super(ex);
  }
}
