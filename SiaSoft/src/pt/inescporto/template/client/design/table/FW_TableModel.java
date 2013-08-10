package pt.inescporto.template.client.design.table;

import java.io.Serializable;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import java.lang.reflect.Field;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import java.util.Collection;
import javax.swing.table.TableColumn;
import java.util.Arrays;
import java.util.List;
import pt.inescporto.template.elements.*;
import java.sql.Timestamp;
import java.util.Iterator;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.swing.JTable;

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
public class FW_TableModel extends AbstractTableModel implements Serializable, DataSourceListener {
  protected Vector dataVector;
  protected Vector columnIdentifiers;
  private DataSource datasource = null;
  private Vector<Integer> newRows = null;
  private Vector<Integer> editableRows = null;
  private Object basicRecord = null;
  private String[] headers = null;
  private String[] fields = null;
  private FW_ColumnManager fwColumnManager = null;
  private JTable jtParent = null;

  public FW_TableModel(DataSource datasource, String[] headers, String fields[]) {
    columnIdentifiers = new Vector();
    this.datasource = datasource;
    this.datasource.addDatasourceListener(this);
    this.headers = headers;
    this.fields = fields;

    try {
      basicRecord = datasource.getAttrs();
      createColumnsFromFields();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public FW_TableModel(DataSource datasource, String[] headers, FW_ColumnManager fwColumnManager) {
    columnIdentifiers = new Vector();
    this.datasource = datasource;
    this.datasource.addDatasourceListener(this);
    this.headers = headers;
    this.fwColumnManager = fwColumnManager;

    try {
      basicRecord = datasource.getAttrs();
      createColumnsFromColumnManager();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public FW_TableModel(Vector dataVector, Vector columnIdentifiers) {
    this.dataVector = dataVector;
    this.columnIdentifiers = columnIdentifiers;
    fireTableDataChanged();
    fireTableStructureChanged();
  }

  public void setJTableParent(JTable jtParent) {
    this.jtParent = jtParent;
  }

  public void setDataVector(Vector dataVector) {
    this.dataVector = dataVector;
    fireTableDataChanged();
  }

  public Object getAttrsAt(int row) {
    if (dataVector != null && row <= dataVector.size())
      return dataVector.elementAt(row);
    return null;
  }

  public void addCleanRow() throws DataSourceException {
    datasource.cleanUpAttrs();
    dataVector.add(datasource.getAttrs());
    newRows = new Vector<Integer>();
    newRows.add(new Integer(dataVector.size() - 1));
    fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
  }

  public void saveRows() throws DataSourceException {
    if (newRows != null) {
      for (Integer newRow : newRows) {
	datasource.setAttrs(dataVector.elementAt(newRow));
	datasource.insert();
      }
      newRows = null;
    }

    if (editableRows != null) {
      for (Integer row : editableRows) {
	datasource.setAttrs(dataVector.elementAt(row));
	datasource.update();
      }
      editableRows = null;
    }
  }

  public void cancelRows() {
    if (newRows != null) {
      for (Integer newRow : newRows) {
	dataVector.removeElementAt(newRow.intValue());
	fireTableRowsDeleted(newRow.intValue(), newRow.intValue());
      }
      newRows = null;
    }

    if (editableRows != null) {
      /*      for (Integer row : editableRows) {
	      datasource.setAttrs(dataVector.elementAt(row));
	      datasource.update();
	    }*/
      editableRows = null;
    }
  }

  public void deleteRow(int rowIndex) throws DataSourceException {
    datasource.setAttrs(dataVector.elementAt(rowIndex));
    try {
      datasource.delete();
    }
    catch (DataSourceException ex) {
      if (ex.getCause() instanceof RowNotFoundException){
        // do nothing
      }
      else
        throw ex;
    }
    fireTableRowsDeleted(rowIndex, rowIndex);
  }

  public void letEditRow(int rowIndex) {
    if (editableRows == null)
      editableRows = new Vector<Integer>();

    editableRows.add(new Integer(rowIndex));
  }

  public Vector<Integer> getInsertingRows() {
    return newRows;
  }

  public Vector<Integer> getUpdatingRows() {
    return editableRows;
  }

  public int getRowCount() {
    return dataVector == null ? 0 : dataVector.size();
  }

  public int getColumnCount() {
    return columnIdentifiers.size();
  }

  public Object getValueAt(int row, int column) {
    Object rowData = dataVector.elementAt(row);
    return getColumnValue(rowData, column);
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (newRows != null) {
      for (Integer newRow : newRows) {
	if (newRow.intValue() == rowIndex)
	  return true;
      }
    }

    if (editableRows != null) {
      for (Integer row : editableRows) {
	if (row.intValue() == rowIndex) {
	  int keyType = getColumnType(dataVector.elementAt(row), columnIndex);
	  return!(((keyType & TmplKeyTypes.PKKEY) > 0) || ((keyType & TmplKeyTypes.LNKKEY) > 0));
	}
      }
    }

    return false;
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Field[] fld;

    FW_TableColumn col = (FW_TableColumn)columnIdentifiers.elementAt(columnIndex);
    String searchField = col.getFieldName();

    fld = dataVector.elementAt(rowIndex).getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
	test = fld[i].get(dataVector.elementAt(rowIndex));
      }
      catch (IllegalAccessException ex) {
      }
      catch (IllegalArgumentException ex) {
      }
      // process template objects
      /**
       *@todo Missing TplBoolean Now corrected
       */

      if (test instanceof pt.inescporto.template.elements.TplObject) {
	TplObject myObject = null;
	myObject = (TplObject)test;
	if (myObject.getField().equalsIgnoreCase(searchField)) {
	  if (myObject.getClass() == TplString.class) {
	    ((TplString)myObject).setValue((String)aValue);
	    break;
	  }
	  if (myObject.getClass() == TplInt.class) {
	    ((TplInt)myObject).setValue((Integer)aValue);
	    break;
	  }
	  if (myObject.getClass() == TplLong.class) {
	    ((TplLong)myObject).setValue((Long)aValue);
	    break;
	  }
	  if (myObject.getClass() == TplFloat.class) {
	    ((TplFloat)myObject).setValue((Float)aValue);
	    break;
	  }
	  if (myObject.getClass() == TplTimestamp.class) {
	    ((TplTimestamp)myObject).setValue((Timestamp)aValue);
	    break;
	  }
          if (myObject.getClass() == TplBoolean.class) {
              ((TplBoolean)myObject).setValue((Boolean)aValue);
           break;
         }

	}
      }
    }
  }

  public String getColumnName(int column) {
    Object id = null;
    // This test is to cover the case when
    // getColumnCount has been subclassed by mistake ...
    if (column < columnIdentifiers.size()) {
      id = columnIdentifiers.elementAt(column);
    }
    return (id == null) ? super.getColumnName(column) : id.toString();
  }

  public void addColumn(Object column) {
    if (columnIdentifiers == null)
      columnIdentifiers = new Vector();

    columnIdentifiers.add(column);
    fireTableStructureChanged();
  }

  private Object getColumnValue(Object basicRecord, int column) {
    Field[] fld;
    String searchField = null;

    for (int j = 0; j < columnIdentifiers.size(); j++) {
      FW_TableColumn col = (FW_TableColumn)columnIdentifiers.elementAt(j);
      if (col.getModelIndex() == column) {
	searchField = col.getFieldName();
	break;
      }
    }

    fld = basicRecord.getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
	test = fld[i].get(basicRecord);
      }
      catch (IllegalAccessException ex) {
      }
      catch (IllegalArgumentException ex) {
      }
      // process template objects
      if (test instanceof pt.inescporto.template.elements.TplObject) {
	TplObject myObject = null;
	myObject = (TplObject)test;
	if (myObject.getField().equalsIgnoreCase(searchField)) {
	  return myObject.getValueAsObject();
	}
      }
    }
    return null;
  }

