package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.go.pga.ejb.session.ObjActions;
import pt.inescporto.siasoft.go.pga.ejb.dao.ObjActionsDao;
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
public class ObjectiveActionsSearchDataSource implements DataSource {
  DataSourceListener tableModelListener = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  boolean nullValues = true;
  boolean update = true;

  String enterpriseID = null;
  String goalID = null;
  String objectiveID = null;
  Timestamp planStartDate = null;
  Timestamp planEndDate = null;
  String userID = null;
  String state = null;

  public ObjectiveActionsSearchDataSource() {
  }

  public void setNullValues(boolean value) {
    nullValues = value;
  }

  public void setUp(boolean value) {
   update = value;
   if (tableModelListener != null)
     tableModelListener.tmplRefresh(new TemplateEvent(this));
 }

  public void setParameters(String enterpriseID, String goalID, String objectiveID, Timestamp planStartDate, Timestamp planEndDate, String userID, String state) {
    this.enterpriseID = enterpriseID;
    this.goalID = goalID;
    this.objectiveID = objectiveID;
    this.planStartDate = planStartDate;
    this.planEndDate = planEndDate;
    this.userID = userID;
    this.state = state;

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
    return "ObjectiveActionsDS";
  }

  public DataSource getDataSourceByName(String dsName) throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void refresh() throws DataSourceException {

  }

  public void refresh(Object exception) throws DataSourceException {

  }

  public Collection listAll() throws DataSourceException {
    Collection<ObjActionsDao> objActionsList = new Vector<ObjActionsDao>();

    if (!nullValues && update) {
      try {
	ObjActions objActions = (ObjActions)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.pga.ejb.session.ObjActions");
	objActionsList = objActions.listAll(enterpriseID, goalID, objectiveID, planStartDate, planEndDate, userID, state);
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

      if (objActionsList == null || objActionsList.isEmpty()) {
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

    return objActionsList;
  }

  public Collection getLastFind() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void cleanUpAttrs() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public Object getAttrs() throws DataSourceException {
    return new ObjActionsDao();
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
