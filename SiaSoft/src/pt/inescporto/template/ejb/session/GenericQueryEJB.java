package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

import java.sql.*;
import javax.sql.*;

import pt.inescporto.template.dao.*;
import javax.naming.*;

public class GenericQueryEJB implements SessionBean {
  protected SessionContext ctx;
  protected DaoGenericQuery query = new DaoGenericQuery("", "java:comp/env/jdbc/siasoft");

  public GenericQueryEJB() {
    System.out.println( "New GenericQuery Session Bean created by EJB container ..." );
  }

  /**
   * EJB Required methods
   */

  public void ejbActivate() throws RemoteException {}

  public void ejbPassivate() throws RemoteException {}

  public void ejbRemove() throws RemoteException {
    query.close();
    query = null;
  }

  public void ejbCreate() {}

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  /**
   * Public methods
   */

  public Vector doQuery( String queryStmt, Vector binds ) throws RemoteException {
    try {
      query.init( queryStmt );
      Vector result = null;
      try {
	result = query.doQuery(binds);
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
      catch (NamingException ex1) {
        ex1.printStackTrace();
      }
      query.close();
      return result;
    }
    catch( Exception ex ) {
      ex.printStackTrace();
      return null;
    }
  }

  public int doExecuteQuery( String queryStmt, Vector binds ) throws RemoteException {
    int count = -1;
    try {
      query.init( queryStmt );
      count = query.doExecuteQuery( binds );
      query.close();
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }
    return count;
  }
}
