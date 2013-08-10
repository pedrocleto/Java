package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

public class TmplIntegerRenderer extends JLabel implements TableCellRenderer {
  boolean isBordered = true;

  public TmplIntegerRenderer() {
    super();
    this.setFont( new Font( "Dialog", 0, 10 ) );
    this.setBorder(BorderFactory.createEmptyBorder());
    this.setOpaque(true);
    this.setHorizontalAlignment( JTextField.RIGHT );
  }

  public Component getTableCellRendererComponent( JTable table, Object value,
                                                  boolean isSelected,
                                                  boolean hasFocus,
                                                  int row, int column ) {
    if( isBordered ) {
      if( isSelected ) {
        Border selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,table.getSelectionBackground());
        this.setBackground(table.getSelectionBackground());
        this.setForeground(table.getSelectionForeground());
        setBorder( selectedBorder );
      }
      else {
        Border unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,table.getBackground());
        this.setBackground(table.getBackground());
        this.setForeground(table.getForeground());
        setBorder( unselectedBorder );
      }
    }

    if( value == null ) {
      setText( "" );
      return this;
    }
    if( value instanceof java.lang.Integer ) {
      setText( ((Integer)value).toString() );
    }
    else {
      setText( "" );
    }
    return this;
  }
}