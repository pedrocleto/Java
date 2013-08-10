package pt.inescporto.permissions.client.web;

import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class ProfFormForm extends TmplAppletForm {
  String[] headers = {getResourceString("label.code"), getResourceString("label.description")};

  JPanel jpnlContent = new JPanel(null);

  // profile
  TmplJTextField jtfldProf = null;
  TmplLookupButton jbtnProfLookup = null;
  TmplLookupField profDesc = null;
  // forms
  TmplJTextField jtfldForm = null;
  TmplLookupButton jbtnFormLookup = null;
  TmplLookupField formDesc = null;
  // visible
  TmplJLabel jlblVisible = null;
  TmplJComboBox jcmbVisible = null;
  // detail FieldPerm
  TmplJTable jtProfField = null;
  TmplJButtonDetailInsert jbtDetailInsert = new TmplJButtonDetailInsert();
  TmplJButtonDetailUpdate jbtDetailUpdate = new TmplJButtonDetailUpdate();
  TmplJButtonDetailDelete jbtDetailDelete = new TmplJButtonDetailDelete();
  TmplJButtonDetailSave jbtDetailSave = new TmplJButtonDetailSave();
  TmplJButtonDetailCancel jbtDetailCancel = new TmplJButtonDetailCancel();

  public ProfFormForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/ProfFormProxy";
    setPermFormId("GR_FORM_IN_ROLE");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.profile"), getResourceString("label.form"), getResourceString("label.visibility")};

    // profiles
    jtfldProf = new TmplJTextField(getResourceString("label.profile"), "profile_id");
    jbtnProfLookup = new TmplLookupButton(getResourceString("label.profileList"), urlBase + "/ProfileProxy", headers, new TmplJTextField[] {jtfldProf});
    profDesc = new TmplLookupField(urlBase + "/ProfileProxy", new JTextField[] {jtfldProf});
    // forms
    jtfldForm = new TmplJTextField(getResourceString("label.form"), "pk_form_id");
    jbtnFormLookup = new TmplLookupButton(getResourceString("label.formList"), urlBase + "/FormProxy", headers, new TmplJTextField[] {jtfldForm});
    formDesc = new TmplLookupField(urlBase + "/FormProxy", new JTextField[] {jtfldForm});
    // visible
    jlblVisible = new TmplJLabel();
    jcmbVisible = new TmplJComboBox(jlblVisible, "visible", new Object[] {"", getResourceString("label.yes"), getResourceString("label.no")}, new Object[] {"", "Y", "N"});

    jtProfField = new TmplJTable(urlBase + "/ProfFieldProxy",
				 new String[] {getResourceString("label.fields"), getResourceString("label.visibility")},
				 new String[] {"pk_field_id", "visible"});
    jtProfField.registerBtnActionListener(jbtDetailInsert);
    jtProfField.registerBtnActionListener(jbtDetailUpdate);
    jtProfField.registerBtnActionListener(jbtDetailDelete);
    jtProfField.registerBtnActionListener(jbtDetailSave);
    jtProfField.registerBtnActionListener(jbtDetailCancel);

    detailLinks = new JComponent[] {jtfldProf, jtfldForm};

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

    jbtnProfLookup.setText(getResourceString("label.profiles"));
    jbtnProfLookup.setBounds(new Rectangle(10, 10, 120, 21));
    jtfldProf.setBounds(new Rectangle(140, 10, 150, 21));
    profDesc.setBounds(new Rectangle(300, 10, 300, 21));

    jbtnFormLookup.setText(getResourceString("label.forms"));
    jbtnFormLookup.setBounds(new Rectangle(10, 40, 120, 21));
    jtfldForm.setBounds(new Rectangle(140, 40, 150, 21));
    formDesc.setBounds(new Rectangle(300, 40, 300, 21));

    jlblVisible.setText(getResourceString("label.visibility"));
    jlblVisible.setBounds(new Rectangle(10, 70, 120, 21));
    jcmbVisible.setBounds(new Rectangle(140, 70, 150, 21));

    JScrollPane jsp = new JScrollPane(jtProfField);
    jsp.setBounds(10, 100, 580, 300);

    jbtDetailInsert.setBounds(10, 410, 60, 22);
    jbtDetailUpdate.setBounds(70, 410, 60, 22);
    jbtDetailDelete.setBounds(130, 410, 60, 22);
    jbtDetailSave.setBounds(190, 410, 60, 22);
    jbtDetailCancel.setBounds(250, 410, 60, 22);

    addTemplateListener(jbtnProfLookup);
    addTemplateListener(jtfldProf);
    addTemplateListener(profDesc);
    addTemplateListener(jbtnFormLookup);
    addTemplateListener(jtfldForm);
    addTemplateListener(formDesc);
    addTemplateListener(jcmbVisible);
    addTemplateListener(jtProfField);

    jpnlContent.add(jbtnProfLookup, null);
    jpnlContent.add(jtfldProf, null);
    jpnlContent.add(profDesc, null);
    jpnlContent.add(jbtnFormLookup, null);
    jpnlContent.add(jtfldForm, null);
    jpnlContent.add(formDesc, null);
    jpnlContent.add(jlblVisible, null);
    jpnlContent.add(jcmbVisible, null);
    jpnlContent.add(jsp, null);
    jpnlContent.add(jbtDetailInsert, null);
    jpnlContent.add(jbtDetailUpdate, null);
    jpnlContent.add(jbtDetailDelete, null);
    jpnlContent.add(jbtDetailSave, null);
    jpnlContent.add(jbtDetailCancel, null);
  }
}
