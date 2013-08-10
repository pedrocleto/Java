package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ResourceBundle;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
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

public class CorrectiveActionsForm extends FW_InternalFrame {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnAuditPlan = new TmplLookupButton();
  TmplJTextField jtfldAuditPlan = new TmplJTextField();
  TmplLookupField jlfldAuditPlan = new TmplLookupField();

  TmplLookupButton jlbtnAudit = new TmplLookupButton();
  TmplJDateField jtfldAudit = new TmplJDateField();

  TmplLookupButton jlbtnAuditAction = new TmplLookupButton();
  TmplJTextField jtfldAuditAction = new TmplJTextField();

  TmplJLabel jlblCorrectiveAction = new TmplJLabel();
  TmplJTextField jtfldCorrectiveAction = new TmplJTextField();

  TmplJLabel jlblCorrectiveDesc = new TmplJLabel();
  TmplJTextField jtfldCorrectiveDesc = new TmplJTextField();

  TmplJLabel jlblPlanStartDate = new TmplJLabel();
  TmplJDateField jtfldPlanStartDate = new TmplJDateField();

  TmplJLabel jlblPlanEndDate = new TmplJLabel();
  TmplJDateField jtfldPlanEndDate = new TmplJDateField();

  TmplJLabel jlblStartDate = new TmplJLabel();
  TmplJDateField jtfldStartDate = new TmplJDateField();

  TmplJLabel jlblEndDate = new TmplJLabel();
  TmplJDateField jtfldEndDate = new TmplJDateField();

  TmplJLabel jlblObs = new TmplJLabel();
  TmplJTextField jtfldObs = new TmplJTextField();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUser = new TmplJTextField();
  TmplLookupField jlfldUser = new TmplLookupField();

  DataSourceRMI detail = null;
  DataSourceRMI detailAction = null;

  DataSourceRMI dsCorrectiveActions = null;
  DataSourceRMI dsAuditActions = null;

  public CorrectiveActionsForm() {
    super();
    /*
    DataSourceRMI master = new DataSourceRMI("AuditPlan");
    master.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");

    detail = new DataSourceRMI("Audit");
    detail.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");

    DSRelation mdRelation = new DSRelation("AuditPlanAudit");
    mdRelation.setMaster(master);
    mdRelation.setDetail(detail);
    mdRelation.addKey(new RelationKey("auditPlanID", "auditPlanID"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    detailAction = new DataSourceRMI("AuditActions");
    detailAction.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditActions");

    DSRelation dsrAAuditActions = new DSRelation("AuditAuditActions");
    dsrAAuditActions.setMaster(detail);
    dsrAAuditActions.setDetail(detailAction);
    dsrAAuditActions.addKey(new RelationKey("auditPlanID", "auditPlanID"));
    dsrAAuditActions.addKey(new RelationKey("auditDate", "auditDate"));

    try {
      detail.addDataSourceLink(dsrAAuditActions);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    */
    dsCorrectiveActions = new DataSourceRMI("CorrectiveActions");
    dsCorrectiveActions.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.CorrectiveActions");
    setDataSource(dsCorrectiveActions);

    init();
    start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    dataSource.addDatasourceListener(jlbtnAuditPlan);
    dataSource.addDatasourceListener(jtfldAuditPlan);
    dataSource.addDatasourceListener(jlfldAuditPlan);

    dataSource.addDatasourceListener(jlbtnAudit);
    dataSource.addDatasourceListener(jtfldAudit);

    dataSource.addDatasourceListener(jlbtnAuditAction);
    dataSource.addDatasourceListener(jtfldAuditAction);

    dataSource.addDatasourceListener(jtfldCorrectiveAction);
    dataSource.addDatasourceListener(jtfldCorrectiveDesc);

    dataSource.addDatasourceListener(jtfldPlanStartDate);
    dataSource.addDatasourceListener(jtfldPlanEndDate);
    dataSource.addDatasourceListener(jtfldStartDate);

    dataSource.addDatasourceListener(jtfldEndDate);
    dataSource.addDatasourceListener(jtfldObs);

    dataSource.addDatasourceListener(jlbtnUser);
    dataSource.addDatasourceListener(jtfldUser);
    dataSource.addDatasourceListener(jlfldUser);

    addFWComponentListener(jlbtnAuditPlan);
    addFWComponentListener(jtfldAuditPlan);
    addFWComponentListener(jlfldAuditPlan);

    addFWComponentListener(jlbtnAudit);
    addFWComponentListener(jtfldAudit);

    addFWComponentListener(jlbtnAuditAction);
    addFWComponentListener(jtfldAuditAction);

    addFWComponentListener(jtfldCorrectiveAction);
    addFWComponentListener(jtfldCorrectiveDesc);

    addFWComponentListener(jtfldPlanStartDate);
    addFWComponentListener(jtfldPlanEndDate);
    addFWComponentListener(jtfldStartDate);
    addFWComponentListener(jtfldEndDate);
    addFWComponentListener(jtfldObs);

    addFWComponentListener(jlbtnUser);
    addFWComponentListener(jtfldUser);
    addFWComponentListener(jlfldUser);
  }

