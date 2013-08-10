package pt.inescporto.template.client.design.list;

import javax.swing.ListModel;

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

public interface MutableListModel extends ListModel {
  public boolean isCellEditable(int index);

  public void setValueAt(Object value, int index);
}
