package pt.inescporto.template.web.util;

import java.util.Collection;
import java.util.Vector;
import pt.inescporto.template.utils.TmplMessages;

public class TmplHttpMessage implements java.io.Serializable {
  protected Object attrs = null;
  protected Object[] pkKeys = null;
  protected String[] lookupDesc = null;
  protected String linkCondition = null;
  protected int returnCode = TmplMessages.NOT_DEFINED;
  protected String errorMsg = null;
  protected int command = TmplMessages.NOT_DEFINED;
  protected Collection all = null;
  protected Collection find = null;
  protected Vector binds = null;
  protected boolean asMaster = true;

  public TmplHttpMessage() {
    attrs = null;
    pkKeys = null;
    lookupDesc = null;
    returnCode = TmplMessages.NOT_DEFINED;
    command = TmplMessages.NOT_DEFINED;
    all = null;
    find = null;
    linkCondition = null;
  }

  public TmplHttpMessage( int command ) {
    attrs = null;
    pkKeys = null;
    lookupDesc = null;
    returnCode = TmplMessages.NOT_DEFINED;
    this.command = command;
    all = null;
    find = null;
    linkCondition = null;
  }

  public TmplHttpMessage( int command, Object attrs) {
    this.command = command;
    this.attrs = attrs;
    pkKeys = null;
    lookupDesc = null;
    returnCode = TmplMessages.NOT_DEFINED;
    all = null;
    find = null;
    linkCondition = null;
  }

  public TmplHttpMessage( int command, Object[] pkKeys) {
    this.command = command;
    this.attrs = null;
    this.pkKeys = pkKeys;
    lookupDesc = null;
    returnCode = TmplMessages.NOT_DEFINED;
    all = null;
    find = null;
    linkCondition = null;
  }

  public void setAttrs( Object attrs ) {
    this.attrs = attrs;
  }

  public Object getAttrs() {
    return this.attrs;
  }

  public void setPkKeys( Object[] pkKeys ) {
    this.pkKeys = pkKeys;
  }

  public Object[] getPkKeys() {
    return this.pkKeys;
  }

  public void setLookupDesc( String[] lookupDesc ) {
    this.lookupDesc = lookupDesc;
  }

  public String[] getLookupDesc() {
    return this.lookupDesc;
  }

  public void setReturnCode( int returnCode ) {
    this.returnCode = returnCode;
  }

  public int getReturnCode() {
    return this.returnCode;
  }

  public void setErrorMsg( String errorMsg ) {
    this.errorMsg = errorMsg;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public int getCommand() {
    return this.command;
  }

  public void setAll( Collection all ) {
    this.all = all;
  }

  public Collection getAll() {
    return this.all;
  }

  public void setFind( Collection find ) {
    this.find = find;
  }

  public Collection getFind() {
    return this.find;
  }

  public void setLinkCondition( String linkCondition ) {
    this.linkCondition = linkCondition;
  }

  public String getLinkCondition() {
    return this.linkCondition;
  }

  public void setBinds( Vector binds ) {
    this.binds = binds;
  }

  public Vector getBinds() {
    return this.binds;
  }

  public void setAsMaster( boolean asMaster ) {
    this.asMaster = asMaster;
  }

  public boolean getAsMaster() {
    return this.asMaster;
  }
}