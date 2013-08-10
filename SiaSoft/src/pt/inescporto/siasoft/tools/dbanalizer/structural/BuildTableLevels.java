package pt.inescporto.siasoft.tools.dbanalizer.structural;

import pt.inescporto.siasoft.tools.TableNode;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;
import pt.inescporto.siasoft.tools.TableConstraint;
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
public class BuildTableLevels {
  MessagePoster poster = null;
  boolean debug = false;

  public BuildTableLevels(MessagePoster poster) {
    this.poster = poster;
  }

  public Vector<TableNode> build(Hashtable<String, TableNode> tableTree) {
    Vector<TableNode> tableOrdered = new Vector<TableNode>();
    Vector<TableNode> tableStack = new Vector<TableNode>(tableTree.values());

    if (poster == null)
      debug = false;

    if (debug)
      poster.postMessage("Ordering <" + tableStack.size() + "> elements.");
    int curPosition = 0;
    while (tableStack.size() > 0) {
      boolean add = false;
      TableNode table = tableStack.elementAt(curPosition);
      if (table.getImportedConstraintlist() != null) {
        add = true;
        for (Iterator<TableConstraint> impKeys = table.getImportedConstraintlist() ; impKeys.hasNext();) {
          TableConstraint impKey = impKeys.next();
          String impTableName = impKey.getPkTableName();
          if (impTableName.equals(table.getTableName())) {
            if (debug)
              poster.postMessage("Loop found on table <" + table.getTableName() + ">");
            continue;
          }
          if (!tableOrdered.contains(tableTree.get(impTableName))) {
            add = false;
            break;
          }
        }
      }
      else
        add = true;
      if (add) {
        if (debug)
          poster.postMessage("Added table <" + table.getTableName() + "> pos is <" + curPosition + ">");
        tableOrdered.add(tableStack.remove(curPosition));
        if (curPosition >= tableStack.size()) {
	  curPosition = 0;
          if (debug)
            poster.postMessage("curPosition reseted. Vector as <" + tableStack.size() + ">");
	}
      }
      else {
	curPosition++;
        if (debug)
          poster.postMessage("Didn't add any table. Pos is <" + curPosition + ">");
        if (curPosition >= tableStack.size()) {
	  curPosition = 0;
          if (debug)
            poster.postMessage("curPosition reseted. Vector as <" + tableStack.size() + ">");
	}
      }
    }
    if (debug)
      poster.postMessage("Ordered <" + tableOrdered.size() + "> elements.");

    return tableOrdered;
  }
}
