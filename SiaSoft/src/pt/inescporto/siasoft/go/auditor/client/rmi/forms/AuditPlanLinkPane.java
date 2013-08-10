package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.rmi.MenuSingleton;

import javax.naming.NamingException;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import java.util.Vector;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditHasLinksDao;
import pt.inescporto.siasoft.go.auditor.ejb.session.AuditHasLinks;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanDao;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

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
public class AuditPlanLinkPane extends JPanel {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplJTextField jtfldAuditPlanScope = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();

  TmplLookupButton jlbtnEnterpriseCell = new TmplLookupButton();
  TmplJTextField jtfldEnterpriseCell = new TmplJTextField();
   TmplJTextField jtfldEnterpriseCellID = new TmplJTextField();
  TmplLookupField jlfldEnterpriseCell = new TmplLookupField();

  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;
  JTabbedPane auditTab = new JTabbedPane(JTabbedPane.BOTTOM);
  private Hashtable<String, Vector> departList = null;

  public AuditPlanLinkPane(DataSource datasource, FW_ComponentListener fwCListener,JTabbedPane auditTab) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.auditTab=auditTab;
    initialize();

    datasource.addDatasourceListener(jtfldAuditPlanScope);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);
    datasource.addDatasourceListener(jlbtnEnterpriseCell);
    datasource.addDatasourceListener(jtfldEnterpriseCell);
    datasource.addDatasourceListener(jtfldEnterpriseCellID);
    datasource.addDatasourceListener(jlfldEnterpriseCell);

    fwCListener.addFWComponentListener(jtfldAuditPlanScope);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
    fwCListener.addFWComponentListener(jlbtnEnterpriseCell);
    fwCListener.addFWComponentListener(jtfldEnterpriseCell);
    fwCListener.addFWComponentListener(jtfldEnterpriseCellID);
    fwCListener.addFWComponentListener(jlfldEnterpriseCell);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldAuditPlanScope.setField("auditPlanScope");
    jtfldEnterprise.setLabel(res.getString("auditPlanLink.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
     if (!MenuSingleton.isSupplier())
       jlbtnEnterprise.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterprise.setText(res.getString("auditPlanLink.label.enterprise"));
    jlbtnEnterprise.setTitle(res.getString("auditPlanLink.label.enterpriseList"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    jtfldEnterpriseCell.setLabel(res.getString("auditPlanLink.label.enterpriseCell"));
    jtfldEnterpriseCellID.setField("fkEnterpriseCellID");
    jlbtnEnterpriseCell.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    if (!MenuSingleton.isSupplier())
      jlbtnEnterpriseCell.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterpriseCell.setText(res.getString("auditPlanLink.label.enterpriseCell"));
    jlbtnEnterpriseCell.setTitle(res.getString("auditPlanLink.label.enterpriseCellList"));
    jlbtnEnterpriseCell.setDefaultFill(jtfldEnterpriseCell);
    JTextField[] txt = new JTextField[2];
    txt[0] = jtfldEnterpriseCell;
    txt[1] = jtfldEnterpriseCellID;
    jlbtnEnterpriseCell.setFillList(txt);

    jlfldEnterpriseCell.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");
    jlfldEnterpriseCell.setRefFieldList(txt);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4, 6}});
    CellConstraints cc = new CellConstraints();

    content.add(jtfldAuditPlanScope, cc.xyw(2, 2, 5));

    content.add(jlbtnEnterprise, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterprise, cc.xy(4, 4));
    content.add(jlfldEnterprise, cc.xy(6, 4));

    content.add(jlbtnEnterpriseCell, cc.xy(2, 6, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterpriseCellID, cc.xy(4, 6));
    content.add(jlfldEnterpriseCell, cc.xy(6, 6));

    add(content, BorderLayout.NORTH);

    AuditHasLinksPane mon = new AuditHasLinksPane();
    AuditHasLinksTab jAuditorLinksPane = new AuditHasLinksTab(mon);

    AuditPlanDao dt = null;
    String auditPlanID = null;
    String enterpriseID = null;
    try {
      dt = (AuditPlanDao)datasource.getCurrentRecord();
      auditPlanID = dt.auditPlanID.getValue();
      enterpriseID = dt.fkEnterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    AuditHasLinksDao auditHasLinksDao = null;
    try {
      AuditHasLinks auditLinks = (AuditHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.auditor.ejb.session.AuditHasLinks");
      try {
	auditHasLinksDao = auditLinks.getLinks(auditPlanID, enterpriseID);
      }
      catch (UserException ex) {
	ex.printStackTrace();
      }
      catch (RemoteException ex) {
	ex.printStackTrace();
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    try{
      departList = (Hashtable<String, Vector>)auditHasLinksDao.departList;
      if (departList.containsKey(auditPlanID)) {
	Vector v = auditHasLinksDao.departList.get(auditPlanID);
	jAuditorLinksPane.refresh(v);
      }
    }
    catch (Exception ex) {

    }
    auditTab.removeAll();
    auditTab.add(jAuditorLinksPane.getAuditHasLinksPane(), 0);
    auditTab.setTitleAt(0, res.getString("label.activelinks"));
    auditTab.setPreferredSize(new Dimension(500, 250));
    add(auditTab, BorderLayout.SOUTH);

  }
}
