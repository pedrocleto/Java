package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasPartDao;
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
 * @author not attributable
 * @version 0.1
 */
public interface ActivityHasPart extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ActivityHasPartDao attrs) throws RemoteException;

  public ActivityHasPartDao getData() throws RemoteException;

  public void insert(ActivityHasPartDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ActivityHasPartDao attrs) throws RemoteException, UserException;

  public void delete(ActivityHasPartDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ActivityHasPartDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ActivityHasPartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ActivityHasPartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ActivityHasPartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ActivityHasPartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public Collection<ActivityHasPartDao> getPartTree(String activityID, String type) throws RemoteException, UserException, RowNotFoundException;
}
