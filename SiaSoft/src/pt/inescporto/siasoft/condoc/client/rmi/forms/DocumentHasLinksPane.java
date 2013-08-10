package pt.inescporto.siasoft.condoc.client.rmi.forms;

import pt.inescporto.template.client.rmi.MenuSingleton;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import javax.swing.table.AbstractTableModel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.util.Vector;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHasLinksNode;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class DocumentHasLinksPane extends JPanel {
  protected JTable docHasLinksTable = null;
  private TableModuleModel tableModel = null;


  public DocumentHasLinksPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    docHasLinksTable = new JTable(new TableModuleModel());
    docHasLinksTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
	  DocumentHasLinksNode decHasLinksNode = (DocumentHasLinksNode)getTableModel().getDataVector().elementAt(docHasLinksTable.getSelectedRow());

	  if (decHasLinksNode.getModuleName().equals("CoEmerg")) {
	    MenuSingleton.loadForm("EMERGENCY", decHasLinksNode.getRefKey());
	  }
	  if (decHasLinksNode.getModuleName().equals("Auditor")) {
	    MenuSingleton.loadForm("AUDIT_M", decHasLinksNode.getRefKey());
	  }
	}
      }
    });

    tableModel = (TableModuleModel)docHasLinksTable.getModel();
    docHasLinksTable.setTableHeader(null);
    docHasLinksTable.sizeColumnsToFit(1);
    docHasLinksTable.sizeColumnsToFit(2);
    docHasLinksTable.setFont(new Font("Dialog", Font.PLAIN, 10));
    docHasLinksTable.setRowHeight(25);
    docHasLinksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    docHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    docHasLinksTable.setShowGrid(false);
    docHasLinksTable.setShowHorizontalLines(false);
    docHasLinksTable.setShowVerticalLines(false);
    docHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    docHasLinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(docHasLinksTable);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return docHasLinksTable;
  }

  public TableModuleModel getTableModel() {
    return tableModel;
  }
}

class TableModuleModel extends AbstractTableModel {
  protected Vector dataVector = new Vector();
  protected Vector columnNames = new Vector();

  public Object getValueAt(int row, int column) {
    if (dataVector != null && dataVector.size() > 0) {
     DocumentHasLinksNode rowVector = (DocumentHasLinksNode)dataVector.elementAt(row);
      switch (column) {
      case 0:
          return rowVector.getModuleDescription();
       case 1:
          return rowVector.getRefKey();
       case 2:
          return rowVector.getModuleName();
      }
    }
    return null;
  }

  public int getColumnCount() {
    return 3;
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public int getRowCount() {
    return dataVector.size();
  }

  public void setDataVector(Vector dataVector) {
    this.dataVector = dataVector;
    fireTableDataChanged();
  }

  public void setDataVector(Vector dataVector, Vector columnNames) {
    this.dataVector = dataVector;
    this.columnNames = columnNames;

    fireTableDataChanged();
  }

  public Vector getDataVector() {
    return dataVector;
  }
}
