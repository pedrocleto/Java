package pt.inescporto.permissions.client.web;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class OperatorIsInRoleForm extends TmplAppletForm {
  String[] headers = {getResourceString("label.code"), getResourceString("label.description")};

  JPanel jpnlContent = new JPanel(null);

  // operatores
  TmplJTextField jtfldOperator = null;
  TmplLookupButton jbtnOperatorLookup = null;
  TmplLookupField operatorDesc = null;
  // roles
  TmplJTextField jtfldRole = null;
  TmplLookupButton jbtnRoleLookup = null;
  TmplLookupField roleDesc = null;
  // permissions
  TmplJLabel jlblPerm = null;
  TmplJComboBox jcmbPerm = null;

  public OperatorIsInRoleForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/OperatorIsInRoleProxy";
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
    // roles
    jtfldRole = new TmplJTextField(getResourceString("label.roles"), "role_id");
    jbtnRoleLookup = new TmplLookupButton(getResourceString("label.roleList"), urlBase + "/RoleProxy", headers, new TmplJTextField[] {jtfldRole});
    roleDesc = new TmplLookupField(urlBase + "/RoleProxy", new JTextField[] {jtfldRole});
    // permissions
    jlblPerm = new TmplJLabel();
    jcmbPerm = new TmplJComboBox(jlblPerm, "def_perm", new Object[] {"", getResourceString("label.yes"), getResourceString("label.no")}, new Object[] {"", "Y", "N"});

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

    jbtnOperatorLookup.setText(getResourceString("label.operator"));
    jbtnOperatorLookup.setBounds(new Rectangle(10, 50, 120, 21));
    jtfldOperator.setBounds(new Rectangle(140, 50, 150, 21));
    operatorDesc.setBounds(new Rectangle(300, 50, 300, 21));

    jbtnRoleLookup.setText(getResourceString("label.roles"));
    jbtnRoleLookup.setBounds(new Rectangle(10, 80, 120, 21));
    jtfldRole.setBounds(new Rectangle(140, 80, 150, 21));
    roleDesc.setBounds(new Rectangle(300, 80, 300, 21));

    jlblPerm.setText(getResourceString("label.default"));
    jlblPerm.setBounds(new Rectangle(10, 110, 120, 21));
    jcmbPerm.setBounds(new Rectangle(140, 110, 150, 21));

    addTemplateListener(jtfldOperator);
    addTemplateListener(jbtnOperatorLookup);
    addTemplateListener(operatorDesc);
    addTemplateListener(jbtnRoleLookup);
    addTemplateListener(jtfldRole);
    addTemplateListener(roleDesc);
    addTemplateListener(jcmbPerm);

    jpnlContent.add(jbtnOperatorLookup, null);
    jpnlContent.add(jtfldOperator, null);
    jpnlContent.add(operatorDesc, null);
    jpnlContent.add(jbtnRoleLookup, null);
    jpnlContent.add(jtfldRole, null);
    jpnlContent.add(roleDesc, null);
    jpnlContent.add(jlblPerm, null);
    jpnlContent.add(jcmbPerm, null);
  }
}
