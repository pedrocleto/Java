package pt.inescporto.permissions.client.rmi;

import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.TmplInternalFrame;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.Color;
import java.awt.GridBagLayout;
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
public class MenuForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel();

  // my components
  TmplJLabel jlblMenu = null;
  TmplJTextField jtfldMenu = null;
  TmplJLabel jlblMenuName = null;
  TmplJTextField jtfldMenuName = null;
  TmplJLabel jlblLocale = null;
  TmplJTextField jtfldLocale = null;
  TmplJLabel jlblIndex = null;
  TmplJTextField jtfldIndex = null;
  TmplJTextField jtfldFather = null;
  TmplLookupButton jbtnFatherLookup = null;
  TmplLookupField fatherDesc = null;
  TmplJTextField jtfldForm = null;
  TmplLookupButton jbtnFormLookup = null;
  TmplLookupField formDesc = null;

  public MenuForm() {
    super();

    url = "pt.inescporto.permissions.ejb.session.GlbMenu";
    setPermFormId("MENU_MENU");

    init();
    start();
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {
        res.getString("label.code"),
        res.getString("label.description"), res.getString("label.locale"),
        res.getString("label.index"), res.getString("label.father"),
        res.getString("label.form")};

    jlblMenu = new TmplJLabel();
    jtfldMenu = new TmplJTextField(jlblMenu, "menu_item_id");
    jlblMenuName = new TmplJLabel();
    jtfldMenuName = new TmplJTextField(jlblMenuName, "menu_descr");
    jlblLocale = new TmplJLabel();
    jtfldLocale = new TmplJTextField(jlblLocale, "menu_loc_ref");
    jlblIndex = new TmplJLabel();
    jtfldIndex = new TmplJTextField(jlblIndex, "menu_index");
    jtfldFather = new TmplJTextField(res.getString("label.menu"), "father_id");
    jbtnFatherLookup = new TmplLookupButton(res.getString("label.menuList"), "pt.inescporto.permissions.ejb.session.GlbMenu", new String[] {res.getString("label.code"), res.getString("label.name")},
                                            new TmplJTextField[] {jtfldFather});
    fatherDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbMenu", new JTextField[] {jtfldFather});
    jtfldForm = new TmplJTextField(res.getString("label.form"), "fk_form_id");
    jbtnFormLookup = new TmplLookupButton(res.getString("label.formList"), "pt.inescporto.permissions.ejb.session.GlbForm", new String[] {res.getString("label.code"), res.getString("label.name")},
                                          new TmplJTextField[] {jtfldForm});
    formDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbForm", new JTextField[] {jtfldForm});

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // register this listeners
    addTemplateListener(jtfldMenu);
    addTemplateListener(jtfldMenuName);
    addTemplateListener(jtfldLocale);
    addTemplateListener(jtfldIndex);
    addTemplateListener(jbtnFatherLookup);
    addTemplateListener(jtfldFather);
    addTemplateListener(fatherDesc);
    addTemplateListener(jbtnFormLookup);
    addTemplateListener(jtfldForm);
    addTemplateListener(formDesc);

    addTemplateComponentListener(jtfldMenu);
    addTemplateComponentListener(jtfldMenuName);
    addTemplateComponentListener(jtfldLocale);
    addTemplateComponentListener(jtfldIndex);
    addTemplateComponentListener(jbtnFatherLookup);
    addTemplateComponentListener(jtfldFather);
    addTemplateComponentListener(fatherDesc);
    addTemplateComponentListener(jbtnFormLookup);
    addTemplateComponentListener(jtfldForm);
    addTemplateComponentListener(formDesc);
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setLayout(new GridBagLayout());
    jpnlContent.setOpaque(false);
    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlDummy, BorderLayout.CENTER);

    jlblMenu.setText(res.getString("label.code"));
    jlblMenuName.setText(res.getString("label.description"));
    jlblLocale.setText(res.getString("label.locale"));
    jlblIndex.setText(res.getString("label.index"));
    jbtnFatherLookup.setText(res.getString("label.father"));
    jbtnFormLookup.setText(res.getString("label.form"));

    // add visual components
    jpnlContent.add(jlblMenu, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldMenu, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblMenuName, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldMenuName, new GridBagConstraints(1, 1, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblLocale, new GridBagConstraints(0, 2, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldLocale, new GridBagConstraints(1, 2, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblIndex, new GridBagConstraints(0, 3, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldIndex, new GridBagConstraints(1, 3, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnFatherLookup, new GridBagConstraints(0, 4, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldFather, new GridBagConstraints(1, 4, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(fatherDesc, new GridBagConstraints(2, 4, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnFormLookup, new GridBagConstraints(0, 5, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldForm, new GridBagConstraints(1, 5, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(formDesc, new GridBagConstraints(2, 5, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}
