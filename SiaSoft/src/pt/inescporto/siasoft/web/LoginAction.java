package pt.inescporto.siasoft.web;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.ejb.FinderException;
import pt.inescporto.template.web.beans.LoggedUser;
import pt.inescporto.siasoft.comun.ejb.session.UserHome;
import pt.inescporto.siasoft.comun.ejb.session.User;
import pt.inescporto.siasoft.comun.ejb.dao.UserDao;

/**
 *
 * <p>Description: Process Login</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: INESCPorto</p>
 * @author Pedro Ribeiro
 * @version 1.0
 */
public final class LoginAction extends Action {

  // --------------------------------------------------------- Public Methods

  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param actionForm The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet exception occurs
   */


  public ActionForward perform(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException {

    LoggedUser loggedUser;

    // Extract attributes we will need
    Locale locale = getLocale(request);
    MessageResources messages = getResources();

    // Get session
    HttpSession session = request.getSession(true);

    // Test LoginForm
    if (form == null) {
      form = (LoginForm)session.getAttribute(mapping.getAttribute());
      if (form == null) {
        form = new LoginForm();
        session.setAttribute(mapping.getAttribute(), form);
      }
    }
    LoginForm faForm = (LoginForm)form;

    // Get action
    String action = faForm.getAction();

    // Validate data and action
    if (action == null) {
      return (mapping.findForward("loginShow"));
    }
    else if (action.equals(messages.getMessage("login.ok"))) {
      if (isValidUser(faForm, request) == false)
        return (mapping.findForward("loginShow"));
      else
        loggedUser = new LoggedUser();
      loggedUser.setUsrId(faForm.User);
      if (loggedUser.getMenuConfig() == null) {
        ActionErrors errors = new ActionErrors();
        errors.add("User", new ActionError("login.error.user.profile"));
        saveErrors(request, errors);
	return (mapping.findForward("loginShow"));
      }
      loggedUser.setUrlBase(request.getRequestURL().toString().replaceFirst(request.getRequestURI(), request.getContextPath()));
      System.out.println("URL base define as <" + loggedUser.getUrlBase() + ">");
      session.setAttribute("loggedUser", loggedUser);

      System.out.println((session.isNew() ? "STARTING SESSION " : "REUSING SESSION") + "(" + session.getId() + ") TO USER(" + loggedUser.getUsrId() + ") FROM " + request.getRemoteHost());
    }
    else if (action.equals(messages.getMessage("login.cancel"))) {
      faForm.reset(mapping, request);
      return (mapping.findForward("loginExit"));
    }

    faForm.reset(mapping, request);
    return (mapping.findForward("indexShow"));
  }

  /**
   * gets Home of Operator EJB
   */
  private UserHome getOperatorHome() {
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/User");
      UserHome operatorHome = (UserHome)PortableRemoteObject.narrow(objref, UserHome.class);

      return operatorHome;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * validates user
   */
  private boolean isValidUser(LoginForm faForm, HttpServletRequest request) {
    boolean valid = true;

    ActionErrors errors = new ActionErrors();

    if (faForm.User == null) {
      errors.add("User", new ActionError("login.error.user.required"));
    }
    else if (faForm.User.compareTo("") == 0) {
      errors.add("User", new ActionError("login.error.user.required"));
    }
    else {
      try {
        UserHome faHome = getOperatorHome();
        User faSession = faHome.create();
        UserDao operatorDao = new UserDao();
        operatorDao.userID.setValue(faForm.User);
        UserDao op = null;
        faSession.findByPrimaryKey(operatorDao);
        op = faSession.getData();
        if (op == null) {
          errors.add("User", new ActionError("login.error.notvalid"));
        }
        else if (op.passwd.getValue().compareTo(faForm.Password) != 0 || op.passwd.getValue().compareTo("") == 0) {
          errors.add("User", new ActionError("login.error.notvalid"));
        }
      }
      catch (Exception re) {
        errors.add("User", new ActionError("login.error.notvalid"));
      }
    }

    if (!errors.empty()) {
      valid = false;
      saveErrors(request, errors);
    }

    return valid;
  }
}
