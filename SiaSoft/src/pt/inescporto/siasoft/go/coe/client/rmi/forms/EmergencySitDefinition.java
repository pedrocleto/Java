package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.rmi.MenuSingleton;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJRadioButton;
import pt.inescporto.template.client.design.TmplButtonGroup;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

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

public class EmergencySitDefinition extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.coe.client.rmi.forms.FormResources");

  JTabbedPane jtpScenarios = new JTabbedPane();

  TmplJLabel jlblEmergencySitDefID = new TmplJLabel();
  TmplJTextField jtfldEmergencySitDefID = new TmplJTextField();

  TmplJLabel jlblEmergencySitDescription = new TmplJLabel();
  TmplJTextField jtfldEmergencySitDescription = new TmplJTextField();

  TmplLookupButton jlbtnEnvAsp = new TmplLookupButton();
  TmplJTextField jtfldEnvAsp = new TmplJTextField();
  TmplLookupField jlfldEnvAsp = new TmplLookupField();

  TmplLookupButton jlbtnEmergSUserResp = new TmplLookupButton();
  TmplJTextField jtfldEmergSUserRespID = new TmplJTextField();
  TmplLookupField jlfldEmergSUserRespDescription = new TmplLookupField();

  TmplLookupButton jlbtnActivity = new TmplLookupButton();
  TmplJTextField jtfldActivity = new TmplJTextField();
  TmplLookupField jlfldActivity = new TmplLookupField();

  TmplJLabel jlblNormal = new TmplJLabel();
  TmplJLabel jlblAnormal = new TmplJLabel();
  TmplJLabel jlblEmergency = new TmplJLabel();

  TmplJRadioButton jrbNormal = new TmplJRadioButton();
  TmplJRadioButton jrbAnormal = new TmplJRadioButton();
  TmplJRadioButton jrbEmergency = new TmplJRadioButton();
  TmplButtonGroup bgType = new TmplButtonGroup();

  public EmergencySitDefinition(DataSource datasource, FW_ComponentListener fwCListener, JTabbedPane jtpScenarios) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.jtpScenarios = jtpScenarios;
    initialize();

    datasource.addDatasourceListener(jtfldEmergencySitDefID);
    datasource.addDatasourceListener(jtfldEmergencySitDescription);
    datasource.addDatasourceListener(jlbtnEnvAsp);
    datasource.addDatasourceListener(jtfldEnvAsp);
    datasource.addDatasourceListener(jlfldEnvAsp);
    datasource.addDatasourceListener(jlbtnEmergSUserResp);
    datasource.addDatasourceListener(jtfldEmergSUserRespID);
    datasource.addDatasourceListener(jlfldEmergSUserRespDescription);
    datasource.addDatasourceListener(bgType);
    datasource.addDatasourceListener(jlbtnActivity);
    datasource.addDatasourceListener(jtfldActivity);
    datasource.addDatasourceListener(jlfldActivity);

    fwCListener.addFWComponentListener(jtfldEmergencySitDefID);
    fwCListener.addFWComponentListener(jtfldEmergencySitDescription);
    fwCListener.addFWComponentListener(jlbtnEnvAsp);
    fwCListener.addFWComponentListener(jtfldEnvAsp);
    fwCListener.addFWComponentListener(jlfldEnvAsp);
    fwCListener.addFWComponentListener(jlbtnEmergSUserResp);
    fwCListener.addFWComponentListener(jtfldEmergSUserRespID);
    fwCListener.addFWComponentListener(jlfldEmergSUserRespDescription);
    fwCListener.addFWComponentListener(bgType);
    fwCListener.addFWComponentListener(jlbtnActivity);
    fwCListener.addFWComponentListener(jtfldActivity);
    fwCListener.addFWComponentListener(jlfldActivity);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblEmergencySitDefID.setText(res.getString("emergSitdef.label.code"));
    jtfldEmergencySitDefID.setField("emergSitID");
    jtfldEmergencySitDefID.setHolder(jlblEmergencySitDefID);

    jlblEmergencySitDescription.setText(res.getString("emergSitdef.label.description"));
    jtfldEmergencySitDescription.setField("emergSitDescription");
    jtfldEmergencySitDescription.setHolder(jlblEmergencySitDescription);

    jtfldEnvAsp.setField("fkEnvAspID");
    jtfldEnvAsp.setLabel(res.getString("emergSitlink.label.aa"));

    jlbtnEnvAsp.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlbtnEnvAsp.setText(res.getString("emergSitlink.label.aa"));
    jlbtnEnvAsp.setTitle(res.getString("emergSitlink.label.aalist"));
    jlbtnEnvAsp.setDefaultFill(jtfldEnvAsp);

    jlfldEnvAsp.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlfldEnvAsp.setDefaultRefField(jtfldEnvAsp);

    jtfldEmergSUserRespID.setField("fkUserID");
    jtfldEmergSUserRespID.setText(res.getString("emergSitdef.buttonlabel.resp"));

    jlbtnEmergSUserResp.setText(res.getString("emergSitdef.buttonlabel.resp"));
    jlbtnEmergSUserResp.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnEmergSUserResp.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEmergSUserResp.setTitle(res.getString("emergSitdef.label.resplist"));
    jlbtnEmergSUserResp.setDefaultFill(jtfldEmergSUserRespID);

    jlfldEmergSUserRespDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldEmergSUserRespDescription.setDefaultRefField(jtfldEmergSUserRespID);

    jtfldActivity.setLabel(res.getString("emergSitlink.label.activity"));
    jtfldActivity.setField("fkActivityID");
    if (!MenuSingleton.isSupplier())
      jlbtnActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlbtnActivity.setText(res.getString("emergSitlink.label.activity"));
    jlbtnActivity.setTitle(res.getString("emergSitlink.label.activitylist"));
    jlbtnActivity.setDefaultFill(jtfldActivity);
    jlfldActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlfldActivity.setDefaultRefField(jtfldActivity);

    jlblNormal.setText(res.getString("emergSitdef.label.normal"));
    jlblAnormal.setText(res.getString("emergSitdef.label.abnormal"));
    jlblEmergency.setText(res.getString("emergSitdef.label.emergency"));

    jrbNormal.setValue("N");
    jrbAnormal.setValue("A");
    jrbEmergency.setValue("E");

    bgType.setField("typeEmerg");
    bgType.add(jrbNormal);
    bgType.add(jrbAnormal);
    bgType.add(jrbEmergency);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 50dlu, 4dlu, 50dlu, 4dlu, 50dlu, 4dlu, 50dlu, 4dlu, 50dlu, 4dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    FormLayout formLayout2 = new FormLayout("65dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 65dlu",
					    "5px, pref, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    JPanel typeContent = new JPanel();
    typeContent.setOpaque(false);
    typeContent.setLayout(formLayout2);

    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8}
    });

    CellConstraints cc = new CellConstraints();

    typeContent.setBorder(BorderFactory.createTitledBorder(res.getString("emergSitdef.label.type")));
    ((TitledBorder)typeContent.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    typeContent.add(jlblNormal, cc.xy(2, 2));
    typeContent.add(jrbNormal, cc.xy(4, 2));
    typeContent.add(jlblAnormal, cc.xy(6, 2));
    typeContent.add(jrbAnormal, cc.xy(8, 2));
    typeContent.add(jlblEmergency, cc.xy(10, 2));
    typeContent.add(jrbEmergency, cc.xy(12, 2));

    content.add(jlblEmergencySitDefID, cc.xy(2, 2));
    content.add(jtfldEmergencySitDefID, cc.xy(4, 2));

    content.add(jlblEmergencySitDescription, cc.xy(2, 4));
    content.add(jtfldEmergencySitDescription, cc.xyw(4, 4, 10));

    content.add(jlbtnEnvAsp, cc.xy(2, 6, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnvAsp, cc.xy(4, 6));
    content.add(jlfldEnvAsp, cc.xyw(6, 6, 8));

    content.add(jlbtnEmergSUserResp, cc.xy(2, 8, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEmergSUserRespID, cc.xy(4, 8));
    content.add(jlfldEmergSUserRespDescription, cc.xyw(6, 8, 8));

    content.add(jlbtnActivity, cc.xy(2, 10, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldActivity, cc.xy(4, 10));
    content.add(jlfldActivity, cc.xyw(6, 10, 8));

    jrbEmergency.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJRadioButton)e.getSource()).isSelected()) {
	  jtpScenarios.setEnabledAt(1, true);
	}
      }
    });
    jrbNormal.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJRadioButton)e.getSource()).isSelected()) {
	  jtpScenarios.setEnabledAt(1, false);
	}
      }
    });
    jrbAnormal.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	if (((TmplJRadioButton)e.getSource()).isSelected()) {
	  jtpScenarios.setEnabledAt(1, false);
	}
      }
    });

    content.add(typeContent, cc.xyw(2, 12, 12));
    add(content, BorderLayout.NORTH);
    add(new EmergencySituationScenarios(datasource, fwCListener, jtpScenarios), BorderLayout.CENTER);
  }
}
