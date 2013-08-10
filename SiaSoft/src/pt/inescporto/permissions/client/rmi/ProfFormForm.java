package pt.inescporto.permissions.client.rmi;

import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.TmplInternalFrame;
import pt.inescporto.template.client.design.TmplJComboBox;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJButtonDetailDelete;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJButtonDetailCancel;
import pt.inescporto.template.client.design.TmplJButtonDetailSave;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJButtonDetailInsert;
import javax.swing.JComponent;
import pt.inescporto.template.client.design.TmplJLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJButtonDetailUpdate;
import pt.inescporto.template.client.design.TmplJTable;
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
public class ProfFormForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  String[] headers = {res.getString("label.code"), res.getString("label.description")};

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

    url = "pt.inescporto.permissions.ejb.session.GlbProfForm";
    setPermFormId("GR_FORM_IN_ROLE");

    init();
    start();
  }

  public void init() {
    super.init();

    this.allHeaders = new String[] {res.getString("label.profile"), res.getString("label.form"), res.getString("label.visibility")};

    // profiles
    jtfldProf = new TmplJTextField(res.getString("label.profile"), "profile_id");
    jbtnProfLookup = new TmplLookupButton(res.getString("label.profileList"), "pt.inescporto.permissions.ejb.session.GlbProfile", headers, new TmplJTextField[] {jtfldProf});
    profDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbProfile", new JTextField[] {jtfldProf});
    // forms
    jtfldForm = new TmplJTextField(res.getString("label.form"), "pk_form_id");
    jbtnFormLookup = new TmplLookupButton(res.getString("label.formList"), "pt.inescporto.permissions.ejb.session.GlbForm", headers, new TmplJTextField[] {jtfldForm});
    formDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbForm", new JTextField[] {jtfldForm});
    // visible
    jlblVisible = new TmplJLabel();
    jcmbVisible = new TmplJComboBox(jlblVisible, "visible", new Object[] {"", res.getString("label.yes"), res.getString("label.no")}, new Object[] {"", "Y", "N"});

    jtProfField = new TmplJTable("pt.inescporto.permissions.ejb.session.GlbProfField",
                                 new String[] {res.getString("label.fields"), res.getString("label.visibility")},
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
    JScrollPane jsp = new JScrollPane(jtProfField);

    setOpaque(false);
    jpnlContent.setLayout(new GridBagLayout());
    jpnlContent.setOpaque(false);
    JPanel jpnlDummy = new JPanel(new BorderLayout());
    JPanel jpnlCommandTabel = new JPanel();
    jpnlCommandTabel.setOpaque(false);
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    jpnlDummy.add(jsp, BorderLayout.CENTER);
    jpnlDummy.add(jpnlCommandTabel, BorderLayout.SOUTH);
    add(jpnlDummy, BorderLayout.CENTER);

    jbtnProfLookup.setText(res.getString("label.profiles"));
    jbtnFormLookup.setText(res.getString("label.forms"));
    jlblVisible.setText(res.getString("label.visibility"));

    // add visual components
    jpnlContent.add(jbtnProfLookup, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldProf, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(profDesc, new GridBagConstraints(2, 0, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnFormLookup, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldForm, new GridBagConstraints(1, 1, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(formDesc, new GridBagConstraints(2, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblVisible, new GridBagConstraints(0, 2, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jcmbVisible, new GridBagConstraints(1, 2, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlCommandTabel.add(jbtDetailInsert);
    jpnlCommandTabel.add(jbtDetailUpdate);
    jpnlCommandTabel.add(jbtDetailDelete);
    jpnlCommandTabel.add(jbtDetailSave);
    jpnlCommandTabel.add(jbtDetailCancel);

    addTemplateListener(jbtnProfLookup);
    addTemplateListener(jtfldProf);
    addTemplateListener(profDesc);
    addTemplateListener(jbtnFormLookup);
    addTemplateListener(jtfldForm);
    addTemplateListener(formDesc);
    addTemplateListener(jcmbVisible);
    addTemplateListener(jtProfField);

    addTemplateComponentListener(jbtnProfLookup);
    addTemplateComponentListener(jtfldProf);
    addTemplateComponentListener(profDesc);
    addTemplateComponentListener(jbtnFormLookup);
    addTemplateComponentListener(jtfldForm);
    addTemplateComponentListener(formDesc);
    addTemplateComponentListener(jcmbVisible);
    addTemplateComponentListener(jtProfField);
  }
}
