package pt.inescporto.siasoft.tools.dbanalizer.sql.query;

import pt.inescporto.siasoft.tools.TableNode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import pt.inescporto.siasoft.tools.TableColumn;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Date;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.IndexField;

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
public class TableData {
  private transient Connection connSource;
  private transient Connection connTarget;

  public TableData(Connection connSource, Connection connTarget) {
    this.connSource = connSource;
    this.connTarget = connTarget;
  }

  public Vector loadData(TableNode table) throws SQLException {
    PreparedStatement stmtSource = connSource.prepareStatement(table.getSelectAllStmt().toString() + " order by 1" );
    ResultSet rsSource = stmtSource.executeQuery();

    Vector valuesSource = new Vector();

    long elapsed = System.currentTimeMillis();
//    System.out.println("Starting at " + new Date(elapsed).toString());

    while (rsSource.next()) {
      Vector row = new Vector();
      for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext();) {
        TableColumn column = columns.next();
        row.add(rsSource.getObject(column.getOrdinalPosition().intValue()));
      }
      valuesSource.add(row);
    }

    PreparedStatement stmtTarget = connTarget.prepareStatement(table.getSelectAllStmt().toString() + " order by 1");
    ResultSet rsTarget = stmtTarget.executeQuery();

    Vector valuesTarget = new Vector();

    while (rsTarget.next()) {
      Vector row = new Vector();
      for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext();) {
        TableColumn column = columns.next();
        row.add(rsTarget.getObject(column.getOrdinalPosition().intValue()));
      }
      valuesTarget.add(row);
    }

    // get primary keys
    if (table.getPrimaryKey() != null) {
      Vector<Integer> pkOrdinalNumber = new Vector();

      for (Iterator<IndexField> ifList = table.getPrimaryKey().getIndexFieldList(); ifList.hasNext();) {
        IndexField field = ifList.next();
        pkOrdinalNumber.add(new Integer(field.getOrdinalPosition()));
      }

      int rowCountSource = 0;
      int rowCountTarget = 0;
      while (rowCountSource < valuesSource.size() || rowCountTarget < valuesTarget.size()) {
        Vector rowSource = (Vector)valuesSource.elementAt(rowCountSource);
	Vector rowTarget = (Vector)valuesTarget.elementAt(rowCountTarget);

	for (Integer o : pkOrdinalNumber) {
          Object sourceValue = rowSource.elementAt(o-1);
          Object targetValue = rowTarget.elementAt(o-1);
          if (sourceValue instanceof java.lang.String) {
            int c = ((String)sourceValue).compareTo(((String)rowTarget.elementAt(o-1)));
            if (c < 0) {
//              System.out.println("Source row " + rowCountSource + " missing.");
              rowCountSource++;
            }
            if (c > 0) {
//              System.out.println("Target row " + rowCountTarget + " missing.");
              rowCountTarget++;
            }
            if (c == 0) {
              if (rowCountSource < valuesSource.size())
                rowCountSource++;
              if (rowCountTarget < valuesTarget.size())
                rowCountTarget++;
            }
          }
	}
      }
    }
    else {
      for (int rowCountSource = 0; rowCountSource < valuesSource.size(); rowCountSource++) {
	Vector rowSource = (Vector)valuesSource.elementAt(rowCountSource);
	for (int rowCountTarget = 0; rowCountTarget < valuesTarget.size(); rowCountTarget++) {
	  Vector rowTarget = (Vector)valuesTarget.elementAt(rowCountTarget);

	  for (int columnCount = 0; columnCount < rowSource.size(); columnCount++) {
	  }
	}
      }
    }

    long end = System.currentTimeMillis();
//    System.out.println("Ended at " + new Date(end).toString());
//    System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + valuesSource.size() + " records and target has " + valuesTarget.size() + " records");

    return valuesSource;
  }
}
