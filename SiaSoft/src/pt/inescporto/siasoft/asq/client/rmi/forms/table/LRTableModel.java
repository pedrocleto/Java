package pt.inescporto.siasoft.asq.client.rmi.forms.table;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.TmplFormModes;
import javax.swing.JOptionPane;
import pt.inescporto.template.web.util.TmplHttpMessageSender;
import javax.swing.table.AbstractTableModel;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.client.drmi.TmplRMILocater;
import java.io.Serializable;
import javax.swing.event.TableModelEvent;
import java.util.Vector;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.elements.TplFloat;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.elements.TplLong;
import pt.inescporto.template.client.design.TmplException;
import java.util.Properties;
import pt.inescporto.template.web.util.TmplHttpMessage;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.elements.TplInt;
import java.sql.Timestamp;
import pt.inescporto.template.utils.AttrsToVector;
import javax.naming.InitialContext;
import pt.inescporto.template.elements.TplTimestamp;
import pt.inescporto.template.client.rmi.ContextPropertiesClient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

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
public class LRTableModel extends AbstractTableModel implements Serializable {
  //
  // private attributes
  //
  private String linkCondition = null;
  private Vector binds = null;
  private Object home = null;
  private Object remote = null;

  //
  // protected attributes
  //
  protected Vector dataVector = null;
  protected String[] headers = null;
  protected String[] fields = null;
  protected Vector columnIdentifiers = null;
  protected String url = null;
  protected Object attrs = null;
  protected int mode = TmplFormModes.MODE_SELECT;
  protected int rowMode = -1;
  protected String staticLinkCondition = null;
  protected int msgCode = TmplMessages.MSG_LISTALL;

  //
  // constructores
  //

  /**
   *
   */
  public LRTableModel(String url, String[] headers, String[] fields) {
    this(url, headers, fields, null, TmplMessages.MSG_LISTALL);
  }

  public LRTableModel(String url, String[] headers, String[] fields, int msgCode) {
    this(url, headers, fields, null, msgCode);
  }

  public LRTableModel(String url, String[] headers, String[] fields,
                        String staticLinkCondition) {
    this(url, headers, fields, staticLinkCondition, TmplMessages.MSG_LISTALL);
  }

  public LRTableModel(String url, String[] headers, String[] fields,
                        String staticLinkCondition, int msgCode) {
    // call default constructor
    super();

    // test args
    if (headers == null)
      throw new IllegalArgumentException("null headers");
    if (fields == null)
      throw new IllegalArgumentException("null fields");
    if (headers.length != fields.length)
      throw new IllegalArgumentException("fields and headers must have the same size");

    // save parameters
    this.url = url;
    this.headers = headers;
    this.fields = fields;
    this.staticLinkCondition = staticLinkCondition;
    this.msgCode = msgCode;

    // initialize connection to proxy and column mapping
    initialize();

    // create a vector of TplObjects for validation and identification of columns
    setColumnIdentifiers();
  }

  // methods that must be implemented from AbstractTableModel
  public int getRowCount() {
    return dataVector == null ? 0 : dataVector.size();
  }

  public int getColumnCount() {
    return headers == null ? 0 : headers.length;
  }

  /**
   * Returns an attribute value for the cell at <code>row</code>
   * and <code>column</code>.
   *
   * @param   row             the row whose value is to be queried
   * @param   column          the column whose value is to be queried
   * @return                  the value Object at the specified cell
   * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
   *               column was given
   */
  public Object getValueAt(int row, int column) {
    // if there are no rows to show
    if (dataVector == null)
      return "";

    // get row from dataVector and test if has values
    Vector rowVector = (Vector)dataVector.elementAt(row);
    if (rowVector.size() == 0)
      return "";
    else
      return rowVector.elementAt(row);
  }

  /**
   * Returns the object at the <code>row</code>
   * @param   row             the row whose value is to be queried
   * @return                  the value Object at the specified row
   * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
   */
  public Object getObjectAt(int row) {
    // if there are no rows to show
    if (dataVector == null)
      return "";

    // get row from dataVector
    Vector rowVector = (Vector)dataVector.elementAt(row);
    if (rowVector.size() == 0)
      return "";
    else
      return rowVector;
  }

