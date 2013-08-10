package pt.inescporto.siasoft.comun.ejb.session;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import java.util.Vector;

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
public interface ProfileBuilder extends EJBObject {
  public Vector getProfileTree() throws RemoteException;
}
