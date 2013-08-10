package pt.inescporto.siasoft.asq.web.proxy;

import javax.servlet.http.HttpSession;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.siasoft.asq.ejb.dao.SourceDao;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.session.SourceHome;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.siasoft.asq.ejb.session.Source;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class SourceProxy extends TmplProxy {
  HttpSession session = null;
  Source source = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    session = req.getSession();

    // Get home of EJB
    if ((Source)session.getAttribute("Source") == null) {
      try {
        Context ic = new InitialContext();
        java.lang.Object objref = ic.lookup("java:comp/env/ejb/Source");
        SourceHome sourceHome = (SourceHome)PortableRemoteObject.narrow(objref, SourceHome.class);
        source = sourceHome.create();
        session.setAttribute("Source", source);
      }
      catch (Exception re) {
        System.out.println("Couldn't locate Source Home");
        re.printStackTrace();
      }

      attrs = new SourceDao();
      session.setAttribute("SourceAttrs", attrs);
    }
    else {
      source = (Source)session.getAttribute("Source");
      this.attrs = session.getAttribute("SourceAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException, UserException {
    source.insert((SourceDao)attrs);
    attrs = source.getData();
    session.setAttribute("SourceAttrs", attrs);
    return attrs;
  }

  public Object doUpdate() throws RemoteException, UserException {
    source.update((SourceDao)attrs);
    attrs = source.getData();
    session.setAttribute("SourceAttrs", attrs);
    return attrs;
  }

  public void doDelete() throws RemoteException, ConstraintViolatedException, UserException {
    source.delete((SourceDao)attrs);
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      source.findNext((SourceDao)attrs);
      attrs = source.getData();
      session.setAttribute("SourceAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      source.findPrev((SourceDao)attrs);
      attrs = source.getData();
      session.setAttribute("SourceAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException, UserException {
    attrs = session.getAttribute("SourceAttrs");
    try {
      source.findByPrimaryKey((SourceDao)attrs);
      attrs = source.getData();
    }
    catch (RowNotFoundException ex) {
      try {
        ((SourceDao)attrs).sourceId.setValue(" ");
        source.findNext((SourceDao)attrs);
        attrs = source.getData();
        session.setAttribute("SourceAttrs", attrs);
      }
      catch (FinderException ex1) {
      }
    }

    return attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException, UserException {
    source.findByPrimaryKey((SourceDao)attrs);
    attrs = source.getData();
    session.setAttribute("SourceAttrs", attrs);
    return attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return source.find((SourceDao)attrs);
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException, RowNotFoundException, UserException {
    try {
      return source.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      source.setLinkCondition(null, null);
      ((SourceDao)attrs).sourceId.setValue((String)pkKeys[0]);
      source.findByPrimaryKey((SourceDao)attrs);
      this.attrs = (Object)source.getData();
      String[] strDesc = {((SourceDao)attrs).sourceDescription.getValue()};
      return strDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
