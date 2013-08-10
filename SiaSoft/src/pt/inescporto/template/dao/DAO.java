package pt.inescporto.template.dao;

import java.sql.*;
import java.lang.reflect.*;
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
import pt.inescporto.template.utils.*;

public class DAO implements java.io.Serializable {
  private int type = -1;
  protected Object attrs;
  protected String table;
  protected String driver;
  protected String url;
  protected transient Connection dbConnection;
  protected transient PreparedStatement ps;
  protected transient ResultSet rs;
  private int lnkCount;
  private String linkCondition = null;
  private Vector binds = null;
  private String selectStmt;
  private String selectAllStmt;
  private String selectAllStmtOrderBy;
  private String insertStmt;
  private String updateStmt;
  private String deleteStmt;
  private String findStmt;
  private String forwardStmt[];
  private String backwardStmt[];
  private String countStmt = null;
  private String selPart = null;

  // link tables
  private Vector<LinkTableNode> linkTabels = null;
  private String inLinkStatement = "";

  // initial attributs and JDBC connection
  public DAO(Object attrs, String driver, String url, String table) {
    this.attrs = attrs;
    this.driver = driver;
    this.url = url;
    this.table = table;
    this.linkCondition = null;

    if (driver == null && driver == "" || driver.length() == 0)
      type = 1;
    else
      type = 2;

    generateSQL();
  }

  // initial attributs and JDBC connection
  public DAO(Object attrs, String driver, String url, String table, String linkCondition, Vector binds) {
    this.attrs = attrs;
    this.driver = driver;
    this.url = url;
    this.table = table;
    if (linkCondition != null || linkCondition.length() != 0) {
      this.linkCondition = linkCondition;
      this.binds = binds;
    }

    if (driver == null && driver == "" || driver.length() == 0)
      type = 1;
    else
      type = 2;

    generateSQL();
  }

  public DAO(Object attrs, Connection dbConnection, String table) {
    this.attrs = attrs;
    this.dbConnection = dbConnection;
    this.table = table;
    this.linkCondition = null;

    type = 3;

    generateSQL();
  }

  public DAO(Object attrs, Connection dbConnection, String table, String linkCondition, Vector binds) {
    this.attrs = attrs;
    this.dbConnection = dbConnection;
    this.table = table;
    if (linkCondition != null || linkCondition.length() != 0) {
      this.linkCondition = linkCondition;
      this.binds = binds;
    }

    type = 3;

    generateSQL();
  }

  /**
   * Add's a link table to this DAO. Duplicates ignored!
   *
   * @param linkTable LinkTableNode
   */
  public void addLinkTable(LinkTableNode linkTable) {
    if (linkTabels == null)
      linkTabels = new Vector<LinkTableNode>();
    if (!linkTabels.contains(linkTable))
      linkTabels.add(linkTable);

    generateSQL();
  }

