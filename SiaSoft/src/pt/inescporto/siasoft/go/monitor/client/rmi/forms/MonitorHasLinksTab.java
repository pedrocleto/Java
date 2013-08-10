package pt.inescporto.siasoft.go.monitor.client.rmi.forms;


import javax.swing.event.ChangeEvent;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import java.util.Vector;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class MonitorHasLinksTab extends JTabbedPane implements ChangeListener {

  private MonitorHasLinksPane monitorHasLinksPane = null;
  private TableModuleModel tableModel = null;


  public MonitorHasLinksTab(MonitorHasLinksPane monitorHasLinksPane) {

    this.monitorHasLinksPane = monitorHasLinksPane;
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
    addTab("Active Links", null, monitorHasLinksPane, "Active Links");
  }


  public void refresh(Vector v) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");
    tableModel = monitorHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }

  public MonitorHasLinksPane getMonitorHasLinksPane() {
    return monitorHasLinksPane;
  }

  public void stateChanged(ChangeEvent e) {


  }
}
