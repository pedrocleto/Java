package pt.inescporto.siasoft.tools;

import java.sql.*;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class DatabaseSpecs {
  private String driver = null;
  private String url = null;
  private String user = null;
  private String passwd = null;
  private String catalog = null;
  private String schema = null;
  private Connection dbConnection;

  public DatabaseSpecs() {
  }

  public String getCatalog() {
    return catalog;
  }

  public String getDriver() {
    return driver;
  }

  public String getPasswd() {
    return passwd;
  }

  public String getSchema() {
    return schema;
  }

  public String getUrl() {
    return url;
  }

  public String getUser() {
    return user;
  }

  public Connection getDbConnection() {
    return dbConnection;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  public void setDbConnection(Connection dbConnection) {
    this.dbConnection = dbConnection;
  }
}
