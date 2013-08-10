package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.naming.NamingException;

import pt.inescporto.siasoft.go.auditor.ejb.session.Audit;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionsDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.PreventiveActionsDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.CorrectiveActionsDao;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.dao.UserException;
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
public class AuditForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  DataSourceRMI master = null;
  JTabbedPane tabbedPane = null;
  AuditDefinitionPane auditDef=null;

  public AuditForm() {
    master = new DataSourceRMI("Audit");
    master.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");

    DataSourceRMI dsAuditTeam = new DataSourceRMI("AuditTeam");
    dsAuditTeam.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditTeam");

    DSRelation dsrAATeam = new DSRelation("AuditAuditTeam");
    dsrAATeam.setMaster(master);
    dsrAATeam.setDetail(dsAuditTeam);
    dsrAATeam.addKey(new RelationKey("auditPlanID", "auditPlanID"));
    dsrAATeam.addKey(new RelationKey("auditDate", "auditDate"));

    try {
      master.addDataSourceLink(dsrAATeam);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsAuditActions = new DataSourceRMI("AuditActions");
    dsAuditActions.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditActions");

    DSRelation dsrAAuditActions = new DSRelation("AuditAuditActions");
    dsrAAuditActions.setMaster(master);
    dsrAAuditActions.setDetail(dsAuditActions);
    dsrAAuditActions.addKey(new RelationKey("auditPlanID", "auditPlanID"));
    dsrAAuditActions.addKey(new RelationKey("auditDate", "auditDate"));

    try {
      master.addDataSourceLink(dsrAAuditActions);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsPreventiveActions = new DataSourceRMI("PreventiveActions");
    dsPreventiveActions.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.PreventiveActions");

    DSRelation dsrAuditActPrev = new DSRelation("AuditActionPreventiveAction");
    dsrAuditActPrev.setMaster(dsAuditActions);
    dsrAuditActPrev.setDetail(dsPreventiveActions);
    dsrAuditActPrev.addKey(new RelationKey("auditPlanID", "auditPlanID"));
    dsrAuditActPrev.addKey(new RelationKey("auditDate", "auditDate"));
    dsrAuditActPrev.addKey(new RelationKey("auditActionID", "auditActionID"));

    try {
      dsAuditActions.addDataSourceLink(dsrAuditActPrev);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsCorrectiveActions = new DataSourceRMI("CorrectiveActions");
    dsCorrectiveActions.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.CorrectiveActions");

    DSRelation dsrAuditActCor = new DSRelation("AuditActionCorrectiveAction");
    dsrAuditActCor.setMaster(dsAuditActions);
    dsrAuditActCor.setDetail(dsCorrectiveActions);
    dsrAuditActCor.addKey(new RelationKey("auditPlanID", "auditPlanID"));
    dsrAuditActCor.addKey(new RelationKey("auditDate", "auditDate"));
    dsrAuditActCor.addKey(new RelationKey("auditActionID", "auditActionID"));

    try {
      dsAuditActions.addDataSourceLink(dsrAuditActCor);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    setDataSource(master);
    setAccessPermitionIdentifier("AUDIT_M");

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    allHeaders = new String[] {res.getString("auditDefinition.label.auditPlan"), res.getString("auditDefinition.label.auditDate"), res.getString("auditDefinition.label.auditEndDate"),res.getString("auditDefinition.label.auditRealStartDate"),res.getString("auditDefinition.label.auditRealEndDate"),res.getString("auditDefinition.label.auditResult"),res.getString("auditDefinition.label.user"),res.getString("auditDefinition.label.auditEntity")};
    start();
  }

  private void jbInit() throws Exception {
    tabbedPane = new JTabbedPane();
    auditDef=new AuditDefinitionPane(dataSource, this);
    tabbedPane.addTab(res.getString("label.definition"),auditDef);
    add(tabbedPane, BorderLayout.CENTER);
  }

  protected boolean insertBefore() {
    try{
     if (auditDef.jtfldAuditDate.getSelectedDate(true).after(auditDef.jtfldAuditEndDate.getSelectedDate(true)) || auditDef.jtfldAuditDate.getSelectedDate(true).equals(auditDef.jtfldAuditEndDate.getSelectedDate(true)) ) {
	  JFrame frame = new JFrame();
	  JOptionPane.showMessageDialog(frame,
                                        res.getString("label.invalidDate"),
					res.getString("label.search"),
					JOptionPane.WARNING_MESSAGE);

	  return false;
	}
   	if (auditDef.jtfldAuditRealStartDate.getSelectedDate(true).after(auditDef.jtfldAuditRealEndDate.getSelectedDate(true)) || auditDef.jtfldAuditRealStartDate.getSelectedDate(true).equals(auditDef.jtfldAuditRealEndDate.getSelectedDate(true))) {
	  JFrame frame = new JFrame();
	  JOptionPane.showMessageDialog(frame,
                                        res.getString("label.invalidDate"),
					res.getString("label.search"),
					JOptionPane.WARNING_MESSAGE);

	  return false;
	}
    }
    catch(NullPointerException ex)
    {
    }
    return true;
  }

  protected boolean updateBefore() {
    try{
     if (auditDef.jtfldAuditDate.getSelectedDate(true).after(auditDef.jtfldAuditEndDate.getSelectedDate(true)) || auditDef.jtfldAuditDate.getSelectedDate(true).equals(auditDef.jtfldAuditEndDate.getSelectedDate(true)) ) {
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame,
                                        res.getString("label.invalidDate"),
                                        res.getString("label.search"),
                                        JOptionPane.WARNING_MESSAGE);

          return false;
        }
        if (auditDef.jtfldAuditRealStartDate.getSelectedDate(true).after(auditDef.jtfldAuditRealEndDate.getSelectedDate(true)) || auditDef.jtfldAuditRealStartDate.getSelectedDate(true).equals(auditDef.jtfldAuditRealEndDate.getSelectedDate(true))) {
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame,
                                        res.getString("label.invalidDate"),
                                        res.getString("label.search"),
                                        JOptionPane.WARNING_MESSAGE);

          return false;
        }
    }
    catch(NullPointerException ex)
    {
    }
    return true;
  }



  protected void insertAfter() {
    Object[] options = {res.getString("label.yesOption"), res.getString("label.noOption")};
    JFrame frame = new JFrame();
    int option = JOptionPane.showOptionDialog(frame,
                                              res.getString("label.copyActions"),
                                              res.getString("audit.label.title"),
                                              JOptionPane.YES_NO_OPTION,
                                              JOptionPane.QUESTION_MESSAGE,
                                              null,
                                              options,
					      options[1]);
    if (option == JOptionPane.YES_OPTION) {
      try {
	AuditDao auditDao = (AuditDao)dataSource.getCurrentRecord();
	Audit audit = null;
	try {
	  audit = (Audit)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");
	  Timestamp auditDate = auditDao.auditDate.getValue();
	  String auditPlanID = auditDao.auditPlanID.getValue();
	  audit.copyActionsFromAuditPlan(auditPlanID, auditDate);
	  dataSource.refresh();
	}
	catch (UserException ex) {
	  ex.printStackTrace();
	}
	catch (RemoteException ex) {
	  ex.printStackTrace();
	}

      }
      catch (DataSourceException ex) {
	ex.printStackTrace();
      }
      catch (NamingException ex) {
	ex.printStackTrace();
      }
    }
    if (option == JOptionPane.NO_OPTION) {
      frame.dispose();
    }
  }

  public boolean setPrimaryKey(Object keyValue) {
    try {

      if (keyValue != null && keyValue instanceof PreventiveActionsDao) {

        // first find Audit
	setCursor(new Cursor(Cursor.WAIT_CURSOR));
        PreventiveActionsDao paDao = (PreventiveActionsDao) keyValue;

	AuditDao attrs = (AuditDao)dataSource.getAttrs();
	attrs.auditPlanID.setValue(paDao.auditPlanID.getValue());
        attrs.auditDate.setValue(paDao.auditDate.getValue());
        dataSource.findByPK(attrs);

        // second find AuditActions
        DataSource dsAuditActions = dataSource.getDataSourceByName("AuditActions");
        AuditActionsDao aaDao = new AuditActionsDao();
        aaDao.auditPlanID.setValue(paDao.auditPlanID.getValue());
        aaDao.auditDate.setValue(paDao.auditDate.getValue());
        aaDao.auditActionID.setValue(paDao.auditActionID.getValue());
        dsAuditActions.findByPK(aaDao);

        // third find PreventiveActions
       DataSource dsPreventiveActions = dataSource.getDataSourceByName("PreventiveActions");
       dsPreventiveActions.findByPK(paDao);

      }
      if (keyValue != null && keyValue instanceof CorrectiveActionsDao) {

          // first find Audit
         setCursor(new Cursor(Cursor.WAIT_CURSOR));
         CorrectiveActionsDao caDao = (CorrectiveActionsDao) keyValue;

         AuditDao attrs = (AuditDao)dataSource.getAttrs();
         attrs.auditPlanID.setValue(caDao.auditPlanID.getValue());
         attrs.auditDate.setValue(caDao.auditDate.getValue());
         dataSource.findByPK(attrs);

         // second find AuditActions
         DataSource dsAuditActions = dataSource.getDataSourceByName("AuditActions");
         AuditActionsDao aaDao = new AuditActionsDao();
         aaDao.auditPlanID.setValue(caDao.auditPlanID.getValue());
         aaDao.auditDate.setValue(caDao.auditDate.getValue());
         aaDao.auditActionID.setValue(caDao.auditActionID.getValue());
         dsAuditActions.findByPK(aaDao);

         // third find CorrectiveActions
        DataSource dsCorrectiveActions = dataSource.getDataSourceByName("CorrectiveActions");
        dsCorrectiveActions.findByPK(caDao);
      }
    }
    catch (DataSourceException ex) {
      return false;
    }
    finally {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    return true;
  }
}
