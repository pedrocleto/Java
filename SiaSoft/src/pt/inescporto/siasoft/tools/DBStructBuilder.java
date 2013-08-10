package pt.inescporto.siasoft.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableScripting;
import java.sql.DatabaseMetaData;
import java.util.Hashtable;
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
public class DBStructBuilder {
  private int lengthOfTask;
  private int current = 0;
  private boolean done = false;
  private boolean canceled = false;
  private String statMessage;

  private MsgPublisher poster = null;
  private Connection conn = null;
  private String catalog = null;
  private String schema = null;

  private Hashtable<String, TableNode> tableList = null;

  public DBStructBuilder(MsgPublisher poster, Connection conn, String catalog, String schema) {
    if (conn == null)
      throw new IllegalArgumentException("Argument <conn> must not be null!");
    if (schema == null)
      throw new IllegalArgumentException("Argument <schema> must not be null!");

    this.poster = poster;
    this.conn = conn;
    this.catalog = catalog;
    this.schema = schema;

    try {
      DatabaseMetaData dbMeta = conn.getMetaData();
      ResultSet rsTable = dbMeta.getTables(catalog, schema, null, new String[] {"TABLE"});
      lengthOfTask = 0;
      while (rsTable.next()) {
	lengthOfTask++;
      }
//      System.out.println("Length of task is <" + lengthOfTask + ">");
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
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
	return new DBStructReader(poster, conn, catalog, schema);
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
  public Hashtable<String, TableNode> getTableList() {
    return tableList;
  }

  /**
   * The actual long running task.  This runs in a SwingWorker thread.
   */
  public class DBStructReader {
    public DBStructReader(MsgPublisher poster, Connection conn, String catalog, String schema) {
      tableList = new Hashtable<String, TableNode>();

      try {
	DatabaseMetaData dbMeta = conn.getMetaData();

	ResultSet rsTable = dbMeta.getTables(catalog, schema, null, new String[] {"TABLE"});

	poster.setProgress("Starting...", current);

	while (rsTable.next()) {
	  current++;
	  TableNode table = new TableNode();
	  table.setTableName(rsTable.getString("TABLE_NAME"));
	  poster.setProgress("[" + current + "] Processing table " + table.getTableName() + " out of " + lengthOfTask + ".", current);

	  // build column list for this table
	  ResultSet rsTableDesc = dbMeta.getColumns(catalog, schema, table.getTableName(), null);
	  while (rsTableDesc.next()) {
	    TableColumn column = new TableColumn(rsTableDesc.getString("COLUMN_NAME"));
	    column.setDataType(new Integer(rsTableDesc.getInt("DATA_TYPE")));
	    column.setTypeName(rsTableDesc.getString("TYPE_NAME"));
	    column.setColumnSize(new Integer(rsTableDesc.getInt("COLUMN_SIZE")));
	    column.setDecimalDigits(new Integer(rsTableDesc.getInt("DECIMAL_DIGITS")));
	    column.setNullable(new Integer(rsTableDesc.getInt("NULLABLE")));
	    column.setOrdinalPosition(new Integer(rsTableDesc.getInt("ORDINAL_POSITION")));

	    // add column to table
	    table.addColumn(column);
	  }

	  // get primary key
	  ResultSet rsPK = dbMeta.getPrimaryKeys(catalog, schema, table.getTableName());
	  if (!rsPK.isLast()) {
	    TableIndex primaryKey = null;
	    boolean first = true;
	    while (rsPK.next()) {
	      if (first) {
                first = false;
		primaryKey = new TableIndex(rsPK.getString("PK_NAME"), TableIndex.UNIQUE);
	      }
	      primaryKey.addIndexField(new IndexField(rsPK.getString("COLUMN_NAME"), rsPK.getShort("KEY_SEQ"), IndexField.NOTDEF_ORDER));
	    }
	    table.setPrimaryKey(primaryKey);
	  }

	  // build index list for this table
	  ResultSet rsIndex = dbMeta.getIndexInfo(catalog, schema, table.getTableName(), true, true);
	  TableIndex lastIndex = null;
	  while (rsIndex.next()) {
	    if (lastIndex == null || (lastIndex != null && !lastIndex.getIndexName().equals(rsIndex.getString("INDEX_NAME")))) {
	      if (lastIndex != null) {
		if (table.getPrimaryKey() == null || (table.getPrimaryKey() != null && !table.getPrimaryKey().getIndexName().equals(lastIndex.getIndexName())))
		  table.addIndex(lastIndex);
	      }
	      lastIndex = new TableIndex(rsIndex.getString("INDEX_NAME"), rsIndex.getBoolean("NON_UNIQUE") ? TableIndex.UNIQUE : TableIndex.NONUNIQUE);
	    }

	    IndexField field = new IndexField(rsIndex.getString("COLUMN_NAME"), rsIndex.getShort("ORDINAL_POSITION"), IndexField.ASC_ORDER);
	    lastIndex.addIndexField(field);
	  }
	  if (lastIndex != null) {
	    if (table.getPrimaryKey() == null || (table.getPrimaryKey() != null && !table.getPrimaryKey().getIndexName().equals(lastIndex.getIndexName())))
	      table.addIndex(lastIndex);
	  }

	  // add imported Key Constraint
	  ResultSet rsImpRefs = dbMeta.getImportedKeys(catalog, schema, table.getTableName());
	  TableConstraint importedKey = null;
	  while (rsImpRefs.next()) {
	    if (importedKey == null || (importedKey != null && !importedKey.getConstraintName().equals(rsImpRefs.getString("FK_NAME")))) {
	      if (importedKey != null)
		table.addImportedConstraint(importedKey);

	      importedKey = new TableConstraint(rsImpRefs.getString("PKTABLE_NAME"), rsImpRefs.getString("FK_NAME"));
	    }

	    KeyReference keyRef = new KeyReference(rsImpRefs.getString("PKCOLUMN_NAME"), rsImpRefs.getString("FKCOLUMN_NAME"), rsImpRefs.getShort("KEY_SEQ"));
	    importedKey.addKeyReference(keyRef);
	  }
	  if (importedKey != null)
	    table.addImportedConstraint(importedKey);

	  // add imported Key Constraint
	  ResultSet rsExpRefs = dbMeta.getExportedKeys(catalog, schema, table.getTableName());
	  TableConstraint exportedKey = null;
	  while (rsExpRefs.next()) {
	    if (exportedKey == null || (exportedKey != null && !exportedKey.getConstraintName().equals(rsExpRefs.getString("FK_NAME")))) {
	      if (exportedKey != null)
		table.addExportedConstraint(exportedKey);

	      exportedKey = new TableConstraint(rsExpRefs.getString("FKTABLE_NAME"), rsExpRefs.getString("FK_NAME"));
	    }

	    KeyReference keyRef = new KeyReference(rsExpRefs.getString("PKCOLUMN_NAME"), rsExpRefs.getString("FKCOLUMN_NAME"), rsExpRefs.getShort("KEY_SEQ"));
	    exportedKey.addKeyReference(keyRef);
	  }
	  if (exportedKey != null)
	    table.addExportedConstraint(exportedKey);

	  // add table to list
	  table.setSelectAllStmt(TableScripting.getSelectTableScript(table));
	  table.setInsertStmt(TableScripting.getInsertTableScript(table));
	  table.setUpdateStmt(TableScripting.getUpdateTableScript(table));
	  tableList.put(table.getTableName(), table);
	}
      }
      catch (SQLException ex) {
	ex.printStackTrace();
      }

      done = true;
      current = lengthOfTask;
      poster.setProgress("End.", current);
    }
  }
}
