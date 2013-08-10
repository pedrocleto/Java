package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;

public interface SessionControlHome extends EJBHome {
  SessionControl create() throws CreateException, RemoteException;
}