  private int getColumnType(Object basicRecord, int column) {
    Field[] fld;
    String searchField = null;

    for (int j = 0; j < columnIdentifiers.size(); j++) {
      FW_TableColumn col = (FW_TableColumn)columnIdentifiers.elementAt(j);
      if (col.getModelIndex() == column) {
	searchField = col.getFieldName();
	break;
      }
    }

    fld = basicRecord.getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
	test = fld[i].get(basicRecord);
      }
      catch (IllegalAccessException ex) {
      }
      catch (IllegalArgumentException ex) {
      }
      // process template objects
      if (test instanceof pt.inescporto.template.elements.TplObject) {
	TplObject myObject = null;
	myObject = (TplObject)test;
	if (myObject.getField().equalsIgnoreCase(searchField)) {
	  return myObject.getKeyType();
	}
      }
    }
    return -1;
  }

  /** @todo ONLY THE LINKED ATTRS TO COLUMNS MUST BE CHECKED!
   * CORRECT THIS ASAP - the best way is to delegate each type column's and make them test*/
  private boolean isRowReadyForSave(Object rowAttrs) {
    Field[] fld;

    fld = rowAttrs.getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
	test = fld[i].get(rowAttrs);
      }
      catch (IllegalAccessException ex) {
      }
      catch (IllegalArgumentException ex) {
      }
      // process template objects
      if (test instanceof pt.inescporto.template.elements.TplObject) {
	TplObject obj = (TplObject)test;
	if (obj.isRequired() && !obj.isGenKey() && obj.getValueAsObject() == null)
	  return false;
      }
    }
    return true;
  }

  protected void createColumnsFromFields() {
    Field[] fld;
    List a = Arrays.asList(fields);
    int curColumn = 0;

    fld = basicRecord.getClass().getDeclaredFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      try {
	Object test = fld[i].get(basicRecord);
	// process template objects
	if (test instanceof pt.inescporto.template.elements.TplObject) {
	  TplObject myObject = null;
	  myObject = (TplObject)test;
	  if (a.contains(myObject.getField())) {
	    FW_TableColumn tc = null;
	    Class type = myObject.getClass();
	    if (type == TplString.class)
	      tc = new FW_TableColumn(curColumn++, 75, new TmplStringRenderer(), new TmplStringEditor(), myObject.getField());
	    else
	      if (type == TplInt.class)
		tc = new FW_TableColumn(curColumn++, 75, new TmplIntegerRenderer(), new TmplIntegerEditor(), myObject.getField());
	      else
		if (type == TplLong.class)
		  tc = new FW_TableColumn(curColumn++, 75, new TmplLongRenderer(), new TmplLongEditor(), myObject.getField());
		else
		  if (type == TplFloat.class)
		    tc = new FW_TableColumn(curColumn++, 75, new TmplFloatRenderer(), new TmplFloatEditor(), myObject.getField());
		  else
		    if (type == TplTimestamp.class)
		      tc = new FW_TableColumn(curColumn++, 75, new TmplTimestampRenderer(), new TmplTimestampEditor(), myObject.getField());
		    else
		      tc = new FW_TableColumn(curColumn++, myObject.getField());
	    tc.setHeaderValue(myObject.getIndex() < headers.length ? headers[myObject.getIndex()] : myObject.getField());
	    addColumn(tc);
	  }
	}
      }
      catch (Exception e) {
	e.printStackTrace();
      }
    }
  }

  protected void createColumnsFromColumnManager() {
    //first let's clean the previous column identifiers
    columnIdentifiers = null;

    //for every node let's add it to the model
    int i = 0;
    Iterator<FW_ColumnNode> it = fwColumnManager.getColumnNodeList();
    while (it.hasNext()) {
      FW_ColumnNode node = it.next();
      FW_TableColumn tc = new FW_TableColumn(i++, 75, node.getTableCellRender(), node.getTableCellEditor(), node.getFieldName());
      tc.setHeaderValue(node.getHeaderValue());
      addColumn(tc);
    }
  }

  public FW_TableColumn getColumnAt(int index) {
    return (FW_TableColumn)columnIdentifiers.elementAt(index);
  }

  private void populate() {
    try {
      Collection all = datasource.listAll();
      setDataVector(new Vector(all));
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public boolean canSave() {
    if (newRows != null) {
      for (Integer row : newRows) {
	if (!isRowReadyForSave(dataVector.elementAt(row)))
	  return false;
      }
    }

    if (editableRows != null) {
      for (Integer row : editableRows) {
	if (!isRowReadyForSave(dataVector.elementAt(row)))
	  return false;
      }
    }

    return true;
  }

  /**
   * ***************************************************************************
   *       Implementation of the <code>DataSourceListener<\code> Interface
   * ***************************************************************************
   */

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
//    System.out.println("tablemodel tmplRefresh");
    populate();
    if (jtParent != null && jtParent.getRowCount() > 0) {
      jtParent.getSelectionModel().setSelectionInterval(0,0);
    }
  }

  public void tmplSave(TemplateEvent e) {
//    System.out.println("tablemodel tmplSave");
  }

  public void tmplLink(TemplateEvent e) {
//    System.out.println("tablemodel tmplLink");
  }
}
