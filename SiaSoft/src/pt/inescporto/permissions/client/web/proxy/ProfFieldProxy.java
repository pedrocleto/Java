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
import pt.inescporto.permissions.ejb.dao.GlbProfFieldDao;
import pt.inescporto.permissions.ejb.session.*;

public class ProfFieldProxy extends TmplProxy {
  GlbProfField profField = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbProfField)session.getAttribute("GlbProfField") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbProfField");
	GlbProfFieldHome profFieldHome = (GlbProfFieldHome)PortableRemoteObject.narrow(objref,
	    GlbProfFieldHome.class);
	profField = profFieldHome.create();
	if (linkCondition != null) {
	  profField.setLinkCondition(linkCondition, binds);
	}
	session.setAttribute("GlbProfField", (Object)profField);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbProfField Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbProfFieldDao());
      if (linkCondition != null) {
	((GlbProfFieldDao)attrs).profileId.setLinkKey();
	((GlbProfFieldDao)attrs).formId.setLinkKey();
      }
      session.setAttribute("GlbProfFieldAttrs", (Object)attrs);
    }
    else {
      profField = (GlbProfField)session.getAttribute("GlbProfField");
      attrs = session.getAttribute("GlbProfFieldAttrs");
      if (linkCondition != null) {
	((GlbProfFieldDao)attrs).profileId.setLinkKey();
	((GlbProfFieldDao)attrs).formId.setLinkKey();
	try {
	  profField.setLinkCondition(linkCondition, binds);
	}
	catch (Exception e) {
	  e.printStackTrace();
	}
      }
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      profField.insert((GlbProfFieldDao)attrs);
      return (Object)profField.getData();
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
      profField.update((GlbProfFieldDao)attrs);
      return (Object)profField.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      profField.delete((GlbProfFieldDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      profField.findNext((GlbProfFieldDao)attrs);
      return (Object)profField.getData();
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
      profField.findPrev((GlbProfFieldDao)attrs);
      return (Object)profField.getData();
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
      ((GlbProfFieldDao)attrs).profileId.setValue(" ");
      ((GlbProfFieldDao)attrs).formId.setValue(" ");
      ((GlbProfFieldDao)attrs).fieldId.setValue(" ");
      profField.findNext((GlbProfFieldDao)attrs);
      return (Object)profField.getData();
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
      profField.findByPrimaryKey((GlbProfFieldDao)attrs);
      return (Object)profField.getData();
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
      return profField.find((GlbProfFieldDao)attrs);
    }
    catch (RowNotFoundException rnfex) {
      throw new RowNotFoundException();
    }
    catch (RemoteException re) {
      throw re;
    }
    catch (FinderException re) {
    }
    return null;
  }

  public Collection doListAll() throws RemoteException {
    try {
      return profField.listAll();
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
