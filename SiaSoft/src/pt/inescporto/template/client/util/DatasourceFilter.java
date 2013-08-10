package pt.inescporto.template.client.util;

import java.util.Vector;

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
public interface DatasourceFilter {
  public Object getBasicRecord() throws DataSourceException;

  public Vector getData() throws DataSourceException;

  public void setSelection(Object attrs) throws DataSourceException;
}
