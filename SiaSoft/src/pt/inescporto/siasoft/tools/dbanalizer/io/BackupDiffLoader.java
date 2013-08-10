package pt.inescporto.siasoft.tools.dbanalizer.io;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.ObjectInputStream;

import java.util.Hashtable;
import java.util.Vector;

import pt.inescporto.siasoft.tools.SwingWorker;
import pt.inescporto.siasoft.tools.TableNode;
import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataNode;
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
public class BackupDiffLoader {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster;
  private String fileName = null;
  private Hashtable<String, TableNode> tableTree = new Hashtable<String,TableNode>();
  private Vector<StatementNode> diffStmtList = new Vector<StatementNode>();
  private Hashtable<String, TableDataNode> dbIncrementalData = new Hashtable<String,TableDataNode>();

  public BackupDiffLoader(MsgPublisher poster, String fileName) {
    if (poster == null)
      throw new IllegalArgumentException("Argument <poster> must not be null!");
    if (fileName == null)
      throw new IllegalArgumentException("Argument <fileName> must not be null!");

    this.poster = poster;
    this.fileName = fileName;

    lengthOfTask = 15;
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
	return new DiffLoader(poster, fileName);
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
  public Hashtable<String, TableDataNode> getDbIncrementalData() {
    return dbIncrementalData;
  }

  public Vector getDiffStmtList() {
    return diffStmtList;
  }

  public Hashtable getTableTree() {
    return tableTree;
  }

  public class DiffLoader {
    public DiffLoader(MsgPublisher poster, String fileName) {
      poster.setProgress("Start reading AET File <" + fileName + ">.", current);
      File file = new File(fileName);

      if (file.exists()) {
	try {
	  // open a stream from file
	  FileInputStream fis = new FileInputStream(file);
	  ObjectInputStream ois = new ObjectInputStream(fis);

	  // read START_HEADER tag
	  AnalizerTag startHeaderTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(startHeaderTag.getTagName(), ++current);

	  // read VERSION tag
          Object vTag = ois.readObject();
          if (vTag instanceof StarterDumpVersionTag) {
            StarterDumpVersionTag vvTag = (StarterDumpVersionTag)vTag;
            poster.setProgress("Full dump version " + vvTag.getMajorVersion() + "." + vvTag.getMinorVersion(), ++current);
          }
          else {
            VersionTag vvTag = (VersionTag)vTag;
            poster.setProgress("Upgrading from version " + vvTag.getTargetMajorVersion() + "." + vvTag.getTargetMinorVersion() +
                               " to version " + vvTag.getSourceMajorVersion() + "." + vvTag.getSourceMinorVersion(), ++current);
          }
	  // read END_HEADER tag
	  AnalizerTag endHeaderTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(endHeaderTag.getTagName(), ++current);

	  // read START_BODY tag
	  AnalizerTag startBodyTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(startBodyTag.getTagName(), ++current);

	  // read START_STRUCT tag
	  AnalizerTag startStructDBTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(startStructDBTag.getTagName(), ++current);

	  // read structural diff
	  tableTree = (Hashtable<String, TableNode>)((Hashtable<String, TableNode>)ois.readObject()).clone();
	  poster.setProgress("Expected table tree has <" + tableTree.size() + "> elements.", ++current);

	  // read END_STRUCT tag
	  AnalizerTag endStructDBTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(endStructDBTag.getTagName(), ++current);

	  // read START_STRUCT tag
	  AnalizerTag startStructTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(startStructTag.getTagName(), ++current);

	  // read structural diff
	  diffStmtList = new Vector<StatementNode>((Vector<StatementNode>)ois.readObject());
	  poster.setProgress("Structural diff has <" + diffStmtList.size() + "> elements.", ++current);

	  // read END_STRUCT tag
	  AnalizerTag endStructTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(endStructTag.getTagName(), ++current);

	  // read START_DATA tag
	  AnalizerTag startDATATag = (AnalizerTag)ois.readObject();
	  poster.setProgress(startDATATag.getTagName(), ++current);

	  // read data diff
	  dbIncrementalData = (Hashtable<String, TableDataNode>)ois.readObject();
	  poster.setProgress("Data diff has <" + dbIncrementalData.size() + "> elements.", ++current);

	  // read END_DATA tag
	  AnalizerTag endDataTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(endDataTag.getTagName(), ++current);

	  // read END_BODY tag
	  AnalizerTag endBodyTag = (AnalizerTag)ois.readObject();
	  poster.setProgress(endBodyTag.getTagName(), ++current);

	  ois.close();
	}
	catch (ClassNotFoundException ex) {
	  poster.setProgress("ERROR (" + ex.getMessage() + ")", current);
	}
	catch (FileNotFoundException ex) {
	  poster.setProgress("ERROR (" + ex.getMessage() + ")", current);
	}
	catch (IOException ex) {
	  poster.setProgress("ERROR (" + ex.getMessage() + ")", current);
	}
      }
      else {
	poster.setProgress("File <" + fileName + "> doesn't exist!", current);
      }

      done = true;

      if (current + 1 < lengthOfTask) {
        current = lengthOfTask;
        poster.setProgress("AET File <" + fileName + "> load error!", current);
      }
      else {
	current = lengthOfTask;
	poster.setProgress("AET File <" + fileName + "> loaded successfully!", current);
      }
    }
  };
}
