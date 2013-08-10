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
import java.text.SimpleDateFormat;
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
public class TmplDateEditor extends JTextField implements TableCellEditor {
  protected transient Vector listeners;
  protected transient Timestamp originalValue;
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public TmplDateEditor() {
    super();
    listeners = new Vector();
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setHorizontalAlignment(JTextField.RIGHT);
    setOpaque(true);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value == null) {
      setText(null);
      return this;
    }
    if (value instanceof java.sql.Timestamp) {
      setText(dateFormat.format(new Date(((Timestamp)value).getTime())));
    }
    else {
      setText("");
    }

    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    if (getText().equals(""))
      originalValue = null;
    else {
      try {
	if (!getText().equals(""))
	  originalValue = Timestamp.valueOf(getText() + " 00:00:00");
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
    return getText().equals("") ? null : Timestamp.valueOf(getText() + " 00:00:00");
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
