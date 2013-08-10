package pt.inescporto.siasoft.asq.client.rmi.forms;

import javax.swing.JMenuItem;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.asq.ejb.session.Regulatory;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import java.util.Vector;
import javax.swing.JInternalFrame;
import java.awt.Dimension;
import pt.inescporto.template.client.rmi.ContextPropertiesClient;

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
public class RegulatoryTreePopupMenu extends MouseAdapter  implements ActionListener {
  private JMenuItem openRegulatory = null;
  private JMenuItem openPDF = null;
  private JMenuItem printPDF = null;
  private JMenuItem editDocument = null;
  private JTree jt=null;
  ContextPropertiesClient cpc = new ContextPropertiesClient();

  private JPopupMenu menu = null;
  //private JFrame frame;


  public RegulatoryTreePopupMenu() {

    menu = new JPopupMenu();
    menu.setBorder(BorderFactory.createTitledBorder(""));
    ((TitledBorder)menu.getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    openRegulatory = new JMenuItem("Abrir Ficha");
    openRegulatory.addActionListener(this);
    openRegulatory.setActionCommand("OPEN_REG");
    menu.add(openRegulatory);

    openPDF = new JMenuItem("Abrir PDF");
    openPDF.addActionListener(this);
    openPDF.setActionCommand("OPEN_PDF");
    menu.add(openPDF);


    printPDF = new JMenuItem("Imprimir PDF");
    printPDF.addActionListener(this);
    printPDF.setActionCommand("PRINT_PDF");
    menu.add(printPDF);
    menu.addSeparator();

    editDocument = new JMenuItem("Editar Documento" );
    editDocument.addActionListener(this);
    editDocument.setActionCommand("EDIT_DOC");
    menu.add(editDocument);

  }

  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("OPEN_REG")) {
      try {
	String key = ((TreeBuilderNode)((DefaultMutableTreeNode)jt.getLastSelectedPathComponent()).getUserObject()).getNodeID();
        JInternalFrame iFrame =new JInternalFrame();
        iFrame.setPreferredSize(new Dimension(520,650));
	RegulatoryReportForm tv = new RegulatoryReportForm();
        tv.setPrimaryKey(key.toString());
        iFrame.add(tv);
        iFrame.setTitle("Ficha Completa do Diploma");
	MenuSingleton.addContainer(iFrame);
      }
      catch (java.lang.Throwable ee) {
	// TODO: Something
	ee.printStackTrace();
      }

    }

    if (e.getActionCommand().equals("OPEN_PDF")) {
      try {
        String key = ((TreeBuilderNode)((DefaultMutableTreeNode)jt.getLastSelectedPathComponent()).getUserObject()).getNodeID();
        RegulatoryDao regDao= new RegulatoryDao();
        Regulatory reg = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
        Vector binds = new Vector();
        binds.add(new TplString(key.toString()));
        reg.setLinkCondition("regID=?", binds);
        regDao = reg.getData();

        String link =regDao.docName.getValue();
	String urlBase = cpc.getBaseURL() + "/pdf";

	if (link != null) {
	  ShowPDF pdfViewer = new ShowPDF();
	  try {
	    pdfViewer.showPdf(urlBase + "/" + link.replaceAll(".pdf", "") + ".pdf");
	  }
	  catch (Exception ex) {
	    ex.printStackTrace();
	  }
	}
      }
      catch (java.lang.Throwable ee) {
	// TODO: Something
	ee.printStackTrace();
      }
    }

    if (e.getActionCommand().equals("PRINT_PDF")) {

    }
    if (e.getActionCommand().equals("EDIT_DOC")) {
      String key = ((TreeBuilderNode)((DefaultMutableTreeNode)jt.getLastSelectedPathComponent()).getUserObject()).getNodeID();
      MenuSingleton.loadForm("REGULATORY",key);
    }

  }

  public JMenuItem getOpenReg() {
    return openRegulatory;
  }

  public JMenuItem getOpenPDF() {
    return openPDF;
  }

  public JMenuItem getEditDocument() {
    return editDocument;
  }

  public JMenuItem getPrintPDF() {
    return printPDF;
  }


  public void setEnvAspItem(JMenuItem envAspItem) {
    this.openRegulatory = envAspItem;
  }

  public void setEnvAspListItem(JMenuItem envAspListItem) {
    this.openPDF = envAspListItem;
  }

  public void setActHasPartListItem(JMenuItem actHasPartListItem) {
    this.editDocument = actHasPartListItem;
  }

  public void setActHasPartItem(JMenuItem actHasPartItem) {
    this.printPDF = actHasPartItem;
  }

  public void mousePressed(java.awt.event.MouseEvent e) {

    displayMenu(e);
}

public void mouseReleased(java.awt.event.MouseEvent e) {

    displayMenu(e);
}

private void displayMenu(MouseEvent e) {
    if(e.isPopupTrigger()){
      jt = (JTree) e.getComponent();
      try{
	if (((TreeBuilderNode)((DefaultMutableTreeNode)jt.getLastSelectedPathComponent()).getUserObject()).getNodeType() == TreeBuilderNode.REGULATORY) {
	  menu.show(e.getComponent(), e.getX(), e.getY());
	}
      }
      catch(NullPointerException ex){

      }
    }
}

}
