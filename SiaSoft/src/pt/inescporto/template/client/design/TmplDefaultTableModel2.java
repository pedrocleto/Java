package pt.inescporto.template.client.design;

import java.util.Vector;
import java.lang.reflect.Field;

import javax.swing.table.AbstractTableModel;

import pt.inescporto.template.elements.TplObject;

public class TmplDefaultTableModel2 extends AbstractTableModel {
  protected Vector dataVector;
  protected Vector columnIdentifiers;

  public TmplDefaultTableModel2() {
    columnIdentifiers = new Vector();
  }

  public TmplDefaultTableModel2(Vector dataVector, Vector columnIdentifiers) {
    this.dataVector = dataVector;
    this.columnIdentifiers = columnIdentifiers;
    fireTableDataChanged();
    fireTableStructureChanged();
  }

  public void setDataVector(Vector dataVector) {
    this.dataVector = dataVector;
    fireTableDataChanged();
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
    columnIdentifiers.add(column);
    fireTableStructureChanged();
  }

  private Object getColumnValue(Object basicRecord, int column) {
    Field[] fld;
    int i;

    fld = basicRecord.getClass().getDeclaredFields();

    // process any field
    for (i = 0; i < fld.length; i++) {
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
	if (myObject.getIndex() == column) {
	  return myObject.getValueAsObject();
	}
      }
    }
    return null;
  }
}
