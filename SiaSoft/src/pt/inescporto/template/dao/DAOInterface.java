package pt.inescporto.template.dao;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public interface DAOInterface extends Serializable {
  public void create(Object row) throws DAOException, DupKeyException;
  public int update(Object row) throws DAOException;
  public int remove(Object row) throws DAOException;
  public void findByPrimaryKey(Object row);
  public Collection find(Object row) throws DAOException, RowNotFoundException;
  public void findPrev(Object row) throws DAOException, RowNotFoundException;
  public void findNext(Object row) throws DAOException, RowNotFoundException;
}
