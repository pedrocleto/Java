package pt.inescporto.siasoft.go.fun.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.go.fun.ejb.dao.EnvironmentUserDao;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;

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
public interface EnvironmentUser extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvironmentUserDao attrs) throws RemoteException;

  public EnvironmentUserDao getData() throws RemoteException;

  public void insert(EnvironmentUserDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvironmentUserDao attrs) throws RemoteException, UserException;

  public void delete(EnvironmentUserDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvironmentUserDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvironmentUserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvironmentUserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvironmentUserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvironmentUserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
