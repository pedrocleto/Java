package pt.inescporto.template.client.design;

import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.DefaultListSelectionModel;
import javax.swing.event.EventListenerList;

import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.permissions.ejb.session.GlbPerm;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.permissions.ejb.session.FormFieldPermission;
import pt.inescporto.template.client.event.TemplateComponentListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.table.TmplTableColumnModel;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.template.client.event.EventSynchronizerWatcherRemover;

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
public class FW_JTable extends JTable implements TemplateComponentListener, ActionListener, FW_ComponentListener {
  protected EventListenerList componentListenerList = new EventListenerList();
  protected int mode;

  // permission control
  protected FormFieldPermission perms = null;
  protected String accessPermitionID = "NOTDEF";

  protected FW_TableModel tm = null;
  protected FW_ColumnManager fwColumnManager = null;
  protected FW_ComponentListener parentListener = null;

  protected boolean bMaster = true;
  protected DataSource datasource = null;
  protected String[] headers = null;
  protected String fields[];

  // synchronizer event
  protected String subject = null;

  public FW_JTable() {
    super();
    putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    setSurrendersFocusOnKeystroke(true);
  }

  public FW_JTable(DataSource datasource, String[] headers, String fields[]) {
    this();
    //save instance data
    this.datasource = datasource;
    this.headers = headers;
    this.fields = fields;

    // set some look
    setAutoCreateColumnsFromModel(false);
    setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 10));
    setOpaque(false);

    //set column model
    setColumnModel(new TmplTableColumnModel());

    //set table model
    tm = new FW_TableModel(datasource, headers, fields);
    setModel(tm);
    tm.setJTableParent(this);

    //create column from model
    createDefaultColumnsFromModel();

    //start control
    setMode(TmplFormModes.MODE_SELECT);
    putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
  }

  public FW_JTable(DataSource datasource, String[] headers, FW_ColumnManager fwColumnManager) {
    this();
    //save instance data
    this.datasource = datasource;
    this.headers = headers;
    this.fwColumnManager = fwColumnManager;

    // set some look
    setAutoCreateColumnsFromModel(false);
    setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 10));
    setOpaque(false);

    //set column model
    setColumnModel(new TmplTableColumnModel());

    //set table model
    tm = new FW_TableModel(datasource, headers, fwColumnManager);
    setModel(tm);
    tm.setJTableParent(this);

    //create column from model
    createDefaultColumnsFromModel();

    //start control
    setMode(TmplFormModes.MODE_SELECT);
    fireTemplateMode(TmplFormModes.MODE_SELECT, TmplFormModes.MODE_LOCK);
    putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
  }

  public void setFW_ParentListener(FW_ComponentListener parentListener) {
    this.parentListener = parentListener;
  }

  public void valueChanged(ListSelectionEvent e) {
    super.valueChanged(e);
    if (mode == TmplFormModes.MODE_SELECT && !e.getValueIsAdjusting() && getSelectedRow() != -1) {
      Object attrs = tm.getAttrsAt(getSelectedRow());
      try {
        fireTemplateMode(TmplFormModes.MODE_SELECT, TmplFormModes.MODE_SELECT);
        //System.out.println("Row selected changed!");
	datasource.setAttrs(attrs);
        datasource.refresh(getModel());
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }
    }
    else {
      if (!e.getValueIsAdjusting() && getSelectedRow() == -1 && mode != TmplFormModes.MODE_LOCK)
        fireTemplateMode(mode, TmplFormModes.MODE_LOCK);
    }
  }

  public void createDefaultColumnsFromModel() {
    for (int i = 0; i < dataModel.getColumnCount(); i++) {
      addColumn(tm.getColumnAt(i));
    }
  }

  /**
   * Start to handle GUI control for master datasource
   */
  public void start() {
    try {
      doGetPermissions();
    }
    catch (TmplException ex1) {
    }
    fireTemplateFormPermission(perms.getFormPerms());

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
   * Used for synchronization control
   * @see EventSynchronizer
   * @param subject String
   */
  public void setPublisherEvent(String subject) {
    this.subject = subject;
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
    fireTemplateMode(TmplFormModes.MODE_INSERT, TmplFormModes.MODE_LOCK);

    // lock parent editing
    if (parentListener != null) {
      parentListener.lockParent();
    }

    try {
      tm.addCleanRow();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  protected void doUpdate() {
    if (getSelectedRow() != -1 ) {
      setMode(TmplFormModes.MODE_UPDATE);
      tm.letEditRow(getSelectedRow());
      fireTemplateMode(TmplFormModes.MODE_UPDATE, TmplFormModes.MODE_LOCK);
    }
  }

  protected void doDelete() {
    if (getSelectedRow() != -1 ) {
      setMode(TmplFormModes.MODE_DELETE);
      doSave();
      fireTemplateMode();
    }
  }

  public boolean doSaveBefore(Object attrs) {
    return true;
  }

  protected boolean doSave() {
    try {
      switch (mode) {
	case TmplFormModes.MODE_INSERT:
	  if (tm.canSave()) {
            if (doSaveBefore(tm.getAttrsAt(tm.getInsertingRows().elementAt(0).intValue()))) {
	      editingStopped(null);
	      tm.saveRows();
	      setMode(TmplFormModes.MODE_SELECT);
	      if (subject != null)
		EventSynchronizer.getInstance().triggerEvent(subject);
	    }
            else
              return false;
	  }
	  else {
	    showErrorMessage("Nem todos os campos obrigatórios estão preenchidos!");
	    return false;
	  }
	  return true;
	case TmplFormModes.MODE_UPDATE:
	  if (tm.canSave()) {
            if (doSaveBefore(tm.getAttrsAt(tm.getUpdatingRows().elementAt(0).intValue()))) {
	      editingStopped(null);
	      tm.saveRows();
	      setMode(TmplFormModes.MODE_SELECT);
	      if (subject != null)
		EventSynchronizer.getInstance().triggerEvent(subject);
	    }
            else {
              return false;
            }
	  }
          else {
            showErrorMessage("Nem todos os campos obrigatórios estão preenchidos!");
            return false;
          }
          return true;
	case TmplFormModes.MODE_DELETE:
	  if (showOkCancelMessage(TmplResourceSingleton.getString("info.dialog.header.question"), TmplResourceSingleton.getString("info.dialog.msg.delete")) == JOptionPane.OK_OPTION) {
	    tm.deleteRow(getSelectedRow());
	  }
          setMode(TmplFormModes.MODE_SELECT);
          if (subject != null)
            EventSynchronizer.getInstance().triggerEvent(subject);
	  return true;
      }
    }
    catch (DataSourceException dsex) {
      if (dsex.getCause() instanceof DupKeyException) {
	showErrorMessage(TmplResourceSingleton.getString("error.msg.dupkey"));
        return false;
      }
      else {
	showErrorMessage(dsex.getException().getMessage());
	return false;
      }
    }

    // enable parent update
    if (parentListener != null)
      parentListener.unlockParent();

    return true;
  }

  public void doCancel() {
    tm.cancelRows();
    editingCanceled(null);

    // enable parent update
    if (parentListener != null)
      parentListener.unlockParent();

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

  // ***************************************************************************
  // Permissions control
  // ***************************************************************************

  /**
   * Set identification of form for permission control
   * @param permFormId form id
   */
  public void setAccessPermitionIdentifier(String permFormId) {
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
      setVisible((perms.getFormPerms() & TmplPerms.PERM_SHOW) != 0);
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
   * notification on <code>TemplateEvent</code> mode events.
   * Creates the event based on current <code>mode</code>.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateMode(TemplateEvent tev) {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        ((TemplateComponentListener)listeners[i + 1]).tmplMode(tev);
      }
    }
  }

  protected void fireTemplateMode(int formMode, int iMode) {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        templateEvent.setMode(iMode);
        templateEvent.setModeForLocking(formMode);
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
        if (perms != null)
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
      // i'm working as master! See the <code>start</code> method for details!
      return;
    else {
      // let's see if there are permissions defined for me!
      int permValue = e.getPermissionForField(accessPermitionID);
      if (permValue != -1)
	fireTemplateFormPermission(permValue);
      else {
        // nok, father didn't send permissions for me! Let's see if there are
        // defined as a master form.
        try {
          doGetPermissions();
        }
        catch (TmplException ex) {
        }
        fireTemplateFormPermission(perms.getFormPerms());
      }
    }
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
        fireTemplateMode(TmplFormModes.MODE_SELECT, TmplFormModes.MODE_LOCK);
        break;
      case TmplFormModes.MODE_FIND:
      case TmplFormModes.MODE_INSERT:
      case TmplFormModes.MODE_UPDATE:
      case TmplFormModes.MODE_LOCK:
        setMode(TmplFormModes.MODE_LOCK);
        TemplateEvent tev = new TemplateEvent(e.getSource());
        tev.setMode(TmplFormModes.MODE_LOCK);
        fireTemplateMode(tev);
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
    for (int i = 1; i < getColumnCount(); i++) {
      if (getCellEditor(1, i) instanceof EventSynchronizerWatcherRemover)
        ((EventSynchronizerWatcherRemover)getCellEditor(1, i)).removeEventSynchronizer();

    }
  }
}
