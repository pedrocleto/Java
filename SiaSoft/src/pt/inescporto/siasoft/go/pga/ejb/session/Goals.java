package pt.inescporto.siasoft.go.pga.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalDao;
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
public interface Goals extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GoalDao attrs) throws RemoteException;

  public GoalDao getData() throws RemoteException;

  public void insert(GoalDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(GoalDao attrs) throws RemoteException, UserException;

  public void delete(GoalDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(GoalDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(GoalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GoalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(GoalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(GoalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
