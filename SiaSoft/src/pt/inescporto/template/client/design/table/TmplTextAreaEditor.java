package pt.inescporto.template.client.design.table;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import java.util.Vector;
import java.awt.Font;
import javax.swing.event.CellEditorListener;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/**
 * <p>Title: Benchmarking</p>
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
public class TmplTextAreaEditor extends JScrollPane implements TableCellEditor {
  protected transient Vector listeners;
  protected transient String originalValue;
  private JTextArea render = new JTextArea();

  public TmplTextAreaEditor() {
    super();
    listeners = new Vector();
    render.setFont(new Font("Dialog", 0, 10));
    render.setLineWrap(true);
    render.setWrapStyleWord(true);
    getViewport().add(render);
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    setBorder(BorderFactory.createEmptyBorder());
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value == null) {
      render.setText(null);
      return this;
    }
    if (value instanceof String) {
      render.setText((String)value);
    }
    else {
      render.setText("");
    }
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    originalValue = render.getText();
    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    return render.getText() == "" ? null : new String(render.getText());
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
    render.setText(originalValue);
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