  // generate sql statements from attribute object
  protected void generateSQL() {
    Field[] fld;
    int i, j, reqCount = 0;
    String selectPart = "";
    String updatePart = "";
    String wherePart = "";
    String insertPart = "";
    String orderByAsc = "";
    String orderByDesc = "";
    String keysPart = "";
    Vector keys = null;
    String tableLinkStatement = "";
    String whereLinkStatement = "";

    lnkCount = 0;
    keys = new Vector();
    fld = attrs.getClass().getDeclaredFields();

    // detect linked tables
    if (linkTabels != null) {
      for (LinkTableNode linkTable : linkTabels) {
        tableLinkStatement += ", " + linkTable.getLinkTableName();

        for (LinkFieldNode fieldNode : linkTable.getLinkFieldList()) {
          whereLinkStatement += " AND " + table + "." + fieldNode.getMasterFieldName() + " = " + linkTable.getLinkTableName() + "." + fieldNode.getDetailFieldName();
        }
      }
    }

    // process any field
    for (i = 0; i < fld.length; i++) {
      try {
        Object test = fld[i].get(attrs);
        // process template objects
        if (test instanceof pt.inescporto.template.elements.TplObject) {
          TplObject myObject = (TplObject)test;
          if (myObject.isPrimaryKey()) {
            wherePart += table + "." + myObject.getField() + " = ? AND ";
            keysPart += table + "." + myObject.getField() + ", ";
            keys.add(test);
            if (myObject.isLinkKey())
              lnkCount++;
          }
          else
            updatePart += myObject.getField() + " = ?, ";
          if (linkTabels == null || !myObject.isPrimaryKey())
            selectPart += table + "." + myObject.getField() + ", ";
          if (myObject.isRequired()) {
            insertPart += myObject.getField() + ", ";
            reqCount++;
          }
        }
      }
      catch (Exception e) {
        System.out.println("DAO : " + e.toString());
      }
    }

    // adjust string length
    keysPart = keysPart.substring(0, keysPart.length() - 2);
    /**@todo DISTINCT doesn't work in every database! test for group by later*/
    if (linkTabels != null)
      selectPart = keysPart + ", " + selectPart.substring(0, selectPart.length() - 2);
//    selectPart = "DISTINCT (" + keysPart + "), " + selectPart.substring(0, selectPart.length() - 2);
    else
      selectPart = selectPart.substring(0, selectPart.length() - 2);
    selPart = selectPart;
    wherePart = wherePart.substring(0, wherePart.length() - 5);
    if (!updatePart.equals(""))
      updatePart = updatePart.substring(0, updatePart.length() - 2);
    insertPart = insertPart.substring(0, insertPart.length() - 2);

    if (linkTabels != null) {
      selectStmt = "SELECT " + selectPart + " FROM " + table + tableLinkStatement + " WHERE " + wherePart + " " + whereLinkStatement;
      selectAllStmt = "SELECT " + selectPart + " FROM " + table + tableLinkStatement + " WHERE " + whereLinkStatement.substring(4, whereLinkStatement.length());
      countStmt = "SELECT COUNT(DISTINCT(" + keysPart + ")) FROM " + table + tableLinkStatement + " WHERE " + whereLinkStatement.substring(4, whereLinkStatement.length());
    }
    else {
      selectStmt = "SELECT " + selectPart + " FROM " + table + " WHERE " + wherePart;
      selectAllStmt = "SELECT " + selectPart + " FROM " + table;
      countStmt = "SELECT COUNT(*) FROM " + table;
    }
    if (!updatePart.equals(""))
      updateStmt = "UPDATE " + table + " SET " + updatePart + " WHERE " + wherePart;
    deleteStmt = "DELETE FROM " + table + " WHERE " + wherePart;
    insertStmt = "INSERT INTO " + table + " ( " + insertPart + " ) VALUES ( ";
    for (i = 0; i < reqCount; i++)
      insertStmt += " ?, ";
    insertStmt = insertStmt.substring(0, insertStmt.length() - 2) + " ) ";
    findStmt = selectStmt;
    if (linkCondition != null) {
      selectStmt += " AND " + linkCondition;
      if (selectAllStmt.indexOf("WHERE") >= 0)
        selectAllStmt += " AND (" + linkCondition + ")";
      else
        selectAllStmt += " WHERE " + linkCondition;
//      this.findStmt += " AND " + this.linkCondition;
    }

    for (i = 0; i < keys.size(); i++) {
      orderByAsc += table + "." + ((TplObject)keys.get(i)).getField() + " ASC, ";
      orderByDesc += table + "." + ((TplObject)keys.get(i)).getField() + " DESC, ";
    }
    orderByAsc = orderByAsc.substring(0, orderByAsc.length() - 2);
    orderByDesc = orderByDesc.substring(0, orderByDesc.length() - 2);

    // complete select statment's
    selectAllStmtOrderBy = selectAllStmt;
    selectAllStmt += " ORDER BY " + orderByAsc;

    forwardStmt = new String[keys.size() - lnkCount];

    if (forwardStmt.length == 0)
      forwardStmt = new String[1];

    for (i = 0; i < forwardStmt.length; i++) {
      String fwdStmt = "";
      for (j = 0; j < keys.size() - i - 1; j++)
        fwdStmt += table + "." + ((TplObject)keys.get(j)).getField() + " = ? AND ";
      fwdStmt += table + "." + ((TplObject)keys.get(j)).getField() + " > ?";
      if (linkTabels != null) {
        forwardStmt[i] = "SELECT " + selectPart + " FROM " + table + tableLinkStatement + " WHERE " + fwdStmt + " " + whereLinkStatement;
      }
      else {
        forwardStmt[i] = "SELECT " + selectPart + " FROM " + table + " WHERE " + fwdStmt;
      }
      if (linkCondition != null)
        forwardStmt[i] += " AND " + linkCondition;
      forwardStmt[i] += " ORDER BY " + orderByAsc;
    }

    backwardStmt = new String[keys.size() - lnkCount];

    if (backwardStmt.length == 0)
      backwardStmt = new String[1];

    for (i = 0; i < backwardStmt.length; i++) {
      String bckStmt = "";
      for (j = 0; j < keys.size() - i - 1; j++)
        bckStmt += table + "." + ((TplObject)keys.get(j)).getField() + " = ? AND ";
      bckStmt += table + "." + ((TplObject)keys.get(j)).getField() + " < ?";
      if (linkTabels != null) {
        backwardStmt[i] = "SELECT " + selectPart + " FROM " + table + tableLinkStatement + " WHERE " + bckStmt + " " + whereLinkStatement;
      }
      else {
        backwardStmt[i] = "SELECT " + selectPart + " FROM " + table + " WHERE " + bckStmt;
      }
      if (linkCondition != null)
        backwardStmt[i] += " AND " + linkCondition;
      backwardStmt[i] += " ORDER BY " + orderByDesc;
    }
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

  void init(String strStmt) throws SQLException, NamingException {

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

    ps = dbConnection.prepareStatement(strStmt);
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

  protected boolean next() {
    Field[] fld;
    int i;
    TplString ts = new TplString();
    TplBoolean tb = new TplBoolean();
    TplInt ti = new TplInt();
    TplLong tl = new TplLong();
    TplFloat tf = new TplFloat();
    TplTimestamp tt = new TplTimestamp();
    TplObjRef to = new TplObjRef();

    fld = attrs.getClass().getDeclaredFields();

    try {
      if (!rs.next())
        return false;

      // for all fields
      for (i = 0; i < fld.length; i++) {
        try {
          // is a string
          if (fld[i].getType().isInstance(ts)) {
            Field strField = fld[i];
            TplString myStr = (TplString)strField.get(attrs);
            myStr.setValue(rs.getString(myStr.getField()));
          }
          // is a boolean
          else if (fld[i].getType().isInstance(tb)) {
            Field intField = fld[i];
            TplBoolean myBoolean = (TplBoolean)intField.get(attrs);
            myBoolean.setValue(new Boolean(rs.getBoolean(myBoolean.getField())));
            if (rs.wasNull())
              myBoolean.resetValue();
          }
          // is a integer
          else if (fld[i].getType().isInstance(ti)) {
            Field intField = fld[i];
            TplInt myInt = (TplInt)intField.get(attrs);
            myInt.setValue(new Integer(rs.getInt(myInt.getField())));
            if (rs.wasNull())
              myInt.resetValue();
          }
          // is a long
          else if (fld[i].getType().isInstance(tl)) {
            Field longField = fld[i];
            TplLong myLong = (TplLong)longField.get(attrs);
            myLong.setValue(new Long(rs.getLong(myLong.getField())));
            if (rs.wasNull())
              myLong.resetValue();
          }
          // is a float
          else if (fld[i].getType().isInstance(tf)) {
            Field floatField = fld[i];
            TplFloat myFloat = (TplFloat)floatField.get(attrs);
            myFloat.setValue(new Float(rs.getFloat(myFloat.getField())));
            if (rs.wasNull())
              myFloat.resetValue();
          }
          // is a Timestamp
          else if (fld[i].getType().isInstance(tt)) {
            Field timeField = fld[i];
            TplTimestamp myTime = (TplTimestamp)timeField.get(attrs);
            myTime.setValue(rs.getTimestamp(myTime.getField()));
            if (rs.wasNull())
              myTime.resetValue();
          }
          // is a Object
          else if (fld[i].getType().isInstance(to)) {
            Field objField = fld[i];
            TplObjRef myObjRef = (TplObjRef)objField.get(attrs);
            Object obj = null;
//            if (rs.getBytes(myObjRef.getField()) != null) {
              try {
                ByteArrayInputStream bIn = new ByteArrayInputStream(rs.getBytes(myObjRef.getField()));
                ObjectInputStream ois = new ObjectInputStream(bIn);
                obj = ois.readObject();
                ois.close();
              }
              catch (IOException ex) {
                // value is null
              }
              catch (NullPointerException ex) {
                // value is null
              }
              catch (ClassNotFoundException ex) {
                ex.printStackTrace();
              }
//            }
            myObjRef.setValue(obj);
          }
        }
        catch (Exception e) {
          System.out.println(e.toString());
        }
      }
      return true;
    }
    catch (SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      return false;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private int setWhere(int nCount, int end) {
    Field[] fld;
    int i, j;

    fld = attrs.getClass().getDeclaredFields();

    j = 0;
    for (i = 0; i < fld.length && j < end; i++) {
      try {
        // is a string
        if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString) {
          TplString myStr = (TplString)fld[i].get(attrs);
          if (myStr.isPrimaryKey()) {
            ps.setString(nCount++, myStr.getValue());
            j++;
          }
        }
        // is a boolean
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean) {
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          if (myBoolean.isPrimaryKey()) {
            if (myBoolean.getValue() != null)
              ps.setBoolean(nCount++, myBoolean.getValue().booleanValue());
            else
              ps.setNull(nCount++, java.sql.Types.BIT);
            j++;
          }
        }
        // is a integer
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt) {
          TplInt myInt = (TplInt)fld[i].get(attrs);
          if (myInt.isPrimaryKey()) {
            if (myInt.getValue() != null)
              ps.setInt(nCount++, myInt.getValue().intValue());
            else
              ps.setNull(nCount++, java.sql.Types.INTEGER);
            j++;
          }
        }
        // is a long
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong) {
          TplLong myLong = (TplLong)fld[i].get(attrs);
          if (myLong.isPrimaryKey()) {
            if (myLong.getValue() != null)
              ps.setLong(nCount++, myLong.getValue().longValue());
            else
              ps.setNull(nCount++, java.sql.Types.INTEGER);
            j++;
          }
        }
        // is a float
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat) {
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          if (myFloat.isPrimaryKey()) {
            if (myFloat.getValue() != null)
              ps.setFloat(nCount++, myFloat.getValue().floatValue());
            else
              ps.setNull(nCount++, java.sql.Types.FLOAT);
            j++;
          }
        }
        // is a Timestamp
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp) {
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          if (myTime.isPrimaryKey()) {
            ps.setTimestamp(nCount++, myTime.getValue());
            j++;
          }
        }
        // is a Object
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplObjRef) {
          TplObjRef myObjRef = (TplObjRef)fld[i].get(attrs);
          if (myObjRef.isPrimaryKey()) {
            if (myObjRef.getValue() != null) {
              ByteArrayOutputStream b = new ByteArrayOutputStream();
              try {
                ObjectOutputStream out = new ObjectOutputStream(b);
                out.writeObject(myObjRef.getValue());
                out.flush();
                out.close();
              }
              catch (IOException ex) {
                ex.printStackTrace();
              }
              ps.setBytes(nCount++, b.toByteArray());
            }
            else
              ps.setNull(nCount++, java.sql.Types.BINARY);
            j++;
          }
        }
      }
      catch (Exception e) {
        System.out.println(e.toString());
      }
    }
    return nCount;
  }

  private void setWhereLink(int nCount) {
    if (this.linkCondition == null || this.linkCondition.length() == 0)
      return;

    if (this.binds == null)
      return;

    Iterator it = this.binds.iterator();
    try {
      while (it.hasNext()) {
        Object test = it.next();
        if (test instanceof pt.inescporto.template.elements.TplObject) {
          if (linkCondition.indexOf(((TplObject)test).getField() + " = ?") != -1) {
            if (test instanceof pt.inescporto.template.elements.TplString) {
              ps.setString(nCount++, ((TplString)test).getValue());
            }
            if (test instanceof pt.inescporto.template.elements.TplBoolean) {
              if (((TplBoolean)test).getValue() != null)
                ps.setBoolean(nCount++, ((TplBoolean)test).getValue().booleanValue());
              else
                ps.setNull(nCount++, java.sql.Types.BOOLEAN);
            }
            if (test instanceof pt.inescporto.template.elements.TplInt) {
              if (((TplInt)test).getValue() != null)
                ps.setInt(nCount++, ((TplInt)test).getValue().intValue());
              else
                ps.setNull(nCount++, java.sql.Types.INTEGER);
            }
            if (test instanceof pt.inescporto.template.elements.TplLong) {
              if (((TplLong)test).getValue() != null)
                ps.setLong(nCount++, ((TplLong)test).getValue().longValue());
              else
                ps.setNull(nCount++, java.sql.Types.INTEGER);
            }
            if (test instanceof pt.inescporto.template.elements.TplFloat) {
              if (((TplFloat)test).getValue() != null)
                ps.setFloat(nCount++, ((TplFloat)test).getValue().floatValue());
              else
                ps.setNull(nCount++, java.sql.Types.FLOAT);
            }
            if (test instanceof pt.inescporto.template.elements.TplTimestamp) {
              if (((TplTimestamp)test).getValue() != null)
                ps.setTimestamp(nCount++, ((TplTimestamp)test).getValue());
              else
                ps.setNull(nCount++, java.sql.Types.TIMESTAMP);
            }
            if (test instanceof pt.inescporto.template.elements.TplObjRef) {
              if (((TplObjRef)test).getValue() != null) {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                try {
                  ObjectOutputStream out = new ObjectOutputStream(b);
                  out.writeObject(((TplObjRef)test).getValue());
                }
                catch (IOException ex) {
                  ex.printStackTrace();
                }
                ps.setBytes(nCount++, b.toByteArray());
              }
              else
                ps.setNull(nCount++, java.sql.Types.JAVA_OBJECT);
            }
          }
        }
      }
    }
    catch (SQLException sqlex) {
      sqlex.printStackTrace();
    }
  }

  public Object load(Object attrs) throws SQLException, NamingException, RowNotFoundException {

    this.attrs = attrs;
    try {
      init(selectStmt);
      int nCount = setWhere(1, 9999);
      setWhereLink(nCount);
      rs = ps.executeQuery();

      if (!next())
        throw new RowNotFoundException();
      return attrs;
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
  }

  public void remove(Object attrs) throws SQLException, NamingException, ConstraintViolatedException {
    this.attrs = attrs;
    try {
      init(deleteStmt);
      int nCount = setWhere(1, 9999);
      if (ps.executeUpdate() == 0)
        throw new SQLException("unable to remove record");
    }
    catch (SQLException ex) {
      if ((ex.getErrorCode() == 0 && Integer.decode(ex.getSQLState()).intValue() == 23503) //postgres
	  ) {
        throw new ConstraintViolatedException();
      }
      else {
	System.out.println(ex.getSQLState());
	ex.printStackTrace();
	throw ex;
      }
    }
    finally {
      close();
    }
  }

  public void create(Object attrs) throws SQLException, NamingException, DupKeyException {
    Field[] fld;
    int nCount = 1;

    fld = attrs.getClass().getDeclaredFields();

    try {
      init(insertStmt);
      for (int i = 0; i < fld.length; i++) {
        try {
          // is a string
          if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString) {
            TplString myStr = (TplString)fld[i].get(attrs);
            if (myStr.isRequired())
              ps.setString(nCount++, myStr.getValue());
          }
          // is a boolean
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean) {
            TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
            if (myBoolean.isRequired()) {
              if (myBoolean.getValue() != null)
                ps.setBoolean(nCount++, myBoolean.getValue().booleanValue());
              else
                ps.setNull(nCount++, java.sql.Types.BIT);
            }
          }
          // is a integer
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt) {
            TplInt myInt = (TplInt)fld[i].get(attrs);
            if (myInt.isRequired()) {
              if (myInt.getValue() != null)
                ps.setInt(nCount++, myInt.getValue().intValue());
              else
                ps.setNull(nCount++, java.sql.Types.INTEGER);
            }
          }
          // is a long
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong) {
            TplLong myLong = (TplLong)fld[i].get(attrs);
            if (myLong.isRequired()) {
              if (myLong.getValue() != null)
                ps.setLong(nCount++, myLong.getValue().longValue());
              else
                ps.setNull(nCount++, java.sql.Types.INTEGER);
            }
          }
          // is a float
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat) {
            TplFloat myFloat = (TplFloat)fld[i].get(attrs);
            if (myFloat.isRequired()) {
              if (myFloat.getValue() != null)
                ps.setFloat(nCount++, myFloat.getValue().floatValue());
              else
                ps.setNull(nCount++, java.sql.Types.FLOAT);
            }
          }
          // is a Timestamp
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp) {
            TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
            if (myTime.isRequired())
              ps.setTimestamp(nCount++, myTime.getValue());
          }
          // is a Object
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplObjRef) {
            TplObjRef myObjRef = (TplObjRef)fld[i].get(attrs);
            if (myObjRef.isRequired()) {
              if (myObjRef.getValue() != null) {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                try {
                  ObjectOutputStream out = new ObjectOutputStream(b);
                  out.writeObject(myObjRef.getValue());
                }
                catch (IOException ex) {
                  ex.printStackTrace();
                }
                ps.setBytes(nCount++, b.toByteArray());
              }
              else
                ps.setNull(nCount++, java.sql.Types.BINARY);
            }
          }
        }
        catch (Exception e) {
          System.out.println(e.toString());
        }
      }
      ps.executeUpdate();
    }
    catch (SQLException ex) {
      if (ex.getErrorCode() == 1
          || ex.getErrorCode() == (int)(2601)
          || ex.getErrorCode() == (int)(2627)
          || ex.getErrorCode() == (int)(  -9) //hsqldb
          || (ex.getErrorCode() == 0 && Integer.decode(ex.getSQLState()).intValue() == 23505) //postgres
          )
        throw new DupKeyException();
      else {
        System.out.println(ex.getSQLState());
	throw ex;
      }
    }
    finally {
      close();
    }
  }

  public void update(Object attrs) throws SQLException, NamingException {
    Field[] fld;
    int nCount = 1;

    this.attrs = attrs;
    if (updateStmt == null || updateStmt.equals(""))
      return;

    fld = attrs.getClass().getDeclaredFields();

    try {
      init(updateStmt);
      for (int i = 0; i < fld.length; i++) {
        try {
          // is a string
          if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString) {
            TplString myStr = (TplString)fld[i].get(attrs);
            if (!myStr.isPrimaryKey())
              ps.setString(nCount++, myStr.getValue());
          }
          // is a boolean
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean) {
            TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
            if (!myBoolean.isPrimaryKey()) {
              if (myBoolean.getValue() != null)
                ps.setBoolean(nCount++, myBoolean.getValue().booleanValue());
              else
                ps.setNull(nCount++, java.sql.Types.BIT);
            }
          }
          // is a integer
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt) {
            TplInt myInt = (TplInt)fld[i].get(attrs);
            if (!myInt.isPrimaryKey()) {
              if (myInt.getValue() != null)
                ps.setInt(nCount++, myInt.getValue().intValue());
              else
                ps.setNull(nCount++, java.sql.Types.INTEGER);
            }
          }
          // is a long
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong) {
            TplLong myLong = (TplLong)fld[i].get(attrs);
            if (!myLong.isPrimaryKey()) {
              if (myLong.getValue() != null)
                ps.setLong(nCount++, myLong.getValue().longValue());
              else
                ps.setNull(nCount++, java.sql.Types.INTEGER);
            }
          }
          // is a float
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat) {
            TplFloat myFloat = (TplFloat)fld[i].get(attrs);
            if (!myFloat.isPrimaryKey()) {
              if (myFloat.getValue() != null)
                ps.setFloat(nCount++, myFloat.getValue().floatValue());
              else
                ps.setNull(nCount++, java.sql.Types.FLOAT);
            }
          }
          // is a Timestamp
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp) {
            TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
            if (!myTime.isPrimaryKey())
              ps.setTimestamp(nCount++, myTime.getValue());
          }
          // is a Object
          else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplObjRef) {
            TplObjRef myObjRef = (TplObjRef)fld[i].get(attrs);
            if (!myObjRef.isPrimaryKey()) {
              if (myObjRef.getValue() != null) {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                try {
                  ObjectOutputStream out = new ObjectOutputStream(b);
                  out.writeObject(myObjRef.getValue());
                }
                catch (IOException ex) {
                  ex.printStackTrace();
                }
                ps.setBytes(nCount++, b.toByteArray());
              }
              else
                ps.setNull(nCount++, java.sql.Types.BINARY);
            }
          }
        }
        catch (Exception e) {
          System.out.println(e.toString());
        }
      }
      nCount = setWhere(nCount, 9999);
      ps.executeUpdate();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
  }

  public void findByPrimaryKey(Object attrs) throws SQLException, NamingException, RowNotFoundException {

    this.attrs = attrs;
    try {
      init(findStmt);
      int nCount = setWhere(1, 9999);
      rs = ps.executeQuery();
      if (!next())
        throw new RowNotFoundException();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
  }

  public void findNextKey(Object attrs) throws SQLException, NamingException, RowNotFoundException {
    int i;
    boolean found = false;

    this.attrs = attrs;
    try {
      for (i = 0; i < this.forwardStmt.length && !found; i++) {
        init(forwardStmt[i]);
        int nCount = setWhere(1, this.forwardStmt.length - i + lnkCount);
        setWhereLink(nCount);
        ps.setFetchSize(1);
        rs = ps.executeQuery();
        found = next();
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
    if (!found)
      throw new RowNotFoundException();
  }

  public void findPrevKey(Object attrs) throws SQLException, NamingException, RowNotFoundException {
    int i;
    boolean found = false;

    this.attrs = attrs;
    try {
      for (i = 0; i < this.backwardStmt.length && !found; i++) {
        init(backwardStmt[i]);
        int nCount = setWhere(1, this.backwardStmt.length - i + lnkCount);
        setWhereLink(nCount);
        ps.setFetchSize(1);
        rs = ps.executeQuery();
        found = next();
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }

    if (!found)
      throw new RowNotFoundException();
  }

  public Collection find(Object attrs) throws SQLException, NamingException, RowNotFoundException {
    Field[] fld;
    String whereStmt = "";

    this.attrs = attrs;
    fld = attrs.getClass().getDeclaredFields();

    // construct where statment
    for (int i = 0; i < fld.length; i++) {
      try {
        // is a string
        if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString) {
          TplString myStr = (TplString)fld[i].get(attrs);
          if (myStr.getValue() != null && !myStr.getValue().equals(""))
            whereStmt += table + "." + myStr.getField() + " LIKE ? AND ";
        }
        // is a boolean
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean) {
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          if (myBoolean.getValue() != null)
            whereStmt += table + "." + myBoolean.getField() + " = ? AND ";
        }
        // is a integer
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt) {
          TplInt myInt = (TplInt)fld[i].get(attrs);
          if (myInt.getValue() != null)
            whereStmt += table + "." + myInt.getField() + " = ? AND ";
        }
        // is a long
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong) {
          TplLong myLong = (TplLong)fld[i].get(attrs);
          if (myLong.getValue() != null)
            whereStmt += table + "." + myLong.getField() + " = ? AND ";
        }
        // is a float
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat) {
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          if (myFloat.getValue() != null)
            whereStmt += table + "." + myFloat.getField() + " = ? AND ";
        }
        // is a Timestamp
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp) {
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          if (myTime.getValue() != null)
            whereStmt += table + "." + myTime.getField() + " = ? AND ";
        }
        // is a Object
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplObjRef) {
          TplObjRef myObjRef = (TplObjRef)fld[i].get(attrs);
          if (myObjRef.getValue() != null)
            whereStmt += table + "." + myObjRef.getField() + " = ? AND ";
        }
      }
      catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    // trim last AND
    if (whereStmt != "")
      whereStmt = whereStmt.substring(0, whereStmt.length() - 5);
    else
      whereStmt = "1 = 1";

    // final query
    String fStmt = "";
    if (linkCondition != null)
      fStmt = "SELECT " + this.selPart + " FROM " + this.table + " WHERE " + whereStmt + " AND " + linkCondition;
    else
      fStmt = "SELECT " + this.selPart + " FROM " + this.table + " WHERE " + whereStmt;

    try {
      init(fStmt);
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      close();
      throw ex;
    }

    // bind the linkCondition
    if (this.linkCondition != null)
      setWhereLink(1);

    // set values
    int count = 1;
    for (int i = 0; i < fld.length; i++) {
      try {
        // is a string
        if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString) {
          TplString myStr = (TplString)fld[i].get(attrs);
          if (myStr.getValue() != null && !myStr.getValue().equals(""))
            ps.setString(count++, "%" + myStr.getValue() + "%");
        }
        // is a boolean
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean) {
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          if (myBoolean.getValue() != null)
            ps.setBoolean(count++, myBoolean.getValue().booleanValue());
        }
        // is a integer
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt) {
          TplInt myInt = (TplInt)fld[i].get(attrs);
          if (myInt.getValue() != null)
            ps.setInt(count++, myInt.getValue().intValue());
        }
        // is a long
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong) {
          TplLong myLong = (TplLong)fld[i].get(attrs);
          if (myLong.getValue() != null)
            ps.setLong(count++, myLong.getValue().longValue());
        }
        // is a float
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat) {
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          if (myFloat.getValue() != null)
            ps.setFloat(count++, myFloat.getValue().floatValue());
        }
        // is a Timestamp
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp) {
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          if (myTime.getValue() != null)
            ps.setTimestamp(count++, myTime.getValue());
        }
        // is a Object
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplObjRef) {
          TplObjRef myObjRef = (TplObjRef)fld[i].get(attrs);
          if (myObjRef.getValue() != null) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            try {
              ObjectOutputStream out = new ObjectOutputStream(b);
              out.writeObject(myObjRef.getValue());
            }
            catch (IOException ex) {
              ex.printStackTrace();
            }
            ps.setBytes(count++, b.toByteArray());
          }
        }
      }
      catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    // execute query
    rs = ps.executeQuery();

    // fetch values
    ArrayList findAttrs = new ArrayList();
    boolean found = false;
    while (next()) {
      try {
        findAttrs.add(AttrsCopy.cloneObject(this.attrs));
      }
      catch (Exception ex) {
        throw new RowNotFoundException();
      }
      found = true;
    }
    close();

    if (!found)
      throw new RowNotFoundException();

    return findAttrs;
  }

  public Collection findExact(Object attrs) throws SQLException, NamingException,
      RowNotFoundException {
    Field[] fld;
    String whereStmt = "";

    this.attrs = attrs;
    fld = attrs.getClass().getDeclaredFields();

    // construct where statment
    for (int i = 0; i < fld.length; i++) {
      try {
        // is a string
        if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplString) {
          TplString myStr = (TplString)fld[i].get(attrs);
          if (myStr.getValue() != null && !myStr.getValue().equals(""))
            whereStmt += table + "." + myStr.getField() + " = ? AND ";
        }
        // is a boolean
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplBoolean) {
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          if (myBoolean.getValue() != null)
            whereStmt += table + "." + myBoolean.getField() + " = ? AND ";
        }
        // is a integer
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplInt) {
          TplInt myInt = (TplInt)fld[i].get(attrs);
          if (myInt.getValue() != null)
            whereStmt += table + "." + myInt.getField() + " = ? AND ";
        }
        // is a long
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplLong) {
          TplLong myLong = (TplLong)fld[i].get(attrs);
          if (myLong.getValue() != null)
            whereStmt += table + "." + myLong.getField() + " = ? AND ";
        }
        // is a float
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplFloat) {
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          if (myFloat.getValue() != null)
            whereStmt += table + "." + myFloat.getField() + " = ? AND ";
        }
        // is a Timestamp
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.TplTimestamp) {
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          if (myTime.getValue() != null)
            whereStmt += table + "." + myTime.getField() + " = ? AND ";
        }
      }
      catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    // trim last AND
    if (whereStmt != "")
      whereStmt = whereStmt.substring(0, whereStmt.length() - 5);
    else
      whereStmt = "1 = 1";

    // final query
    String fStmt = "";
    fStmt = "SELECT " + this.selPart + " FROM " + this.table + " WHERE " + whereStmt;

    if (linkCondition != null)
      fStmt += " AND " + linkCondition;

    try {
      init(fStmt);
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      close();
      throw ex;
    }

    // set values
    int count = 1;
    for (int i = 0; i < fld.length; i++) {
      try {
        // is a string
        if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.
            TplString) {
          TplString myStr = (TplString)fld[i].get(attrs);
          if (myStr.getValue() != null && !myStr.getValue().equals(""))
            ps.setString(count++, myStr.getValue());
        }
        // is a boolean
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.
                 TplBoolean) {
          TplBoolean myBoolean = (TplBoolean)fld[i].get(attrs);
          if (myBoolean.getValue() != null)
            ps.setBoolean(count++, myBoolean.getValue().booleanValue());
        }
        // is a integer
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.
                 TplInt) {
          TplInt myInt = (TplInt)fld[i].get(attrs);
          if (myInt.getValue() != null)
            ps.setInt(count++, myInt.getValue().intValue());
        }
        // is a long
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.
                 TplLong) {
          TplLong myLong = (TplLong)fld[i].get(attrs);
          if (myLong.getValue() != null)
            ps.setLong(count++, myLong.getValue().longValue());
        }
        // is a float
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.
                 TplFloat) {
          TplFloat myFloat = (TplFloat)fld[i].get(attrs);
          if (myFloat.getValue() != null)
            ps.setFloat(count++, myFloat.getValue().floatValue());
        }
        // is a Timestamp
        else if (fld[i].get(attrs) instanceof pt.inescporto.template.elements.
                 TplTimestamp) {
          TplTimestamp myTime = (TplTimestamp)fld[i].get(attrs);
          if (myTime.getValue() != null)
            ps.setTimestamp(count++, myTime.getValue());
        }
      }
      catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    if (linkCondition != null)
      setWhereLink(count);

    // execute query
    rs = ps.executeQuery();

    // fetch values
    ArrayList findAttrs = new ArrayList();
    boolean found = false;
    while (next()) {
      try {
        findAttrs.add(AttrsCopy.cloneObject(this.attrs));
      }
      catch (Exception ex) {
        throw new RowNotFoundException();
      }
      found = true;
    }
    close();

    if (!found)
      throw new RowNotFoundException();

    return findAttrs;
  }

  public Collection listAll() throws SQLException, NamingException, RowNotFoundException {
    try {
      init(selectAllStmt);

      if (this.linkCondition != null)
        setWhereLink(1);

      rs = ps.executeQuery();

      ArrayList idList = new ArrayList();

      while (next()) {
        try {
          idList.add(AttrsCopy.cloneObject(this.attrs));
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      return idList;
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
  }

  public Collection listAllOnlyBind() throws SQLException, NamingException, RowNotFoundException {
    try {
      init(selectAllStmt);

      if (this.linkCondition != null)
        setWhereLink(1);

      rs = ps.executeQuery();

      ArrayList idList = new ArrayList();

      while (next()) {
        try {
          idList.add(AttrsCopy.cloneObject(this.attrs));
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      return idList;
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
  }

  public Collection listAllOrderBy(String orderByStmt) throws SQLException, NamingException, RowNotFoundException {
    try {
      init(selectAllStmtOrderBy + " ORDER BY " + orderByStmt);

      if (linkCondition != null)
        setWhereLink(1);

      rs = ps.executeQuery();

      ArrayList idList = new ArrayList();

      while (next()) {
        try {
          idList.add(AttrsCopy.cloneObject(this.attrs));
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      return idList;
    }
    catch (SQLException ex) {
      ex.printStackTrace();
      throw ex;
    }
    finally {
      close();
    }
  }

  public Integer count() throws SQLException, NamingException {
    Integer count = null;

    if (linkCondition != null && !linkCondition.equals("")) {
      countStmt += (linkTabels == null ?  " WHERE " : " AND ") + linkCondition;
    }

    try {
//      System.out.println(countStmt);
      init(countStmt);

      if (linkCondition != null) {
        setWhereLink(1);
      }

      rs = ps.executeQuery();

      count = (!rs.next() ? null : new Integer(rs.getInt(1)));
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
    finally {
      close();
    }

    return count;
  }
}
