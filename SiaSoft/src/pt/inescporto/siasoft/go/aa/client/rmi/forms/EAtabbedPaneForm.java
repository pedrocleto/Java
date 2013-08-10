package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.util.ResourceBundle;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.design.TmplJComboBox;
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
public class EAtabbedPaneForm extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  //
  TmplJLabel jlblEnvAspID = new TmplJLabel();
  TmplJTextField jtfldEnvAspID = new TmplJTextField();
  TmplJLabel jlblEnvAspName = new TmplJLabel();
  TmplJTextField jtfldEnvAspName = new TmplJTextField();

  TmplLookupButton lbtnEnvAspClass = new TmplLookupButton();
  TmplJTextField jtfldEnvAspClass = new TmplJTextField();
  TmplLookupField jlfldEnvAspClass = new TmplLookupField();

  TmplJButton jbtnProc = new TmplJButton();
  TmplJLabel jlblDescription = new TmplJLabel();
  TmplJTextArea jtaDescription = new TmplJTextArea();
  JScrollPane jspDescription = new JScrollPane();

  TmplLookupButton lbtnActivity = new TmplLookupButton();
  TmplJTextField jtfldActivity = new TmplJTextField();
  TmplLookupField jlfldActivity = new TmplLookupField();

  TmplLookupButton lbtnUserResp = new TmplLookupButton();
  TmplJTextField jtfldUserResp = new TmplJTextField();
  TmplLookupField jlfldUserResp = new TmplLookupField();
  TmplJLabel jlblState = new TmplJLabel();
  TmplJComboBox jcmbState = new TmplJComboBox();

  public EAtabbedPaneForm(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jtfldEnvAspName);
    fwCListener.addFWComponentListener(lbtnEnvAspClass);
    fwCListener.addFWComponentListener(jtfldEnvAspClass);
    fwCListener.addFWComponentListener(jlfldEnvAspClass);
    fwCListener.addFWComponentListener(jtaDescription);
    fwCListener.addFWComponentListener(lbtnActivity);
    fwCListener.addFWComponentListener(jtfldActivity);
    fwCListener.addFWComponentListener(jlfldActivity);
    fwCListener.addFWComponentListener(lbtnUserResp);
    fwCListener.addFWComponentListener(jtfldUserResp);
    fwCListener.addFWComponentListener(jlfldUserResp);
    fwCListener.addFWComponentListener(jcmbState);

    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jtfldEnvAspName);
    datasource.addDatasourceListener(jtfldEnvAspClass);
    datasource.addDatasourceListener(jlfldEnvAspClass);
    datasource.addDatasourceListener(jtaDescription);
    datasource.addDatasourceListener(jtfldActivity);
    datasource.addDatasourceListener(jlfldActivity);
    datasource.addDatasourceListener(jtfldUserResp);
    datasource.addDatasourceListener(jlfldUserResp);
    datasource.addDatasourceListener(jcmbState);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblEnvAspID.setText(res.getString("eaTabbedPane.label.code"));
    jtfldEnvAspID.setField("envAspID");
    jtfldEnvAspID.setHolder(jlblEnvAspID);

    jlblEnvAspName.setText(res.getString("eaTabbedPane.label.name"));
    jtfldEnvAspName.setField("envAspName");
    jtfldEnvAspName.setHolder(jlblEnvAspName);

    jtfldEnvAspClass.setField("envAspClassID");
    jtfldEnvAspClass.setLabel(res.getString("eaTabbedPane.label.class"));
    lbtnEnvAspClass.setText(res.getString("eaTabbedPane.label.class"));
    lbtnEnvAspClass.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspClass");
    lbtnEnvAspClass.setFillList(new JTextField[] {jtfldEnvAspClass});
    lbtnEnvAspClass.setTitle(res.getString("eaTabbedPane.label.class"));
    jlfldEnvAspClass.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspClass");
    jlfldEnvAspClass.setDefaultRefField(jtfldEnvAspClass);

    jlblDescription.setText(res.getString("eaTabbedPane.label.description"));
    jtaDescription.setHolder(jlblDescription);
    jtaDescription.setField("envAspDescription");
    jtaDescription.setWrapStyleWord(true);
    jtaDescription.setLineWrap(true);
    jspDescription.getViewport().add(jtaDescription);

    jtfldActivity.setField("activityID");
    jtfldActivity.setLabel(res.getString("eaLinkTabbedPane.label.activity"));
    lbtnActivity.setText(res.getString("eaLinkTabbedPane.label.activity"));
    lbtnActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      lbtnActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    lbtnActivity.setFillList(new JTextField[] {jtfldActivity});
    lbtnActivity.setTitle(res.getString("eaLinkTabbedPane.label.list"));
    jlfldActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlfldActivity.setDefaultRefField(jtfldActivity);
    if (!MenuSingleton.isSupplier())
      jlfldActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");

    jtfldUserResp.setField("userID");
    jtfldUserResp.setLabel(res.getString("eaTabbedPane.label.user"));
    lbtnUserResp.setText(res.getString("eaTabbedPane.label.user"));
    lbtnUserResp.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    lbtnUserResp.setFillList(new JTextField[] {jtfldUserResp});
    lbtnUserResp.setTitle(res.getString("eaTabbedPane.label.user"));
    if (!MenuSingleton.isSupplier())
      lbtnUserResp.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldUserResp.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUserResp.setDefaultRefField(jtfldUserResp);
    if (!MenuSingleton.isSupplier())
      jlfldUserResp.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");

    jlblState.setText(res.getString("eaTabbedPane.label.state"));
    jcmbState.setHolder(jlblState);
    jcmbState.setDataItems(new Object[] {"", res.getString("eaTabbedPane.state.valid"), res.getString("eaTabbedPane.state.notvalid")});
    jcmbState.setDataValues(new Object[] {null, Boolean.TRUE, Boolean.FALSE});
    jcmbState.setField("state");

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu, 10dlu, left:35dlu, 4dlu, 70dlu, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] {{2, 4, 6, 8, 10, 12}});
    CellConstraints cc = new CellConstraints();

    content.add(jlblEnvAspName, cc.xy(2,2));
    content.add(jtfldEnvAspName, cc.xyw(4,2,3));
    content.add(jlblEnvAspID, cc.xy(8,2));
    content.add(jtfldEnvAspID, cc.xy(10,2));

    content.add(lbtnEnvAspClass, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnvAspClass, cc.xy(4, 4));
    content.add(jlfldEnvAspClass, cc.xyw(6, 4, 5));

    content.add(jlblDescription, cc.xy(2, 6));
    content.add(jspDescription, cc.xywh(4, 6, 7, 3));

    content.add(lbtnActivity, cc.xy(2, 10, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldActivity, cc.xy(4, 10));
    content.add(jlfldActivity, cc.xyw(6, 10, 5));

    content.add(lbtnUserResp, cc.xy(2, 12, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldUserResp, cc.xy(4, 12));
    content.add(jlfldUserResp, cc.xy(6, 12));
    content.add(jlblState, cc.xy(8, 12));
    content.add(jcmbState, cc.xy(10, 12));

    add(content, BorderLayout.CENTER);
  }
}
