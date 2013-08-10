package pt.inescporto.siasoft.tools.dbanalizer.sql.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.Vector;
import java.util.Date;

import pt.inescporto.siasoft.tools.TableNode;
import pt.inescporto.siasoft.tools.TableIndex;
import pt.inescporto.siasoft.tools.IndexField;
import pt.inescporto.siasoft.tools.TableColumn;

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
public class TableDataReader {
  private TableDataReader() {
  }

  public static TableDataNode loadData(Connection connSource, Connection connTarget, TableNode tableSource, TableNode tableTarget) throws SQLException {
    TableDataNode dataNode = new TableDataNode(tableSource.getTableName());
    Vector<Vector> valuesSource = null;
    Vector<Vector> valuesTarget = null;

    long elapsed = System.currentTimeMillis();
//    System.out.println("Starting at " + new Date(elapsed).toString() + " processing table <" + tableSource.getTableName() + ">");

    // if source table doesn't exist, then there's nothing to do for now!
    if (tableSource != null) {
      // make some processing ahead
      boolean targetExists = tableTarget != null;
      int columnCountDiff = -1;
      if (targetExists) {
        columnCountDiff = (new Integer(tableSource.getColumnCount())).compareTo(new Integer(tableTarget.getColumnCount()));
      }

      // process source table node
      String sSelectStmt = columnCountDiff <= 0 ? tableSource.getSelectAllStmt().toString() : tableTarget.getSelectAllStmt().toString();
      sSelectStmt = tableSource.getSelectAllStmt().toString();
      PreparedStatement stmtSource = connSource.prepareStatement(sSelectStmt);
      ResultSet rsSource = stmtSource.executeQuery();
      valuesSource = getDataVector(rsSource, tableSource);

      if (targetExists) {
	// process target table
	PreparedStatement stmtTarget = connTarget.prepareStatement(tableTarget.getSelectAllStmt().toString());
	ResultSet rsTarget = stmtTarget.executeQuery();
	valuesTarget = getDataVector(rsTarget, tableTarget);

        // compare column count and build a mean mapping columns
        int sourceColumnCount = stmtSource.getMetaData().getColumnCount();
        int targetColumnCount = stmtTarget.getMetaData().getColumnCount();
        Integer colMap[][] = new Integer[max(sourceColumnCount, targetColumnCount)][2];

	if (sourceColumnCount != targetColumnCount) {
//	  System.out.println("Column count doesn't match on table <" + tableSource.getTableName() + ">.");
	}
        buildColumnMapping(colMap, tableSource.getColumnlist(), tableTarget.getColumnlist());

        // is there's no data then theres nothing to do
	if (valuesSource.size() == 0) {
	  long end = System.currentTimeMillis();
//	  System.out.print("Ended at " + new Date(end).toString() + ". ");
//	  System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + valuesSource.size() + " records and target has " + valuesTarget.size() + " records");
	  return null;
	}

        // if there's no data in target then all data will be inserted
	if (valuesTarget.size() == 0) {
	  long end = System.currentTimeMillis();
//	  System.out.println("All records will be inserted.");
	  for (Vector row : valuesSource) {
	    dataNode.addRow2Insert(row);
	  }
//	  System.out.print("Ended at " + new Date(end).toString() + ". ");
	  //System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + valuesSource.size() + " records and target has " + valuesTarget.size() + " records");
	  return dataNode;
	}

        // At this point source and target table exists and contains data so lets do the work!
	// get primary keys
	if (tableSource.getPrimaryKey() != null) {
	  Vector<Integer> pkOrdinalNumber = new Vector();

	  for (Iterator<IndexField> ifList = tableSource.getPrimaryKey().getIndexFieldList(); ifList.hasNext(); ) {
	    IndexField field = ifList.next();
	    pkOrdinalNumber.add(new Integer(field.getOrdinalPosition()));
	  }

	  int rowCountSource = 0;
	  int rowCountTarget = 0;
	  int lastRCS = -1;
	  int lastRCT = -1;
	  boolean sourceEnded = false;
	  boolean targetEnded = false;
	  while (!sourceEnded || !targetEnded) {
	    if (rowCountSource == lastRCS && rowCountTarget == lastRCT) {
//	      System.out.println("PANIC! - Source <" + rowCountSource + ", " + valuesSource.size() + ">, Target <" + rowCountTarget + ", " + valuesTarget.size() + ">");
	      break;
	    }
	    lastRCS = rowCountSource;
	    lastRCT = rowCountTarget;

	    Vector rowSource = (Vector)valuesSource.elementAt(rowCountSource == valuesSource.size() ? rowCountSource - 1 : rowCountSource);
	    Vector rowTarget = (Vector)valuesTarget.elementAt(rowCountTarget == valuesTarget.size() ? rowCountTarget - 1 : rowCountTarget);

	    if (rowSource == null)
	      System.out.println("RowSource is null!");
	    if (rowTarget == null)
	      System.out.println("RowTarget is null!");

	    for (int i = 0; i < pkOrdinalNumber.size(); i++) {
	      Integer o = pkOrdinalNumber.elementAt(i);
	      Object sourceValue = rowSource.elementAt(o - 1);
	      Object targetValue = rowTarget.elementAt(o - 1);

	      int c = 0;
	      if (!sourceEnded && !targetEnded) {
		if (sourceValue instanceof java.lang.String) {
		  c = ((String)sourceValue).compareTo(((String)rowTarget.elementAt(o - 1)));
		}
		if (sourceValue instanceof java.lang.Integer) {
		  c = ((Integer)sourceValue).compareTo(((Integer)rowTarget.elementAt(o - 1)));
		}
		if (sourceValue instanceof java.lang.Long) {
		  c = ((Long)sourceValue).compareTo(((Long)rowTarget.elementAt(o - 1)));
		}
		if (sourceValue instanceof java.sql.Date) {
		  c = ((Date)sourceValue).compareTo(((Date)rowTarget.elementAt(o - 1)));
		}
	      }
	      if (sourceEnded && !targetEnded)
		c = 1;
	      if (!sourceEnded && targetEnded)
		c = -1;
	      if (c < 0) {
//		System.out.println("Source row " + (rowCountSource + 1) + " missing.");
		// test next source record
		if (!sourceEnded) {
		  rowCountSource++;
		  if (rowCountSource >= valuesSource.size())
		    sourceEnded = true;
		  dataNode.addRow2Insert(rowSource);
		}
		break;
	      }
	      if (c > 0) {
//		System.out.println("Target row " + (rowCountTarget + 1) + " missing.");
		// test next target record
		if (!targetEnded) {
		  rowCountTarget++;
		  if (rowCountTarget >= valuesTarget.size())
		    targetEnded = true;
		}
		break;
	      }
	      if (c == 0) {
		if (i < pkOrdinalNumber.size() - 1)
		  continue;

		// ok, primary keys match, let's test the rest if update is necessary!
		if (!compareRows(rowSource, rowTarget, pkOrdinalNumber, colMap)) {
//		  System.out.println("Target row " + (rowCountTarget + 1) + " marked for update.");
		  dataNode.addRow2Update(getRowForUpdate(rowSource, pkOrdinalNumber));
		}

		// test next to columns
		if (!sourceEnded) {
		  rowCountSource++;
		  if (rowCountSource >= valuesSource.size())
		    sourceEnded = true;
		}
		if (!targetEnded) {
		  rowCountTarget++;
		  if (rowCountTarget >= valuesTarget.size())
		    targetEnded = true;
		}
	      }
	    }
	  }
	}
	else {
//          System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + tableSource.getTableName() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
/*	  for (int rowCountSource = 0; rowCountSource < valuesSource.size(); rowCountSource++) {
	    Vector rowSource = (Vector)valuesSource.elementAt(rowCountSource);
	    for (int rowCountTarget = 0; rowCountTarget < valuesTarget.size(); rowCountTarget++) {
	      Vector rowTarget = (Vector)valuesTarget.elementAt(rowCountTarget);

	      for (int columnCount = 0; columnCount < rowSource.size(); columnCount++) {
	      }
	    }
	  }*/
	}
      }
      else {
	// all values will be inserted!
//        System.out.println("all values will be inserted!");
	for (Vector row : valuesSource) {
	  dataNode.addRow2Insert(row);
	}
      }

    }

    long end = System.currentTimeMillis();
/*    System.out.print("Ended at " + new Date(end).toString() + ". ");
    System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + (valuesSource == null ? "0" : valuesSource.size()) + " records and target has " +
		       (valuesTarget != null ? valuesTarget.size() : "0") + " records");*/

    return dataNode;
  }

