package pt.inescporto.siasoft.tools;

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
public class TableColumn implements Serializable {
  private String columnName = null;
  private Integer dataType = null;
  private String typeName = null;
  private Integer columnSize = null;
  private Integer decimalDigits = null;
  private Integer nullable = null;
  private Integer ordinalPosition = null;

  public TableColumn() {
  }

  public TableColumn(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnName() {
    return columnName;
  }

  public Integer getColumnSize() {
    return columnSize;
  }

  public Integer getDataType() {
    return dataType;
  }

  public Integer getDecimalDigits() {
    return decimalDigits;
  }

  public Integer getNullable() {
    return nullable;
  }

  public Integer getOrdinalPosition() {
    return ordinalPosition;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public void setOrdinalPosition(Integer ordinalPosition) {
    this.ordinalPosition = ordinalPosition;
  }

  public void setNullable(Integer nullable) {
    this.nullable = nullable;
  }

  public void setDecimalDigits(Integer decimalDigits) {
    this.decimalDigits = decimalDigits;
  }

  public void setDataType(Integer dataType) {
    this.dataType = dataType;
  }

  public void setColumnSize(Integer columnSize) {
    this.columnSize = columnSize;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String toString() {
    return columnName + "[" + typeName + "]";
  }
}
