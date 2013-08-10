package pt.inescporto.template.client.design.table;

import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.JTable;
import java.util.EventObject;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.util.Vector;
import java.awt.Font;
import javax.swing.JOptionPane;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import javax.swing.event.ChangeEvent;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplCheckBoxEditor
    extends JCheckBox
    implements TableCellEditor {
  protected transient Vector listeners;
  protected transient Boolean originalValue;

  public TmplCheckBoxEditor() {
    super();
    listeners = new Vector();
    this.setFont(new Font("Dialog", 0, 10));
    this.setHorizontalAlignment(JCheckBox.CENTER);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value == null) {
      setSelected(false);
      return this;
    }
    if (value instanceof java.lang.Boolean) {
      setSelected(((Boolean)value).booleanValue());
    }

    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);

    originalValue = new Boolean(isSelected());

    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    return new Boolean(isSelected());
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
    setText(originalValue.toString());
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
