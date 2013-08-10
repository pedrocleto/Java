package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

public class TmplFloatRenderer extends JLabel implements TableCellRenderer {
  boolean isBordered = true;

  public TmplFloatRenderer() {
    super();
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    setHorizontalAlignment(JTextField.RIGHT);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
						 boolean isSelected,
						 boolean hasFocus,
						 int row, int column) {
    if (isBordered) {
      if (isSelected) {
	Border selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
	setBackground(table.getSelectionBackground());
	setForeground(table.getSelectionForeground());
	setBorder(selectedBorder);
      }
      else {
	Border unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
	setBackground(table.getBackground());
	setForeground(table.getForeground());
	setBorder(unselectedBorder);
      }
    }

    if (value == null) {
      setText("");
      return this;
    }
    if (value instanceof java.lang.Float) {
      setText(((Float)value).toString());
    }
    else {
      setText("");
    }
    return this;
  }
}
