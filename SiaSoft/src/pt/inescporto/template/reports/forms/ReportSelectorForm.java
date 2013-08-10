package pt.inescporto.template.reports.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRDataSource;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.Map;
import java.awt.Window;
import java.awt.event.MouseEvent;
import pt.inescporto.template.reports.jasper.TmplJasperReportsViewer;
import java.util.Vector;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.JOptionPane;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ReportSelectorForm extends JFrame {
//  ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.common.language.textresources");

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton btCancel = new JButton();
  JButton btOK = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable tblReports = new JTable();
  ReportTableModel model;

  Collection col = new Vector();

//  TreeTableHeader[] headers = { new TreeTableHeader(res.getString("label.reportName"),"name",String.class)};

  Map parameters;
  Collection data;
  JRDataSource datasource;

  public ReportSelectorForm(Collection data, Collection col, Map parameters) {
    super();
    try {
      this.col = col;
      this.parameters = parameters;
      this.data = data;

      refreshTable();
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ReportSelectorForm(Window owner, Collection data, Collection col, Map parameters) {
//    super(null);

    try {
      this.col = col;
      this.parameters = parameters;
      this.data = data;
      refreshTable();
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ReportSelectorForm(Collection data, Collection col, Map parameters, JRDataSource datasource) {
    super();
    try {
      this.col = col;
      this.parameters = parameters;
      this.data = data;
      this.datasource = datasource;

      refreshTable();
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ReportSelectorForm(JFrame owner, Collection data, Collection col, Map parameters, JRDataSource datasource) {
//    super(owner);
    try {
      this.col = col;
      this.parameters = parameters;
      this.data = data;
      this.datasource = datasource;
      refreshTable();
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void refreshTable() {
    if ( model == null ) {
      Vector columnNames = new Vector();
      columnNames.add(new String("Name"));
      model = new ReportTableModel();
      model.setDataVector(new Vector(col), columnNames);
//      model = new BasicDataModel(col,new ArrayList(Arrays.asList(this.headers)));
      tblReports.setModel(model);
      tblReports.createDefaultColumnsFromModel();
    } else {
      this.model.fireTableDataChanged();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    btCancel.addActionListener(new ReportSelectorForm_btCancel_actionAdapter(this));
    btOK.addActionListener(new ReportSelectorForm_btOK_actionAdapter(this));
    this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    btCancel.setText("Cancelar");
    jPanel2.setLayout(flowLayout1);
    jPanel1.setLayout(borderLayout2);
    this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
    btOK.setText("OK");
    jPanel2.add(btOK);
    jPanel2.add(btCancel);
    jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(tblReports);
    tblReports.setRowHeight(16);
    tblReports.addMouseListener(new ReportSelectorForm_tbl_mouseAdapter(this));
    this.setTitle("Seleccionar Relatório");
  }

  public void btCancel_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void tbl_mouseClicked(MouseEvent e) {
    if( e.getClickCount() == 2 ) {
      open_report();
    }
  }

  public void selectFirstReport() {
    tblReports.getSelectionModel().setSelectionInterval(0,0);
  }

  public void open_report() {
    try {
      int sel = tblReports.getSelectedRow();
      if (sel != -1) {
        ReportDataDao rData = (ReportDataDao) model.getDataVector().get(sel);
        java.net.URL url = Class.forName(rData.anchor.getValue()).getResource(rData.file.getValue());
        TmplJasperReportsViewer tjViewer;

        HashMap reportParms = new HashMap(parameters);
        reportParms.putAll(rData.parameters);

        if ( datasource == null )
          tjViewer = new TmplJasperReportsViewer(data, url.openStream(), reportParms, "Relatório");
        else
          tjViewer = new TmplJasperReportsViewer(data, url.openStream(), reportParms, "Relatório", datasource);
        tjViewer.viewReport();
        tjViewer.setVisible(true);
      } else {
        JOptionPane.showMessageDialog(this, "Deve seleccionar um relatório", "Aviso", JOptionPane.INFORMATION_MESSAGE);
      }
    } catch (Exception ex) {
/*      MessageDialog mdialog = new MessageDialog("message.erroropeningReport",ex);
      mdialog.showOKDialog(this);*/
      ex.printStackTrace();
    }
  }

  public void btOK_actionPerformed(ActionEvent e) {
    open_report();
  }

}

class ReportSelectorForm_btOK_actionAdapter implements ActionListener {
  private ReportSelectorForm adaptee;
  ReportSelectorForm_btOK_actionAdapter(ReportSelectorForm adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btOK_actionPerformed(e);
  }
}

class ReportSelectorForm_btCancel_actionAdapter implements ActionListener {
  private ReportSelectorForm adaptee;
  ReportSelectorForm_btCancel_actionAdapter(ReportSelectorForm adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btCancel_actionPerformed(e);
  }
}

class ReportSelectorForm_tbl_mouseAdapter extends java.awt.event.MouseAdapter {
  ReportSelectorForm adaptee;

  ReportSelectorForm_tbl_mouseAdapter(ReportSelectorForm adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.tbl_mouseClicked(e);
  }
}

class ReportTableModel extends AbstractTableModel {
  protected Vector<ReportDataDao> dataVector = new Vector();
  protected Vector columnNames = new Vector();

  public Object getValueAt(int row, int column) {
    if (dataVector != null && dataVector.size() > 0) {
      ReportDataDao rowVector = dataVector.elementAt(row);
      switch (column) {
        case 0:
          return rowVector.name.getValue();
      }
    }
    return null;
  }

  public int getColumnCount() {
    return 1;
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

  public void setDataVector(Vector<ReportDataDao> dataVector, Vector columnNames) {
    this.dataVector = dataVector;
    this.columnNames = columnNames;

    fireTableDataChanged();
  }

  public Vector getDataVector() {
    return dataVector;
  }
}
