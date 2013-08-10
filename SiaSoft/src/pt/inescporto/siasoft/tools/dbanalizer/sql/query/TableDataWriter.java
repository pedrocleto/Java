package pt.inescporto.siasoft.tools.dbanalizer.sql.query;

import java.util.Vector;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import pt.inescporto.siasoft.tools.TableNode;
import pt.inescporto.siasoft.tools.TableColumn;
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
public class TableDataWriter {
  private TableDataWriter() {
  }

  public static void saveData(MessagePoster poster, Connection conn, TableNode table, TableDataNode data) {
    poster.postMessage("Processing data for table <" + table.getTableName() + ">");

    // get mapping columns
    Vector<Integer> colTypes = new Vector<Integer>();
    for (Iterator<TableColumn> columnList = table.getColumnlist(); columnList.hasNext(); ) {
      TableColumn tc = columnList.next();
      colTypes.add(tc.getDataType());
    }

    // first let's delete data

    // second let's update data
    try {
      if (data.getAllRows4update() != null) {
	String stmt = table.getUpdateStmt().toString().replace(';', ' ');
	PreparedStatement ps = conn.prepareStatement(stmt);
	int parIndex = 1;
	for (Vector row : data.getAllRows4update()) {
	  ps.setObject(parIndex, row.elementAt(parIndex - 1), colTypes.elementAt(parIndex).intValue());
	  parIndex++;
	}
      }
    }
    catch (SQLException ex) {
      poster.postMessage(ex.getMessage());
    }

    // second let's insert data
    try {
      if (data.getAllRows4insert() != null) {
        String stmt = table.getInsertStmt().toString().replace(';', ' ');
        PreparedStatement ps = conn.prepareStatement(stmt);
        for (Vector row : data.getAllRows4insert()) {
          int parIndex = 1;
          for (Object value : row) {
	    if (value != null)
	      ps.setObject(parIndex, value, colTypes.elementAt(parIndex - 1).intValue());
	    else
	      ps.setNull(parIndex, colTypes.elementAt(parIndex - 1).intValue());
	    parIndex++;
	  }
          ps.executeUpdate();
        }
      }
    }
    catch (SQLException ex) {
      poster.postMessage(ex.getMessage());
    }

    poster.postMessage("End processing.");
  }
}
