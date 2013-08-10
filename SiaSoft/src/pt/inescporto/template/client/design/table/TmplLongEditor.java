package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import pt.inescporto.template.client.design.TmplResourceSingleton;

public class TmplLongEditor extends JTextField implements TableCellEditor {
  protected transient Vector listeners;
  protected transient Long originalValue;

  public TmplLongEditor() {
    super();
    listeners = new Vector();
    this.setFont( new Font( "Dialog", 0, 10 ) );
//    this.setBorder(BorderFactory.createEmptyBorder());
    this.setHorizontalAlignment( JTextField.RIGHT );
  }

  public Component getTableCellEditorComponent( JTable table, Object value,
                                                boolean isSelected,
                                                int row, int column ) {
    if( value == null ) {
      setText(null);
      return this;
    }
    if( value instanceof java.lang.Long ) {
      setText( ((Long)value).toString() );
    }
    else {
      setText( "" );
    }

    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    if( getText().equals("") )
      originalValue = null;
    else {
      try {
        originalValue = new Long( getText() );
      }
      catch( Exception ex ) {
        JOptionPane.showMessageDialog( this,
               TmplResourceSingleton.getString("error.msg.notValidNum"),
               TmplResourceSingleton.getString("error.dialog.header"),
               JOptionPane.ERROR_MESSAGE );
      }
    }

    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    return getText().equals("") ? null : new Long( getText() );
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
    listeners.addElement( cel );
  }

  public void removeCellEditorListener(CellEditorListener cel) {
    listeners.removeElement( cel );
  }

  protected void fireEditingCanceled() {
    setText( originalValue.toString() );
    ChangeEvent ce = new ChangeEvent( this );
    for( int i = listeners.size(); i >= 0; i-- ) {
      ((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
    }
  }

  protected void fireEditingStopped() {
    ChangeEvent ce = new ChangeEvent( this );
    for( int i = listeners.size() - 1; i >= 0; i-- ) {
      ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
    }
  }
}