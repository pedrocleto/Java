package pt.inescporto.siasoft.tools;

import java.util.Hashtable;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Arrays;

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
public class DatabaseTableTree {
  private Hashtable<String, TableNode> tableTree = null;

  public DatabaseTableTree(Hashtable<String, TableNode> tableTree) {
    this.tableTree = tableTree;
  }

  public DefaultMutableTreeNode getTree() {
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();

    Object tables[] = tableTree.keySet().toArray();

    Arrays.sort(tables);

    //    for (Enumeration<String> e = tableTree.keys() ; e.hasMoreElements();) {
    for (int i = 0; i < tables.length; i++) {
      String tableName = (String)tables[i];
      TableNode table = tableTree.get(tableName);
      DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table);

      // read Columns
      DefaultMutableTreeNode columnNode = new DefaultMutableTreeNode("Columns");
      for (Iterator<TableColumn> tableColumnsList = table.getColumnlist(); tableColumnsList.hasNext();) {
        TableColumn column = tableColumnsList.next();
        DefaultMutableTreeNode col = new DefaultMutableTreeNode(column);
        columnNode.add(col);
      }
      tableNode.add(columnNode);

      // read primary key
      DefaultMutableTreeNode pkNode = new DefaultMutableTreeNode("Primary Key");
      if (table.getPrimaryKey() != null) {
	TableIndex primaryKey = table.getPrimaryKey();
	DefaultMutableTreeNode pk = new DefaultMutableTreeNode(primaryKey);

	for (Iterator<IndexField> indexFieldList = primaryKey.getIndexFieldList(); indexFieldList.hasNext(); ) {
	  IndexField field = indexFieldList.next();
	  DefaultMutableTreeNode indexField = new DefaultMutableTreeNode(field);
	  pk.add(indexField);
	}

	pkNode.add(pk);
      }
      tableNode.add(pkNode);

      // read Indexes
      DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode("Indexes");
      for (Iterator<TableIndex> tableIndexesList = table.getIndexlist(); tableIndexesList.hasNext();) {
        TableIndex index = tableIndexesList.next();
        DefaultMutableTreeNode ind = new DefaultMutableTreeNode(index);

        for (Iterator<IndexField> indexFieldList = index.getIndexFieldList(); indexFieldList.hasNext();) {
          IndexField field = indexFieldList.next();
          DefaultMutableTreeNode indexField = new DefaultMutableTreeNode(field);
          ind.add(indexField);
        }

        indexNode.add(ind);
      }
      tableNode.add(indexNode);

      // read Primary Key

      // read Imported Keys
      DefaultMutableTreeNode importedKeysNode = new DefaultMutableTreeNode("Imported Keys");
      for (Iterator<TableConstraint> tableImportedKeysList = table.getImportedConstraintlist(); tableImportedKeysList.hasNext();) {
        TableConstraint constraint = tableImportedKeysList.next();
        DefaultMutableTreeNode cons = new DefaultMutableTreeNode(constraint);

        for (Iterator<KeyReference> keyRefList = constraint.getKeyReferencesList(); keyRefList.hasNext();) {
          KeyReference field = keyRefList.next();
          DefaultMutableTreeNode keyRef = new DefaultMutableTreeNode(field);
          cons.add(keyRef);
        }

        importedKeysNode.add(cons);
      }
      tableNode.add(importedKeysNode);

      // read Exported Keys
      DefaultMutableTreeNode exportedKeysNode = new DefaultMutableTreeNode("Exported Keys");
      for (Iterator<TableConstraint> tableExportedKeysList = table.getExportedConstraintlist(); tableExportedKeysList.hasNext();) {
        TableConstraint constraint = tableExportedKeysList.next();
        DefaultMutableTreeNode cons = new DefaultMutableTreeNode(constraint);

        for (Iterator<KeyReference> keyRefList = constraint.getKeyReferencesList(); keyRefList.hasNext();) {
          KeyReference field = keyRefList.next();
          DefaultMutableTreeNode keyRef = new DefaultMutableTreeNode(field);
          cons.add(keyRef);
        }

        exportedKeysNode.add(cons);
      }
      tableNode.add(exportedKeysNode);

      // add table to tree
      rootNode.add(tableNode);
    }

    return rootNode;
  }
}
