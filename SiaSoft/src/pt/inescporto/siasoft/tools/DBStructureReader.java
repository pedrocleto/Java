package pt.inescporto.siasoft.tools;

import java.util.Hashtable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import pt.inescporto.template.client.design.thirdparty.ProgressMonitor;
import pt.inescporto.template.client.design.thirdparty.ProgressUtil;
import javax.swing.JFrame;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableScripting;

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
public class DBStructureReader extends Thread {
  public static int SOURCE = 1;
  public static int DESTINY = 2;
  private JFrame owner = null;
  private AnalizerEventListener listener = null;
  private MessagePoster poster = null;
  private Connection conn = null;
  private String catalog = null;
  private String schema = null;
  private int type = 0;
  private boolean started = false;
  private boolean done = false;

  public DBStructureReader(JFrame owner, AnalizerEventListener listener, MessagePoster poster, Connection conn, String catalog, String schema, int type) {
    if (listener == null)
      throw new IllegalArgumentException("Argument <listener> must not be null!");
    if (conn == null)
      throw new IllegalArgumentException("Argument <conn> must not be null!");
    if (schema == null)
      throw new IllegalArgumentException("Argument <schema> must not be null!");

    this.owner = owner;
    this.listener = listener;
    this.poster = poster;
    this.conn = conn;
    this.catalog = catalog;
    this.schema = schema;
    this.type = type;
  }

  public boolean isDone() {
    return done;
  }

  public boolean isStarted() {
    return started;
  }

  public void run() {
    started = true;
    Hashtable<String, TableNode> tableList = new Hashtable<String, TableNode>();

    try {
      DatabaseMetaData dbMeta = conn.getMetaData();

      ResultSet rsTable = dbMeta.getTables(catalog, schema, null, new String[] {"TABLE"});

      ProgressMonitor monitor = ProgressUtil.createModalProgressMonitor(owner, Integer.MAX_VALUE, true, 100);
      monitor.start("Starting...");

      while (rsTable.next()) {
	TableNode table = new TableNode();
	table.setTableName(rsTable.getString("TABLE_NAME"));
        monitor.setCurrent("Processing table " + table.getTableName(), tableList.size());

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
      monitor.setCurrent("End.", Integer.MAX_VALUE);
    }
    catch (SQLException ex) {
      poster.postMessage(ex.getMessage());
    }

    switch(type) {
      case 1:
        listener.setSourceStructure(tableList);
        break;
      case 2:
        listener.setTargetStructure(tableList);
        break;
    }

    done = true;
    started = false;
  }
}
