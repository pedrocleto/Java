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
import pt.inescporto.permissions.ejb.dao.GlbProfileDao;
import pt.inescporto.permissions.ejb.session.*;

public class ProfileProxy extends TmplProxy {
  GlbProfile profile = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbProfile)session.getAttribute("GlbProfile") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbProfile");
	GlbProfileHome profileHome = (GlbProfileHome)PortableRemoteObject.narrow(objref,
	    GlbProfileHome.class);
	profile = profileHome.create();
	session.setAttribute("GlbProfile", (Object)profile);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbProfile Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbProfileDao());
      session.setAttribute("GlbProfileAttrs", (Object)attrs);
    }
    else {
      profile = (GlbProfile)session.getAttribute("GlbProfile");
      this.attrs = session.getAttribute("GlbProfileAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      profile.insert((GlbProfileDao)attrs);
      return (Object)profile.getData();
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
      profile.update((GlbProfileDao)attrs);
      return (Object)profile.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      profile.delete((GlbProfileDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      profile.findNext((GlbProfileDao)attrs);
      return (Object)profile.getData();
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
      profile.findPrev((GlbProfileDao)attrs);
      return (Object)profile.getData();
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
      ((GlbProfileDao)attrs).profileId.setValue(" ");
      profile.findNext((GlbProfileDao)attrs);
      return (Object)profile.getData();
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
      profile.findByPrimaryKey((GlbProfileDao)attrs);
      return (Object)profile.getData();
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
      return profile.find((GlbProfileDao)attrs);
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
      return profile.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbProfileDao)attrs).profileId.setValue((String)pkKeys[0]);
      profile.findByPrimaryKey((GlbProfileDao)attrs);
      this.attrs = (Object)profile.getData();
      String[] profileDesc = {((GlbProfileDao)attrs).profileName.getValue()};
      return profileDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
