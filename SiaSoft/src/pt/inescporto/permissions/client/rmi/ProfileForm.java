package pt.inescporto.permissions.client.rmi;

import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.Color;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JTextField;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class ProfileForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel(null);

  TmplJLabel jlblProfile = null;
  TmplJTextField jtfldProfile = null;
  TmplJLabel jlblProfileName = null;
  TmplJTextField jtfldProfileName = null;
  TmplJLabel jlblLanguage = null;
  TmplJTextField jtfldLanguage = null;
  TmplJLabel jlblCurrency = null;
  TmplJComboBox jcbCurrency = null;
  TmplJTextField jtfldEnterprise = null;
  TmplLookupButton jbtnEnterpriseLookup = null;
  TmplLookupField enterpriseDesc = null;
  TmplJTextField jtfldMenu = null;
  TmplLookupButton jbtnMenuLookup = null;
  TmplLookupField menuDesc = null;

  public ProfileForm() {
    super();

    DataSourceRMI test = new DataSourceRMI("Profile");
    test.setUrl("pt.inescporto.permissions.ejb.session.GlbProfile");
    setDataSource(test);


    this.allHeaders = new String[] {res.getString("label.code"), res.getString("label.description")};

    setAccessPermitionIdentifier("PROFILES");

    init();
    start();
  }

  public void init() {
    jlblProfile = new TmplJLabel();
    jtfldProfile = new TmplJTextField(jlblProfile, "profile_id");
    jlblProfileName = new TmplJLabel();
    jtfldProfileName = new TmplJTextField(jlblProfileName, "profile_name");
    jlblLanguage = new TmplJLabel();
    jtfldLanguage = new TmplJTextField(jlblLanguage, "language");
    jlblCurrency = new TmplJLabel();
    jcbCurrency = new TmplJComboBox(jlblCurrency, "currency", new Object[] {"", res.getString("label.currency.euro"), res.getString("label.currency.dollar")}, new Object[] {"", "EURO","DOLLAR"});
    jtfldEnterprise = new TmplJTextField(res.getString("label.enterprise"), "enterprise");
    jbtnEnterpriseLookup = new TmplLookupButton(res.getString("label.enterpriseList"), "pt.inescporto.siasoft.comun.ejb.session.Enterprise", new String[] {res.getString("label.code"), res.getString("label.description")}, new TmplJTextField[] {jtfldEnterprise});
    enterpriseDesc = new TmplLookupField("pt.inescporto.siasoft.comun.ejb.session.Enterprise", new JTextField[] {jtfldEnterprise});
    jtfldMenu = new TmplJTextField(res.getString("label.menu"), "menu_config_id");
    jbtnMenuLookup = new TmplLookupButton(res.getString("label.menuList"), "pt.inescporto.permissions.ejb.session.GlbMenuConfig", new String[] {res.getString("label.code"), res.getString("label.description")}, new TmplJTextField[] {jtfldMenu});
    menuDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbMenuConfig", new JTextField[] {jtfldMenu});

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setLayout(new GridBagLayout());
    jpnlContent.setOpaque(false);
    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlDummy, BorderLayout.CENTER);

    jlblProfile.setText(res.getString("label.code"));
    jlblProfileName.setText(res.getString("label.description"));
    jlblLanguage.setText(res.getString("label.language"));
    jlblCurrency.setText(res.getString("label.currency"));
    jbtnEnterpriseLookup.setText(res.getString("label.enterprise"));
    jbtnMenuLookup.setText(res.getString("label.menu"));

    // add visual components
    jpnlContent.add(jlblProfile, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldProfile, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblProfileName, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldProfileName, new GridBagConstraints(1, 1, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblLanguage, new GridBagConstraints(0, 2, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldLanguage, new GridBagConstraints(1, 2, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblCurrency, new GridBagConstraints(0, 3, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jcbCurrency, new GridBagConstraints(1, 3, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnEnterpriseLookup, new GridBagConstraints(0, 4, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldEnterprise, new GridBagConstraints(1, 4, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(enterpriseDesc, new GridBagConstraints(2, 4, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnMenuLookup, new GridBagConstraints(0, 5, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldMenu, new GridBagConstraints(1, 5, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(menuDesc, new GridBagConstraints(2, 5, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    dataSource.addDatasourceListener(jtfldProfile);
    dataSource.addDatasourceListener(jtfldProfileName);
    dataSource.addDatasourceListener(jtfldLanguage);
    dataSource.addDatasourceListener(jcbCurrency);
    dataSource.addDatasourceListener(jbtnEnterpriseLookup);
    dataSource.addDatasourceListener(jtfldEnterprise);
    dataSource.addDatasourceListener(enterpriseDesc);
    dataSource.addDatasourceListener(jbtnMenuLookup);
    dataSource.addDatasourceListener(jtfldMenu);
    dataSource.addDatasourceListener(menuDesc);

    addFWComponentListener(jtfldProfile);
    addFWComponentListener(jtfldProfileName);
    addFWComponentListener(jtfldLanguage);
    addFWComponentListener(jcbCurrency);
    addFWComponentListener(jbtnEnterpriseLookup);
    addFWComponentListener(jtfldEnterprise);
    addFWComponentListener(enterpriseDesc);
    addFWComponentListener(jbtnMenuLookup);
    addFWComponentListener(jtfldMenu);
    addFWComponentListener(menuDesc);
  }
}
