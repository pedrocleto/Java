package pt.inescporto.siasoft.tools.dbanalizer.io;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import pt.inescporto.siasoft.tools.TableNode;
import java.util.Vector;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataNode;
import java.io.FileOutputStream;
import java.util.Hashtable;

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
public class StarterDumpSaver {
  private String fileName = null;
  private String majorVersion = null;
  private String minorVersion = null;
  private Hashtable<String, TableNode> tableTree = null;
  private Vector<StatementNode> diffStmtList = null;
  private Hashtable<String, TableDataNode > dbIncrementalData = null;

  public StarterDumpSaver(String fileName, String sourceMajorVersion,
                       String sourceMinorVersion, Hashtable<String, TableNode> tableTree,
      Vector<StatementNode> diffStmtList, Hashtable<String, TableDataNode> dbIncrementalData) {
    this.fileName = fileName;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.tableTree = tableTree;
    this.diffStmtList = diffStmtList;
    this.dbIncrementalData = dbIncrementalData;
  }

  public void save2file() {
    File file = new File(fileName);
    if (file.exists()) {
      file.delete();
    }

    try {
      file.createNewFile();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    try {
      FileOutputStream fos = new FileOutputStream(file);
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      // first save a start header TAG
      oos.writeObject(new AnalizerTag(AnalizerTag.START_HEADER, "Starting of Starter Dump file named <" + file.getName() + ">"));

      // second save a version header TAG
      StarterDumpVersionTag vTag = new StarterDumpVersionTag(AnalizerTag.VERSION, "DBAnalizerTool automatic file generated");
      vTag.setMajorVersion(majorVersion);
      vTag.setMinorVersion(minorVersion);
      oos.writeObject(vTag);

      // close header
      oos.writeObject(new AnalizerTag(AnalizerTag.END_HEADER, "HEADER end."));

      // write body
      oos.writeObject(new AnalizerTag(AnalizerTag.START_BODY, "Body starts."));

      // third write struct of expected database structure to be updated
      oos.writeObject(new AnalizerTag(AnalizerTag.START_STRUCT, "Expected structural starts."));
      oos.writeObject(tableTree);
      oos.writeObject(new AnalizerTag(AnalizerTag.END_STRUCT, "Expected structural ends."));

      // fourth write struct diff even if it has no elements
      oos.writeObject(new AnalizerTag(AnalizerTag.START_STRUCT, "Structural SQL script starts."));
      oos.writeObject(diffStmtList);
      oos.writeObject(new AnalizerTag(AnalizerTag.END_STRUCT, "Structural SQL script ends."));

      // fifth write data diff even if it has no elements
      oos.writeObject(new AnalizerTag(AnalizerTag.START_DATA, "Data starts."));
      oos.writeObject(dbIncrementalData);
      oos.writeObject(new AnalizerTag(AnalizerTag.END_STRUCT, "Data ends."));

      // write end body
      oos.writeObject(new AnalizerTag(AnalizerTag.END_BODY, "Body ends."));

      // close
      oos.flush();
      oos.close();
    }
    catch (FileNotFoundException ex1) {
      ex1.printStackTrace();
    }
    catch (IOException ex1) {
      ex1.printStackTrace();
    }
  }
}
