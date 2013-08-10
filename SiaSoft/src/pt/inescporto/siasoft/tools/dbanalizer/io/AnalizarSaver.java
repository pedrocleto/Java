package pt.inescporto.siasoft.tools.dbanalizer.io;

import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import java.util.Vector;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataNode;
import java.util.Hashtable;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.*;
import pt.inescporto.siasoft.tools.TableNode;

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
public class AnalizarSaver {
  private String fileName = null;
  private String sourceMajorVersion = null;
  private String sourceMinorVersion = null;
  private String targetMajorVersion = null;
  private String targetMinorVersion = null;
  private Hashtable<String, TableNode> tableTree = null;
  private Vector<StatementNode> diffStmtList = null;
  private Hashtable<String, TableDataNode > dbIncrementalData = null;

  public AnalizarSaver(String fileName, String sourceMajorVersion,
		       String sourceMinorVersion, String targetMajorVersion,
		       String targetMinorVersion, Hashtable<String, TableNode> tableTree,
      Vector<StatementNode> diffStmtList, Hashtable<String, TableDataNode> dbIncrementalData) {
    this.fileName = fileName;
    this.sourceMajorVersion = sourceMajorVersion;
    this.sourceMinorVersion = sourceMinorVersion;
    this.targetMajorVersion = targetMajorVersion;
    this.targetMinorVersion = targetMinorVersion;
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
      oos.writeObject(new AnalizerTag(AnalizerTag.START_HEADER, "Starting of DIFF file named <" + file.getName() + ">"));

      // second save a version header TAG
      VersionTag vTag = new VersionTag(AnalizerTag.VERSION, "DBAnalizerTool automatic file generated");
      vTag.setSourceMajorVersion(sourceMajorVersion);
      vTag.setSourceMinorVersion(sourceMinorVersion);
      vTag.setTargetMajorVersion(targetMajorVersion);
      vTag.setTargetMinorVersion(targetMinorVersion);
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
      oos.writeObject(new AnalizerTag(AnalizerTag.START_STRUCT, "Structural diff starts."));
      oos.writeObject(diffStmtList);
      oos.writeObject(new AnalizerTag(AnalizerTag.END_STRUCT, "Structural diff ends."));

      // fifth write data diff even if it has no elements
      oos.writeObject(new AnalizerTag(AnalizerTag.START_DATA, "Data diff starts."));
      oos.writeObject(dbIncrementalData);
      oos.writeObject(new AnalizerTag(AnalizerTag.END_STRUCT, "Data diff ends."));

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
