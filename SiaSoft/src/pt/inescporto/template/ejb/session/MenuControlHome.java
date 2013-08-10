package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;

public interface MenuControlHome extends EJBHome {
  MenuControl create() throws CreateException, RemoteException;
}
