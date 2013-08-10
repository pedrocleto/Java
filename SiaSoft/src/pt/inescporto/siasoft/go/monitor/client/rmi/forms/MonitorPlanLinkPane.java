package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JPanel;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.rmi.MenuSingleton;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.util.Vector;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanDao;
import pt.inescporto.template.client.util.DataSourceException;
import java.util.Hashtable;
import javax.naming.NamingException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.go.monitor.ejb.session.MonitorHasLinks;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorHasLinksDao;

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
public class MonitorPlanLinkPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane montab=new JTabbedPane(JTabbedPane.BOTTOM);
  private Hashtable<String, Vector> eaList = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  //
  TmplJTextField jtfldMonitorPlanDescription = new TmplJTextField() {
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

  public MonitorPlanLinkPane(DataSource datasource, FW_ComponentListener fwCListener, JTabbedPane montab) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.montab = montab;

    initialize();

    datasource.addDatasourceListener(jtfldMonitorPlanDescription);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);

    fwCListener.addFWComponentListener(jtfldMonitorPlanDescription);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldMonitorPlanDescription.setField("monitorPlanDescription");

    jtfldEnterprise.setLabel(res.getString("monitorPlan.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");
    if (!MenuSingleton.isSupplier())
      jlbtnEnterprise.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setText(res.getString("monitorPlan.label.enterprise"));
    jlbtnEnterprise.setTitle(res.getString("monitorPlan.label.enterpriselist"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jtfldMonitorPlanDescription, cc.xyw(2, 2, 5));

    content.add(jlbtnEnterprise, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterprise, cc.xy(4, 4));
    content.add(jlfldEnterprise, cc.xy(6, 4));

    add(content, BorderLayout.NORTH);

    MonitorHasLinksPane mon = new MonitorHasLinksPane();
    MonitorHasLinksTab jMonitorLinksPane = new MonitorHasLinksTab(mon);

    MonitorPlanDao dt = null;
    String monitorPlanID = null;
    String enterpriseID = null;
    try {
      dt = (MonitorPlanDao)datasource.getCurrentRecord();
      monitorPlanID = dt.monitorPlanID.getValue();
      enterpriseID = dt.fkEnterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    MonitorHasLinksDao monitorHasLinksDao = null;
    try {
      MonitorHasLinks monitorLinks = (MonitorHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorHasLinks");
      try {
	monitorHasLinksDao = monitorLinks.getLinks(monitorPlanID, enterpriseID);
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
    eaList = (Hashtable<String, Vector>)monitorHasLinksDao.eaList;
    try{
      if (eaList.containsKey(monitorPlanID)) {
	Vector v = monitorHasLinksDao.eaList.get(monitorPlanID);
	jMonitorLinksPane.refresh(v);
      }
    }
    catch(NullPointerException ex){

    }
    montab.removeAll();
    montab.add(jMonitorLinksPane.getMonitorHasLinksPane(),0);
    montab.setTitleAt(0,res.getString("label.activelinks"));
    montab.setPreferredSize(new Dimension(500,250));
    add(montab, BorderLayout.CENTER);

  }
}
