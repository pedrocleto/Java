package pt.inescporto.template.client.rmi;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.TmplFormModes;
import javax.swing.JOptionPane;
import javax.naming.NamingException;
import pt.inescporto.template.client.design.TmplDlgLookup;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.client.event.DataSourceListener;
import java.util.Vector;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.utils.TmplMessages;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.design.TmplException;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.elements.TplInt;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JComponent;
import pt.inescporto.template.client.design.TmplGetter;
import javax.swing.event.EventListenerList;
import java.util.Collection;
import java.util.ArrayList;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import pt.inescporto.template.client.event.TemplateComponentListener;
import javax.swing.JFrame;

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
public class TmplNavPanel extends TmplBasicPanel implements ActionListener {
  private Class[] parametersType = null;
  private EventListenerList listenerList = new EventListenerList();
  private EventListenerList componentListenerList = new EventListenerList();
  private TemplateEvent templateEvent = null;

  protected Class[] createParamsType = null;
  protected Object[] createParamsValue = null;
  protected int mode;
  protected boolean isMaster = true;
  protected JComponent[] detailLinks = null;
  protected String staticLinkCondition = null;
  protected Collection lastFind = null;

  public String[] allHeaders = new String[] {};

  public TmplNavPanel() {
    super();
  }

  public void init() {
    super.init();

    try {
      ejbLocator = TmplEJBLocater.getInstance();
      home = ejbLocator.getEJBHome(url);
      remote = ejbLocator.getEJBRemote(url, "create", createParamsType, createParamsValue);
    }
    catch (NamingException ex1) {
      showErrorMessage(ex1.toString());
    }
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
      ex.printStackTrace();
    }

    // set mode to VIEW
    setMode(TmplFormModes.MODE_SELECT);

