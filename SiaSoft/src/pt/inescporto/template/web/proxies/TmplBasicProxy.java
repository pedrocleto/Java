package pt.inescporto.template.web.proxies;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class TmplBasicProxy extends HttpServlet {
  protected boolean   bDebug = false;
  protected Object    msgObject = null;
  protected Object    attrs = null;

  public void init( ServletConfig config ) throws ServletException {
    super.init( config );
    bDebug = true;//Boolean.getBoolean(config.getInitParameter("debug"));
  }

  public void service( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, java.io.IOException {
    // reset some values
    attrs = null;

    /**
     * read message from client
     */
    ObjectInputStream inData = new ObjectInputStream( req.getInputStream() );
    resp.setContentType( "application/octet-stream" );
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream outData = new ObjectOutputStream( byteOut );
    try {
      msgObject = inData.readObject();
    }
    catch( java.lang.ClassNotFoundException e ) {
      if( bDebug )
        e.printStackTrace();
    }
    inData.close();

    /**
     * call message received
     */
    try {
      messageReceived();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }

    /**
     * call initialization method
     */
    try {
      doInitialize( req, resp );
      if( attrs == null && bDebug )
        System.out.println( "Attribute attrs not initialized (" + getClass().getName() + ")..." );
    }
    catch( Exception ex ) {
      if( bDebug ) {
        System.out.println( "Initialization failure..." );
        ex.printStackTrace();
      }
    }

    /**
     * call abstract method processMessage for message processing
     * NOTE: Must set correct value in <code>msgObject</code> for client notification
     */
    try {
      procMsg( req, resp );
    }
    catch( Exception ex ) {
      if( bDebug ) {
        System.out.println( "Proxy Processing failure..." );
        ex.printStackTrace();
      }
    }

    /**
     * write msg back to client
     */
    outData.writeObject(msgObject);
    outData.flush();

    byte[] buf = byteOut.toByteArray();
    resp.setContentLength(buf.length);
    ServletOutputStream servletOut = resp.getOutputStream();
    servletOut.write( buf );
    servletOut.close();
  }

  protected void messageReceived() {
  }

  protected abstract void doInitialize( HttpServletRequest req, HttpServletResponse resp );
  protected abstract void procMsg( HttpServletRequest req, HttpServletResponse resp );
}
