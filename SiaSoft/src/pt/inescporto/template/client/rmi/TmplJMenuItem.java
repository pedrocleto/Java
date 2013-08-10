package pt.inescporto.template.client.rmi;

import javax.swing.*;
import pt.inescporto.template.client.beans.MenuNode;

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
public class TmplJMenuItem extends JMenuItem {
  private MenuNode menuNode = null;

  public TmplJMenuItem(String actionCommand) {
    super(actionCommand);
  }

  public void setMenuNode(MenuNode menuNode) {
    this.menuNode = menuNode;
  }

  public MenuNode getMenuNode() {
    return menuNode;
  }
}