    try {
      doSend(TmplMessages.MSG_FIRST); // find first
    }
    catch (TmplException tmplex) {
      if (tmplex.getErrorCode() == TmplMessages.MSG_NOTFOUND)
	doInsert();
    }
  }

  public void setCreateParamsType(Class[] paramsType) {
    createParamsType = paramsType;
  }

  public Class[] getCreateParamsType() {
    return this.createParamsType;
  }

  public void setCreateParamsValue(Object[] paramsValue) {
    createParamsValue = paramsValue;
  }

  public Class[] getCreateParamsValue() {
    return this.createParamsType;
  }

  protected void refresh() {
    fireTemplateRefresh();
  }

  protected boolean doValidate() {
    return fireTemplateRequired();
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
    else
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
    if (e.getActionCommand().equals("REPORT"))
      doReport();
    /*    if (e.getActionCommand().equals("EXIT")) {
	  dispose();
	}*/
  }

  protected void setMode(int iMode) {
    mode = iMode;
    fireTemplateMode();
  }

  protected void doInsert() {
    setMode(TmplFormModes.MODE_INSERT);

    // cleanup the attrs
    Field[] fld;

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
    int errorCode;
    if (showOkCancelMessage(TmplResourceSingleton.getString("info.dialog.header.question"),
			    TmplResourceSingleton.getString("info.dialog.msg.delete")) == JOptionPane.OK_OPTION) {

      try {
	doSend(TmplMessages.MSG_DELETE);
	doSend(TmplMessages.MSG_FIRST);
      }
      catch (TmplException ex) {
	if (ex.getErrorCode() == TmplMessages.USR_ERROR)
	  this.showErrorMessage(ex.getDetail().getMessage());
	else
	  doInsert();
      }
    }
  }

  protected void doSave() {
    if (mode != TmplFormModes.MODE_FIND && !doValidate())
      return;

    // broadcast save to Template Components
    fireTemplateSave();

    switch (mode) {
      case TmplFormModes.MODE_INSERT:
	try {
	  saveDoitInsert();
	}
	catch (TmplException tmplex) {
	  if (tmplex.getErrorCode() == TmplMessages.MSG_DUPKEY)
	    showErrorMessage(TmplResourceSingleton.getString("error.msg.dupkey"));
	  else
	    showErrorMessage(tmplex.getDetail().getMessage());
return;
	}
	break
	    ;
      case TmplFormModes.MODE_UPDATE:
	try {
	  saveDoitUpdate();
	}
	catch (TmplException tmplex) {
	  if (tmplex.getDetail() != null)
	    showErrorMessage(tmplex.getDetail().getMessage());
	  else
	    showErrorMessage(tmplex.getMessage());
return;
	}
	break
	    ;
      case TmplFormModes.MODE_FIND:
	try {
	  doSend(TmplMessages.MSG_FIND);
	}
	catch (TmplException tmplex) {
	  if (tmplex.getErrorCode() == TmplMessages.MSG_NOTFOUND)
	    showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.notFound"));
return;
	}
	break
	    ;
    }

    this.doSaveAfter();
    setMode(TmplFormModes.MODE_SELECT);
  }

  protected void saveDoitInsert() throws TmplException {
    doSend(TmplMessages.MSG_INSERT);
  }

  protected void saveDoitUpdate() throws TmplException {
    doSend(TmplMessages.MSG_UPDATE);
  }

  protected void doCancel() {
    if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_FIND) {
      try {
	doSend(TmplMessages.MSG_FIRST); // find first
	setMode(TmplFormModes.MODE_SELECT);
      }
      catch (TmplException ex) {
	if (ex.getErrorCode() == TmplMessages.MSG_NOTFOUND)
	  doInsert();
	else
	  showErrorMessage(ex.getDetail().getMessage());
      }

    }
    else {
      setMode(TmplFormModes.MODE_SELECT);
      refresh();
    }
  }

  protected void doNext() {
    try {
      doSend(TmplMessages.MSG_FORWARD);
    }
    catch (TmplException ex) {
      if (ex.getErrorCode() == TmplMessages.MSG_NOTFOUND)
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.last"));
      else
	showErrorMessage(ex.getDetail().getMessage());
    }
  }

  protected void doPrevious() {
    try {
      doSend(TmplMessages.MSG_BACKWARD);
    }
    catch (TmplException ex) {
      if (ex.getErrorCode() == TmplMessages.MSG_NOTFOUND)
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.first"));
      else
	this.showErrorMessage(ex.getDetail().getMessage());
    }
  }

  protected void doAll() {
    Component parent = getParent();
    while (parent != null && !(parent instanceof JFrame))
      parent = parent.getParent();
    TmplDlgLookup dlgAll = new TmplDlgLookup((parent != null) ? (JFrame)parent : null, TmplResourceSingleton.getString("dialog.header.list"), getURL(), allHeaders, fireTemplateGetComponents(), remote);

    try {
      doSend(TmplMessages.MSG_FIND_PK);
    }
    catch (TmplException ex) {
    }
  }

  protected void doFind() {
    setMode(TmplFormModes.MODE_FIND);
  }

  protected void doFindRes() {
    if (lastFind == null || lastFind.isEmpty()) {
      showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.resNotFound"));
      return;
    }

    Component parent = getParent();
    while (parent != null && !(parent instanceof JFrame))
      parent = parent.getParent();
    TmplDlgLookup dlgAll = new TmplDlgLookup((parent != null) ? (JFrame)parent : null, TmplResourceSingleton.getString("info.dialog.msg.result"), lastFind, new String[] {}, fireTemplateGetComponents());

    try {
      doSend(TmplMessages.MSG_FIND_PK);
    }
    catch (TmplException ex) {
    }
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

  public void doFindExact() {
  }

  protected void doSend(int toDo) throws TmplException {
    Method[] methodList = remote.getClass().getMethods();
    Method execMethod = null;

    try {
      switch (toDo) {
	case TmplMessages.MSG_INITIALIZE:
	  for (int i = methodList.length - 1; i >= 0; i--) {
	    Method curMethod = methodList[i];
	    if (curMethod.getName().equals("setData")) {
	      parametersType = curMethod.getParameterTypes();
	      break;
	    }
	  }
	  if (parametersType != null) {
	    try {
	      attrs = parametersType[0].newInstance();
	    }
	    catch (InstantiationException ex) {
	      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
	      tmplex.setDetail(ex);
	      throw tmplex;
	    }
	    catch (IllegalAccessException ex) {
	      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
	      tmplex.setDetail(ex);
	      throw tmplex;
	    }
	  }
	  break;
	case TmplMessages.MSG_FIRST:
	  Field[] fld;
	  fld = attrs.getClass().getDeclaredFields();
	  for (int i = 0; i < fld.length; i++) {
	    if (fld[i].getType() == TplString.class)
	      ((TplString)fld[i].get(attrs)).setValue(" ");
	    if (fld[i].getType() == TplInt.class)
	      ((TplInt)fld[i].get(attrs)).setValue(new Integer(0));
	  }

	  // find and execute findNext( Object attrs )
	  execMethod = remote.getClass().getMethod("findNext", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});

	  // find and execute getData()
	  execMethod = remote.getClass().getMethod("getData");
	  attrs = execMethod.invoke(remote);
	  break;
	case TmplMessages.MSG_FIND_PK:

	  // find and execute findPrev( Object attrs )
	  execMethod = remote.getClass().getMethod("findByPrimaryKey", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});

	  // find and execute getData()
	  execMethod = remote.getClass().getMethod("getData");
	  attrs = execMethod.invoke(remote);
	  break;
	case TmplMessages.MSG_BACKWARD:

	  // find and execute findPrev( Object attrs )
	  execMethod = remote.getClass().getMethod("findPrev", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});

	  // find and execute getData()
	  execMethod = remote.getClass().getMethod("getData");
	  attrs = execMethod.invoke(remote);
	  break;
	case TmplMessages.MSG_FORWARD:

	  // find and execute findNext( Object attrs )
	  execMethod = remote.getClass().getMethod("findNext", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});

	  // find and execute getData()
	  execMethod = remote.getClass().getMethod("getData");
	  attrs = execMethod.invoke(remote);
	  break;
	case TmplMessages.MSG_INSERT:

	  // find and execute insert( Object attrs )
	  execMethod = remote.getClass().getMethod("insert", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});
	  break;
	case TmplMessages.MSG_UPDATE:

	  // find and execute update( Object attrs )
	  execMethod = remote.getClass().getMethod("update", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});
	  break;
	case TmplMessages.MSG_DELETE:

	  // find and execute delete( Object attrs )
	  execMethod = remote.getClass().getMethod("delete", parametersType);
	  execMethod.invoke(remote, new Object[] {attrs});
	  break;
	case TmplMessages.MSG_FIND:

	  // find and execute find( Object attrs )
	  execMethod = remote.getClass().getMethod("find", parametersType);
	  Collection all = (Collection)execMethod.invoke(remote, new Object[] {attrs});
	  if (!all.isEmpty()) {
	    lastFind = all;
	    Iterator i = lastFind.iterator();
	    attrs = i.next();
	  }
	  else
	    lastFind = null;
	  break;
      }
    }
    catch (InvocationTargetException ex1) {
      if (ex1.getTargetException().getClass() == RowNotFoundException.class) {
	TmplException tmplex = new TmplException(TmplMessages.MSG_NOTFOUND);
	throw tmplex;
      }
      if (ex1.getTargetException().getClass() == DupKeyException.class) {
	TmplException tmplex = new TmplException(TmplMessages.MSG_DUPKEY);
	throw tmplex;
      }
      if (ex1.getTargetException().getClass() == UserException.class) {
	TmplException tmplex = new TmplException(TmplMessages.USR_ERROR);
	tmplex.setDetail(ex1.getTargetException());
	throw tmplex;
      }
      if (ex1.getTargetException().getClass() == RemoteException.class) {
	/** @todo get a new home and remote interface of EJB. Container has
	 * destroyed this one. For now try a dispose all*/
	System.exit( -1);
      }
    }
    catch (IllegalArgumentException ex1) {
      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
      tmplex.setDetail(ex1);
      throw tmplex;
    }
    catch (IllegalAccessException ex1) {
      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
      tmplex.setDetail(ex1);
      throw tmplex;
    }
    catch (NoSuchMethodException ex2) {
      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
      tmplex.setDetail(ex2);
      throw tmplex;
    }
    catch (SecurityException ex2) {
      TmplException tmplex = new TmplException(TmplMessages.NOT_DEFINED);
      tmplex.setDetail(ex2);
      throw tmplex;
    }

    // process resulting values
    linkAttrs();
    refresh();
  }

  protected void doGetAttrs() throws TmplException {
    doSend(TmplMessages.MSG_INITIALIZE);
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
  protected void doGetPermissions() throws TmplException {
    try {
      super.doGetPermissions();
      // broadcast perms value
      linkPerms();
    }
    catch (TmplException ex) {
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
    this.staticLinkCondition = linkCondition;
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
	binds.add(((TplObject)((TmplJTextField)links[i]).getLink()));
    }

    return binds;
  }

  // ***************************************************************************
  //                        Template listener control
  // ***************************************************************************

  public synchronized void addTemplateListener(DataSourceListener l) {
    listenerList.add(DataSourceListener.class, l);
  }

  public synchronized void removeTemplateListener(DataSourceListener l) {
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
//          System.out.println((listeners[i+1]).toString());
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
        templateEvent.setPermFieldList(perms.getFieldPermList());
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
