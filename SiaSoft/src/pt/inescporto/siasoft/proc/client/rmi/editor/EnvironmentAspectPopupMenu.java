package pt.inescporto.siasoft.proc.client.rmi.editor;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.siasoft.go.aa.client.rmi.forms.EnvironmentalAspectDialog;
import javax.swing.JFrame;
import org.jgraph.graph.DefaultGraphCell;
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
public class EnvironmentAspectPopupMenu extends JPopupMenu implements ActionListener{

  private JMenuItem envAspItem = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  private Graph graph = null;
  private JFrame frame;

  public EnvironmentAspectPopupMenu(Graph graph) {
    this.graph = graph;
    envAspItem = new JMenuItem(res.getString("activityPopupMenu.label.addEnvAsp"));
    envAspItem.addActionListener(this);
    envAspItem.setActionCommand("ADD_ENVASP");
    add(envAspItem);

  }
   public void actionPerformed(ActionEvent e) {
     if (e.getActionCommand().equals("ADD_ENVASP")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
        parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;
      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      EnvironmentalAspectDialog eaDialog = new EnvironmentalAspectDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      eaDialog.pack();
      eaDialog.setModal(true);
      eaDialog.setVisible(true);
    }

   }

   public JMenuItem getEnvAspItem() {
     return envAspItem;
   }
   public void setEnvAspItem(JMenuItem envAspItem) {
     this.envAspItem = envAspItem;
   }

}
