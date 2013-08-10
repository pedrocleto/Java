package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityConnectionDao;
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
public interface ActivityConnection extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ActivityConnectionDao attrs) throws RemoteException;

  public ActivityConnectionDao getData() throws RemoteException;

  public void insert(ActivityConnectionDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ActivityConnectionDao attrs) throws RemoteException, UserException;

  public void delete(ActivityConnectionDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void deleteForActivity(String activityID) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ActivityConnectionDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ActivityConnectionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ActivityConnectionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ActivityConnectionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ActivityConnectionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
