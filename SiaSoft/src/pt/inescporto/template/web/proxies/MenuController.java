package pt.inescporto.template.web.proxies;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import pt.inescporto.template.ejb.session.MenuControl;
import pt.inescporto.template.ejb.session.MenuControlHome;
import pt.inescporto.template.web.beans.LoggedUser;
import pt.inescporto.template.utils.beans.FormData;

public class MenuController extends HttpServlet {
  protected boolean bDebug = false;
  protected MenuControl menu = null;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    bDebug = Boolean.getBoolean(config.getInitParameter("debug"));
  }

  public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
    String menuId = null;
    FormData formData = null;
    ServletContext sc = getServletContext();
    RequestDispatcher rd;

    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((MenuControl)session.getAttribute("Menu") == null) {
      try {
	Context ic = new InitialContext();
	java.lang.Object objref = ic.lookup("java:comp/env/ejb/Menu");
	MenuControlHome menuHome = (MenuControlHome)PortableRemoteObject.narrow(objref,
	    MenuControlHome.class);
	menu = menuHome.create();
	session.setAttribute("Menu", (Object)menu);
      }
      catch (Exception re) {
	System.out.println("Couldn't locate Menu Home");
	re.printStackTrace();
        return;
      }
    }
    else
      menu = (MenuControl)session.getAttribute("Menu");

    // Get LoggedUser JavaBean
    LoggedUser usrInfo = (LoggedUser)session.getAttribute("loggedUser");
    if (usrInfo == null) {
      rd = sc.getRequestDispatcher("/SessionEnded.jsp");
      rd.forward(req, resp);
      return;
    }

    // Get request menu identification
    menuId = (String)req.getParameter("MENUID");
    if (menuId == null)
      return;

    // Get FormData JavaBean and save it
    formData = menu.getFormData(usrInfo.getMenuConfig(), menuId);
    session.setAttribute("formData", formData);

    // Forward output to support
    if (formData.getSupport() != null) {
      rd = sc.getRequestDispatcher("/" + formData.getSupport());
      rd.forward(req, resp);
    }
  }
}
