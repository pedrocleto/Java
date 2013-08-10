package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.comun.ejb.dao.UnitsDao;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

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
public interface Units extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(UnitsDao attrs) throws RemoteException;

  public UnitsDao getData() throws RemoteException;

  public void insert(UnitsDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(UnitsDao attrs) throws RemoteException, UserException;

  public void delete(UnitsDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(UnitsDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(UnitsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(UnitsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(UnitsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(UnitsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
