package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

public interface UserHome extends EJBHome {
  User create() throws CreateException, RemoteException;
  User create( String linkCondition, Vector binds ) throws CreateException, RemoteException;
}
