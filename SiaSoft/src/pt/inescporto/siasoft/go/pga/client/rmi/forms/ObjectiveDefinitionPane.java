package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import java.util.ResourceBundle;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.table.FW_LookupEditor;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.rmi.MenuSingleton;

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
public class ObjectiveDefinitionPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  private JScrollPane jScrollPane1 = new JScrollPane();
  private FW_JTable jtblObjective = null;
  private FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public ObjectiveDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    // table listener registration
    navBarDetail.setFW_ComponentListener(jtblObjective);
    navBarDetail.setActionListener(jtblObjective);

    fwCListener.addFWComponentListener(jtblObjective);
  }

  public FW_ComponentListener getMasterListener() {
    return jtblObjective;
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
                                                res.getString("objective.label.code"),
                                                "objectiveID",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
                                                res.getString("objective.label.desc"),
                                                "objectiveDescription",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
                                                res.getString("objective.label.indicator"),
                                                "indicator",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    FW_LookupEditor a = new FW_LookupEditor("pt.inescporto.siasoft.proc.ejb.session.Activity", "Lista de Actividades", null);
    if (!MenuSingleton.isSupplier())
      a.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    colManager.addColumnNode(new FW_ColumnNode(3,
                                                res.getString("objective.label.activity"),
                                                "fkActivityID",
                                                new TmplLookupRenderer("pt.inescporto.siasoft.proc.ejb.session.Activity"),
                                                a));
    colManager.addColumnNode(new FW_ColumnNode(4,
                                                res.getString("objective.label.regulatory"),
                                                "fkRegID",
                                                new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.Regulatory"),
                                                new FW_LookupEditor("pt.inescporto.siasoft.asq.ejb.session.Regulatory", "Lista de Diplomas", null)));
    FW_LookupEditor b = new FW_LookupEditor("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan", "Lista de Planos de Monitorização", null);
    if (!MenuSingleton.isSupplier())
      b.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    colManager.addColumnNode(new FW_ColumnNode(5,
                                                res.getString("objective.label.monitor"),
                                                "fkMonitorID",
                                                new TmplLookupRenderer("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan"),
                                                b));

    try {
      jtblObjective = new FW_JTable(datasource.getDataSourceByName("Objective"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblObjective.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
                                           "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblObjective);
    content.add(jScrollPane1, cc.xy(2,2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

    add(navBarDetail, BorderLayout.SOUTH);
  }
}
