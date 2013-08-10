package pt.inescporto.permissions.client.rmi;

import pt.inescporto.template.client.rmi.TmplInternalFrame;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Rectangle;
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

public class FormForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");

  JPanel jpnlContent = new JPanel(null);

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

    url = "pt.inescporto.permissions.ejb.session.GlbForm";
    setPermFormId("GR_FORM");

    init();
    start();
  }
  public void init() {
    super.init();

    this.allHeaders = new String[] {res.getString("label.code"),
        res.getString("label.description"), res.getString("label.class"),
        res.getString("label.archive"), res.getString("label.support")};

    jlblForm = new TmplJLabel(res.getString("label.code"));
    jtfldForm = new TmplJTextField(jlblForm, "pk_form_id");
    jlblFormName = new TmplJLabel(res.getString("label.description"));
    jtfldFormName = new TmplJTextField(jlblFormName, "form_desc");
    jlblClassRun = new TmplJLabel("Classe");
    jtfldClassRun = new TmplJTextField(jlblClassRun, "class");
    jlblArchive = new TmplJLabel(res.getString("label.archive"));
    jtfldArchive = new TmplJTextField(jlblArchive, "archives");
    jlblSupport = new TmplJLabel(res.getString("label.support"));
    jtfldSupport = new TmplJTextField(jlblSupport, "support");

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setLayout(new GridBagLayout());
    jpnlContent.setOpaque(false);
    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlDummy, BorderLayout.CENTER);

    // add visual components to form
    jpnlContent.add(jlblForm, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldForm, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblFormName, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldFormName, new GridBagConstraints(1, 1, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblClassRun, new GridBagConstraints(0, 2, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldClassRun, new GridBagConstraints(1, 2, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblArchive, new GridBagConstraints(0, 3, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldArchive, new GridBagConstraints(1, 3, 2, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblSupport, new GridBagConstraints(0, 4, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldSupport, new GridBagConstraints(1, 4, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    // register this listeners
    addTemplateListener(jtfldForm);
    addTemplateListener(jtfldFormName);
    addTemplateListener(jtfldClassRun);
    addTemplateListener(jtfldArchive);
    addTemplateListener(jtfldSupport);

    addTemplateComponentListener(jtfldForm);
    addTemplateComponentListener(jtfldFormName);
    addTemplateComponentListener(jtfldClassRun);
    addTemplateComponentListener(jtfldArchive);
    addTemplateComponentListener(jtfldSupport);
  }
}