  private void jbInit() throws Exception {

    // Audit Plan
    jtfldAuditPlan.setLabel("audit plan");
    jtfldAuditPlan.setField("auditPlanID");
    jlbtnAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");

    if (!MenuSingleton.isSupplier())
      jlbtnAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnAuditPlan.setText("audit plan");
    jlbtnAuditPlan.setTitle("audit plan list");
    jlbtnAuditPlan.setDefaultFill(jtfldAuditPlan);
    jlfldAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");

    if (!MenuSingleton.isSupplier())
      jlfldAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldAuditPlan.setDefaultRefField(jtfldAuditPlan);

    // Audit
    jtfldAudit.setField("auditDate");
    jtfldAudit.setLabel("audit");

    jlbtnAudit.setText("audit");
    jlbtnAudit.setTitle("auditList");
    jlbtnAudit.setFillList(new JTextField[] {jtfldAuditPlan, jtfldAudit});
    jlbtnAudit.setComponentLinkList(new JComponent[] {jtfldAuditPlan});
    jlbtnAudit.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");

    // Audit Action
    jtfldAuditAction.setField("auditActionID");
    jtfldAuditAction.setLabel("auditAction");

    jlbtnAuditAction.setText("auditAction");
    jlbtnAuditAction.setTitle("auditAction List");
    jlbtnAuditAction.setFillList(new JTextField[] {jtfldAuditPlan, jtfldAudit, jtfldAuditAction});
    jlbtnAuditAction.setComponentLinkList(new JComponent[] {jtfldAuditPlan, jtfldAudit});
    jlbtnAuditAction.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditActions");

    // Corrective Action
    jlblCorrectiveAction.setText("corrective action");
    jtfldCorrectiveAction.setField("correctiveActionID");
    jtfldCorrectiveAction.setHolder(jlblCorrectiveAction);

    jlblCorrectiveDesc.setText("corrective desc");
    jtfldCorrectiveDesc.setField("correctiveActionDescription");
    jtfldCorrectiveDesc.setHolder(jlblCorrectiveDesc);

    // Plan Start Date
    jlblPlanStartDate.setText(res.getString("auditDefinition.label.auditDate"));
    jtfldPlanStartDate.setField("planStartDate");
    jtfldPlanStartDate.setHolder(jlblPlanStartDate);

    // Plan End Date
    jlblPlanEndDate.setText(res.getString("auditDefinition.label.auditEndDate"));
    jtfldPlanEndDate.setField("planEndDate");
    jtfldPlanEndDate.setHolder(jlblPlanEndDate);

    // Start Date
    jlblStartDate.setText(res.getString("auditDefinition.label.auditRealStartDate"));
    jtfldStartDate.setField("startDate");
    jtfldStartDate.setHolder(jlblStartDate);

    // End Date
    jlblEndDate.setText(res.getString("auditDefinition.label.auditRealEndDate"));
    jtfldEndDate.setField("endDate");
    jtfldEndDate.setHolder(jlblEndDate);

    // Obs
    jlblObs.setText("obs");
    jtfldObs.setField("obs");
    jtfldObs.setHolder(jlblObs);

    // User
    jtfldUser.setLabel(res.getString("auditDefinition.label.user"));
    jtfldUser.setField("fkUserID");
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");

    jlbtnUser.setText(res.getString("auditDefinition.label.user"));
    jlbtnUser.setTitle(res.getString("auditDefinition.label.userList"));
    jlbtnUser.setDefaultFill(jtfldUser);
    jlfldUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUser.setDefaultRefField(jtfldUser);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref, 5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 6, 8, 10}
    });

    CellConstraints cc = new CellConstraints();

    content.add(jlbtnAuditPlan, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldAuditPlan, cc.xy(4, 2));
    content.add(jlfldAuditPlan, cc.xyw(6, 2, 3));

    content.add(jlbtnAudit, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldAudit, cc.xy(4, 4));

    content.add(jlbtnAuditAction, cc.xy(2, 6, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldAuditAction, cc.xy(4, 6));

    FormLayout fl = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				   "5px, pref, 5px");

    JPanel panel = new JPanel(fl);
    panel.setOpaque(false);

    panel.add(jlblCorrectiveAction, cc.xy(2, 2));
    panel.add(jtfldCorrectiveAction, cc.xy(4, 2));

    panel.add(jlblCorrectiveDesc, cc.xy(6, 2));
    panel.add(jtfldCorrectiveDesc, cc.xy(8, 2));
    content.add(panel, cc.xyw(2, 8, 8, CellConstraints.FILL, CellConstraints.FILL));

    JPanel jpnlCh = new JPanel(fl);
    jpnlCh.setOpaque(false);

    jpnlCh.add(jlblPlanStartDate, cc.xy(2, 2));
    jpnlCh.add(jtfldPlanStartDate, cc.xy(4, 2));
    jpnlCh.add(jlblPlanEndDate, cc.xy(6, 2));
    jpnlCh.add(jtfldPlanEndDate, cc.xy(8, 2));

    jpnlCh.setBorder(BorderFactory.createTitledBorder(res.getString("auditDefinition.label.startPlanDateBorder")));
    TitledBorder titledBorder = (TitledBorder)jpnlCh.getBorder();
    titledBorder.setTitleFont(new Font("Dialog", Font.PLAIN, 10));
    content.add(jpnlCh, cc.xyw(2, 10, 8, CellConstraints.FILL, CellConstraints.FILL));

    JPanel jpnl = new JPanel(fl);
    jpnl.setOpaque(false);

    jpnl.add(jlblStartDate, cc.xy(2, 2));
    jpnl.add(jtfldStartDate, cc.xy(4, 2));
    jpnl.add(jlblEndDate, cc.xy(6, 2));
    jpnl.add(jtfldEndDate, cc.xy(8, 2));

    jpnl.setBorder(BorderFactory.createTitledBorder(res.getString("auditDefinition.label.endPlanDateBorder")));
    TitledBorder titledB = (TitledBorder)jpnl.getBorder();
    titledB.setTitleFont(new Font("Dialog", Font.PLAIN, 10));
    content.add(jpnl, cc.xyw(2, 12, 8, CellConstraints.FILL, CellConstraints.FILL));

    content.add(jlblObs, cc.xy(2, 14, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldObs, cc.xy(4, 14));

    content.add(jlbtnUser, cc.xy(2, 16, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldUser, cc.xy(4, 16));
    content.add(jlfldUser, cc.xyw(6, 16, 3));

    add(content, BorderLayout.CENTER);
  }
}
