package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import javax.swing.JOptionPane;
import pt.inescporto.template.client.event.DataSourceListener;
import javax.naming.NamingException;
import pt.inescporto.template.client.util.DataSource;
import javax.swing.JFrame;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.siasoft.go.monitor.ejb.session.Monitor;
import pt.inescporto.template.client.util.DSRelation;
import java.util.Collection;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorStatedDao;
import java.util.ResourceBundle;
import java.util.Vector;

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
public class MonitorGanttDatasource  implements DataSource {
  DataSourceListener chartListener = null;
  boolean nullValues = true;
  boolean update = true;
  String enterpriseID = null;
  String envAspID = null;
  String activityID = null;
  String state = null;
  String userID = null;
  String monPlanID = null;
  Timestamp startDate = null;
  Timestamp endDate = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  public MonitorGanttDatasource() {
  }

  public void setUP(boolean value) {
    update = value;
   if (chartListener != null)
      chartListener.tmplRefresh(new TemplateEvent(this));
  }

  public void setNullValues(boolean value) {
    nullValues = value;
  }

  public void setParameters(String enterpriseID, Timestamp startDate, Timestamp endDate,
                            String state, String userID, String monPlanID,
                            String envAspID, String activityID) {
    this.enterpriseID = enterpriseID;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = state;
    this.userID = userID;
    this.monPlanID = monPlanID;
    this.envAspID = envAspID;
    this.activityID = activityID;

    if (chartListener != null)
      chartListener.tmplRefresh(new TemplateEvent(this));
  }

  /** *******************************************************
   ** Listeners utilities
   ** *******************************************************
   */
  public void addDatasourceListener(DataSourceListener l) {
    chartListener = l;
  }

  public void removeDatasourceListener(DataSourceListener l) {
    if (chartListener == l)
      chartListener = null;
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
    return "MonitorGanttDS";
  }

  public DataSource getDataSourceByName(String dsName) throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void refresh() throws DataSourceException {

  }

  public void refresh(Object exception) throws DataSourceException {

  }

  public Collection listAll() throws DataSourceException {
    Collection<MonitorStatedDao> monStatedList = new Vector<MonitorStatedDao>();

    if (!nullValues && update) {
      try {
        Monitor mon = (Monitor)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.monitor.ejb.session.Monitor");
        monStatedList = mon.getFilteredMonitor(enterpriseID, startDate, endDate, state, userID, monPlanID, envAspID, activityID);
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
      if (monStatedList.isEmpty()) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                                      res.getString("label.notFound"),
                                      res.getString("label.warning"),
                                      JOptionPane.WARNING_MESSAGE);
      }
    }
    else {
      //monStatedList = new Vector<MonitorStatedDao>();
      update = true;
      nullValues = true;
    }
    return monStatedList;
  }


  public Collection getLastFind() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void cleanUpAttrs() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public Object getAttrs() throws DataSourceException {
    return new MonitorStatedDao();
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
