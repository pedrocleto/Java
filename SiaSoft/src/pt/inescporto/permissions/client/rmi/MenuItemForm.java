package pt.inescporto.permissions.client.rmi;

import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.TmplInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import pt.inescporto.template.client.design.TmplLookupButton;
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
public class MenuItemForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel();

  TmplJTextField jtfldConfig = null;
  TmplLookupButton jbtnConfigLookup = null;
  TmplLookupField configDesc = null;
  TmplJTextField jtfldItem = null;
  TmplLookupButton jbtnItemLookup = null;
  TmplLookupField itemDesc = null;

  public MenuItemForm() {
    super();

    url = "pt.inescporto.permissions.ejb.session.GlbMenuItem";
    setPermFormId("MENU_ITEMS");

    init();
    start();
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {res.getString("label.code"), res.getString("label.description")};

    jtfldConfig = new TmplJTextField(res.getString("label.config"), "menu_config_id");
    jbtnConfigLookup = new TmplLookupButton(res.getString("label.configList"), "pt.inescporto.permissions.ejb.session.GlbMenuConfig", new String[] {res.getString("label.code"), res.getString("label.name")},
                                            new TmplJTextField[] {jtfldConfig});
    configDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbMenuConfig", new JTextField[] {jtfldConfig});

    jtfldItem = new TmplJTextField(res.getString("label.menu"), "menu_item_id");
    jbtnItemLookup = new TmplLookupButton(res.getString("label.menuList"), "pt.inescporto.permissions.ejb.session.GlbMenu", new String[] {res.getString("label.code"), res.getString("label.name")},
                                          new TmplJTextField[] {jtfldItem});
    itemDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbMenu", new JTextField[] {jtfldItem});

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    addTemplateListener(jbtnConfigLookup);
    addTemplateListener(jtfldConfig);
    addTemplateListener(configDesc);
    addTemplateListener(jbtnItemLookup);
    addTemplateListener(jtfldItem);
    addTemplateListener(itemDesc);

    addTemplateComponentListener(jbtnConfigLookup);
    addTemplateComponentListener(jtfldConfig);
    addTemplateComponentListener(configDesc);
    addTemplateComponentListener(jbtnItemLookup);
    addTemplateComponentListener(jtfldItem);
    addTemplateComponentListener(itemDesc);
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setLayout(new GridBagLayout());
    jpnlContent.setOpaque(false);
    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlDummy, BorderLayout.CENTER);

    jbtnConfigLookup.setText(res.getString("label.config"));
    jbtnItemLookup.setText(res.getString("label.menu"));

    // add visual components
    jpnlContent.add(jbtnConfigLookup, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldConfig, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(configDesc, new GridBagConstraints(2, 0, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnItemLookup, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldItem, new GridBagConstraints(1, 1, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(itemDesc, new GridBagConstraints(2, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}
