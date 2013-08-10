package pt.inescporto.siasoft.tools.dbanalizer;

import pt.inescporto.siasoft.tools.TableNode;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;
import pt.inescporto.siasoft.tools.TableColumn;
import pt.inescporto.siasoft.tools.MessagePoster;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.IndexField;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableActions;
import pt.inescporto.siasoft.tools.TableConstraint;
import pt.inescporto.siasoft.tools.KeyReference;
import pt.inescporto.siasoft.tools.AnalizerEventListener;

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
public class DBComparator extends Thread {
  AnalizerEventListener listener = null;
  MessagePoster poster = null;
  Hashtable<String, TableNode> tableTreeSource = null;
  Hashtable<String, TableNode> tableTreeDestiny = null;
  Hashtable<String, TableActions> tableActions = new Hashtable<String,TableActions>();

  public DBComparator(AnalizerEventListener listener, MessagePoster poster, Hashtable<String, TableNode> tableTreeSource, Hashtable<String, TableNode> tableTreeDestiny) {
    this.listener = listener;
    this.poster = poster;
    this.tableTreeSource = tableTreeSource;
    this.tableTreeDestiny = tableTreeDestiny;
  }

  public void run() {
    compareStructure(poster, tableTreeSource, tableTreeDestiny);
    listener.setTableActions(tableActions);
  }

