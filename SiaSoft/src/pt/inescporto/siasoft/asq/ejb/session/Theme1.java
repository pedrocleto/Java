package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.dao.Theme1Dao;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public interface Theme1 extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(Theme1Dao attrs) throws RemoteException;

  public Theme1Dao getData() throws RemoteException;

  public void insert(Theme1Dao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(Theme1Dao attrs) throws RemoteException, UserException;

  public void delete(Theme1Dao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(Theme1Dao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(Theme1Dao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(Theme1Dao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(Theme1Dao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(Theme1Dao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
