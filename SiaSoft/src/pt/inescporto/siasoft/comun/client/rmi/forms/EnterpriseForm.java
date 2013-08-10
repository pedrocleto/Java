package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

/**
 *
 * <p>Description: Enterprise Form</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: INESCPorto</p>
 * @author jap
 * @version 1.0
 */
public class EnterpriseForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  JPanel jpnlContent = new JPanel();

  TmplJLabel jlblEnterprise = null;
  TmplJTextField jtfldEnterprise = null;

  TmplJLabel jlblEName = null;
  TmplJTextField jtfldEName = null;

  TmplJLabel jlblCAE = null;
  TmplJTextField jtfldCAE = null;

  TmplJLabel jlblAddress = null;
  TmplJTextField jtfldAddress = null;

  TmplJLabel jlblUrl = null;
  TmplJTextField jtfldUrl = null;

  TmplJLabel jlblCoordinator = null;
  TmplJTextField jtfldCoordinator = null;

  TmplJLabel jlblSupplier = null;
  TmplJComboBox jcbSupplier = null;

  public EnterpriseForm() {
    super();

    DataSourceRMI dsEnterprise = new DataSourceRMI("Enterprise");
    dsEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    setDataSource(dsEnterprise);

    allHeaders = new String[] {res.getString("e.label.code"), res.getString("e.label.desc"), res.getString("e.label.address"), res.getString("e.label.url"), res.getString("e.label.coordinator"), res.getString("e.label.admin")};
    setAccessPermitionIdentifier("ENTERPRISE");
    setPublisherEvent(SyncronizerSubjects.sysENTERPRISE);

    init();
    start();
  }

  public void init() {
    allHeaders = new String[] {res.getString("e.label.code"), res.getString("e.label.desc"), res.getString("e.label.cae"), res.getString("e.label.address"), res.getString("e.label.url"), res.getString("e.label.coordinator"), res.getString("e.label.admin")};

    jlblEnterprise = new TmplJLabel(res.getString("e.label.code"));
    jtfldEnterprise = new TmplJTextField( jlblEnterprise, "enterpriseID" );
    jlblEName = new TmplJLabel(res.getString("e.label.desc"));
    jtfldEName = new TmplJTextField( jlblEName, "enterpriseName" );
    jlblCAE = new TmplJLabel(res.getString("e.label.cae"));
    jtfldCAE = new TmplJTextField( jlblCAE, "CAE" );
    jlblAddress = new TmplJLabel(res.getString("e.label.address"));
    jtfldAddress = new TmplJTextField( jlblAddress, "address" );
    jlblUrl = new TmplJLabel(res.getString("e.label.url"));
    jtfldUrl = new TmplJTextField( jlblUrl, "URL" );
    jlblCoordinator = new TmplJLabel(res.getString("e.label.coordinator"));
    jtfldCoordinator = new TmplJTextField( jlblCoordinator, "coordinator" );
    jlblSupplier = new TmplJLabel(res.getString("e.label.admin"));
    jcbSupplier = new TmplJComboBox(jlblSupplier, "supplier", new Object[] {"", res.getString("label.no"), res.getString("label.yes")}, new Object[] {"", "N", "Y"});

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4, 6, 8, 10, 12}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblEnterprise, cc.xy(2, 2));
    jpnlContent.add(jtfldEnterprise, cc.xy(4, 2));
    jpnlContent.add(jlblEName, cc.xy(2, 4));
    jpnlContent.add(jtfldEName, cc.xyw(4, 4, 2));
    jpnlContent.add(jlblCAE, cc.xy(2, 6));
    jpnlContent.add(jtfldCAE, cc.xy(4, 6));
    jpnlContent.add(jlblAddress, cc.xy(2, 8));
    jpnlContent.add(jtfldAddress, cc.xyw(4, 8, 2));
    jpnlContent.add(jlblUrl, cc.xy(2, 10));
    jpnlContent.add(jtfldUrl, cc.xyw(4, 10, 2));
    jpnlContent.add(jlblCoordinator, cc.xy(2, 12));
    jpnlContent.add(jtfldCoordinator, cc.xyw(4, 12, 2));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);

    dataSource.addDatasourceListener(jtfldEnterprise);
    dataSource.addDatasourceListener(jtfldEName);
    dataSource.addDatasourceListener(jtfldCAE);
    dataSource.addDatasourceListener(jtfldAddress);
    dataSource.addDatasourceListener(jtfldUrl);
    dataSource.addDatasourceListener(jlblCoordinator);

    addFWComponentListener(jtfldEnterprise);
    addFWComponentListener(jtfldEName);
    addFWComponentListener(jtfldCAE);
    addFWComponentListener(jtfldAddress);
    addFWComponentListener(jtfldUrl);
    addFWComponentListener(jtfldCoordinator);
  }
}
