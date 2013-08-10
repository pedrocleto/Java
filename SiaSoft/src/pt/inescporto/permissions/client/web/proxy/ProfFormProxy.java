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
import pt.inescporto.permissions.ejb.dao.GlbProfFormDao;
import pt.inescporto.permissions.ejb.session.*;

public class ProfFormProxy extends TmplProxy {
  GlbProfForm profForm = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbProfForm)session.getAttribute("GlbProfForm") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbProfForm");
	GlbProfFormHome profFormHome = (GlbProfFormHome)PortableRemoteObject.narrow(objref,
	    GlbProfFormHome.class);
	profForm = profFormHome.create();
	session.setAttribute("GlbProfForm", (Object)profForm);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbProfForm Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbProfFormDao());
      session.setAttribute("GlbProfFormAttrs", (Object)attrs);
    }
    else {
      profForm = (GlbProfForm)session.getAttribute("GlbProfForm");
      this.attrs = session.getAttribute("GlbProfFormAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      profForm.insert((GlbProfFormDao)attrs);
      return (Object)profForm.getData();
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
      profForm.update((GlbProfFormDao)attrs);
      return (Object)profForm.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      profForm.delete((GlbProfFormDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      profForm.findNext((GlbProfFormDao)attrs);
      return (Object)profForm.getData();
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
      profForm.findPrev((GlbProfFormDao)attrs);
      return (Object)profForm.getData();
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
      ((GlbProfFormDao)attrs).profileId.setValue(" ");
      ((GlbProfFormDao)attrs).formId.setValue(" ");
      profForm.findNext((GlbProfFormDao)attrs);
      return (Object)profForm.getData();
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
      profForm.findByPrimaryKey((GlbProfFormDao)attrs);
      return (Object)profForm.getData();
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
      return profForm.find((GlbProfFormDao)attrs);
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
      return profForm.listAll();
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
