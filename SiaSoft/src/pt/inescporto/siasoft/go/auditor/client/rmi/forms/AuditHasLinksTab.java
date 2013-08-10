package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import pt.inescporto.siasoft.proc.client.rmi.editor.ActivitySelectionListener;
import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import java.util.Vector;
import java.awt.Font;


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
public class AuditHasLinksTab extends JTabbedPane implements ChangeListener, ActivitySelectionListener {

  private AuditHasLinksPane AuditHasLinksPane = null;
  private TableModuleModel tableModel = null;


  public AuditHasLinksTab(AuditHasLinksPane monitorHasLinksPane) {

    this.AuditHasLinksPane = monitorHasLinksPane;
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
    addTab("Active Links", null, AuditHasLinksPane, "Active Links");
  }


  public void refresh(Vector v) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");
    tableModel = AuditHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }

  public AuditHasLinksPane getAuditHasLinksPane() {
    return AuditHasLinksPane;
  }

  public void stateChanged(ChangeEvent e) {


  }
}
