package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
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
public class ObjectiveActionsSearchTable extends JPanel {

  ObjectiveActionsSearchDataSource oaDS = null;
  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();

  JScrollPane jsp = new JScrollPane();
  FW_JTable fwObjectiveActionsList = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  public ObjectiveActionsSearchTable(ObjectiveActionsSearchDataSource oaDS, TmplJButtonUpdate jbtnUpdate) {
    this.oaDS = oaDS;
    this.jbtnUpdate = jbtnUpdate;
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("oaTable.label.goal"),
					       "goalID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("oaTable.label.objective"),
					       "objectiveID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("oaTable.label.objAction"),
					       "actionID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("oaTable.label.desc"),
					       "actionDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("oaTable.label.planStartDate"),
					       "planStartDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("oaTable.label.planEndDate"),
					       "planEndDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("oaTable.label.startDate"),
					       "startDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("oaTable.label.endDate"),
                                               "endDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(8,
                                               res.getString("oaTable.label.state"),
                                               "status",
                                               new TmplLookupRenderer(new Object[] {res.getString("oaTable.label.waiting"), res.getString("oaTable.label.running"), res.getString("oaTable.label.delayed"), res.getString("oaTable.label.ended"), res.getString("oaTable.label.schedule")}, new Object[] {"W", "R", "D", "E", "S"}),
                                               new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(9,
					       res.getString("oaTable.label.user"),
					       "fkUserID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    fwObjectiveActionsList = new FW_JTable(oaDS, null, colManager);

    fwObjectiveActionsList.setAsMaster(true);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 130dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});

    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwObjectiveActionsList);
    content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
    add(content, BorderLayout.CENTER);
    add(jbtnUpdate, BorderLayout.SOUTH);
  }
}
