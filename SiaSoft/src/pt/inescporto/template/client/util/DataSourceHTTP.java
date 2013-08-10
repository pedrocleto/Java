package pt.inescporto.template.client.util;

import javax.swing.event.EventListenerList;
import pt.inescporto.template.client.event.DataSourceListener;
import java.util.Vector;
import java.util.Collection;
import pt.inescporto.template.client.design.TmplException;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.TmplHttpMessageSender;
import pt.inescporto.template.web.util.TmplHttpMessage;
import java.util.Iterator;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.Hashtable;
import java.lang.reflect.Field;
import java.awt.Cursor;
import pt.inescporto.template.client.design.TmplResourceSingleton;

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
public class DataSourceHTTP implements DataSourceListener, DataSource {
  private EventListenerList listenerList = new EventListenerList();
  private String name = null;

  protected String url = null;
  protected String urlBase = null;
  protected Object attrs = null;

  // for HTTP Propose
  protected TmplHttpMessageSender httpSender = null;
  protected TmplHttpMessageSender httpSenderPerms = null;

  // linkcondition parameters
  protected String linkConditionStmt = null;
  protected Vector binds = null;

  // DataSources
  Vector<DSRelation> relations = null;

  // helper
  public Collection all = null;
  Collection lastFind = null;

  public DataSourceHTTP(String name, String urlBase) {
    this.name = name;
    this.urlBase = urlBase;
  }

  // ***************************************************************************
  //                        Java Bean set and get
  // ***************************************************************************

  public void setUrl(String url) {
    this.url = url;

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
    httpSender = new TmplHttpMessageSender(url);
    httpSenderPerms = new TmplHttpMessageSender(urlBase + "/PermsProxy");
  }

  protected TmplHttpMessage doSend(int toDo) throws TmplException {
    TmplHttpMessage msg = new TmplHttpMessage(toDo, attrs);

    // indicates if proxy as to behave as Master or Detail
//    msg.setAsMaster(isMaster);

    if (toDo == TmplMessages.MSG_SETLINK)
      msg.setLinkCondition(linkConditionStmt);

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
      fireTemplateLinkAttrs();
      fireTemplateRefresh();
    }

    return msg;
  }

  protected Object doSendObject( Object msg ) throws TmplException {
    if( msg instanceof pt.inescporto.template.web.util.TmplHttpMsgPerm ) {
      msg = httpSenderPerms.doSendObject( msg );
      switch( httpSender.getErrCode() ) {
        case TmplMessages.CERR_IO :
          throw new TmplException(TmplMessages.CERR_IO);
//          showErrorMessage( TmplResourceSingleton.getString("error.msg.connection") );
        case TmplMessages.CERR_CLASSNOTFOUND :
          throw new TmplException(TmplMessages.CERR_CLASSNOTFOUND);
        case TmplMessages.CERR_MALFORMEDURL :
          throw new TmplException(TmplMessages.CERR_MALFORMEDURL);
//          showErrorMessage( TmplResourceSingleton.getString("error.msg.url") );
      }
    }
    else {
      msg = httpSender.doSendObject( msg );
      switch( httpSender.getErrCode() ) {
        case TmplMessages.CERR_IO :
          throw new TmplException(TmplMessages.CERR_IO);
//          showErrorMessage( TmplResourceSingleton.getString("error.msg.connection") );
        case TmplMessages.CERR_CLASSNOTFOUND :
          throw new TmplException(TmplMessages.CERR_CLASSNOTFOUND);
        case TmplMessages.CERR_MALFORMEDURL :
          throw new TmplException(TmplMessages.CERR_MALFORMEDURL);
//          showErrorMessage( TmplResourceSingleton.getString("error.msg.url") );
      }
    }

    return msg;
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
//        System.out.println("linkCondition <" + lcn.getStatement() + ">");
//        System.out.println("Binds <" + ((TplString)lcn.getBinds().elementAt(0)).getField() + " = " + ((TplString)lcn.getBinds().elementAt(0)).getValue() + ">");
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
