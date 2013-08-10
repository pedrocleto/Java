package pt.inescporto.siasoft.comun.ejb.session;

import java.util.Vector;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.CreateException;

/**
 * <p>Title: Benchmarking</p>
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
public interface EnterpriseHome extends EJBHome {
  Enterprise create() throws CreateException, RemoteException;

  Enterprise create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
