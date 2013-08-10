package pt.inescporto.siasoft.asq.ejb.session;

import java.util.Vector;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public interface SourceHome extends EJBHome {
  Source create() throws CreateException, RemoteException;

  Source create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
