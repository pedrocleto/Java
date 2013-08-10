package pt.inescporto.siasoft.tools;

import java.util.*;
import java.io.Serializable;

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
public class TableIndex implements Serializable {
  public static int NOTDEF = -1;
  public static int UNIQUE = 1;
  public static int NONUNIQUE = 2;

  private String indexName = null;
  private int indexType = NOTDEF;
  private Vector<IndexField> indexFields = new Vector<IndexField>();

  public TableIndex() {
  }

  public TableIndex(String indexName, int indexType) {
    this.indexName = indexName;
    this.indexType = indexType;
  }

  public String getIndexName() {
    return indexName;
  }

  public int getIndexType() {
    return indexType;
  }

  public void addIndexField(IndexField indexField) {
    indexFields.add(indexField);
  }

  public boolean removeIndexField(IndexField indexField) {
    return indexFields.remove(indexField);
  }

  public IndexField removeIndexField(int position) {
    return indexFields.remove(position);
  }

  public Iterator<IndexField> getIndexFieldList() {
    return indexFields.iterator();
  }

  public String toString() {
    return indexName + " [" + indexType + "]";
  }
}
