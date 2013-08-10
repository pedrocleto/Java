package pt.inescporto.siasoft.go.coe.ejb.session;

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
public interface EmergencyPlanCoursesHome extends EJBHome {
  EmergencyPlanCourses create() throws CreateException, RemoteException;

  EmergencyPlanCourses create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
