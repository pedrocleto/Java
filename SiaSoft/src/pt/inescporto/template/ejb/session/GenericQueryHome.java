package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;

public interface GenericQueryHome extends EJBHome {
  GenericQuery create() throws CreateException, RemoteException;
}
