package pt.inescporto.siasoft.asq.web.forms;

import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHasLinksNode;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.asq.ejb.session.RegulatoryHasLinks;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.rmi.FW_JPanelBasic;

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
public class RegulatoryLinkPane extends FW_JPanelBasic implements DataSourceListener {
  private DataSource datasource = null;
  private FW_ComponentListener fwCListener = null;
  private RegulatoryHasLinks regulatoryHasLinks = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");

  JPanel jpnlContent = new JPanel();

  TmplJTextField jtfldRegulatoryDescription = new TmplJTextField() {
    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jtblLinks = null;

  public RegulatoryLinkPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    setAccessPermitionIdentifier("REG_ACTIVE_LINKS");
  }

  public RegulatoryLinkPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    fwCListener.addFWComponentListener(jtfldRegulatoryDescription);

    datasource.addDatasourceListener(jtfldRegulatoryDescription);
    datasource.addDatasourceListener(this);

    setAccessPermitionIdentifier("REG_ACTIVE_LINKS");
  }

  private void jbInit() throws Exception {
    setOpaque(false);
    setLayout(new BorderLayout());

    jtfldRegulatoryDescription.setField("name");

    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");

    jtblLinks = new JTable(new TableModuleModel(columnNames));
    jtblLinks.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
	  RegulatoryHasLinksNode regHasLinkNode = (RegulatoryHasLinksNode)((TableModuleModel)jtblLinks.getModel()).getDataVector().elementAt(jtblLinks.getSelectedRow());

	  if (regHasLinkNode.getModuleName().equals("AA")) {
	    MenuSingleton.loadForm("ENVIRONMENTAL_ASPECT", regHasLinkNode.getRefKey());
	  }
	  if (regHasLinkNode.getModuleName().equals("ACTIVITY")) {
	    MenuSingleton.loadForm("ACTIVITY", regHasLinkNode.getRefKey());
	  }
	}
      }
    });

    jtblLinks.setTableHeader(null);
    jtblLinks.sizeColumnsToFit(1);
    jtblLinks.sizeColumnsToFit(2);
    jtblLinks.setFont(new Font("Dialog", Font.PLAIN, 10));
    jtblLinks.setRowHeight(25);
    jtblLinks.setCursor(new Cursor(Cursor.HAND_CURSOR));
    jtblLinks.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jtblLinks.setShowGrid(false);
    jtblLinks.setShowHorizontalLines(false);
    jtblLinks.setShowVerticalLines(false);
    jtblLinks.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    jtblLinks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(jtblLinks);

    add(jtfldRegulatoryDescription, BorderLayout.NORTH);
    add(jsp, BorderLayout.CENTER);
  }

  /*****************************************************************************
   *           Implementation of Datasource Listener Interface
   *****************************************************************************/
  public void tmplInitialize(TemplateEvent e) {
    try {
      regulatoryHasLinks = (RegulatoryHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.RegulatoryHasLinks");
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
  }

  public void tmplRefresh(TemplateEvent e) {
    // get current record
    RegulatoryDao regDao = null;
    try {
      regDao = (RegulatoryDao)datasource.getCurrentRecord();
    }
    catch (DataSourceException ex1) {
      ex1.printStackTrace();
    }

    // get list of links
    Vector<RegulatoryHasLinksNode> list = null;
    try {
      list = regulatoryHasLinks.getLinks(regDao.regId.getValue(), MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise());
    }
    catch (UserException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }

    // populate table
    TableModuleModel tm = (TableModuleModel)jtblLinks.getModel();
    tm.setDataVector(list);
  }

  public void tmplSave(TemplateEvent e) {
  }

  public void tmplLink(TemplateEvent e) {
  }
}

class TableModuleModel extends AbstractTableModel {
  protected Vector<RegulatoryHasLinksNode> dataVector = new Vector();
  protected Vector columnNames = new Vector();

  public TableModuleModel(Vector columnNames) {
    this.columnNames = columnNames;

    fireTableStructureChanged();
  }

  public Object getValueAt(int row, int column) {
    if (dataVector != null && dataVector.size() > 0) {
      RegulatoryHasLinksNode rowVector = dataVector.elementAt(row);
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
