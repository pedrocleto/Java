package pt.inescporto.template.client.design.table;

import javax.swing.*;
import java.awt.Font;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.Border;

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
public class TmplTextAreaRender extends JScrollPane implements TableCellRenderer {
  boolean isBordered = true;
  JTextArea render = new JTextArea();

  public TmplTextAreaRender() {
    super();
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    render.setEditable(false);
    render.setEnabled(true);
    this.setEnabled(true);
    render.setLineWrap(true);
    render.setWrapStyleWord(true);
    render.setFont(new Font("Dialog", 0, 10));
    getViewport().add(render);
    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row, int column) {
    if (isBordered) {
      if (isSelected) {
        Border selectedBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, table.getSelectionBackground());
        render.setBackground(table.getSelectionBackground());
        render.setForeground(table.getSelectionForeground());
        setBorder(selectedBorder);
      }
      else {
        Border unselectedBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, table.getBackground());
        render.setBackground(table.getBackground());
        render.setForeground(table.getForeground());
        setBorder(unselectedBorder);
      }
    }

    if (value == null) {
      render.setText("");
      return this;
    }
    if (value instanceof String) {
      render.setText((String)value);
    }
    else {
      render.setText("");
    }
    return this;
  }

  public void setEnabled(boolean value) {
    super.setEnabled(true);
  }
}
