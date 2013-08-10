package pt.inescporto.template.client.design.table;

import javax.swing.JTable;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import javax.swing.table.TableCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.util.Date;
import java.awt.Font;
import java.awt.Component;
import java.sql.Timestamp;

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
public class TmplDateRenderer extends JTextField implements TableCellRenderer {
  private boolean isBordered = true;
  protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public TmplDateRenderer() {
    super();
    setFont( new Font( "Dialog", Font.PLAIN, 10 ) );
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
    if( value instanceof java.sql.Timestamp ) {
      setText(dateFormat.format(new Date(((Timestamp)value).getTime())));
    }
    else {
      setText( "" );
    }
    return this;
  }
}
