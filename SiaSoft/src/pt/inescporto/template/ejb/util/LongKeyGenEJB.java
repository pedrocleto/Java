package pt.inescporto.template.ejb.util;

import javax.ejb.*;
import java.rmi.RemoteException;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import pt.inescporto.template.dao.UserException;

/**
* Stateless session bean.
*/
public class LongKeyGenEJB implements SessionBean {
  private SessionContext ctx = null;
  private String logicalDBName = "java:comp/env/jdbc/siasoft";
  private boolean verboseMode = false;

  /**
   * Creates connection to database
   */
  private Connection getConnection() throws SQLException, NamingException {
    Connection connection =  null;

    try {
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(logicalDBName);
      connection =  ds.getConnection();
    }
    catch( NamingException ne ) {
      throw new NamingException( "Can´t connect to DB!" );
    }
    return connection;
  }

  /**
   * Computes next value
   */
  private Long getNext( long keyValue ) {
    Long nextValue;

    nextValue = new Long(keyValue + 1);

    return( nextValue );
  }

  //
  // EJB-required methods
  //
  public void ejbCreate() throws CreateException, RemoteException {
  }

  public void ejbRemove() throws RemoteException {
  }

  public void ejbActivate() throws RemoteException {
  }

  public void ejbPassivate() throws RemoteException {
  }

  public void setSessionContext(SessionContext ctx) throws RemoteException {
    this.ctx = ctx;
  }

  //
  // Business methods
  //
  public Long GenerateKey(String keyTable, String keyColumn) throws RemoteException, UserException {
    Connection con = null;
    PreparedStatement ps = null;
    Long nextValue;

    if( verboseMode )
      System.out.println("GenerateKey(" + keyTable + ", " + keyColumn + ") called.");

    try {
      con = getConnection();
      ps  = con.prepareStatement("SELECT " + keyColumn + " FROM " + keyTable + " ORDER BY " + keyColumn + " DESC");
      ps.executeQuery();

      ResultSet rs = ps.getResultSet();
      boolean hasRecord = rs.next();
      if (hasRecord)
        nextValue = getNext( rs.getLong(keyColumn) );
      else
        nextValue = new Long(1);
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
    finally {
      try {
        if( ps != null ) ps.close();
        if( con != null )con.close();
      }
      catch (Exception e) {};
    }
    return nextValue;
  }
}
