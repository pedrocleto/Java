package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

public class TmplStringRenderer extends JLabel implements TableCellRenderer {
  boolean isBordered = true;

  public TmplStringRenderer() {
    super();
    this.setFont(new Font("Dialog", Font.PLAIN, 10));
    this.setBorder(BorderFactory.createEmptyBorder());
    this.setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
						 boolean isSelected,
						 boolean hasFocus,
						 int row, int column) {
    if (isBordered) {
      if (isSelected) {
	Border selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
	this.setBackground(table.getSelectionBackground());
	this.setForeground(table.getSelectionForeground());
	setBorder(selectedBorder);
      }
      else {
	Border unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
	this.setBackground(table.getBackground());
	this.setForeground(table.getForeground());
	setBorder(unselectedBorder);
      }
    }

    if (value == null) {
      setText("");
      return this;
    }
    if (value instanceof String) {
      setText((String)value);
    }
    else {
      if (value instanceof ImageIcon) {
        setIcon((ImageIcon)value);
      }
      else {
	setText("");
        setIcon(null);
      }
    }
    return this;
  }
}
