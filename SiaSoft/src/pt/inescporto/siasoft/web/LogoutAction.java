package pt.inescporto.siasoft.web;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;

/**
 *
 * <p>Description: Processes Logout</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: INESCPorto</p>
 * @author Pedro Ribeiro
 * @version 1.0
 */
public final class LogoutAction extends Action {

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

    // Extract attributes we will need
    Locale locale = getLocale(request);
    MessageResources messages = getResources();

    // Get session
    HttpSession session = request.getSession();

    //User Log Control
    /*
         String User = (String)session.getAttribute("logedUser");
         if (User==null)
      return (mapping.findForward("loginShow"));
     */
    //****************

     // Test LogoutForm
     if (form == null) {
       form = (LogoutForm)session.getAttribute(mapping.getAttribute());
       if (form == null) {
         form = new LogoutForm();
         session.setAttribute(mapping.getAttribute(), form);
       }
     }
    LogoutForm faForm = (LogoutForm)form;

    // Get action
    String action = faForm.getAction();

    // Validate data and action
    if (action == null) {
      return (mapping.findForward("logoutShow"));
    }
    else {

      removeSessionAttrs(session);

      session.invalidate();

      return (mapping.findForward("loginShow"));
    }

  }

  /**
   * removes session attributes
   */
  private void removeSessionAttrs(HttpSession session) {
    String atr;

    session.removeAttribute("loggedUser");

    Enumeration e = session.getAttributeNames();
    // Remove all session attributes

    while (e.hasMoreElements()) {
      try {
        atr = (String)e.nextElement();
        //System.out.println(atr);
        session.removeAttribute(atr);
        e = session.getAttributeNames();
      }
      catch (Exception x) {
        x.printStackTrace();
      }
    }
  }

}
