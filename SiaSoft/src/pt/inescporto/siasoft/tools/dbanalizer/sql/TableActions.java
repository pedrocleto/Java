package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.io.*;
import java.util.*;

import pt.inescporto.siasoft.tools.*;

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
public class TableActions implements Serializable, Comparable {
  public static final int NONE = -1;
  public static final int CREATE = 1;
  public static final int UPDATE = 2;
  public static final int DELETE = 3;
  public static final int CHANGE = 4;

  private TableNode table = null;
  private int action = NONE;
  private Vector<TableColumn> columns2add = new Vector<TableColumn>();
  private Vector<String> columns2drop = new Vector<String>();
  private TableIndex primaryKey2create = null;
  private TableIndex primaryKey2drop = null;
  private Vector<TableIndex> indexes2create = new Vector<TableIndex>();
  private Vector<String> indexes2drop = new Vector<String>();
  private Vector<TableConstraint> constraints2Create = new Vector<TableConstraint>();
  private Vector<String> constraints2drop = new Vector<String>();

  public TableActions(TableNode table, int action) {
    this.table = table;
    this.action = action;
  }

  public TableNode getTable() {
    return table;
  }

  public String getTableName() {
    return table.getTableName();
  }

  public int getAction() {
    return action;
  }

  public void addColumn2add(TableColumn column) {
    columns2add.add(column);
  }

  public Vector<TableColumn> getColumns2add() {
    return columns2add;
  }

  public void addColumn2drop(String columnName) {
    columns2drop.add(columnName);
  }

  public Vector<String> getColumns2drop() {
    return columns2drop;
  }

  public void addIndexes2create(TableIndex index) {
    indexes2create.add(index);
  }

  public Vector<TableIndex> getIndexes2add() {
    return indexes2create;
  }

  public void addIndex2drop(String indexName) {
    indexes2drop.add(indexName);
  }

  public Vector<String> getIndexes2drop() {
    return indexes2drop;
  }

  public void setPrimaryKey2drop(TableIndex primaryKey2drop) {
    this.primaryKey2drop = primaryKey2drop;
  }

  public void setPrimaryKey2create(TableIndex primaryKey2create) {
    this.primaryKey2create = primaryKey2create;
  }

  public TableIndex getPrimaryKey2create() {
    return primaryKey2create;
  }

  public TableIndex getPrimaryKey2drop() {
    return primaryKey2drop;
  }

  public void addConstraint2create(TableConstraint constraint) {
    constraints2Create.add(constraint);
  }

  public Vector<TableConstraint> getConstraint2create() {
    return constraints2Create;
  }

  public void addConstraint2drop(String constraintName) {
    columns2drop.add(constraintName);
  }

  public Vector<String> getConstraint2drop() {
    return constraints2drop;
  }

  public int compareTo(Object o) {
    return ((TableActions)o).getTableName().compareTo(getTableName());
  }
}
