package pt.inescporto.siasoft.proc.client.rmi.editor;

import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_JPanelNav;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JDialog;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityRegulatoryDao;
import pt.inescporto.template.client.rmi.MenuSingleton;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.design.TmplJLabel;
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
 * @author not attributable
 * @version 0.1
 */

public class MasqPanel extends FW_JPanelNav {

   static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButtonSave jbtnSave = new TmplJButtonSave();
  String activityID = null;
  String fkEnterpriseID = null;
  DataSourceRMI master = null;

  JPanel jpnlMenuNav = new JPanel();

  TmplLookupButton jlbtnReg = new TmplLookupButton();
  TmplJTextField jtfldReg = new TmplJTextField();
  TmplLookupField jlfldReg = new TmplLookupField();

  TmplJLabel jlblActivity = new TmplJLabel();
  TmplJTextField jtfldActivity = new TmplJTextField();

  public MasqPanel(String activityID, String fkEnterpriseID) {
    this.activityID = activityID;
    this.fkEnterpriseID = fkEnterpriseID;
    master = new DataSourceRMI("ActivityRegulatory");
    master.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityRegulatory");

    setDataSource(master);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    addFWComponentListener(jlbtnReg);
    addFWComponentListener(jtfldReg);
    addFWComponentListener(jlfldReg);
    addFWComponentListener(jtfldActivity);

    addFWComponentListener(jbtnSave);
    addFWComponentListener(jbtnCancel);

    dataSource.addDatasourceListener(jlbtnReg);
    dataSource.addDatasourceListener(jtfldReg);
    dataSource.addDatasourceListener(jlfldReg);
    dataSource.addDatasourceListener(jtfldActivity);

    dataSource.addDatasourceListener(jbtnSave);
    dataSource.addDatasourceListener(jbtnCancel);

    start();
    doInsert();
  }
  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    add(jpnlMenuNav, java.awt.BorderLayout.SOUTH);

    jtfldReg.setLabel(res.getString("masq.label.reg"));
    jtfldReg.setField("fkRegID");
    jlbtnReg.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlbtnReg.setText(res.getString("masq.label.reg"));
    jlbtnReg.setTitle(res.getString("masq.label.list"));
    jlbtnReg.setDefaultFill(jtfldReg);
    jlfldReg.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlfldReg.setDefaultRefField(jtfldReg);

    jlblActivity.setText(res.getString("masq.label.activity"));
    jtfldActivity.setField("fkActivityID");
    jtfldActivity.setHolder(jlblActivity);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 90dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    CellConstraints cc = new CellConstraints();

    content.add(jlbtnReg, cc.xy(2, 2));
    content.add(jtfldReg, cc.xy(4, 2));
    content.add(jlfldReg, cc.xyw(6, 2, 3));

    content.add(jlblActivity, cc.xy(2,4));
    content.add(jtfldActivity, cc.xy(4,4));

    jpnlMenuNav.add(jbtnSave, null);
    jpnlMenuNav.add(jbtnCancel, null);

    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);

    add(content, BorderLayout.CENTER);
    setBorder(BorderFactory.createTitledBorder(res.getString("masqPopup.label.add")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
  }

  protected void doInsert() {
  try {
    ActivityRegulatoryDao daoAR = (ActivityRegulatoryDao)dataSource.getCurrentRecord();
    daoAR.fkActivityID.setLinkKey();
    daoAR.fkActivityID.setValueAsObject(activityID);
    dataSource.reLinkAttrs();
    super.doInsert();
  }
  catch (DataSourceException ex) {
    ex.printStackTrace();
  }
}
protected boolean doSave() {
  if (super.doSave()) {
    Container parent = getParent();
    while (parent != null && !(parent instanceof JDialog))
      parent = parent.getParent();
    ((JDialog)parent).dispose();
  }
  return true;
 }
 protected void doCancel() {
  Container parent = getParent();
  while (parent != null && !(parent instanceof JDialog))
    parent = parent.getParent();
  ((JDialog)parent).dispose();
}

}
