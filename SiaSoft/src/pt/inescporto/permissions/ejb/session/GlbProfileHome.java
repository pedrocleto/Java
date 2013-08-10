package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;

public interface GlbProfileHome extends EJBHome {
  GlbProfile create() throws CreateException, RemoteException;
}
