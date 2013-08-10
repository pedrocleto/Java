package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import pt.inescporto.template.client.rmi.MenuSingleton;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.util.Vector;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorHasLinksNode;

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
public class MonitorHasLinksPane extends JPanel {
  protected JTable monHasLinksTable = null;
  private TableModuleModel tableModel = null;


  public MonitorHasLinksPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    monHasLinksTable = new JTable(new TableModuleModel());
    monHasLinksTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
	 MonitorHasLinksNode monitorHasLinksNode = (MonitorHasLinksNode)getTableModel().getDataVector().elementAt(monHasLinksTable.getSelectedRow());
         MenuSingleton.loadForm("ENVIRONMENTAL_ASPECT", monitorHasLinksNode.getRefKey());
	}
      }
    });

    tableModel = (TableModuleModel)monHasLinksTable.getModel();
    monHasLinksTable.setTableHeader(null);
    monHasLinksTable.sizeColumnsToFit(1);
    monHasLinksTable.sizeColumnsToFit(2);
    monHasLinksTable.setFont(new Font("Dialog", Font.PLAIN, 10));
    monHasLinksTable.setRowHeight(25);
    monHasLinksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    monHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    monHasLinksTable.setShowGrid(false);
    monHasLinksTable.setShowHorizontalLines(false);
    monHasLinksTable.setShowVerticalLines(false);
    monHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    monHasLinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(monHasLinksTable);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return monHasLinksTable;
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
     MonitorHasLinksNode rowVector = (MonitorHasLinksNode)dataVector.elementAt(row);
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
