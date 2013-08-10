package pt.inescporto.siasoft.asq.web.proxy;

import javax.servlet.http.HttpSession;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.siasoft.asq.ejb.dao.LegislationDao;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.session.LegislationHome;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.siasoft.asq.ejb.session.Legislation;
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
public class LegislationProxy extends TmplProxy {
  HttpSession session = null;
  Legislation legislation = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    session = req.getSession();

    // Get home of EJB
    if ((Legislation)session.getAttribute("Legislation") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/Legislation");
	LegislationHome legislationHome = (LegislationHome)PortableRemoteObject.narrow(objref, LegislationHome.class);
	legislation = legislationHome.create();
	session.setAttribute("Legislation", legislation);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate Legislation Home");
	re.printStackTrace();
      }

      attrs = new LegislationDao();
      session.setAttribute("LegislationAttrs", attrs);
    }
    else {
      legislation = (Legislation)session.getAttribute("Legislation");
      this.attrs = session.getAttribute("LegislationAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException, UserException {
    legislation.insert((LegislationDao)attrs);
    attrs = legislation.getData();
    session.setAttribute("LegislationAttrs", attrs);
    return attrs;
  }

  public Object doUpdate() throws RemoteException, UserException {
    legislation.update((LegislationDao)attrs);
    attrs = legislation.getData();
    session.setAttribute("LegislationAttrs", attrs);
    return attrs;
  }

  public void doDelete() throws RemoteException, ConstraintViolatedException, UserException {
    legislation.delete((LegislationDao)attrs);
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      legislation.findNext((LegislationDao)attrs);
      attrs = legislation.getData();
      session.setAttribute("LegislationAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      legislation.findPrev((LegislationDao)attrs);
      attrs = legislation.getData();
      session.setAttribute("LegislationAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException, UserException {
    attrs = session.getAttribute("LegislationAttrs");
    try {
      legislation.findByPrimaryKey((LegislationDao)attrs);
      attrs = legislation.getData();
    }
    catch (RowNotFoundException ex) {
      try {
	((LegislationDao)attrs).legisId.setValue(" ");
	legislation.findNext((LegislationDao)attrs);
	attrs = legislation.getData();
	session.setAttribute("LegislationAttrs", attrs);
      }
      catch (FinderException ex1) {
      }
    }

    return attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException, UserException {
    legislation.findByPrimaryKey((LegislationDao)attrs);
    attrs = legislation.getData();
    session.setAttribute("LegislationAttrs", attrs);
    return attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return legislation.find((LegislationDao)attrs);
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException, RowNotFoundException, UserException {
    try {
      return legislation.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      legislation.setLinkCondition(null, null);
      ((LegislationDao)attrs).legisId.setValue((String)pkKeys[0]);
      legislation.findByPrimaryKey((LegislationDao)attrs);
      this.attrs = (Object)legislation.getData();
      String[] strDesc = {((LegislationDao)attrs).legisDescription.getValue()};
      return strDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
