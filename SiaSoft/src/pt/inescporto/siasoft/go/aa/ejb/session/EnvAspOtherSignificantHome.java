package pt.inescporto.siasoft.go.aa.ejb.session;

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
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public interface EnvAspOtherSignificantHome extends EJBHome {
  EnvAspOtherSignificant create() throws CreateException, RemoteException;

  EnvAspOtherSignificant create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
