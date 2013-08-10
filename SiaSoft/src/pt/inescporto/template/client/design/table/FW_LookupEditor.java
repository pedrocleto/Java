package pt.inescporto.template.client.design.table;

import javax.swing.JPanel;
import javax.swing.table.TableCellEditor;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.event.CellEditorListener;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;
import java.util.Vector;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.BorderLayout;

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
public class FW_LookupEditor extends JPanel implements TableCellEditor {
  protected transient Vector listeners;
  protected transient String originalValue;

  protected TmplJTextField jtfldValue = new TmplJTextField();
  protected TmplLookupButton jbtnLookup = new TmplLookupButton();

  protected String linkCondition = null;

  public FW_LookupEditor(String url, String title, String[] headers) {
    super();
    listeners = new Vector();

    jbtnLookup.setUrl(url);
    jbtnLookup.setTitle(title);
    jbtnLookup.setHeaders(headers);
    jbtnLookup.setDefaultFill(jtfldValue);

    setLayout(new BorderLayout());
    add(jtfldValue, BorderLayout.CENTER);
    add(jbtnLookup, BorderLayout.EAST);
  }

  public void setLinkCondition(String linkCondition) {
    this.linkCondition = linkCondition;
    jbtnLookup.setLinkCondition(linkCondition);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value == null) {
      jtfldValue.setText(null);
      return this;
    }
    if (value instanceof String) {
      jtfldValue.setText((String)value);
    }
    else {
      jtfldValue.setText(null);
    }
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    originalValue = jtfldValue.getText().equals("") ? null : jtfldValue.getText();
    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    return jtfldValue.getText().equals("") ? null : new String(jtfldValue.getText());
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
    jtfldValue.setText(originalValue);
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
