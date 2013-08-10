package pt.inescporto.template.web.util;

import java.util.Vector;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.*;

public class TmplQuery {
  protected String url = null;
  protected String queryStmt = null;
  protected Vector binds = null;

  public TmplQuery( String url, String queryStmt, Vector binds ) {
    this.url = url + "/GenericQueryProxy";
    this.queryStmt = queryStmt;
    this.binds = (binds == null ? new Vector() : binds);
  }

  public Vector doQuery() {
    try {
      TmplHttpQueryMessage msg = new TmplHttpQueryMessage( TmplMessages.MSG_LISTALL,
            this.queryStmt, binds );
      TmplHttpMessageSender req = new TmplHttpMessageSender( this.url );
      msg = (TmplHttpQueryMessage)req.doSendObject((Object)msg);
      return msg.getResult();
    }
    catch( Exception ex ) {
      return null;
    }
  }

  public void doExecuteQuery() {
    try {
      TmplHttpQueryMessage msg = new TmplHttpQueryMessage( TmplMessages.MSG_UPDATE,
            this.queryStmt, binds );
      TmplHttpMessageSender req = new TmplHttpMessageSender( this.url );
      msg = (TmplHttpQueryMessage)req.doSendObject((Object)msg);
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }
  }
}