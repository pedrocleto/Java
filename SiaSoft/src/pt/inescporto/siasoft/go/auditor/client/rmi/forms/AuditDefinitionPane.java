package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Font;
import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.TmplJDatePicker;
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
public class AuditDefinitionPane extends JPanel {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;

  TmplLookupButton jlbtnAuditPlan = new TmplLookupButton();
  TmplJTextField jtfldAuditPlan = new TmplJTextField();
  TmplLookupField jlfldAuditPlan = new TmplLookupField();

  private TmplJLabel jlblAuditDate = new TmplJLabel();
  public TmplJDatePicker jtfldAuditDate = new TmplJDatePicker();

  private TmplJLabel jlblAuditEndDate = new TmplJLabel();
  public TmplJDatePicker jtfldAuditEndDate = new TmplJDatePicker();

  private TmplJLabel jlblAuditRealStartDate = new TmplJLabel();
  public TmplJDatePicker jtfldAuditRealStartDate = new TmplJDatePicker();

  private TmplJLabel jlblAuditRealEndDate = new TmplJLabel();
  public TmplJDatePicker jtfldAuditRealEndDate = new TmplJDatePicker();

  private TmplJLabel jlblAuditResult = new TmplJLabel();
  private TmplJTextField jtfldAuditResult = new TmplJTextField();

  TmplLookupButton jlbtnDoc = new TmplLookupButton();
  TmplJTextField jtfldDoc = new TmplJTextField();
  TmplLookupField jlfldDoc = new TmplLookupField();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUser = new TmplJTextField();
  TmplLookupField jlfldUser = new TmplLookupField();

  TmplLookupButton jlbtnAuditEntity = new TmplLookupButton();
  TmplJTextField jtfldAuditEntity = new TmplJTextField();
  TmplLookupField jlfldAuditEntity = new TmplLookupField();

