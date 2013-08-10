package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import pt.inescporto.template.client.rmi.MenuSingleton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import pt.inescporto.template.client.design.TmplJDatePicker;

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

public class MonitorDefinition extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnMonitorPlan = new TmplLookupButton();
  TmplJTextField jtfldMonitorPlanID = new TmplJTextField();
  TmplLookupField jlfldMonitorPlanDescription = new TmplLookupField();

  private TmplJLabel jlblMonitorDate = new TmplJLabel();
  //public TmplJDateField jtfldMonitorDate = new TmplJDateField();
  public TmplJDatePicker jtfldMonitorDate=new TmplJDatePicker();

  private TmplJLabel jlblMonitorEndDate = new TmplJLabel();
  //public TmplJDateField jtfldMonitorEndDate = new TmplJDateField();
  public TmplJDatePicker jtfldMonitorEndDate=new TmplJDatePicker();

  private TmplJLabel jlblMonitorRealStartDate = new TmplJLabel();
  //public TmplJDateField jtfldMonitorRealStartDate = new TmplJDateField();
  public TmplJDatePicker jtfldMonitorRealStartDate=new TmplJDatePicker();

  private TmplJLabel jlblMonitorRealEndDate = new TmplJLabel();
  //public TmplJDateField jtfldMonitorRealEndDate = new TmplJDateField();
  public TmplJDatePicker jtfldMonitorRealEndDate=new TmplJDatePicker();

  private TmplJLabel jlblMonitorResult = new TmplJLabel();
  private TmplJTextField jtfldMonitorResult = new TmplJTextField();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUserID = new TmplJTextField();
  TmplLookupField jlfldUserDescription = new TmplLookupField();



  public MonitorDefinition(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jlbtnMonitorPlan);
    datasource.addDatasourceListener(jtfldMonitorPlanID);
    datasource.addDatasourceListener(jlfldMonitorPlanDescription);
    datasource.addDatasourceListener(jtfldMonitorDate);
    datasource.addDatasourceListener(jtfldMonitorEndDate);
    datasource.addDatasourceListener(jtfldMonitorRealStartDate);
    datasource.addDatasourceListener(jtfldMonitorRealEndDate);
    datasource.addDatasourceListener(jtfldMonitorResult);
    datasource.addDatasourceListener(jlbtnUser);
    datasource.addDatasourceListener(jtfldUserID);
    datasource.addDatasourceListener(jlfldUserDescription);

    fwCListener.addFWComponentListener(jlbtnMonitorPlan);
    fwCListener.addFWComponentListener(jtfldMonitorPlanID);
    fwCListener.addFWComponentListener(jlfldMonitorPlanDescription);
    fwCListener.addFWComponentListener(jtfldMonitorDate);
    fwCListener.addFWComponentListener(jtfldMonitorEndDate);
    fwCListener.addFWComponentListener(jtfldMonitorRealStartDate);
    fwCListener.addFWComponentListener(jtfldMonitorRealEndDate);
    fwCListener.addFWComponentListener(jtfldMonitorResult);
    fwCListener.addFWComponentListener(jlbtnUser);
    fwCListener.addFWComponentListener(jtfldUserID);
    fwCListener.addFWComponentListener(jlfldUserDescription);

  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldMonitorPlanID.setField("monitorPlanID");
    jlbtnMonitorPlan.setText(res.getString("monitorDef.label.monitorPlanID"));
    jlbtnMonitorPlan.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan");
    if (!MenuSingleton.isSupplier())
      jlbtnMonitorPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnMonitorPlan.setTitle(res.getString("monitorDef.label.monitorPlanIDList"));
    jlbtnMonitorPlan.setDefaultFill(jtfldMonitorPlanID);

    jlfldMonitorPlanDescription.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan");
    jlfldMonitorPlanDescription.setDefaultRefField(jtfldMonitorPlanID);

    jlblMonitorDate.setText(res.getString("monitorDef.label.monitorDate"));
    jtfldMonitorDate.setField("monitorDate");
    jtfldMonitorDate.setHolder(jlblMonitorDate);

    jlblMonitorEndDate.setText(res.getString("monitorDef.label.monitorEndDate"));
    jtfldMonitorEndDate.setField("monitorEndDate");
    jtfldMonitorEndDate.setHolder(jlblMonitorEndDate);

    jlblMonitorRealStartDate.setText(res.getString("monitorDef.label.monitorRealStartDate"));
    jtfldMonitorRealStartDate.setField("monitorRealStartDate");
    jtfldMonitorRealStartDate.setHolder(jlblMonitorRealStartDate);

    jlblMonitorRealEndDate.setText(res.getString("monitorDef.label.monitorRealEndDate"));
    jtfldMonitorRealEndDate.setField("monitorRealEndDate");
    jtfldMonitorRealEndDate.setHolder(jlblMonitorRealEndDate);

    jlblMonitorResult.setText(res.getString("monitorDef.label.monitorResult"));
    jtfldMonitorResult.setField("monitorResult");
    jtfldMonitorResult.setHolder(jlblMonitorResult);

    jtfldUserID.setField("fkUserID");

    jlbtnUser.setText(res.getString("monitorDef.label.monitorUser"));
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnUser.setTitle(res.getString("monitorDef.label.monitorUserList"));
    jlbtnUser.setDefaultFill(jtfldUserID);

    jlfldUserDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUserDescription.setDefaultRefField(jtfldUserID);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref, 5px");

    FormLayout formLayout2 = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					    "5px, pref, 5px");

    JPanel content = new JPanel();
    JPanel datePrev = new JPanel();
    JPanel dateReal = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    datePrev.setOpaque(false);
    datePrev.setLayout(formLayout2);
    dateReal.setOpaque(false);
    dateReal.setLayout(formLayout2);

    formLayout.setRowGroups(new int[][] { {2, 8, 10}
    });

    CellConstraints cc = new CellConstraints();

    content.add(jlbtnMonitorPlan, cc.xy(2, 2));
    content.add(jtfldMonitorPlanID, cc.xy(4, 2));
    content.add(jlfldMonitorPlanDescription, cc.xyw(6, 2, 4));

    datePrev.setBorder(BorderFactory.createTitledBorder(res.getString("monitorDef.label.monitorForseenDate")));
    ((TitledBorder)datePrev.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    datePrev.add(jlblMonitorDate, cc.xy(2, 2));
    datePrev.add(jtfldMonitorDate, cc.xy(4, 2));
    datePrev.add(jlblMonitorEndDate, cc.xy(6, 2));
    datePrev.add(jtfldMonitorEndDate, cc.xy(8, 2));

    content.add(datePrev, cc.xyw(2, 4, 8));

    dateReal.setBorder(BorderFactory.createTitledBorder(res.getString("monitorDef.label.monitorRealDate")));
    ((TitledBorder)dateReal.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    dateReal.add(jlblMonitorRealStartDate, cc.xy(2, 2));
    dateReal.add(jtfldMonitorRealStartDate, cc.xy(4, 2));
    dateReal.add(jlblMonitorRealEndDate, cc.xy(6, 2));
    dateReal.add(jtfldMonitorRealEndDate, cc.xy(8, 2));

    content.add(dateReal, cc.xyw(2, 6, 8));

    content.add(jlblMonitorResult, cc.xy(2, 8));
    content.add(jtfldMonitorResult, cc.xyw(4, 8, 6));

    content.add(jlbtnUser, cc.xy(2, 10, CellConstraints.FILL, CellConstraints.FILL));
    content.add(jtfldUserID, cc.xy(4, 10));
    content.add(jlfldUserDescription, cc.xyw(6, 10, 4));

    add(content, BorderLayout.NORTH);
    add(new ParametersTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
