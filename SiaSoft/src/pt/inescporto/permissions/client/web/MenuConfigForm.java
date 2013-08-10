package pt.inescporto.permissions.client.web;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.web.*;
import java.awt.Color;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author jap
 * @version 1.0
 */
public class MenuConfigForm extends TmplAppletForm {
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TmplJLabel jlblMenuConfig = null;
  TmplJLabel jlblMenuConfigName = null;
  TmplJTextField jtfldMenuConfig = null;
  TmplJTextField jtfldMenuConfigName = null;

  public MenuConfigForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/MenuConfigProxy";
    setPermFormId("MENU_CONF");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.code"), getResourceString("label.description")};

    jlblMenuConfig = new TmplJLabel();
    jtfldMenuConfig = new TmplJTextField(jlblMenuConfig, "menu_config_id");
    jlblMenuConfigName = new TmplJLabel();
    jtfldMenuConfigName = new TmplJTextField(jlblMenuConfigName, "menu_config_descr");

    jlblMenuConfig.setBounds(10, 10, 80, 21);
    jtfldMenuConfig.setBounds(90, 10, 220, 21);
    jlblMenuConfigName.setBounds(10, 40, 80, 21);
    jtfldMenuConfigName.setBounds(90, 40, 220, 21);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jPanel1.setLayout(null);
    jPanel1.setOpaque(false);
    jlblMenuConfig.setText(getResourceString("label.code"));
    jlblMenuConfigName.setText(getResourceString("label.description"));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jlblMenuConfig);
    jPanel1.add(jlblMenuConfigName);
    jPanel1.add(jtfldMenuConfigName);
    jPanel1.add(jtfldMenuConfig);

    addTemplateListener(jtfldMenuConfig);
    addTemplateListener(jtfldMenuConfigName);
  }
}
