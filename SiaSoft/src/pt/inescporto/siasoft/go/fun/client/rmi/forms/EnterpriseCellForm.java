package pt.inescporto.siasoft.go.fun.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.util.ResourceBundle;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import pt.inescporto.siasoft.go.fun.ejb.dao.EnterpriseCellDao;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;

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
public class EnterpriseCellForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.fun.client.rmi.forms.FormResources");
  DataSourceRMI detail = null;

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();

  TmplJLabel jlblEnterpriseCellID = new TmplJLabel();
  TmplJTextField jtfldEnterpriseCellID = new TmplJTextField();

  TmplJLabel jlblEnterpriseCellDescription = new TmplJLabel();
  TmplJTextField jtfldEnterpriseCellDescription = new TmplJTextField();

  TmplLookupButton jlbtnFatherCellID = new TmplLookupButton();
  TmplJTextField jtfldFatherCellID = new TmplJTextField();
  TmplLookupField jlfldFatherCellID = new TmplLookupField();

  TmplJLabel jlblEnterpriseCellObs = new TmplJLabel();
  TmplJTextArea jtfldEnterpriseCellObs = new TmplJTextArea();

  public EnterpriseCellForm() {
    super();

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("EnterpriseCell");
    master.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("enterpriseID = ?", binds);
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }
    }

    // register the master datasource the this form
    setDataSource(master);

    // set the id from permission control
    setAccessPermitionIdentifier("ENTERPRISECELL");

    // headers for the table headers
    allHeaders = new String[] {res.getString("enterpriseCell.label.enterprise"), res.getString("enterpriseCell.label.code"), res.getString("enterpriseCell.label.desc"),res.getString("enterpriseCell.label.obs"),res.getString("enterpriseCell.label.x"),res.getString("enterpriseCell.label.y"),res.getString("enterpriseCell.label.dx"),res.getString("enterpriseCell.label.dy"),res.getString("enterpriseCell.label.father")};

    init();
    start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    dataSource.addDatasourceListener(jlbtnEnterprise);
    dataSource.addDatasourceListener(jtfldEnterprise);
    dataSource.addDatasourceListener(jlfldEnterprise);
    dataSource.addDatasourceListener(jtfldEnterpriseCellID);
    dataSource.addDatasourceListener(jtfldEnterpriseCellDescription);
    dataSource.addDatasourceListener(jlbtnFatherCellID);
    dataSource.addDatasourceListener(jtfldFatherCellID);
    dataSource.addDatasourceListener(jlfldFatherCellID);
    dataSource.addDatasourceListener(jtfldEnterpriseCellObs);

    addFWComponentListener(jlbtnEnterprise);
    addFWComponentListener(jtfldEnterprise);
    addFWComponentListener(jlfldEnterprise);
    addFWComponentListener(jtfldEnterpriseCellID);
    addFWComponentListener(jtfldEnterpriseCellDescription);
    addFWComponentListener(jlbtnFatherCellID);
    addFWComponentListener(jtfldFatherCellID);
    addFWComponentListener(jlfldFatherCellID);
    addFWComponentListener(jtfldEnterpriseCellObs);
  }

  private void jbInit() throws Exception {
    jtfldEnterprise.setText(res.getString("enterpriseCell.label.enterprise"));
    jtfldEnterprise.setField("enterpriseID");
    jlbtnEnterprise.setText(res.getString("enterpriseCell.label.enterprise"));
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setTitle(res.getString("enterpriseCell.label.list"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    jlblEnterpriseCellID.setText(res.getString("enterpriseCell.label.code"));
    jtfldEnterpriseCellID.setField("enterpriseCellID");
    jtfldEnterpriseCellID.setHolder(jlblEnterpriseCellID);

    jlblEnterpriseCellDescription.setText(res.getString("enterpriseCell.label.desc"));
    jtfldEnterpriseCellDescription.setField("enterpriseCellDescription");
    jtfldEnterpriseCellDescription.setHolder(jlblEnterpriseCellDescription);

    jtfldFatherCellID.setText(res.getString("enterpriseCell.label.father"));
    jtfldFatherCellID.setField("fatherCellID");
    jlbtnFatherCellID.setText(res.getString("enterpriseCell.label.father"));
    jlbtnFatherCellID.setLinkCondition("enterpriseID = '" + jtfldEnterprise.getText() + "'");
    jlbtnFatherCellID.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    jlbtnFatherCellID.setTitle(res.getString("enterpriseCell.label.depList"));
    jlbtnFatherCellID.setDefaultFill(jtfldFatherCellID);
    jlfldFatherCellID.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    jlfldFatherCellID.setRefFieldList(new JTextField[] {jtfldEnterprise, jtfldFatherCellID});

    jlblEnterpriseCellObs.setText(res.getString("enterpriseCell.label.obs"));
    jtfldEnterpriseCellObs.setField("enterpriseCellObs");
    jtfldEnterpriseCellObs.setHolder(jlblEnterpriseCellObs);
    jtfldEnterpriseCellObs.setWrapStyleWord(true);
    jtfldEnterpriseCellObs.setLineWrap(true);
    JScrollPane jspObs = new JScrollPane(jtfldEnterpriseCellObs);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 70dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, 50dlu:grow, 5px");

    JPanel jpnlContent = new JPanel(new BorderLayout());
    jpnlContent.setLayout(formLayout);

    if (MenuSingleton.isSupplier())
      formLayout.setRowGroups(new int[][] { {2, 4, 6, 8}});
    else
      formLayout.setRowGroups(new int[][] { {4, 6, 8}});

    CellConstraints cc = new CellConstraints();

    if (MenuSingleton.isSupplier()) {
      jpnlContent.add(jlbtnEnterprise, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
      jpnlContent.add(jtfldEnterprise, cc.xy(4, 2));
      jpnlContent.add(jlfldEnterprise, cc.xyw(6, 2, 2));
    }

    jpnlContent.add(jlblEnterpriseCellID, cc.xy(2, 4));
    jpnlContent.add(jtfldEnterpriseCellID, cc.xy(4, 4));

    jpnlContent.add(jlblEnterpriseCellDescription, cc.xy(2, 6));
    jpnlContent.add(jtfldEnterpriseCellDescription, cc.xyw(4, 6, 4));

    jpnlContent.add(jlbtnFatherCellID, cc.xy(2, 8, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(jtfldFatherCellID, cc.xy(4, 8));
    jpnlContent.add(jlfldFatherCellID, cc.xyw(6, 8, 2));

    jpnlContent.add(jlblEnterpriseCellObs, cc.xy(2, 10));
    jpnlContent.add(jspObs, cc.xyw(4, 10, 4, CellConstraints.FILL, CellConstraints.FILL));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);
  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	EnterpriseCellDao daoEC = (EnterpriseCellDao)dataSource.getCurrentRecord();
	daoEC.enterpriseID.setLinkKey();
	daoEC.enterpriseID.setValueAsObject(MenuSingleton.getEnterprise());
	dataSource.reLinkAttrs();
      }
      super.doInsert();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }
}
