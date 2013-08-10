package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplIntegerRenderer;
import pt.inescporto.template.client.design.table.TmplIntegerEditor;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
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
public class AuditPlanActionsPane extends JPanel {

  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  FW_JTable fwAuditActionsList = null;
  DataSource dataSource = null;
  FW_ComponentListener fwCListener = null;
  JScrollPane jsp = new JScrollPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  public AuditPlanActionsPane(DataSource dataSource, FW_ComponentListener fwCListener) {
    this.dataSource = dataSource;
    this.fwCListener = fwCListener;
    initialize();

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwAuditActionsList);
    navBarDetail.setActionListener(fwAuditActionsList);

    fwCListener.addFWComponentListener(fwAuditActionsList);
  }

  public FW_ComponentListener getMasterListener() {
    return fwAuditActionsList;
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();

    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("label.code"),
					       "auditPlanActionID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("label.desc"),
					       "auditPlanActionDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("auditPlanActions.label.startingDay"),
					       "startingday",
					       new TmplIntegerRenderer(),
					       new TmplIntegerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("auditPlanActions.label.durationDays"),
					       "durationdays",
					       new TmplIntegerRenderer(),
					       new TmplIntegerEditor()));
    try {
      fwAuditActionsList = new FW_JTable(dataSource.getDataSourceByName("AuditPlanActions"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    fwAuditActionsList.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwAuditActionsList);
    content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }
}
