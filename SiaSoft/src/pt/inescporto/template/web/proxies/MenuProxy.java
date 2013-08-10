package pt.inescporto.template.web.proxies;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import pt.inescporto.template.web.util.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.ejb.session.MenuControl;
import pt.inescporto.template.ejb.session.MenuControlHome;
import pt.inescporto.template.web.beans.LoggedUser;

public class MenuProxy extends HttpServlet {
  protected boolean bDebug = true;
  protected Object msgObject = null;
  protected MenuControl menu = null;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    bDebug = Boolean.getBoolean(config.getInitParameter("debug"));
  }

  public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
    // read data from client
    if (readData(req)) {
      try {
        // process data
        processMessage(req, resp);
      }
      catch (Exception ex) {
        if (bDebug) {
          System.out.println("Proxy Processing failure...");
          ex.printStackTrace();
        }
      }

      // write data back to client
      if (!writeData(resp))
        System.out.println("Proxy write failure...");
    }
    else
      System.out.println("Proxy read failure...");
  }

  /**
   * read message from client
   */
  public boolean readData(HttpServletRequest req) {
    boolean result = true;
    ObjectInputStream inData = null;

    try {
      inData = new ObjectInputStream(req.getInputStream());
      msgObject = inData.readObject();
      inData.close();
    }
    catch (Exception ex) {
      result = false;
      if (bDebug)
        ex.printStackTrace();
    }

    return result;
  }

  /**
   * write msg back to client
   */
  public boolean writeData(HttpServletResponse resp) {
    boolean result = true;
    ByteArrayOutputStream byteOut = null;
    ObjectOutputStream outData = null;

    try {
      byteOut = new ByteArrayOutputStream();
      outData = new ObjectOutputStream(byteOut);
      resp.setContentType("application/octet-stream");
      outData.writeObject(msgObject);
      outData.flush();
      byte[] buf = byteOut.toByteArray();
      resp.setContentLength(buf.length);
      ServletOutputStream servletOut = resp.getOutputStream();
      servletOut.write(buf);
      servletOut.close();
    }
    catch (Exception ex) {
      result = false;
      if (bDebug)
        ex.printStackTrace();
    }
    return result;
  }

  public void processMessage(HttpServletRequest req, HttpServletResponse resp) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if ((MenuControl)session.getAttribute("Menu") == null) {
      try {
        Context ic = new InitialContext();
        java.lang.Object objref = ic.lookup("java:comp/env/ejb/Menu");
        MenuControlHome menuHome = (MenuControlHome)PortableRemoteObject.narrow(objref, MenuControlHome.class);
        menu = menuHome.create();
        session.setAttribute("Menu", (Object)menu);
      }
      catch (Exception re) {
        System.out.println("Couldn't locate Menu Home");
        re.printStackTrace();
      }
    }
    else
      menu = (MenuControl)session.getAttribute("Menu");

    // process message
    if (menu != null) {
      TmplHttpMenuMsg msg = (TmplHttpMenuMsg)msgObject;

      // Get user information
      LoggedUser usrInfo = (LoggedUser)session.getAttribute("loggedUser");

      switch (msg.getCommand()) {
        case TmplMessages.MENU_GET_ROOT:
          try {
            Vector menuList = menu.getRootNodes(usrInfo.getMenuConfig());
            msg.setResult(menuList);
          }
          catch (RemoteException rex) {
          }
          break;
        case TmplMessages.MENU_GET_CHILDREN:
          try {
            Vector menuList = menu.getChildrenForNode(usrInfo.getMenuConfig(), msg.getSearchId());
            msg.setResult(menuList);
          }
          catch (RemoteException rex) {
          }
          break;
        case TmplMessages.MENU_GET_USERINFO:
          msg.setUsrInfo(usrInfo);
          break;
      }
    }
  }
}
