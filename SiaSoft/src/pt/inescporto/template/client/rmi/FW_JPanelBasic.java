package pt.inescporto.template.client.rmi;

import javax.swing.JPanel;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.util.TmplPerms;
import javax.swing.event.EventListenerList;
import pt.inescporto.template.client.design.TmplException;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;
import pt.inescporto.permissions.ejb.session.GlbPerm;
import java.awt.event.ComponentListener;
import pt.inescporto.permissions.ejb.session.FormFieldPermission;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import java.awt.Component;
import pt.inescporto.template.client.event.TemplateEvent;
import javax.swing.JComponent;
import pt.inescporto.template.client.event.TemplateComponentListener;

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
public class FW_JPanelBasic extends JPanel implements TemplateComponentListener, ComponentListener {
  protected EventListenerList componentListenerList = new EventListenerList();

  protected int minWidth;
  protected int minHeight;
  protected FormFieldPermission perms = null;
  protected String accessPermitionID = "NOTDEF";
  protected boolean hasVisualPerms = false;

  // synchronizer event
  protected String subject = null;

  public FW_JPanelBasic() {
  }

  public FW_JPanelBasic(String accessPermitionID) {
    this.accessPermitionID = accessPermitionID;
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

  /**
   * Used for synchronization control
   * @see EventSynchronizer
   * @param subject String
   */
  public void setPublisherEvent(String subject) {
    this.subject = subject;
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
      for (int i = 0; i < all.length; i++) {
	all[i].setVisible(false);
      }
      setVisible(false);
//        showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
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

  // ***************************************************************************
  // Miscelanious
  // ***************************************************************************

  public void MinimumPack() {
    minHeight = this.getHeight();
    minWidth = this.getWidth();
  }

  //Methods used to avoid the frame getting it's size below the minimum size
  public void componentResized(ComponentEvent e) {
    int width = getWidth();
    int height = getHeight();

    //we check if either the width or the height are below minimum
    boolean resize = false;

    if (width < minWidth) {
      resize = true;
      width = minWidth;
    }
    if (height < minHeight) {
      resize = true;
      height = minHeight;
    }
    if (resize) {
      setSize(width, height);
    }
  }

  public void componentMoved(ComponentEvent e) {}

  public void componentShown(ComponentEvent e) {}

  public void componentHidden(ComponentEvent e) {}

  /** *********************************************************************** **
   **        Implementation of the TemplateComponentListener interface        **
   ** *********************************************************************** **
   */

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplPermissions(TemplateEvent e) {
    // i'm working as slave! See the <code>start</code> method for details!
    int permValue = e.getPermissionForField(accessPermitionID);
    if (permValue != -1) {
      Component[] all = this.getComponents();

      if ((permValue & TmplPerms.PERM_SHOW) == 0) {
	for (int i = 0; i < all.length; i++) {
	  all[i].setVisible(false);
	}
	setVisible(false);
//        showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
      }
      else {
	hasVisualPerms = (permValue & TmplPerms.PERM_SHOW) != 0;
      }
    }
  }

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplMode(TemplateEvent e) {
  }

  public JComponent tmplGetComponent() {
//    System.out.println("table tmplGetComponent()");
    return this;
  }

  public void tmplEnable(TemplateEvent e) {
//    System.out.println("table tmplEnable()");
  }

  public boolean tmplRequired(TemplateEvent e) {
//    System.out.println("table tmplRequired()");
    return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
//    System.out.println("table tmplValidate()");
    return true;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
