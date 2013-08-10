package pt.inescporto.siasoft.go.aa.client.rmi.forms;

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
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksNode;

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
public class EAHasLinksPane extends JPanel {
  protected JTable eaHasLinksTable = null;
  private TableModuleModel tableModel = null;

  public EAHasLinksPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    eaHasLinksTable = new JTable(new TableModuleModel());
    eaHasLinksTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
	  EAHasLinksNode decHasLinksNode = (EAHasLinksNode)getTableModel().getDataVector().elementAt(eaHasLinksTable.getSelectedRow());
	  if (decHasLinksNode.getModuleName().equals("PGA")) {
	    MenuSingleton.loadForm("EMERGENCY", decHasLinksNode.getRefKey());
	  }
          if (decHasLinksNode.getModuleName().equals("Monitor")) {
            MenuSingleton.loadForm("MONITOR_PLAN", decHasLinksNode.getRefKey());
          }
          if (decHasLinksNode.getModuleName().equals("PGA")) {
            MenuSingleton.loadForm("GOALS", decHasLinksNode.getRefKey());
          }
	}
      }
    });
    tableModel = (TableModuleModel)eaHasLinksTable.getModel();
    eaHasLinksTable.setTableHeader(null);
    eaHasLinksTable.sizeColumnsToFit(1);
    eaHasLinksTable.sizeColumnsToFit(2);
    eaHasLinksTable.setFont(new Font("Dialog", Font.PLAIN, 10));
    eaHasLinksTable.setRowHeight(25);
    eaHasLinksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    eaHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    eaHasLinksTable.setShowGrid(false);
    eaHasLinksTable.setShowHorizontalLines(false);
    eaHasLinksTable.setShowVerticalLines(false);
    eaHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    eaHasLinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(eaHasLinksTable);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return eaHasLinksTable;
  }

  public TableModuleModel getTableModel() {
    return tableModel;
  }
}

class TableModuleModel extends AbstractTableModel {
  protected Vector<EAHasLinksNode> dataVector = new Vector();
  protected Vector columnNames = new Vector();

  public Object getValueAt(int row, int column) {
    if (dataVector != null && dataVector.size() > 0) {
      EAHasLinksNode rowVector = dataVector.elementAt(row);
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

  public void setDataVector(Vector<EAHasLinksNode> dataVector, Vector columnNames) {
    this.dataVector = dataVector;
    this.columnNames = columnNames;

    fireTableDataChanged();
  }

  public Vector getDataVector() {
    return dataVector;
  }
}
