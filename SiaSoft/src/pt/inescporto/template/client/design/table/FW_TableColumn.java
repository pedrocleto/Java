package pt.inescporto.template.client.design.table;

import javax.swing.table.TableColumn;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

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
public class FW_TableColumn extends TableColumn {
  private String fieldName = null;

  public FW_TableColumn(String fieldName) {
    super(0);
    this.fieldName = fieldName;
  }

  public FW_TableColumn(int modelIndex, String fieldName) {
    super(modelIndex, 75, new TmplStringRenderer(), null);
    this.fieldName = fieldName;
  }

  public FW_TableColumn(int modelIndex, int width, String fieldName) {
    super(modelIndex, width, new TmplStringRenderer(), null);
    this.fieldName = fieldName;
  }

  public FW_TableColumn(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor, String fieldName) {
    super(modelIndex, width, cellRenderer, cellEditor);
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String toString() {
    return getHeaderValue().toString();
  }
}