  /**
   * Sets the object value for the cell at <code>column</code> and
   * <code>row</code>.  <code>aValue</code> is the new value.  This method
   * will generate a <code>tableChanged</code> notification.
   *
   * @param   aValue          the new value; this can be null
   * @param   row             the row whose value is to be changed
   * @param   column          the column whose value is to be changed
   * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
   *               column was given
   */
  public void setValueAt(Object aValue, int row, int column) {
    Vector rowVector = (Vector)dataVector.elementAt(row);
    if (rowVector.size() != 0) {
        rowVector.setElementAt(aValue, row);
    }

    // generate notification
    fireTableChanged(new TableModelEvent(this, row, row, column));
  }

  // public methods
  public String getColumnName(int columnIndex) {
    return headers[columnIndex];
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    switch (mode) {
      case TmplFormModes.MODE_SELECT:
        rowMode = -1;
        return false;
      case TmplFormModes.MODE_INSERT:
        if (getRowCount() - 1 == rowIndex) {
          if (((TplObject)columnIdentifiers.get(columnIndex)).isGenKey() ||
              ((TplObject)columnIdentifiers.get(columnIndex)).isLinkKey())
            return false;
          else
            return true;
        }
        else
          return false;
      case TmplFormModes.MODE_UPDATE:
        if (rowMode == rowIndex)
          return!((TplObject)columnIdentifiers.get(columnIndex)).isPrimaryKey();
        else
          return false;
      default:
        return false;
    }
  }

  public Class getColumnClass(int columnIndex) {
    return columnIdentifiers.elementAt(columnIndex).getClass();
  }

  /* ************************************************************************ *
   * ** TmplTableModel Methods                                             ** *
   * ************************************************************************ */

  /**
   * set message code
   */
  public void setMessageCode(int msgCode) {
    this.msgCode = msgCode;
  }

  /**
   * Set form mode
   */
  public void setFormMode(int mode) {
    this.mode = mode;
  }

  public void setRowMode(int rowMode) {
    this.rowMode = rowMode;
  }

  public String getColumnFieldName(int columnIndex) {
    return this.fields[columnIndex];
  }

  /**
   *
   */
  public Object getAttrs() {
    return attrs;
  }

