package pt.inescporto.permissions.client.rmi;

import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.TmplInternalFrame;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.Color;
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
public class MenuConfigForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel(null);

  TmplJLabel jlblMenuConfig = null;
  TmplJLabel jlblMenuConfigName = null;
  TmplJTextField jtfldMenuConfig = null;
  TmplJTextField jtfldMenuConfigName = null;

  public MenuConfigForm() {
    super();

    url = "pt.inescporto.permissions.ejb.session.GlbMenuConfig";
    setPermFormId("MENU_CONF");

    init();
    start();
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {res.getString("label.code"), res.getString("label.description")};

    jlblMenuConfig = new TmplJLabel();
    jtfldMenuConfig = new TmplJTextField(jlblMenuConfig, "menu_config_id");
    jlblMenuConfigName = new TmplJLabel();
    jtfldMenuConfigName = new TmplJTextField(jlblMenuConfigName, "menu_config_descr");

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

    jlblMenuConfig.setText(res.getString("label.code"));
    jlblMenuConfigName.setText(res.getString("label.description"));

    // add visual components
    jpnlContent.add(jlblMenuConfig, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldMenuConfig, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblMenuConfigName, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldMenuConfigName, new GridBagConstraints(1, 1, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    addTemplateListener(jtfldMenuConfig);
    addTemplateListener(jtfldMenuConfigName);

    addTemplateComponentListener(jtfldMenuConfig);
    addTemplateComponentListener(jtfldMenuConfigName);
  }
}
