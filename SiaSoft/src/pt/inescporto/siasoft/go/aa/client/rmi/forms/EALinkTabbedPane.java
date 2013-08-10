package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.rmi.MenuSingleton;
import javax.naming.NamingException;
import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import java.util.Vector;
import javax.swing.JTabbedPane;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksDao;
import pt.inescporto.siasoft.go.aa.ejb.session.EAHasLinks;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksNode;

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
public class EALinkTabbedPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  //
  TmplJLabel jlblEnvAspID = new TmplJLabel();
  TmplJTextField jtfldEnvAspID = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };
  TmplJLabel jlblEnvAspName = new TmplJLabel();
  TmplJTextField jtfldEnvAspName = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  JTabbedPane envAspTab = new JTabbedPane();
  private Hashtable<String, Vector> coeList = null;
  private Hashtable<String, Vector> monitorList = null;

  public EALinkTabbedPane(DataSource datasource, FW_ComponentListener fwCListener, JTabbedPane envAspTab) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.envAspTab = envAspTab;
    initialize();

    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jtfldEnvAspName);

    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jtfldEnvAspName);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblEnvAspID.setText(res.getString("eaLinkTabbedPane.label.code"));
    jtfldEnvAspID.setField("envAspID");
    jtfldEnvAspID.setHolder(jlblEnvAspID);

    jlblEnvAspName.setText(res.getString("eaLinkTabbedPane.label.name"));
    jtfldEnvAspName.setField("envAspName");
    jtfldEnvAspName.setHolder(jlblEnvAspName);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu, 10dlu, left:35dlu, 4dlu, 70dlu, 5px",
					   "5px, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
//    formLayout.setRowGroups(new int[][] {{2}});
    CellConstraints cc = new CellConstraints();

    content.add(jlblEnvAspName, cc.xy(2, 2));
    content.add(jtfldEnvAspName, cc.xyw(4, 2, 3));
    content.add(jlblEnvAspID, cc.xy(8, 2));
    content.add(jtfldEnvAspID, cc.xy(10, 2));

    add(content, BorderLayout.NORTH);

    EAHasLinksPane mon = new EAHasLinksPane();
    EAHasLinksTab jEaLinksPane = new EAHasLinksTab(mon);

    EnvironmentAspectDao dt = new EnvironmentAspectDao();
    String envAspID = null;
    String enterpriseID = null;
    try {
      dt = (EnvironmentAspectDao)datasource.getCurrentRecord();
      envAspID = dt.envAspectID.getValue();
      if (!MenuSingleton.isSupplier())
	enterpriseID = MenuSingleton.getEnterprise();
    }
    catch (DataSourceException ex) {

    }
    EAHasLinksDao envAspHasLinksDao = new EAHasLinksDao();
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

    /** populate links table */
    Vector<EAHasLinksNode> result = new Vector<EAHasLinksNode>();
    try {

      result.addAll(envAspHasLinksDao.pgaList.subList(0, envAspHasLinksDao.pgaList.size()));
      result.addAll(envAspHasLinksDao.monitorList.subList(0, envAspHasLinksDao.monitorList.size()));
      result.addAll(envAspHasLinksDao.coeList.subList(0, envAspHasLinksDao.coeList.size()));
      jEaLinksPane.refresh(result);
    }
    catch (NullPointerException ex) {
      ex.printStackTrace();
    }

    envAspTab.removeAll();
    envAspTab.add(jEaLinksPane.getEAHasLinksPane(), 0);
    envAspTab.setTitleAt(0, res.getString("label.activelinks"));
    envAspTab.setPreferredSize(new Dimension(500, 250));
    add(envAspTab, BorderLayout.SOUTH);
  }
}
