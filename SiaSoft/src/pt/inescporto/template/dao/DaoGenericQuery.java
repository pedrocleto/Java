package pt.inescporto.template.dao;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import pt.inescporto.template.elements.*;

public class DaoGenericQuery implements java.io.Serializable {
  private int type = -1;
  protected String driver;
  protected String url;
  protected Connection dbConnection;
  protected PreparedStatement ps;
  protected ResultSet rs;

  // initial attributs and JDBC connection
  public DaoGenericQuery(String driver, String url) {
    this.driver = driver;
    this.url = url;

    if (driver == null || (driver == "" && driver.length() == 0))
      type = 1;
    else
      type = 2;
  }

  public DaoGenericQuery(Connection con) {
    this.dbConnection = con;

    type = 3;
  }

  protected void bindQuery(Vector binds) throws SQLException {
    int count = 1;
    Iterator it = binds.iterator();

    while (it.hasNext()) {
      Object bindVar = it.next();
      if (bindVar instanceof pt.inescporto.template.elements.TplString) {
	ps.setString(count++, ((TplString)bindVar).getValue());
      }
      if (bindVar instanceof pt.inescporto.template.elements.TplBoolean) {
	if (((TplBoolean)bindVar).getValue() != null)
	  ps.setBoolean(count++, ((TplBoolean)bindVar).getValue().booleanValue());
	else
	  ps.setNull(count++, java.sql.Types.BIT);
      }
      if (bindVar instanceof pt.inescporto.template.elements.TplInt) {
	if (((TplInt)bindVar).getValue() != null)
	  ps.setInt(count++, ((TplInt)bindVar).getValue().intValue());
	else
	  ps.setNull(count++, java.sql.Types.INTEGER);
      }
      if (bindVar instanceof pt.inescporto.template.elements.TplLong) {
	if (((TplLong)bindVar).getValue() != null)
	  ps.setLong(count++, ((TplLong)bindVar).getValue().longValue());
	else
	  ps.setNull(count++, java.sql.Types.INTEGER);
      }
      if (bindVar instanceof pt.inescporto.template.elements.TplFloat) {
	if (((TplFloat)bindVar).getValue() != null)
	  ps.setFloat(count++, ((TplFloat)bindVar).getValue().floatValue());
	else
	  ps.setNull(count++, java.sql.Types.FLOAT);
      }
      if (bindVar instanceof pt.inescporto.template.elements.TplTimestamp) {
	ps.setTimestamp(count++, ((TplTimestamp)bindVar).getValue());
      }
      if (bindVar instanceof pt.inescporto.template.elements.TplObjRef) {
	if (((TplObjRef)bindVar).getValue() != null) {
	  ByteArrayOutputStream b = new ByteArrayOutputStream();
	  try {
	    ObjectOutputStream out = new ObjectOutputStream(b);
	    out.writeObject(((TplObjRef)bindVar).getValue());
	  }
	  catch (IOException ex) {
	    ex.printStackTrace();
	  }
	  ps.setBytes(count++, b.toByteArray());
	}
	else
	  ps.setNull(count++, java.sql.Types.LONGVARBINARY);
      }
    }
  }

