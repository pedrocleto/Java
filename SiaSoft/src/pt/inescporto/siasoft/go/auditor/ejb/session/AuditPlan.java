package pt.inescporto.siasoft.go.auditor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanDao;
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
public interface AuditPlan extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(AuditPlanDao attrs) throws RemoteException;

  public AuditPlanDao getData() throws RemoteException;

  public void insert(AuditPlanDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(AuditPlanDao attrs) throws RemoteException, UserException;

  public void delete(AuditPlanDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(AuditPlanDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(AuditPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(AuditPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(AuditPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(AuditPlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
