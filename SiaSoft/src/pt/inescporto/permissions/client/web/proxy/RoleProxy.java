package pt.inescporto.permissions.client.web.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
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
import pt.inescporto.permissions.ejb.dao.GlbRoleDao;
import pt.inescporto.permissions.ejb.session.*;

public class RoleProxy extends TmplProxy {
  GlbRole role = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbRole)session.getAttribute("GlbRole") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbRole");
	GlbRoleHome roleHome = (GlbRoleHome)PortableRemoteObject.narrow(objref,
	    GlbRoleHome.class);
	role = roleHome.create();
	session.setAttribute("GlbRole", (Object)role);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbRole Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbRoleDao());
      session.setAttribute("GlbRoleAttrs", (Object)attrs);
    }
    else {
      role = (GlbRole)session.getAttribute("GlbRole");
      this.attrs = session.getAttribute("GlbRoleAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      role.insert((GlbRoleDao)attrs);
      return (Object)role.getData();
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
      role.update((GlbRoleDao)attrs);
      return (Object)role.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      role.delete((GlbRoleDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      role.findNext((GlbRoleDao)attrs);
      return (Object)role.getData();
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
      role.findPrev((GlbRoleDao)attrs);
      return (Object)role.getData();
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
      ((GlbRoleDao)attrs).roleId.setValue(" ");
      role.findNext((GlbRoleDao)attrs);
      return (Object)role.getData();
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
      role.findByPrimaryKey((GlbRoleDao)attrs);
      return (Object)role.getData();
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
      return role.find((GlbRoleDao)attrs);
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
      return role.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbRoleDao)attrs).roleId.setValue((String)pkKeys[0]);
      role.findByPrimaryKey((GlbRoleDao)attrs);
      this.attrs = (Object)role.getData();
      String[] roleDesc = {((GlbRoleDao)attrs).roleName.getValue()};
      return roleDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
