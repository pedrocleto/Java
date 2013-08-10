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
import pt.inescporto.permissions.ejb.dao.GlbFieldPermDao;
import pt.inescporto.permissions.ejb.session.*;

public class FieldPermProxy extends TmplProxy {
  GlbFieldPerm fieldPerm = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbFieldPerm)session.getAttribute("GlbFieldPerm") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbFieldPerm");
	GlbFieldPermHome fieldPermHome = (GlbFieldPermHome)PortableRemoteObject.narrow(objref, GlbFieldPermHome.class);
	fieldPerm = fieldPermHome.create();
	if (linkCondition != null) {
	  fieldPerm.setLinkCondition(linkCondition, binds);
	}
	session.setAttribute("GlbFieldPerm", (Object)fieldPerm);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbFieldPerm Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbFieldPermDao());
      if (linkCondition != null && !(msg.getAsMaster())) {
	((GlbFieldPermDao)attrs).roleId.setLinkKey();
	((GlbFieldPermDao)attrs).formId.setLinkKey();
      }
      session.setAttribute("GlbFieldPermDao", (Object)attrs);
    }
    else {
      fieldPerm = (GlbFieldPerm)session.getAttribute("GlbFieldPerm");
      attrs = (GlbFieldPermDao)session.getAttribute("GlbFieldPermDao");
      if (linkCondition != null && !(msg.getAsMaster())) {
	((GlbFieldPermDao)attrs).roleId.setLinkKey();
	((GlbFieldPermDao)attrs).formId.setLinkKey();
	try {
	  fieldPerm.setLinkCondition(linkCondition, binds);
	}
	catch (Exception e) {
	  e.printStackTrace();
	}
      }
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      fieldPerm.insert((GlbFieldPermDao)attrs);
      return (Object)fieldPerm.getData();
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
      fieldPerm.update((GlbFieldPermDao)attrs);
      return (Object)fieldPerm.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      fieldPerm.delete((GlbFieldPermDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      fieldPerm.findNext((GlbFieldPermDao)attrs);
      return (Object)fieldPerm.getData();
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
      fieldPerm.findPrev((GlbFieldPermDao)attrs);
      return (Object)fieldPerm.getData();
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
      fieldPerm.findNext(new GlbFieldPermDao(" ", " ", " "));
      return (Object)fieldPerm.getData();
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
      fieldPerm.findByPrimaryKey((GlbFieldPermDao)attrs);
      return (Object)fieldPerm.getData();
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
      return fieldPerm.find((GlbFieldPermDao)attrs);
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
      return fieldPerm.listAll();
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
