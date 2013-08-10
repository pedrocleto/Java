package pt.inescporto.template.client.design.table;

import java.sql.Timestamp;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;

public class TmplTimestampRenderer extends JTextField implements TableCellRenderer {
  private boolean isBordered = true;
  private SimpleDateFormat dateFormat = null;

  public TmplTimestampRenderer() {
    this(null);
  }

  public TmplTimestampRenderer(String pattern) {
    super();
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    setHorizontalAlignment(JTextField.RIGHT);

    dateFormat = pattern == null ?
	(SimpleDateFormat)SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
	: new SimpleDateFormat(pattern, Locale.getDefault());
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
    if (value instanceof java.sql.Timestamp) {
      setText(dateFormat.format(new Date(((Timestamp)value).getTime())));
    }
    else {
      setText("");
    }
    return this;
  }
}
