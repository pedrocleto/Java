package pt.inescporto.template.client.design.table;

import java.io.*;

import javax.swing.table.*;

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
public class FW_ColumnNode implements Serializable {
  private Integer index = null;
  private String headerValue = null;
  private String fieldName = null;
  private TableCellRenderer tcr = null;
  private TableCellEditor tce = null;

  public FW_ColumnNode() {
  }

  public FW_ColumnNode(String headerValue, String fieldName, TableCellRenderer tcr, TableCellEditor tce) {
    this(null, headerValue, fieldName, tcr, tce);
  }

  public FW_ColumnNode(Integer index, String headerValue, String fieldName, TableCellRenderer tcr, TableCellEditor tce) {
    this.index = index;
    this.headerValue = headerValue;
    this.fieldName = fieldName;
    this.tcr = tcr;
    this.tce = tce;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Integer getIndex() {
    return index;
  }

  public String getHeaderValue() {
    return headerValue;
  }

  public TableCellEditor getTableCellEditor() {
    return tce;
  }

  public TableCellRenderer getTableCellRender() {
    return tcr;
  }

  public void setTableCellRender(TableCellRenderer tcr) {
    this.tcr = tcr;
  }

  public void setTableCellEditor(TableCellEditor tce) {
    this.tce = tce;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public void setHeaderValue(String headerValue) {
    this.headerValue = headerValue;
  }
}
