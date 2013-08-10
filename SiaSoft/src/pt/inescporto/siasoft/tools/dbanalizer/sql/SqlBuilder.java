package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import java.sql.Connection;
import pt.inescporto.siasoft.tools.test.MsgPublisher;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.TableNode;
import pt.inescporto.siasoft.tools.TableColumn;
import pt.inescporto.siasoft.tools.SwingWorker;
import pt.inescporto.siasoft.tools.dbanalizer.structural.BuildTableLevels;

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
public class SqlBuilder {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster = null;
  private Connection conn = null;
  private Hashtable<String, TableNode> tableTreeSource = null;
  private Hashtable<String, TableActions> tableActions = null;

  private Vector<StatementNode> stmtList = new Vector<StatementNode>();

  public SqlBuilder(MsgPublisher poster,
                  Hashtable<String, TableNode> tableTreeSource,
        Hashtable<String, TableActions> tableActions) {
    if (tableTreeSource == null)
      throw new IllegalArgumentException("Argument <tableTreeSource> must not be null!");
    if (tableActions == null)
      throw new IllegalArgumentException("Argument <tableActions> must not be null!");

    this.poster = poster;
    this.conn = conn;
    this.tableTreeSource = tableTreeSource;
    this.tableActions = tableActions;

    lengthOfTask = 8;
    System.out.println("Length of task is <" + lengthOfTask + ">");
  }

  /**
   * Called from ProgressBarDemo to start the task.
   */
  public void go() {
    final SwingWorker worker = new SwingWorker() {
      public Object construct() {
	current = 0;
	done = false;
	canceled = false;
	statMessage = null;
	return new SqlGen(poster, tableTreeSource, tableActions);
      }
    };
    worker.start();
  }

  /**
   * Called from ProgressBarDemo to find out how much work needs
   * to be done.
   */
  public int getLengthOfTask() {
    return lengthOfTask;
  }

  /**
   * Called from ProgressBarDemo to find out how much has been done.
   */
  public int getCurrent() {
    return current;
  }

  public void stop() {
    canceled = true;
    statMessage = null;
  }

  /**
   * Called from ProgressBarDemo to find out if the task has completed.
   */
  public boolean isDone() {
    return done;
  }

  /**
   * Returns the most recent status message, or null
   * if there is no current status message.
   */
  public Vector<StatementNode> getStmtList() {
    return stmtList;
  }

