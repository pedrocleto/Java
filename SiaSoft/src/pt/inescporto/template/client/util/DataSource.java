package pt.inescporto.template.client.util;

import java.util.Collection;
import java.util.Vector;
import pt.inescporto.template.client.event.DataSourceListener;

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
public interface DataSource {
  /** *******************************************************
   ** Listeners utilities
   ** *******************************************************
   */
  /**
   * Register some interest in this datasource
   *
   * @param l DataSourceListener
   */
  public void addDatasourceListener(DataSourceListener l);

  /**
   * Remove interest in this datasource
   *
   * @param l DataSourceListener
   */
  public void removeDatasourceListener(DataSourceListener l);

  /** *******************************************************
   ** Utility methods
   ** *******************************************************
   */

  /**
   * Start handling datasource for the master case!
   *
   * @throws DataSourceException
   */
  public void initialize() throws DataSourceException;

  /**
   *
   * @param order int
   * @throws DataSourceException
   */
  public void setSortOrder(int order) throws DataSourceException;

  public void setLinkCondition(String linkConditionStmt, Vector binds) throws DataSourceException;

  public void addDataSourceLink(DSRelation dsRelation) throws DataSourceException;

  public String getDataSourceName() throws DataSourceException;

  public DataSource getDataSourceByName(String dsName) throws DataSourceException;

  public void refresh() throws DataSourceException;

  public void refresh(Object exception) throws DataSourceException;

  public Collection listAll() throws DataSourceException;

  public Collection getLastFind() throws DataSourceException;

  /**
   * Make a fresh new instance of the attributes representing this datasource.
   * Every item flagged been a LNKKEY is preserved. In a near future this will
   * be replace with PKKEY | FKKEY value.
   *
   * @throws DataSourceException
   */
  public void cleanUpAttrs() throws DataSourceException;

  public Object getAttrs() throws DataSourceException;

  public void setAttrs(Object attrs) throws DataSourceException;

  public Object getCurrentRecord() throws DataSourceException;

  public void reLinkAttrs() throws DataSourceException;

  /** *******************************************************
   ** Operational methods
   ** *******************************************************
   */
  public void insert() throws DataSourceException;

  public void update() throws DataSourceException;

  public void delete() throws DataSourceException;

  /** *******************************************************
   ** Navegational methods
   ** *******************************************************
   */
  public boolean first() throws DataSourceException;

  public boolean next() throws DataSourceException;

  public boolean previous() throws DataSourceException;

  public boolean last() throws DataSourceException;

  /** *******************************************************
   ** Finding data methods
   ** *******************************************************
   */
  public void findByPK(Object record) throws DataSourceException;

  public void findExact() throws DataSourceException;

  public void find() throws DataSourceException;
}
