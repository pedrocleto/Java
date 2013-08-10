package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;

import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.rmi.FW_JPanelNav;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Hashtable;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksDao;
import pt.inescporto.siasoft.go.aa.ejb.session.EAHasLinks;

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
public class EnvironmentalAspectPane extends FW_JPanelNav {
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButtonSave jbtnSave = new TmplJButtonSave();
  JPanel jpnlMenuNav = new JPanel();

  JTabbedPane jTabbedPane = new JTabbedPane();
  JTabbedPane envAspTab = new JTabbedPane();
  DataSourceRMI master = null;
  private Hashtable<String, Vector> coeList = null;

  String activityID = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  public EnvironmentalAspectPane(String activityID) {
    this.activityID = activityID;
    master = new DataSourceRMI("EnvironmentalAspect");
    master.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");

    setDataSource(master);
//    perms = new Integer(15);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    addFWComponentListener(jbtnSave);
    addFWComponentListener(jbtnCancel);

    dataSource.addDatasourceListener(jbtnSave);
    dataSource.addDatasourceListener(jbtnCancel);

    start();

    doInsert();
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    add(jpnlMenuNav, java.awt.BorderLayout.SOUTH);

    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, BorderLayout.CENTER);
    jTabbedPane.addTab(res.getString("envAspectPane.label.desc"), new EAtabbedPaneForm(dataSource, this));
    jTabbedPane.addTab(res.getString("envAspectPane.label.significance"), new EAStabbedPane(dataSource, this));
    jTabbedPane.addTab(res.getString("envAspectPane.label.links"), new EALinkTabbedPane(dataSource, this, envAspTab));

    jpnlMenuNav.add(jbtnSave, null);
    jpnlMenuNav.add(jbtnCancel, null);

    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);

    setBorder(BorderFactory.createTitledBorder(res.getString("envAspectPane.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
  }

  protected void doInsert() {
    try {
      EnvironmentAspectDao daoEA = (EnvironmentAspectDao)dataSource.getCurrentRecord();
      daoEA.activityID.setLinkKey();
      daoEA.activityID.setValueAsObject(activityID);
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
    //updateTable();
  }

/*  protected void insertAfter() {
    updateTable();
  }

  protected void updateAfter() {
    updateTable();
  }

  protected void refresh() {
    try {
      dataSource.refresh();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    updateTable();
  }

    protected void updateTable() {
    EAHasLinksPane mon = new EAHasLinksPane();
    EAHasLinksTab jDocLinksPane = new EAHasLinksTab(mon);

    EnvironmentAspectDao dt = null;
    String envAspID = null;
    String enterpriseID = null;
    try {
      dt = (EnvironmentAspectDao)dataSource.getCurrentRecord();
      envAspID = dt.envAspectID.getValue();
      if (!MenuSingleton.isSupplier())
        enterpriseID = MenuSingleton.getEnterprise();
    }
    catch (DataSourceException ex) {

    }
    EAHasLinksDao envAspHasLinksDao = null;
    try {
      EAHasLinks envAspLinks = (EAHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.aa.ejb.session.EAHasLinks");
      try {
        envAspHasLinksDao = envAspLinks.getLinks(envAspID, enterpriseID);
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
    coeList = (Hashtable<String, Vector>)envAspHasLinksDao.coeList;
    monitorList = (Hashtable<String, Vector>)envAspHasLinksDao.monitorList;
    Vector<Vector> result = new Vector<Vector>(2);
    if (coeList.containsKey(envAspID) && monitorList.containsKey(envAspID)) {
      result.add(0, envAspHasLinksDao.coeList.get(envAspID));
      result.add(1, envAspHasLinksDao.monitorList.get(envAspID));
      jDocLinksPane.refresh(result);
    }
    else {
      if (coeList.containsKey(envAspID)) {
        Vector v = envAspHasLinksDao.coeList.get(envAspID);
        jDocLinksPane.refreshOne(v);
      }

      if (monitorList.containsKey(envAspID)) {
        Vector v = envAspHasLinksDao.monitorList.get(envAspID);
        jDocLinksPane.refreshOne(v);
      }
    }

    envAspTab.removeAll();
    envAspTab.add(jDocLinksPane.getEAHasLinksPane(), 0);
    envAspTab.setTitleAt(0, res.getString("label.activelinks"));

  }*/
}
