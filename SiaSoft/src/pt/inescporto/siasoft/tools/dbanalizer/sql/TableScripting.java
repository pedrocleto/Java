package pt.inescporto.siasoft.tools.dbanalizer.sql;

import pt.inescporto.siasoft.tools.TableNode;
import java.util.Iterator;
import pt.inescporto.siasoft.tools.TableColumn;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.IndexField;
import pt.inescporto.siasoft.tools.TableConstraint;
import pt.inescporto.siasoft.tools.KeyReference;
import java.util.Vector;

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
public class TableScripting {
  private TableScripting() {
  }

  public static StringBuffer getCreateTableScript(TableNode table) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("CREATE TABLE " + table.getTableName() + " (" + "\r\n");

    for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext(); ) {
      TableColumn column = columns.next();

      buffer.append(getFieldScript(column) + (columns.hasNext() ? ",\r\n" : ""));
    }

    // create primary key
    if (table.getPrimaryKey() != null) {
      buffer.append(",\r\n");

      TableIndex index = table.getPrimaryKey();

      buffer.append("constraint " + index.getIndexName() + " primary key (");
      for (Iterator<IndexField> fields = index.getIndexFieldList(); fields.hasNext(); ) {
	IndexField field = fields.next();
	buffer.append(field.getFieldName() + (fields.hasNext() ? ", " : ""));
      }
      buffer.append(")");
    }

    // create foreign key's
    if (table.getImportedConstraintlist().hasNext())
      buffer.append(",\r\n");
    for (Iterator<TableConstraint> fKeys = table.getImportedConstraintlist(); fKeys.hasNext(); ) {
      TableConstraint fKey = fKeys.next();
      String keysTable = "(";
      String keysForeignTable = "(";

      for (Iterator<KeyReference> keyRefs = fKey.getKeyReferencesList(); keyRefs.hasNext(); ) {
	KeyReference keyRef = keyRefs.next();

	keysTable += keyRef.getFkField() + (keyRefs.hasNext() ? ", " : "");
	keysForeignTable += keyRef.getPkField() + (keyRefs.hasNext() ? ", " : "");
      }
      keysTable += ")";
      keysForeignTable += ")";

      buffer.append("constraint " + fKey.getConstraintName() + " foreign key" + keysTable + " references " + fKey.getPkTableName() + keysForeignTable);
      buffer.append("" + (fKeys.hasNext() ? ",\r\n" : "\r\n"));
    }

    buffer.append(");\r\n");

    // create index
    for (Iterator<TableIndex> indexes = table.getIndexlist(); indexes.hasNext(); ) {
      TableIndex index = indexes.next();

      buffer.append("CREATE " + (index.getIndexType() == TableIndex.UNIQUE ? "UNIQUE" : "") + " INDEX " + index.getIndexName() + " ON " + table.getTableName() + "(");
      for (Iterator<IndexField> fields = index.getIndexFieldList(); fields.hasNext(); ) {
	IndexField field = fields.next();
	buffer.append(field.getFieldName() + (fields.hasNext() ? ", " : ""));
      }
      buffer.append(")\r\n");
    }

    return buffer;
  }

  public static StringBuffer getDropTableScript(TableNode table) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("DROP TABLE " + table.getTableName() + " CASCADE;" + "\r\n");

    return buffer;
  }

  public static StringBuffer getAddColumnTableScript(String tableName, TableColumn column) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("ALTER TABLE " + tableName + " ADD COLUMN " + getFieldScript(column) + ";\r\n");

    return buffer;
  }

  public static StringBuffer getDropColumnTableScript(String tableName, String columnName) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("ALTER TABLE " + tableName + " DROP COLUMN " + columnName + ";\r\n");

    return buffer;
  }

  public static StringBuffer addPrimaryKeyTableScript(String tableName, TableIndex primaryKey) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("ALTER TABLE " + tableName + " ADD CONSTRAINT " + primaryKey.getIndexName() + " PRIMARY KEY (");
    for (Iterator<IndexField> fields = primaryKey.getIndexFieldList(); fields.hasNext(); ) {
      IndexField field = fields.next();
      buffer.append(field.getFieldName() + (fields.hasNext() ? ", " : ""));
    }
    buffer.append(");\r\n");

    return buffer;
  }

  public static StringBuffer getAddConstraintTableScript(String tableName, TableConstraint constraint) {
    StringBuffer buffer = new StringBuffer();

    String keysTable = "(";
    String keysForeignTable = "(";

    for (Iterator<KeyReference> keyRefs = constraint.getKeyReferencesList(); keyRefs.hasNext(); ) {
      KeyReference keyRef = keyRefs.next();

      keysTable += keyRef.getFkField() + (keyRefs.hasNext() ? ", " : "");
      keysForeignTable += keyRef.getPkField() + (keyRefs.hasNext() ? ", " : "");
    }
    keysTable += ")";
    keysForeignTable += ")";

    buffer.append("ALTER TABLE " + tableName + " ADD CONSTRAINT " + constraint.getConstraintName() + " FOREIGN " + keysTable + " REFERENCES " + constraint.getPkTableName() + keysForeignTable +
		  ";\r\n");

    return buffer;
  }

  public static StringBuffer getDropConstraintTableScript(String tableName, String indexName) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("ALTER TABLE " + tableName + " DROP CONSTRAINT " + indexName + ";\r\n");

    return buffer;
  }

  public static StringBuffer getDropIndexScript(String tableName, String indexName) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("DROP INDEX " + indexName + ";\r\n");

    return buffer;
  }

  public static StringBuffer getCreateIndexScript(String tableName, TableIndex index) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("CREATE " + (index.getIndexType() == TableIndex.UNIQUE ? "UNIQUE" : "") + " INDEX " + index.getIndexName() + " ON " + tableName + "(");

    for (Iterator<IndexField> fields = index.getIndexFieldList(); fields.hasNext(); ) {
      IndexField field = fields.next();
      buffer.append(field.getFieldName() + (fields.hasNext() ? ", " : ""));
    }
    buffer.append(")\r\n");

    return buffer;
  }

  public static StringBuffer getSelectTableScript(TableNode table) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("SELECT ");

    for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext(); ) {
      TableColumn column = columns.next();

      buffer.append(column.getColumnName() + (columns.hasNext() ? ", " : ""));
    }

    buffer.append(" FROM " + table.getTableName());

    String orderBy = "";
    if (table.getPrimaryKey() != null) {
      for (Iterator<IndexField> ifList = table.getPrimaryKey().getIndexFieldList(); ifList.hasNext(); ) {
	IndexField field = ifList.next();
	orderBy += field.getFieldName() + (ifList.hasNext() ? ", " : "");
      }

      buffer.append(" ORDER BY " + orderBy);
    }
    else {
      // order by all
      for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext(); ) {
	TableColumn column = columns.next();

	orderBy += (column.getColumnName() + (columns.hasNext() ? ", " : ""));
      }

      buffer.append(" ORDER BY " + orderBy);
    }

    return buffer;
  }

  public static StringBuffer getInsertTableScript(TableNode table) {
    StringBuffer buffer = new StringBuffer();

    buffer.append("INSERT INTO " + table.getTableName() + " (");

    String tableValues = "";
    for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext(); ) {
      TableColumn column = columns.next();

      buffer.append(column.getColumnName() + (columns.hasNext() ? ", " : ""));
      tableValues += "?" + (columns.hasNext() ? ", " : "");
    }

    buffer.append(") VALUES (" + tableValues + ")");

    return buffer;
  }

  public static StringBuffer getUpdateTableScript(TableNode table) {
    // if there isn't a primary key then this script doesn't work
    if (table.getPrimaryKey() == null)
      return null;

    // lets do the work
    StringBuffer buffer = new StringBuffer();

    buffer.append("UPDATE " + table.getTableName() + " SET ");

    for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext(); ) {
      TableColumn column = columns.next();

      boolean isPK = false;
      for (Iterator<IndexField> iFields = table.getPrimaryKey().getIndexFieldList(); iFields.hasNext(); ) {
	IndexField iField = iFields.next();
	if (iField.getFieldName().equals(column.getColumnName())) {
	  isPK = true;
	  break;
	}
      }
      if (!isPK) {
	buffer.append(column.getColumnName() + " = ?" + (columns.hasNext() ? ", " : ""));
      }
    }

    buffer.append(" WHERE ");
    for (Iterator<IndexField> iFields = table.getPrimaryKey().getIndexFieldList(); iFields.hasNext(); ) {
      IndexField iField = iFields.next();
      buffer.append(iField.getFieldName() + " = ?" + (iFields.hasNext() ? " AND " : ""));
    }

    return buffer;
  }

  private static String getFieldScript(TableColumn column) {
    SqlTypes sqlTypes = new SqlTypes();
    StringBuffer buffer = new StringBuffer();

    //    buffer.append(column.getColumnName() + " " + column.getTypeName());
    buffer.append(column.getColumnName() + " " + sqlTypes.getSQLtypeName(column.getDataType(), column.getColumnSize(), column.getDecimalDigits()));
/*    if (column.getColumnSize().intValue() > 0) {
      buffer.append("(" + column.getColumnSize().intValue());
      if (column.getDecimalDigits().intValue() > 0)
	buffer.append(", " + column.getDecimalDigits().intValue());
      buffer.append(")");
    }*/
    buffer.append(" " + (column.getNullable().intValue() == 0 ? "NOT NULL" : "NULL"));

    return buffer.toString();
  }
}
