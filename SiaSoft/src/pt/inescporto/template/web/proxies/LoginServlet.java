package pt.inescporto.template.web.proxies;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
  protected boolean   bDebug = false;

  public void init( ServletConfig config ) throws ServletException {
    super.init( config );
    bDebug = Boolean.getBoolean(config.getInitParameter("debug"));
  }

  public void service( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, java.io.IOException {
    ServletContext sc = getServletContext();

    // Get session
    HttpSession session = req.getSession();
    if( session.isNew() ) {
      System.out.println( "New Session." );
      System.out.println( req.getParameter("USER") );
      System.out.println( req.getParameter("PASS") );
    }
    else {
      System.out.println( "Session ID = " + session.getId() + "." );
    }
  }
}
