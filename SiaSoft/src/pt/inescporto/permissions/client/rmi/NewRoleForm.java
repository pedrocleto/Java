package pt.inescporto.permissions.client.rmi;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.util.DataSourceRMI;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class NewRoleForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.permissions.client.web.PermissionsResources");
  JPanel jpnlContent = new JPanel();

  TmplJLabel jlblRole = null;
  TmplJTextField jtfldRole = null;
  TmplJLabel jlblRoleName = null;
  TmplJTextField jtfldRoleName = null;

  public NewRoleForm() {
    super();

    DataSourceRMI test = new DataSourceRMI("Role");
    test.setUrl("pt.inescporto.permissions.ejb.session.GlbRole");
    setDataSource(test);

    allHeaders = new String[] {res.getString("label.code"), res.getString("label.description")};
    setAccessPermitionIdentifier("GR_PERM");

    init();
    start();
  }

  public void init() {
    jlblRole = new TmplJLabel();
    jtfldRole = new TmplJTextField(jlblRole, "role_id");
    jlblRoleName = new TmplJLabel();
    jtfldRoleName = new TmplJTextField(jlblRoleName, "role_name");

    jlblRole.setText(res.getString("label.code"));
    jlblRoleName.setText(res.getString("label.description"));

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblRole, cc.xy(2,2));
    jpnlContent.add(jtfldRole, cc.xy(4,2));
    jpnlContent.add(jlblRoleName, cc.xy(2,4));
    jpnlContent.add(jtfldRoleName, cc.xyw(4,4,2));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);

    dataSource.addDatasourceListener(jtfldRole);
    dataSource.addDatasourceListener(jtfldRoleName);

    addFWComponentListener(jtfldRole);
    addFWComponentListener(jtfldRoleName);
  }
}
