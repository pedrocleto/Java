package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import java.awt.Font;
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
public class EmergencySitHasLinksTab extends JTabbedPane implements ChangeListener{

  private EmergencySitHasLinksPane emergSitHasLinksPane = null;
  private TableModuleModel tableModel = null;


  public EmergencySitHasLinksTab(EmergencySitHasLinksPane monitorHasLinksPane) {

    this.emergSitHasLinksPane = monitorHasLinksPane;
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
    addTab("Active Links", null, emergSitHasLinksPane, "Active Links");
  }


  public void refresh(Vector v) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");
    tableModel = emergSitHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }

  public EmergencySitHasLinksPane getEmergSitHasLinksPane() {
    return emergSitHasLinksPane;
  }

  public void stateChanged(ChangeEvent e) {


  }
}
