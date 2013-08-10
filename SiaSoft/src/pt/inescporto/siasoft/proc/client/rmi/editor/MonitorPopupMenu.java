package pt.inescporto.siasoft.proc.client.rmi.editor;

import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import org.jgraph.graph.DefaultGraphCell;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
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
public class MonitorPopupMenu extends JPopupMenu implements ActionListener{
  private JMenuItem auditorItem = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  private Graph graph = null;
  private JFrame frame;

  public MonitorPopupMenu(Graph graph) {
    this.graph = graph;
    auditorItem = new JMenuItem(res.getString("monitorPopup.label.add"));
    auditorItem.addActionListener(this);
    auditorItem.setActionCommand("ADD_MONITOR");
    add(auditorItem);

  }
   public void actionPerformed(ActionEvent e) {
     if (e.getActionCommand().equals("ADD_MONITOR")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
        parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;
      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();

      MonitorDialog monitorDialog = new MonitorDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      monitorDialog.pack();
      monitorDialog.setModal(true);
      monitorDialog.setVisible(true);

    }
   }

   public JMenuItem getAuditorItem() {
     return auditorItem;
   }
   public void setAuditorItemItem(JMenuItem auditorItem) {
     this.auditorItem = auditorItem;
   }
}
