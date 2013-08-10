package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.MenuSingleton;
import javax.naming.NamingException;
import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import java.util.Vector;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalDao;
import javax.swing.JTabbedPane;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalHasLinksDao;
import pt.inescporto.siasoft.go.pga.ejb.session.GoalHasLinks;

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
public class GoalsLinkPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");
  JTabbedPane goaltab=new JTabbedPane();
  private Hashtable<String, Vector> regulatoryList = null;

  //
  TmplJTextField jtfldGoalDescription = new TmplJTextField() {
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

  TmplLookupButton jlbtnRegulatory = new TmplLookupButton();
  TmplJTextField jtfldRegulatory = new TmplJTextField();
  TmplLookupField jlfldRegulatory = new TmplLookupField();

  public GoalsLinkPane(DataSource datasource, FW_ComponentListener fwCListener,JTabbedPane goaltab) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.goaltab=goaltab;
    initialize();

    datasource.addDatasourceListener(jtfldGoalDescription);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);
    datasource.addDatasourceListener(jlbtnRegulatory);
    datasource.addDatasourceListener(jtfldRegulatory);
    datasource.addDatasourceListener(jlfldRegulatory);

    fwCListener.addFWComponentListener(jtfldGoalDescription);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
    fwCListener.addFWComponentListener(jlbtnRegulatory);
    fwCListener.addFWComponentListener(jtfldRegulatory);
    fwCListener.addFWComponentListener(jlfldRegulatory);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldGoalDescription.setField("goalDescription");

    jtfldEnterprise.setLabel(res.getString("goal.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");
    if (!MenuSingleton.isSupplier())
      jlbtnEnterprise.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise()+ "'");
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setText(res.getString("goal.label.enterprise"));
    jlbtnEnterprise.setTitle("Lista de Empresas");
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    jtfldRegulatory.setLabel(res.getString("goal.label.regulatory"));
    jtfldRegulatory.setField("fkRegID");
    jlbtnRegulatory.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlbtnRegulatory.setText(res.getString("goal.label.regulatory"));
    jlbtnRegulatory.setTitle("Lista de Diplomas");
    jlbtnRegulatory.setDefaultFill(jtfldRegulatory);
    jlfldRegulatory.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
    jlfldRegulatory.setDefaultRefField(jtfldRegulatory);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4, 6}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jtfldGoalDescription, cc.xyw(2, 2, 5));

    content.add(jlbtnEnterprise, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterprise, cc.xy(4, 4));
    content.add(jlfldEnterprise, cc.xy(6, 4));

    content.add(jlbtnRegulatory, cc.xy(2, 6, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldRegulatory, cc.xy(4, 6));
    content.add(jlfldRegulatory, cc.xy(6, 6));

    add(content, BorderLayout.NORTH);

    GoalHasLinksPane mon = new GoalHasLinksPane();
    GoalHasLinksTab jGoalsLinksPane = new GoalHasLinksTab(mon);

    GoalDao dt = null;
    String monitorPlanID = null;
    String enterpriseID = null;
    try {
      dt = (GoalDao)datasource.getCurrentRecord();
      monitorPlanID = dt.goalID.getValue();
      enterpriseID = dt.enterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    GoalHasLinksDao goalHasLinksDao = null;
    try {
      GoalHasLinks monitorLinks = (GoalHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.pga.ejb.session.GoalHasLinks");
      try {
	goalHasLinksDao = monitorLinks.getLinks(monitorPlanID, enterpriseID);
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
      regulatoryList = (Hashtable<String, Vector>)goalHasLinksDao.regulatoryList;
      if (regulatoryList.containsKey(monitorPlanID)) {
	Vector v = goalHasLinksDao.regulatoryList.get(monitorPlanID);
	jGoalsLinksPane.refresh(v);
      }
    }
    catch(Exception ex){

    }

    goaltab.removeAll();
    goaltab.add(jGoalsLinksPane.getGoalHasLinksPane(), 0);
    goaltab.setTitleAt(0, res.getString("label.activelinks"));
    goaltab.setPreferredSize(new Dimension(500, 250));
    add(goaltab, BorderLayout.SOUTH);

  }
}
