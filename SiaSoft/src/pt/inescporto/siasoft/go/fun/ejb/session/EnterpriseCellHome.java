package pt.inescporto.siasoft.go.fun.ejb.session;

import javax.ejb.EJBHome;
import pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell;
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
public interface EnterpriseCellHome extends EJBHome {
  EnterpriseCell create() throws CreateException, RemoteException;

  EnterpriseCell create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
