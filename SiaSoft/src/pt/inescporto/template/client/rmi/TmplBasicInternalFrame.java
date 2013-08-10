package pt.inescporto.template.client.rmi;

import javax.swing.JInternalFrame;
import pt.inescporto.template.utils.TmplMessages;
import java.awt.BorderLayout;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.permissions.ejb.session.GlbForm;
import pt.inescporto.template.client.design.TmplException;
import javax.swing.JOptionPane;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import pt.inescporto.permissions.ejb.session.GlbPerm;
import java.awt.event.ComponentListener;
import java.awt.Component;
import java.awt.AWTEvent;
import java.awt.event.WindowEvent;
import pt.inescporto.permissions.ejb.dao.GlbFormDao;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import javax.swing.SwingUtilities;
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
 * @author jap
 * @version 0.1
 */
public class TmplBasicInternalFrame extends JInternalFrame implements ComponentListener {
  protected TmplEJBLocater ejbLocator = null;
  private BorderLayout borderLayout1 = new BorderLayout();
  protected String url = null;

  protected String permFormId = "NOTDEF";
  protected FormFieldPermission perms = null;

  protected String urlBase;
  protected Object attrs = null;
  protected boolean isSupplier = false;
  protected Object home = null;
  protected Object remote = null;
  protected int minWidth;
  protected int minHeight;

  public TmplBasicInternalFrame() {
    // catch closing events
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    addComponentListener(this);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void init() {
    // get initialization parameters
    try {
      urlBase = "";
      setURL();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    try {
      getIconFromDB();
    }
    catch (TmplException ex2) {
    }
  }

  public void start() {
    try {
      // get permissions based on service <code>permFormId</code>
      try {
	doGetPermissions();
      }
      catch (TmplException ex1) {
      }
      finally {
	// stop if permissions equals NONE
	if ((int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0)
          stop();
      }
    }
    catch (Exception ex) {
    }
    try {
      getIconFromDB();
    }
    catch (TmplException ex2) {
    }
  }

  public void stop() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        dispose();
      }
    });
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
  }

  protected String getURL() {
    return url;
  }

  private void setURL() {
    url = urlBase + url;
  }

  // ***************************************************************************
  // Permissions control
  // ***************************************************************************
  /**
   * Set identification of form for permission control
   * @param permFormId form id
   */
  protected void setPermFormId(String permFormId) {
    this.permFormId = permFormId;
  }

  /**
   * Get permissions for this form based on form ID
   * @throws TmplException
   */
  protected void doGetPermissions() throws TmplException {
    try {
      GlbPerm perm = (GlbPerm)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.permissions.ejb.session.GlbPerm");

      perms = perm.getFormPerms(MenuSingleton.getRole(), permFormId);
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
  }

  protected void getIconFromDB() throws TmplException {
    if (permFormId.compareTo("NOTDEF") == 0)
      return;

    try {
      GlbForm form = (GlbForm)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.permissions.ejb.session.GlbForm");

      form.findByPrimaryKey(new GlbFormDao(permFormId));
      GlbFormDao formData = form.getData();
      /*      if (formData.scIcon.getValue() != null)
	      setIconImage(((ImageIcon)formData.scIcon.getValue()).getImage());*/
    }
    catch (java.rmi.RemoteException rex) {
    }
    catch (javax.naming.NamingException nex) {
    }
    catch (javax.ejb.FinderException nex) {
    }
  }

  /**
   *
   */
  protected void linkPerms() {
    Component[] all = this.getComponents();

    if ((int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0) {
      for (int i = 0; i < all.length; i++)
	all[i].setVisible(false);
      showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
    }
  }

  // ***************************************************************************
  // Miscelanious
  // ***************************************************************************

  public void MinimumPack() {
    this.pack();
    minHeight = this.getHeight();
    minWidth = this.getWidth();
  }

  //Methods used to avoid the frame getting it's size below the minimum size
  public void componentResized(ComponentEvent e) {

    int width = getWidth();
    int height = getHeight();

    //we check if either the width
    //or the height are below minimum

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
      this.setSize(width, height);
    }
  }

  public void componentMoved(ComponentEvent e) {}

  public void componentShown(ComponentEvent e) {  }

  public void componentHidden(ComponentEvent e) {}

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
    return JOptionPane.showConfirmDialog(this,
					 msg, title, JOptionPane.OK_CANCEL_OPTION);
  }

  public int showYesNoMessage(String title, String msg) {
    return JOptionPane.showConfirmDialog(this,
					 msg, title, JOptionPane.YES_NO_OPTION);
  }
}