  private void compareStructure(MessagePoster poster, Hashtable<String, TableNode> tableTreeSource, Hashtable<String, TableNode> tableTreeDestiny) {
    // compare tables
    // tables to create
    for (Enumeration<String> sourceTables = tableTreeSource.keys(); sourceTables.hasMoreElements();) {
      String tableName = sourceTables.nextElement();
      if (!tableTreeDestiny.containsKey(tableName)) {
        poster.postMessage("Destiny does't have table <" + tableName + ">.This table will be marked for creation.");
        TableActions tblAction = new TableActions(tableTreeSource.get(tableName), TableActions.CREATE);
        tableActions.put(tableName, tblAction);
      }
      else {
	TableNode sourceTable = tableTreeSource.get(tableName);
	TableNode destinyTable = tableTreeDestiny.get(tableName);

	// compare primary keys
	if (sourceTable.getPrimaryKey() != null && destinyTable.getPrimaryKey() != null) {
	  // compare index fields
	  for (Iterator<IndexField> sourceIFields = sourceTable.getPrimaryKey().getIndexFieldList(); sourceIFields.hasNext(); ) {
	    IndexField sourceIField = sourceIFields.next();
	    boolean matchField = false;
	    for (Iterator<IndexField> destinyIFields = destinyTable.getPrimaryKey().getIndexFieldList(); destinyIFields.hasNext(); ) {
	      IndexField destinyIField = destinyIFields.next();
	      if (sourceIField.getFieldName().equals(destinyIField.getFieldName())) {
		if (sourceIField.equals(destinyIField)) {
		  matchField = true;
		  break;
		}
	      }
	    }
	    if (!matchField) {
	      TableActions tblAction = null;
	      tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	      tblAction.setPrimaryKey2drop(destinyTable.getPrimaryKey());
	      tblAction.setPrimaryKey2create(sourceTable.getPrimaryKey());
	      tableActions.put(tableName, tblAction);
	      poster.postMessage("Destiny index <" + destinyTable.getPrimaryKey().getIndexName() + "> on table <" + tableName +
				 "> don't have matching field names. This index will be marked replaced.");
	      break;
	    }
	  }
	}
	else {
	  if ((sourceTable.getPrimaryKey() == null && destinyTable.getPrimaryKey() != null) || sourceTable.getPrimaryKey() != null && destinyTable.getPrimaryKey() == null) {
	    if (sourceTable.getPrimaryKey() == null && destinyTable.getPrimaryKey() != null) {
	      TableActions tblAction = null;
	      tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	      tblAction.setPrimaryKey2drop(destinyTable.getPrimaryKey());
	      tableActions.put(tableName, tblAction);
	      poster.postMessage("Primary key <" + destinyTable.getPrimaryKey().getIndexName() + "> on table <" + tableName + "> doesn't exist. This primary key will be marked for drop.");
	    }
	    else {
	      TableActions tblAction = null;
	      tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	      tblAction.setPrimaryKey2create(sourceTable.getPrimaryKey());
	      tableActions.put(tableName, tblAction);
	      poster.postMessage("Primary key <" + sourceTable.getPrimaryKey().getIndexName() + "> on table <" + tableName + "> doesn't exist. This primary key will be marked for creation.");
	    }
	  }
	}

	// compare indexes
	for (Iterator<TableIndex> sourceIndexes = sourceTable.getIndexlist(); sourceIndexes.hasNext(); ) {
	  TableIndex sourceIndex = sourceIndexes.next();
	  if (!destinyTable.getIndexlist().hasNext()) {
	    TableActions tblAction = null;
	    tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	    tblAction.setPrimaryKey2create(sourceIndex);
	    tableActions.put(tableName, tblAction);
	    poster.postMessage("Index <" + sourceIndex.getIndexName() + "> on table <" + tableName + "> doesn't exist. This index will be marked for creation.");
	    continue;
	  }
	  for (Iterator<TableIndex> destenyIndexes = destinyTable.getIndexlist(); destenyIndexes.hasNext(); ) {
	    TableIndex destinyIndex = destenyIndexes.next();

	    // find Index matching
	    boolean foundIndex = false;
	    if (sourceIndex.getIndexName().equals(destinyIndex.getIndexName())) {
	      foundIndex = true;

	      // compare index fields
	      for (Iterator<IndexField> sourceIFields = sourceIndex.getIndexFieldList(); sourceIFields.hasNext(); ) {
		IndexField sourceIField = sourceIFields.next();
		boolean matchField = false;
		for (Iterator<IndexField> destinyIFields = destinyIndex.getIndexFieldList(); destinyIFields.hasNext(); ) {
		  IndexField destinyIField = destinyIFields.next();
		  if (sourceIField.getFieldName().equals(destinyIField.getFieldName())) {
		    if (sourceIField.equals(destinyIField)) {
		      matchField = true;
		      break;
		    }
		  }
		}
		if (!matchField) {
		  TableActions tblAction = null;
		  tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
		  tblAction.addIndex2drop(destinyIndex.getIndexName());
		  tblAction.addIndexes2create(sourceIndex);
		  tableActions.put(tableName, tblAction);
		  poster.postMessage("Destiny index <" + destinyIndex.getIndexName() + "> on table <" + tableName + "> don't have matching field names. This index will be marked replaced.");
		  break;
		}
	      }
	    }
	    if (!foundIndex) {
	      TableActions tblAction = null;
	      tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	      tblAction.setPrimaryKey2create(sourceIndex);
	      tableActions.put(tableName, tblAction);
	      poster.postMessage("Index <" + sourceIndex.getIndexName() + "> on table <" + tableName + "> doesn't exist. This index will be marked for creation.");
	    }
	  }
	}

	// compare columns
	for (Iterator<TableColumn> sourceColumns = sourceTable.getColumnlist(); sourceColumns.hasNext(); ) {
	  TableColumn sourceColumn = sourceColumns.next();
	  boolean exist = false;
	  for (Iterator<TableColumn> destinyColumns = destinyTable.getColumnlist(); destinyColumns.hasNext(); ) {
	    TableColumn destinyColumn = destinyColumns.next();
	    if (sourceColumn.getColumnName().equals(destinyColumn.getColumnName())) {
	      exist = true;
	      boolean match = false;
	      if (sourceColumn.getDataType().intValue() == destinyColumn.getDataType().intValue()) {
		if (sourceColumn.getColumnSize().intValue() == destinyColumn.getColumnSize().intValue()) {
		  if (sourceColumn.getDecimalDigits().intValue() == destinyColumn.getDecimalDigits().intValue()) {
		    if (sourceColumn.getOrdinalPosition().intValue() == destinyColumn.getOrdinalPosition().intValue()) {
		      if (sourceColumn.getNullable().intValue() == destinyColumn.getNullable().intValue()) {
			match = true;
//                        poster.postMessage("Column <" + sourceColumn.getColumnName() + "> match on table <" + tableName  + ">.");
			break;
		      }
		      else {
			break;
		      }
		    }
		    else {
		      break;
		    }
		  }
		  else {
		    break;
		  }
		}
		else {
		  break;
		}
	      }
	      if (!match) {
		TableActions tblAction = null;
		tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
		tblAction.addColumn2drop(sourceColumn.getColumnName());
		tblAction.addColumn2add(sourceColumn);
		tableActions.put(tableName, tblAction);
		poster.postMessage("Column <" + sourceColumn.getColumnName() + "> doesn't match on table <" + tableName + ">.");
		break;
	      }
	    }
	  }
	  if (!exist) {
	    TableActions tblAction = null;
	    tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	    tblAction.addColumn2add(sourceColumn);
	    tableActions.put(tableName, tblAction);
	    poster.postMessage("Column <" + sourceColumn.getColumnName() + "> doesn't exist on table <" + tableName + ">.");
	  }
	}

	// compare imported constraint's
	for (Iterator<TableConstraint> sourceConstraints = sourceTable.getImportedConstraintlist(); sourceConstraints.hasNext(); ) {
	  TableConstraint sourceConstraint = sourceConstraints.next();
	  if (!destinyTable.getImportedConstraintlist().hasNext()) {
	    TableActions tblAction = null;
	    tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	    tblAction.addConstraint2create(sourceConstraint);
	    tableActions.put(tableName, tblAction);
	    poster.postMessage("Imported constraint <" + sourceConstraint.getConstraintName() + "> on table <" + tableName + "> doesn't exist. This constraint will be marked for creation.");
	    continue;
	  }
          boolean foundIndex = false;
	  for (Iterator<TableConstraint> destenyConstraints = destinyTable.getImportedConstraintlist(); destenyConstraints.hasNext(); ) {
	    TableConstraint destinyConstraint = destenyConstraints.next();

	    if (sourceConstraint.getConstraintName().equals(destinyConstraint.getConstraintName())) {
	      foundIndex = true;

	      // compare index fields
	      for (Iterator<KeyReference> sourceKeys = sourceConstraint.getKeyReferencesList(); sourceKeys.hasNext(); ) {
		KeyReference sourceKey = sourceKeys.next();
		boolean matchField = false;
                for (Iterator<KeyReference> destinyKeys = destinyConstraint.getKeyReferencesList(); destinyKeys.hasNext(); ) {
		  KeyReference destinyKey = destinyKeys.next();
		  if (sourceKey.getPkField().equals(destinyKey.getPkField())) {
		    if (sourceKey.equals(destinyKey)) {
		      matchField = true;
		      break;
		    }
		  }
		}
		if (!matchField) {
		  TableActions tblAction = null;
		  tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
		  tblAction.addColumn2drop(destinyConstraint.getConstraintName());
		  tblAction.addConstraint2create(sourceConstraint);
		  tableActions.put(tableName, tblAction);
		  poster.postMessage("Destiny index <" + destinyConstraint.getConstraintName() + "> on table <" + tableName + "> don't match. This constraint will be marked for replacement.");
		  break;
		}
	      }
	    }
          }
	    if (!foundIndex) {
	      TableActions tblAction = null;
	      tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	      tblAction.addConstraint2create(sourceConstraint);
	      tableActions.put(tableName, tblAction);
	      poster.postMessage("Imported constraint <" + sourceConstraint.getConstraintName() + "> on table <" + tableName + "> doesn't exist. This constraint will be marked for creation.");
	    }
	}
      }
      yield();
    }

    // tables to delete
    for (Enumeration<String> destinyTables = tableTreeDestiny.keys(); destinyTables.hasMoreElements();) {
      String tableName = destinyTables.nextElement();
      if (!tableTreeSource.containsKey(tableName)) {
        poster.postMessage("Source does't have table <" + tableName + ">. This table will be marked for deletion!");
        TableActions tblAction = new TableActions(tableTreeDestiny.get(tableName), TableActions.DELETE);
        tableActions.put(tableName, tblAction);
      }
      yield();
    }

    if (tableActions.size() == 0) {
      poster.postMessage("Nothing to do!");
      poster.postSeparator();
      return;
    }

    poster.postSeparator();
  }
}
