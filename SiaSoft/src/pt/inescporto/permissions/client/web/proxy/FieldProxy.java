package pt.inescporto.permissions.client.web.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
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
import pt.inescporto.permissions.ejb.dao.GlbFieldDao;
import pt.inescporto.permissions.ejb.session.*;

public class FieldProxy extends TmplProxy {
  GlbField field = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbField)session.getAttribute("GlbField") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbField");
	GlbFieldHome fieldHome = (GlbFieldHome)PortableRemoteObject.narrow(objref,
	    GlbFieldHome.class);
	field = fieldHome.create();
	session.setAttribute("GlbField", (Object)field);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbField Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbFieldDao());
      session.setAttribute("GlbFieldAttrs", (Object)attrs);
    }
    else {
      field = (GlbField)session.getAttribute("GlbField");
      this.attrs = session.getAttribute("GlbFieldAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      field.insert((GlbFieldDao)attrs);
      return (Object)field.getData();
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
      field.update((GlbFieldDao)attrs);
      return (Object)field.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      field.delete((GlbFieldDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      field.findNext((GlbFieldDao)attrs);
      return (Object)field.getData();
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
      field.findPrev((GlbFieldDao)attrs);
      return (Object)field.getData();
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
      ((GlbFieldDao)attrs).fieldId.setValue(" ");
      field.findNext((GlbFieldDao)attrs);
      return (Object)field.getData();
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
      field.findByPrimaryKey((GlbFieldDao)attrs);
      return (Object)field.getData();
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
      return field.find((GlbFieldDao)attrs);
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
      return field.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbFieldDao)attrs).fieldId.setValue((String)pkKeys[0]);
      field.findByPrimaryKey((GlbFieldDao)attrs);
      this.attrs = (Object)field.getData();
      String[] fieldDesc = {((GlbFieldDao)attrs).fieldDesc.getValue()};
      return fieldDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