  /**
   * The actual long running task.  This runs in a SwingWorker thread.
   */
  public class SqlGen {
    public SqlGen(MsgPublisher poster,
		  Hashtable<String, TableNode> tableTreeSource,
	Hashtable<String, TableActions> tableActions) {

      boolean somethig2do = false;
      boolean taskHasSomethig2do = false;

      // get table order sequence
      BuildTableLevels btl = new BuildTableLevels(null);
      Vector<TableNode> tblOrder = btl.build(tableTreeSource);

      // process table actions drop table
      taskHasSomethig2do = false;
      poster.setProgress("DROP TABLE", current++);
      for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements(); ) {
	TableActions tableAction = actions.nextElement();
	if (tableAction.getAction() == TableActions.DELETE) {
	  String stmt = TableScripting.getDropTableScript(tableAction.getTable()).toString();
	  stmtList.add(new StatementNode(StatementNode.DROP_TABLE, stmt));
          taskHasSomethig2do = true;
          somethig2do = true;
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      // process table actions create table
      taskHasSomethig2do = false;
      poster.setProgress("CREATE TABLE", current++);
      for (TableNode table : tblOrder) {
	if (tableActions.containsKey(table.getTableName()) && tableActions.get(table.getTableName()).getAction() == TableActions.CREATE) {
	  TableActions tableAction = tableActions.get(table.getTableName());
	  String stmt = TableScripting.getCreateTableScript(tableAction.getTable()).toString();
	  stmtList.add(new StatementNode(StatementNode.CREATE_TABLE, stmt));
          taskHasSomethig2do = true;
          somethig2do = true;
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      // process table actions drop primary keys
      taskHasSomethig2do = false;
      poster.setProgress("DROP PRIMARY KEYS", current++);
      for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements(); ) {
	TableActions tableAction = actions.nextElement();
	if (tableAction.getAction() == TableActions.CHANGE && tableAction.getPrimaryKey2drop() != null) {
	  String stmt = TableScripting.getDropConstraintTableScript(tableAction.getTable().getTableName(), tableAction.getPrimaryKey2drop().getIndexName()).toString();
	  stmtList.add(new StatementNode(StatementNode.DROP_PRIMARY_KEY, stmt));
          taskHasSomethig2do = true;
          somethig2do = true;
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      // process table actions drop indexes
      taskHasSomethig2do = false;
      poster.setProgress("DROP INDEXES", current++);
      for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements(); ) {
	TableActions tableAction = actions.nextElement();
	if (tableAction.getAction() == TableActions.CHANGE && tableAction.getIndexes2drop().size() > 0) {
	  for (String indexName : tableAction.getIndexes2drop()) {
	    String stmt = TableScripting.getDropConstraintTableScript(tableAction.getTable().getTableName(), indexName).toString();
	    stmtList.add(new StatementNode(StatementNode.DROP_INDEX, stmt));
            taskHasSomethig2do = true;
            somethig2do = true;
	  }
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      // process table actions drop/create columns
      taskHasSomethig2do = false;
      poster.setProgress("DROP/CREATE COLUMN", current++);
      for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements(); ) {
	TableActions tableAction = actions.nextElement();
	if (tableAction.getAction() == TableActions.CHANGE) {
	  // drop columns
	  for (String columnName : tableAction.getColumns2drop()) {
	    String stmt = TableScripting.getDropColumnTableScript(tableAction.getTable().getTableName(), columnName).toString();
	    stmtList.add(new StatementNode(StatementNode.DROP_COLUMN, stmt));
            taskHasSomethig2do = true;
            somethig2do = true;
	  }
	  // add columns
	  for (TableColumn column : tableAction.getColumns2add()) {
	    String stmt = TableScripting.getAddColumnTableScript(tableAction.getTable().getTableName(), column).toString();
	    stmtList.add(new StatementNode(StatementNode.ADD_COLUMN, stmt));
            taskHasSomethig2do = true;
            somethig2do = true;
	  }
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      // process table actions create primary keys
      taskHasSomethig2do = false;
      poster.setProgress("CREATE PRIMARY KEYS", current++);
      for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements(); ) {
	TableActions tableAction = actions.nextElement();
	if (tableAction.getAction() == TableActions.CHANGE && tableAction.getPrimaryKey2create() != null) {
	  String stmt = TableScripting.addPrimaryKeyTableScript(tableAction.getTable().getTableName(), tableAction.getPrimaryKey2create()).toString();
	  stmtList.add(new StatementNode(StatementNode.ADD_PRIMARY_KEY, stmt));
          taskHasSomethig2do = true;
          somethig2do = true;
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      // process table actions add indexes
      taskHasSomethig2do = false;
      poster.setProgress("ADD INDEXES", current++);
      for (Enumeration<TableActions> actions = tableActions.elements(); actions.hasMoreElements(); ) {
	TableActions tableAction = actions.nextElement();
	if (tableAction.getAction() == TableActions.CHANGE && tableAction.getIndexes2add().size() > 0) {
	  for (TableIndex index : tableAction.getIndexes2add()) {
	    String stmt = TableScripting.getCreateIndexScript(tableAction.getTable().getTableName(), index).toString();
	    stmtList.add(new StatementNode(StatementNode.ADD_INDEX, stmt));
            taskHasSomethig2do = true;
            somethig2do = true;
	  }
	}
      }
      poster.setProgress((taskHasSomethig2do ? "Done." : "Nothing to do."), current);

      poster.setProgress((somethig2do ? "Globally done." : "Globally nothing to do."), current);

      done = true;
      current = lengthOfTask;
      poster.setProgress("End.", current);
    }
  }
}
