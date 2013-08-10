package pt.inescporto.permissions.client.web;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

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
public class MenuItemForm extends TmplAppletForm {
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TmplJTextField jtfldConfig = null;
  TmplLookupButton jbtnConfigLookup = null;
  TmplLookupField configDesc = null;
  TmplJTextField jtfldItem = null;
  TmplLookupButton jbtnItemLookup = null;
  TmplLookupField itemDesc = null;

  public MenuItemForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/MenuItemProxy";
    setPermFormId("MENU_ITEMS");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.code"), getResourceString("label.description")};

    jtfldConfig = new TmplJTextField(getResourceString("label.config"), "menu_config_id");
    jbtnConfigLookup = new TmplLookupButton(getResourceString("label.configList"), urlBase + "/MenuConfigProxy", new String[] {getResourceString("label.code"), getResourceString("label.name")},
					    new TmplJTextField[] {jtfldConfig});
    configDesc = new TmplLookupField(urlBase + "/MenuConfigProxy", new JTextField[] {jtfldConfig});

    jtfldItem = new TmplJTextField(getResourceString("label.menu"), "menu_item_id");
    jbtnItemLookup = new TmplLookupButton(getResourceString("label.menuList"), urlBase + "/MenusProxy", new String[] {getResourceString("label.code"), getResourceString("label.name")},
					  new TmplJTextField[] {jtfldItem});
    itemDesc = new TmplLookupField(urlBase + "/MenusProxy", new JTextField[] {jtfldItem});

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
  }

  private void jbInit() throws Exception {
    jPanel1.setLayout(null);
    jPanel1.setOpaque(false);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);

    jbtnConfigLookup.setText(getResourceString("label.config"));
    jbtnItemLookup.setText(getResourceString("label.menu"));

    jbtnConfigLookup.setBounds(10, 10, 100, 21);
    jtfldConfig.setBounds(120, 10, 100, 21);
    configDesc.setBounds(230, 10, 300, 21);
    jbtnItemLookup.setBounds(10, 40, 100, 21);
    jtfldItem.setBounds(120, 40, 100, 21);
    itemDesc.setBounds(230, 40, 300, 21);

    jPanel1.add(jbtnConfigLookup);
    jPanel1.add(jtfldConfig);
    jPanel1.add(configDesc);
    jPanel1.add(jbtnItemLookup);
    jPanel1.add(jtfldItem);
    jPanel1.add(itemDesc);
  }
}
