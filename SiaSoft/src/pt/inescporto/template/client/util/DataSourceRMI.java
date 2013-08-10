package pt.inescporto.template.client.util;

import pt.inescporto.template.client.rmi.TmplEJBLocater;
import javax.naming.NamingException;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.client.design.TmplException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.elements.TplInt;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.event.TemplateEvent;
import javax.swing.event.EventListenerList;
import java.util.Iterator;
import java.util.Hashtable;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.elements.TplTimestamp;
import java.sql.Timestamp;

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
public class DataSourceRMI implements DataSourceListener, DataSource {
  private EventListenerList listenerList = new EventListenerList();
  private String name = null;

  protected String url = null;
  protected Object attrs = null;

  // for RMI proposes
  protected TmplEJBLocater ejbLocator = null;
  protected Class[] createParamsType = null;
  protected Object[] createParamsValue = null;
  protected Class[] parametersType = null;
  protected Object home = null;
  protected Object remote = null;

  // linkcondition parameters
  protected String linkConditionStmt = null;
  protected Vector binds = null;

  // DataSources
  Vector<DSRelation> relations = null;

  // helper
  public Collection all = null;
  Collection lastFind = null;

  public DataSourceRMI(String name) {
    this.name = name;
  }

  // ***************************************************************************
  //                        Java Bean set and get
  // ***************************************************************************

  public void setUrl(String url) {
    this.url = url;
    try {
      connect();
      doSend(TmplMessages.MSG_INITIALIZE);
      linkConditionStmt = null;
      binds = null;
      doSend(TmplMessages.MSG_SETLINK);
    }
    catch (TemplateException ex) {
      ex.printStackTrace();
    }
    catch (TmplException ex) {
      ex.printStackTrace();
    }
  }

  public void setUrl(String url, Class[] createParamsType, Object[] createParamsValue) {
    this.url = url;
    this.createParamsType = createParamsType;
    this.createParamsValue = createParamsValue;

    try {
      connect();
      doSend(TmplMessages.MSG_SETLINK);
      doSend(TmplMessages.MSG_INITIALIZE);
    }
    catch (TemplateException ex) {
      ex.printStackTrace();
    }
    catch (TmplException ex) {
      ex.printStackTrace();
    }
  }

  public void setStaticLinkCondition(String staticLinkCondition) {
  }

  public void setStaticBinds(Vector staticBinds) {
  }

  // ***************************************************************************
  //                          Connection Control
  // ***************************************************************************

  protected void connect() throws TemplateException {
    try {
      ejbLocator = TmplEJBLocater.getInstance();
      home = ejbLocator.getEJBHome(url);
      remote = ejbLocator.getEJBRemote(url, "create", createParamsType, createParamsValue);
    }
    catch (NamingException ex) {
      throw new TemplateException("Error in naming " + url, ex);
    }
  }

