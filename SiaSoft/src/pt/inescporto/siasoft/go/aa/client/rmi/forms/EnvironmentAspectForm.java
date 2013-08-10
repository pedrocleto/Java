package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.awt.Cursor;
import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JTabbedPane;

import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspSIAMethodDao;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspOtherMatrixMethodDao;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspOtherMethodDao;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvAspSIAMethod;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvAspOtherMethod;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvAspOtherMatrixMethod;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.go.aa.ejb.session.EAHasLinks;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksDao;
import java.util.Hashtable;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspOtherSignificantDao;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvAspOtherSignificant;
import pt.inescporto.template.dao.*;
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
 * @author jap
 * @version 0.1
 */
public class EnvironmentAspectForm extends FW_InternalFrame {
  JTabbedPane jTabbedPane = new JTabbedPane();
  JTabbedPane envAspTab = new JTabbedPane();
  DataSourceRMI master = null;
  private Hashtable<String, Vector> coeList = null;
  private Hashtable<String, Vector> monitorList = null;


  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  public EnvironmentAspectForm() {
    master = new DataSourceRMI("EnvironmentAspect");
    master.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");

    setDataSource(master);
    setAccessPermitionIdentifier("ENVIROMENTAL_ASPECT");
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    allHeaders = new String[] {res.getString("envAsp.label.code"), res.getString("envAsp.label.name"), res.getString("eaevaluation.label.desc"),res.getString("eaTabbedPane.label.class"),res.getString("eaLinkTabbedPane.label.activity"),res.getString("eaTabbedPane.label.user"),res.getString("eaTabbedPane.label.state"),res.getString("envAsp.label.functionality"),res.getString("envAsp.label.applicabitity"),res.getString("label.significance"),res.getString("label.significanceMethod")};
    start();
  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, java.awt.BorderLayout.CENTER);
    jTabbedPane.addTab(res.getString("eaevaluation.label.desc"), new EAtabbedPaneForm(dataSource, this));
    jTabbedPane.addTab(res.getString("eaevaluation.label.significance"), new EAStabbedPane(dataSource, this));
    jTabbedPane.addTab(res.getString("eaevaluation.label.links"), new EALinkTabbedPane(dataSource, this,envAspTab));
  }

  public boolean setPrimaryKey(Object keyValue) {
    try {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      EnvironmentAspectDao attrs = (EnvironmentAspectDao)dataSource.getAttrs();
      attrs.envAspectID.setValue((String)keyValue);
      dataSource.findByPK(attrs);
    }
    catch (DataSourceException ex) {
      return false;
    }
    finally {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    return true;
  }

  protected void updateAfter() {
    try {
      EnvironmentAspectDao daoEC = (EnvironmentAspectDao)dataSource.getCurrentRecord();
      Vector binds = new Vector();
      daoEC.significance.setValue(null);
      binds.add(new TplString(daoEC.envAspectID.getValue()));
      EnvironmentAspect envasp = null;
      try {
	envasp = (EnvironmentAspect)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
      }
      catch (NamingException ex1) {
      }
      //Sia method
    try {
      if (daoEC.significanceMethod.getValue().equals("S")) {
	EnvAspSIAMethodDao daoSM = new EnvAspSIAMethodDao();
	EnvAspSIAMethod envaspSIA = null;
	try {
	  envaspSIA = (EnvAspSIAMethod)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspSIAMethod");

	  try {
	    daoSM.envAspectID.setValue(daoEC.envAspectID.getValue());

	    envaspSIA.findByPrimaryKey(daoSM);

	    daoSM = envaspSIA.getData();
	  }
	  catch (UserException ex5) {
	  }
	  catch (RowNotFoundException ex5) {
	  }

	  catch (RemoteException ex4) {
	  }
	}
	catch (NamingException ex3) {

	}
	try {
	  if (daoSM.envSignificanceDesc.getValue().equals(res.getString("label.Significant"))) {
	    daoEC.significance.setValue(true);
	  }
	  else
	    daoEC.significance.setValue(false);
	}
	catch (Exception ex) {}
      }
      //custom method
      if (daoEC.significanceMethod.getValue().equals("C")) {
	EnvAspOtherMethodDao daoOM = new EnvAspOtherMethodDao();
	EnvAspOtherMethod envaspOM = null;
	try {
	  envaspOM = (EnvAspOtherMethod)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspOtherMethod");
	  try {
	    daoOM.envAspectID.setValue(daoEC.envAspectID.getValue());

	    envaspOM.findByPrimaryKey(daoOM);

	    daoOM = envaspOM.getData();
	  }
	  catch (UserException ex5) {
	  }
	  catch (RowNotFoundException ex5) {
	  }

	  catch (RemoteException ex4) {
	  }
	}
	catch (NamingException ex3) {

	}
	try {
	  if (daoOM.envSignificanceDesc.getValue().equals(res.getString("label.Significant"))) {
	    daoEC.significance.setValue(true);
	  }
	  else
	    daoEC.significance.setValue(false);
	}
	catch (Exception ex) {}
      }
    }
    catch (Exception ex) {}

      if (daoEC.otherSignificance.getValue().equals(true)) {

	EnvAspOtherSignificantDao daoOS = new EnvAspOtherSignificantDao();
	EnvAspOtherSignificant envaspOS = null;
	try {
	  envaspOS = (EnvAspOtherSignificant)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspOtherSignificant");
	  try {
	    daoOS.envAspectID.setValue(daoEC.envAspectID.getValue());

	    envaspOS.findByPrimaryKey(daoOS);

	    daoOS = envaspOS.getData();
	  }
	  catch (UserException ex5) {
	  }
	  catch (RowNotFoundException ex5) {
	  }

	  catch (RemoteException ex4) {
	  }
	}
	catch (NamingException ex3) {

	}
        try{
	  if (daoOS.envtype.getValue().equals("V")) {
	    if (daoOS.significance.getValue().equals("S")) {
	      daoEC.significance.setValue(true);
	    }
	    else
	      if (daoOS.significance.getValue().equals("NS")) {
		daoEC.significance.setValue(false);
	      }
	  }
	}
        catch(Exception ex){}
      }
      try {
	if (daoEC.significance.getValue().equals(true)) {
	  daoEC.significanceDesc.setValue(res.getString("label.Significant"));
	}
	if (daoEC.significance.getValue().equals(false)) {
	  daoEC.significanceDesc.setValue(res.getString("label.NonSignificant"));
	}
      }
      catch (Exception ex) {
	daoEC.significanceDesc.setValue("");
      }

      try {
	envasp.update(daoEC);
      }
      catch (UserException ex2) {
      }
      catch (RemoteException ex2) {
      }
      dataSource.refresh();

    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    catch (NullPointerException ex) {
    }
    updateTable();
  }

 protected void doCancel() {
    if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_FIND) {
      try {
	if (dataSource.first()) {
	  // set mode to VIEW
	  setMode(TmplFormModes.MODE_SELECT);
	}
	else {
	  doInsert();
	}
      }
      catch (DataSourceException dsex) {
	showErrorMessage(dsex.getMessage());
      }

    }
    else {
      setMode(TmplFormModes.MODE_SELECT);
      refresh();
    }
    updateTable();
  }

  protected void insertAfter() {
    updateTable();
  }

  protected void doNext() {
    try {
      dataSource.next();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.LAST_RECORD)
        showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.last"));
      else
        showErrorMessage(dsex.getException().getMessage());
    }
    updateTable();
  }

  protected void doPrevious() {
    try {
      dataSource.previous();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.FIRST_RECORD)
        showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.first"));
      else
        showErrorMessage(dsex.getException().getMessage());
    }
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
    try{
      Vector<EAHasLinksNode> result = new Vector<EAHasLinksNode>();
      try {

        result.addAll(envAspHasLinksDao.pgaList.subList(0, envAspHasLinksDao.pgaList.size()));
        result.addAll(envAspHasLinksDao.monitorList.subList(0, envAspHasLinksDao.monitorList.size()));
        result.addAll(envAspHasLinksDao.coeList.subList(0, envAspHasLinksDao.coeList.size()));
        jDocLinksPane.refresh(result);
      }
      catch (NullPointerException ex) {
        ex.printStackTrace();
      }

      envAspTab.removeAll();
      envAspTab.add(jDocLinksPane.getEAHasLinksPane(), 0);
      envAspTab.setTitleAt(0, res.getString("label.activelinks"));
    }
    catch(Exception ex){
    }

  }

}
