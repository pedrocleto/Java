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
import pt.inescporto.permissions.ejb.dao.GlbMenuItemDao;
import pt.inescporto.permissions.ejb.session.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author jap
 * @version 1.0
 */
public class MenuItemProxy extends TmplProxy {
  GlbMenuItem menuItem = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbMenuItem)session.getAttribute("GlbMenuItem") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbMenuItem");
	GlbMenuItemHome menuItemHome = (GlbMenuItemHome)PortableRemoteObject.narrow(objref, GlbMenuItemHome.class);
	menuItem = menuItemHome.create();
	session.setAttribute("GlbMenuItem", (Object)menuItem);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbMenuItem Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbMenuItemDao());
      session.setAttribute("GlbMenuItemAttrs", (Object)attrs);
    }
    else {
      menuItem = (GlbMenuItem)session.getAttribute("GlbMenuItem");
      this.attrs = session.getAttribute("GlbMenuItemAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      menuItem.insert((GlbMenuItemDao)attrs);
      return (Object)menuItem.getData();
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
      menuItem.update((GlbMenuItemDao)attrs);
      return (Object)menuItem.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      menuItem.delete((GlbMenuItemDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      menuItem.findNext((GlbMenuItemDao)attrs);
      return (Object)menuItem.getData();
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
      menuItem.findPrev((GlbMenuItemDao)attrs);
      return (Object)menuItem.getData();
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
      ((GlbMenuItemDao)attrs).configId.setValue(" ");
      menuItem.findNext((GlbMenuItemDao)attrs);
      return (Object)menuItem.getData();
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
      menuItem.findByPrimaryKey((GlbMenuItemDao)attrs);
      return (Object)menuItem.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
    }
    return this.attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return menuItem.find((GlbMenuItemDao)attrs);
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
      return menuItem.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbMenuItemDao)attrs).configId.setValue((String)pkKeys[0]);
      menuItem.findByPrimaryKey((GlbMenuItemDao)attrs);
      this.attrs = (Object)menuItem.getData();
      String[] menuItemDesc = {
	  ((GlbMenuItemDao)attrs).itemId.getValue()};
      return menuItemDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
