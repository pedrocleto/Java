package pt.inescporto.template.client.util;

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
public class DataSourceDB {
  private String name = null;
  private Object attrs = null;

  public DataSourceDB() {
  }

  public Object getAttrs() {
    return attrs;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAttrs(Object attrs) {
    this.attrs = attrs;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append(name);

    return buffer.toString();
  }
}
