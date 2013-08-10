package pt.inescporto.siasoft.go.auditor.ejb.session;

import java.util.Collection;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import javax.ejb.EJBObject;

import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionTypeDao;
import pt.inescporto.template.dao.UserException;
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
public interface AuditActionType extends EJBObject {
 public Collection<AuditActionTypeDao> listAll(String enterpriseID, Timestamp startDate, Timestamp endDate, String state, String auditPlanID, Timestamp auditDate, String auditActionID, String type, String userID) throws RemoteException, UserException;
}
