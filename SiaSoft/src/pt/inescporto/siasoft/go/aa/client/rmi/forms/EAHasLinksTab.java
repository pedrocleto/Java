package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import pt.inescporto.siasoft.proc.client.rmi.editor.ActivitySelectionListener;
import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import java.util.Vector;
import java.awt.Font;
import pt.inescporto.template.elements.TplObject;


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
public class EAHasLinksTab extends JTabbedPane implements ChangeListener {

  private EAHasLinksPane envAspHasLinksPane = null;
  private TableModuleModel tableModel = null;

  public EAHasLinksTab(EAHasLinksPane envAspHasLinksPane) {

    this.envAspHasLinksPane = envAspHasLinksPane;
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
    addTab("Active Links", null, envAspHasLinksPane, "Active Links");
  }


  public void refresh(Vector result) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");

    tableModel = envAspHasLinksPane.getTableModel();
    tableModel.setDataVector(result, columnNames);
  }

  public void refreshOne(Vector v) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");

    tableModel = envAspHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }

  public EAHasLinksPane getEAHasLinksPane() {
    return envAspHasLinksPane;
  }

  public void stateChanged(ChangeEvent e) {
  }
}
