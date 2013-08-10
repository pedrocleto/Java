package pt.inescporto.template.web.proxies;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.TmplHttpMessage;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.ConstraintViolatedException;

public abstract class TmplProxy extends TmplBasicProxy {
  protected Collection all = null;
  protected Collection find = null;
  protected TmplHttpMessage msg = null;
  protected String[] desc = null;
  protected String linkCondition = null;
  protected Vector binds = null;

  protected void messageReceived() {
    // reset some values
    desc = null;
    all = null;
    msg = (TmplHttpMessage)msgObject;

    // Get Link Condition
    try {
      linkCondition = msg.getLinkCondition();
      binds = msg.getBinds();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void procMsg(HttpServletRequest req, HttpServletResponse resp) {
    // set link condition if exists
    if (msg.getCommand() == TmplMessages.MSG_INITIALIZE && msg.getLinkCondition() != null)
      this.setLinkCondition(req, msg.getLinkCondition(), msg.getBinds());

    // set default return code
    msg.setReturnCode(TmplMessages.MSG_OK);
    if (msg.getAttrs() != null)
      this.attrs = msg.getAttrs();

    /* process */
    switch (msg.getCommand()) {
      case TmplMessages.MSG_BACKWARD: // move backward
        try {
          attrs = doBackward();
        }
        catch (RowNotFoundException rnfex) {
          msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          msg.setErrorMsg("Não Encontrado!");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_FIRST: // find first
        try {
          attrs = doFindFirst();
        }
        catch (RowNotFoundException rnfex) {
          msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          msg.setErrorMsg("Não Encontrado!");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_FIND_PK: // find pk
        try {
          attrs = doFindPk();
        }
        catch (RowNotFoundException rnfex) {
          msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          msg.setErrorMsg("Não Encontrado!");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_FIND: // find
        try {
          find = doFind();
        }
        catch (RowNotFoundException rnfex) {
          msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          msg.setErrorMsg("Não Encontrado!");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_FORWARD: // move forward
        try {
          attrs = doForward();
        }
        catch (RowNotFoundException rnfex) {
          msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          msg.setErrorMsg("Não Encontrado!");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_INSERT: // insert
        try {
          attrs = doInsert();
        }
        catch (DupKeyException dkex) {
          msg.setReturnCode(TmplMessages.MSG_DUPKEY);
          msg.setErrorMsg("Código Duplicado !");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_UPDATE: // update
        try {
          attrs = doUpdate();
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_DELETE: // delete
        try {
          doDelete();
          try {
            attrs = doFindFirst();
          }
          catch (RowNotFoundException rnfex) {
            msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
            msg.setErrorMsg("Não Encontrado!");
          }
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (ConstraintViolatedException dcex) {
          msg.setReturnCode(TmplMessages.MSG_CONSTRAINTD);
          msg.setErrorMsg("O Registo Actual Está a Ser Usado!");
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_LISTALL: // return a collection *
        try {
          all = doListAll();
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      case TmplMessages.MSG_GET_DESC: // return descriptions
        try {
          desc = doLookupDesc(msg.getPkKeys());
        }
        catch (RowNotFoundException rnfex) {
          msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          msg.setErrorMsg("Não Encontrado!");
        }
        catch (UserException ex) {
          msg.setReturnCode(TmplMessages.USR_ERROR);
          msg.setErrorMsg(ex.getMessage());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break
            ;
      case TmplMessages.MSG_SETLINK: // save link condition
        try {
          setLinkCondition(req, msg.getLinkCondition(), msg.getBinds());
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
      default:
        try {
          processUserMessage(req, resp);
        }
        catch (Exception e) {
          if (bDebug)
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
          msg.setErrorMsg(e.getLocalizedMessage());
        }
        break;
    }

    // save results to msg
    msg.setAttrs(attrs);
    msg.setAll(all);
    msg.setFind(find);
    msg.setLookupDesc(desc);

    msgObject = (Object)msg;
  }

  protected void setLinkCondition(HttpServletRequest req, String linkCondition, Vector binds) {
    System.out.println("Link condition set to value: " + linkCondition);
  }

  protected void processUserMessage(HttpServletRequest req, HttpServletResponse resp) {
  }

  protected abstract Object doBackward() throws RowNotFoundException, RemoteException, UserException;

  protected abstract Object doFindFirst() throws RowNotFoundException, RemoteException, UserException;

  protected abstract Object doFindPk() throws RowNotFoundException, RemoteException, UserException;

  protected abstract Collection doFind() throws RowNotFoundException, RemoteException, UserException;

  protected abstract Object doForward() throws RowNotFoundException, RemoteException, UserException;

  protected abstract Object doInsert() throws DupKeyException, RemoteException, UserException;

  protected abstract Object doUpdate() throws RemoteException, UserException;

  protected abstract void doDelete() throws RemoteException, ConstraintViolatedException, UserException;

  protected abstract Collection doListAll() throws RemoteException, RowNotFoundException, UserException;

  protected abstract String[] doLookupDesc(Object[] pkKeys) throws RowNotFoundException, RemoteException, UserException;
}