  protected Vector getResponse() throws SQLException {
    ResultSetMetaData rsMeta = rs.getMetaData();
    Vector resp = new Vector();
    while (rs.next()) {
      Vector rowData = new Vector();
      for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
	switch (rsMeta.getColumnType(i)) {
	  case Types.VARCHAR:
	  case Types.CHAR:
	    TplString colS = new TplString(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
	    colS.setValue(rs.getString(i));
	    rowData.add(colS);
	    break;
	  case Types.BIT:
	    TplBoolean colB = new TplBoolean(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
            Boolean bVal = new Boolean(rs.getBoolean(i));
	    colB.setValue(rs.wasNull() ? null : bVal);
	    rowData.add(colB);
	    break;
	  case Types.INTEGER:
	  case Types.SMALLINT:
	  case Types.NUMERIC:
	    if (rsMeta.getScale(i) == 0 && rs.getLong(i) <= Integer.MAX_VALUE && rs.getLong(i) >= Integer.MIN_VALUE && rsMeta.getPrecision(i) != 0) {
	      TplInt colI = new TplInt(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
	      colI.setValue(new Integer(rs.getInt(i)));
	      rowData.add(colI);
	      break;
	    }
	  case Types.BIGINT:
	    if (rsMeta.getScale(i) == 0 && rsMeta.getPrecision(i) != 0) {
	      TplLong colL = new TplLong(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
	      colL.setValue(new Long(rs.getLong(i)));
	      rowData.add(colL);
	      break;
	    }
	  case Types.FLOAT:
	  case Types.DOUBLE:
	  case Types.DECIMAL:
	    TplFloat colF = new TplFloat(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
	    colF.setValue(new Float(rs.getFloat(i)));
	    rowData.add(colF);
	    break;
	  case Types.DATE:
	  case Types.TIME:
	  case Types.TIMESTAMP:
	    TplTimestamp colT = new TplTimestamp(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
	    colT.setValue(rs.getTimestamp(i));
	    rowData.add(colT);
	    break;
	  case Types.VARBINARY:
	  case Types.LONGVARBINARY:
	    Object obj = null;
	    if (rs.getBytes(i) != null) {
	      ByteArrayInputStream bIn = new ByteArrayInputStream(rs.getBytes(i));
	      try {
		ObjectInputStream ois = new ObjectInputStream(bIn);
		obj = ois.readObject();
	      }
	      catch (IOException ex) {
		ex.printStackTrace();
	      }
	      catch (ClassNotFoundException ex) {
		ex.printStackTrace();
	      }
	    }
	    TplObjRef colO = new TplObjRef(rsMeta.getColumnName(i), TmplKeyTypes.NOKEY, (rsMeta.isNullable(i) & ResultSetMetaData.columnNoNulls) > 0);
	    colO.setValue(obj);
	    rowData.add(colO);
	    break;
	  default:
	    System.out.println("DaoGenericQuery Type " + rsMeta.getColumnType(i) + " not included...");
	    break;
	}
      }
      resp.add(rowData);
    }
    this.close();
    return resp;
  }

  public Vector doQuery(Vector binds) throws NamingException, SQLException {
    // bind
    this.bindQuery(binds);

    // do
    try {
      rs = ps.executeQuery();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }

    //construct response and ret
    return this.getResponse();
  }

  public int doExecuteQuery(Vector binds) throws NamingException, SQLException {
    // bind
    this.bindQuery(binds);

    // execute
    return ps.executeUpdate();
  }

  private void freeResources() {
    try {
      if (rs != null) {
	rs.close();
	rs = null;
      }
    }
    catch (Exception e) {
      System.err.println("Exception when closing ResultSet: " + e.getMessage());
    }
    try {
      if (ps != null) {
	ps.close();
	ps = null;
      }
    }
    catch (Exception e) {
      System.err.println("Exception when closing PreparedStatement: " + e.getMessage());
    }
    try {
      if (url != null && dbConnection != null && !dbConnection.isClosed()) {
	dbConnection.close();
	dbConnection = null;
      }
    }
    catch (Exception e) {
      System.err.println("Exception when closing DB Connection: " + e.getMessage());
    }
  }

  public void init(String queryStmt) throws SQLException, NamingException {

    freeResources();

    switch (type) {
      case 1:
	try {
	  InitialContext ic = new InitialContext();
	  DataSource ds = (DataSource)ic.lookup(url);
	  dbConnection = ds.getConnection();
	}
	catch (NamingException ne) {
	  throw new NamingException(ne.getMessage());
	}
	break
	    ;
      case 2:
	try {
	  Class.forName(driver);
	  dbConnection = DriverManager.getConnection(url);
	}
	catch (Exception ex) {
	  throw new SQLException(ex.getMessage());
	}
	break
	    ;
      case 3:
	break;
      default:
	break;
    }

    ps = dbConnection.prepareStatement(queryStmt);
  }

  // For each init(...) there should be a close() call
  public void close() {
    freeResources();
  }

  protected void finalize() throws Throwable {
    try {
      freeResources();
    }
    finally {
      super.finalize();
    }
  }
}
