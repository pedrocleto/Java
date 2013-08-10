package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.util.ResourceBundle;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

/**
 * <p>Title: Benchmarking</p>
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
public class UserForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");
  JPanel jpnlContent = new JPanel();

  TmplJLabel jlblUserId = new TmplJLabel();
  TmplJTextField jtfldUserId = new TmplJTextField();

  TmplJLabel jlblUserName = new TmplJLabel();
  TmplJTextField jtfldUserName = new TmplJTextField();

  TmplJLabel jlblUserEmail = new TmplJLabel();
  TmplJTextField jtfldUserEmail = new TmplJTextField();

  TmplJLabel jlblUserPassword = new TmplJLabel();
  TmplJPasswordField jpwfUserPassword = new TmplJPasswordField();

  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupButton jbtnEnterpriseLookup = new TmplLookupButton();
  TmplLookupField jtfldEnterpriseName = new TmplLookupField();

  public UserForm() {
    super();

    DataSourceRMI dsEnterprise = new DataSourceRMI("Users");
    dsEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    setDataSource(dsEnterprise);

    allHeaders = new String[] {res.getString("user.label.code"), res.getString("user.label.desc"), res.getString("user.label.passwd"), res.getString("user.label.email"), res.getString("user.label.enterprise")};
    setAccessPermitionIdentifier("USER");
    setPublisherEvent(SyncronizerSubjects.sysUSER);

    try {
      jbInit();
      start();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jlblUserId = new TmplJLabel(res.getString("user.label.code"));
    jtfldUserId = new TmplJTextField( jlblUserId, "userID" );

    jlblUserName = new TmplJLabel(res.getString("user.label.desc"));
    jtfldUserName = new TmplJTextField( jlblUserName, "userName" );

    jlblUserEmail = new TmplJLabel(res.getString("user.label.email"));
    jtfldUserEmail = new TmplJTextField( jlblUserEmail, "eMail" );

    jlblUserPassword = new TmplJLabel(res.getString("user.label.passwd"));
    jpwfUserPassword = new TmplJPasswordField(jlblUserPassword, "passwd");

    jtfldEnterprise = new TmplJTextField("Empresa", "enterpriseID");
    jbtnEnterpriseLookup = new TmplLookupButton(res.getString("label.enterpriseList"), "pt.inescporto.siasoft.comun.ejb.session.Enterprise",
                                                new String[] {res.getString("e.label.code"), res.getString("e.label.desc")},
                                                new TmplJTextField[] {jtfldEnterprise});
    jtfldEnterpriseName = new TmplLookupField("pt.inescporto.siasoft.comun.ejb.session.Enterprise", new JTextField[] {jtfldEnterprise}, 0);
    jbtnEnterpriseLookup.setText(res.getString("user.label.enterprise"));

    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4, 6, 10}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblUserId, cc.xy(2, 2));
    jpnlContent.add(jtfldUserId, cc.xy(4, 2));
    jpnlContent.add(jlblUserName, cc.xy(2, 4));
    jpnlContent.add(jtfldUserName, cc.xyw(4, 4, 3));
    jpnlContent.add(jlblUserEmail, cc.xy(2, 6));
    jpnlContent.add(jtfldUserEmail, cc.xyw(4, 6, 3));
    jpnlContent.add(jlblUserPassword, cc.xy(2, 8));
    jpnlContent.add(jpwfUserPassword, cc.xy(4, 8));
    jpnlContent.add(jbtnEnterpriseLookup, cc.xy(2, 10));
    jpnlContent.add(jtfldEnterprise, cc.xy(4, 10));
    jpnlContent.add(jtfldEnterpriseName, cc.xy(6, 10));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);

    dataSource.addDatasourceListener(jtfldUserId);
    dataSource.addDatasourceListener(jtfldUserName);
    dataSource.addDatasourceListener(jtfldUserEmail);
    dataSource.addDatasourceListener(jpwfUserPassword);
    dataSource.addDatasourceListener(jbtnEnterpriseLookup);
    dataSource.addDatasourceListener(jtfldEnterprise);
    dataSource.addDatasourceListener(jtfldEnterpriseName);

    addFWComponentListener(jtfldUserId);
    addFWComponentListener(jtfldUserName);
    addFWComponentListener(jtfldUserEmail);
    addFWComponentListener(jpwfUserPassword);
    addFWComponentListener(jbtnEnterpriseLookup);
    addFWComponentListener(jtfldEnterprise);
    addFWComponentListener(jtfldEnterpriseName);
  }
}
