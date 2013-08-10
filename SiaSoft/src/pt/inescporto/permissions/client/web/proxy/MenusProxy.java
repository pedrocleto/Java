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
import pt.inescporto.permissions.ejb.dao.GlbMenuDao;
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
public class MenusProxy extends TmplProxy {
  GlbMenu menu = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbMenu)session.getAttribute("GlbMenu") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbMenu");
	GlbMenuHome menuHome = (GlbMenuHome)PortableRemoteObject.narrow(objref, GlbMenuHome.class);
	menu = menuHome.create();
	session.setAttribute("GlbMenu", (Object)menu);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbMenu Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbMenuDao());
      session.setAttribute("GlbMenuAttrs", (Object)attrs);
    }
    else {
      menu = (GlbMenu)session.getAttribute("GlbMenu");
      this.attrs = session.getAttribute("GlbMenuAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      menu.insert((GlbMenuDao)attrs);
      return (Object)menu.getData();
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
      menu.update((GlbMenuDao)attrs);
      return (Object)menu.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      menu.delete((GlbMenuDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      menu.findNext((GlbMenuDao)attrs);
      return (Object)menu.getData();
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
      menu.findPrev((GlbMenuDao)attrs);
      return (Object)menu.getData();
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
      ((GlbMenuDao)attrs).menuId.setValue(" ");
      menu.findNext((GlbMenuDao)attrs);
      return (Object)menu.getData();
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
      menu.findByPrimaryKey((GlbMenuDao)attrs);
      return (Object)menu.getData();
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
      return menu.find((GlbMenuDao)attrs);
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
      return menu.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbMenuDao)attrs).menuId.setValue((String)pkKeys[0]);
      menu.findByPrimaryKey((GlbMenuDao)attrs);
      this.attrs = (Object)menu.getData();
      String[] menuDesc = {
	  ((GlbMenuDao)attrs).menuName.getValue()};
      return menuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