  public static TableDataNode loadData(Connection connSource, TableDataNode dataNodeTarget, TableNode tableSource, TableNode tableTarget) throws SQLException {
    TableDataNode dataNode = new TableDataNode(tableSource.getTableName());
    Vector<Vector> valuesSource = null;
    Vector<Vector> valuesTarget = null;

    long elapsed = System.currentTimeMillis();
//    System.out.println("Starting at " + new Date(elapsed).toString() + " processing table <" + tableSource.getTableName() + ">");

    // if source table doesn't exist, then there's nothing to do for now!
    if (tableSource != null) {
      // make some processing ahead
      boolean targetExists = tableTarget != null;
      int columnCountDiff = -1;
      if (targetExists) {
        columnCountDiff = (new Integer(tableSource.getColumnCount())).compareTo(new Integer(tableTarget.getColumnCount()));
      }

      // process source table node
      String sSelectStmt = columnCountDiff <= 0 ? tableSource.getSelectAllStmt().toString() : tableTarget.getSelectAllStmt().toString();
      sSelectStmt = tableSource.getSelectAllStmt().toString();
      PreparedStatement stmtSource = connSource.prepareStatement(sSelectStmt);
      ResultSet rsSource = stmtSource.executeQuery();
      valuesSource = getDataVector(rsSource, tableSource);

      if (dataNodeTarget != null && dataNodeTarget.getAllRows4insert() != null) {
        // process target table
        valuesTarget = dataNodeTarget.getAllRows4insert();

        // compare column count and build a mean mapping columns
        int sourceColumnCount = stmtSource.getMetaData().getColumnCount();
        int targetColumnCount = ((Vector)valuesTarget.firstElement()).size();
        Integer colMap[][] = new Integer[max(sourceColumnCount, targetColumnCount)][2];

        if (sourceColumnCount != targetColumnCount) {
//          System.out.println("Column count doesn't match on table <" + tableSource.getTableName() + ">.");
        }
        buildColumnMapping(colMap, tableSource.getColumnlist(), tableTarget.getColumnlist());

        // is there's no data then theres nothing to do
        if (valuesSource.size() == 0) {
          long end = System.currentTimeMillis();
/*          System.out.print("Ended at " + new Date(end).toString() + ". ");
          System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + valuesSource.size() + " records and target has " + valuesTarget.size() + " records");*/
          return null;
        }

        // if there's no data in target then all data will be inserted
        if (valuesTarget.size() == 0) {
          long end = System.currentTimeMillis();
//          System.out.println("All records will be inserted.");
          for (Vector row : valuesSource) {
            dataNode.addRow2Insert(row);
          }
/*          System.out.print("Ended at " + new Date(end).toString() + ". ");
          System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + valuesSource.size() + " records and target has " + valuesTarget.size() + " records");*/
          return dataNode;
        }

        // At this point source and target table exists and contains data so lets do the work!
        // get primary keys
        if (tableSource.getPrimaryKey() != null) {
          Vector<Integer> pkOrdinalNumber = new Vector();

          for (Iterator<IndexField> ifList = tableSource.getPrimaryKey().getIndexFieldList(); ifList.hasNext(); ) {
            IndexField field = ifList.next();
            pkOrdinalNumber.add(new Integer(field.getOrdinalPosition()));
          }

          int rowCountSource = 0;
          int rowCountTarget = 0;
          int lastRCS = -1;
          int lastRCT = -1;
          boolean sourceEnded = false;
          boolean targetEnded = false;
          while (!sourceEnded || !targetEnded) {
            if (rowCountSource == lastRCS && rowCountTarget == lastRCT) {
//              System.out.println("PANIC! - Source <" + rowCountSource + ", " + valuesSource.size() + ">, Target <" + rowCountTarget + ", " + valuesTarget.size() + ">");
              break;
            }
            lastRCS = rowCountSource;
            lastRCT = rowCountTarget;

            Vector rowSource = (Vector)valuesSource.elementAt(rowCountSource == valuesSource.size() ? rowCountSource - 1 : rowCountSource);
            Vector rowTarget = (Vector)valuesTarget.elementAt(rowCountTarget == valuesTarget.size() ? rowCountTarget - 1 : rowCountTarget);

/*            if (rowSource == null)
              System.out.println("RowSource is null!");
            if (rowTarget == null)
              System.out.println("RowTarget is null!");*/

            for (int i = 0; i < pkOrdinalNumber.size(); i++) {
              Integer o = pkOrdinalNumber.elementAt(i);
              Object sourceValue = rowSource.elementAt(o - 1);
              Object targetValue = rowTarget.elementAt(o - 1);

              int c = 0;
              if (!sourceEnded && !targetEnded) {
                if (sourceValue instanceof java.lang.String) {
                  c = ((String)sourceValue).compareTo(((String)rowTarget.elementAt(o - 1)));
                }
                if (sourceValue instanceof java.lang.Integer) {
                  c = ((Integer)sourceValue).compareTo(((Integer)rowTarget.elementAt(o - 1)));
                }
                if (sourceValue instanceof java.lang.Long) {
                  c = ((Long)sourceValue).compareTo(((Long)rowTarget.elementAt(o - 1)));
                }
                if (sourceValue instanceof java.sql.Date) {
                  c = ((Date)sourceValue).compareTo(((Date)rowTarget.elementAt(o - 1)));
                }
              }
              if (sourceEnded && !targetEnded)
                c = 1;
              if (!sourceEnded && targetEnded)
                c = -1;
              if (c < 0) {
//                System.out.println("Source row " + (rowCountSource + 1) + " missing.");
                // test next source record
                if (!sourceEnded) {
                  rowCountSource++;
                  if (rowCountSource >= valuesSource.size())
                    sourceEnded = true;
                  dataNode.addRow2Insert(rowSource);
                }
                break;
              }
              if (c > 0) {
//                System.out.println("Target row " + (rowCountTarget + 1) + " missing.");
                // test next target record
                if (!targetEnded) {
                  rowCountTarget++;
                  if (rowCountTarget >= valuesTarget.size())
                    targetEnded = true;
                }
                break;
              }
              if (c == 0) {
                if (i < pkOrdinalNumber.size() - 1)
                  continue;

                // ok, primary keys match, let's test the rest if update is necessary!
                if (!compareRows(rowSource, rowTarget, pkOrdinalNumber, colMap)) {
//                  System.out.println("Target row " + (rowCountTarget + 1) + " marked for update.");
                  dataNode.addRow2Update(getRowForUpdate(rowSource, pkOrdinalNumber));
                }

                // test next to columns
                if (!sourceEnded) {
                  rowCountSource++;
                  if (rowCountSource >= valuesSource.size())
                    sourceEnded = true;
                }
                if (!targetEnded) {
                  rowCountTarget++;
                  if (rowCountTarget >= valuesTarget.size())
                    targetEnded = true;
                }
              }
            }
          }
        }
        else {
//          System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + tableSource.getTableName() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
/*	  for (int rowCountSource = 0; rowCountSource < valuesSource.size(); rowCountSource++) {
            Vector rowSource = (Vector)valuesSource.elementAt(rowCountSource);
            for (int rowCountTarget = 0; rowCountTarget < valuesTarget.size(); rowCountTarget++) {
              Vector rowTarget = (Vector)valuesTarget.elementAt(rowCountTarget);

              for (int columnCount = 0; columnCount < rowSource.size(); columnCount++) {
              }
            }
          }*/
        }
      }
      else {
        // all values will be inserted!
//        System.out.println("all values will be inserted!");
        for (Vector row : valuesSource) {
          dataNode.addRow2Insert(row);
        }
      }

    }

    long end = System.currentTimeMillis();
/*    System.out.print("Ended at " + new Date(end).toString() + ". ");
    System.out.println("Took " + ((double)end - (double)elapsed) / (double)1000 + " seconds. Source has " + (valuesSource == null ? "0" : valuesSource.size()) + " records and target has " +
                       (valuesTarget != null ? valuesTarget.size() : "0") + " records");*/

    return dataNode;
  }

