package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface GlbFieldPermHome extends EJBHome {
  GlbFieldPerm create() throws CreateException, RemoteException;
}
