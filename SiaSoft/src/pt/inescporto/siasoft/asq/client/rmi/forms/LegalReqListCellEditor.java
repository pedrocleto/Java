package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.design.list.DefaultListCellEditor;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import javax.swing.DefaultCellEditor;
import java.util.EventObject;
import java.awt.event.MouseEvent;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import pt.inescporto.template.client.design.TmplJTextArea;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJButton;
import java.awt.GridBagLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.JTextField;

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
public class LegalReqListCellEditor extends DefaultListCellEditor {

  public LegalReqListCellEditor(final MyEditorPanel panel) {
    super(new JTextField());

    editorComponent = panel;
    delegate = new EditorDelegate() {
      public void setValue(Object value) {
	panel.setSelectedItem(value);
      }

      public Object getCellEditorValue() {
	return panel.getSelectedItem();
      }

      public boolean shouldSelectCell(EventObject anEvent) {
	if (anEvent instanceof MouseEvent) {
	  MouseEvent e = (MouseEvent)anEvent;
	  return e.getID() != MouseEvent.MOUSE_DRAGGED;
	}
	return true;
      }

      public boolean stopCellEditing() {
/*	if (comboBox.isEditable()) {
	  // Commit edited value.
	  comboBox.actionPerformed(new ActionEvent(DefaultCellEditor.this, 0, ""));
	}*/
	return super.stopCellEditing();
      }
    };
//    panel.addActionListener(delegate);
  }
}

class MyEditorPanel extends JPanel implements ListCellRenderer {
  private JPanel jPanel = null;
  private TmplJTextField tmplJTextField = null;
  private JScrollPane jScrollPane = null;
  private TmplJTextArea tmplJTextArea1 = null;
  private JRadioButton jRadioButton = null;
  private JRadioButton jRadioButton1 = null;
  private JRadioButton jRadioButton2 = null;
  private TmplJButton tmplJButton = null;

  public MyEditorPanel() {
    this.setSize(new java.awt.Dimension(438, 188));
    initialize();
  }

  public void setSelectedItem(Object value) {

  }

  public Object getSelectedItem() {
    return null;
  }

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    return this;
  }

  private void initialize() {
    try {
      GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
      gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints1.gridy = 1;
      gridBagConstraints1.weightx = 1.0;
      gridBagConstraints1.weighty = 0.0D;
      gridBagConstraints1.gridwidth = 2;
      gridBagConstraints1.gridx = 0;
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = 0;
      gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
      gridBagConstraints.weightx = 1.0D;
      gridBagConstraints.gridy = 0;
      this.setLayout(new GridBagLayout());
      this.setSize(new java.awt.Dimension(492, 208));
      this.add(getJPanel(), gridBagConstraints);
      this.add(getJScrollPane(), gridBagConstraints1);
    }
    catch (java.lang.Throwable e) {
      //  Do Something
    }
  }

  /**
   * This method initializes jPanel
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJPanel() {
    if (jPanel == null) {
      try {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
        jPanel = new JPanel();
        jPanel.setPreferredSize(new java.awt.Dimension(200, 27));
        jPanel.setLayout(flowLayout);
        jPanel.add(getTmplJTextField(), null);
        jPanel.add(getJRadioButton(), null);
        jPanel.add(getJRadioButton1(), null);
        jPanel.add(getJRadioButton2(), null);
        jPanel.add(getTmplJButton(), null);
        jPanel.setEnabled(true);
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return jPanel;
  }

  /**
   * This method initializes tmplJTextField
   *
   * @return pt.inescporto.template.client.design.TmplJTextField
   */
  private TmplJTextField getTmplJTextField() {
    if (tmplJTextField == null) {
      try {
        tmplJTextField = new TmplJTextField();
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return tmplJTextField;
  }

  /**
   * This method initializes jScrollPane
   *
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getJScrollPane() {
    if (jScrollPane == null) {
      try {
        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(getTmplJTextArea1());
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return jScrollPane;
  }

  /**
   * This method initializes tmplJTextArea1
   *
   * @return pt.inescporto.template.client.design.TmplJTextArea
   */
  private TmplJTextArea getTmplJTextArea1() {
    if (tmplJTextArea1 == null) {
      try {
        tmplJTextArea1 = new TmplJTextArea();
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return tmplJTextArea1;
  }

  /**
   * This method initializes jRadioButton
   *
   * @return javax.swing.JRadioButton
   */
  private JRadioButton getJRadioButton() {
    if (jRadioButton == null) {
      try {
        jRadioButton = new JRadioButton();
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return jRadioButton;
  }

  /**
   * This method initializes jRadioButton1
   *
   * @return javax.swing.JRadioButton
   */
  private JRadioButton getJRadioButton1() {
    if (jRadioButton1 == null) {
      try {
        jRadioButton1 = new JRadioButton();
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return jRadioButton1;
  }

  /**
   * This method initializes jRadioButton2
   *
   * @return javax.swing.JRadioButton
   */
  private JRadioButton getJRadioButton2() {
    if (jRadioButton2 == null) {
      try {
        jRadioButton2 = new JRadioButton();
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return jRadioButton2;
  }

  /**
   * This method initializes tmplJButton
   *
   * @return pt.inescporto.template.client.design.TmplJButton
   */
  private TmplJButton getTmplJButton() {
    if (tmplJButton == null) {
      try {
        tmplJButton = new TmplJButton();
      }
      catch (java.lang.Throwable e) {
        // TODO: Something
      }
    }
    return tmplJButton;
  }
} //  @jve:decl-index=0:visual-constraint="10,10"