  private static boolean compareRows(Vector rowSource, Vector rowTarget, Vector keys, Integer[][]colMap) {
    boolean match = true;

    for (int i = 0; i < rowSource.size(); i++) {
      if (keys == null || (keys != null && !keys.contains(new Integer(i)))) {
        Integer sourceColumnPos = colMap[i][0];
        Integer targetColumnPos = colMap[i][1];

	Object sourceValue = rowSource.elementAt(sourceColumnPos);
	Object targetValue = targetColumnPos == null ? null : rowTarget.elementAt(targetColumnPos);

	int c = 0;
	if (sourceValue == null && targetValue != null)
          c = -1;
	if (sourceValue != null && targetValue == null)
	  c = 1;
	if (sourceValue != null && targetValue != null) {
          if (!sourceValue.getClass().getName().equals(targetValue.getClass().getName())) {
//	    System.out.println("---> Source value type <" + sourceValue.getClass().getName() + "> doesn't match target values type <" + targetValue.getClass().getName() + ">.<---");
            return false;
	  }

	  if (sourceValue instanceof java.lang.String) {
	    c = ((String)sourceValue).compareTo(((String)targetValue));
	  }
	  if (sourceValue instanceof java.lang.Integer) {
	    c = ((Integer)sourceValue).compareTo(((Integer)targetValue));
	  }
	  if (sourceValue instanceof java.lang.Long) {
	    c = ((Long)sourceValue).compareTo(((Long)targetValue));
	  }
	  if (sourceValue instanceof java.lang.Float) {
	    c = ((Float)sourceValue).compareTo(((Float)targetValue));
	  }
	  if (sourceValue instanceof java.lang.Boolean) {
	    c = ((Boolean)sourceValue).compareTo(((Boolean)targetValue));
	  }
	  if (sourceValue instanceof java.sql.Date) {
	    c = ((Date)sourceValue).compareTo(((Date)targetValue));
	  }
	}
	if (c != 0) {
//	  System.out.println("NO MATCH: Source is <" + (sourceValue == null ? "null" : sourceValue.toString()) + "> Target value is <" + (targetValue == null ? "null" : targetValue.toString()) + ">.");
	  match = false;
	  break;
	}
      }
    }

    return match;
  }

