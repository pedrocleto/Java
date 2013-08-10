package pt.inescporto.template.client.web;

import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.EventListenerList;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.web.util.*;

public abstract class TmplAppletNavForm extends TmplAppletBasicForm implements ActionListener {
  protected int mode;
  protected boolean isMaster = true;
  protected JComponent[] detailLinks = null;
  private String linkCondition = null;
  private Collection lastFind = null;
  private EventListenerList listenerList = new EventListenerList();
  private EventListenerList componentListenerList = new EventListenerList();
  private TemplateEvent templateEvent = null;
  public String[] allHeaders = new String[] {};

  public TmplAppletNavForm() {
    super();
  }

  public void start() {
    // broadcast start for child objects
    fireTemplateInitialize();

    try {
      // get permissions for this for based on service <code>permFormId</code>
      doGetPermissions();

      // stop if permissions equals NONE
      if ((int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0)
        stop();

      // get Attributes for connection with components
      doGetAttrs();
    }
    catch (Exception ex) {
    }

    // set mode to VIEW
    setMode(TmplFormModes.MODE_SELECT);

    if (doSend(TmplMessages.MSG_FIRST).getReturnCode() != TmplMessages.MSG_OK) { // find first
      if ((int)(perms.getFormPerms() & TmplPerms.PERM_INSERT) != 0)
        doInsert();
      else {
        getRootPane().setVisible(false);
        showInformationMessage("Não existem dados para vizualização !");
        stop();
      }
    }
  }

  protected void refresh() {
    fireTemplateRefresh();
  }

  protected boolean doValidate() {
    return fireTemplateRequired();
  }

  protected void save() {
    fireTemplateSave();
  }

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
    else if (e.getActionCommand().equals("NEXT"))
      doNext();
    if (e.getActionCommand().equals("PREV"))
      doPrevious();
    if (e.getActionCommand().equals("ALL"))
      doAll();
    if (e.getActionCommand().equals("LINK")) {
      try {
        doSendLinkCondition(((TmplJButtonLink)e.getSource()).getLinks());
        (getAppletContext()).showDocument(new URL(((TmplJButtonLink)e.getSource()).getUrl()));
      }
      catch (java.net.MalformedURLException ev) {
        ev.printStackTrace();
      }
    }
    if (e.getActionCommand().equals("LINK_MENU")) {
      try {
        doSendLinkCondition(((TmplJButtonLinkMenu)e.getSource()).getLinks());

        (getAppletContext()).showDocument(new URL(urlBase + "/MenuController?MENUID=" + ((TmplJButtonLinkMenu)e.getSource()).getMenuId()), "content");
      }
      catch (java.net.MalformedURLException ev) {
        ev.printStackTrace();
      }
    }
    if (e.getActionCommand().equals("FIND"))
      doFind();
    if (e.getActionCommand().equals("FINDRES"))
      doFindRes();
    if (e.getActionCommand().equals("REPORT"))
      doReport();
  }

  protected void setMode(int iMode) {
    mode = iMode;
    fireTemplateMode();
  }

