package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pt.inescporto.siasoft.go.auditor.ejb.session.Audit;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditStatedDao;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.event.TemplateEvent;
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
public class AuditSearchDataSource implements DataSource {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  DataSourceListener tableModelListener = null;
  boolean nullValues = true;
  boolean update = true;
  String enterpriseID = null;
  Timestamp startDate = null;
  Timestamp endDate = null;
  String state = null;
  String enterpriseCellID = null;
  String activityID = null;
  String auditPlanID = null;
  String auditType = null;

  public AuditSearchDataSource() {
  }

  public void setNullValues(boolean value) {
    nullValues = value;
  }

  public void setUp(boolean value) {
    update = value;
    if (tableModelListener != null)
      tableModelListener.tmplRefresh(new TemplateEvent(this));

  }

  public void setParameters(String enterpriseID, Timestamp startDate, Timestamp endDate, String state, String enterpriseCellID, String activityID, String auditPlanID, String auditType) {
    this.enterpriseID = enterpriseID;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = state;
    this.enterpriseCellID = enterpriseCellID;
    this.activityID = activityID;
    this.auditPlanID = auditPlanID;
    this.auditType = auditType;

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
    return "AuditSearchDS";
  }

  public DataSource getDataSourceByName(String dsName) throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void refresh() throws DataSourceException {

  }

  public void refresh(Object exception) throws DataSourceException {

  }

  public Collection listAll() throws DataSourceException {
    Collection<AuditStatedDao> auditStatedList = new Vector<AuditStatedDao>();

    if (!nullValues && update) {
      try {
	Audit audit = (Audit)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.auditor.ejb.session.Audit");
	auditStatedList = audit.getFilteredAudit(enterpriseID,enterpriseCellID, activityID, auditPlanID, state, startDate, endDate, auditType);
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

      if (auditStatedList == null || auditStatedList.isEmpty()) {
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

    return auditStatedList;
  }

  public Collection getLastFind() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void cleanUpAttrs() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public Object getAttrs() throws DataSourceException {
    return new AuditStatedDao();
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
