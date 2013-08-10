package pt.inescporto.permissions.client.rmi;

import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.TmplInternalFrame;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJComboBox;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridBagLayout;

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
public class OperatorIsInRoleForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  String[] headers = {res.getString("label.code"), res.getString("label.description")};

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

    url = "pt.inescporto.permissions.ejb.session.GlbOperatorIsInRole";
    setPermFormId("OPERATOR_IN_ROLE");

    init();
    start();
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {res.getString("label.operator"), res.getString("label.role"), res.getString("label.default")};

    // operatores
    jtfldOperator = new TmplJTextField(res.getString("label.operators"), "IDOperator");
    jbtnOperatorLookup = new TmplLookupButton(res.getString("label.operatorList"), "pt.inescporto.siasoft.comun.ejb.session.User", new String[] {res.getString("label.code"), res.getString("label.name")},
                                              new TmplJTextField[] {jtfldOperator});
    operatorDesc = new TmplLookupField("pt.inescporto.siasoft.comun.ejb.session.User", new JTextField[] {jtfldOperator});
    // roles
    jtfldRole = new TmplJTextField(res.getString("label.roles"), "role_id");
    jbtnRoleLookup = new TmplLookupButton(res.getString("label.roleList"), "pt.inescporto.permissions.ejb.session.GlbRole", headers, new TmplJTextField[] {jtfldRole});
    roleDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbRole", new JTextField[] {jtfldRole});
    // permissions
    jlblPerm = new TmplJLabel();
    jcmbPerm = new TmplJComboBox(jlblPerm, "def_perm", new Object[] {"", res.getString("label.yes"), res.getString("label.no")}, new Object[] {"", "Y", "N"});

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

    jbtnOperatorLookup.setText(res.getString("label.operator"));
    jbtnRoleLookup.setText(res.getString("label.roles"));
    jlblPerm.setText(res.getString("label.default"));

    // add visual components
    jpnlContent.add(jbtnOperatorLookup, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldOperator, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(operatorDesc, new GridBagConstraints(2, 0, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnRoleLookup, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldRole, new GridBagConstraints(1, 1, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(roleDesc, new GridBagConstraints(2, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblPerm, new GridBagConstraints(0, 2, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jcmbPerm, new GridBagConstraints(1, 2, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    addTemplateListener(jtfldOperator);
    addTemplateListener(jbtnOperatorLookup);
    addTemplateListener(operatorDesc);
    addTemplateListener(jbtnRoleLookup);
    addTemplateListener(jtfldRole);
    addTemplateListener(roleDesc);
    addTemplateListener(jcmbPerm);

    addTemplateComponentListener(jtfldOperator);
    addTemplateComponentListener(jbtnOperatorLookup);
    addTemplateComponentListener(operatorDesc);
    addTemplateComponentListener(jbtnRoleLookup);
    addTemplateComponentListener(jtfldRole);
    addTemplateComponentListener(roleDesc);
    addTemplateComponentListener(jcmbPerm);
  }
}
