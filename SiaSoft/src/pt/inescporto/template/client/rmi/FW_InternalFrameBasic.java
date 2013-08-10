package pt.inescporto.template.client.rmi;

import java.awt.event.ComponentListener;
import javax.swing.event.EventListenerList;
import pt.inescporto.template.client.design.TmplException;
import javax.swing.SwingUtilities;
import javax.swing.JInternalFrame;
import pt.inescporto.permissions.ejb.session.GlbPerm;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.util.TmplPerms;
import java.awt.Component;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import javax.swing.JOptionPane;
import java.awt.event.ComponentEvent;
import pt.inescporto.permissions.ejb.session.FormFieldPermission;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.event.TemplateEvent;
import javax.swing.JComponent;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplGetter;
import pt.inescporto.template.client.event.TemplateComponentListener;
import java.util.ArrayList;

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
public abstract class FW_InternalFrameBasic extends JInternalFrame implements FW_ComponentListener, ComponentListener {
  protected EventListenerList componentListenerList = new EventListenerList();

  protected int minWidth;
  protected int minHeight;
  protected FormFieldPermission perms = null;
  protected String accessPermitionID = "NOTDEF";
  protected boolean hasVisualPerms = false;

  // synchronizer event
  protected String subject = null;

  public FW_InternalFrameBasic() {
  }

  public void start() {
    try {
      // get permissions
      doGetPermissions();

      // broadcast perms
      fireTemplateFormPermission(perms.getFormPerms());
    }
    catch (TmplException ex1) {
      SwingUtilities.invokeLater(new DisposeFrame(this));
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
      for (int i = 0; i < all.length; i++)
        all[i].setVisible(false);
      showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
    }
    else {
      hasVisualPerms = (perms.getFormPerms() & TmplPerms.PERM_SHOW) != 0;
    }
  }

  // ***************************************************************************
  //                        Template listener control
  // ***************************************************************************

  public synchronized void addFWComponentListener(TemplateComponentListener l) {
    componentListenerList.add(TemplateComponentListener.class, l);
  }

  public synchronized void removeFWComponentListener(TemplateComponentListener l) {
    componentListenerList.remove(TemplateComponentListener.class, l);
  }

  public void lockParent() {}

  public void unlockParent() {}

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> mode events.
   * Creates the event based on current <code>mode</code>.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateMode() {
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> form permissions events.
   * Lazily creates the event.
   * Stops on first false retun.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateFormPermission(int permission) {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this, permission);
        templateEvent.setPermFieldList(perms.getFieldPermList());
        ((TemplateComponentListener)listeners[i + 1]).tmplPermissions(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> enable events.
   * Creates the event based on parameter enable.
   *
   * @param enable boolean
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateEnable(boolean enable) {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        templateEvent.setEnabled(enable);
        ((TemplateComponentListener)listeners[i + 1]).tmplEnable(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> validate events.
   * Lazily creates the event.
   * Stops on first false retun.
   *
   * @return boolean
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected boolean fireTemplateValidate() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        if (!((TemplateComponentListener)listeners[i + 1]).tmplValidate(templateEvent))
          return false;
      }
    }
    return true;
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> required events.
   * Lazily creates the event.
   * Stops on first false retun.
   *
   * @return boolean
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected TmplGetter fireTemplateRequired() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        try {
          if (!((TemplateComponentListener)listeners[i + 1]).tmplRequired(templateEvent)) {
            return (TmplGetter)templateEvent.getCompFailed();
          }
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> get component.
   * Lazily creates the event.
   *
   * @return ArrayList
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected ArrayList fireTemplateGetComponents() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();
    ArrayList pkList = new ArrayList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        JComponent component = ((TemplateComponentListener)listeners[i + 1]).tmplGetComponent();
        if (component != null) {
          if (component instanceof TmplJTextField && ((TmplJTextField)component).getLink().isPrimaryKey())
            pkList.add(component);
        }
      }
    }
    return pkList;
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> enable events.
   * Creates the event based on parameter enable.
   *
   * @param enable boolean
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateDispose() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        ((TemplateComponentListener)listeners[i + 1]).tmplDispose(templateEvent);
      }
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
    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
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
    return JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this), msg, title, JOptionPane.OK_CANCEL_OPTION);
  }

  public int showYesNoMessage(String title, String msg) {
    return JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this), msg, title, JOptionPane.YES_NO_OPTION);
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

  public void dispose() {
    fireTemplateDispose();
    super.dispose();
  }
}

class DisposeFrame implements Runnable {
  FW_InternalFrameBasic frm = null;

  public DisposeFrame(FW_InternalFrameBasic frm) {
    this.frm = frm;
  }

  public void run() {
    if (frm != null)
      frm.dispose();
  }
}
