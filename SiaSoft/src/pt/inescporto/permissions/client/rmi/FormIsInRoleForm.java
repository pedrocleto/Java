package pt.inescporto.permissions.client.rmi;

import javax.swing.JPanel;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.design.FW_NavBarDetail;

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
public class FormIsInRoleForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  DataSourceRMI detail = null;

  String[] headers = {res.getString("label.code"), res.getString("label.description")};

  JPanel jpnlContent = new JPanel(null);

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
  FW_JTable jtFieldPerm = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public FormIsInRoleForm() {
    super();

    DataSourceRMI master = new DataSourceRMI("FormIsInRole");
    master.setUrl("pt.inescporto.permissions.ejb.session.GlbFormIsInRole");

    detail = new DataSourceRMI("FieldPerm");
    detail.setUrl("pt.inescporto.permissions.ejb.session.GlbFieldPerm");

    DSRelation mdRelation = new DSRelation("MasterDetail");
    mdRelation.setMaster(master);
    mdRelation.setDetail(detail);
    mdRelation.addKey(new RelationKey("role_id", "role_id"));
    mdRelation.addKey(new RelationKey("pk_form_id", "pk_form_id"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
    }

    setDataSource(master);
    dataSource.addDatasourceListener(detail);

    allHeaders = new String[] {res.getString("label.role"), res.getString("label.form"), res.getString("label.permission")};
    setAccessPermitionIdentifier("GR_FORM_IN_ROLE");

    init();
    start();
  }

  public void init() {
    // roles
    jtfldRole = new TmplJTextField(res.getString("label.role"), "role_id");
    jbtnRoleLookup = new TmplLookupButton(res.getString("label.roleList"), "pt.inescporto.permissions.ejb.session.GlbRole", headers, new TmplJTextField[] {jtfldRole});
    roleDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbRole", new JTextField[] {jtfldRole});
    // forms
    jtfldForm = new TmplJTextField(res.getString("label.form"), "pk_form_id");
    jbtnFormLookup = new TmplLookupButton(res.getString("label.formList"), "pt.inescporto.permissions.ejb.session.GlbForm", headers, new TmplJTextField[] {jtfldForm});
    formDesc = new TmplLookupField("pt.inescporto.permissions.ejb.session.GlbForm", new JTextField[] {jtfldForm});
    // permissions
    jlblPerm = new TmplJLabel();
    jtfldPerm = new TmplJTextField(jlblPerm, "permission");

    jtFieldPerm = new FW_JTable(detail,
                                 new String[] {res.getString("label.fields"), res.getString("label.permission")},
                                 new String[] {"pk_field_id", "permission"});
    jtFieldPerm.setAsMaster(false);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    navBarDetail.setFW_ComponentListener(jtFieldPerm);
    navBarDetail.setActionListener(jtFieldPerm);
  }

  private void jbInit() throws Exception {
    JScrollPane jsp = new JScrollPane(jtFieldPerm);

    setOpaque(false);
    jpnlContent.setLayout(new GridBagLayout());
    jpnlContent.setOpaque(false);
    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    jpnlDummy.add(jsp, BorderLayout.CENTER);
    jpnlDummy.add(navBarDetail, BorderLayout.SOUTH);
    add(jpnlDummy, BorderLayout.CENTER);

    jbtnRoleLookup.setText(res.getString("label.roles"));
    jbtnFormLookup.setText(res.getString("label.forms"));
    jlblPerm.setText(res.getString("label.permission"));

    // add visual components
    jpnlContent.add(jbtnRoleLookup, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldRole, new GridBagConstraints(1, 0, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(roleDesc, new GridBagConstraints(2, 0, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jbtnFormLookup, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldForm, new GridBagConstraints(1, 1, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(formDesc, new GridBagConstraints(2, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jlblPerm, new GridBagConstraints(0, 2, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jpnlContent.add(jtfldPerm, new GridBagConstraints(1, 2, 1, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    dataSource.addDatasourceListener(jbtnRoleLookup);
    dataSource.addDatasourceListener(roleDesc);
    dataSource.addDatasourceListener(jtfldRole);
    dataSource.addDatasourceListener(jbtnFormLookup);
    dataSource.addDatasourceListener(formDesc);
    dataSource.addDatasourceListener(jtfldForm);
    dataSource.addDatasourceListener(jtfldPerm);

    addFWComponentListener(jbtnRoleLookup);
    addFWComponentListener(roleDesc);
    addFWComponentListener(jtfldRole);
    addFWComponentListener(jbtnFormLookup);
    addFWComponentListener(formDesc);
    addFWComponentListener(jtfldForm);
    addFWComponentListener(jtfldPerm);
    addFWComponentListener(jtFieldPerm);

    // add visual components to panel
  }
}
