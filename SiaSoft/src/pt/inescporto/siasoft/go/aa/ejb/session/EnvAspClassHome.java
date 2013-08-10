package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBHome;
import java.util.Vector;
import javax.ejb.CreateException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvAspClass;

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
public interface EnvAspClassHome extends EJBHome {
  EnvAspClass create() throws CreateException, RemoteException;

  EnvAspClass create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
