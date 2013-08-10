package pt.inescporto.siasoft.go.coe.ejb.session;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public interface EmergencySituationHasLinksHome extends EJBHome {
  EmergencySituationHasLinks create() throws CreateException, RemoteException;
}
