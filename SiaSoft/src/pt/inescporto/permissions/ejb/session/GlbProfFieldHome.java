package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;

public interface GlbProfFieldHome extends EJBHome {
  GlbProfField create() throws CreateException, RemoteException;
}
