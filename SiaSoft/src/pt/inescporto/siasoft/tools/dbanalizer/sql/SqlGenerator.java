package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.util.Hashtable;
import pt.inescporto.siasoft.tools.dbanalizer.structural.BuildTableLevels;
import pt.inescporto.siasoft.tools.TableColumn;
import pt.inescporto.siasoft.tools.TableIndex;
import java.util.Vector;
import pt.inescporto.siasoft.tools.TableNode;
import java.util.Enumeration;
import pt.inescporto.siasoft.tools.MessagePoster;

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
public class SqlGenerator {
  private Hashtable<String, TableNode> tableTreeSource = null;
  private Hashtable<String, TableActions> tableActions = new Hashtable<String,TableActions>();

  public SqlGenerator(Hashtable<String, TableNode> tableTreeSource, Hashtable<String, TableActions> tableActions) {
    this.tableTreeSource = tableTreeSource;
    this.tableActions = tableActions;
  }

  public Vector<StatementNode> generateSQL(MessagePoster poster) {
    Vector<StatementNode> stmtList = new Vector<StatementNode>();

    // generate sql
    poster.postMessage("*************************** SQL SCRIPT ************************************");

    // get table order sequence
    BuildTableLevels btl = new BuildTableLevels(null);
    Vector<TableNode> tblOrder = btl.build(tableTreeSource);

    // process table actions drop table
    poster.postMessage("DROP TABLE----------------------------------------------");
    for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements();) {
      TableActions tableAction = actions.nextElement();
      if (tableAction.getAction() == TableActions.DELETE) {
        String stmt = TableScripting.getDropTableScript(tableAction.getTable()).toString();
        stmtList.add(new StatementNode(StatementNode.DROP_TABLE, stmt));
        poster.postMessage(stmt);
      }
    }

    // process table actions create table
    poster.postMessage("CREATE TABLE----------------------------------------------");
    for (TableNode table : tblOrder) {
      if (tableActions.containsKey(table.getTableName()) && tableActions.get(table.getTableName()).getAction() == TableActions.CREATE) {
        TableActions tableAction = tableActions.get(table.getTableName());
        String stmt = TableScripting.getCreateTableScript(tableAction.getTable()).toString();
        stmtList.add(new StatementNode(StatementNode.CREATE_TABLE, stmt));
        poster.postMessage(stmt);
      }
    }

    // process table actions drop primary keys
    poster.postMessage("DROP PRIMARY KEYS----------------------------------------------");
    for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements();) {
      TableActions tableAction = actions.nextElement();
      if (tableAction.getAction() == TableActions.CHANGE && tableAction.getPrimaryKey2drop() != null) {
        String stmt = TableScripting.getDropConstraintTableScript(tableAction.getTable().getTableName(), tableAction.getPrimaryKey2drop().getIndexName()).toString();
        stmtList.add(new StatementNode(StatementNode.DROP_PRIMARY_KEY, stmt));
        poster.postMessage(stmt);
      }
    }

    // process table actions drop indexes
    poster.postMessage("DROP INDEXES----------------------------------------------");
    for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements();) {
      TableActions tableAction = actions.nextElement();
      if (tableAction.getAction() == TableActions.CHANGE && tableAction.getIndexes2drop().size() > 0) {
        for (String indexName : tableAction.getIndexes2drop()) {
          String stmt = TableScripting.getDropConstraintTableScript(tableAction.getTable().getTableName(), indexName).toString();
          stmtList.add(new StatementNode(StatementNode.DROP_INDEX, stmt));
          poster.postMessage(stmt);
        }
      }
    }

    // process table actions drop/create columns
    poster.postMessage("DROP/CREATE COLUMN----------------------------------------------");
    for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements();) {
      TableActions tableAction = actions.nextElement();
      if (tableAction.getAction() == TableActions.CHANGE) {
        // drop columns
        for (String columnName : tableAction.getColumns2drop()) {
          String stmt = TableScripting.getDropColumnTableScript(tableAction.getTable().getTableName(), columnName).toString();
          stmtList.add(new StatementNode(StatementNode.DROP_COLUMN, stmt));
          poster.postMessage(stmt);
        }
        // add columns
        for (TableColumn column : tableAction.getColumns2add()) {
          String stmt = TableScripting.getAddColumnTableScript(tableAction.getTable().getTableName(), column).toString();
          stmtList.add(new StatementNode(StatementNode.ADD_COLUMN, stmt));
          poster.postMessage(stmt);
        }
      }
    }

    // process table actions create primary keys
    poster.postMessage("CREATE PRIMARY KEYS----------------------------------------------");
    for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements();) {
      TableActions tableAction = actions.nextElement();
      if (tableAction.getAction() == TableActions.CHANGE && tableAction.getPrimaryKey2create() != null) {
        String stmt = TableScripting.addPrimaryKeyTableScript(tableAction.getTable().getTableName(), tableAction.getPrimaryKey2create()).toString();
        stmtList.add(new StatementNode(StatementNode.ADD_PRIMARY_KEY, stmt));
        poster.postMessage(stmt);
      }
    }

    // process table actions add indexes
    poster.postMessage("ADD INDEXES----------------------------------------------");
    for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements();) {
      TableActions tableAction = actions.nextElement();
      if (tableAction.getAction() == TableActions.CHANGE && tableAction.getIndexes2add().size() > 0) {
        for (TableIndex index : tableAction.getIndexes2add()) {
          String stmt = TableScripting.getCreateIndexScript(tableAction.getTable().getTableName(), index).toString();
          stmtList.add(new StatementNode(StatementNode.ADD_INDEX, stmt));
          poster.postMessage(stmt);
        }
      }
    }

    return stmtList;
  }
}
