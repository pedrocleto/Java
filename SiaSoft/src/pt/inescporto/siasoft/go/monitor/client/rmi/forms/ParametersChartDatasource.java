package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import pt.inescporto.template.client.event.DataSourceListener;
import javax.naming.NamingException;
import pt.inescporto.template.client.util.DataSource;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
import java.util.Collection;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorParameterDao;
import java.util.ResourceBundle;
import java.util.Vector;
import pt.inescporto.template.client.util.LinkConditionNode;
import pt.inescporto.siasoft.go.monitor.ejb.session.MonitorParameters;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.elements.TplObject;

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
public class ParametersChartDatasource implements DataSourceListener, DataSource {
  DataSourceListener chartListener = null;
  MonitorParameters mon = null;

  String linkCondition = null;
  Vector<TplObject> binds = null;

  String monitorPlanID = null;
  String parametersID = null;
  Timestamp monitorDate = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  public ParametersChartDatasource() {
    try {
      mon = (MonitorParameters)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorParameters");
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
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
    this.linkCondition = linkConditionStmt;
    this.binds = binds;
  }

  public void addDataSourceLink(DSRelation dsRelation) throws DataSourceException {

  }

  public String getDataSourceName() throws DataSourceException {
    return "ParametersChartDS";
  }

  public DataSource getDataSourceByName(String dsName) throws DataSourceException {
    if (dsName.equals("ParametersChartDS"))
      return this;
    else
      return null;
  }

  public void refresh() throws DataSourceException {

  }

  public void refresh(Object exception) throws DataSourceException {

  }

  public Collection listAll() throws DataSourceException {
    Collection<MonitorParameterDao> monParameterList = new Vector<MonitorParameterDao>();

    try {
      mon.setLinkCondition(linkCondition, binds);
      monParameterList = mon.listAll();
    }
    catch (UserException ex1) {
      ex1.printStackTrace();
    }
    catch (RemoteException ex1) {
      ex1.printStackTrace();
    }
    catch (RowNotFoundException ex) {
      ex.printStackTrace();
    }

    return monParameterList;
  }

  public Collection getLastFind() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public void cleanUpAttrs() throws DataSourceException {
    throw new DataSourceException("Not Implemented!");
  }

  public Object getAttrs() throws DataSourceException {
    return new MonitorParameterDao();
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

  public void tmplInitialize(TemplateEvent e) {

  }

  public void tmplRefresh(TemplateEvent e) {

    LinkConditionNode lcn = e.getLinkConditionNode("" + this);

    if (lcn != null) {
      try {
//	System.out.println("linkCondition <" + lcn.getStatement() + ">");
	if (lcn.getBinds() != null) {
          for (TplObject field : (Vector<TplObject>)lcn.getBinds()) {
//            System.out.print("<" + field.getField() + ", " + field.getValueAsObject().toString() + ">");
          }
//          System.out.println("");
	}

	setLinkCondition(lcn.getStatement(), lcn.getBinds());
        if (lcn.getStatement() != null && lcn.getBinds() != null && !((TplObject)lcn.getBinds().elementAt(0)).getValueAsObject().equals(""))
          chartListener.tmplRefresh(e);
      }
      catch (DataSourceException ex) {
	ex.printStackTrace();
      }
      catch(Exception ex){

      }
    /*catch (TmplException ex) {
	//ex.printStackTrace();
      }*/
    }
}

  public void tmplSave(TemplateEvent e) {

  }

  public void tmplLink(TemplateEvent e) {

  }

}
