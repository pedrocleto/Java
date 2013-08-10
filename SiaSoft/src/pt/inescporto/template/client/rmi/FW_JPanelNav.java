package pt.inescporto.template.client.rmi;

import java.util.Collection;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import javax.swing.JComponent;

import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.design.TmplDlgLookup2;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.AllDSFilter;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.event.TemplateComponentListener;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.TmplGetter;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.LastFindDSFilter;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.client.design.FormRecordSelector;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.client.event.EventSynchronizer;

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
public class FW_JPanelNav extends FW_JPanelBasic implements FW_ComponentListener, ActionListener, FormRecordSelector {
  protected int mode;
  protected String staticLinkCondition = null;
  protected Collection lastFind = null;
  protected DataSource dataSource = null;
  public String[] allHeaders = new String[] {};

  public FW_JPanelNav() {
    super();
  }

  public void setDataSource(DataSource ds) {
    dataSource = ds;
  }

  public void start() {
    super.start();

    // broadcast perms
    fireTemplateFormPermission(perms.getFormPerms());

    try {
      if (dataSource != null) {
        dataSource.initialize();
        if (dataSource.first()) {
          // set mode to VIEW
          setMode(TmplFormModes.MODE_SELECT);
        }
        else {
          doInsert();
        }
      }
      else
        System.err.println("No data source defined!");
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  protected void refresh() {
    try {
      dataSource.refresh();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Response to normal navegational commands
   *
   * @param e ActionEvent
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
    if (e.getActionCommand().equals("NEXT"))
      doNext();
    if (e.getActionCommand().equals("PREV"))
      doPrevious();
    if (e.getActionCommand().equals("ALL"))
      doAll();
    if (e.getActionCommand().equals("FIND"))
      doFind();
    if (e.getActionCommand().equals("FINDRES"))
      doFindRes();
    if (e.getActionCommand().equals("EXIT")) {
      Component par = this;
      while (par.getParent() != null) {
        par = par.getParent();
        if (par instanceof java.awt.Window) {
          ((java.awt.Window)par).dispose();
          break;
        }
      }
    }
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

  protected boolean insertBefore() {
    return true;
  }

  /**
   * Prepare the form for insert mode
   */
  protected void doInsert() {
    setMode(TmplFormModes.MODE_INSERT);
    try {
      dataSource.cleanUpAttrs();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Called after success of inserting current record
   */
  protected void insertAfter() {
  }

  /**
   * Called before the frame enter in update mode!
   * @return boolean false stops update
   */
  protected boolean updateBefore() {
    return true;
  }

  protected void doUpdate() {
    setMode(TmplFormModes.MODE_UPDATE);
  }

  /**
   * Called after success of updating current record
   */
  protected void updateAfter() {
  }

  protected boolean deleteBefore() {
    return true;
  }

  protected void doDelete() {
    int errorCode;
    if (showOkCancelMessage(TmplResourceSingleton.getString("info.dialog.header.question"),
                            TmplResourceSingleton.getString("info.dialog.msg.delete")) == JOptionPane.OK_OPTION) {
      try {
        if (deleteBefore()) {
          dataSource.delete();
          deleteAfter();
          if (subject != null)
            EventSynchronizer.getInstance().triggerEvent(subject);
        }
      }
      catch (DataSourceException dsex) {
        if (dsex.getCause() instanceof ConstraintViolatedException)
          showErrorMessage(TmplResourceSingleton.getString("error.msg.constraint"));
        else
          if (dsex.getCause() instanceof RowNotFoundException) {
            doInsert();
          }
          else
            showErrorMessage(dsex.getCause().getMessage());
      }
    }
  }

  protected void deleteAfter() {
  }

  protected boolean doSave() {
    switch (mode) {
      case TmplFormModes.MODE_INSERT:
        TmplGetter compFailure = fireTemplateRequired();
        if (compFailure != null) {
          showErrorMessage(TmplResourceSingleton.getString("error.msg.required.field") + compFailure.getLabelName() + TmplResourceSingleton.getString("error.msg.required.end"));
          return false;
        }
        try {
          if (insertBefore()) {
            dataSource.insert();
            insertAfter();
            if (subject != null)
              EventSynchronizer.getInstance().triggerEvent(subject);
          }
          else
            return false;
        }
        catch (DataSourceException dsex) {
          if (dsex.getCause() instanceof DupKeyException)
            showErrorMessage(TmplResourceSingleton.getString("error.msg.dupkey"));
          else
            showErrorMessage(dsex.getException().getMessage());
          return false;
        }
        break
            ;
      case TmplFormModes.MODE_UPDATE:
        try {
          if (updateBefore()) {
            dataSource.update();
            updateAfter();
            if (subject != null)
              EventSynchronizer.getInstance().triggerEvent(subject);
          }
          else
            return false;
        }
        catch (DataSourceException dsex) {
          showErrorMessage(dsex.getMessage());
          return false;
        }
        break
            ;
      case TmplFormModes.MODE_FIND:
        try {
          dataSource.find();
        }
        catch (DataSourceException dsex) {
          if (dsex.getStatusCode() == dsex.NOT_FOUND) {
            showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.notFound"));
            setMode(TmplFormModes.MODE_SELECT);
            return true;
          }
          else
            showInformationMessage(dsex.getCause().getMessage());
          return false;
        }
        break;
    }

    setMode(TmplFormModes.MODE_SELECT);
    return true;
  }

  protected void doCancel() {
    if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_FIND) {
      try {
        if (dataSource.first()) {
          // set mode to VIEW
          setMode(TmplFormModes.MODE_SELECT);
        }
        else {
          doInsert();
        }
      }
      catch (DataSourceException dsex) {
        showErrorMessage(dsex.getMessage());
      }

    }
    else {
      setMode(TmplFormModes.MODE_SELECT);
      refresh();
    }
  }

  protected void doNext() {
    try {
      dataSource.next();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.LAST_RECORD)
        showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.last"));
      else
        showErrorMessage(dsex.getException().getMessage());
    }
  }

  protected void doPrevious() {
    try {
      dataSource.previous();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.FIRST_RECORD)
        showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.first"));
      else
        showErrorMessage(dsex.getException().getMessage());
    }
  }

  protected void doAll() {
    Component parent = getParent();
    while (parent != null && !(parent instanceof JFrame))
      parent = parent.getParent();
    TmplDlgLookup2 dlgAll = new TmplDlgLookup2((parent != null) ? (JFrame)parent : null, TmplResourceSingleton.getString("dialog.header.list"), allHeaders, new AllDSFilter(dataSource));
  }

  protected void doFind() {
    setMode(TmplFormModes.MODE_FIND);
  }

  protected void doFindRes() {
    try {
      if (dataSource.getLastFind() != null) {
        Component parent = getParent();
        while (parent != null && !(parent instanceof JFrame))
          parent = parent.getParent();
        TmplDlgLookup2 dlgAll = new TmplDlgLookup2((parent != null) ? (JFrame)parent : null, TmplResourceSingleton.getString("dialog.header.list"), allHeaders, new LastFindDSFilter(dataSource));
      }
      else {
        showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.resNotFound"));
      }
    }
    catch (IllegalArgumentException ex) {
    }
    catch (DataSourceException ex) {
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

  public boolean setPrimaryKey(Object keyValue) {
    return keyValue == null;
  }
}