  protected void doSend(int toDo) throws TmplException {
    Method[] methodList = remote.getClass().getMethods();
    Method execMethod = null;

//    System.out.println("Datasource name is <" + name + "> and ToDo number is <" + toDo + ">");

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
              attrsAreSet(attrs);
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
	  return;
	case TmplMessages.MSG_SETLINK:
	  execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
	  execMethod.invoke(remote, new Object[] {linkConditionStmt, binds});
	  return;
	case TmplMessages.MSG_FIRST:
	  Field[] fld;
	  fld = attrs.getClass().getDeclaredFields();
	  for (int i = 0; i < fld.length; i++) {
	    if (fld[i].getType() == TplString.class)
	      ((TplString)fld[i].get(attrs)).setValue(" ");
            if (fld[i].getType() == TplInt.class)
              ((TplInt)fld[i].get(attrs)).setValue(new Integer(0));
            if (fld[i].getType() == TplTimestamp.class)
              ((TplTimestamp)fld[i].get(attrs)).setValue(new Timestamp(0));
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

          // find and execute getData()
          execMethod = remote.getClass().getMethod("getData");
          attrs = execMethod.invoke(remote);
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
	  all = (Collection)execMethod.invoke(remote, new Object[] {attrs});

	  if (!all.isEmpty()) {
	    lastFind = all;
	    Iterator i = lastFind.iterator();
	    attrs = i.next();
	  }
	  else
	    lastFind = null;
	  break;
        case TmplMessages.MSG_LISTALL:
          execMethod = remote.getClass().getMethod("listAll");
          all = (Collection)execMethod.invoke(remote);
          return;
      }
    }
    catch (InvocationTargetException ex1) {
      if (ex1.getTargetException().getClass() == RowNotFoundException.class) {
	TmplException tmplex = new TmplException(TmplMessages.MSG_NOTFOUND, ex1.getTargetException());
	throw tmplex;
      }
      if (ex1.getTargetException().getClass() == DupKeyException.class) {
	TmplException tmplex = new TmplException(TmplMessages.MSG_DUPKEY, ex1.getTargetException());
	throw tmplex;
      }
      if (ex1.getTargetException().getClass() == ConstraintViolatedException.class) {
        TmplException tmplex = new TmplException(TmplMessages.MSG_CONSTRAINTD, ex1.getTargetException());
        throw tmplex;
      }
      if (ex1.getTargetException().getClass() == UserException.class) {
        TmplException tmplex = new TmplException(TmplMessages.USR_ERROR, ex1.getTargetException());
        throw tmplex;
      }
      if (ex1.getTargetException().getClass() == RemoteException.class) {
	/** @todo get a new home and remote interface of EJB. Container has
	 * destroyed this one. For now try a dispose all*/
	System.exit( -1);
      }
      else {
        ex1.printStackTrace();
	throw new TmplException("Unknow Exception");
      }
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
    fireTemplateLinkAttrs();
    fireTemplateRefresh();
  }

