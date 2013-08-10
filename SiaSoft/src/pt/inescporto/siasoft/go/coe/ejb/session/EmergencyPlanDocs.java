package pt.inescporto.siasoft.go.coe.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencyPlanDocsDao;
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
public interface EmergencyPlanDocs extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EmergencyPlanDocsDao attrs) throws RemoteException;

  public EmergencyPlanDocsDao getData() throws RemoteException;

  public void insert(EmergencyPlanDocsDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EmergencyPlanDocsDao attrs) throws RemoteException, UserException;

  public void delete(EmergencyPlanDocsDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EmergencyPlanDocsDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EmergencyPlanDocsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EmergencyPlanDocsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EmergencyPlanDocsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EmergencyPlanDocsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
