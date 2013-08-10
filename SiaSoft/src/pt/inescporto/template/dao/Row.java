package pt.inescporto.template.dao;

import java.util.Hashtable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;
import java.sql.Types;
import pt.inescporto.template.elements.TplString;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.*;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class Row {
  public Row() {
  }

  public Hashtable create(String tableName) throws Exception {
    String jdbcDriver = null;
    String url = null;
    String catalog = null;
    String schema = null;
    String user = null;
    String passwd = null;
    Connection dbConnection = null;
    int type = 3;


    switch (type) {
      case 1:
        jdbcDriver = "com.inet.tds.TdsDriver";
        url = "jdbc:inetdae://hobbes.inescn.pt:1433?database=P50Indasa";
        catalog = "P50Indasa";
        schema = "qmat";
        user = "qmat";
        passwd = "QMAT";
        Class.forName(jdbcDriver);
        break;
      case 2:
        jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        url = "jdbc:oracle:thin:@hobbes.inescn.pt:1521:olympu";
        catalog = null;
        schema = "SONAFI";
        user = "sonafi";
        passwd = "popo1";
        Class.forName(jdbcDriver);
        break;
      case 3:
        jdbcDriver = "org.postgresql.Driver";
        url = "jdbc:postgresql://luxuria.inescn.pt";
        catalog = "SIASoft";
        schema = "public";
        user = "SIASoft";
        passwd = "canela";
        Class.forName(jdbcDriver);
        break;
    }
    dbConnection = DriverManager.getConnection(url, user, passwd);

    DatabaseMetaData dbMeta = dbConnection.getMetaData();

    System.out.println("Connected to " + dbMeta.getDatabaseProductName() + " version " + dbMeta.getDatabaseProductVersion());
    System.out.println("Using driver " + dbMeta.getDriverName() + " version " + dbMeta.getDriverVersion());
    System.out.println("Catalog term is " + dbMeta.getCatalogTerm());
    System.out.println("Schema term is " + dbMeta.getSchemaTerm());


    System.out.println("\r\nCatalog list: ");
    ResultSet rsCatalog = dbMeta.getCatalogs();
    while (rsCatalog.next()) {
      for (int i = 1; i <= rsCatalog.getMetaData().getColumnCount(); i++)
        System.out.println(rsCatalog.getObject(i));
    }

    try {
      System.out.println("\r\nSchema list: ");
      ResultSet rsSchema = dbMeta.getSchemas();
      while (rsSchema.next()) {
        for (int i = 1; i <= rsSchema.getMetaData().getColumnCount(); i++) {
          System.out.println(rsSchema.getObject(i));
        }
      }
    }
    catch (SQLException ex) {
    }

//    System.out.println("\r\nType Information");

    ResultSet rsTypes = dbMeta.getTypeInfo();
    int j = 0;
/*    while (rsTypes.next()) {
      System.out.print("Row " + j++ + " : ");
      for (int i = 1; i <= rsTypes.getMetaData().getColumnCount(); i++)
        System.out.print(rsTypes.getObject(i) + ", ");
      System.out.println("");
    }*/

    System.out.println("\r\nTable Information :");
    ResultSet rsTable = dbMeta.getTables(catalog, schema, null, new String[] {"TABLE"});
    j = 0;
    while (rsTable.next()) {
      System.out.print("Row " + j++ + " : ");
      for (int i = 1; i <= rsTable.getMetaData().getColumnCount(); i++)
        System.out.print(rsTable.getObject(i) + ", ");
      System.out.println("");
    }

    System.out.println("\r\nIndex Information :");
    ResultSet rsIndex = dbMeta.getIndexInfo(catalog, schema, tableName, true, true);
    j = 0;
    while (rsIndex.next()) {
      System.out.print("Row " + j++ + " : ");
      for (int i = 1; i <= rsIndex.getMetaData().getColumnCount(); i++)
        System.out.print(rsIndex.getMetaData().getColumnName(i) + "<" + rsIndex.getObject(i) + ">, ");
      System.out.println("");
    }

    System.out.println("\r\nPrimary Key Information :");
    ResultSet rsPK = dbMeta.getTableTypes();
    j = 0;
    while (rsPK.next()) {
      System.out.print("Row " + j++ + " : ");
      for (int i = 1; i <= rsPK.getMetaData().getColumnCount(); i++)
        System.out.print(rsPK.getObject(i) + ", ");
      System.out.println("");
    }

/*    System.out.println("Information from driver:");
    System.out.println("  Autocommit : " + dbConnection.getAutoCommit());
    System.out.println("  Catalog : " + dbConnection.getCatalog());
    System.out.println("  Transaction Isolation Level : " + dbConnection.getTransactionIsolation());
    System.out.println("  Read only : " + dbConnection.isReadOnly());
    Map map = dbConnection.getTypeMap();
    if (map == null)
      map = new HashMap();
    map.put(new Integer(Types.VARCHAR), TplString.class.getName());
    dbConnection.setTypeMap(map);

    PreparedStatement ps = dbConnection.prepareStatement("SELECT * FROM " + tableName + " WHERE 0 = 1");
    ps.executeQuery();

    ResultSetMetaData rsMeta = ps.getMetaData();
    System.out.println(Types.VARCHAR);
    System.out.println("Information from table " + tableName + ":");
    System.out.println("  Number of columns : " + rsMeta.getColumnCount());
    for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
      System.out.println("  Column nº " + i + " name : " + rsMeta.getColumnName(i));
      System.out.println("    Type : " + rsMeta.getColumnTypeName(i) + ", " + rsMeta.getColumnType(i));
      System.out.println("    Class name : " + rsMeta.getColumnClassName(i));
      System.out.println("    Display Size : " + rsMeta.getColumnDisplaySize(i));
      System.out.println("    Precision : " + rsMeta.getPrecision(i));
      System.out.println("    Scale : " + rsMeta.getScale(i));
      System.out.println("    Nullable : " + rsMeta.isNullable(i));
      System.out.println("    Searchable : " + rsMeta.isSearchable(i));
    }*/

    return null;
  }

  public static void main(String[] args) {
    Row row = new Row();
    try {
      row.create("auditactions");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
