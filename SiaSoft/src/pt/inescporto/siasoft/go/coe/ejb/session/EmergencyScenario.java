package pt.inescporto.siasoft.go.coe.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencyScenarioDao;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
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
public interface EmergencyScenario extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EmergencyScenarioDao attrs) throws RemoteException;

  public EmergencyScenarioDao getData() throws RemoteException;

  public void insert(EmergencyScenarioDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EmergencyScenarioDao attrs) throws RemoteException, UserException;

  public void delete(EmergencyScenarioDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EmergencyScenarioDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EmergencyScenarioDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EmergencyScenarioDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EmergencyScenarioDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EmergencyScenarioDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
