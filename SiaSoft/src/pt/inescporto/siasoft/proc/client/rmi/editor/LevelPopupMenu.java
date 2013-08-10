package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import pt.inescporto.siasoft.proc.client.rmi.forms.*;
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
public class LevelPopupMenu extends JPopupMenu implements ActionListener {
  private JMenuItem actListItem = null;
  private JFrame frame;
  private String parentID = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  Graph graph = null;
  public LevelPopupMenu() {
  }

  public LevelPopupMenu(String parentID, Graph graph) {
    this.parentID = parentID;
    this.graph = graph;


    setBorder(BorderFactory.createTitledBorder(res.getString("levelPopupMenu.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    actListItem = new JMenuItem("Efectuar Balanço de Massa");
    actListItem.addActionListener(this);
    actListItem.setActionCommand("LIST_ACT");
    add(actListItem);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("LIST_ACT")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
	parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;
      ActivityListDialog alDialog = new ActivityListDialog(frame, parentID);
      alDialog.pack();
      alDialog.setModal(true);
      alDialog.setVisible(true);
    }
  }

  public JMenuItem getActListItem() {
    return actListItem;
  }

  public void setActListItem(JMenuItem actListItem) {
    this.actListItem = actListItem;
  }
}
