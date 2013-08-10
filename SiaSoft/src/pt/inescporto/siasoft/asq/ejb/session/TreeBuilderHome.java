package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;

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
public interface TreeBuilderHome extends EJBHome {
  TreeBuilder create() throws CreateException, RemoteException;
}
