package pt.inescporto.template.client.design.list;

import pt.inescporto.template.client.design.thirdparty.JListMutable;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.TmplFormModes;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.design.TmplGetter;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.event.TemplateComponentListener;
import javax.swing.event.EventListenerList;
import pt.inescporto.template.client.util.DataSource;
import java.awt.event.ActionListener;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.dao.RowNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.*;


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

public class FW_JList extends JListMutable implements FW_ComponentListener, TemplateComponentListener, ActionListener {
  protected EventListenerList componentListenerList = new EventListenerList();
  protected int mode;
  protected FW_ListModel lm = null;

  protected boolean bMaster = true;
  protected DataSource datasource = null;
  protected String fields[];

  public FW_JList(DataSource datasource, FW_ListModel dataModel) {
    super(dataModel);

    lm = dataModel;

    this.datasource = datasource;
//    start();
  }

  /*-------------------------------------------------[ Model Support ]---------------------------------------------------*/

  public boolean isCellEditable(int index) {
    if (getModel() instanceof FW_ListModel)
      return ((FW_ListModel)getModel()).isCellEditable(index);
    return false;
  }

  public void setValueAt(Object value, int index) {
    ((FW_ListModel)getModel()).setValueAt(value, index);
  }

  /**
   * Start to handle GUI control for master datasource
   */
  public void start() {
//    addFWComponentListener(this);
    /**@todo this table as permissions to. make this ASAP*/
    fireTemplateFormPermission(15);

    //start control
    try {
      datasource.initialize();
      datasource.first();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    setMode(TmplFormModes.MODE_SELECT);
  }

  /**
   * Set this value to true if we have to control the datasource as a datasource
   * master or if it's a detail datasource. If is a detail datasource the table
   * model will responde to events from template event throw this table.
   *
   * @param bMaster boolean
   */
  public void setAsMaster(boolean bMaster) {
    this.bMaster = bMaster;
  }

  /** *********************************************************************** **
   **                GUI control for modes and data maintenance               **
   ** *********************************************************************** **
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("INSERT"))
      doInsert();
    if (e.getActionCommand().equals("UPDATE"))
      doUpdate();
    if (e.getActionCommand().equals("DELETE"))
      doDelete();
    if (e.getActionCommand().equals("SAVE"))
      doSave();
    if (e.getActionCommand().equals("CANCEL"))
      doCancel();
  }

  protected void doInsert() {
    setMode(TmplFormModes.MODE_INSERT);
    fireTemplateMode();
    try {
      lm.addCleanRow();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  protected void doInsertAfter(Object attrs) {
  }

  protected void doUpdate() {
    if (getSelectedIndex() != -1 ) {
      setMode(TmplFormModes.MODE_UPDATE);
      lm.letEditRow(getSelectedIndex());
      fireTemplateMode();
    }
  }

  protected void doUpdateAfter(Object attrs) {
  }

  protected void doDelete() {
    if (getSelectedIndex() != -1 ) {
      setMode(TmplFormModes.MODE_DELETE);
      doSave();
      fireTemplateMode();
    }
  }

  protected void doDeleteAfter(Object attrs) {
  }

  protected boolean doSave() {
    Object attrs = null;
    try {
      switch (mode) {
        case TmplFormModes.MODE_INSERT:
          if (lm.canSave()) {
            editingStopped(null);
            attrs = lm.saveRows();
	    try {
	      doInsertAfter(attrs);
	    }
	    catch (Exception ex2) {
	    }
            setMode(TmplFormModes.MODE_SELECT);
          }
          else {
            showErrorMessage("Nem todos os campos obrigatórios estão preenchidos!");
            return false;
          }
          return true;
        case TmplFormModes.MODE_UPDATE:
          if (lm.canSave()) {
            editingStopped(null);
            attrs = lm.saveRows();
	    try {
	      doUpdateAfter(attrs);
	    }
	    catch (Exception ex1) {
	    }
            setMode(TmplFormModes.MODE_SELECT);
          }
          else {
            showErrorMessage("Nem todos os campos obrigatórios estão preenchidos!");
            return false;
          }
          return true;
        case TmplFormModes.MODE_DELETE:
          if (showOkCancelMessage(TmplResourceSingleton.getString("info.dialog.header.question"), TmplResourceSingleton.getString("info.dialog.msg.delete")) == JOptionPane.OK_OPTION) {
            attrs = lm.getAttrsAt(getSelectedIndex());
	    try {
	      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	      ObjectOutputStream outData = new ObjectOutputStream(byteOut);
	      outData.writeObject(attrs);
	      outData.flush();
              ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
              ObjectInputStream inData = new ObjectInputStream(byteIn);
              attrs = inData.readObject();
	    }
            catch (IOException ex3) {
              ex3.printStackTrace();
            }
            catch (ClassNotFoundException ex3) {
              ex3.printStackTrace();
            }

            lm.deleteRow(getSelectedIndex());
	    try {
	      doDeleteAfter(attrs);
	    }
	    catch (Exception ex) {
	    }
          }
          setMode(TmplFormModes.MODE_SELECT);
          return true;
      }
    }
    catch (DataSourceException dsex) {
      if (dsex.getCause() instanceof DupKeyException)
        showErrorMessage(TmplResourceSingleton.getString("error.msg.dupkey"));
      else if (dsex.getCause() instanceof RowNotFoundException) {
        try {
          doDeleteAfter(attrs);
        }
        catch (Exception ex) {
        }
        setMode(TmplFormModes.MODE_SELECT);
        return true;
      }
      else
        showErrorMessage(dsex.getException().getMessage());
      return false;
    }

    return true;
  }

  public void doCancel() {
    lm.cancelRows();
    editingCanceled(null);
    setMode(TmplFormModes.MODE_SELECT);
  }

  /**
   * Change the current state of the form.
   *
   * @param iMode int
   *
   * @see TmplFormModes
   */
  protected void setMode(int iMode) {
    mode = iMode;
    fireTemplateMode();
  }

  public int getMode() {
    return mode;
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
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        templateEvent.setMode(mode);
        ((TemplateComponentListener)listeners[i + 1]).tmplMode(templateEvent);
      }
    }
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
        /**@todo fix the permissions field list ASAP */
//        templateEvent.setPermFieldList(perms.);
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
        if (!((TemplateComponentListener)listeners[i + 1]).tmplRequired(templateEvent)) {
          return (TmplGetter)templateEvent.getCompFailed();
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
    return JOptionPane.showConfirmDialog(this,
                                         msg, title, JOptionPane.OK_CANCEL_OPTION);
  }

  public int showYesNoMessage(String title, String msg) {
    return JOptionPane.showConfirmDialog(this,
                                         msg, title, JOptionPane.YES_NO_OPTION);
  }
  /** *********************************************************************** **
   **        Implementation of the TemplateComponentListener interface        **
   ** *********************************************************************** **
   */

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplPermissions(TemplateEvent e) {
    if (bMaster)
      return;
    fireTemplateFormPermission(e.getPermission());
  }

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplMode(TemplateEvent e) {
    if (bMaster)
      return;

    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
        setMode(TmplFormModes.MODE_SELECT);
        break;
      case TmplFormModes.MODE_FIND:
      case TmplFormModes.MODE_INSERT:
      case TmplFormModes.MODE_UPDATE:
      case TmplFormModes.MODE_LOCK:
        setMode(TmplFormModes.MODE_LOCK);
        fireTemplateMode();
        break;
    }
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
