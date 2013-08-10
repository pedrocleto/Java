package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import javax.swing.JPanel;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksDao;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationDao;
import pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituationHasLinks;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.swing.JTabbedPane;
import java.util.Vector;
import java.util.Hashtable;
import java.awt.Dimension;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */

public class EmergencySitLink extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane emergSitTab = new JTabbedPane(JTabbedPane.BOTTOM);
  private Hashtable<String, Vector> eaList = null;

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.coe.client.rmi.forms.FormResources");

  TmplJTextField jtfldEmergSitDescription = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  public EmergencySitLink(DataSource datasource, FW_ComponentListener fwCListener, JTabbedPane montab) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.emergSitTab = montab;

    initialize();

    datasource.addDatasourceListener(jtfldEmergSitDescription);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);

    fwCListener.addFWComponentListener(jtfldEmergSitDescription);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldEmergSitDescription.setField("emergSitDescription");

    jtfldEnterprise.setLabel(res.getString("emergSitlink.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");
    if (!MenuSingleton.isSupplier())
      jlbtnEnterprise.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setText(res.getString("emergSitlink.label.enterprise"));
    jlbtnEnterprise.setTitle(res.getString("emergSitlink.label.enterpriselist"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu,pref, 2dlu,pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jtfldEmergSitDescription, cc.xyw(2, 2, 5));

    content.add(jlbtnEnterprise, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterprise, cc.xy(4, 4));
    content.add(jlfldEnterprise, cc.xy(6, 4));

    add(content, BorderLayout.NORTH);

    EmergencySitHasLinksPane mon = new EmergencySitHasLinksPane();
    EmergencySitHasLinksTab jEmergSitLinksPane = new EmergencySitHasLinksTab(mon);

    EmergencySituationDao dt = null;
    String emergSitID = null;
    String enterpriseID = null;
    try {
      dt = (EmergencySituationDao)datasource.getCurrentRecord();
      emergSitID = dt.emergSitID.getValue();
      enterpriseID = dt.fkEnterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    EmergencySituationHasLinksDao emergencySituationHasLinksDao = null;
    try {
      EmergencySituationHasLinks emergencySitLinks = (EmergencySituationHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituationHasLinks");
      try {
	emergencySituationHasLinksDao = emergencySitLinks.getLinks(emergSitID, enterpriseID);
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
    try {
      eaList = (Hashtable<String, Vector>)emergencySituationHasLinksDao.eaList;
      if (eaList.containsKey(emergSitID)) {
	Vector v = emergencySituationHasLinksDao.eaList.get(emergSitID);
	jEmergSitLinksPane.refresh(v);
      }
    }
    catch (Exception ex) {
    }

    emergSitTab.removeAll();
    emergSitTab.add(jEmergSitLinksPane.getEmergSitHasLinksPane(), 0);
    emergSitTab.setTitleAt(0, res.getString("label.activelinks"));
    emergSitTab.setPreferredSize(new Dimension(500, 250));
    add(emergSitTab, BorderLayout.CENTER);
  }
}

