package pt.inescporto.siasoft.tools;

import pt.inescporto.siasoft.tools.dbanalizer.structural.BuildTableLevels;
import java.util.Vector;
import java.util.Hashtable;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataNode;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataReader;
import java.sql.Connection;
import pt.inescporto.siasoft.tools.test.MsgPublisher;

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
public class DBDataBuilder {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster = null;
  private Hashtable<String,TableDataNode> dbIncrementalData = null;
  private Connection sConn = null;
  private Connection tConn = null;
  private Hashtable<String, TableNode> sourceTableTree = null;
  private Hashtable<String, TableNode> targetTableTree = null;
  private Hashtable<String, TableDataNode> dataNodeTargetList = null;

  public DBDataBuilder(MsgPublisher poster, Connection sConn, Connection tConn,
                       Hashtable<String, TableNode> sourceTableTree,
      Hashtable<String, TableNode> targetTableTree) {
    if (sConn == null)
      throw new IllegalArgumentException("Argument <sConn> must not be null!");
/*    if (tConn == null)
      throw new IllegalArgumentException("Argument <tConn> must not be null!");*/
    if (sourceTableTree == null)
      throw new IllegalArgumentException("Argument <sourceTableTree> must not be null!");
/*    if (targetTableTree == null)
      throw new IllegalArgumentException("Argument <targetTableTree> must not be null!");*/

    this.poster = poster;
    this.sConn = sConn;
    this.tConn = tConn;
    this.sourceTableTree = sourceTableTree;
    this.targetTableTree = targetTableTree;
    lengthOfTask = sourceTableTree.size() + 1;
//    System.out.println("Length of task is <" + lengthOfTask + ">");
  }

  public DBDataBuilder(MsgPublisher poster, Connection sConn,
                       Hashtable<String, TableNode> sourceTableTree) {
    if (sConn == null)
      throw new IllegalArgumentException("Argument <sConn> must not be null!");
/*    if (tConn == null)
      throw new IllegalArgumentException("Argument <tConn> must not be null!");*/
    if (sourceTableTree == null)
      throw new IllegalArgumentException("Argument <sourceTableTree> must not be null!");
/*    if (targetTableTree == null)
      throw new IllegalArgumentException("Argument <targetTableTree> must not be null!");*/

    this.poster = poster;
    this.sConn = sConn;
    this.tConn = tConn;
    this.sourceTableTree = sourceTableTree;
    this.targetTableTree = targetTableTree;
    lengthOfTask = sourceTableTree.size() + 1;
//    System.out.println("Length of task is <" + lengthOfTask + ">");
  }

