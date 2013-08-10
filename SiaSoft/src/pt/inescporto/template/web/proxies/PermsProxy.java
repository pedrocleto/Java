package pt.inescporto.template.web.proxies;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.TmplHttpMsgPerm;
import pt.inescporto.permissions.ejb.session.*;
import pt.inescporto.permissions.ejb.dao.GlbFormIsInRoleDao;
import pt.inescporto.template.web.beans.LoggedUser;

public class PermsProxy extends HttpServlet {
  protected Collection          all = null;
  protected TmplHttpMsgPerm     msg = null;
  protected boolean             bDebug = false;
  protected GlbFormIsInRoleDao  attrs = null;
  protected GlbFormIsInRole     formIsInRole = null;
  protected LoggedUser          usrInfo = null;

  public void init( ServletConfig config ) throws ServletException {
    super.init( config );
    bDebug = Boolean.getBoolean(config.getInitParameter("debug"));
  }

  public void service( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, java.io.IOException {
    // reset some values
    all = null;

    // get message
    ObjectInputStream inData = new ObjectInputStream( req.getInputStream() );

    resp.setContentType( "application/octet-stream" );

    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream outData = new ObjectOutputStream( byteOut );

    try {
      msg = (TmplHttpMsgPerm)inData.readObject();
    }
    catch( java.lang.ClassNotFoundException e ) {
      if( bDebug )
        e.printStackTrace();
      msg.setReturnCode(TmplMessages.MSG_INITERROR);
    }
    inData.close();

    if( bDebug )
      System.out.println( "Option received : " + msg.getCommand() );

    msg.setReturnCode(TmplMessages.MSG_OK);

    // initialize some stuff
    try {
      this.doInitialize( req, resp );
      if( bDebug )
        System.out.println( "Attribute attrs not initialized..." );
    }
    catch( Exception re ) {
      if( bDebug ) {
        System.out.println( "Initialization failure..." );
        re.printStackTrace();
      }
      msg.setReturnCode(TmplMessages.MSG_INITERROR);
    }

    /* process */
    switch( msg.getCommand() ) {
      case TmplMessages.MSG_FORMPERM :
        if( usrInfo != null )
          msg.setSupplier( usrInfo.isSupplier() );
        if( msg.getFormId().equalsIgnoreCase("NOTDEF") )
          msg.setPerms(15);
        else {
          try {
            attrs.roleId.setValue( usrInfo.getRoleId() );
            attrs.formId.setValue( msg.getFormId() );
            formIsInRole.findByPrimaryKey( attrs );
            attrs = formIsInRole.getData();
            msg.setPerms( attrs.permission.getValue().intValue() );
          }
          catch( Exception ex ) {
            if( bDebug )
              ex.printStackTrace();
            msg.setPerms(0);
            msg.setReturnCode(TmplMessages.MSG_NOTFOUND);
          }
        }
        break;
    }

    // save results to msg
    outData.writeObject((Object)this.msg);
    outData.flush();

    byte[] buf = byteOut.toByteArray();
    resp.setContentLength(buf.length);
    ServletOutputStream servletOut = resp.getOutputStream();
    servletOut.write( buf );
    servletOut.close();
  }

  public void doInitialize( HttpServletRequest req, HttpServletResponse resp ) {
    // Get session
    HttpSession session = req.getSession();

    // Get home of EJB
    if( (GlbFormIsInRole)session.getAttribute("GlbFormIsInRole") == null ) {
      try {
        Context ic = new InitialContext();
        java.lang.Object objref = ic.lookup( "java:comp/env/ejb/GlbFormIsInRole" );
        GlbFormIsInRoleHome formIsInRoleHome = (GlbFormIsInRoleHome) PortableRemoteObject.narrow( objref,
                                                GlbFormIsInRoleHome.class );
        formIsInRole = formIsInRoleHome.create();
        session.setAttribute("GlbFormIsInRole", (Object)formIsInRole);
      }
      catch( Exception re ) {
        System.out.println( "Couldn't locate GlbFormIsInRole Home" );
        re.printStackTrace();
      }

      this.attrs = new GlbFormIsInRoleDao();
      session.setAttribute("GlbFormIsInRoleAttrs", attrs);
    }
    else {
      formIsInRole = (GlbFormIsInRole)session.getAttribute("GlbFormIsInRole");
      this.attrs = (GlbFormIsInRoleDao)session.getAttribute("GlbFormIsInRoleAttrs");
    }

    usrInfo = (LoggedUser)session.getAttribute("loggedUser");
  }

  public Collection doListAll() throws RemoteException {
    return null;
  }
}
