package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.BorderLayout;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.siasoft.go.monitor.client.rmi.forms.MonitorSearchDatasource;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.TmplJButtonUpdate;


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

public class MonitorSearchTable extends JPanel {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  MonitorSearchDatasource monSearchDS = null;
  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblParameters = new FW_JTable();
  TmplJButtonUpdate update = new TmplJButtonUpdate();

  public MonitorSearchTable(MonitorSearchDatasource monSearchDS, TmplJButtonUpdate update) {
    this.monSearchDS = monSearchDS;
    this.update=update;
    initialize();
    jtblParameters.start();
  }

  private void initialize(){
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("monitorSearch.label.plan"),
					       "monitorPlanID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("monitorDef.label.monitorDate"),
					       "monitorDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("monitorDef.label.monitorEndDate"),
					       "monitorEndDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("monitorDef.label.monitorRealStartDate"),
					       "monitorRealStartDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("monitorDef.label.monitorRealEndDate"),
					       "monitorRealEndDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("monitorSearch.label.state"),
					       "status",
                                               new TmplLookupRenderer(new Object[] {res.getString("monitorSearch.label.state.wait"), res.getString("monitorSearch.label.state.running"), res.getString("monitorSearch.label.state.delayed"), res.getString("monitorSearch.label.state.ended"), res.getString("monitorSearch.label.state.schedule")}, new Object[] {"W", "R", "D","E","S"}),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("monitorDef.label.monitorUser"),
					       "fkUserID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("monitorDef.label.monitorResult"),
					       "monitorResult",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    jtblParameters = new FW_JTable(monSearchDS, null, colManager);
    jtblParameters.setAsMaster(true);


    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 130dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });

    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblParameters);
    content.add(jScrollPane1, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(update,BorderLayout.SOUTH);
  }
}
