package pt.inescporto.siasoft.form.ejb.session;

import java.util.Vector;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.CreateException;

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
public interface CoursePlanHome extends EJBHome {
  CoursePlan create() throws CreateException, RemoteException;

  CoursePlan create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
