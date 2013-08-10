package pt.inescporto.template.client.design.table;

import java.util.Vector;
import java.util.Iterator;

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
public class FW_ColumnManager {
  private Vector<FW_ColumnNode> columnList = new Vector<FW_ColumnNode>();

  public FW_ColumnManager() {
  }

  public void addColumnNode(FW_ColumnNode node) {
    columnList.add(node);
  }

  public Iterator<FW_ColumnNode> getColumnNodeList() {
    return columnList.iterator();
  }
}