  private static Vector<Vector> getDataVector(ResultSet rs, TableNode table) throws SQLException {
    Vector<Vector> values = new Vector<Vector>();

    while (rs.next()) {
      Vector row = new Vector();
      for (Iterator<TableColumn> columns = table.getColumnlist(); columns.hasNext(); ) {
	TableColumn column = columns.next();
	row.add(rs.getObject(column.getColumnName()));
      }
      values.add(row);
    }

    return values;
  }

  private static int max(int a, int b) {
    return a != b ? (a > b ? a : b) : a;
  }

  private static void buildColumnMapping(Integer[][] colMap, Iterator<TableColumn> sColList, Iterator<TableColumn> tColList) {
    int i = 0;
    int j = 0;
    for (Iterator<TableColumn> scols = sColList; scols.hasNext(); ) {
      TableColumn scol = scols.next();

      boolean match = false;
      for (Iterator<TableColumn> tcols = tColList; tcols.hasNext(); ) {
	TableColumn tcol = tcols.next();
	if (scol.getColumnName().equals(tcol.getColumnName())) {
	  match = true;
	  colMap[i][0] = new Integer(i);
	  colMap[i][1] = new Integer(j);
          j++;
	  break;
	}
      }
      if (!match) {
	colMap[i][0] = new Integer(i);
	colMap[i][1] = null;
      }
      i++;
    }
  }

  private static Vector getRowForUpdate(Vector row, Vector<Integer> pkOrdinal) {
    Vector row4update = new Vector();

    for (int i = 0; i < row.size(); i++) {
      if (pkOrdinal.contains(new Integer(i+1))) {
        continue;
      }
      else {
        row4update.add(row.elementAt(i));
      }
    }

    for (int i = 0; i < row.size(); i++) {
      if (pkOrdinal.contains(new Integer(i+1))) {
        row4update.add(row.elementAt(i));
      }
      else {
        break;
      }
    }

    return row4update;
  }
}
