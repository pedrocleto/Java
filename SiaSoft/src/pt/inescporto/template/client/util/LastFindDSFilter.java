package pt.inescporto.template.client.util;

import java.util.Vector;
import java.util.Collection;

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
public class LastFindDSFilter implements DatasourceFilter {
  public DataSource datasource = null;

  public LastFindDSFilter(DataSource datasource) {
    this.datasource = datasource;
  }

  public Object getBasicRecord() throws DataSourceException {
    return datasource.getAttrs();
  }

  public Vector getData() throws DataSourceException {
    Collection all = datasource.getLastFind();

    return all == null ? null : new Vector(all);
  }

  public void setSelection(Object attrs) throws DataSourceException {
    datasource.findByPK(attrs);
  }
}
