package pt.inescporto.siasoft.tools.dbanalizer;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableActions;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.TableColumn;
import pt.inescporto.siasoft.tools.TableNode;
import pt.inescporto.siasoft.tools.TableConstraint;
import pt.inescporto.siasoft.tools.KeyReference;
import pt.inescporto.siasoft.tools.IndexField;
import pt.inescporto.siasoft.tools.test.MsgPublisher;
import pt.inescporto.siasoft.tools.SwingWorker;

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
public class DBStructCompare {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster = null;
  private Hashtable<String, TableNode> tableTreeSource = null;
  private Hashtable<String, TableNode> tableTreeDestiny = null;

  Hashtable<String, TableActions> tableActions = new Hashtable<String, TableActions>();

  public DBStructCompare(MsgPublisher poster,
                         Hashtable<String, TableNode> tableTreeSource,
      Hashtable<String, TableNode> tableTreeDestiny) {
    if (tableTreeSource == null)
      throw new IllegalArgumentException("Argument <tableTreeSource> must not be null!");
    if (tableTreeDestiny == null)
      System.out.println("Making DBStructCompare with argument <tableTreeDestiny> equal to null!");

    this.poster = poster;
    this.tableTreeSource = tableTreeSource;
    this.tableTreeDestiny = tableTreeDestiny;

    lengthOfTask = tableTreeSource.keySet().size();
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
        return new DBStructComparator(poster, tableTreeSource, tableTreeDestiny);
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
  public Hashtable<String, TableActions> getTableActions() {
    return tableActions;
  }

  public class DBStructComparator {
    public DBStructComparator(MsgPublisher poster, Hashtable<String, TableNode> tableTreeSource, Hashtable<String, TableNode> tableTreeDestiny) {
      // compare tables
      // tables to create
      for (Enumeration<String> sourceTables = tableTreeSource.keys(); sourceTables.hasMoreElements(); ) {
        current++;
	String tableName = sourceTables.nextElement();
	if (tableTreeDestiny == null || (tableTreeDestiny != null && !tableTreeDestiny.containsKey(tableName))) {
	  poster.setProgress("Target does't have table <" + tableName + ">.This table will be marked for creation.", current);
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
		poster.setProgress("Target index <" + destinyTable.getPrimaryKey().getIndexName() + "> on table <" + tableName +
				   "> don't have matching field names. This index will be marked replaced.", current);
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
		poster.setProgress("Primary key <" + destinyTable.getPrimaryKey().getIndexName() + "> on table <" + tableName + "> doesn't exist. This primary key will be marked for drop.", current);
	      }
	      else {
		TableActions tblAction = null;
		tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
		tblAction.setPrimaryKey2create(sourceTable.getPrimaryKey());
		tableActions.put(tableName, tblAction);
		poster.setProgress("Primary key <" + sourceTable.getPrimaryKey().getIndexName() + "> on table <" + tableName + "> doesn't exist. This primary key will be marked for creation.", current);
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
	      poster.setProgress("Index <" + sourceIndex.getIndexName() + "> on table <" + tableName + "> doesn't exist. This index will be marked for creation.", current);
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
		    poster.setProgress("Target index <" + destinyIndex.getIndexName() + "> on table <" + tableName + "> don't have matching field names. This index will be marked replaced.", current);
		    break;
		  }
		}
	      }
	      if (!foundIndex) {
		TableActions tblAction = null;
		tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
		tblAction.setPrimaryKey2create(sourceIndex);
		tableActions.put(tableName, tblAction);
		poster.setProgress("Index <" + sourceIndex.getIndexName() + "> on table <" + tableName + "> doesn't exist. This index will be marked for creation.", current);
	      }
	    }
	  }

	  // compare columns source to target
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
//                        poster.setProgress("Column <" + sourceColumn.getColumnName() + "> match on table <" + tableName  + ">.");
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
		  poster.setProgress("Column <" + sourceColumn.getColumnName() + "> doesn't match on table <" + tableName + ">.", current);
		  break;
		}
	      }
	    }
	    if (!exist) {
	      TableActions tblAction = null;
	      tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
	      tblAction.addColumn2add(sourceColumn);
	      tableActions.put(tableName, tblAction);
	      poster.setProgress("Column <" + sourceColumn.getColumnName() + "> doesn't exist on table <" + tableName + ">.", current);
	    }
	  }

          // compare columns source to target
          for (Iterator<TableColumn> targetColumns = destinyTable.getColumnlist(); targetColumns.hasNext(); ) {
            TableColumn targetColumn = targetColumns.next();
            boolean exist = false;
            for (Iterator<TableColumn> sourceColumns = sourceTable.getColumnlist(); sourceColumns.hasNext(); ) {
              TableColumn sourceColumn = sourceColumns.next();
              if (targetColumn.getColumnName().equals(sourceColumn.getColumnName())) {
                exist = true;
                break;
              }
            }
            if (!exist) {
              TableActions tblAction = null;
              tblAction = !tableActions.containsKey(tableName) ? new TableActions(sourceTable, TableActions.CHANGE) : tableActions.get(tableName);
              tblAction.addColumn2drop(targetColumn.getColumnName());
              tableActions.put(tableName, tblAction);
              poster.setProgress("Column <" + targetColumn.getColumnName() + "> exist on table <" + tableName + "> in target table.", current);
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
	      poster.setProgress("Imported constraint <" + sourceConstraint.getConstraintName() + "> on table <" + tableName + "> doesn't exist. This constraint will be marked for creation.", current);
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
		    poster.setProgress("Target index <" + destinyConstraint.getConstraintName() + "> on table <" + tableName + "> don't match. This constraint will be marked for replacement.", current);
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
	      poster.setProgress("Imported constraint <" + sourceConstraint.getConstraintName() + "> on table <" + tableName + "> doesn't exist. This constraint will be marked for creation.", current);
	    }
	  }
	}
      }
      done = true;
      current = lengthOfTask;

      // tables to delete
      if (tableTreeDestiny != null) {
	for (Enumeration<String> destinyTables = tableTreeDestiny.keys(); destinyTables.hasMoreElements(); ) {
	  String tableName = destinyTables.nextElement();
	  if (!tableTreeSource.containsKey(tableName)) {
	    poster.setProgress("Source does't have table <" + tableName + ">. This table will be marked for deletion!", current);
	    TableActions tblAction = new TableActions(tableTreeDestiny.get(tableName), TableActions.DELETE);
	    tableActions.put(tableName, tblAction);
	  }
	}
      }

      if (tableActions.size() == 0) {
	poster.setProgress("Nothing to do!", current);
        return;
      }
      poster.setProgress("Done!", current);
    }
  }
}
