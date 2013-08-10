package pt.inescporto.template.ejb.util;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface LongKeyGenHome extends EJBHome {
  LongKeyGen create() throws RemoteException, CreateException;
}
