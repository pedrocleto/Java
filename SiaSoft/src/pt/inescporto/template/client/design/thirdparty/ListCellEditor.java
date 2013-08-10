package pt.inescporto.template.client.design.thirdparty;

import javax.swing.CellEditor;
import javax.swing.JList;
import java.awt.Component;

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
public interface ListCellEditor extends CellEditor {
  Component getListCellEditorComponent(JList list, Object value,
				       boolean isSelected,
				       int index);
}
