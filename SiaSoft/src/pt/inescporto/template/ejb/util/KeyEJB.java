package pt.inescporto.template.ejb.util;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import pt.inescporto.template.dao.UserException;

/**
* Stateless session bean.
*/
public class KeyEJB implements SessionBean {
  private String logicalDBName = "java:comp/env/jdbc/siasoft";
  private SessionContext ctx = null;
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
  private String getNext( String keyValue ) {
    String sYear = keyValue.substring( 0, 4 );
    String sValue = keyValue.substring( 4 );
    int iYear = Integer.parseInt( sYear );
    long lValue = Long.parseLong( sValue );
    String nextValue = "";
    Calendar rightNow = Calendar.getInstance();

    int curYear = rightNow.get(Calendar.YEAR);

    if( curYear != iYear ) {
      iYear = curYear;
      lValue = 1;
    }
    else
      lValue++;

    sValue = Long.toString( lValue, 10 );
    sYear = Integer.toString( iYear );

    nextValue = sYear;

    while( nextValue.length() + sValue.length() < keyValue.length() )
      nextValue = nextValue + "0";

    nextValue = nextValue + sValue;

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
  public String GenerateKey( String keyTable, String keyColumn ) throws UserException {
    Connection con = null;
    PreparedStatement ps = null;
    String nextValue = null;

    if( verboseMode )
      System.out.println("GenerateKey(" + keyTable + ", " + keyColumn + ") called.");

    try {
      con = getConnection();
      ps = con.prepareStatement("SELECT " + keyColumn + " FROM " + keyTable);
      ps.executeQuery();

      ResultSet rs = ps.getResultSet();
      rs.next();
      nextValue = getNext(rs.getString(keyColumn));

      ps = con.prepareStatement("UPDATE " + keyTable + " SET " + keyColumn + " = ?");
      ps.setString(1, nextValue);
      ps.executeUpdate();
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
        if (ps != null) ps.close();
        if (con != null) con.close();
      }
      catch (Exception e) {
      };
    }
    return nextValue;
  }
}
