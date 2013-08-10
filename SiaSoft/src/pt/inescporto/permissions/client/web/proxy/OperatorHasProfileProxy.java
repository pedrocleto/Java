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
import pt.inescporto.permissions.ejb.dao.GlbOperatorHasProfileDao;
import pt.inescporto.permissions.ejb.session.*;

public class OperatorHasProfileProxy extends TmplProxy {
  GlbOperatorHasProfile operatorHasProfile = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbOperatorHasProfile)session.getAttribute("GlbOperatorHasProfile") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbOperatorHasProfile");
	GlbOperatorHasProfileHome operatorHasProfileHome = (GlbOperatorHasProfileHome)PortableRemoteObject.narrow(objref,
	    GlbOperatorHasProfileHome.class);
	operatorHasProfile = operatorHasProfileHome.create();
	session.setAttribute("GlbOperatorHasProfile", (Object)operatorHasProfile);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbOperatorHasProfile Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbOperatorHasProfileDao());
      session.setAttribute("GlbOperatorHasProfileAttrs", (Object)attrs);
    }
    else {
      operatorHasProfile = (GlbOperatorHasProfile)session.getAttribute("GlbOperatorHasProfile");
      this.attrs = session.getAttribute("GlbOperatorHasProfileAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      operatorHasProfile.insert((GlbOperatorHasProfileDao)attrs);
      return (Object)operatorHasProfile.getData();
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
      operatorHasProfile.update((GlbOperatorHasProfileDao)attrs);
      return (Object)operatorHasProfile.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      operatorHasProfile.delete((GlbOperatorHasProfileDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      operatorHasProfile.findNext((GlbOperatorHasProfileDao)attrs);
      return (Object)operatorHasProfile.getData();
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
      operatorHasProfile.findPrev((GlbOperatorHasProfileDao)attrs);
      return (Object)operatorHasProfile.getData();
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
      ((GlbOperatorHasProfileDao)attrs).operatorId.setValue(" ");
      ((GlbOperatorHasProfileDao)attrs).profileId.setValue(" ");
      operatorHasProfile.findNext((GlbOperatorHasProfileDao)attrs);
      return (Object)operatorHasProfile.getData();
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
      operatorHasProfile.findByPrimaryKey((GlbOperatorHasProfileDao)attrs);
      return (Object)operatorHasProfile.getData();
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
      return operatorHasProfile.find((GlbOperatorHasProfileDao)attrs);
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
      return operatorHasProfile.listAll();
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
