package pt.inescporto.siasoft.proc.ejb.session;

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
public interface PartHome extends EJBHome {
  Part create() throws CreateException, RemoteException;

  Part create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
