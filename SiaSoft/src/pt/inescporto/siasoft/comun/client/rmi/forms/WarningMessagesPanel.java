package pt.inescporto.siasoft.comun.client.rmi.forms;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import pt.inescporto.siasoft.comun.ejb.dao.WarningMessageDao;
import java.awt.event.MouseEvent;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.comun.ejb.session.WarningMessage;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.table.TmplCheckBoxEditor;
import pt.inescporto.template.client.design.table.TmplCheckBoxRender;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.template.dao.*;
import javax.ejb.*;
import java.rmi.*;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.elements.TplString;

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
public class WarningMessagesPanel extends JPanel {
  protected JTable jtWarningMsg = null;
  private TableWarningMessagesModel tableModel = null;
  public boolean hasData = true;

  public WarningMessagesPanel() {
    super(new BorderLayout());
    try {
      initializeUI();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void initializeUI() throws Exception {
    setLayout(new BorderLayout());

    tableModel = new TableWarningMessagesModel();
    WarningMessage wm = (WarningMessage)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.comun.ejb.session.WarningMessage");

    Vector data = null;
    try {
      data = new Vector(wm.findByUser(MenuSingleton.getName()));
    }
    catch (RowNotFoundException ex) {
      hasData = false;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }
    tableModel.setDataVector(data);

    jtWarningMsg = new JTable(tableModel);

    jtWarningMsg.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
          String moduleID = ((WarningMessageDao)tableModel.getDataVector().elementAt(jtWarningMsg.getSelectedRow())).moduleID.getValue();
	  Object[] keys = (Object[])((WarningMessageDao)tableModel.getDataVector().elementAt(jtWarningMsg.getSelectedRow())).keyRef.getValue();
	  if (moduleID.equals(WarningMessageDao.regModuleRevogated)) {
	    MenuSingleton.loadForm("REGULATORY", ((TplString)keys[0]).getValue());
	  }
	  if (moduleID.equals(WarningMessageDao.docModuleHistory)) {
	    MenuSingleton.loadForm("DOCUMENT", ((TplString)keys[0]).getValue());
	  }
	}
      }
    });
    jtWarningMsg.getColumnModel().getColumn(0).setHeaderValue("Mensagem");
    jtWarningMsg.getColumnModel().getColumn(1).setHeaderValue("Data");
    jtWarningMsg.getColumnModel().getColumn(2).setHeaderValue("V");
    jtWarningMsg.getColumnModel().getColumn(1).setMaxWidth(70);
    jtWarningMsg.getColumnModel().getColumn(1).setMinWidth(70);
    jtWarningMsg.getColumnModel().getColumn(2).setMaxWidth(20);
    jtWarningMsg.getColumnModel().getColumn(2).setMinWidth(20);
    TmplCheckBoxEditor accept = new TmplCheckBoxEditor();
    accept.addActionListener(tableModel);
    jtWarningMsg.getColumnModel().getColumn(1).setCellRenderer(new TmplDateRenderer());
    jtWarningMsg.getColumnModel().getColumn(2).setCellEditor(accept);
    jtWarningMsg.getColumnModel().getColumn(2).setCellRenderer(new TmplCheckBoxRender());
    jtWarningMsg.setFont(new Font("Dialog", Font.PLAIN, 10));
    jtWarningMsg.setShowGrid(false);
    jtWarningMsg.setShowHorizontalLines(false);
    jtWarningMsg.setShowVerticalLines(false);
    jtWarningMsg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(jtWarningMsg);
    add(jsp, BorderLayout.CENTER);
  }

  public JTable getActHasLinksTable() {
    return jtWarningMsg;
  }

  public TableWarningMessagesModel getTableModel() {
    return tableModel;
  }

  class TableWarningMessagesModel extends AbstractTableModel implements ActionListener {
    protected Vector dataVector = new Vector();
    protected Vector columnNames = new Vector();
    protected WarningMsgParser wmParser = new WarningMsgParser();

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      if (columnIndex == 2) {
	if (dataVector != null && dataVector.size() > 0) {
	  WarningMessageDao rowVector = (WarningMessageDao)dataVector.elementAt(rowIndex);
	  rowVector.accepted.setValueAsObject(aValue);
//	  System.out.println("Missing save to DB!!!");
	}
      }
    }

    public Object getValueAt(int row, int column) {
      if (dataVector != null && dataVector.size() > 0) {
	WarningMessageDao rowVector = (WarningMessageDao)dataVector.elementAt(row);
	switch (column) {
          case 0:
            return wmParser.getWMsg(rowVector.message.getValue(), rowVector.moduleID.getValue(), rowVector.keyRef);
          case 1:
            return rowVector.dateRef.getValue();
          case 2:
	    return rowVector.accepted.getValue();
	}
      }
      return null;
    }

    public int getColumnCount() {
      return 3;
    }

    public boolean isCellEditable(int row, int column) {
      return column == 2;
    }

    public int getRowCount() {
      return dataVector == null ? 0 : dataVector.size();
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

    public void actionPerformed(ActionEvent e) {
      System.out.println("Value changed no row <" + jtWarningMsg.getEditingRow() + "> to <" + ((TmplCheckBoxEditor)e.getSource()).isSelected() + ">");
    }
  }
}
