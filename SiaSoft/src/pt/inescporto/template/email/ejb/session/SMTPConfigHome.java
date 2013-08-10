package pt.inescporto.template.email.ejb.session;

import javax.ejb.EJBHome;
import pt.inescporto.template.email.ejb.session.SMTPConfig;
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
public interface SMTPConfigHome extends EJBHome {
  SMTPConfig create() throws CreateException, RemoteException;

  SMTPConfig create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
