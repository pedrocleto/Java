package pt.inescporto.siasoft.asq.client.rmi.forms;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Font;

/**
 * <p>Title: SIASoft</p>
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
class RegulatoryListCellRenderer extends JPanel implements ListCellRenderer {
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel jlblDescription = new JLabel();
  JTextArea jtareaResume = new JTextArea();

  public RegulatoryListCellRenderer() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

    jlblDescription.setText(((RegulatoryListNode)value).getDescription());
    jtareaResume.setText(((RegulatoryListNode)value).getResume());

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);

    return this;
  }

  private void jbInit() throws Exception {
    setBorder(new LineBorder(SystemColor.activeCaptionBorder));

    jlblDescription.setOpaque(false);
    jlblDescription.setFont(new Font("Dialog", Font.BOLD, 10));
    jtareaResume.setEditable(false);
    jtareaResume.setLineWrap(true);
    jtareaResume.setWrapStyleWord(true);
    jtareaResume.setOpaque(false);
    jtareaResume.setFont(new Font("Dialog", Font.PLAIN, 10));
    setLayout(borderLayout1);
    add(jlblDescription, java.awt.BorderLayout.NORTH);
    add(jtareaResume, java.awt.BorderLayout.CENTER);
  }

  public void showResume(boolean value) {
    if (value)
      remove(jtareaResume);
    else
      add(jtareaResume, java.awt.BorderLayout.CENTER);
    jtareaResume.invalidate();
    jtareaResume.repaint();
    borderLayout1.invalidateLayout(this);
    invalidate();
    repaint();
  }
}

