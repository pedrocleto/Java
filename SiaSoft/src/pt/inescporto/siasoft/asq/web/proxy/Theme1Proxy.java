package pt.inescporto.siasoft.asq.web.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.naming.InitialContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.dao.Theme1Dao;
import pt.inescporto.siasoft.asq.ejb.session.Theme1;
import pt.inescporto.siasoft.asq.ejb.session.Theme1Home;
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
public class Theme1Proxy extends TmplProxy {
  HttpSession session = null;
  Theme1 theme1 = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    session = req.getSession();

    // Get home of EJB
    if ((Theme1)session.getAttribute("Theme1") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/Theme1");
	Theme1Home theme1Home = (Theme1Home)PortableRemoteObject.narrow(objref, Theme1Home.class);
	theme1 = (linkCondition == null) ? theme1Home.create() : theme1Home.create(linkCondition, binds);
	session.setAttribute("Theme1", theme1);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate Theme1 Home");
	re.printStackTrace();
      }

      attrs = new Theme1Dao();
      if (linkCondition != null)
	((Theme1Dao)attrs).themeId.setLinkKey();
      else
	((Theme1Dao)attrs).themeId.resetLinkKey();

      session.setAttribute("Theme1Attrs", attrs);
    }
    else {
      theme1 = (Theme1)session.getAttribute("Theme1");
      this.attrs = session.getAttribute("Theme1Attrs");
      if (linkCondition != null)
	((Theme1Dao)attrs).themeId.setLinkKey();
      else
	((Theme1Dao)attrs).themeId.resetLinkKey();
      try {
        theme1.setLinkCondition(linkCondition, binds);
      }
      catch (RemoteException ex) {
      }
      session.setAttribute("Theme1Attrs", attrs);
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException, UserException {
    theme1.insert((Theme1Dao)attrs);
    attrs = theme1.getData();
    session.setAttribute("Theme1Attrs", attrs);
    return attrs;
  }

  public Object doUpdate() throws RemoteException, UserException {
    theme1.update((Theme1Dao)attrs);
    attrs = theme1.getData();
    session.setAttribute("Theme1Attrs", attrs);
    return attrs;
  }

  public void doDelete() throws RemoteException, ConstraintViolatedException, UserException {
    theme1.delete((Theme1Dao)attrs);
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      theme1.findNext((Theme1Dao)attrs);
      attrs = theme1.getData();
      session.setAttribute("Theme1Attrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      theme1.findPrev((Theme1Dao)attrs);
      attrs = theme1.getData();
      session.setAttribute("Theme1Attrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException, UserException {
    attrs = session.getAttribute("Theme1Attrs");
    try {
      theme1.findByPrimaryKey((Theme1Dao)attrs);
      attrs = theme1.getData();
    }
    catch (RowNotFoundException ex) {
      try {
	if (linkCondition == null)
	  ((Theme1Dao)attrs).themeId.setValue(" ");
	((Theme1Dao)attrs).theme1Id.setValue(" ");
	theme1.findNext((Theme1Dao)attrs);
	attrs = theme1.getData();
	session.setAttribute("Theme1Attrs", attrs);
      }
      catch (FinderException ex1) {
      }
    }

    return attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException, UserException {
    theme1.findByPrimaryKey((Theme1Dao)attrs);
    attrs = theme1.getData();
    session.setAttribute("Theme1Attrs", attrs);
    return attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return theme1.find((Theme1Dao)attrs);
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException, RowNotFoundException, UserException {
    try {
      return theme1.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      theme1.setLinkCondition(null, null);
      ((Theme1Dao)attrs).themeId.setValue((String)pkKeys[0]);
      ((Theme1Dao)attrs).theme1Id.setValue((String)pkKeys[1]);
      theme1.findByPrimaryKey((Theme1Dao)attrs);
      this.attrs = (Object)theme1.getData();
      String[] strDesc = {((Theme1Dao)attrs).description.getValue()};
      return strDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
