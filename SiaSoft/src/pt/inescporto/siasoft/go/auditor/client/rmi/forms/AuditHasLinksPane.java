package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

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
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditHasLinksNode;

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
public class AuditHasLinksPane extends JPanel {
  protected JTable auditPlanHasLinksTable = null;
  private TableModuleModel tableModel = null;


  public AuditHasLinksPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    auditPlanHasLinksTable = new JTable(new TableModuleModel());
    /*auditPlanHasLinksTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
         AuditHasLinksNode emergSitHasLinksNode = (AuditHasLinksNode)getTableModel().getDataVector().elementAt(auditPlanHasLinksTable.getSelectedRow());
         MenuSingleton.loadForm("ENVIRONMENTAL_ASPECT", emergSitHasLinksNode.getRefKey());
        }
      }
    });*/

    tableModel = (TableModuleModel)auditPlanHasLinksTable.getModel();
    auditPlanHasLinksTable.setTableHeader(null);
    auditPlanHasLinksTable.sizeColumnsToFit(1);
    auditPlanHasLinksTable.sizeColumnsToFit(2);
    auditPlanHasLinksTable.setFont(new Font("Dialog", Font.PLAIN, 10));
    auditPlanHasLinksTable.setRowHeight(25);
    auditPlanHasLinksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    auditPlanHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    auditPlanHasLinksTable.setShowGrid(false);
    auditPlanHasLinksTable.setShowHorizontalLines(false);
    auditPlanHasLinksTable.setShowVerticalLines(false);
    auditPlanHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    auditPlanHasLinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(auditPlanHasLinksTable);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return auditPlanHasLinksTable;
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
    AuditHasLinksNode rowVector = (AuditHasLinksNode)dataVector.elementAt(row);
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
