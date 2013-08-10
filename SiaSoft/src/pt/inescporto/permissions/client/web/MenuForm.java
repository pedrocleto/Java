package pt.inescporto.permissions.client.web;

import pt.inescporto.template.client.web.TmplAppletForm;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
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
public class MenuForm extends TmplAppletForm {
  JPanel jPanel1 = new JPanel();

  // template report button
//  TmplJButtonReport jbtnReport = null;

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

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/MenusProxy";
    setPermFormId("MENU_MENU");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {
	getResourceString("label.code"),
	getResourceString("label.description"), getResourceString("label.locale"),
	getResourceString("label.index"), getResourceString("label.father"),
	getResourceString("label.form")};

    jlblMenu = new TmplJLabel();
    jtfldMenu = new TmplJTextField(jlblMenu, "menu_item_id");
    jlblMenuName = new TmplJLabel();
    jtfldMenuName = new TmplJTextField(jlblMenuName, "menu_descr");
    jlblLocale = new TmplJLabel();
    jtfldLocale = new TmplJTextField(jlblLocale, "menu_loc_ref");
    jlblIndex = new TmplJLabel();
    jtfldIndex = new TmplJTextField(jlblIndex, "menu_index");
    jtfldFather = new TmplJTextField(getResourceString("label.menu"), "father_id");
    jbtnFatherLookup = new TmplLookupButton(getResourceString("label.menuList"), urlBase + "/MenusProxy", new String[] {getResourceString("label.code"), getResourceString("label.name")},
					    new TmplJTextField[] {jtfldFather});
    fatherDesc = new TmplLookupField(urlBase + "/MenusProxy", new JTextField[] {jtfldFather});
    jtfldForm = new TmplJTextField(getResourceString("label.form"), "fk_form_id");
    jbtnFormLookup = new TmplLookupButton(getResourceString("label.formList"), urlBase + "/FormProxy", new String[] {getResourceString("label.code"), getResourceString("label.name")},
					  new TmplJTextField[] {jtfldForm});
    formDesc = new TmplLookupField(urlBase + "/FormProxy", new JTextField[] {jtfldForm});

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
  }

  private void jbInit() throws Exception {
    jlblMenu.setText(getResourceString("label.code"));
    jlblMenuName.setText(getResourceString("label.description"));
    jlblLocale.setText(getResourceString("label.locale"));
    jlblIndex.setText(getResourceString("label.index"));
    jbtnFatherLookup.setText(getResourceString("label.father"));
    jbtnFormLookup.setText(getResourceString("label.form"));

    jPanel1.setLayout(null);
    jPanel1.setOpaque(false);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);

    jlblMenu.setBounds(10, 10, 100, 21);
    jtfldMenu.setBounds(120, 10, 100, 21);
    jlblMenuName.setBounds(10, 40, 100, 21);
    jtfldMenuName.setBounds(120, 40, 300, 21);
    jlblLocale.setBounds(10, 70, 100, 21);
    jtfldLocale.setBounds(120, 70, 300, 21);
    jlblIndex.setBounds(10, 110, 100, 21);
    jtfldIndex.setBounds(120, 110, 100, 21);
    jbtnFatherLookup.setBounds(10, 140, 100, 21);
    jtfldFather.setBounds(120, 140, 200, 21);
    fatherDesc.setBounds(330, 140, 200, 21);
    jbtnFormLookup.setBounds(10, 170, 100, 21);
    jtfldForm.setBounds(120, 170, 200, 21);
    formDesc.setBounds(330, 170, 200, 21);

    jPanel1.add(jlblMenu);
    jPanel1.add(jtfldMenu);
    jPanel1.add(jlblMenuName);
    jPanel1.add(jtfldMenuName);
    jPanel1.add(jlblLocale);
    jPanel1.add(jtfldLocale);
    jPanel1.add(jlblIndex);
    jPanel1.add(jtfldIndex);
    jPanel1.add(jbtnFatherLookup);
    jPanel1.add(jtfldFather);
    jPanel1.add(fatherDesc);
    jPanel1.add(jbtnFormLookup);
    jPanel1.add(jtfldForm);
    jPanel1.add(formDesc);
  }
}
