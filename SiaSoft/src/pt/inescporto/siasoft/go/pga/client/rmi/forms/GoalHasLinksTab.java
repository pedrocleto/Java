package pt.inescporto.siasoft.go.pga.client.rmi.forms;

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
public class GoalHasLinksTab extends JTabbedPane implements ChangeListener {

  private GoalHasLinksPane goalHasLinksPane = null;
  private TableModuleModel tableModel = null;

  public GoalHasLinksTab(GoalHasLinksPane goalHasLinksPane) {

    this.goalHasLinksPane = goalHasLinksPane;
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
    addTab("Active Links", null, goalHasLinksPane, "Active Links");
  }

  public void refresh(Vector v) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");
    tableModel = goalHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }

  public GoalHasLinksPane getGoalHasLinksPane() {
    return goalHasLinksPane;
  }

  public void stateChanged(ChangeEvent e) {
  }
}