  public AuditDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jlbtnAuditPlan);
    datasource.addDatasourceListener(jtfldAuditPlan);
    datasource.addDatasourceListener(jlfldAuditPlan);
    datasource.addDatasourceListener(jtfldAuditDate);
    datasource.addDatasourceListener(jtfldAuditEndDate);
    datasource.addDatasourceListener(jtfldAuditRealStartDate);
    datasource.addDatasourceListener(jtfldAuditRealEndDate);
    datasource.addDatasourceListener(jtfldAuditResult);
    datasource.addDatasourceListener(jlbtnUser);
    datasource.addDatasourceListener(jtfldUser);
    datasource.addDatasourceListener(jlfldUser);
    datasource.addDatasourceListener(jlbtnAuditEntity);
    datasource.addDatasourceListener(jtfldAuditEntity);
    datasource.addDatasourceListener(jlfldAuditEntity);
    datasource.addDatasourceListener(jlbtnDoc);
    datasource.addDatasourceListener(jtfldDoc);
    datasource.addDatasourceListener(jlfldDoc);

    fwCListener.addFWComponentListener(jlbtnAuditPlan);
    fwCListener.addFWComponentListener(jtfldAuditPlan);
    fwCListener.addFWComponentListener(jlfldAuditPlan);
    fwCListener.addFWComponentListener(jtfldAuditDate);
    fwCListener.addFWComponentListener(jtfldAuditEndDate);
    fwCListener.addFWComponentListener(jtfldAuditRealStartDate);
    fwCListener.addFWComponentListener(jtfldAuditRealEndDate);
    fwCListener.addFWComponentListener(jtfldAuditResult);

    fwCListener.addFWComponentListener(jlbtnDoc);
    fwCListener.addFWComponentListener(jtfldDoc);
    fwCListener.addFWComponentListener(jlfldDoc);

    fwCListener.addFWComponentListener(jlbtnUser);
    fwCListener.addFWComponentListener(jtfldUser);
    fwCListener.addFWComponentListener(jlfldUser);
    fwCListener.addFWComponentListener(jlbtnAuditEntity);
    fwCListener.addFWComponentListener(jtfldAuditEntity);
    fwCListener.addFWComponentListener(jlfldAuditEntity);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldAuditPlan.setLabel(res.getString("auditDefinition.label.auditPlan"));
    jtfldAuditPlan.setField("auditPlanID");
    jlbtnAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlbtnAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnAuditPlan.setText(res.getString("auditDefinition.label.auditPlan"));
    jlbtnAuditPlan.setTitle(res.getString("auditDefinition.label.auditPlanList"));
    jlbtnAuditPlan.setDefaultFill(jtfldAuditPlan);
    jlfldAuditPlan.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");
    if (!MenuSingleton.isSupplier())
      jlfldAuditPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldAuditPlan.setDefaultRefField(jtfldAuditPlan);

    jlblAuditDate.setText(res.getString("auditDefinition.label.auditDate"));
    jtfldAuditDate.setField("auditDate");
    jtfldAuditDate.setHolder(jlblAuditDate);

    jlblAuditEndDate.setText(res.getString("auditDefinition.label.auditEndDate"));
    jtfldAuditEndDate.setField("auditEndDate");
    jtfldAuditEndDate.setHolder(jlblAuditEndDate);

    jlblAuditRealStartDate.setText(res.getString("auditDefinition.label.auditRealStartDate"));
    jtfldAuditRealStartDate.setField("auditRealStartDate");
    jtfldAuditRealStartDate.setHolder(jlblAuditRealStartDate);

    jlblAuditRealEndDate.setText(res.getString("auditDefinition.label.auditRealEndDate"));
    jtfldAuditRealEndDate.setField("auditRealEndDate");
    jtfldAuditRealEndDate.setHolder(jlblAuditRealEndDate);

    jlblAuditResult.setText(res.getString("auditDefinition.label.auditResult"));
    jtfldAuditResult.setField("auditResult");
    jtfldAuditResult.setHolder(jlblAuditResult);

    jtfldDoc.setLabel(res.getString("auditDefinition.label.document"));
    jtfldDoc.setField("fkDocumentID");
    jlbtnDoc.setUrl("pt.inescporto.siasoft.condoc.ejb.session.Document");
    jlbtnDoc.setText(res.getString("auditDefinition.label.document"));
    jlbtnDoc.setTitle(res.getString("auditDefinition.label.documentList"));
    jlbtnDoc.setDefaultFill(jtfldDoc);
    jlfldDoc.setUrl("pt.inescporto.siasoft.condoc.ejb.session.Document");
    jlfldDoc.setDefaultRefField(jtfldDoc);

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

    jtfldAuditEntity.setLabel(res.getString("auditDefinition.label.auditEntity"));
    jtfldAuditEntity.setField("fkAuditEntityID");
    jlbtnAuditEntity.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditEntity");
    jlbtnAuditEntity.setText(res.getString("auditDefinition.label.auditEntity"));
    jlbtnAuditEntity.setTitle(res.getString("auditDefinition.label.auditEntityList"));
    jlbtnAuditEntity.setDefaultFill(jtfldAuditEntity);
    jlfldAuditEntity.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditEntity");
    jlfldAuditEntity.setDefaultRefField(jtfldAuditEntity);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,50dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref, 5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2,8,10}});

    CellConstraints cc = new CellConstraints();

    content.add(jlbtnAuditPlan, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldAuditPlan, cc.xy(4, 2));
    content.add(jlfldAuditPlan, cc.xyw(6, 2, 3));

    FormLayout fl = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,50dlu:grow, 5px",
				   "5px, pref, 5px");
    JPanel jpnlCh = new JPanel(fl);
    jpnlCh.setOpaque(false);
    jpnlCh.add(jlblAuditDate, cc.xy(2, 2));
    jpnlCh.add(jtfldAuditDate, cc.xy(4, 2));
    jpnlCh.add(jlblAuditEndDate, cc.xy(6, 2));
    jpnlCh.add(jtfldAuditEndDate, cc.xy(8, 2));
    jpnlCh.setBorder(BorderFactory.createTitledBorder(res.getString("auditDefinition.label.startPlanDateBorder")));
    TitledBorder titledBorder = (TitledBorder)jpnlCh.getBorder();
    titledBorder.setTitleFont(new Font("Dialog", Font.PLAIN, 10));
    content.add(jpnlCh, cc.xyw(2, 4, 8, CellConstraints.FILL, CellConstraints.FILL));

    JPanel jpnl = new JPanel(fl);
    jpnl.setOpaque(false);
    jpnl.add(jlblAuditRealStartDate, cc.xy(2, 2));
    jpnl.add(jtfldAuditRealStartDate, cc.xy(4, 2));
    jpnl.add(jlblAuditRealEndDate, cc.xy(6, 2));
    jpnl.add(jtfldAuditRealEndDate, cc.xy(8, 2));
    jpnl.setBorder(BorderFactory.createTitledBorder(res.getString("auditDefinition.label.endPlanDateBorder")));
    TitledBorder titledB = (TitledBorder)jpnl.getBorder();
    titledB.setTitleFont(new Font("Dialog", Font.PLAIN, 10));
    content.add(jpnl, cc.xyw(2, 6, 8, CellConstraints.FILL, CellConstraints.FILL));

    content.add(jlblAuditResult, cc.xy(2, 8));
    content.add(jtfldAuditResult, cc.xy(4, 8));

    content.add(jlbtnDoc, cc.xy(2,10, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldDoc, cc.xy(4,10));
    content.add(jlfldDoc, cc.xyw(6, 10,3));

    content.add(jlbtnUser, cc.xy(2, 12, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldUser, cc.xy(4, 12));
    content.add(jlfldUser, cc.xyw(6, 12, 3));

    content.add(jlbtnAuditEntity, cc.xy(2, 14, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldAuditEntity, cc.xy(4, 14));
    content.add(jlfldAuditEntity, cc.xyw(6, 14, 3));

    add(content, BorderLayout.NORTH);
    add(new AuditTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
