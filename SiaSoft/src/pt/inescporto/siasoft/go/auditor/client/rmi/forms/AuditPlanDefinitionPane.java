package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplJDatePicker;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
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
 * @author Rute
 * @version 0.1
 */
public class AuditPlanDefinitionPane extends JPanel {
  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplJLabel jlblAuditPlanID = new TmplJLabel();
  TmplJTextField jtfldAuditPlanID = new TmplJTextField();

  TmplJLabel jlblAuditPlanScope = new TmplJLabel();
  TmplJTextField jtfldAuditPlanScope = new TmplJTextField();

  TmplJLabel jlblAuditPlanPropose = new TmplJLabel();
  TmplJTextField jtfldAuditPlanPropose = new TmplJTextField();

  TmplJLabel jlblPeriodicity = new TmplJLabel();
  TmplJComboBox jcmbPeriodicity = new TmplJComboBox();

  TmplJLabel jlblNextAuditDate = new TmplJLabel();
  //TmplJDateField jtfldNextAuditDate = new TmplJDateField();
  TmplJDatePicker jtfldNextAuditDate = new TmplJDatePicker();

  TmplJLabel jlblAuditType = new TmplJLabel();
  TmplJComboBox jcmbAuditType = new TmplJComboBox();

  TmplLookupButton jlbtnActivity = new TmplLookupButton();
  TmplJTextField jtfldActivity = new TmplJTextField();
  TmplLookupField jlfldActivity = new TmplLookupField();

  public AuditPlanDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldAuditPlanID);
    datasource.addDatasourceListener(jtfldAuditPlanScope);
    datasource.addDatasourceListener(jtfldAuditPlanPropose);
    datasource.addDatasourceListener(jcmbPeriodicity);
    datasource.addDatasourceListener(jtfldNextAuditDate);
    datasource.addDatasourceListener(jcmbAuditType);
    datasource.addDatasourceListener(jlbtnActivity);
    datasource.addDatasourceListener(jtfldActivity);
    datasource.addDatasourceListener(jlfldActivity);

    fwCListener.addFWComponentListener(jtfldAuditPlanID);
    fwCListener.addFWComponentListener(jtfldAuditPlanScope);
    fwCListener.addFWComponentListener(jtfldAuditPlanPropose);
    fwCListener.addFWComponentListener(jcmbPeriodicity);
    fwCListener.addFWComponentListener(jtfldNextAuditDate);
    fwCListener.addFWComponentListener(jcmbAuditType);
    fwCListener.addFWComponentListener(jlbtnActivity);
    fwCListener.addFWComponentListener(jtfldActivity);
    fwCListener.addFWComponentListener(jlfldActivity);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblAuditPlanID.setText(res.getString("label.code"));
    jtfldAuditPlanID.setField("auditPlanID");
    jtfldAuditPlanID.setHolder(jlblAuditPlanID);

    jlblAuditPlanScope.setText(res.getString("auditPlan.label.scope"));
    jtfldAuditPlanScope.setField("auditPlanScope");
    jtfldAuditPlanScope.setHolder(jlblAuditPlanScope);

    jlblAuditPlanPropose.setText(res.getString("auditPlan.label.propose"));
    jtfldAuditPlanPropose.setField("auditPlanPropose");
    jtfldAuditPlanPropose.setHolder(jlblAuditPlanPropose);

    jlblPeriodicity.setText(res.getString("auditPlan.label.periodicity"));
    jcmbPeriodicity.setField("periodicity");
    jcmbPeriodicity.setDataItems(new Object[] {"", "Mensal", "Bimensal", "Trimestral", "Quadrimestral", "Semestral", "Anual"});
    jcmbPeriodicity.setDataValues(new Object[] {null, "M", "B", "T", "Q", "S", "A"});

    jlblAuditType.setText(res.getString("auditPlan.label.auditType"));
    jcmbAuditType.setField("auditType");
    jcmbAuditType.setDataItems(new Object[] {"", res.getString("asDefinition.label.internal"), res.getString("asDefinition.label.external")});
    jcmbAuditType.setDataValues(new Object[] {null, "I", "E"});

    jlblNextAuditDate.setText(res.getString("auditPlan.label.nextAuditDate"));
    jtfldNextAuditDate.setField("nextAuditDate");
    jtfldNextAuditDate.setHolder(jlblNextAuditDate);

    jtfldActivity.setLabel(res.getString("auditPlanLink.label.activity"));
    jtfldActivity.setField("fkActivityID");
    jlbtnActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      jlbtnActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActivity.setText(res.getString("auditPlanLink.label.activity"));
    jlbtnActivity.setTitle(res.getString("auditPlanLink.label.activityList"));
    jlbtnActivity.setDefaultFill(jtfldActivity);
    jlfldActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlfldActivity.setDefaultRefField(jtfldActivity);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8, 10, 12}});

    CellConstraints cc = new CellConstraints();

    content.add(jlblAuditPlanID, cc.xy(2, 2));
    content.add(jtfldAuditPlanID, cc.xy(4, 2));

    content.add(jlblAuditPlanScope, cc.xy(2, 4));
    content.add(jtfldAuditPlanScope, cc.xyw(4, 4, 6));

    content.add(jlblAuditPlanPropose, cc.xy(2, 6));
    content.add(jtfldAuditPlanPropose, cc.xyw(4, 6, 6));

    content.add(jlblPeriodicity, cc.xy(2, 8));
    content.add(jcmbPeriodicity, cc.xy(4, 8));

    content.add(jlblNextAuditDate, cc.xy(6, 8));
    content.add(jtfldNextAuditDate, cc.xy(8, 8));

    content.add(jlblAuditType, cc.xy(2, 10));
    content.add(jcmbAuditType, cc.xy(4, 10));

    content.add(jlbtnActivity, cc.xy(2, 12, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldActivity, cc.xy(4, 12));
    content.add(jlfldActivity, cc.xyw(6, 12, 4));

    add(content, BorderLayout.NORTH);
    add(new AuditActionTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
