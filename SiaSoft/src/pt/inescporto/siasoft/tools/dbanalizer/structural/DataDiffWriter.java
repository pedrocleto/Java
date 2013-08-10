package pt.inescporto.siasoft.tools.dbanalizer.structural;

import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataNode;
import pt.inescporto.siasoft.tools.TableNode;
import pt.inescporto.siasoft.tools.TableColumn;
import pt.inescporto.siasoft.tools.test.MsgPublisher;
import pt.inescporto.siasoft.tools.SwingWorker;
import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.IndexField;
import java.sql.ResultSet;

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
public class DataDiffWriter {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster;
  private Connection conn = null;
  private Hashtable<String, TableNode> tableTree = null;
  private Hashtable<String, TableDataNode > dbIncrementalData = null;

  public DataDiffWriter(MsgPublisher poster, Connection conn, Hashtable<String,
			TableNode> tableTree, Hashtable<String, TableDataNode> dbIncrementalData) {
    if (poster == null)
      throw new IllegalArgumentException("Argument <poster> must not be null!");
    if (conn == null)
      throw new IllegalArgumentException("Argument <conn> must not be null!");
    if (tableTree == null)
      throw new IllegalArgumentException("Argument <tableTree> must not be null!");
    if (dbIncrementalData == null)
      throw new IllegalArgumentException("Argument <dbIncrementalData> must not be null!");

    this.poster = poster;
    this.conn = conn;
    this.tableTree = tableTree;
    this.dbIncrementalData = dbIncrementalData;

    lengthOfTask = dbIncrementalData.size();
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
        return new ApplyStructDiff(poster, conn, tableTree, dbIncrementalData);
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

  public class ApplyStructDiff {
    public ApplyStructDiff(MsgPublisher poster, Connection conn,
        Hashtable<String, TableNode> tableTree,
        Hashtable<String, TableDataNode > dbIncrementalData) {
      poster.setProgress("Start of applying data diff ...", current);

      // get table order sequence
      poster.setProgress("Ordering tables.", current++);
      BuildTableLevels btl = new BuildTableLevels(null);
      Vector<TableNode> tblOrder = btl.build(tableTree);

      poster.setProgress("Applying data.", current);
      for (TableNode table : tblOrder) {
        TableDataNode data = dbIncrementalData.get(table.getTableName());
        if (dbIncrementalData.containsKey(table.getTableName())) {
          // get mapping columns
          Vector<Integer> colTypes = new Vector<Integer>();
          for (Iterator<TableColumn> columnList = table.getColumnlist(); columnList.hasNext(); ) {
            TableColumn tc = columnList.next();
            colTypes.add(tc.getDataType());
          }
          // get primary keys
          Vector<Integer> colTypes4update = null;
          if (table.getPrimaryKey() != null) {
            Vector<Integer> pkOrdinalNumber = new Vector<Integer>();
            colTypes4update = new Vector<Integer>();
	    for (Iterator<IndexField> ifList = table.getPrimaryKey().getIndexFieldList(); ifList.hasNext(); ) {
	      IndexField field = ifList.next();
	      pkOrdinalNumber.add(new Integer(field.getOrdinalPosition()));
	    }
	    // build mapping columns for updating
	    for (int i = 0; i < colTypes.size(); i++) {
	      if (pkOrdinalNumber.contains(new Integer(i + 1))) {
		continue;
	      }
	      else {
		colTypes4update.add(colTypes.elementAt(i));
	      }
	    }

	    for (int i = 0; i < colTypes.size(); i++) {
	      if (pkOrdinalNumber.contains(new Integer(i + 1))) {
		colTypes4update.add(colTypes.elementAt(i));
	      }
	      else {
		break;
	      }
	    }
	  }
          // first let's delete data
          /**
           * @todo Data to delete must be performed in reverse order
           */

          // second let's update data
          try {
            if (data.getAllRows4update() != null) {
              String stmt = table.getUpdateStmt().toString().replace(';', ' ');
              PreparedStatement ps = conn.prepareStatement(stmt);
              /**
               * @todo make test for non indexed tables
               */
              for (Vector row : data.getAllRows4update()) {
                int parIndex = 1;
                for (Object element : row) {
		  if (element != null) {
		    ps.setObject(parIndex, element, colTypes4update.elementAt(parIndex-1).intValue());
		  }
		  else
		    ps.setNull(parIndex, colTypes4update.elementAt(parIndex-1).intValue());
		  parIndex++;
		}
                ps.executeUpdate();
              }
            }
          }
          catch (SQLException ex) {
            poster.setProgress(ex.getMessage(), current);
          }

          // second let's insert data
          try {
            if (data.getAllRows4insert() != null) {
              String stmt = table.getInsertStmt().toString().replace(';', ' ');
              PreparedStatement ps = conn.prepareStatement(stmt);
              for (Vector row : data.getAllRows4insert()) {
                int parIndex = 1;
                for (Object value : row) {
                  if (value != null)
                    ps.setObject(parIndex, value, colTypes.elementAt(parIndex - 1).intValue());
                  else
                    ps.setNull(parIndex, colTypes.elementAt(parIndex - 1).intValue());
                  parIndex++;
                }
                ps.executeUpdate();
              }
            }
          }
          catch (SQLException ex) {
            poster.setProgress(ex.getMessage(), current);
          }
          poster.setProgress(table.getTableName() + " updated.", ++current);
        }
      }

      // postprocessing
      // WarningMessages
      try {
        PreparedStatement ps = conn.prepareStatement("SELECT MAX(messagesequence) FROM WarningMessages");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          String maxValue = rs.getString(1);
          if (maxValue != null) {
	    ps = conn.prepareStatement("UPDATE km_warningmessages SET messagesequence = '" + maxValue + "'");
	    ps.executeUpdate();
	  }
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
      // RegulatoryHistory
      try {
        PreparedStatement ps = conn.prepareStatement("SELECT MAX(regHistID) FROM RegulatoryHistory");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          String maxValue = rs.getString(1);
          if (maxValue != null) {
	    ps = conn.prepareStatement("UPDATE km_reghist SET regHistID = '" + maxValue + "'");
	    ps.executeUpdate();
	  }
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
      // AsqClientApplicability
      try {
        PreparedStatement ps = conn.prepareStatement("SELECT MAX(recordID) FROM AsqClientApplicability");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          String maxValue = rs.getString(1);
          if (maxValue != null) {
	    ps = conn.prepareStatement("UPDATE km_asqclientapplicability SET recordID = '" + maxValue + "'");
	    ps.executeUpdate();
	  }
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
      // Activity
      try {
        PreparedStatement ps = conn.prepareStatement("SELECT MAX(activityID) FROM Activity");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          String maxValue = rs.getString(1);
          if (maxValue != null) {
	    ps = conn.prepareStatement("UPDATE km_activity SET activityID = '" + maxValue + "'");
	    ps.executeUpdate();
	  }
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }

      current = lengthOfTask;
      poster.setProgress("Done!", current);
      done = true;
    }
  }
}