  protected void doInsert() {
    setMode(TmplFormModes.MODE_INSERT);

    // cleanup the attrs
    Field[]       fld;

    fld = attrs.getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
        test = fld[i].get(attrs);
        // process template objects
        if (test instanceof pt.inescporto.template.elements.TplObject) {
          if (!((TplObject)test).isLinkKey())
            ((TplObject)test).resetValue();
        }
      }
      catch (IllegalArgumentException ex) {
      }
      catch (IllegalAccessException ex) {
      }
    }
  }

  protected void doUpdate() {
    setMode(TmplFormModes.MODE_UPDATE);
  }

  protected void doDelete() {
    TmplHttpMessage lastMsg;
    if (showOkCancelMessage(TmplResourceSingleton.getString("info.dialog.header.question"),
                            TmplResourceSingleton.getString("info.dialog.msg.delete")) == JOptionPane.OK_OPTION) {
      lastMsg = doSend(TmplMessages.MSG_DELETE);
      if (lastMsg.getReturnCode() == TmplMessages.USR_ERROR) {
        showErrorMessage(lastMsg.getErrorMsg());
      }
      else if (lastMsg.getReturnCode() != TmplMessages.MSG_OK) {
        doInsert();
      }
    }
  }

  protected boolean doSave() {
    TmplHttpMessage lastMsg;
    if (mode != TmplFormModes.MODE_FIND && !doValidate())
      return false;

    save();

    switch (mode) {
      case TmplFormModes.MODE_INSERT:
        lastMsg = doSend(TmplMessages.MSG_INSERT);
        if (lastMsg.getReturnCode() != TmplMessages.MSG_OK) {
          showErrorMessage(lastMsg.getErrorMsg());
          return false;
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        lastMsg = doSend(TmplMessages.MSG_UPDATE);
        if (lastMsg.getReturnCode() != TmplMessages.MSG_OK) {
          showErrorMessage(lastMsg.getErrorMsg());
          return false;
        }
        break;
      case TmplFormModes.MODE_FIND:
        lastMsg = doSend(TmplMessages.MSG_FIND);
        if (lastMsg.getReturnCode() != TmplMessages.MSG_OK) {
          showErrorMessage(lastMsg.getErrorMsg());
          return false;
        }
        break;
    }

    this.doSaveAfter();
    setMode(TmplFormModes.MODE_SELECT);
    return true;
  }

  protected void doCancel() {
    if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_FIND) {
      if (doSend(TmplMessages.MSG_FIRST).getReturnCode() != TmplMessages.MSG_OK) // find first
        doInsert();
      else
        setMode(TmplFormModes.MODE_SELECT);
    }
    else {
      setMode(TmplFormModes.MODE_SELECT);
      refresh();
    }
  }

  protected void doNext() {
    if (doSend(TmplMessages.MSG_FORWARD).getReturnCode() == TmplMessages.MSG_NOTFOUND)
      showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.last"));
  }

  protected void doPrevious() {
    if (doSend(TmplMessages.MSG_BACKWARD).getReturnCode() == TmplMessages.MSG_NOTFOUND)
      showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.first"));
  }

  protected void doAll() {
    TmplDlgLookup dlgAll = new TmplDlgLookup(null, TmplResourceSingleton.getString("dialog.header.list"), getURL(), allHeaders, fireTemplateGetComponents(), isMaster);

    doSend(TmplMessages.MSG_FIND_PK);
  }

  protected void doFind() {
    setMode(TmplFormModes.MODE_FIND);
  }

  protected void doFindRes() {
    if (lastFind == null || lastFind.isEmpty()) {
      showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.resNotFound"));
      return;
    }

    TmplDlgLookup dlgAll = new TmplDlgLookup(null, TmplResourceSingleton.getString("info.dialog.msg.result"), lastFind, new String[] {}, fireTemplateGetComponents());

    doSend(TmplMessages.MSG_FIND_PK);
  }

  /**
   * For report handling.
   * Respondes whenever <code>TmplButtonReport</code> is pressed.
   *
   * @see TmplButtonReport
   * @see RReportHttp
   * @see RVectorSource
   * @see RArea
   */
  protected void doReport() {
  }

  protected void doSaveAfter() {
  }

  protected TmplHttpMessage doSend(int toDo) {
    TmplHttpMessage msg = new TmplHttpMessage(toDo, attrs);

    // indicates if proxy as to behave as Master or Detail
    msg.setAsMaster(isMaster);

    if (toDo == TmplMessages.MSG_SETLINK)
      msg.setLinkCondition(linkCondition);

    msg = (TmplHttpMessage)doSendObject((Object)msg);
    if (httpSender.getErrCode() == TmplMessages.MSG_OK) {
      if (toDo == TmplMessages.MSG_FIND && !msg.getFind().isEmpty()) {
        lastFind = msg.getFind();
        Iterator i = lastFind.iterator();
        attrs = i.next();
      }
      else
        attrs = msg.getAttrs();

      // process resulting values
      linkAttrs();
      refresh();
    }

    return msg;
  }

  protected int doGetAttrs() {
    return doSend(TmplMessages.MSG_INITIALIZE).getReturnCode();
  }

  protected void linkAttrs() {
    fireTemplateLinkAttrs();
  }

  // ***************************************************************************
  // Permissions control
  // ***************************************************************************

  /**
   *
   */
  protected int doGetPermissions() {
    TmplHttpMsgPerm msg = new TmplHttpMsgPerm(TmplMessages.MSG_FORMPERM, permFormId);

    msg = (TmplHttpMsgPerm)doSendObject((Object)msg);
    if (httpSenderPerms.getErrCode() == TmplMessages.MSG_OK) {
      isSupplier = msg.isSupplier();
      /**@todo correct this after fixing permissions field control!*/
//      perms = new Integer(msg.getPerms());

      // broadcast perms value
      linkPerms();
    }

    return msg.getReturnCode();
  }

  /**
   *
   */
  protected void linkPerms() {
    Component[] all = getContentPane().getComponents();

    if ((int)(perms.getFormPerms() & TmplPerms.PERM_SHOW) == 0) {
      for (int i = 0; i < all.length; i++)
        all[i].setVisible(false);
      showErrorMessage(TmplResourceSingleton.getString("error.msg.perms"));
    }
    else {
      // broadcast perms
      fireTemplateFormPermission(perms.getFormPerms());
    }
  }

  // ***************************************************************************
  //                         Link condition Control
  // ***************************************************************************

  /**
   *
   */
  protected void setLinkCondition(String linkCondition) {
    this.linkCondition = linkCondition;
  }

  /**
   *
   */
  protected void doSendLinkCondition(JComponent[] links) {
    String dinamicLinkCondition = "";
    Vector binds = new Vector();

    if (links == null)
      return;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField) {
        dinamicLinkCondition += ((TmplJTextField)links[i]).getField() + " = ? ";
        binds.add(((TmplJTextField)links[i]).getLink());
      }
      dinamicLinkCondition += " AND ";
    }

    // trim string to a valid where statment
    dinamicLinkCondition = dinamicLinkCondition.substring(0, dinamicLinkCondition.length() - 5);

    // initialize http message
    TmplHttpMessage msg = new TmplHttpMessage(TmplMessages.MSG_SETLINK);
    if (this.linkCondition == null || this.linkCondition.length() == 0)
      msg.setLinkCondition(dinamicLinkCondition);
    else
      msg.setLinkCondition(this.linkCondition + " AND " + dinamicLinkCondition);

    msg.setBinds(binds);

    // send value
    try {
      httpSender.doSendObject((Object)msg);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *
   */
  protected String getLinkConditionStmt(JComponent[] links) {
    String dinamicLinkCondition = "";

    if (links == null)
      return null;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField)
        dinamicLinkCondition += ((TmplJTextField)links[i]).getField() + " = ? ";
      dinamicLinkCondition += " AND ";
    }

    // trim string to a valid where statment
    dinamicLinkCondition = dinamicLinkCondition.substring(0, dinamicLinkCondition.length() - 5);

    return dinamicLinkCondition;
  }

  protected Vector getLinkConditionBinds(JComponent[] links) {
    Vector binds = new Vector();

    if (links == null)
      return null;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField)
        binds.add(((TmplJTextField)links[i]).getLink());
    }

    return binds;
  }

  // ***************************************************************************
  //                        Template listener control
  // ***************************************************************************

  public void addTemplateListener(DataSourceListener l) {
    listenerList.add(DataSourceListener.class, l);
  }

  public void removeTemplateListener(DataSourceListener l) {
    listenerList.remove(DataSourceListener.class, l);
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> initialize events.
   * Lazily creates the event.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateInitialize() {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == DataSourceListener.class) {
        templateEvent = new TemplateEvent(this);
        ((DataSourceListener)listeners[i + 1]).tmplInitialize(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> refresh events.
   * Lazily creates the event.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateRefresh() {
    // get links
    String linkCondition = getLinkConditionStmt(detailLinks);
    Vector binds = getLinkConditionBinds(detailLinks);

    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == DataSourceListener.class) {
        templateEvent = new TemplateEvent(this);
        if (detailLinks != null) {
          templateEvent.setLinkCondition(linkCondition);
          templateEvent.setBinds(binds);
        }
        ((DataSourceListener)listeners[i + 1]).tmplRefresh(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> save events.
   * Lazily creates the event.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateSave() {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == DataSourceListener.class) {
        templateEvent = new TemplateEvent(this);
        ((DataSourceListener)listeners[i + 1]).tmplSave(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> enable events.
   * Creates the event based on parameter enable.
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
        templateEvent = new TemplateEvent(this);
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
        templateEvent = new TemplateEvent(this);
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
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected boolean fireTemplateRequired() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
        templateEvent = new TemplateEvent(this);
        if (!((TemplateComponentListener)listeners[i + 1]).tmplRequired(templateEvent)) {
          try {
            showErrorMessage(
                TmplResourceSingleton.getString("error.msg.required.field") +
                ((TmplGetter)templateEvent.getCompFailed()).getLabelName() +
                TmplResourceSingleton.getString("error.msg.required.end"));
          }
          catch (Exception ex) {
          }
          return false;
        }
      }
    }
    return true;
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
        templateEvent = new TemplateEvent(this, permission);
        /**@todo fix the permissions field list ASAP */
//        templateEvent.setPermFieldList(perms.);
        ((TemplateComponentListener)listeners[i + 1]).tmplPermissions(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> link attributes events.
   * Lazily creates the event.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateLinkAttrs() {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    Field[] fld;

    fld = attrs.getClass().getDeclaredFields();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == DataSourceListener.class) {
        for (int j = 0; j < fld.length; j++) {
          Object test = null;
          try {
            test = fld[j].get(attrs);
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
          // process template objects
          if (test instanceof pt.inescporto.template.elements.TplObject) {
            templateEvent = new TemplateEvent(this);
            templateEvent.setLink((TplObject)test);
            ((DataSourceListener)listeners[i + 1]).tmplLink(templateEvent);
          }
        }
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> get component.
   * Lazily creates the event.
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

  // ***************************************************************************
  //                        Template listener control
  // ***************************************************************************

  public synchronized void addTemplateComponentListener(TemplateComponentListener l) {
    componentListenerList.add(TemplateComponentListener.class, l);
  }

  public synchronized void removeTemplateComponentListener(TemplateComponentListener l) {
    componentListenerList.remove(TemplateComponentListener.class, l);
  }

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
}
