package pt.inescporto.permissions.client.web;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class FormForm extends TmplAppletForm {
  JPanel jpnlContent = new JPanel(null);

  // template report button
  TmplJButtonReport jbtnReport = null;

  // my components
  TmplJLabel jlblForm = null;
  TmplJTextField jtfldForm = null;
  TmplJLabel jlblFormName = null;
  TmplJTextField jtfldFormName = null;
  TmplJLabel jlblClassRun = null;
  TmplJTextField jtfldClassRun = null;
  TmplJLabel jlblArchive = null;
  TmplJTextField jtfldArchive = null;
  TmplJLabel jlblSupport = null;
  TmplJTextField jtfldSupport = null;

  public FormForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    urlWS = "/FormProxy";
    setPermFormId("GR_FORM");
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {getResourceString("label.code"),
	getResourceString("label.description"), getResourceString("label.class"),
	getResourceString("label.archive"), getResourceString("label.support")};

    jbtnReport = new TmplJButtonReport();
    jlblForm = new TmplJLabel();
    jtfldForm = new TmplJTextField(jlblForm, "pk_form_id");
    jlblFormName = new TmplJLabel();
    jtfldFormName = new TmplJTextField(jlblFormName, "form_desc");
    jlblClassRun = new TmplJLabel("Classe");
    jtfldClassRun = new TmplJTextField(jlblClassRun, "class");
    jlblArchive = new TmplJLabel(getResourceString("label.archive"));
    jtfldArchive = new TmplJTextField(jlblArchive, "archives");
    jlblSupport = new TmplJLabel(getResourceString("label.support"));
    jtfldSupport = new TmplJTextField(jlblSupport, "support");

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

    jbtnReport.setBounds(new Rectangle(530, 10, 60, 22));

    jlblForm.setText(getResourceString("label.code"));
    jlblForm.setBounds(new Rectangle(25, 50, 120, 17));
    jtfldForm.setBounds(new Rectangle(172, 49, 200, 21));
    jlblFormName.setText(getResourceString("label.description"));
    jlblFormName.setBounds(new Rectangle(25, 80, 120, 17));
    jtfldFormName.setBounds(new Rectangle(172, 80, 400, 21));
    jlblClassRun.setBounds(new Rectangle(25, 110, 120, 17));
    jtfldClassRun.setBounds(new Rectangle(172, 110, 400, 21));
    jlblArchive.setBounds(new Rectangle(25, 140, 120, 17));
    jtfldArchive.setBounds(new Rectangle(172, 140, 400, 21));
    jlblSupport.setBounds(new Rectangle(25, 170, 120, 17));
    jtfldSupport.setBounds(new Rectangle(172, 170, 400, 21));

    // add action listeners
    jbtnReport.addActionListener(this);

    jpnlMenuNav.add(jbtnReport, null);
    jpnlContent.add(jlblForm, null);
    jpnlContent.add(jtfldForm, null);
    jpnlContent.add(jlblFormName, null);
    jpnlContent.add(jtfldFormName, null);
    jpnlContent.add(jlblClassRun, null);
    jpnlContent.add(jtfldClassRun, null);
    jpnlContent.add(jlblArchive, null);
    jpnlContent.add(jtfldArchive, null);
    jpnlContent.add(jlblSupport, null);
    jpnlContent.add(jtfldSupport, null);

    // register this listeners
    addTemplateListener(jbtnReport);
    addTemplateListener(jtfldForm);
    addTemplateListener(jtfldFormName);
    addTemplateListener(jtfldClassRun);
    addTemplateListener(jtfldArchive);
    addTemplateListener(jtfldSupport);
  }
}
