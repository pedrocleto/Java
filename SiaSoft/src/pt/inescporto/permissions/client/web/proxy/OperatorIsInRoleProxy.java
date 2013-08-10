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
import pt.inescporto.permissions.ejb.dao.GlbOperatorIsInRoleDao;
import pt.inescporto.permissions.ejb.session.*;

public class OperatorIsInRoleProxy extends TmplProxy {
  GlbOperatorIsInRole operatorIsInRole = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbOperatorIsInRole)session.getAttribute("GlbOperatorIsInRole") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbOperatorIsInRole");
	GlbOperatorIsInRoleHome operatorIsInRoleHome = (GlbOperatorIsInRoleHome)PortableRemoteObject.narrow(objref,
	    GlbOperatorIsInRoleHome.class);
	operatorIsInRole = operatorIsInRoleHome.create();
	session.setAttribute("GlbOperatorIsInRole", (Object)operatorIsInRole);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbOperatorIsInRole Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbOperatorIsInRoleDao());
      session.setAttribute("GlbOperatorIsInRoleAttrs", (Object)attrs);
    }
    else {
      operatorIsInRole = (GlbOperatorIsInRole)session.getAttribute("GlbOperatorIsInRole");
      this.attrs = session.getAttribute("GlbOperatorIsInRoleAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      operatorIsInRole.insert((GlbOperatorIsInRoleDao)attrs);
      return (Object)operatorIsInRole.getData();
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
      operatorIsInRole.update((GlbOperatorIsInRoleDao)attrs);
      return (Object)operatorIsInRole.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      operatorIsInRole.delete((GlbOperatorIsInRoleDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      operatorIsInRole.findNext((GlbOperatorIsInRoleDao)attrs);
      return (Object)operatorIsInRole.getData();
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
      operatorIsInRole.findPrev((GlbOperatorIsInRoleDao)attrs);
      return (Object)operatorIsInRole.getData();
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
      ((GlbOperatorIsInRoleDao)attrs).operatorId.setValue(" ");
      ((GlbOperatorIsInRoleDao)attrs).roleId.setValue(" ");
      operatorIsInRole.findNext((GlbOperatorIsInRoleDao)attrs);
      return (Object)operatorIsInRole.getData();
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
      operatorIsInRole.findByPrimaryKey((GlbOperatorIsInRoleDao)attrs);
      return (Object)operatorIsInRole.getData();
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
      return operatorIsInRole.find((GlbOperatorIsInRoleDao)attrs);
    }
    /*    catch( RowNotFoundException rnfex ) {
	  throw new RowNotFoundException();
	}*/
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException {
    try {
      return operatorIsInRole.listAll();
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
}
