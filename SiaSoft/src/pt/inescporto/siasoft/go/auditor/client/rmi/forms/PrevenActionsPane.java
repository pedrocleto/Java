package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
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
public class PrevenActionsPane extends FW_InternalFrame {

  TmplLookupButton jlbtnAuditPlan = new TmplLookupButton();
  TmplJTextField jtfldAuditPlan = new TmplJTextField();
  TmplLookupField jlfldAuditPlan = new TmplLookupField();

  TmplLookupButton jlbtnAudit = new TmplLookupButton();
  TmplJDateField jtfldAudit = new TmplJDateField();

  TmplLookupButton jlbtnAuditAction = new TmplLookupButton();
  TmplJTextField jtfldAuditAction = new TmplJTextField();
  TmplLookupField jlfldAuditAction = new TmplLookupField();

  TmplJLabel jlblPreventiveAction = new TmplJLabel();
  TmplJTextField jtfldPreventiveAction = new TmplJTextField();

  TmplJLabel jlblPreventiveActionDesc = new TmplJLabel();
  TmplJTextField jtfldPreventiveActionDesc = new TmplJTextField();

  TmplJLabel jlblDate = new TmplJLabel();
  TmplJDateField jtfldDate = new TmplJDateField();

  TmplJLabel jlblEndDate = new TmplJLabel();
  TmplJDateField jtfldEndDate = new TmplJDateField();

  TmplJLabel jlblRealStartDate = new TmplJLabel();
  TmplJDateField jtfldRealStartDate = new TmplJDateField();

  TmplJLabel jlblRealEndDate = new TmplJLabel();
  TmplJDateField jtfldRealEndDate = new TmplJDateField();

  TmplJLabel jlblPreventiveActionObs = new TmplJLabel();
  TmplJTextField jtfldPreventiveActionObs = new TmplJTextField();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUser = new TmplJTextField();
  TmplLookupField jlfldUser = new TmplLookupField();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  public PrevenActionsPane() {
    super();

   DataSourceRMI master = new DataSourceRMI("PreventiveActions");
   master.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.PreventiveActions");

   setDataSource(master);

    init();
    start();
  }

  public void init(){
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
    dataSource.addDatasourceListener(jlfldAuditAction);

    dataSource.addDatasourceListener(jtfldPreventiveActionDesc);
    dataSource.addDatasourceListener(jtfldDate);
    dataSource.addDatasourceListener(jtfldEndDate);
    dataSource.addDatasourceListener(jtfldRealStartDate);
    dataSource.addDatasourceListener(jtfldRealEndDate);
    dataSource.addDatasourceListener(jtfldPreventiveActionObs);

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
    addFWComponentListener(jlfldAuditAction);

    addFWComponentListener(jtfldPreventiveActionDesc);
    addFWComponentListener(jtfldDate);
    addFWComponentListener(jtfldEndDate);
    addFWComponentListener(jtfldRealStartDate);
    addFWComponentListener(jtfldRealEndDate);
    addFWComponentListener(jtfldPreventiveActionObs);
    addFWComponentListener(jlbtnUser);
    addFWComponentListener(jtfldUser);
    addFWComponentListener(jlfldUser);
  }

  private void jbInit() throws Exception{

    // Audit Plan
    jtfldAuditPlan.setLabel(res.getString("aaDefinition.label.auditPlan"));
    jtfldAuditPlan.setField("auditPlanID");

    jlbtnAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlbtnAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnAuditPlan.setText(res.getString("aaDefinition.label.auditPlan"));
    jlbtnAuditPlan.setTitle(res.getString("aaDefinition.label.auditPlanList"));
    jlbtnAuditPlan.setDefaultFill(jtfldAuditPlan);

    jlfldAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlfldAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldAuditPlan.setDefaultRefField(jtfldAuditPlan);
   // jlfldAuditPlan.tmplInitialize(new TemplateEvent(this));

    //Audit
    jtfldAudit.setLabel(res.getString("auditAction.label.date"));
    jtfldAudit.setField("auditDate");

    jlbtnAudit.setText(res.getString("auditAction.label.date"));
    jlbtnAudit.setTitle(res.getString("actionType.label.auditList"));
    jlbtnAudit.setFillList(new JTextField[] {jtfldAuditPlan, jtfldAudit});
    jlbtnAudit.setComponentLinkList(new JComponent[] {jtfldAuditPlan});
    jlbtnAudit.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");
    jlbtnAudit.setDefaultFill(jtfldAudit);

    jtfldAuditAction.setLabel(res.getString("auditAction.label.auditAction"));
    jtfldAuditAction.setField("auditActionID");

 //   jlfldAuditAction.tmplInitialize(new TemplateEvent(this));

    jlbtnAuditAction.setText(res.getString("auditAction.label.auditAction"));
    jlbtnAuditAction.setTitle(res.getString("actionType.label.auditActionsList"));
    jlbtnAuditAction.setFillList(new JTextField[] {jtfldAuditPlan, jtfldAudit, jtfldAuditAction});
    jlbtnAuditAction.setComponentLinkList(new JComponent[] {jtfldAuditPlan, jtfldAudit});
    jlbtnAuditAction.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditActions");
    jlbtnAuditAction.setDefaultFill(jtfldAuditAction);

    jlblPreventiveAction.setText(res.getString("actionType.label.action"));
    jtfldPreventiveAction.setField("preventiveActionID");
    jtfldPreventiveAction.setHolder(jlblPreventiveAction);

    jlblPreventiveActionDesc.setText(res.getString("actionType.label.desc"));
    jtfldPreventiveActionDesc.setField("preventiveActionDescription");
    jtfldPreventiveActionDesc.setHolder(jlblPreventiveActionDesc);

    jlblDate.setText(res.getString("actionType.label.planStartDate"));
    jtfldDate.setField("planStartDate");
    jtfldDate.setHolder(jlblDate);

    jlblEndDate.setText(res.getString("actionType.label.planEndDate"));
    jtfldEndDate.setField("planEndDate");
    jtfldEndDate.setHolder(jlblEndDate);

    jlblRealStartDate.setText(res.getString("actionType.label.startDate"));
    jtfldRealStartDate.setField("startDate");
    jtfldRealStartDate.setHolder(jlblRealStartDate);

    jlblRealEndDate.setText(res.getString("actionType.label.endDate"));
    jtfldRealEndDate.setField("endDate");
    jtfldRealEndDate.setHolder(jlblRealEndDate);

    jlblPreventiveActionObs.setText(res.getString("actionType.label.obs"));
    jtfldPreventiveActionObs.setField("obs");
    jtfldPreventiveActionObs.setHolder(jlblPreventiveActionObs);

    // User
    jlbtnUser.setText(res.getString("actionType.label.user"));
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnUser.setTitle(res.getString("actionType.label.userList"));
    jlbtnUser.setDefaultFill(jtfldUser);

    jtfldUser.setField("fkUserID");
    jtfldUser.setLabel(res.getString("auditAction.label.user"));
    jlfldUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUser.setDefaultRefField(jtfldUser);
  //  jlfldUser.tmplInitialize(new TemplateEvent(this));

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
                                             "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref, 5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref");