  public DBDataBuilder(MsgPublisher poster, Connection sConn,
                       Hashtable<String, TableDataNode> dataNodeTargetList,
                       Hashtable<String, TableNode> sourceTableTree,
                       Hashtable<String, TableNode> targetTableTree) {
    if (sConn == null)
      throw new IllegalArgumentException("Argument <sConn> must not be null!");
    if (dataNodeTargetList == null)
      throw new IllegalArgumentException("Argument <dataNodeTargetList> must not be null!");
    if (sourceTableTree == null)
      throw new IllegalArgumentException("Argument <sourceTableTree> must not be null!");
    if (targetTableTree == null)
      throw new IllegalArgumentException("Argument <targetTableTree> must not be null!");

    this.poster = poster;
    this.sConn = sConn;
    this.dataNodeTargetList = dataNodeTargetList;
    this.sourceTableTree = sourceTableTree;
    this.targetTableTree = targetTableTree;
    lengthOfTask = sourceTableTree.size() + 1;
//    System.out.println("Length of task is <" + lengthOfTask + ">");
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
        if (dataNodeTargetList == null)
          return new DataDiff(poster, sConn, tConn, sourceTableTree, targetTableTree);
        else
          return new DataDiff(poster, sConn, dataNodeTargetList, sourceTableTree, targetTableTree);
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
  public Hashtable<String,TableDataNode> getIncrementalDataList() {
    return dbIncrementalData;
  }

  /**
   * The actual long running task.  This runs in a SwingWorker thread.
   */
  public class DataDiff {
    public DataDiff(MsgPublisher poster, Connection sConn, Connection tConn,
		    Hashtable<String, TableNode> sourceTableTree,
	Hashtable<String, TableNode> targetTableTree) {
      try {
        // get table order sequence
        poster.setProgress("Ordering table tree", current);
        BuildTableLevels btl = new BuildTableLevels(null);
        Vector<TableNode> tblOrder = btl.build(sourceTableTree);

        poster.setProgress("Processing tables:", ++current);
        dbIncrementalData = new Hashtable<String,TableDataNode>();
        for (TableNode table : tblOrder) {
          TableDataNode data = TableDataReader.loadData(sConn, tConn, table, targetTableTree == null ? null : targetTableTree.containsKey(table.getTableName()) ? targetTableTree.get(table.getTableName()) : null);
          poster.setProgress(table.getTableName(), current++);
          if (data != null && data.isSomething2do()) {
            poster.setProgress("\t" + (data.getAllRows4insert() == null ? "0" : data.getAllRows4insert().size()) + " rows for insert.", current);
            poster.setProgress("\t" + (data.getAllRows4update() == null ? "0" : data.getAllRows4update().size()) + " rows for update.", current);
            poster.setProgress("\t" + (data.getAllRows4delete() == null ? "0" : data.getAllRows4delete().size()) + " rows for delete.", current);
            dbIncrementalData.put(table.getTableName(), data);
          }
          else
            poster.setProgress("\tNothing to do.", current);
        }
      }
      catch (Exception ex) {
        poster.setProgress("ERROR -> " + ex.getMessage(), current);
        ex.printStackTrace();
      }

      done = true;
      current = lengthOfTask;
      poster.setProgress("End.", current);
    }

    public DataDiff(MsgPublisher poster, Connection sConn,
                    Hashtable<String, TableDataNode> dataNodeTargetList,
                    Hashtable<String, TableNode> sourceTableTree,
        Hashtable<String, TableNode> targetTableTree) {
      try {
        // get table order sequence
        poster.setProgress("Ordering table tree", current);
        BuildTableLevels btl = new BuildTableLevels(null);
        Vector<TableNode> tblOrder = btl.build(sourceTableTree);

        for (String tableName : dataNodeTargetList.keySet())
//          System.out.println(tableName);

        poster.setProgress("Processing tables:", ++current);
        dbIncrementalData = new Hashtable<String,TableDataNode>();
        for (TableNode table : tblOrder) {
          TableDataNode dataNodeTarget = null;
          dataNodeTarget = dataNodeTargetList.get(table.getTableName());
          TableDataNode data = TableDataReader.loadData(sConn, dataNodeTarget, table, targetTableTree == null ? null : targetTableTree.containsKey(table.getTableName()) ? targetTableTree.get(table.getTableName()) : null);
          poster.setProgress(table.getTableName() + ":", current++);
          if (data != null && data.isSomething2do()) {
            poster.setProgress("\t" + (data.getAllRows4insert() == null ? "0" : data.getAllRows4insert().size()) + " rows for insert.", current);
            poster.setProgress("\t" + (data.getAllRows4update() == null ? "0" : data.getAllRows4update().size()) + " rows for update.", current);
            poster.setProgress("\t" + (data.getAllRows4delete() == null ? "0" : data.getAllRows4delete().size()) + " rows for delete.", current);
	    dbIncrementalData.put(table.getTableName(), data);
	  }
          else
            poster.setProgress("\tNothing to do.", current);
        }
      }
      catch (Exception ex) {
        poster.setProgress("ERROR -> " + ex.getMessage(), current);
        ex.printStackTrace();
      }

      done = true;
      current = lengthOfTask;
      poster.setProgress("End.", current);
    }
  }
}
