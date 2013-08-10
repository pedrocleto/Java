package pt.inescporto.template.web.util;

import java.util.Vector;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.beans.LoggedUser;

public class TmplHttpMenuMsg implements java.io.Serializable {
  protected String searchId = null;
  protected Vector result = null;
  protected LoggedUser usrInfo = null;
  protected int returnCode = TmplMessages.NOT_DEFINED;
  protected int command = TmplMessages.NOT_DEFINED;

  public TmplHttpMenuMsg( int command, String searchId ) {
    this.command = command;
    this.searchId = searchId;
  }

  public void setSearchId( String searchId ) {
    this.searchId = searchId;
  }

  public String getSearchId() {
    return this.searchId;
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

  public void setUsrInfo(LoggedUser usrInfo) {
    this.usrInfo = usrInfo;
  }

  public LoggedUser getUsrInfo() {
    return usrInfo;
  }
}
