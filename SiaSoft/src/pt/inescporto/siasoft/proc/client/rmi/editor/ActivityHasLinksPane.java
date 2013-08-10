package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import pt.inescporto.siasoft.proc.ejb.dao.ActHasLinksNode;
import pt.inescporto.template.client.rmi.MenuSingleton;

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
public class ActivityHasLinksPane extends JPanel {
  protected JTable actHasLinksTable = null;
  private TableModuleModel tableModel = null;
  private ProcessEditor pEditor = null;
  public ActivityHasLinksPane(ProcessEditor pEditor) {
    this.pEditor = pEditor;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    actHasLinksTable = new JTable(new TableModuleModel());

    actHasLinksTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
	  if (pEditor.getModuleID().equals("MASQ")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("REGULATORY", actHasLinksNode.getRefKey());
	  }
	  if (pEditor.getModuleID().equals("MDoc")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("DOCUMENT", actHasLinksNode.getRefKey());
	  }

	  if (pEditor.getModuleID().equals("MForm")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("EMERGENCY", actHasLinksNode.getRefKey());
	  }

	  if (pEditor.getModuleID().equals("MCom")) {
	  }
	  if (pEditor.getModuleSelected().equals("smAuditor")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("AUDIT_PLAN", actHasLinksNode.getRefKey());
	  }

	  if (pEditor.getModuleSelected().equals("smAA")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("ENVIRONMENTAL_ASPECT", actHasLinksNode.getRefKey());
	  }

	  if (pEditor.getModuleSelected().equals("smMonitor")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("MONITOR_PLAN", actHasLinksNode.getRefKey());
	  }
	  if (pEditor.getModuleSelected().equals("smFun")) {

	  }
	  if (pEditor.getModuleSelected().equals("smCO_EMERG")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("EMERGENCY", actHasLinksNode.getRefKey());
	  }

	  if (pEditor.getModuleSelected().equals("smPGA")) {
	    ActHasLinksNode actHasLinksNode = (ActHasLinksNode)getTableModel().getDataVector().elementAt(actHasLinksTable.getSelectedRow());
	    MenuSingleton.loadForm("GOALS", actHasLinksNode.getRefKey());
	  }
        }
      }
    });
    tableModel = (TableModuleModel)actHasLinksTable.getModel();
    actHasLinksTable.setTableHeader(null);
    actHasLinksTable.sizeColumnsToFit(1);
    actHasLinksTable.sizeColumnsToFit(2);
    actHasLinksTable.setFont(new Font("Dialog", Font.PLAIN, 10));
    actHasLinksTable.setRowHeight(30);
    actHasLinksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
    actHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    actHasLinksTable.setShowGrid(false);
    actHasLinksTable.setShowHorizontalLines(false);
    actHasLinksTable.setShowVerticalLines(false);
    actHasLinksTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    actHasLinksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(actHasLinksTable);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return actHasLinksTable;
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
      ActHasLinksNode rowVector = (ActHasLinksNode)dataVector.elementAt(row);
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
