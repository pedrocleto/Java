package pt.inescporto.template.ejb.util;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface KeyHome extends EJBHome {
  Key create() throws RemoteException, CreateException;
}
