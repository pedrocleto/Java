package pt.inescporto.siasoft.tools.dbanalizer.io;

import java.io.*;
import java.util.*;

import pt.inescporto.siasoft.tools.*;
import pt.inescporto.siasoft.tools.dbanalizer.sql.*;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.*;

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
public class AnalizerLoader {
  private String fileName = null;
  private Hashtable<String, TableNode> tableTree = new Hashtable<String,TableNode>();
  private Vector<StatementNode> diffStmtList = new Vector<StatementNode>();
  private Hashtable<String, TableDataNode> dbIncrementalData = new Hashtable<String,TableDataNode>();

  public AnalizerLoader(String fileName) {
    this.fileName = fileName;
  }

  public boolean loadDiffFile(MessagePoster poster) {
    poster.postMessage("Start reading AET File <" + fileName + ">.");
    File file = new File(fileName);

    if (file.exists()) {
      try {
	// open a stream from file
	FileInputStream fis = new FileInputStream(file);
	ObjectInputStream ois = new ObjectInputStream(fis);

	// read START_HEADER tag
	AnalizerTag startHeaderTag = (AnalizerTag)ois.readObject();
	poster.postMessage(startHeaderTag.getTagName());

	// read VERSION tag
	VersionTag vTag = (VersionTag)ois.readObject();
	poster.postMessage("Upgrading from version " + vTag.getTargetMajorVersion() + "." + vTag.getTargetMinorVersion() +
			   " to version " + vTag.getSourceMajorVersion() + "." + vTag.getSourceMinorVersion());
        // read END_HEADER tag
        AnalizerTag endHeaderTag = (AnalizerTag)ois.readObject();
        poster.postMessage(endHeaderTag.getTagName());

        // read START_BODY tag
        AnalizerTag startBodyTag = (AnalizerTag)ois.readObject();
        poster.postMessage(startBodyTag.getTagName());

        // read START_STRUCT tag
        AnalizerTag startStructDBTag = (AnalizerTag)ois.readObject();
        poster.postMessage(startStructDBTag.getTagName());

        // read structural diff
        tableTree = (Hashtable<String, TableNode>)((Hashtable<String, TableNode>)ois.readObject()).clone();
        poster.postMessage("Expected table tree has <" + tableTree.size() + "> elements.");

        // read END_STRUCT tag
        AnalizerTag endStructDBTag = (AnalizerTag)ois.readObject();
        poster.postMessage(endStructDBTag.getTagName());

        // read START_STRUCT tag
        AnalizerTag startStructTag = (AnalizerTag)ois.readObject();
        poster.postMessage(startStructTag.getTagName());

        // read structural diff
        diffStmtList = new Vector<StatementNode>((Vector<StatementNode>)ois.readObject());
        poster.postMessage("Structural diff has <" + diffStmtList.size() + "> elements.");

        // read END_STRUCT tag
        AnalizerTag endStructTag = (AnalizerTag)ois.readObject();
        poster.postMessage(endStructTag.getTagName());

        // read START_DATA tag
        AnalizerTag startDATATag = (AnalizerTag)ois.readObject();
        poster.postMessage(startDATATag.getTagName());

        // read data diff
        dbIncrementalData = (Hashtable<String, TableDataNode>)((Hashtable<String, TableDataNode>)ois.readObject()).clone();
        poster.postMessage("Data diff has <" + dbIncrementalData.size() + "> elements.");

        // read END_DATA tag
        AnalizerTag endDataTag = (AnalizerTag)ois.readObject();
        poster.postMessage(endDataTag.getTagName());

        // read END_BODY tag
        AnalizerTag endBodyTag = (AnalizerTag)ois.readObject();
        poster.postMessage(endBodyTag.getTagName());

	ois.close();
      }
      catch (ClassNotFoundException ex) {
        poster.postMessage("ERROR (" + ex.getMessage() + ")");
        return false;
      }
      catch (FileNotFoundException ex) {
        poster.postMessage("ERROR (" + ex.getMessage() + ")");
        return false;
      }
      catch (IOException ex) {
        poster.postMessage("ERROR (" + ex.getMessage() + ")");
        return false;
      }
    }
    else {
      poster.postMessage("File <" + fileName + "> doesn't exist!");
      return false;
    }

    poster.postMessage("AET File <" + fileName + "> loaded successfully!");
    return true;
  }

  public Hashtable getDbIncrementalData() {
    return dbIncrementalData;
  }

  public Vector getDiffStmtList() {
    return diffStmtList;
  }

  public Hashtable getTableTree() {
    return tableTree;
  }
}
