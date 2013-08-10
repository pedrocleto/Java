package pt.inescporto.template.client.design.thirdparty;

import javax.swing.DefaultListModel;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 * @version 0.1
 */

public class DefaultMutableListModel extends DefaultListModel implements MutableListModel {
  public boolean isCellEditable(int index) {
    return true;
  }

  public void setValueAt(Object value, int index) {
    super.setElementAt(value, index);
  }
}
