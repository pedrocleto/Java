package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.table.TableCellRenderer;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplCheckBoxRender extends JCheckBox implements TableCellRenderer {
  boolean isBordered = true;

  public TmplCheckBoxRender() {
    super();
    setFont( new Font( "Dialog", 0, 10 ) );
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    setHorizontalAlignment( JCheckBox.CENTER );
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

    if( value != null && value instanceof java.lang.Boolean ) {
      setSelected(((Boolean)value).booleanValue());
    }
    return this;
  }
}
