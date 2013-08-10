package pt.inescporto.siasoft.asq.web.proxy;

import javax.servlet.http.HttpSession;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.siasoft.asq.ejb.dao.ScopeDao;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.session.ScopeHome;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.siasoft.asq.ejb.session.Scope;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class ScopeProxy extends TmplProxy {
  HttpSession session = null;
  Scope scope = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    session = req.getSession();

    // Get home of EJB
    if ((Scope)session.getAttribute("Scope") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/Scope");
	ScopeHome scopeHome = (ScopeHome)PortableRemoteObject.narrow(objref, ScopeHome.class);
	scope = scopeHome.create();
	session.setAttribute("Scope", scope);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate Scope Home");
	re.printStackTrace();
      }

      attrs = new ScopeDao();
      session.setAttribute("ScopeAttrs", attrs);
    }
    else {
      scope = (Scope)session.getAttribute("Scope");
      this.attrs = session.getAttribute("ScopeAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException, UserException {
    scope.insert((ScopeDao)attrs);
    attrs = scope.getData();
    session.setAttribute("ScopeAttrs", attrs);
    return attrs;
  }

  public Object doUpdate() throws RemoteException, UserException {
    scope.update((ScopeDao)attrs);
    attrs = scope.getData();
    session.setAttribute("ScopeAttrs", attrs);
    return attrs;
  }

  public void doDelete() throws RemoteException, ConstraintViolatedException, UserException {
    scope.delete((ScopeDao)attrs);
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      scope.findNext((ScopeDao)attrs);
      attrs = scope.getData();
      session.setAttribute("ScopeAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      scope.findPrev((ScopeDao)attrs);
      attrs = scope.getData();
      session.setAttribute("ScopeAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException, UserException {
    attrs = session.getAttribute("ScopeAttrs");
    try {
      scope.findByPrimaryKey((ScopeDao)attrs);
      attrs = scope.getData();
    }
    catch (RowNotFoundException ex) {
      try {
	((ScopeDao)attrs).scopeId.setValue(" ");
	scope.findNext((ScopeDao)attrs);
	attrs = scope.getData();
	session.setAttribute("ScopeAttrs", attrs);
      }
      catch (FinderException ex1) {
      }
    }

    return attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException, UserException {
    scope.findByPrimaryKey((ScopeDao)attrs);
    attrs = scope.getData();
    session.setAttribute("ScopeAttrs", attrs);
    return attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return scope.find((ScopeDao)attrs);
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException {
    try {
      return scope.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      scope.setLinkCondition(null, null);
      ((ScopeDao)attrs).scopeId.setValue((String)pkKeys[0]);
      scope.findByPrimaryKey((ScopeDao)attrs);
      this.attrs = (Object)scope.getData();
      String[] strDesc = {((ScopeDao)attrs).scopeDescription.getValue()};
      return strDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
