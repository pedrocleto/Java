package pt.inescporto.template.client.design.table;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import java.util.Vector;
import java.awt.Font;
import javax.swing.event.CellEditorListener;
import java.awt.Component;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import java.sql.Timestamp;
import pt.inescporto.template.client.design.TmplJDatePicker;
import java.util.Date;

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
public class TmplTimestampPickerEditor extends TmplJDatePicker implements TableCellEditor {
  protected transient Vector listeners;
  protected transient Timestamp originalValue;

  public TmplTimestampPickerEditor() {
    super();
    listeners = new Vector();
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setHorizontalAlignment(JTextField.RIGHT);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value == null) {
      setSelectedDate(null);
      return this;
    }
    if (value instanceof java.sql.Timestamp) {
      setSelectedDate(new Date(((Timestamp)value).getTime()));
    }
    else {
      setSelectedDate(null);
    }

    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    if (getSelectedDate(true) == null)
      originalValue = null;
    else {
      try {
        originalValue = new Timestamp(getSelectedDate(true).getTime());
      }
      catch (Exception ex) {
        JOptionPane.showMessageDialog(table,
                                      TmplResourceSingleton.getString("error.msg.notValidDate"),
                                      TmplResourceSingleton.getString("error.dialog.header"),
                                      JOptionPane.ERROR_MESSAGE);
      }
    }

    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
//    System.out.println(this.getClass().getName() + " value is <" + getSelectedDate(true) + ">");
    return getSelectedDate(true) == null ? null : new Timestamp(getSelectedDate(true).getTime());
  }

  public boolean isCellEditable(EventObject eo) {
    return true;
  }

  public boolean shouldSelectCell(EventObject eo) {
    return true;
  }

  public boolean stopCellEditing() {
    fireEditingStopped();
    return true;
  }

  public void addCellEditorListener(CellEditorListener cel) {
    listeners.addElement(cel);
  }

  public void removeCellEditorListener(CellEditorListener cel) {
    listeners.removeElement(cel);
  }

  protected void fireEditingCanceled() {
    setSelectedDate(new Date(originalValue.getTime()));
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size(); i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
    }
  }

  protected void fireEditingStopped() {
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
    }
  }
}
