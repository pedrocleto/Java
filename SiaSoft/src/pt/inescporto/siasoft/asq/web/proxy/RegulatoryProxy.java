package pt.inescporto.siasoft.asq.web.proxy;

import javax.servlet.http.HttpSession;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.session.RegulatoryHome;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.siasoft.asq.ejb.session.Regulatory;
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
public class RegulatoryProxy extends TmplProxy {
  HttpSession session = null;
  Regulatory reg = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    session = req.getSession();

    // Get home of EJB
    if ((Regulatory)session.getAttribute("Regulatory") == null) {
      try {
        Context ic = new InitialContext();
        java.lang.Object objref = ic.lookup("java:comp/env/ejb/Regulatory");
        RegulatoryHome regHome = (RegulatoryHome)PortableRemoteObject.narrow(objref, RegulatoryHome.class);
        reg = regHome.create();
        session.setAttribute("Regulatory", reg);
      }
      catch (Exception re) {
        System.out.println("Couldn't locate Regulatory Home");
        re.printStackTrace();
      }

      attrs = new RegulatoryDao();
      session.setAttribute("RegulatoryAttrs", attrs);
    }
    else {
      reg = (Regulatory)session.getAttribute("Regulatory");
      this.attrs = session.getAttribute("RegulatoryAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException, UserException {
    reg.insert((RegulatoryDao)attrs);
    attrs = reg.getData();
    session.setAttribute("RegulatoryAttrs", attrs);
    return attrs;
  }

  public Object doUpdate() throws RemoteException, UserException {
    reg.update((RegulatoryDao)attrs);
    attrs = reg.getData();
    session.setAttribute("RegulatoryAttrs", attrs);
    return attrs;
  }

  public void doDelete() throws RemoteException, ConstraintViolatedException, UserException {
    reg.delete((RegulatoryDao)attrs);
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      reg.findNext((RegulatoryDao)attrs);
      attrs = reg.getData();
      session.setAttribute("RegulatoryAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      reg.findPrev((RegulatoryDao)attrs);
      attrs = reg.getData();
      session.setAttribute("RegulatoryAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException, UserException {
    attrs = session.getAttribute("RegulatoryAttrs");
    try {
      reg.findByPrimaryKey((RegulatoryDao)attrs);
      attrs = reg.getData();
    }
    catch (RowNotFoundException ex) {
      try {
        ((RegulatoryDao)attrs).regId.setValue(" ");
        reg.findNext((RegulatoryDao)attrs);
        attrs = reg.getData();
        session.setAttribute("RegulatoryAttrs", attrs);
      }
      catch (FinderException ex1) {
      }
    }

    return attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException, UserException {
    reg.findByPrimaryKey((RegulatoryDao)attrs);
    attrs = reg.getData();
    session.setAttribute("RegulatoryAttrs", attrs);
    return attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return reg.find((RegulatoryDao)attrs);
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException {
    try {
      return reg.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      reg.setLinkCondition(null, null);
      ((RegulatoryDao)attrs).regId.setValue((String)pkKeys[0]);
      reg.findByPrimaryKey((RegulatoryDao)attrs);
      this.attrs = (Object)reg.getData();
      String[] strDesc = {((RegulatoryDao)attrs).name.getValue()};
      return strDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
