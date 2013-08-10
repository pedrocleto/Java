package pt.inescporto.permissions.client.web;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class OperatorHasProfileForm extends TmplAppletForm {
  String[] headers = {getResourceString("label.code"), getResourceString("label.description")};

  JPanel jpnlContent = new JPanel(null);

  // operatores
  TmplJTextField jtfldOperator = null;
  TmplLookupButton jbtnOperatorLookup = null;
  TmplLookupField operatorDesc = null;
  // profiles
  TmplJTextField jtfldProfile = null;
  TmplLookupButton jbtnProfileLookup = null;
  TmplLookupField profileDesc = null;
  // permissions
  TmplJLabel jlblPerm = null;
  TmplJComboBox jcmbPerm = null;

  public OperatorHasProfileForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/OperatorHasProfileProxy";
    setPermFormId("OPERATOR_IN_ROLE");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.operator"), getResourceString("label.role"), getResourceString("label.default")};

    // operatores
    jtfldOperator = new TmplJTextField(getResourceString("label.operators"), "IDOperator");
    jbtnOperatorLookup = new TmplLookupButton(getResourceString("label.operatorList"), urlBase + "/UserProxy", new String[] {getResourceString("label.code"), getResourceString("label.name")},
					      new TmplJTextField[] {jtfldOperator});
    operatorDesc = new TmplLookupField(urlBase + "/UserProxy", new JTextField[] {jtfldOperator});
    // profiles
    jtfldProfile = new TmplJTextField(getResourceString("label.profiles"), "profile_id");
    jbtnProfileLookup = new TmplLookupButton(getResourceString("label.profileList"), urlBase + "/ProfileProxy", headers, new TmplJTextField[] {jtfldProfile});
    profileDesc = new TmplLookupField(urlBase + "/ProfileProxy", new JTextField[] {jtfldProfile});
    // permissions
    jlblPerm = new TmplJLabel();
    jcmbPerm = new TmplJComboBox(jlblPerm, "def_prof", new Object[] {"", getResourceString("label.yes"), getResourceString("label.no")}, new Object[] {"", "Y", "N"});

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

    jbtnOperatorLookup.setText(getResourceString("label.operators"));
    jbtnOperatorLookup.setBounds(new Rectangle(10, 50, 120, 21));
    jtfldOperator.setBounds(new Rectangle(140, 50, 150, 21));
    operatorDesc.setBounds(new Rectangle(300, 50, 300, 21));

    jbtnProfileLookup.setText(getResourceString("label.roles"));
    jbtnProfileLookup.setBounds(new Rectangle(10, 80, 120, 21));
    jtfldProfile.setBounds(new Rectangle(140, 80, 150, 21));
    profileDesc.setBounds(new Rectangle(300, 80, 300, 21));

    jlblPerm.setText(getResourceString("label.default"));
    jlblPerm.setBounds(new Rectangle(10, 110, 120, 21));
    jcmbPerm.setBounds(new Rectangle(140, 110, 150, 21));

    addTemplateListener(jbtnOperatorLookup);
    addTemplateListener(jtfldOperator);
    addTemplateListener(operatorDesc);
    addTemplateListener(jbtnProfileLookup);
    addTemplateListener(jtfldProfile);
    addTemplateListener(profileDesc);
    addTemplateListener(jcmbPerm);

    jpnlContent.add(jbtnOperatorLookup, null);
    jpnlContent.add(jtfldOperator, null);
    jpnlContent.add(operatorDesc, null);
    jpnlContent.add(jbtnProfileLookup, null);
    jpnlContent.add(jtfldProfile, null);
    jpnlContent.add(profileDesc, null);
    jpnlContent.add(jlblPerm, null);
    jpnlContent.add(jcmbPerm, null);
  }
}
