package pt.inescporto.template.client.design;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.elements.TplObject;
import java.lang.reflect.Field;
import javax.swing.table.TableColumnModel;
import java.util.Collection;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JOptionPane;
import pt.inescporto.template.client.design.table.FW_TableColumn;
import javax.swing.JFrame;
import pt.inescporto.template.client.design.table.TmplTableColumnModel;
import javax.swing.ListSelectionModel;

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
public class TmplDlgLookup2 extends JDialog {
  private DatasourceFilter datasource = null;
  private JTable table = null;
  private Object basicRecord = null;
  private String[] headers = null;
  private TableColumnModel cm = new TmplTableColumnModel() {
    public void addColumn(TableColumn tc) {
      tc.setMinWidth(150);
      super.addColumn(tc);
    }
  };
  private TmplDefaultTableModel2 tm = null;

  // GUI
  private JButton jbtnOk = new JButton();
  private JButton jbtnCancel = new JButton();

  public TmplDlgLookup2(JFrame parent, String title, String[] headers, DatasourceFilter datasource) throws IllegalArgumentException {
    super(parent, title, true);

    if (datasource == null)
      throw new IllegalArgumentException("Datasource can't be null!");
    this.datasource = datasource;
    this.headers = headers;

    tm = new TmplDefaultTableModel2();
    try {
      basicRecord = datasource.getBasicRecord();
      createColumns();
      populate();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    // create table
    table = new JTable();

    // adjust table
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setFont(new Font("Dialog", Font.PLAIN, 10));
    table.getTableHeader().setFont(new Font("Dialog", Font.PLAIN, 10));
    table.setAutoCreateColumnsFromModel(false);
    table.setColumnModel(cm);
    table.setModel(tm);
    table.setAutoResizeMode(tm.getColumnCount() < 3 ? JTable.AUTO_RESIZE_LAST_COLUMN : JTable.AUTO_RESIZE_OFF);
    table.createDefaultColumnsFromModel();

    try {
      jbInit();
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
    }

    // adjust dialog to center of screen
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - 450) / 2;
    int y = (screen.height - 500) / 2;
    setLocation(x, y);
    setMinimumSize(new Dimension(450, 500));
    setPreferredSize(new Dimension(450, 500));

    //hook actions of Ok and Cancel buttons
    jbtnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okActionPerformed(e);
      }
    });
    jbtnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelActionPerformed(e);
      }
    });

    // get mouse clicks in table
    table.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
          okActionPerformed(new ActionEvent(this, 0, "DOUBLE"));
        }
      }

      public void mouseEntered(MouseEvent me) {}

      public void mouseExited(MouseEvent me) {}

      public void mousePressed(MouseEvent me) {}

      public void mouseReleased(MouseEvent me) {}
    });

    pack();
    setVisible(true);
  }

  // initialize GUI
  private void jbInit() throws Exception {
    JScrollPane jsp = new JScrollPane();
    jsp.getViewport().add(table);
    add(jsp, BorderLayout.CENTER);

    jbtnOk.setText(TmplResourceSingleton.getString("button.ok"));
    jbtnCancel.setText(TmplResourceSingleton.getString("button.cancel"));

    JPanel jpButtons = new JPanel();
    jpButtons.add(jbtnOk);
    jpButtons.add(jbtnCancel);

    add(jpButtons, BorderLayout.SOUTH);
  }

  protected void createColumns() {
    Field[] fld;
    int i;

    fld = basicRecord.getClass().getDeclaredFields();

    // process any field
    for (i = 0; i < fld.length; i++) {
      try {
        Object test = fld[i].get(basicRecord);
        // process template objects
        if (test instanceof pt.inescporto.template.elements.TplObject) {
          TplObject myObject = null;
          myObject = (TplObject)test;
          FW_TableColumn tc = new FW_TableColumn(myObject.getIndex(), myObject.getField());
          tc.setHeaderValue(myObject.getIndex() < headers.length ? headers[myObject.getIndex()] : myObject.getField());
          tm.addColumn(tc);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void populate() {
    try {
      Collection all = datasource.getData();
      tm.setDataVector(new Vector(all));
    }
    catch (DataSourceException ex) {
      tm.setDataVector(new Vector());
      ex.printStackTrace();
    }
  }

  protected void okActionPerformed(ActionEvent e) {
    if (table.getSelectedRow() == -1)
      showErrorMessage(TmplResourceSingleton.getString("error.msg.notSelected"));
    else {
      Object value = tm.dataVector.elementAt(table.getSelectedRow());
      try {
	datasource.setSelection(value);
      }
      catch (DataSourceException ex) {
        /**@todo SOME ONE CAN DELETE THIS RECORD - delete from table and show a warning*/
        ex.printStackTrace();
      }
      dispose();
    }
  }

  protected void cancelActionPerformed(ActionEvent e) {
    this.dispose();
  }

  void showErrorMessage(String msg) {
    JOptionPane.showMessageDialog(getContentPane(), msg,
                                  TmplResourceSingleton.getString("error.dialog.header"),
                                  JOptionPane.ERROR_MESSAGE);
  }
}
