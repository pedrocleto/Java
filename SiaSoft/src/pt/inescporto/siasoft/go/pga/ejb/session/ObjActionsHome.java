package pt.inescporto.siasoft.go.pga.ejb.session;

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
public interface ObjActionsHome extends EJBHome{

 ObjActions create() throws CreateException, RemoteException;

}
