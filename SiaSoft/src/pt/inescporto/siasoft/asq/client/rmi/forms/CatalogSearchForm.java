package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.rmi.*;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import javax.swing.JTable;
import pt.inescporto.template.client.design.TmplJButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.naming.*;
import pt.inescporto.siasoft.asq.ejb.session.CatalogSearch;
import pt.inescporto.siasoft.asq.ejb.dao.StatHits;
import java.util.Vector;
import pt.inescporto.template.dao.*;
import java.rmi.*;
import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import pt.inescporto.template.client.design.table.TmplProgressBarRender;
import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;

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
public class CatalogSearchForm extends FW_InternalFrameBasic implements ActionListener {
  private TmplJLabel jlblQueryStmt = null;
  private TmplJTextField jtfldQueryStmt = null;
  private TmplJButton jbtnQuery = null;
  private ContextPropertiesClient cpc = new ContextPropertiesClient();

  private CatalogSearch cs = null;

  JTable jtblResult = null;
  HitsTableModel hitsTM = null;

  public CatalogSearchForm() {
    super();

    try {
      initializeUI();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    setAccessPermitionIdentifier("CatalogSearch");

    try {
      cs = (CatalogSearch)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.CatalogSearch");
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }

    start();
  }

  private void initializeUI() throws Exception {
    // build UI components
    jlblQueryStmt = new TmplJLabel("Pergunta");
    jtfldQueryStmt = new TmplJTextField();
    jbtnQuery = new TmplJButton("Pesquisar");
    jbtnQuery.addActionListener(this);

    hitsTM = new HitsTableModel(new Object[] {"Documento", "Resumo", "Relevância"});
    jtblResult = new JTable(hitsTM);
    jtblResult.setShowGrid(false);
    jtblResult.setShowHorizontalLines(false);
    jtblResult.setShowVerticalLines(false);

    jtblResult.createDefaultColumnsFromModel();

    ((TableColumn)jtblResult.getColumnModel().getColumn(0)).setMaxWidth(80);
    ((TableColumn)jtblResult.getColumnModel().getColumn(0)).setMinWidth(80);
    ((TableColumn)jtblResult.getColumnModel().getColumn(2)).setCellRenderer(new TmplProgressBarRender());
    ((TableColumn)jtblResult.getColumnModel().getColumn(2)).setMaxWidth(80);
    ((TableColumn)jtblResult.getColumnModel().getColumn(2)).setMinWidth(80);
    jtblResult.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
          showPDF((String)jtblResult.getValueAt(jtblResult.getSelectedRow(), 0));
        }
      }
      public void mouseEntered(MouseEvent me) {}
      public void mouseExited(MouseEvent me) {}
      public void mousePressed(MouseEvent me) {}
      public void mouseReleased(MouseEvent me) {}
    });

    // managing layout
    JPanel jpnlContent = new JPanel(new BorderLayout());

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 5px");

    formLayout.setRowGroups(new int[][] { {2, 4}
    });
    CellConstraints cc = new CellConstraints();
    JPanel jpnlParameters = new JPanel(formLayout);

    jpnlParameters.add(jlblQueryStmt, cc.xy(2, 2));
    jpnlParameters.add(jtfldQueryStmt, cc.xy(4, 2));
    jpnlParameters.add(jbtnQuery, cc.xy(2, 4));

    jpnlContent.add(jpnlParameters, BorderLayout.NORTH);
    jpnlContent.add(new JScrollPane(jtblResult), BorderLayout.CENTER);
    add(jpnlContent);
  }

  public void actionPerformed(ActionEvent e) {
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    Vector<StatHits> result = null;
    try {
      result = cs.queryCatalog(jtfldQueryStmt.getText());
      hitsTM.setDataVector(result);
    }
    catch (UserException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  private void showPDF(String documentName) {
    getWordsToHighlight();
    String urlBase = cpc.getBaseURL();
    ShowPDF pdfViewer = new ShowPDF();
    try {
      pdfViewer.showPdf(urlBase + "/pdf/" + documentName + ".pdf" + getWordsToHighlight());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *
   * @return String #search=\u201Dword1 word2\u201D
   */
  private String getWordsToHighlight() {
/*    String os = System.getProperty("os.name");
    if (os.equalsIgnoreCase("linux")) {
      return "";
    }*/

    String query = jtfldQueryStmt.getText();

    String words[] = query.split(" ");

    String resultQuery = "#search=\"";
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      if (word.charAt(0) == '-')
        continue;
      if (word.charAt(0) == '+')
        word = word.substring(1, word.length());
      resultQuery += word + "%20";
    }

    resultQuery = resultQuery.substring(0, resultQuery.length() - 3) + "\"";

//    System.out.println("Query is <" + resultQuery + ">");
    return resultQuery;
  }
}

class HitsTableModel extends AbstractTableModel {
  protected Vector<StatHits> dataVector = new Vector<StatHits>();
  protected Vector columnNames = new Vector();

  public HitsTableModel(Object[] columnNames) {
    this.columnNames = new Vector(Arrays.asList(columnNames));
  }

  public Object getValueAt(int row, int column) {
    if (dataVector != null && dataVector.size() > 0) {
      StatHits hit = dataVector.elementAt(row);
      switch (column) {
        case 0:
          boolean isWindowsPath = hit.getPath().lastIndexOf("\\") != -1;
	  return (hit.getPath().substring(hit.getPath().lastIndexOf(isWindowsPath ? "\\" : "/") + 1, hit.getPath().length())).replaceAll(".pdf", "");
        case 1:
          return hit.getTitle();
        case 2:
          return new Float(hit.getScore() * 100.0F);
      }
    }
    return null;
  }

  public int getColumnCount() {
    return columnNames.size();
  }

  public String getColumnName(int column) {
    return (String)columnNames.elementAt(column);
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public int getRowCount() {
    return dataVector.size();
  }

  public void setDataVector(Vector<StatHits> dataVector) {
    this.dataVector = dataVector;
    fireTableDataChanged();
  }

  public void setDataVector(Vector<StatHits> dataVector, Vector columnNames) {
    this.dataVector = dataVector;
    this.columnNames = columnNames;

    fireTableDataChanged();
  }

  public Vector<StatHits> getDataVector() {
    return dataVector;
  }
}
