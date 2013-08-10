package pt.inescporto.template.email.client.rmi.forms;

import java.util.ResourceBundle;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJPasswordField;
import pt.inescporto.template.client.design.TmplJCheckBox;

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
public class SMTPConfigForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.template.email.client.rmi.forms.EmailResources");
  JPanel jpnlContent = new JPanel();

  TmplJLabel jlblHostName = null;
  TmplJTextField jtfldHostName = null;
  TmplJLabel jlblUserName = null;
  TmplJTextField jtfldUserName = null;
  TmplJLabel jlblUserPass = null;
  TmplJPasswordField jtfldUserPass = null;
  TmplJLabel jlblFromEmail = null;
  TmplJTextField jtfldFromEmail = null;
  TmplJLabel jlblFromName = null;
  TmplJTextField jtfldFromName = null;
  TmplJCheckBox jchkbDebug = null;

  public SMTPConfigForm() {
    super();

    DataSourceRMI master = new DataSourceRMI("SMTPConfig");
    master.setUrl("pt.inescporto.template.email.ejb.session.SMTPConfig");
    setDataSource(master);

    allHeaders = new String[] {res.getString("email.label.hostname"), res.getString("email.label.username")};
    setAccessPermitionIdentifier("SMTP_CONFIG");

    init();
    start();
  }

  public void init() {
    jlblHostName = new TmplJLabel();
    jtfldHostName = new TmplJTextField(jlblHostName, "hostName");
    jlblUserName = new TmplJLabel();
    jtfldUserName = new TmplJTextField(jlblUserName, "userName");
    jlblUserPass = new TmplJLabel();
    jtfldUserPass = new TmplJPasswordField(jlblUserPass, "password");
    jlblFromEmail = new TmplJLabel();
    jtfldFromEmail = new TmplJTextField(jlblFromEmail, "fromEmail");
    jlblFromName = new TmplJLabel();
    jtfldFromName = new TmplJTextField(jlblFromName, "fromUser");
    jchkbDebug = new TmplJCheckBox(res.getString("email.label.debug"), "debug", new Object[] {Boolean.TRUE, Boolean.FALSE});

    jlblHostName.setText(res.getString("email.label.hostname"));
    jlblUserName.setText(res.getString("email.label.username"));
    jlblUserPass.setText(res.getString("email.label.userpasswd"));
    jlblFromEmail.setText(res.getString("email.label.fromemail"));
    jlblFromName.setText(res.getString("email.label.fromuser"));
    jchkbDebug.setText(res.getString("email.label.debug"));

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }


    dataSource.addDatasourceListener(jtfldHostName);
    dataSource.addDatasourceListener(jtfldUserName);
    dataSource.addDatasourceListener(jtfldUserPass);
    dataSource.addDatasourceListener(jtfldFromEmail);
    dataSource.addDatasourceListener(jtfldFromName);
    dataSource.addDatasourceListener(jchkbDebug);

    addFWComponentListener(jtfldHostName);
    addFWComponentListener(jtfldUserName);
    addFWComponentListener(jtfldUserPass);
    addFWComponentListener(jtfldFromEmail);
    addFWComponentListener(jtfldFromName);
    addFWComponentListener(jchkbDebug);
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 150dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4, 6, 8, 10, 12}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblHostName, cc.xy(2, 2));
    jpnlContent.add(jtfldHostName, cc.xy(4, 2));
    jpnlContent.add(jlblUserName, cc.xy(2, 4));
    jpnlContent.add(jtfldUserName, cc.xy(4, 4));
    jpnlContent.add(jlblUserPass, cc.xy(2, 6));
    jpnlContent.add(jtfldUserPass, cc.xy(4, 6));
    jpnlContent.add(jlblFromEmail, cc.xy(2, 8));
    jpnlContent.add(jtfldFromEmail, cc.xy(4, 8));
    jpnlContent.add(jlblFromName, cc.xy(2, 10));
    jpnlContent.add(jtfldFromName, cc.xy(4, 10));
    jpnlContent.add(jchkbDebug, cc.xy(2, 12));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);
  }
}
