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
import pt.inescporto.permissions.ejb.dao.GlbFormDao;
import pt.inescporto.permissions.ejb.session.*;

public class FormProxy extends TmplProxy {
  GlbForm form = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((GlbForm)session.getAttribute("GlbForm") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbForm");
	GlbFormHome formHome = (GlbFormHome)PortableRemoteObject.narrow(objref,
	    GlbFormHome.class);
	form = formHome.create();
	session.setAttribute("GlbForm", (Object)form);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate GlbForm Home");
	re.printStackTrace();
      }

      this.attrs = (Object)(new GlbFormDao());
      session.setAttribute("GlbFormAttrs", (Object)attrs);
    }
    else {
      form = (GlbForm)session.getAttribute("GlbForm");
      this.attrs = session.getAttribute("GlbFormAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException {
    try {
      form.insert((GlbFormDao)attrs);
      return (Object)form.getData();
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
      form.update((GlbFormDao)attrs);
      return (Object)form.getData();
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public void doDelete() throws RemoteException {
    try {
      form.delete((GlbFormDao)attrs);
    }
    catch (RemoteException re) {
      throw re;
    }
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      form.findNext((GlbFormDao)attrs);
      return (Object)form.getData();
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
      form.findPrev((GlbFormDao)attrs);
      return (Object)form.getData();
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
      ((GlbFormDao)attrs).formId.setValue(" ");
      form.findNext((GlbFormDao)attrs);
      return (Object)form.getData();
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
      form.findByPrimaryKey((GlbFormDao)attrs);
      return (Object)form.getData();
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
      return form.find((GlbFormDao)attrs);
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
      return form.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      ((GlbFormDao)attrs).formId.setValue((String)pkKeys[0]);
      form.findByPrimaryKey((GlbFormDao)attrs);
      this.attrs = (Object)form.getData();
      this.desc = new String[] {((GlbFormDao)attrs).formDesc.getValue()};
      return this.desc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
