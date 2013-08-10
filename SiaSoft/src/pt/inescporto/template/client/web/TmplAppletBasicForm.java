package pt.inescporto.template.client.web;

import java.net.URL;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.web.util.*;
import java.net.*;
import pt.inescporto.permissions.ejb.session.FormFieldPermission;

public abstract class TmplAppletBasicForm extends JApplet {
  protected String urlWS = null;
  protected TmplHttpMessageSender httpSender = null;
  protected TmplHttpMessageSender httpSenderPerms = null;
  protected String permFormId = "NOTDEF";
  protected ResourceBundle resourceBundle = null;
  protected FormFieldPermission perms;
  protected String urlBase;
  protected Object attrs = null;
  protected boolean isSupplier = false;
  BorderLayout borderLayout = new BorderLayout();

  public TmplAppletBasicForm() {
    // set look & feel to windows
    try {
      String laf = UIManager.getSystemLookAndFeelClassName();
      UIManager.setLookAndFeel( laf );
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void init() {
    // get initialization parameters
    try {
      if (getParameter("urlBase") != null) {
        urlBase = getParameter("urlBase");
        setURL();
      }
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }
  }

  public void start() {
    try {
      // get permissions based on service <code>permFormId</code>
      doGetPermissions();

      // stop if permissions equals NONE
      if( (int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0 )
        stop();
    }
    catch( Exception ex ) {
    }
  }

  private void jbInit() throws Exception {
    setBounds(0,0,800,600);
    getContentPane().setLayout(borderLayout);
    getContentPane().setBackground(Color.white);
    getContentPane().setForeground(Color.black);
    this.setFont(new java.awt.Font("Dialog", 0, 10));
  }

  protected void setResourceFile( String resFile ) {
    try {
      resourceBundle = ResourceBundle.getBundle(resFile);
    }
    catch (MissingResourceException ex) {
      ex.printStackTrace();
    }
  }

  protected String getResourceString( String key ) {
    return resourceBundle == null ? null : resourceBundle.getString( key );
  }

  protected String getURL() {
    return urlWS;
  }

  private void setURL() {
    urlWS = urlBase + urlWS;
    httpSender = new TmplHttpMessageSender( urlWS );
    httpSenderPerms = new TmplHttpMessageSender( urlBase + "/PermsProxy" );
  }

  /**
   * communication control
   */
  protected Object doSendObject( Object msg ) {
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    if( msg instanceof pt.inescporto.template.web.util.TmplHttpMsgPerm ) {
      msg = httpSenderPerms.doSendObject( msg );
      switch( httpSender.getErrCode() ) {
        case TmplMessages.CERR_IO :
          showErrorMessage( TmplResourceSingleton.getString("error.msg.connection") );
          break;
        case TmplMessages.CERR_CLASSNOTFOUND :
        case TmplMessages.CERR_MALFORMEDURL :
          showErrorMessage( TmplResourceSingleton.getString("error.msg.url") );
          break;
      }
    }
    else {
      msg = httpSender.doSendObject( msg );
      switch( httpSender.getErrCode() ) {
        case TmplMessages.CERR_IO :
          showErrorMessage( TmplResourceSingleton.getString("error.msg.connection") );
          break;
        case TmplMessages.CERR_CLASSNOTFOUND :
        case TmplMessages.CERR_MALFORMEDURL :
          showErrorMessage( TmplResourceSingleton.getString("error.msg.url") );
          break;
      }
    }

    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    return msg;
  }

  protected void changeMenu( String menuId ) {
    MenuApplet menuApplet = (MenuApplet)AppletRegistry.getInstance().getApplet( "menu" );
    menuApplet.setSelection( menuId );
  }

  // ***************************************************************************
  // Permissions control
  // ***************************************************************************

  /**
   *
   */
  protected void setPermFormId( String permFormId ) {
    this.permFormId = permFormId;
  }

  /**
   *
   */
  protected int doGetPermissions() {
    TmplHttpMsgPerm msg = new TmplHttpMsgPerm( TmplMessages.MSG_FORMPERM, permFormId );

    msg = (TmplHttpMsgPerm)doSendObject( (Object)msg );
    if( httpSenderPerms.getErrCode() == TmplMessages.MSG_OK ) {
      isSupplier = msg.isSupplier();
//      perms = new Integer( msg.getPerms() );
    }

    return msg.getReturnCode();
  }

  /**
   *
   */
  protected void linkPerms() {
    Component[] all = getContentPane().getComponents();

    if( (int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0 ) {
      for( int i = 0; i < all.length; i++ )
        all[i].setVisible(false);
      showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
    }
  }

  // ***************************************************************************
  // Miscelanious
  // ***************************************************************************

  /**
   * Get images from the web server
   */
  public java.awt.Image getImage( String imageRelativeUrl ) {
    java.awt.Image image = null;

    try {
      java.awt.MediaTracker mt = new java.awt.MediaTracker( this );
      java.net.URL url = new java.net.URL( urlBase + imageRelativeUrl );
      image = Toolkit.getDefaultToolkit().getImage( url );
      mt.addImage( image, 0 );
      mt.waitForID( 0 );
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }
    return image;
  }

  /**
   * Displayable message boxs
   */
  public void showErrorMessage( String msg ) {
    JOptionPane.showMessageDialog( this.getContentPane(),
            msg, TmplResourceSingleton.getString("error.dialog.header"),
            JOptionPane.ERROR_MESSAGE );
  }

  public void showInformationMessage( String msg ) {
    JOptionPane.showMessageDialog( this.getContentPane(),
            msg, TmplResourceSingleton.getString("info.dialog.header"),
            JOptionPane.INFORMATION_MESSAGE );
  }

  public int showOkCancelMessage( String title, String msg ) {
    return JOptionPane.showConfirmDialog( this.getContentPane(),
            msg, title, JOptionPane.OK_CANCEL_OPTION );
  }

  public int showYesNoMessage( String title, String msg ) {
    return JOptionPane.showConfirmDialog( this.getContentPane(),
            msg, title, JOptionPane.YES_NO_OPTION );
  }
}


