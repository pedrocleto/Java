package pt.inescporto.permissions.client.rmi;

import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.TmplInternalFrame;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.Color;

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
public class RoleForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel(null);

  TmplJLabel jlblRole = null;
  TmplJTextField jtfldRole = null;
  TmplJLabel jlblRoleName = null;
  TmplJTextField jtfldRoleName = null;

  public RoleForm() {
    super();

    url = "pt.inescporto.permissions.ejb.session.GlbRole";
//    setPermFormId("GR_PERM");

    init();
    start();
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {res.getString("label.code"), res.getString("label.description")};

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

    jlblRole.setText(res.getString("label.code"));
    jlblRole.setBounds(new Rectangle(25, 51, 120, 17));
    jtfldRole.setBounds(new Rectangle(172, 49, 200, 21));
    jlblRoleName.setText(res.getString("label.description"));
    jlblRoleName.setBounds(new Rectangle(25, 82, 120, 17));
    jtfldRoleName.setBounds(new Rectangle(172, 81, 400, 21));

    addTemplateListener(jtfldRole);
    addTemplateListener(jtfldRoleName);

    addTemplateComponentListener(jtfldRole);
    addTemplateComponentListener(jtfldRoleName);

    jpnlContent.add(jlblRole, null);
    jpnlContent.add(jtfldRole, null);
    jpnlContent.add(jlblRoleName, null);
    jpnlContent.add(jtfldRoleName, null);
  }
}
