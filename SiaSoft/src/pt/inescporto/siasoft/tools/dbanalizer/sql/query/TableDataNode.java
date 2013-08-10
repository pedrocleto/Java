package pt.inescporto.siasoft.tools.dbanalizer.sql.query;

import java.io.Serializable;
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
public class TableDataNode implements Serializable {
  private String tableName = null;
  private Vector<Vector> data2insert  = null;
  private Vector<Vector> data2update  = null;
  private Vector<Vector> data2delete  = null;

  public TableDataNode(String tableName) {
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  public synchronized void addRow2Insert(Vector row) {
    if (data2insert == null)
      data2insert = new Vector<Vector>();
    data2insert.add(row);
  }

  public synchronized void addRow2Update(Vector row) {
    if (data2update == null)
      data2update = new Vector<Vector>();
    data2update.add(row);
  }

  public synchronized void addRow2Delete(Vector row) {
    if (data2delete == null)
      data2delete = new Vector<Vector>();
    data2delete.add(row);
  }

  public Vector<Vector> getAllRows4insert() {
    return data2insert;
  }

  public Vector<Vector> getAllRows4update() {
    return data2update;
  }

  public Vector<Vector> getAllRows4delete() {
    return data2delete;
  }

  public boolean isSomething2do() {
    return data2insert != null || data2update != null || data2delete != null;
  }
}
