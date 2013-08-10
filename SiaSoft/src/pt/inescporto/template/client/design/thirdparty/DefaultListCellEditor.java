package pt.inescporto.template.client.design.thirdparty;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.JList;

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
public class DefaultListCellEditor extends DefaultCellEditor implements ListCellEditor {
  public DefaultListCellEditor(final JCheckBox checkBox) {
    super(checkBox);
  }

  public DefaultListCellEditor(final JComboBox comboBox) {
    super(comboBox);
  }

  public DefaultListCellEditor(final JTextField textField) {
    super(textField);
  }

  public Component getListCellEditorComponent(JList list, Object value, boolean isSelected, int index) {
    delegate.setValue(value);
    return editorComponent;
  }
}
