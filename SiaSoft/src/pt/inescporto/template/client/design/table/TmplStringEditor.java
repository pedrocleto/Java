package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class TmplStringEditor extends JTextField implements TableCellEditor {
  protected transient Vector listeners;
  protected transient String originalValue;

  public TmplStringEditor() {
    super();
    listeners = new Vector();
    setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
					       boolean isSelected,
					       int row, int column) {
    if (value == null) {
      setText(null);
      return this;
    }
    if (value instanceof String) {
      setText((String)value);
    }
    else {
      setText("");
    }
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    originalValue = getText();
    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    return getText() == "" ? null : new String(getText());
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
    setText(originalValue);
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
