package pt.inescporto.template.client.design.table;

import java.text.DecimalFormat;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

public class TmplMoneyRenderer extends JLabel implements TableCellRenderer {
  boolean isBordered = true;
  DecimalFormat df = new DecimalFormat( "#,##0.00;-#,##0.00" );

  public TmplMoneyRenderer() {
    super();
    setFont( new Font( "Dialog", 0, 10 ) );
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    setHorizontalAlignment( JTextField.RIGHT );
  }

  public Component getTableCellRendererComponent( JTable table, Object value,
                                                  boolean isSelected,
                                                  boolean hasFocus,
                                                  int row, int column ) {
    if( isBordered ) {
      if( isSelected ) {
        Border selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,table.getSelectionBackground());
        setBackground(table.getSelectionBackground());
        setForeground(table.getSelectionForeground());
        setBorder( selectedBorder );
      }
      else {
        Border unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,table.getBackground());
        setBackground(table.getBackground());
        setForeground(table.getForeground());
        setBorder( unselectedBorder );
      }
    }

    if( value == null ) {
      setText( "" );
      return this;
    }
    if( value instanceof java.lang.Float ) {
      try {
        setText( df.parse( df.format( ((Float)value).floatValue() ) ).toString() );
      }
      catch( java.text.ParseException pex ) {
      }
    }
    else {
      setText( "" );
    }
    return this;
  }
}