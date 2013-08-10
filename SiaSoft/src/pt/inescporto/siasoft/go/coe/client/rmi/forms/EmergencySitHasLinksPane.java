package pt.inescporto.siasoft.go.coe.client.rmi.forms;

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
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksNode;

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
public class EmergencySitHasLinksPane extends JPanel {
  protected JTable emergSitHasLinksTable = null;
  private TableModuleModel tableModel = null;


  public EmergencySitHasLinksPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    emergSitHasLinksTable = new JTable(new TableModuleModel());
    emergSitHasLinksTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
         EmergencySituationHasLinksNode emergSitHasLinksNode = (EmergencySituationHasLinksNode)getTableModel().getDataVector().elementAt(emergSitHasLinksTable.getSelectedRow());
         MenuSingleton.loadForm("ENVIRONMENTAL_ASPECT", emergSitHasLinksNode.getRefKey());
        }
      }
    });

    tableModel = (TableModuleModel)emergSitHasLinksTable.getModel();
    emergSitHasLinksTable.setTableHeader(null);
    emergSitHasLinksTable.sizeColumnsToFit(1);
    emergSitHasLinksTable.sizeColumnsToFit(2);
    emergSitHasLinksTable.setFont(new Font("Dialog", Font.PLAIN, 10));
    emergSitHasLinksTable.setRowHeight(25);
    emergSitHasLinksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    emergSitHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    emergSitHasLinksTable.setShowGrid(false);
    emergSitHasLinksTable.setShowHorizontalLines(false);
    emergSitHasLinksTable.setShowVerticalLines(false);
    emergSitHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    emergSitHasLinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(emergSitHasLinksTable);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return emergSitHasLinksTable;
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
     EmergencySituationHasLinksNode rowVector = (EmergencySituationHasLinksNode)dataVector.elementAt(row);
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
