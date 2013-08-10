package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jgraph.graph.DefaultGraphCell;

import pt.inescporto.siasoft.go.aa.client.rmi.forms.EnvironmentalAspectDialog;
import pt.inescporto.siasoft.proc.client.rmi.forms.EnvAspectListDialog;
import pt.inescporto.siasoft.proc.client.rmi.forms.ActivityHasPartDialog;
import pt.inescporto.siasoft.proc.client.rmi.forms.ActivityHasPartListDialog;
import pt.inescporto.siasoft.proc.client.rmi.forms.ActivityListDialog;
import pt.inescporto.siasoft.proc.client.rmi.forms.ActivityNameDialog;

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
public class ActivityPopupMenu extends JPopupMenu implements ActionListener {

  private JMenuItem envAspListItem = null;
  private JMenuItem actHasPartItem = null;
  private JMenuItem actHasPartListItem = null;
  private JMenuItem actListItem = null;
  private JMenuItem actNameItem = null;

  private Graph graph = null;
  private JFrame frame;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ActivityPopupMenu() {
  }

  public ActivityPopupMenu(Graph graph) {
    this.graph = graph;
    setBorder(BorderFactory.createTitledBorder(res.getString("activityPopupMenu.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));



    envAspListItem = new JMenuItem(res.getString("activityPopupMenu.label.listEnvAsp"));
    envAspListItem.addActionListener(this);
    envAspListItem.setActionCommand("LIST_ENVASP");
    add(envAspListItem);
    addSeparator();

    actHasPartItem = new JMenuItem(res.getString("activityPopupMenu.label.addES"));
    actHasPartItem.addActionListener(this);
    actHasPartItem.setActionCommand("ADD_ACTHASPART");
    add(actHasPartItem);

    actHasPartListItem = new JMenuItem(res.getString("activityPopupMenu.label.listES"));
    actHasPartListItem.addActionListener(this);
    actHasPartListItem.setActionCommand("LIST_ACTHASPART");
    add(actHasPartListItem);
    addSeparator();

    actListItem = new JMenuItem("Efectuar Balanço de Massa");
    actListItem.addActionListener(this);
    actListItem.setActionCommand("LIST_ACT");
    add(actListItem);
    addSeparator();

    actNameItem = new JMenuItem(res.getString("activityPopupMenu.label.details"));
    actNameItem.addActionListener(this);
    actNameItem.setActionCommand("ACT_NAME");
    add(actNameItem);
  }

  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("LIST_ENVASP")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
	parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;

      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      EnvAspectListDialog ealDialog = new EnvAspectListDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      ealDialog.pack();
      ealDialog.setModal(true);
      ealDialog.setVisible(true);
    }

    if (e.getActionCommand().equals("ADD_ACTHASPART")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
	parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;

      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      ActivityHasPartDialog aHpDialog = new ActivityHasPartDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      aHpDialog.pack();
      aHpDialog.setModal(true);
      aHpDialog.setVisible(true);
    }
    if (e.getActionCommand().equals("LIST_ACTHASPART")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
	parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;

      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      ActivityHasPartListDialog aplDialog = new ActivityHasPartListDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      aplDialog.pack();
      aplDialog.setModal(true);
      aplDialog.setVisible(true);
    }

    if (e.getActionCommand().equals("LIST_ACT")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
	parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;

      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      ActivityListDialog alDialog = new ActivityListDialog(frame, ((GraphNode)cell.getUserObject()).getId());
      alDialog.pack();
      alDialog.setModal(true);
      alDialog.setVisible(true);
    }
    if (e.getActionCommand().equals("ACT_NAME")) {
      Component parent = getParent();
      while (parent != null && !(parent instanceof JFrame))
	parent = parent.getParent();
      frame = (parent != null) ? (JFrame)parent : null;

      DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
      ActivityNameDialog actNameDialog = new ActivityNameDialog(frame, cell, graph.getGraph());
      actNameDialog.pack();
      actNameDialog.setModal(true);
      actNameDialog.setVisible(true);
    }
  }

  public JMenuItem getActListItem() {
    return actListItem;
  }

  public JMenuItem getActNameItem() {
    return actNameItem;
  }

  public JMenuItem getEnvAspListItem() {
    return envAspListItem;
  }

  public JMenuItem getActHasPartListItem() {
    return actHasPartListItem;
  }

  public JMenuItem getActHasPartItem() {
    return actHasPartItem;
  }

  public void setActListItem(JMenuItem actListItem) {
    this.actListItem = actListItem;
  }

  public void setActNameItem(JMenuItem actNameItem) {
    this.actNameItem = actNameItem;
  }

  public void setEnvAspListItem(JMenuItem envAspListItem) {
    this.envAspListItem = envAspListItem;
  }

  public void setActHasPartListItem(JMenuItem actHasPartListItem) {
    this.actHasPartListItem = actHasPartListItem;
  }

  public void setActHasPartItem(JMenuItem actHasPartItem) {
    this.actHasPartItem = actHasPartItem;
  }
}
