package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import java.awt.Font;
import java.util.Vector;

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
public class DocumentHasLinksTab extends JTabbedPane implements ChangeListener {

  private DocumentHasLinksPane documentHasLinksPane = null;
  private TableModuleModel tableModel = null;

  public DocumentHasLinksTab(DocumentHasLinksPane documentHasLinksPane) {

    this.documentHasLinksPane = documentHasLinksPane;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    addChangeListener(this);
    setFont(new Font("Dialog", Font.PLAIN, 12));
    addTab("Active Links", null, documentHasLinksPane, "Active Links");
  }

  public void refresh(Vector<Vector> result) {
    Vector columnNames = new Vector();
    columnNames.addElement("ModuleDescription");
    columnNames.addElement("RefKey");
    columnNames.addElement("ModuleName");
    Vector v = new Vector();
    if (result.elementAt(0).size() > 1) {
      for (int i = 0; i < result.elementAt(0).size(); i++)
	v.add(result.elementAt(0).elementAt(i));
    }
    else {
      v.add(result.elementAt(0).elementAt(0));
    }
    if (result.elementAt(1).size() > 1) {
      for (int i = 0; i < result.elementAt(1).size(); i++)
	v.add(result.elementAt(1).elementAt(i));
    }
    else {
      v.add(result.elementAt(1).elementAt(0));
    }
    tableModel = documentHasLinksPane.getTableModel();
    tableModel.setDataVector(v, columnNames);
  }


  public void refreshOne(Vector v) {
     Vector columnNames = new Vector();
     columnNames.addElement("ModuleDescription");
     columnNames.addElement("RefKey");
     columnNames.addElement("ModuleName");
     tableModel = documentHasLinksPane.getTableModel();
     tableModel.setDataVector(v, columnNames);
   }


  public DocumentHasLinksPane getDocumentHasLinksPane() {
    return documentHasLinksPane;
  }

  public void stateChanged(ChangeEvent e) {


  }
}
