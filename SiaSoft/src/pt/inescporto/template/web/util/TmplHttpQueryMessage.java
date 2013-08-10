package pt.inescporto.template.web.util;

import java.util.Vector;
import pt.inescporto.template.utils.TmplMessages;

public class TmplHttpQueryMessage implements java.io.Serializable {
  protected String queryStmt = null;
  protected Vector binds = null;
  protected Vector result = null;
  protected int returnCode = TmplMessages.NOT_DEFINED;
  protected int command = TmplMessages.NOT_DEFINED;

  public TmplHttpQueryMessage( int command, String queryStmt, Vector binds ) {
    this.queryStmt = queryStmt;
    this.binds = binds;
    this.command = command;
  }

  public void setQueryStmt( String queryStmt ) {
    this.queryStmt = queryStmt;
  }

  public String getQueryStmt() {
    return this.queryStmt;
  }

  public void setBinds( Vector binds ) {
    this.binds = binds;
  }

  public Vector getBinds() {
    return this.binds;
  }

  public void setResult( Vector result ) {
    this.result = result;
  }

  public Vector getResult() {
    return this.result;
  }

  public void setReturnCode( int returnCode ) {
    this.returnCode = returnCode;
  }

  public int getReturnCode() {
    return this.returnCode;
  }

  public int getCommand() {
    return this.command;
  }
}