package pt.inescporto.permissions.client.rmi;

import pt.inescporto.template.client.rmi.TmplInternalFrame;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.Color;
import java.util.ResourceBundle;

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
public class FieldForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel(null);

  TmplJLabel jlblField = null;
  TmplJTextField jtfldField = null;
  TmplJLabel jlblFieldName = null;
  TmplJTextField jtfldFieldName = null;

  public FieldForm() {
    super();

    url = "pt.inescporto.permissions.ejb.session.GlbField";
    setPermFormId("GR_FIELD");

    init();
    start();
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

    jlblField.setText(res.getString("label.code"));
    jlblField.setBounds(new Rectangle(25, 51, 120, 17));
    jtfldField.setBounds(new Rectangle(172, 49, 200, 21));
    jlblFieldName.setText(res.getString("label.description"));
    jlblFieldName.setBounds(new Rectangle(25, 82, 120, 17));
    jtfldFieldName.setBounds(new Rectangle(172, 81, 400, 21));

    addTemplateListener(jtfldField);
    addTemplateListener(jtfldFieldName);
    addTemplateComponentListener(jtfldField);
    addTemplateComponentListener(jtfldFieldName);

    jpnlContent.add(jlblField, null);
    jpnlContent.add(jtfldField, null);
    jpnlContent.add(jlblFieldName, null);
    jpnlContent.add(jtfldFieldName, null);
  }
}
