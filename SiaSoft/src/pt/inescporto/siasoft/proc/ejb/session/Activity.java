package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
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
 * @author jap
 * @version 0.1
 */
public interface Activity extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ActivityDao attrs) throws RemoteException;

  public ActivityDao getData() throws RemoteException;

  public void insert(ActivityDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ActivityDao attrs) throws RemoteException, UserException;

  public void delete(Vector<String> actlistID) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ActivityDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ActivityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ActivityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ActivityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ActivityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public Collection<ActivityDao> getActivityTree(String startActivityNode) throws RemoteException, RowNotFoundException, UserException;
}
