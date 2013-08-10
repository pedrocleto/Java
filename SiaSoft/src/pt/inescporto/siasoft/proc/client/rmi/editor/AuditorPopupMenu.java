package pt.inescporto.siasoft.proc.client.rmi.editor;

import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import org.jgraph.graph.DefaultGraphCell;
import pt.inescporto.siasoft.go.aa.client.rmi.forms.EnvironmentalAspectDialog;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;

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
public class AuditorPopupMenu extends JPopupMenu implements ActionListener{
  private JMenuItem auditorItem = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  private Graph graph = null;
  private JFrame frame;

  public AuditorPopupMenu(Graph graph) {
    this.graph = graph;
    auditorItem = new JMenuItem(res.getString("auditPopup.label.add"));
    auditorItem.addActionListener(this);
    auditorItem.setActionCommand("ADD_AUDIT");
    add(auditorItem);

  }
   public void actionPerformed(ActionEvent e) {
     if (e.getActionCommand().equals("ADD_AUDIT")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
        parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;
      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      AuditorDialog auditorDialog = new AuditorDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      auditorDialog.pack();
      auditorDialog.setModal(true);
      auditorDialog.setVisible(true);
    }
   }

   public JMenuItem getAuditorItem() {
     return auditorItem;
   }
   public void setAuditorItemItem(JMenuItem auditorItem) {
     this.auditorItem = auditorItem;
   }
}
