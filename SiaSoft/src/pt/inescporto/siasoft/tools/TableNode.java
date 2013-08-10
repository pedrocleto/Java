package pt.inescporto.siasoft.tools;

import java.io.*;
import java.util.*;

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
public class TableNode implements Serializable {
  private StringBuffer selectAllStmt = null;
  private StringBuffer selectWhereStmt = null;
  private StringBuffer insertStmt = null;
  private StringBuffer updateStmt = null;
  private String tableName = null;
  private Vector<TableColumn> columns = new Vector<TableColumn>();
  private TableIndex primaryKey = null;
  private Vector<TableIndex> indexes = new Vector<TableIndex>();
  private Vector<TableConstraint> importedConstraint = new Vector<TableConstraint>();
  private Vector<TableConstraint> exportedConstraint = new Vector<TableConstraint>();

  public TableNode() {
  }

  public String toString() {
    return tableName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void addColumn(TableColumn column) {
    columns.add(column);
  }

  public boolean removeColumn(TableColumn column) {
    return columns.remove(column);
  }

  public TableColumn removeColumn(int position) {
    return columns.remove(position);
  }

  public Iterator<TableColumn> getColumnlist() {
    return columns.iterator();
  }

  public int getColumnCount() {
    return columns.size();
  }

  public void setPrimaryKey(TableIndex primaryKey) {
    this.primaryKey = primaryKey;
  }

  public void setSelectAllStmt(StringBuffer selectAllStmt) {
    this.selectAllStmt = selectAllStmt;
  }

  public void setSelectWhereStmt(StringBuffer selectWhereStmt) {
    this.selectWhereStmt = selectWhereStmt;
  }

  public void setUpdateStmt(StringBuffer updateStmt) {
    this.updateStmt = updateStmt;
  }

  public void setInsertStmt(StringBuffer insertStmt) {
    this.insertStmt = insertStmt;
  }

  public TableIndex getPrimaryKey() {
    return primaryKey;
  }

  public StringBuffer getSelectAllStmt() {
    return selectAllStmt;
  }

  public StringBuffer getSelectWhereStmt() {
    return selectWhereStmt;
  }

  public StringBuffer getUpdateStmt() {
    return updateStmt;
  }

  public StringBuffer getInsertStmt() {
    return insertStmt;
  }

  public void addIndex(TableIndex index) {
    indexes.add(index);
  }

  public boolean removeIndex(TableIndex index) {
    return indexes.remove(index);
  }

  public TableIndex removeIndex(int position) {
    return indexes.remove(position);
  }

  public Iterator<TableIndex> getIndexlist() {
    return indexes.iterator();
  }

  public void addImportedConstraint(TableConstraint constraint) {
    importedConstraint.add(constraint);
  }

  public boolean removeImportedConstraint(TableConstraint constraint) {
    return importedConstraint.remove(constraint);
  }

  public TableConstraint removeImportedConstraint(int position) {
    return importedConstraint.remove(position);
  }

  public Iterator<TableConstraint> getImportedConstraintlist() {
    return importedConstraint.iterator();
  }

  public void addExportedConstraint(TableConstraint constraint) {
    exportedConstraint.add(constraint);
  }

  public boolean removeExportedConstraint(TableConstraint constraint) {
    return exportedConstraint.remove(constraint);
  }

  public TableConstraint removeExportedConstraint(int position) {
    return exportedConstraint.remove(position);
  }

  public Iterator<TableConstraint> getExportedConstraintlist() {
    return exportedConstraint.iterator();
  }
}
