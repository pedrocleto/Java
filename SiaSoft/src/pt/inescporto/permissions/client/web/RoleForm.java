package pt.inescporto.permissions.client.web;

import java.awt.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;
import javax.swing.JPanel;

public class RoleForm extends TmplAppletForm {
  JPanel jpnlContent = new JPanel(null);

  TmplJLabel jlblRole = null;
  TmplJTextField jtfldRole = null;
  TmplJLabel jlblRoleName = null;
  TmplJTextField jtfldRoleName = null;

  public RoleForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/RoleProxy";
    setPermFormId("WEB_GR_PERM");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.code"), getResourceString("label.description")};

    jlblRole = new TmplJLabel();
    jtfldRole = new TmplJTextField(jlblRole, "role_id");
    jlblRoleName = new TmplJLabel();
    jtfldRoleName = new TmplJTextField(jlblRoleName, "role_name");

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

    jlblRole.setText(getResourceString("label.code"));
    jlblRole.setBounds(new Rectangle(25, 51, 120, 17));
    jtfldRole.setBounds(new Rectangle(172, 49, 200, 21));
    jlblRoleName.setText(getResourceString("label.description"));
    jlblRoleName.setBounds(new Rectangle(25, 82, 120, 17));
    jtfldRoleName.setBounds(new Rectangle(172, 81, 400, 21));

    addTemplateListener(jtfldRole);
    addTemplateListener(jtfldRoleName);

    jpnlContent.add(jlblRole, null);
    jpnlContent.add(jtfldRole, null);
    jpnlContent.add(jlblRoleName, null);
    jpnlContent.add(jtfldRoleName, null);
  }
}
