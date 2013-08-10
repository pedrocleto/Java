package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
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
public interface EnvironmentAspect extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvironmentAspectDao attrs) throws RemoteException;

  public EnvironmentAspectDao getData() throws RemoteException;

  public void insert(EnvironmentAspectDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvironmentAspectDao attrs) throws RemoteException, UserException;

  public void delete(EnvironmentAspectDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvironmentAspectDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvironmentAspectDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvironmentAspectDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvironmentAspectDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvironmentAspectDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public Collection<EnvironmentAspectDao> getEnvAspTree(String activityID, String classID) throws RemoteException, UserException, RowNotFoundException;
}
