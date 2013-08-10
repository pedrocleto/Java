package pt.inescporto.permissions.client.web.proxy;

import java.rmi.RemoteException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.permissions.ejb.dao.GlbFormIsInRoleDao;
import pt.inescporto.permissions.ejb.session.*;

public class FormIsInRoleProxy extends TmplProxy {
  GlbFormIsInRole formIsInRole = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbFormIsInRole)session.getAttribute("GlbFormIsInRole") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbFormIsInRole");
	GlbFormIsInRoleHome formIsInRoleHome = (GlbFormIsInRoleHome)PortableRemoteObject.narrow(objref,
	    GlbFormIsInRoleHome.class);
	formIsInRole = formIsInRoleHome.create();
	session.setAttribute("GlbFormIsInRole", (Object)formIsInRole);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbFormIsInRole Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbFormIsInRoleDao());
      session.setAttribute("GlbFormIsInRoleAttrs", (Object)attrs);
    }
    else {
      formIsInRole = (GlbFormIsInRole)session.getAttribute("GlbFormIsInRole");
      attrs = session.getAttribute("GlbFormIsInRoleAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      formIsInRole.insert((GlbFormIsInRoleDao)attrs);
      return (Object)formIsInRole.getData();
    }
    catch (DupKeyException dkex) {
      throw dkex;
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doUpdate() throws RemoteException {
    try {
      formIsInRole.update((GlbFormIsInRoleDao)attrs);
      return (Object)formIsInRole.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      formIsInRole.delete((GlbFormIsInRoleDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      formIsInRole.findNext((GlbFormIsInRoleDao)attrs);
      return (Object)formIsInRole.getData();
    }
    catch (RowNotFoundException rnfex) {
      throw new RowNotFoundException();
    }
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException fe) {
    }
    return this.attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      formIsInRole.findPrev((GlbFormIsInRoleDao)attrs);
      return (Object)formIsInRole.getData();
    }
    catch (RowNotFoundException rnfex) {
      throw new RowNotFoundException();
    }
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
    }
    return this.attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException {
    try {
      ((GlbFormIsInRoleDao)attrs).roleId.setValue(" ");
      ((GlbFormIsInRoleDao)attrs).formId.setValue(" ");
      formIsInRole.findNext((GlbFormIsInRoleDao)attrs);
      return (Object)formIsInRole.getData();
    }
    catch (RowNotFoundException rnfex) {
      throw new RowNotFoundException();
    }
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
    }
    return this.attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException {
    try {
      formIsInRole.findByPrimaryKey((GlbFormIsInRoleDao)attrs);
      return (Object)formIsInRole.getData();
    }
    /*    catch( RowNotFoundException rnfex ) {
	  throw new RowNotFoundException();
	}*/
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
    }
    return this.attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return formIsInRole.find((GlbFormIsInRoleDao)attrs);
    }
    catch (RowNotFoundException rnfex) {
      throw new RowNotFoundException();
    }
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException {
    try {
      return formIsInRole.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      return null;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  protected void setLinkCondition(HttpServletRequest req, String linkCondition, Vector binds) {
    HttpSession session = req.getSession();
    session.setAttribute("linkCondition", linkCondition);
    session.setAttribute("binds", binds);
  }
}
