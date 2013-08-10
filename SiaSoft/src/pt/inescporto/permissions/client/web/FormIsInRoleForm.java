package pt.inescporto.permissions.client.web;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class FormIsInRoleForm extends TmplAppletForm {
  String[] headers = {getResourceString("label.code"), getResourceString("label.description")};

  JPanel jpnlContent = new JPanel(null);

  // template report button
  TmplJButtonReport jbtnReport = null;

  // roles
  TmplJTextField jtfldRole = null;
  TmplLookupButton jbtnRoleLookup = null;
  TmplLookupField roleDesc = null;
  // forms
  TmplJTextField jtfldForm = null;
  TmplLookupButton jbtnFormLookup = null;
  TmplLookupField formDesc = null;
  // permissions
  TmplJLabel jlblPerm = null;
  TmplJTextField jtfldPerm = null;

  // detail FieldPerm
  TmplJTable jtFieldPerm = null;
  TmplJButtonDetailInsert jbtDetailInsert = new TmplJButtonDetailInsert();
  TmplJButtonDetailUpdate jbtDetailUpdate = new TmplJButtonDetailUpdate();
  TmplJButtonDetailDelete jbtDetailDelete = new TmplJButtonDetailDelete();
  TmplJButtonDetailSave jbtDetailSave = new TmplJButtonDetailSave();
  TmplJButtonDetailCancel jbtDetailCancel = new TmplJButtonDetailCancel();

  public FormIsInRoleForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/FormIsInRoleProxy";
    setPermFormId("GR_FORM_IN_ROLE");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.role"), getResourceString("label.form"), getResourceString("label.permission")};

    jbtnReport = new TmplJButtonReport();
    // roles
    jtfldRole = new TmplJTextField(getResourceString("label.role"), "role_id");
    jbtnRoleLookup = new TmplLookupButton(getResourceString("label.roleList"), urlBase + "/RoleProxy", headers, new TmplJTextField[] {jtfldRole});
    roleDesc = new TmplLookupField(urlBase + "/RoleProxy", new JTextField[] {jtfldRole});
    // forms
    jtfldForm = new TmplJTextField(getResourceString("label.form"), "pk_form_id");
    jbtnFormLookup = new TmplLookupButton(getResourceString("label.formList"), urlBase + "/FormProxy", headers, new TmplJTextField[] {jtfldForm});
    formDesc = new TmplLookupField(urlBase + "/FormProxy", new JTextField[] {jtfldForm});
    // permissions
    jlblPerm = new TmplJLabel();
    jtfldPerm = new TmplJTextField(jlblPerm, "permission");

    jtFieldPerm = new TmplJTable(urlBase + "/FieldPermProxy",
				 new String[] {getResourceString("label.fields"), getResourceString("label.permission")},
				 new String[] {"pk_field_id", "permission"});
    jtFieldPerm.registerBtnActionListener(jbtDetailInsert);
    jtFieldPerm.registerBtnActionListener(jbtDetailUpdate);
    jtFieldPerm.registerBtnActionListener(jbtDetailDelete);
    jtFieldPerm.registerBtnActionListener(jbtDetailSave);
    jtFieldPerm.registerBtnActionListener(jbtDetailCancel);

    detailLinks = new JComponent[] {jtfldRole, jtfldForm};
    jtFieldPerm.setBinds(detailLinks);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jpnlMenuNav.add(jbtnReport);

    jpnlContent.setLayout(null);
    jpnlContent.setOpaque(false);
    getContentPane().add(jpnlContent, BorderLayout.CENTER);

    jbtnRoleLookup.setText(getResourceString("label.roles"));
    jbtnRoleLookup.setBounds(new Rectangle(10, 10, 120, 21));
    jtfldRole.setBounds(new Rectangle(140, 10, 150, 21));
    roleDesc.setBounds(new Rectangle(300, 10, 290, 21));

    jbtnFormLookup.setText(getResourceString("label.forms"));
    jbtnFormLookup.setBounds(new Rectangle(10, 40, 120, 21));
    jtfldForm.setBounds(new Rectangle(140, 40, 150, 21));
    formDesc.setBounds(new Rectangle(300, 40, 290, 21));

    jlblPerm.setText(getResourceString("label.permission"));
    jlblPerm.setBounds(new Rectangle(10, 70, 120, 21));
    jtfldPerm.setBounds(new Rectangle(140, 70, 150, 21));

    JScrollPane jsp = new JScrollPane(jtFieldPerm);
    jsp.setBounds(10, 100, 580, 300);

    jbtDetailInsert.setBounds(10, 410, 60, 22);
    jbtDetailUpdate.setBounds(70, 410, 60, 22);
    jbtDetailDelete.setBounds(130, 410, 60, 22);
    jbtDetailSave.setBounds(190, 410, 60, 22);
    jbtDetailCancel.setBounds(250, 410, 60, 22);

    addTemplateListener(jbtnRoleLookup);
    addTemplateListener(roleDesc);
    addTemplateListener(jtfldRole);

    addTemplateListener(jbtnFormLookup);
    addTemplateListener(formDesc);
    addTemplateListener(jtfldForm);

    addTemplateListener(jtfldPerm);

    addTemplateListener(jtFieldPerm);

    // add action listeners
    jbtnReport.addActionListener(this);

    jpnlContent.add(jbtnReport, null);
    jpnlContent.add(jbtnRoleLookup, null);
    jpnlContent.add(jtfldRole, null);
    jpnlContent.add(roleDesc, null);
    jpnlContent.add(jbtnFormLookup, null);
    jpnlContent.add(jtfldForm, null);
    jpnlContent.add(formDesc, null);
    jpnlContent.add(jlblPerm, null);
    jpnlContent.add(jtfldPerm, null);
    jpnlContent.add(jsp, null);
    jpnlContent.add(jbtDetailInsert, null);
    jpnlContent.add(jbtDetailUpdate, null);
    jpnlContent.add(jbtDetailDelete, null);
    jpnlContent.add(jbtDetailSave, null);
    jpnlContent.add(jbtDetailCancel, null);
  }
}
