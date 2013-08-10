package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityTypeDao;
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
public interface ActivityType extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ActivityTypeDao attrs) throws RemoteException;

  public ActivityTypeDao getData() throws RemoteException;

  public void insert(ActivityTypeDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ActivityTypeDao attrs) throws RemoteException, UserException;

  public void delete(ActivityTypeDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ActivityTypeDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ActivityTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ActivityTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ActivityTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ActivityTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
