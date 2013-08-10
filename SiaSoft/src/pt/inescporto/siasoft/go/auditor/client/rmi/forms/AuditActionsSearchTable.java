package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionTypeDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.CorrectiveActionsDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.PreventiveActionsDao;

import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
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
public class AuditActionsSearchTable extends JPanel{
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();
  JScrollPane jsp = new JScrollPane();
  FW_JTable fwAuditActionsType = null;
  AuditActionsSearchDataSource aaDS = null;

  public AuditActionsSearchTable(AuditActionsSearchDataSource aaDS, TmplJButtonUpdate jbtnUpdate) {
    this.aaDS = aaDS;
    this.jbtnUpdate = jbtnUpdate;
    initialize();
  }
  private void initialize(){
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();

    colManager.addColumnNode(new FW_ColumnNode(0,
                                               res.getString("aaSearchTable.label.auditPlan"),
                                               "auditPlanID",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
                                               res.getString("aaSearchTable.label.auditDate"),
                                               "auditDate",
                                               new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(2,
                                               res.getString("aaSearchTable.label.auditAction"),
                                               "auditActionID",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));


    colManager.addColumnNode(new FW_ColumnNode(3,
                                               res.getString("aaSearchTable.label.auditActionType"),
                                               "auditActionTypeID",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(4,
                                               res.getString("aaSearchTable.label.type"),
                                               "type",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(5,
                                               res.getString("aaSearchTable.label.auditActionTypeDesc"),
                                               "auditActionTypeDescription",
                                               new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(6,
                                               res.getString("aaSearchTable.label.planStartDate"),
                                               "planStartDate",
                                               new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(7,
                                               res.getString("aaSearchTable.label.planEndDate"),
                                               "planEndDate",
                                               new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(8,
                                               res.getString("aaSearchTable.label.startDate"),
                                               "startDate",
                                               new TmplDateRenderer(),
					       new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(9,
                                               res.getString("aaSearchTable.label.endDate"),
                                               "endDate",
                                               new TmplDateRenderer(),
                                               new TmplDateEditor()));

    colManager.addColumnNode(new FW_ColumnNode(10,
                                              res.getString("asDefinition.label.state"),
                                              "status",
                                              new TmplLookupRenderer(new Object[] {res.getString("asDefinition.label.waiting"), res.getString("asDefinition.label.running"), res.getString("asDefinition.label.delayed"), res.getString("asDefinition.label.ended"), res.getString("asDefinition.label.schedule")}, new Object[] {"W", "R", "D", "E", "S"}),
                                              new TmplStringEditor()));


    colManager.addColumnNode(new FW_ColumnNode(11,
                                              res.getString("aaSearchTable.label.obs"),
                                              "obs",
                                              new TmplStringRenderer(),
                                              new TmplStringEditor()));
   colManager.addColumnNode(new FW_ColumnNode(12,
                                              res.getString("aaSearchTable.label.user"),
                                              "fkUserID",
                                              new TmplStringRenderer(),
                                              new TmplStringEditor()));

   fwAuditActionsType = new FW_JTable(aaDS, null, colManager);
   fwAuditActionsType.setAsMaster(true);

   fwAuditActionsType.addMouseListener(new MouseAdapter() {
     public void mousePressed(MouseEvent e) {
       if(e.getClickCount() == 2){
          AuditActionTypeDao aatDao = (AuditActionTypeDao)((FW_TableModel)fwAuditActionsType.getModel()).getAttrsAt(fwAuditActionsType.getSelectedRow());
          if (aatDao.type.getValue().equals("C")) {
            CorrectiveActionsDao caDao = new CorrectiveActionsDao();
            caDao.auditPlanID.setValue(aatDao.auditPlanID.getValue());
            caDao.auditDate.setValue(aatDao.auditDate.getValue());
            caDao.auditActionID.setValue(aatDao.auditActionID.getValue());
            caDao.correctiveActionID.setValue(aatDao.auditActionTypeID.getValue());

           // MenuSingleton.loadForm("CORRECTIVEACTIONS", caDao);

          }
          else {
            PreventiveActionsDao paDao = new PreventiveActionsDao();
            paDao.auditPlanID.setValue(aatDao.auditPlanID.getValue());
            paDao.auditDate.setValue(aatDao.auditDate.getValue());
            paDao.auditActionID.setValue(aatDao.auditActionID.getValue());
            paDao.preventiveActionID.setValue(aatDao.auditActionTypeID.getValue());

            // MenuSingleton.loadForm("PREVENTIVEACTIONS", paDao);
          }
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

   jsp.getViewport().add(fwAuditActionsType);
   content.add(jsp, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
   add(content, BorderLayout.CENTER);
   add(jbtnUpdate, BorderLayout.SOUTH);
  }
}
