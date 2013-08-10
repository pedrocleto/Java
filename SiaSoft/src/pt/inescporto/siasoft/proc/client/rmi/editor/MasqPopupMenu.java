package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jgraph.graph.*;

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
public class MasqPopupMenu extends JPopupMenu implements ActionListener{
  private JMenuItem masqItem = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  private Graph graph = null;
  private JFrame frame;

  public MasqPopupMenu(Graph graph) {
    this.graph = graph;
    masqItem = new JMenuItem(res.getString("masqPopup.label.add"));
    masqItem.addActionListener(this);
    masqItem.setActionCommand("ADD_REG");
    add(masqItem);

  }
   public void actionPerformed(ActionEvent e) {
     if (e.getActionCommand().equals("ADD_REG")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
        parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;
      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      MasqDialog masqDialog = new MasqDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      masqDialog.pack();
      masqDialog.setModal(true);
      masqDialog.setVisible(true);
    }
   }

  public JMenuItem getMasqItem() {
    return masqItem;
  }

  public void setMasqItem(JMenuItem masqItem) {
    this.masqItem = masqItem;
  }
}
