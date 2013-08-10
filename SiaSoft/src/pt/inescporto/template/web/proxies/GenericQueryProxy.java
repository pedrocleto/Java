package pt.inescporto.template.web.proxies;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.TmplHttpQueryMessage;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.ejb.session.*;

public class GenericQueryProxy extends HttpServlet {
  protected TmplHttpQueryMessage msg = null;
  private boolean bDebug = false;

  public void init( ServletConfig config ) throws ServletException {
    super.init( config );
    bDebug = Boolean.getBoolean(config.getInitParameter("debug"));
  }

  public void service( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, java.io.IOException {
    GenericQuery genericQuery = null;

    // get message
    ObjectInputStream inData = new ObjectInputStream( req.getInputStream() );

    resp.setContentType( "application/octet-stream" );

    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream outData = new ObjectOutputStream( byteOut );

    try {
      msg = (TmplHttpQueryMessage)inData.readObject();
    }
    catch( java.lang.ClassNotFoundException e ) {
      if( bDebug )
        e.printStackTrace();
      msg.setReturnCode(TmplMessages.MSG_INITERROR);
    }
    inData.close();

    if( bDebug )
      System.out.println( "Option received : " + msg.getCommand() );

    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if( (GenericQuery)session.getAttribute("GenericQuery") == null ) {
      try {
        Context ic = new InitialContext();
        java.lang.Object objref = ic.lookup( "java:comp/env/ejb/GenericQuery" );
        GenericQueryHome genericQueryHome = (GenericQueryHome) PortableRemoteObject.narrow( objref,
                                                GenericQueryHome.class );
        genericQuery = genericQueryHome.create();
        session.setAttribute("GenericQuery", (Object)genericQuery);
      }
      catch( Exception re ) {
        if( bDebug ) {
          System.out.println( "Couldn't locate GenericQuery Home" );
          re.printStackTrace();
        }
        msg.setReturnCode(TmplMessages.MSG_INITERROR);
      }
    }
    else
      genericQuery = (GenericQuery)session.getAttribute("GenericQuery");

    // set default return code
    msg.setReturnCode(TmplMessages.MSG_OK);

    /* process */
    switch( msg.getCommand() ) {
      case TmplMessages.MSG_LISTALL :             // return a collection *
        try {
          msg.setResult( genericQuery.doQuery(msg.getQueryStmt(), msg.getBinds()) );
        }
        catch( Exception e ) {
          if( bDebug )
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
        }
        break;
      case TmplMessages.MSG_UPDATE :              // query's for insert, update, delete
        try {
          genericQuery.doExecuteQuery( msg.getQueryStmt(), msg.getBinds() );
        }
        catch( Exception e ) {
          if( bDebug )
            e.printStackTrace();
          msg.setReturnCode(TmplMessages.MSG_SQLERROR);
        }
        break;
    }

    // write response
    outData.writeObject((Object)this.msg);
    outData.flush();

    byte[] buf = byteOut.toByteArray();
    resp.setContentLength(buf.length);
    ServletOutputStream servletOut = resp.getOutputStream();
    servletOut.write( buf );
    servletOut.close();
  }
}