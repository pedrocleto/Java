package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.BorderLayout;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorDao;
import pt.inescporto.siasoft.go.monitor.ejb.session.Monitor;
import java.sql.Timestamp;
import pt.inescporto.template.dao.*;
import java.rmi.*;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import javax.naming.*;

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

public class MonitorForm extends FW_InternalFrame {
  JTabbedPane jTabbedPane = new JTabbedPane();
  DataSourceRMI master = null;
  MonitorDefinition monitorDef = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  public MonitorForm() {
    master = new DataSourceRMI("Monitor");
    master.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.Monitor");

    DataSourceRMI dsParameters = new DataSourceRMI("MonitorParameters");
    dsParameters.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorParameters");

    DSRelation prRelation = new DSRelation("MonitorMonitorParameters");
    prRelation.setMaster(master);
    prRelation.setDetail(dsParameters);
    prRelation.addKey(new RelationKey("monitorPlanID", "monitorPlanID"));
    prRelation.addKey(new RelationKey("monitorDate", "monitorDate"));

    try {
      master.addDataSourceLink(prRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    MonitorParamChartDatasource dsChart = new MonitorParamChartDatasource();

    DSRelation chRelation = new DSRelation("MonitorParametersMParamChart");
    chRelation.setMaster(dsParameters);
    chRelation.setDetail(dsChart);
    chRelation.addKey(new RelationKey("monitorPlanID", "monitorPlanID"));
    chRelation.addKey(new RelationKey("parameterID", "parameterID"));

    try {
      dsParameters.addDataSourceLink(chRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }


    setDataSource(master);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    allHeaders = new String[] {res.getString("monitorDef.label.monitorPlanID"), res.getString("monitorDef.label.monitorDate"), res.getString("monitorDef.label.monitorEndDate"),res.getString("monitorDef.label.monitorRealStartDate"),res.getString("monitorDef.label.monitorRealEndDate"),res.getString("monitorDef.label.monitorResult"),res.getString("monitorDef.label.monitorUser")};
    start();

  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, BorderLayout.CENTER);
    monitorDef = new MonitorDefinition(dataSource, this);
    jTabbedPane.add(res.getString("label.definition"), monitorDef);
  }

  protected boolean insertBefore() {
    try {
      if (monitorDef.jtfldMonitorDate.getSelectedDate(true).after(monitorDef.jtfldMonitorEndDate.getSelectedDate(true)) || monitorDef.jtfldMonitorDate.getSelectedDate(true).equals(monitorDef.jtfldMonitorEndDate.getSelectedDate(true)) ) {
	JFrame frame = new JFrame();
	JOptionPane.showMessageDialog(frame,
				      res.getString("label.invalidDate"),
				      res.getString("label.search"),
				      JOptionPane.WARNING_MESSAGE);

	return false;
      }
      if (monitorDef.jtfldMonitorRealStartDate.getSelectedDate(true).after(monitorDef.jtfldMonitorRealEndDate.getSelectedDate(true)) || monitorDef.jtfldMonitorRealStartDate.getSelectedDate(true).equals(monitorDef.jtfldMonitorRealEndDate.getSelectedDate(true))) {
	JFrame frame = new JFrame();
	JOptionPane.showMessageDialog(frame,
				      res.getString("label.invalidDate"),
				      res.getString("label.search"),
				      JOptionPane.WARNING_MESSAGE);

	return false;
      }
    }
    catch (NullPointerException ex) {
    }
    return true;
  }

  protected boolean updateBefore() {
    try {
      if (monitorDef.jtfldMonitorDate.getSelectedDate(true).after(monitorDef.jtfldMonitorEndDate.getSelectedDate(true))|| monitorDef.jtfldMonitorDate.getSelectedDate(true).equals(monitorDef.jtfldMonitorEndDate.getSelectedDate(true)) ) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                                      res.getString("label.invalidDate"),
                                      res.getString("label.search"),
                                      JOptionPane.WARNING_MESSAGE);

        return false;
      }
      if (monitorDef.jtfldMonitorRealStartDate.getSelectedDate(true).after(monitorDef.jtfldMonitorRealEndDate.getSelectedDate(true)) || monitorDef.jtfldMonitorRealStartDate.getSelectedDate(true).equals(monitorDef.jtfldMonitorRealEndDate.getSelectedDate(true))) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                                      res.getString("label.invalidDate"),
                                      res.getString("label.search"),
                                      JOptionPane.WARNING_MESSAGE);

        return false;
      }
    }
    catch (NullPointerException ex) {
    }
    return true;
  }


  protected void insertAfter() {

    //Option Pane Used to copy Parameters
    Object[] options = {res.getString("copyParam.label.yes"), res.getString("copyParam.label.no")};
    JFrame frame = new JFrame();
    int n = JOptionPane.showOptionDialog(frame,
					 res.getString("copyParam.label.question"),
					 res.getString("copyParam.label.title"),
					 JOptionPane.YES_NO_OPTION,
					 JOptionPane.QUESTION_MESSAGE,
					 null,
					 options,
					 options[1]);
    if (n == JOptionPane.YES_OPTION) {
      try {
	MonitorDao daoEC = (MonitorDao)dataSource.getCurrentRecord();
	Monitor mon = null;
	try {
	  mon = (Monitor)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.monitor.ejb.session.Monitor");
	  mon.copyParametersFromMonitorPlan(daoEC.monitorPlanID.getValue(), (Timestamp)daoEC.monitorDate.getValue());
	  dataSource.refresh();
	}
	catch (NamingException ex2) {
	  ex2.printStackTrace();
	}
	catch (UserException ex1) {
	  ex1.printStackTrace();
	}
	catch (RemoteException ex1) {
	  ex1.printStackTrace();
	}
      }
      catch (DataSourceException ex) {
	ex.printStackTrace();
      }

    }
    else
      if (n == JOptionPane.NO_OPTION) {
	frame.dispose();
      }
  }

}
