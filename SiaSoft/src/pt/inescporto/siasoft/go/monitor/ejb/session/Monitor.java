package pt.inescporto.siasoft.go.monitor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorDao;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import java.sql.Timestamp;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorStatedDao;

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
public interface Monitor extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(MonitorDao attrs) throws RemoteException;

  public MonitorDao getData() throws RemoteException;

  public void insert(MonitorDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(MonitorDao attrs) throws RemoteException, UserException;

  public void delete(MonitorDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(MonitorDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(MonitorDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(MonitorDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(MonitorDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(MonitorDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public void copyParametersFromMonitorPlan(String monitorPlanID, Timestamp monitorDate) throws RemoteException, UserException;

  public Collection<MonitorStatedDao> getFilteredMonitor(String enterpriseID, Timestamp startDate, Timestamp endDate, String state, String userID, String monPlanID, String envAspID, String activityID) throws RemoteException, UserException;
}