  public void attrsAreSet(Object attrs) {
  }
  // ***************************************************************************
  //                        Helper methods
  // ***************************************************************************
  private TplObject getTplObjectByField(String field) {
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
          if (((TplObject)test).getField().equalsIgnoreCase(field))
            return (TplObject)test;
        }
      }
      catch (IllegalArgumentException ex) {
        ex.printStackTrace();
      }
      catch (IllegalAccessException ex) {
        ex.printStackTrace();
      }
    }

    return null;
  }

  // ***************************************************************************
  //                        Template listener control
  // ***************************************************************************

  public synchronized void addDatasourceListener(DataSourceListener l) {
    listenerList.add(DataSourceListener.class, l);
  }

  public synchronized void removeDatasourceListener(DataSourceListener l) {
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
	TemplateEvent templateEvent = new TemplateEvent(this);
	try {
	  ((DataSourceListener)listeners[i + 1]).tmplInitialize(templateEvent);
	}
	catch (Exception ex) {
          ex.printStackTrace();
	}
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
    Hashtable<String, LinkConditionNode> linkList = new Hashtable<String,LinkConditionNode>();

    // is there any relations with this data set ?
    if (relations != null) {
      // ok, lets make a link pair for every one of theme
      for (DSRelation rel : relations) {
        String linkCondition = "";
        Vector binds = new Vector();
	for (RelationKey keyPair : rel.relationKeys) {
	  linkCondition += keyPair.getDatailKey() + " = ? AND ";
          // we nead to clone this object and add it to the binds
          TplObject tplClonnable = getTplObjectByField(keyPair.getMasterKey());
	  TplObject tplClone = null;
	  try {
	    tplClone = tplClonnable.getClass().newInstance();
	  }
	  catch (IllegalAccessException ex) {
            ex.printStackTrace();
	  }
	  catch (InstantiationException ex) {
            ex.printStackTrace();
	  }
          tplClone.setindex(tplClonnable.getIndex());
          tplClone.setField(keyPair.getDatailKey());
          tplClone.setKeyType(tplClonnable.getKeyType());
          tplClone.setValueAsObject(tplClonnable.getValueAsObject());
          binds.add(tplClone);
	}
        linkCondition = linkCondition.substring(0,linkCondition.length()-5);
        linkList.put("" + rel.getDetail(), new LinkConditionNode(linkCondition.equals("") ? null : linkCondition, binds.size() == 0 ? null : binds));
      }
    }
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == DataSourceListener.class) {
	TemplateEvent templateEvent = new TemplateEvent(this);
	if (relations != null) {
	  templateEvent.setLinkList(linkList);
	}
	try {
	  ((DataSourceListener)listeners[i + 1]).tmplRefresh(templateEvent);
	}
	catch (Exception ex1) {
          ex1.printStackTrace();
	}
      }
    }
  }

  protected void fireTemplateRefresh(Object exception) {
    // get links
    Hashtable<String, LinkConditionNode> linkList = new Hashtable<String,LinkConditionNode>();

    // is there any relations with this data set ?
    if (relations != null) {
      // ok, lets make a link pair for every one of theme
      for (DSRelation rel : relations) {
        String linkCondition = "";
        Vector binds = new Vector();
        for (RelationKey keyPair : rel.relationKeys) {
          linkCondition += keyPair.getDatailKey() + " = ? AND ";
          // we nead to clone this object and add it to the binds
          TplObject tplClonnable = getTplObjectByField(keyPair.getMasterKey());
          TplObject tplClone = null;
          try {
            tplClone = tplClonnable.getClass().newInstance();
          }
          catch (IllegalAccessException ex) {
            ex.printStackTrace();
          }
          catch (InstantiationException ex) {
            ex.printStackTrace();
          }
          tplClone.setindex(tplClonnable.getIndex());
          tplClone.setField(keyPair.getDatailKey());
          tplClone.setKeyType(tplClonnable.getKeyType());
          tplClone.setValueAsObject(tplClonnable.getValueAsObject());
          binds.add(tplClone);
        }
        linkCondition = linkCondition.substring(0,linkCondition.length()-5);
        linkList.put("" + rel.getDetail(), new LinkConditionNode(linkCondition.equals("") ? null : linkCondition, binds.size() == 0 ? null : binds));
      }
    }
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == DataSourceListener.class) {
        TemplateEvent templateEvent = new TemplateEvent(this);
        if (relations != null) {
          templateEvent.setLinkList(linkList);
        }
        if (!exception.equals((DataSourceListener)listeners[i + 1])) {
	  try {
	    ((DataSourceListener)listeners[i + 1]).tmplRefresh(templateEvent);
	  }
	  catch (Exception ex1) {
            ex1.printStackTrace();
	  }
	}
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
	TemplateEvent templateEvent = new TemplateEvent(this);
	try {
	  ((DataSourceListener)listeners[i + 1]).tmplSave(templateEvent);
	}
	catch (Exception ex) {
          ex.printStackTrace();
	}
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
	    TemplateEvent templateEvent = new TemplateEvent(this);
	    templateEvent.setLink((TplObject)test);
	    try {
	      ((DataSourceListener)listeners[i + 1]).tmplLink(templateEvent);
	    }
	    catch (Exception ex1) {
              ex1.printStackTrace();
	    }
	  }
	}
      }
    }
  }

  // ***************************************************************************
  //                     Implementation of Template Listener
  //
  // we can also responde to other DataSources
  // ***************************************************************************

  public void tmplInitialize(TemplateEvent e) {
//    System.out.println("datasource tmplInititialize");
    try {
      connect();
    }
    catch (TemplateException ex) {
      ex.printStackTrace();
    }
    fireTemplateInitialize();
  }

  public void tmplRefresh(TemplateEvent e) {
//    System.out.println("datasource tmplRefresh:");

    LinkConditionNode lcn = e.getLinkConditionNode("" + this);

    if (lcn != null) {
      try {
/*        System.out.println("linkCondition <" + lcn.getStatement() + ">");
        Vector<TplObject> binds = (Vector<TplObject>)lcn.getBinds();
        for (TplObject tplBind : binds) {
          System.out.println("Binds <" + tplBind.getField() + " = " + tplBind.getValueAsObject() + ">");
        }*/
	setLinkCondition(lcn.getStatement(), lcn.getBinds());
        doSend(TmplMessages.MSG_SETLINK);
        first();
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }
      catch (TmplException ex) {
        ex.printStackTrace();
      }
    }
  }

  public void tmplSave(TemplateEvent e) {
//    System.out.println("datasource tmplSave");
  }

  public void tmplLink(TemplateEvent e) {
//    System.out.println("datasource tmplLink");
  }

  /** *******************************************************
   ** Implementation of DataSource
   ** *******************************************************
   */
  /**
   *
   * @param order int
   * @throws DataSourceException
   */
  public void setSortOrder(int order) throws DataSourceException {

  }

  public void setLinkCondition(String linkConditionStmt, Vector binds) throws DataSourceException {
    this.linkConditionStmt = linkConditionStmt;
    this.binds = binds;
    first();
  }

  public String getDataSourceName() throws DataSourceException {
    return name;
  }

  public void addDataSourceLink(DSRelation dsRelation) throws DataSourceException {
    if (relations == null)
      relations = new Vector<DSRelation>();
    relations.add(dsRelation);
    addDatasourceListener((DataSourceListener)dsRelation.getDetail());
  }

  public DataSource getDataSourceByName(String dsName) throws DataSourceException {
    if (getDataSourceName().equals(dsName))
      return this;
    else {
      if (relations == null)
        return null;
      for (DSRelation rel : relations) {
        DataSource detail = rel.getDetail();
	DataSource detailDs = detail.getDataSourceByName(dsName);
        if (detailDs != null)
	  return detailDs;
      }
    }
/*    for (DSRelation rel : relations) {
      if (rel.getDetail().getDataSourceName().equals(dsName))
        return rel.getDetail();
    }*/
    return null;
  }

  public void initialize() throws DataSourceException {
    fireTemplateInitialize();
  }

  public void refresh() throws DataSourceException {
    fireTemplateRefresh();
  }

  public void refresh(Object exception) throws DataSourceException {
    fireTemplateRefresh(exception);
  }

  public void insert() throws DataSourceException {
    // broadcast save to Template Components
    fireTemplateSave();

    try {
      doSend(TmplMessages.MSG_INSERT);
    }
    catch (TmplException tmplex) {
      System.out.println(tmplex.getDetail().getMessage());
      throw new DataSourceException(tmplex.getDetail());
    }
  }

  public void update() throws DataSourceException {
    // broadcast save to Template Components
    fireTemplateSave();

    try {
      doSend(TmplMessages.MSG_UPDATE);
    }
    catch (TmplException tmplex) {
      if( tmplex.getDetail() != null )
        System.out.println(tmplex.getDetail().getMessage());
      else
        System.out.println(tmplex.getMessage());
      return;
    }
  }

  public void delete() throws DataSourceException {
    try {
      doSend(TmplMessages.MSG_DELETE);
      doSend(TmplMessages.MSG_FIRST);
    }
    catch (TmplException ex) {
      throw new DataSourceException(ex.getDetail());
    }
  }

  public boolean first() throws DataSourceException {
    try {
      doSend(TmplMessages.MSG_SETLINK);
      doSend(TmplMessages.MSG_FIRST);
    }
    catch (TmplException ex) {
      fireTemplateLinkAttrs();
      fireTemplateRefresh();
      return false;
    }
    return true;
  }

  public boolean next() throws DataSourceException {
    try {
      doSend(TmplMessages.MSG_FORWARD);
    }
    catch (TmplException ex) {
      if( ex.getErrorCode() == TmplMessages.MSG_NOTFOUND )
        throw new DataSourceException(DataSourceException.LAST_RECORD);
      else
        throw new DataSourceException(ex.getDetail().getMessage());
    }
    return true;
  }

  public boolean previous() throws DataSourceException {
    try {
      doSend(TmplMessages.MSG_BACKWARD);
    }
    catch (TmplException ex) {
      if( ex.getErrorCode() == TmplMessages.MSG_NOTFOUND )
        throw new DataSourceException(DataSourceException.FIRST_RECORD);
      else
        throw new DataSourceException(ex.getDetail().getMessage());
    }
    return true;
  }

  public boolean last() throws DataSourceException {
    return true;
  }

  public void findByPK(Object record) throws DataSourceException {
    attrs = record;
    try {
      doSend(TmplMessages.MSG_FIND_PK);
    }
    catch (TmplException ex) {
      throw new DataSourceException(ex.getDetail());
    }
  }

  public void findExact() throws DataSourceException {

  }

  public void find() throws DataSourceException {
    // broadcast save to Template Components
    fireTemplateSave();

    try {
      doSend(TmplMessages.MSG_FIND);
    }
    catch (TmplException tmplex) {
      first();
      if (tmplex.getErrorCode() != TmplMessages.MSG_OK) {
        throw new DataSourceException(DataSourceException.NOT_FOUND);
      }
      else
        throw new DataSourceException(tmplex);
    }
  }

  public Collection listAll() throws DataSourceException {
    try {
      doSend(TmplMessages.MSG_SETLINK);
      doSend(TmplMessages.MSG_LISTALL);
    }
    catch (TmplException ex) {
      throw new DataSourceException(ex);
    }
    return all;
  }

  public Collection getLastFind() throws DataSourceException {
    return lastFind;
  }

  public void cleanUpAttrs() throws DataSourceException {
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
          else {
            for (int j = 0; binds != null && j < binds.size(); j++) {
              TplObject element = (TplObject)binds.elementAt(j);
              if (element.getField().equalsIgnoreCase(((TplObject)test).getField())) {
                ((TplObject)test).setValueAsObject(element.getValueAsObject());
              }
            }
          }
	}
      }
      catch (IllegalArgumentException ex) {
        throw new DataSourceException(ex);
      }
      catch (IllegalAccessException ex) {
        throw new DataSourceException(ex);
      }
    }
    fireTemplateRefresh();
  }

  public Object getAttrs() throws DataSourceException {
    Object newInstance = null;
    try {
      newInstance = attrs.getClass().newInstance();
    }
    catch (IllegalAccessException ex) {
      throw new DataSourceException(ex);
    }
    catch (InstantiationException ex) {
      throw new DataSourceException(ex);
    }

    if (binds != null && binds.size() != 0) {
      Field[] fld;

      fld = newInstance.getClass().getDeclaredFields();

      // process any field
      for (int i = 0; i < fld.length; i++) {
	Object test = null;
	try {
	  test = fld[i].get(newInstance);
	  // process template objects
	  if (test instanceof pt.inescporto.template.elements.TplObject) {
	    for (int j = 0; j < binds.size(); j++) {
	      TplObject element = (TplObject)binds.elementAt(j);
	      if (element.getField().equalsIgnoreCase(((TplObject)test).getField())) {
		((TplObject)test).setValueAsObject(element.getValueAsObject());
	      }
	    }
	  }
	}
	catch (IllegalArgumentException ex) {
	  throw new DataSourceException(ex);
	}
	catch (IllegalAccessException ex) {
	  throw new DataSourceException(ex);
	}
      }
    }

    return newInstance;
  }

  public Object getCurrentRecord() throws DataSourceException {
    return attrs;
  }

  public void setAttrs(Object attrs) throws DataSourceException {
    if (attrs != null && this.attrs != null) {
      if (this.attrs.getClass().equals(attrs.getClass())) {
	this.attrs = attrs;
      }
      else
	throw new DataSourceException("Not of the same type");
    }
  }

  public void reLinkAttrs() throws DataSourceException {
    fireTemplateLinkAttrs();
  }
}
