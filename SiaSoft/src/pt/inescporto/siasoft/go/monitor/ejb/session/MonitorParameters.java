package pt.inescporto.siasoft.go.monitor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorParameterDao;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
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
public interface MonitorParameters extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(MonitorParameterDao attrs) throws RemoteException;

  public MonitorParameterDao getData() throws RemoteException;

  public void insert(MonitorParameterDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(MonitorParameterDao attrs) throws RemoteException, UserException;

  public void delete(MonitorParameterDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(MonitorParameterDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(MonitorParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(MonitorParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(MonitorParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(MonitorParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
