package pt.inescporto.siasoft.tools.dbanalizer.structural;

import pt.inescporto.siasoft.tools.SwingWorker;
import pt.inescporto.siasoft.tools.test.MsgPublisher;
import java.util.Vector;
import pt.inescporto.siasoft.tools.dbanalizer.io.BackupDiffLoader;
import java.util.Hashtable;
import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

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
public class StructDiffWriter {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster;
  private Connection conn = null;
  Vector<StatementNode> stmtList = null;

  public StructDiffWriter(MsgPublisher poster, Connection conn, Vector<StatementNode> stmtList) {
    if (poster == null)
      throw new IllegalArgumentException("Argument <poster> must not be null!");
    if (conn == null)
      throw new IllegalArgumentException("Argument <conn> must not be null!");
    if (stmtList == null)
      throw new IllegalArgumentException("Argument <stmtList> must not be null!");

    this.poster = poster;
    this.conn = conn;
    this.stmtList = stmtList;

    lengthOfTask = stmtList.size();
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
	return new ApplyStructDiff(poster, conn, stmtList);
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
    public ApplyStructDiff(MsgPublisher poster, Connection conn, Vector<StatementNode> stmtList) {
      poster.setProgress("Start of applying structural diff ...", current);
      for (StatementNode sNode : stmtList) {
	String stmt = sNode.getStatement();
	poster.setProgress(stmt, current++);
	stmt = stmt.replace(';', ' ');
	try {
	  PreparedStatement ps = conn.prepareCall(stmt);
	  ps.execute();
	}
	catch (SQLException ex) {
	  poster.setProgress(ex.getMessage(), current);
	}
      }

      done = true;
      current = lengthOfTask;
      poster.setProgress("Done!", current);
    }
  }
}
