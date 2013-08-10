package pt.inescporto.siasoft.go.pga.ejb.session;

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
public interface ObjectiveActionsHome extends EJBHome {
  ObjectiveActions create() throws CreateException, RemoteException;

  ObjectiveActions create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
