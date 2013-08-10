package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.Font;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

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
public class MyTabbedPane extends JTabbedPane implements ChangeListener, ActivitySelectionListener {
  private ActHasPartPane actHasPartPane = null;
  private EnvAspectPane envAspectPane = null;
  protected Hashtable<String, EnvAspLayerNode> layers = null;
  private ActivityHasLinksPane activityHasLinksPane = null;
  private TableModuleModel tableModel = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public MyTabbedPane(ActHasPartPane actHasPartPane, EnvAspectPane envAspectPane, ActivityHasLinksPane activityHasLinksPane) {
    this.actHasPartPane = actHasPartPane;
    this.envAspectPane = envAspectPane;
    this.activityHasLinksPane = activityHasLinksPane;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    addChangeListener(this);
    setFont(new Font("Dialog", Font.PLAIN, 12));
    addTab(res.getString("myTabbedPane.label.partClass"), null, actHasPartPane, res.getString("myTabbedPane.label.partClass"));
    addTab(res.getString("myTabbedPane.label.envAspClass"), null, envAspectPane, res.getString("myTabbedPane.label.envAspClass"));
    addTab(res.getString("myTabbedPane.label.links"), null, activityHasLinksPane, res.getString("myTabbedPane.label.links"));

  }

  public void stateChanged(ChangeEvent e) {
    layers = envAspectPane.getLayers();
    if (((JTabbedPane)e.getSource()).getSelectedComponent().equals(actHasPartPane)) {
      for (EnvAspLayerNode node : layers.values()) {
	node.getJpnlLayer().removeAll();
	node.getJpnlLayer().validate();
	node.getJpnlLayer().repaint();
      }
    }
    if (((JTabbedPane)e.getSource()).getSelectedComponent().equals(activityHasLinksPane)) {
      for (EnvAspLayerNode node : layers.values()) {
	node.getJpnlLayer().removeAll();
	node.getJpnlLayer().validate();
	node.getJpnlLayer().repaint();
      }
    }
  }
  public void refresh(Vector v) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");
    tableModel = activityHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }

  public ActHasPartPane getActHasPartPane() {
    return actHasPartPane;
  }

  public EnvAspectPane getEnvAspectPane() {
    return envAspectPane;
  }

  public ActivityHasLinksPane getActivityHasLinksPane() {
    return activityHasLinksPane;
  }

  public void setActHasPartPane(ActHasPartPane actHasPartPane) {
    this.actHasPartPane = actHasPartPane;
  }

  public void setEnvAspectPane(EnvAspectPane envAspectPane) {
    this.envAspectPane = envAspectPane;
  }
}
