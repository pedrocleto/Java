package pt.inescporto.siasoft.go.monitor.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanDao;
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
public interface MonitorPlan extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(MonitorPlanDao attrs) throws RemoteException;

  public MonitorPlanDao getData() throws RemoteException;

  public void insert(MonitorPlanDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(MonitorPlanDao attrs) throws RemoteException, UserException;

  public void delete(MonitorPlanDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(MonitorPlanDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(MonitorPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(MonitorPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(MonitorPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(MonitorPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
