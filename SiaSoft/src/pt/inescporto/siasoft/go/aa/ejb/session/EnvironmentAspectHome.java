package pt.inescporto.siasoft.go.aa.ejb.session;

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
 * @author jap
 * @version 0.1
 */
public interface EnvironmentAspectHome extends EJBHome {
  EnvironmentAspect create() throws CreateException, RemoteException;

  EnvironmentAspect create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
