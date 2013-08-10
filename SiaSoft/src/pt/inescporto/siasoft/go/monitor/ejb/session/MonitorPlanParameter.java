package pt.inescporto.siasoft.go.monitor.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanParameterDao;
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
public interface MonitorPlanParameter extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(MonitorPlanParameterDao attrs) throws RemoteException;

  public MonitorPlanParameterDao getData() throws RemoteException;

  public void insert(MonitorPlanParameterDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(MonitorPlanParameterDao attrs) throws RemoteException, UserException;

  public void delete(MonitorPlanParameterDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(MonitorPlanParameterDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(MonitorPlanParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(MonitorPlanParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(MonitorPlanParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(MonitorPlanParameterDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