    JPanel jpnlContent = new JPanel();
    jpnlContent.setLayout(formLayout);
    jpnlContent.setOpaque(false);

    formLayout.setRowGroups(new int[][] {{4,6,8}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlbtnAuditPlan, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
    jpnlContent.add(jtfldAuditPlan, cc.xy(4, 2));
    jpnlContent.add(jlfldAuditPlan, cc.xyw(6, 2, 3));

    jpnlContent.add(jlbtnAudit, cc.xy(2, 4));
    jpnlContent.add(jtfldAudit, cc.xy(4, 4));

    jpnlContent.add(jlbtnAuditAction, cc.xy(2, 6));
    jpnlContent.add(jtfldAuditAction, cc.xy(4, 6));
    jpnlContent.add(jlfldAuditAction, cc.xyw(6, 6, 3));

    jpnlContent.add(jlblPreventiveAction, cc.xy(2,8));
    jpnlContent.add(jtfldPreventiveAction, cc.xy(4,8));

    jpnlContent.add(jlblPreventiveActionDesc, cc.xy(2, 10));
    jpnlContent.add(jtfldPreventiveActionDesc, cc.xy(4, 10));

    FormLayout fl = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
				   "5px, pref, 5px");
    JPanel panel = new JPanel(fl);
    panel.setOpaque(false);
    panel.add(jlblDate, cc.xy(2, 2));
    panel.add(jtfldDate, cc.xy(4, 2));
    panel.add(jlblEndDate, cc.xy(6, 2));
    panel.add(jtfldEndDate, cc.xy(8, 2));

    panel.setBorder(BorderFactory.createTitledBorder(res.getString("auditDefinition.label.startPlanDateBorder")));
    TitledBorder titledBorder = (TitledBorder)panel.getBorder();
    titledBorder.setTitleFont(new Font("Dialog", Font.PLAIN, 10));
    jpnlContent.add(panel, cc.xyw(2, 12, 8, CellConstraints.FILL, CellConstraints.FILL));

    JPanel jpnl = new JPanel(fl);
    jpnl.setOpaque(false);
    jpnl.add(jlblRealStartDate, cc.xy(2, 2));
    jpnl.add(jtfldRealStartDate, cc.xy(4, 2));
    jpnl.add(jlblRealEndDate, cc.xy(6, 2));
    jpnl.add(jtfldRealEndDate, cc.xy(8, 2));
    jpnl.setBorder(BorderFactory.createTitledBorder(res.getString("auditDefinition.label.endPlanDateBorder")));
    TitledBorder titledB = (TitledBorder)jpnl.getBorder();
    titledB.setTitleFont(new Font("Dialog", Font.PLAIN, 10));
    jpnlContent.add(jpnl, cc.xyw(2, 14, 8, CellConstraints.FILL, CellConstraints.FILL));

    jpnlContent.add(jlblPreventiveActionObs, cc.xy(2, 16));
    jpnlContent.add(jtfldPreventiveActionObs, cc.xy(4, 16));

    jpnlContent.add(jlbtnUser, cc.xy(2, 18, CellConstraints.FILL, CellConstraints.DEFAULT));
    jpnlContent.add(jtfldUser, cc.xy(4, 18));
    jpnlContent.add(jlfldUser, cc.xyw(6, 18, 3));

    add(jpnlContent, BorderLayout.CENTER);
  }
}
