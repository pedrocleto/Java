package pt.inescporto.permissions.client.web;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.web.*;

public class FieldForm extends TmplAppletForm {
  JPanel jpnlContent = new JPanel(null);

  TmplJLabel jlblField = null;
  TmplJTextField jtfldField = null;
  TmplJLabel jlblFieldName = null;
  TmplJTextField jtfldFieldName = null;

  public FieldForm() {
    super();

    setResourceFile("pt.inescporto.permissions.client.web.PermissionsResources");
    setPermFormId("GR_FIELD");
    urlWS = "/FieldProxy";
  }

  public void init() {
    super.init();

    jlblField = new TmplJLabel();
    jtfldField = new TmplJTextField(jlblField, "pk_field_id");
    jlblFieldName = new TmplJLabel();
    jtfldFieldName = new TmplJTextField(jlblFieldName, "field_desc");

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

    jlblField.setText(getResourceString("label.code"));
    jlblField.setBounds(new Rectangle(25, 51, 120, 17));
    jtfldField.setBounds(new Rectangle(172, 49, 200, 21));
    jlblFieldName.setText(getResourceString("label.description"));
    jlblFieldName.setBounds(new Rectangle(25, 82, 120, 17));
    jtfldFieldName.setBounds(new Rectangle(172, 81, 400, 21));

    addTemplateListener(jtfldField);
    addTemplateListener(jtfldFieldName);

    jpnlContent.add(jlblField, null);
    jpnlContent.add(jtfldField, null);
    jpnlContent.add(jlblFieldName, null);
    jpnlContent.add(jtfldFieldName, null);
  }
}
