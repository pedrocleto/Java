package pt.inescporto.siasoft.go.monitor.ejb.session;

import pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan;
import javax.ejb.EJBHome;
import java.util.Vector;
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
public interface MonitorPlanHome extends EJBHome {
  MonitorPlan create() throws CreateException, RemoteException;

  MonitorPlan create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
