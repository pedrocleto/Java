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
import pt.inescporto.permissions.ejb.dao.GlbMenuConfigDao;
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
public class MenuConfigProxy extends TmplProxy {
  GlbMenuConfig menuConfig = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbMenuConfig)session.getAttribute("GlbMenuConfig") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbMenuConfig");
	GlbMenuConfigHome menuConfigHome = (GlbMenuConfigHome)PortableRemoteObject.narrow(objref,
	    GlbMenuConfigHome.class);
	menuConfig = menuConfigHome.create();
	session.setAttribute("GlbMenuConfig", (Object)menuConfig);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbMenuConfig Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbMenuConfigDao());
      session.setAttribute("GlbMenuConfigAttrs", (Object)attrs);
    }
    else {
      menuConfig = (GlbMenuConfig)session.getAttribute("GlbMenuConfig");
      this.attrs = session.getAttribute("GlbMenuConfigAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      menuConfig.insert((GlbMenuConfigDao)attrs);
      return (Object)menuConfig.getData();
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
      menuConfig.update((GlbMenuConfigDao)attrs);
      return (Object)menuConfig.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      menuConfig.delete((GlbMenuConfigDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      menuConfig.findNext((GlbMenuConfigDao)attrs);
      return (Object)menuConfig.getData();
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
      menuConfig.findPrev((GlbMenuConfigDao)attrs);
      return (Object)menuConfig.getData();
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
      ((GlbMenuConfigDao)attrs).configId.setValue(" ");
      menuConfig.findNext((GlbMenuConfigDao)attrs);
      return (Object)menuConfig.getData();
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
      menuConfig.findByPrimaryKey((GlbMenuConfigDao)attrs);
      return (Object)menuConfig.getData();
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
      return menuConfig.find((GlbMenuConfigDao)attrs);
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
      return menuConfig.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbMenuConfigDao)attrs).configId.setValue((String)pkKeys[0]);
      menuConfig.findByPrimaryKey((GlbMenuConfigDao)attrs);
      this.attrs = (Object)menuConfig.getData();
      String[] menuConfigDesc = {((GlbMenuConfigDao)attrs).configName.getValue()};
      return menuConfigDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
