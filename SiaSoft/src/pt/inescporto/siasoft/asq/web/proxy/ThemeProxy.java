package pt.inescporto.siasoft.asq.web.proxy;

import javax.servlet.http.HttpSession;
import pt.inescporto.template.web.proxies.TmplProxy;
import pt.inescporto.siasoft.asq.ejb.dao.ThemeDao;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.session.ThemeHome;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.naming.InitialContext;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.siasoft.asq.ejb.session.Theme;
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
public class ThemeProxy extends TmplProxy {
  HttpSession session = null;
  Theme theme = null;

  public void doInitialize(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    session = req.getSession();

    // Get home of EJB
    if ((Theme)session.getAttribute("Theme") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/Theme");
	ThemeHome themeHome = (ThemeHome)PortableRemoteObject.narrow(objref, ThemeHome.class);
	theme = themeHome.create();
	session.setAttribute("Theme", theme);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate Theme Home");
	re.printStackTrace();
      }

      attrs = new ThemeDao();
      session.setAttribute("ThemeAttrs", attrs);
    }
    else {
      theme = (Theme)session.getAttribute("Theme");
      this.attrs = session.getAttribute("ThemeAttrs");
    }
  }

  public Object doInsert() throws DupKeyException, RemoteException, UserException {
    theme.insert((ThemeDao)attrs);
    attrs = theme.getData();
    session.setAttribute("ThemeAttrs", attrs);
    return attrs;
  }

  public Object doUpdate() throws RemoteException, UserException {
    theme.update((ThemeDao)attrs);
    attrs = theme.getData();
    session.setAttribute("ThemeAttrs", attrs);
    return attrs;
  }

  public void doDelete() throws RemoteException, ConstraintViolatedException, UserException {
    theme.delete((ThemeDao)attrs);
  }

  public Object doForward() throws RowNotFoundException, RemoteException {
    try {
      theme.findNext((ThemeDao)attrs);
      attrs = theme.getData();
      session.setAttribute("ThemeAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doBackward() throws RemoteException, RowNotFoundException {
    try {
      theme.findPrev((ThemeDao)attrs);
      attrs = theme.getData();
      session.setAttribute("ThemeAttrs", attrs);
    }
    catch (FinderException fe) {
    }

    return attrs;
  }

  public Object doFindFirst() throws RemoteException, RowNotFoundException, UserException {
    attrs = session.getAttribute("ThemeAttrs");
    try {
      theme.findByPrimaryKey((ThemeDao)attrs);
      attrs = theme.getData();
    }
    catch (RowNotFoundException ex) {
      try {
	((ThemeDao)attrs).themeId.setValue(" ");
	theme.findNext((ThemeDao)attrs);
	attrs = theme.getData();
	session.setAttribute("ThemeAttrs", attrs);
      }
      catch (FinderException ex1) {
      }
    }

    return attrs;
  }

  public Object doFindPk() throws RemoteException, RowNotFoundException, UserException {
    theme.findByPrimaryKey((ThemeDao)attrs);
    attrs = theme.getData();
    session.setAttribute("ThemeAttrs", attrs);
    return attrs;
  }

  public Collection doFind() throws RemoteException, RowNotFoundException {
    try {
      return theme.find((ThemeDao)attrs);
    }
    catch (FinderException re) {
      return null;
    }
  }

  public Collection doListAll() throws RemoteException {
    try {
      return theme.listAll();
    }
    catch (RemoteException rex) {
      throw rex;
    }
  }

  public String[] doLookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      theme.setLinkCondition(null, null);
      ((ThemeDao)attrs).themeId.setValue((String)pkKeys[0]);
      theme.findByPrimaryKey((ThemeDao)attrs);
      this.attrs = (Object)theme.getData();
      String[] strDesc = {((ThemeDao)attrs).description.getValue()};
      return strDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
