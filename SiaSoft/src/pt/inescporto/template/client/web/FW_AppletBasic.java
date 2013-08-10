package pt.inescporto.template.client.web;

import java.awt.Component;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.design.TmplException;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.DataSourceHTTP;
import pt.inescporto.template.client.util.TmplPerms;

import pt.inescporto.permissions.ejb.session.GlbPerm;
import pt.inescporto.permissions.ejb.session.FormFieldPermission;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class FW_AppletBasic extends JApplet {
  protected EventListenerList componentListenerList = new EventListenerList();

  protected FormFieldPermission perms = null;
  protected String accessPermitionID = "NOTDEF";
  protected boolean hasVisualPerms = false;
  protected String urlBase = null;

  public FW_AppletBasic() {
  }

  public void init() {
    // get initialization parameters
    try {
      System.out.println("My code base is <" + getCodeBase() + ">");
      if (getParameter("urlBase") != null) {
        urlBase = getParameter("urlBase");
      }
      System.out.println("My url base is <" + urlBase + ">");
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }
  }

  public void start() {
    try {
      doGetPermissions();
    }
    catch (TmplException ex1) {
      return;
    }
  }

  public boolean hasVisualPerms() {
    return hasVisualPerms;
  }

  // ***************************************************************************
  // Permissions control
  // ***************************************************************************

  /**
   * Set identification of form for permission control
   * @param permFormId form id
   */
  protected void setAccessPermitionIdentifier(String permFormId) {
    this.accessPermitionID = permFormId;
  }

  /**
   * Get permissions for this form based on form ID
   * @throws TmplException
   */
  protected void doGetPermissions() throws TmplException {
    DataSourceHTTP datasourcePermission = new DataSourceHTTP("DSPerms", urlBase);
    datasourcePermission.setUrl("/PermsProxy");

    try {
      GlbPerm perm = (GlbPerm)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.permissions.ejb.session.GlbPerm");

      perms = perm.getFormPerms(MenuSingleton.getRole(), accessPermitionID);
    }
    catch (java.rmi.RemoteException rex) {
      //can't get form perms
      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
      tmplex.setDetail(rex);
      throw tmplex;
    }
    catch (javax.naming.NamingException nex) {
      //can't find GlbPerm
      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
      tmplex.setDetail(nex);
      throw tmplex;
    }

    Component[] all = this.getComponents();

    if ((int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0) {
      for (int i = 0; i < all.length; i++)
        all[i].setVisible(false);
      showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
    }
    else {
      hasVisualPerms = (perms.getFormPerms() & TmplPerms.PERM_SHOW) != 0;
    }
  }

  /** **************************************************************************
   *               GUI methods for basic dialog's
   ** **************************************************************************
   */

  /**
   * Displayable message box for error messages
   * @param msg message
   */
  public void showErrorMessage(String msg) {
    JOptionPane.showMessageDialog(this,
                                  msg, TmplResourceSingleton.getString("error.dialog.header"),
                                  JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displayable message box for information messages
   * @param msg message
   */
  public void showInformationMessage(String msg) {
    JOptionPane.showMessageDialog(this,
                                  msg, TmplResourceSingleton.getString("info.dialog.header"),
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displayable message box for querying
   * @param title title fot the box
   * @param msg question
   * @return value Yes or No
   */
  public int showOkCancelMessage(String title, String msg) {
    return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.OK_CANCEL_OPTION);
  }

  public int showYesNoMessage(String title, String msg) {
    return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION);
  }
}
