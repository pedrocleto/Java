package pt.inescporto.siasoft.go.pga.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.pga.ejb.dao.ObjectiveHasEADao;
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
public interface ObjectiveHasEA extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ObjectiveHasEADao attrs) throws RemoteException;

  public ObjectiveHasEADao getData() throws RemoteException;

  public void insert(ObjectiveHasEADao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ObjectiveHasEADao attrs) throws RemoteException, UserException;

  public void delete(ObjectiveHasEADao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ObjectiveHasEADao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ObjectiveHasEADao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ObjectiveHasEADao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ObjectiveHasEADao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ObjectiveHasEADao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
