package pt.inescporto.siasoft.go.auditor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditDao;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import java.sql.Timestamp;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditStatedDao;

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
public interface Audit extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(AuditDao attrs) throws RemoteException;

  public AuditDao getData() throws RemoteException;

  public void insert(AuditDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(AuditDao attrs) throws RemoteException, UserException;

  public void delete(AuditDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(AuditDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(AuditDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(AuditDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(AuditDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(AuditDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public void copyActionsFromAuditPlan(String auditPlanID, Timestamp auditDate) throws RemoteException, UserException;

  public Collection<AuditStatedDao> getFilteredAudit(String enterpriseID, String enterpriseCellID, String activityID, String auditPlanID, String state, Timestamp startDate, Timestamp endDate, String auditType) throws RemoteException, UserException;
}
