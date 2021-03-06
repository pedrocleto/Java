package pt.inescporto.siasoft.go.auditor.ejb.session;

import javax.ejb.EJBHome;
import java.util.Vector;
import pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

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
public interface AuditPlanHome extends EJBHome {
  AuditPlan create() throws CreateException, RemoteException;

  AuditPlan create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
