package pt.inescporto.siasoft.proc.ejb.session;

import java.util.Vector;
import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.proc.ejb.session.ActivityType;

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
public interface ActivityTypeHome extends EJBHome {
  ActivityType create() throws CreateException, RemoteException;

  ActivityType create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
