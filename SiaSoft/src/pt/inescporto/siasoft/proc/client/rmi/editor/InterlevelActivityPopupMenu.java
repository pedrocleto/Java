package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jgraph.graph.*;
import pt.inescporto.siasoft.proc.client.rmi.forms.*;
import java.util.ResourceBundle;

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
public class InterlevelActivityPopupMenu  extends JPopupMenu implements ActionListener{

  private JMenuItem linkItem = new JMenuItem();
  private Graph graph = null;
  private JFrame frame;
  private ProcessEditor pEditor;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public InterlevelActivityPopupMenu(Graph graph, ProcessEditor pEditor) {
    this.graph = graph;
    this.pEditor=pEditor;
    linkItem = new JMenuItem(res.getString("interLevel.label.link"));
    linkItem.addActionListener(this);
    linkItem.setActionCommand("LINK");
    add(linkItem);

  }
   public void actionPerformed(ActionEvent e){
     if (e.getActionCommand().equals("LINK")) {
   Component parent = getParent();
   while (parent != null && !(parent instanceof JFrame))
     parent = parent.getParent();
   frame = (parent != null) ? (JFrame)parent : null;
   DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();

   InterLevelActivityDialog interLevelActivityDialog = new InterLevelActivityDialog(frame, cell, graph.getGraph(), pEditor);
   interLevelActivityDialog.pack();
   interLevelActivityDialog.setModal(true);
   interLevelActivityDialog.setVisible(true);
   }
 }

  public JMenuItem getLinkItem() {
    return linkItem;
  }
}