  /**
   *  Adds a row to the end of the model.  The new row will contain
   *  <code>null</code> values unless <code>rowData</code> is specified.
   *  Notification of the row being added will be generated.
   *
   * @param   rowData          optional data of the row being added
   */
  public void addRow(Vector rowData) {
    if (rowData == null) {
      rowData = new Vector(1);
      rowData.add("");
    }
    else {
      rowData.setSize(getColumnCount());
    }

    if (dataVector == null)
      dataVector = new Vector();
    dataVector.addElement(rowData);

    // Generate notification
    rowMode = getRowCount() - 1;
    newRowsAdded(new TableModelEvent(this, rowMode, rowMode,
                                     TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

  /**
   *  Ensures that the new rows have the correct number of columns.
   *  This is accomplished by  using the <code>setSize</code> method in
   *  <code>Vector</code> which truncates vectors
   *  which are too long, and appends <code>null</code>s if they
   *  are too short.
   *  This method also sends out a <code>tableChanged</code>
   *  notification message to all the listeners.
   *
   * @param event         this <code>TableModelEvent</code> describes
   *                      where the rows were added.
   *			  If <code>null</code> it assumes
   *                      all the rows were newly added.
   *
   */
  public void newRowsAdded(TableModelEvent event) {
    int start = event.getFirstRow();
    int end = event.getLastRow();

    if (start < 0)
      start = 0;
    if (end < 0)
      end = getRowCount() - 1;

    // Have to make sure all the new columns have the correct
    // number of columns
    for (int i = start; i < end; i++)
      ((Vector)dataVector.elementAt(i)).setSize(getColumnCount());

    // Now we send the notification
    fireTableChanged(event);
  }

  public boolean validateRow() {
    for (int i = 0; i < columnIdentifiers.size(); i++) {
      TplObject test = (TplObject)columnIdentifiers.elementAt(i);
      if (test.isRequired() && !(!test.isGenKey() && getValueAt(rowMode, i) != "" && getValueAt(rowMode, i) != null)) {
        JOptionPane.showMessageDialog(null,
                                      TmplResourceSingleton.getString("error.msg.required.column") + getColumnName(i) +
                                      TmplResourceSingleton.getString("error.msg.required.column.end"),
                                      TmplResourceSingleton.getString("error.dialog.header"), JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    return true;
  }

  // set columnIdentifiers based on attributes and columnFields
  protected void setColumnIdentifiers() {
    columnIdentifiers = new Vector();
    Field[] fld = null;

    if (attrs != null)
      fld = attrs.getClass().getDeclaredFields();
    else
      return;

    for (int i = 0; i < fields.length; i++) {
      for (int j = 0; j < fld.length; j++) {
        try {
          // process template objects
          Object test = fld[j].get(attrs);
          if (test instanceof pt.inescporto.template.elements.TplObject &&
              ((TplObject)test).getField().equals(fields[i])) {
            columnIdentifiers.add(test);
            break;
          }
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  /**
   *
   */
  public void clearValues() {
    dataVector = null;
    fireTableDataChanged();
  }

  //
  // Communication methods
  //

  /**
   *  Gets data from <code>url</code) and save it to <code>dataVector</code>.
   *  This is accomplished by  using the <code>httpSender</code> for communitation
   *  with the server and with <code>TmplHttpMessage<code> for transport and
   *  error control when used in TmplAppletNav and by reflection when using RMI.
   *
   * @see #TmplHttpMessage
   * @see #TmplHttpMessageSender
   */
  public void populate(String linkCondition, Vector binds) {
    int errorCode = TmplMessages.MSG_OK;
    String localLC = null;
    java.util.Collection allListed = null;

    // set link condition in message (both static and dinamic)
    if (linkCondition != null && binds != null) {
      this.linkCondition = linkCondition;
      this.binds = binds;

      localLC = linkCondition;
      if (staticLinkCondition != null)
        localLC += " AND (" + staticLinkCondition + ")";
    }
    else {
      if (staticLinkCondition != null)
        localLC = staticLinkCondition;
    }

    // RMI
    try {
      Method execMethod = null;
      if (binds != null) {
	execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
	execMethod.invoke(remote, new Object[] {localLC, binds});
      }

      execMethod = remote.getClass().getMethod("listAll");
      allListed = (java.util.Collection)execMethod.invoke(remote);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    // try convert list, if list is null start a new empty vector
    try {
      if (msgCode == TmplMessages.MSG_LISTALL)
        dataVector = new Vector(AttrsToVector.ListConvert(allListed));
      else
        dataVector = new Vector(AttrsToVector.ListConvert(new Vector(allListed)));
    }
    catch (Exception ex) {
      dataVector = new Vector();
    }

    // broadcast data changed
    fireTableDataChanged();
  }

  public void initialize() {
      Method[] methodList = null;
      Method execMethod = null;

      // get home and remote interface for given url
      try {
        ContextPropertiesClient client = new ContextPropertiesClient();
        Properties prop = client.getInitialContextProperties();
        InitialContext initial = new InitialContext(prop);

        String s = url.substring(url.lastIndexOf('.') + 1);

        Object objRef = initial.lookup(s);

        Class objClass = Class.forName(url + "Home");
        home = PortableRemoteObject.narrow(objRef, objClass);

        Method createMethod;
        createMethod = objClass.getMethod("create");
        remote = createMethod.invoke(home);

      }
      catch (Exception ex) {
        remote = TmplRMILocater.getInstance().getRMIHome(url);
        if (remote == null)
          ex.printStackTrace();
      }

      methodList = remote.getClass().getMethods();

      // instantiate new DAO
      Class parametersType[] = null;
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
          ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
          ex.printStackTrace();
        }
      }

    if (attrs == null)
      return;
  }

  /**
   * Saves new, updated or deleted rows. Uses <code>doSend</code> for proxy
   * comunications. Determines mode based on <code>mode</code> that have to be
   * set using <code>setFormMode</code>. The row that as changed must be set using
   * <code>setRowMode</code>.
   *
   * Warning: JUST ONE ROW CAN HAVE CHANGED SINCE LAST SAVE!
   *
   */
  public int saveRow() throws TmplException {
    int errorCode = TmplMessages.MSG_OK;
    Object newRow = null;
    Field[] fld = null;
    Vector row = (Vector)dataVector.elementAt(rowMode);

    // create a new attribute object
    try {
      newRow = attrs.getClass().newInstance();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    fld = newRow.getClass().getDeclaredFields();

    // for every column find match in attrs object and set the correct value
    for (int i = 0; i < fields.length; i++) {
      for (int j = 0; j < fld.length; j++) {
        try {
          // process template objects
          Object test = fld[j].get(newRow);
          if (test instanceof pt.inescporto.template.elements.TplObject &&
              ((TplObject)test).getField().equals(fields[i])) {
            if (row.elementAt(i) != null) {
              int trueRow = i;
              if (test instanceof pt.inescporto.template.elements.TplString) {
                ((TplString)test).setValue((String)row.elementAt(trueRow));
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplInt) {
                ((TplInt)test).setValue((Integer)row.elementAt(trueRow));
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplLong) {
                ((TplLong)test).setValue((Long)row.elementAt(trueRow));
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplFloat) {
                ((TplFloat)test).setValue((Float)row.elementAt(trueRow));
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplTimestamp) {
                ((TplTimestamp)test).setValue((Timestamp)row.elementAt(trueRow));
                break;
              }
            }
          }
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }

    // find match for link keys and set the correct value if we are inserting
    if (binds != null) {
      for (int i = 0; i < binds.size(); i++) {
        TplObject bindField = (TplObject)binds.elementAt(i);
        for (int j = 0; j < fld.length; j++) {
          try {
            // process template objects
            Object test = fld[j].get(newRow);
            if (test instanceof pt.inescporto.template.elements.TplObject &&
                ((TplObject)test).getField().compareTo(bindField.getField()) == 0) {
              if (test instanceof pt.inescporto.template.elements.TplString) {
                ((TplString)test).setValue(((TplString)bindField).getValue());
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplInt) {
                ((TplInt)test).setValue(((TplInt)bindField).getValue());
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplLong) {
                ((TplLong)test).setValue(((TplLong)bindField).getValue());
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplFloat) {
                ((TplFloat)test).setValue(((TplFloat)bindField).getValue());
                break;
              }
              if (test instanceof pt.inescporto.template.elements.TplTimestamp) {
                ((TplTimestamp)test).setValue(((TplTimestamp)bindField).getValue());
                break;
              }
            }
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
      try {
        Method execMethod = null;
        switch (this.mode) {
          case TmplFormModes.MODE_INSERT:
            execMethod = remote.getClass().getMethod("insert", new Class[] {attrs.getClass()});
            execMethod.invoke(remote, new Object[] {newRow});
            break;
          case TmplFormModes.MODE_UPDATE:
            execMethod = remote.getClass().getMethod("update", new Class[] {attrs.getClass()});
            execMethod.invoke(remote, new Object[] {newRow});
            break;
          case TmplFormModes.MODE_SELECT:
            execMethod = remote.getClass().getMethod("delete", new Class[] {attrs.getClass()});
            execMethod.invoke(remote, new Object[] {newRow});
            break;
        }
      }
      catch (InvocationTargetException ex1) {
        if (ex1.getTargetException().getClass() == RowNotFoundException.class)
          throw new TmplException(TmplResourceSingleton.getString("info.dialog.msg.notFound"), TmplMessages.MSG_NOTFOUND);
        if (ex1.getTargetException().getClass() == DupKeyException.class)
          throw new TmplException(TmplResourceSingleton.getString("error.msg.dupkey"), TmplMessages.MSG_DUPKEY);
        if (ex1.getTargetException().getClass() == UserException.class)
          throw new TmplException(((UserException)ex1.getTargetException()).getMessage(), TmplMessages.USR_ERROR);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    // return result
    return errorCode;
  }
}
