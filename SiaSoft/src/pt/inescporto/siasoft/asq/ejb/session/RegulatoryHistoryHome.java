package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBHome;
import java.util.Vector;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

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
public interface RegulatoryHistoryHome extends EJBHome {
  RegulatoryHistory create() throws CreateException, RemoteException;

  RegulatoryHistory create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
