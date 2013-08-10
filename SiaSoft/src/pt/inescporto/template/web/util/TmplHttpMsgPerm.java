package pt.inescporto.template.web.util;

import java.util.Collection;
import pt.inescporto.template.utils.TmplMessages;

public class TmplHttpMsgPerm implements java.io.Serializable {
  protected int returnCode = TmplMessages.NOT_DEFINED;
  protected int command = TmplMessages.NOT_DEFINED;
  protected Collection all = null;
  protected String formId = null;
  protected int perms = 15;
  protected boolean supplier = false;

  public TmplHttpMsgPerm() {
    returnCode = TmplMessages.NOT_DEFINED;
    command = TmplMessages.NOT_DEFINED;
    all = null;
    formId = null;
  }

  public TmplHttpMsgPerm( int command, String formId ) {
    returnCode = TmplMessages.NOT_DEFINED;
    this.command = command;
    all = null;
    this.formId = formId;
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

  public void setAll( Collection all ) {
    this.all = all;
  }

  public Collection getAll() {
    return this.all;
  }

  public void setFormId( String formId ) {
    this.formId = formId;
  }

  public String getFormId() {
    return this.formId;
  }

  public void setPerms( int perms ) {
    this.perms = perms;
  }

  public int getPerms() {
    return this.perms;
  }

  /* ************************************************************************* *
   * *** This methods represents state of enterprise                       *** *
   * ***************************************************************************/
  public void setSupplier( boolean supplier ) {
    this.supplier = supplier;
  }

  public boolean isSupplier() {
    return supplier;
  }
}