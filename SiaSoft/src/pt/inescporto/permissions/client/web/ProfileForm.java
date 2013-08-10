package pt.inescporto.permissions.client.web;

import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class ProfileForm extends TmplAppletForm {
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

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/ProfileProxy";
    setPermFormId("PROFILES");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.code"), getResourceString("label.description")};

    jlblProfile = new TmplJLabel();
    jtfldProfile = new TmplJTextField(jlblProfile, "profile_id");
    jlblProfileName = new TmplJLabel();
    jtfldProfileName = new TmplJTextField(jlblProfileName, "profile_name");
    jlblLanguage = new TmplJLabel();
    jtfldLanguage = new TmplJTextField(jlblLanguage, "language");
    jlblCurrency = new TmplJLabel();
    jcbCurrency = new TmplJComboBox(jlblCurrency, "currency", new Object[] {"", getResourceString("label.currency.euro"), getResourceString("label.currency.dollar")}, new Object[] {"", "EURO",
				    "DOLLAR"});
    jtfldEnterprise = new TmplJTextField(getResourceString("label.enterprise"), "enterprise");
    jbtnEnterpriseLookup = new TmplLookupButton(getResourceString("label.enterpriseList"), urlBase + "/EnterpriseProxy", new String[] {getResourceString("label.code"),
						getResourceString("label.description")}, new TmplJTextField[] {jtfldEnterprise});
    enterpriseDesc = new TmplLookupField(urlBase + "/EnterpriseProxy", new JTextField[] {jtfldEnterprise});
    jtfldMenu = new TmplJTextField(getResourceString("label.menu"), "menu_config_id");
    jbtnMenuLookup = new TmplLookupButton(getResourceString("label.menuList"), urlBase + "/MenuConfigProxy", new String[] {getResourceString("label.code"), getResourceString("label.description")},
					  new TmplJTextField[] {jtfldMenu});
    menuDesc = new TmplLookupField(urlBase + "/MenuConfigProxy", new JTextField[] {jtfldMenu});

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jpnlContent.setLayout(null);
    jpnlContent.setOpaque(false);
    getContentPane().add(jpnlContent, BorderLayout.CENTER);

    jlblProfile.setText(getResourceString("label.code"));
    jlblProfile.setBounds(new Rectangle(10, 51, 80, 21));
    jtfldProfile.setBounds(new Rectangle(100, 51, 120, 21));
    jlblProfileName.setText(getResourceString("label.description"));
    jlblProfileName.setBounds(new Rectangle(10, 82, 80, 21));
    jtfldProfileName.setBounds(new Rectangle(100, 82, 430, 21));
    jlblLanguage.setText(getResourceString("label.language"));
    jlblLanguage.setBounds(new Rectangle(10, 113, 80, 21));
    jtfldLanguage.setBounds(new Rectangle(100, 113, 120, 21));
    jlblCurrency.setText(getResourceString("label.currency"));
    jlblCurrency.setBounds(new Rectangle(10, 144, 80, 21));
    jcbCurrency.setBounds(new Rectangle(100, 144, 120, 21));
    jbtnEnterpriseLookup.setText(getResourceString("label.enterprise"));
    jbtnEnterpriseLookup.setBounds(10, 175, 80, 21);
    jtfldEnterprise.setBounds(100, 175, 120, 21);
    enterpriseDesc.setBounds(230, 175, 300, 21);
    jbtnMenuLookup.setText(getResourceString("label.menu"));
    jbtnMenuLookup.setBounds(10, 206, 80, 21);
    jtfldMenu.setBounds(100, 206, 120, 21);
    menuDesc.setBounds(230, 206, 300, 21);

    jpnlContent.add(jlblProfile, null);
    jpnlContent.add(jtfldProfile, null);
    jpnlContent.add(jlblProfileName, null);
    jpnlContent.add(jtfldProfileName, null);
    jpnlContent.add(jlblLanguage, null);
    jpnlContent.add(jtfldLanguage, null);
    jpnlContent.add(jlblCurrency, null);
    jpnlContent.add(jcbCurrency, null);
    jpnlContent.add(jbtnEnterpriseLookup, null);
    jpnlContent.add(jtfldEnterprise, null);
    jpnlContent.add(enterpriseDesc, null);
    jpnlContent.add(jbtnMenuLookup, null);
    jpnlContent.add(jtfldMenu, null);
    jpnlContent.add(menuDesc, null);

    addTemplateListener(jtfldProfile);
    addTemplateListener(jtfldProfileName);
    addTemplateListener(jtfldLanguage);
    addTemplateListener(jcbCurrency);
    addTemplateListener(jbtnEnterpriseLookup);
    addTemplateListener(jtfldEnterprise);
    addTemplateListener(enterpriseDesc);
    addTemplateListener(jbtnMenuLookup);
    addTemplateListener(jtfldMenu);
    addTemplateListener(menuDesc);
  }
}
