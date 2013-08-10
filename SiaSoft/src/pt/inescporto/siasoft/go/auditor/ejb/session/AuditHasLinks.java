package pt.inescporto.siasoft.go.auditor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditHasLinksDao;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public interface AuditHasLinks  extends EJBObject {
  public AuditHasLinksDao getLinks(String auditPlanID, String enterpriseID) throws RemoteException, UserException;
}
