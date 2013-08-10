package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pt.inescporto.siasoft.go.auditor.ejb.session.AuditActionType;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionTypeDao;

import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
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
public class AuditActionsSearchDataSource implements DataSource{

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  DataSourceListener tableModelListener = null;
  boolean nullValues = true;
  boolean update = true;
  String enterpriseID = null;
  Timestamp startDate = null;
  Timestamp endDate = null;
  String state = null;
  String auditPlanID = null;
  Timestamp auditDate = null;
  String auditActionID = null;
  String type = null;
  String userID = null;

  public AuditActionsSearchDataSource() {
  }

  public void setNullValues(boolean value) {
    nullValues = value;
  }

  public void setUp(boolean value) {
    update = value;
    if (tableModelListener != null)
      tableModelListener.tmplRefresh(new TemplateEvent(this));
  }

  public void setParameters(String enterpriseID, Timestamp startDate, Timestamp endDate, String state, String auditPlanID, Timestamp auditDate, String auditActionID, String type, String userID) {
    this.enterpriseID = enterpriseID;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = state;
    this.auditPlanID = auditPlanID;
    this.auditDate = auditDate;
    this.auditActionID = auditActionID;
    this.type = type;
    this.userID = userID;


    if (tableModelListener != null)
      tableModelListener.tmplRefresh(new TemplateEvent(this));
  }

  /** *******************************************************
   ** Listeners utilities
   ** *******************************************************
   */
  public void addDatasourceListener(DataSourceListener l) {
    tableModelListener = l;
  }

  public void removeDatasourceListener(DataSourceListener l) {
    if (tableModelListener == l)
      tableModelListener = null;
  }

  /** *******************************************************
   ** Utility methods
   ** *******************************************************
   */
  public void initialize() throws DataSourceException {

  }

  public void setSortOrder(int order) throws DataSourceException {

  }

  public void setLinkCondition(String linkConditionStmt, Vector binds) throws DataSourceException {

  }

  public void addDataSourceLink(DSRelation dsRelation) throws DataSourceException {

  }

  public String getDataSourceName() throws DataSourceException {
    return "AuditActionTypeDS";
  }

  public DataSource getDataSourceByName(String dsName) throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void refresh() throws DataSourceException {

  }

  public void refresh(Object exception) throws DataSourceException {

  }

  public Collection listAll() throws DataSourceException {
    Collection<AuditActionTypeDao> auditActionTypeList = new Vector<AuditActionTypeDao>();

    if (!nullValues && update) {
      try {
	AuditActionType auditActionType = (AuditActionType)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.auditor.ejb.session.AuditActionType");
	auditActionTypeList = auditActionType.listAll(enterpriseID, startDate, endDate, state, auditPlanID, auditDate, auditActionID, type, userID);
      }

      catch (UserException ex) {
	ex.printStackTrace();
      }
      catch (RemoteException ex) {
	ex.printStackTrace();
      }

      catch (NamingException ex) {
	ex.printStackTrace();
      }

      if (auditActionTypeList == null || auditActionTypeList.isEmpty()) {
	JFrame frame = new JFrame();
	JOptionPane.showMessageDialog(frame,
				      res.getString("label.notFound"),
				      res.getString("label.warning"),
				      JOptionPane.WARNING_MESSAGE);
      }
    }
    else {
      update = true;
      nullValues = true;
    }

    return auditActionTypeList;
  }

  public Collection getLastFind() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void cleanUpAttrs() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public Object getAttrs() throws DataSourceException {
    return new AuditActionTypeDao();
  }

  public void setAttrs(Object attrs) throws DataSourceException {
  }

  public Object getCurrentRecord() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void reLinkAttrs() throws DataSourceException {

  }

  /** *******************************************************
   ** Operational methods
   ** *******************************************************
   */
  public void insert() throws DataSourceException {

  }

  public void update() throws DataSourceException {

  }

  public void delete() throws DataSourceException {

  }

  /** *******************************************************
   ** Navegational methods
   ** *******************************************************
   */
  public boolean first() throws DataSourceException {
    return false;
  }

  public boolean next() throws DataSourceException {
    return false;
  }

  public boolean previous() throws DataSourceException {
    return false;
  }

  public boolean last() throws DataSourceException {
    return false;
  }

  /** *******************************************************
   ** Finding data methods
   ** *******************************************************
   */
  public void findByPK(Object record) throws DataSourceException {

  }

  public void findExact() throws DataSourceException {

  }

  public void find() throws DataSourceException {

  }

}
