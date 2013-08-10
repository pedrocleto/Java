package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import javax.swing.JPanel;
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
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditStatedDao;

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
public class AuditSearchTable extends JPanel {

  AuditSearchDataSource asDataSource = null;
  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();
  JScrollPane jsp = new JScrollPane();
  FW_JTable fwAudit = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  public AuditSearchTable(AuditSearchDataSource asDataSource, TmplJButtonUpdate jbtnUpdate) {
    this.asDataSource = asDataSource;
    this.jbtnUpdate = jbtnUpdate;
    initialize();
    fwAudit.start();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("asDefinition.label.auditPlan"),
					       "auditPlanID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("asDefinition.label.auditDate"),
					       "auditDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("asDefinition.label.auditEndDate"),
					       "auditEndDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("auditDefinition.label.auditRealStartDate"),
					       "auditRealStartDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("auditDefinition.label.auditRealEndDate"),
					       "auditRealEndDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(5, res.getString("asDefinition.label.result"),
                                               "auditResult",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("asDefinition.label.state"),
					       "status",
					       new TmplLookupRenderer(new Object[] {res.getString("asDefinition.label.waiting"), res.getString("asDefinition.label.running"), res.getString("asDefinition.label.delayed"), res.getString("asDefinition.label.ended"), res.getString("asDefinition.label.schedule")}, new Object[] {"W", "R", "D", "E", "S"}),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(7,
                                               res.getString("asDefinition.label.user"),
                                               "fkUserID",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(8,
                                               res.getString("asDefinition.label.auditEntity"),
                                               "fkAuditEntityID",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));

    fwAudit = new FW_JTable(asDataSource, null, colManager);
    fwAudit.setAsMaster(true);

    fwAudit.addMouseListener(new MouseAdapter() {
     public void mousePressed(MouseEvent e) {
       if(e.getClickCount() == 2){
	AuditStatedDao auditStatedDao = (AuditStatedDao)((FW_TableModel)fwAudit.getModel()).getAttrsAt(fwAudit.getSelectedRow());
        String auditPlanID = auditStatedDao.auditPlanID.getValue();
        MenuSingleton.loadForm("AUDIT_M", auditPlanID);
       }
     }
   });
    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});

    CellConstraints cc = new CellConstraints();
    jsp.getViewport().add(fwAudit);
    content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
    add(content, BorderLayout.CENTER);
    add(jbtnUpdate, BorderLayout.SOUTH);
  }
}
