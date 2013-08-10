package pt.inescporto.siasoft.asq.client.rmi.forms;

import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.ejb.FinderException;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.naming.NamingException;
import javax.swing.ImageIcon;

import pt.inescporto.template.client.rmi.FW_JPanelBasic;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.TmplJDatePicker;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability;
import pt.inescporto.siasoft.asq.ejb.dao.AsqClientApplicabilityDao;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import java.awt.Container;

public class RegulatoryDefinitionPanel extends JPanel implements ActionListener{
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  private JPanel jpnlData = null;
  private DataSource datasource = null;

  private TmplJTextField jtfldRegulatoryName = null;
  private JScrollPane jspResume = null;
  private TmplJTextArea jtaResume = null;
  private TmplJLabel jlblPublishDate = null;
  private TmplJDatePicker jtfldPublishDate = null;
  private TmplJLabel jlblSource = null;
  private TmplJComboBox cmbxSource = null;
  private TmplJLabel jlblObs = null;
  private TmplJTextArea jtafldObs = null;
  private FW_JPanelBasic jPanel = null;
  private HTMLViewerButton jbtnViewHTML = null;
  public PDFViewerButton jbtnViewPDF = new PDFViewerButton();
  public TmplJButton jbtnAddPDFtoList = new TmplJButton();
  private TmplJLabel jlblApplicability = null;

  private TmplJCheckBox jchkbDirect = null;
  private TmplJCheckBox jchkbIndirect = null;
  private TmplJCheckBox jchkbInform = null;

  /**
   * This method initializes
   *
   */
  public RegulatoryDefinitionPanel() {
    initialize();
  }

  public RegulatoryDefinitionPanel(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    initialize();

    fwCListener.addFWComponentListener(jtfldRegulatoryName);
    fwCListener.addFWComponentListener(jtaResume);
    fwCListener.addFWComponentListener(jtfldPublishDate);
    fwCListener.addFWComponentListener(cmbxSource);
    fwCListener.addFWComponentListener(jtafldObs);
    fwCListener.addFWComponentListener(jPanel);

    datasource.addDatasourceListener(jtfldRegulatoryName);
    datasource.addDatasourceListener(jtaResume);
    datasource.addDatasourceListener(jtfldPublishDate);
    datasource.addDatasourceListener(cmbxSource);
    datasource.addDatasourceListener(jbtnViewHTML);
    datasource.addDatasourceListener(jbtnViewPDF);
    datasource.addDatasourceListener(new Applicability(jchkbDirect, jchkbIndirect, jchkbInform));
  }

  /**
   * This method initializes this
   *
   */
  private void initialize() {
    try {
      setLayout(new BorderLayout());
      setSize(new java.awt.Dimension(412, 229));
      setOpaque(false);

      jlblPublishDate = new TmplJLabel();
      jlblPublishDate.setText("Data");
      jlblPublishDate.setBounds(5, 120, 40, 21);

      jlblSource = new TmplJLabel();
      jlblSource.setText("Fonte");
      jlblSource.setBounds(190, 120, 40, 21);

      jlblObs = new TmplJLabel();
      jlblObs.setText("Obs");

      //jbtnAddPDFtoList = new TmplJButton();
      jbtnAddPDFtoList.setActionCommand("ADD_PDF");
      jbtnAddPDFtoList.addActionListener(this);
      jbtnAddPDFtoList.setBounds(315, 145, 40, 40);
      jbtnAddPDFtoList.setIcon(new ImageIcon(PDFViewerButton.class.getResource("bookmark.png")));

      //jbtnViewPDF = new PDFViewerButton();
      jbtnViewPDF.setBounds(365, 145, 40, 40);
      jbtnViewPDF.setField("documentName");

      jpnlData = new JPanel(null);
      jpnlData.add(getJtfldRegulatoryName());
      jpnlData.add(getJspResume());
      jpnlData.add(jlblPublishDate);
      jpnlData.add(getJtfldPublishDate());
      jpnlData.add(jlblSource);
      jpnlData.add(getCmbxSource());
      jpnlData.add(getJPanel());
      if (MenuSingleton.getEnvironmentType().equals("RMI"))
        jpnlData.add(getJbtnViewHTML());
      if (MenuSingleton.getEnvironmentType().equals("WEB"))
        jpnlData.add(jbtnAddPDFtoList);
      jpnlData.add(jbtnViewPDF);

      add(jpnlData, BorderLayout.CENTER);
    }
    catch (java.lang.Throwable e) {
      e.printStackTrace();
    }
  }

  /**
   * IMPLEMENTATION OF ActionListener INTERFACE
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("ADD_PDF")) {
      try {
	RegulatoryDao regDao = (RegulatoryDao)datasource.getCurrentRecord();
	MenuSingleton.addItemToMenu(regDao.regId.getValue(), regDao);
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * This method initializes tmplJTextField
   *
   * @return pt.inescporto.template.client.design.TmplJTextField
   */
  private TmplJTextField getJtfldRegulatoryName() {
    if (jtfldRegulatoryName == null) {
      try {
	jtfldRegulatoryName = new TmplJTextField();
	jtfldRegulatoryName.setField("name");
	jtfldRegulatoryName.setLabel("Nome");
        jtfldRegulatoryName.setBounds(5,5, 400, 21);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jtfldRegulatoryName;
  }

  /**
   * This method initializes jScrollPane
   *
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getJspResume() {
    if (jspResume == null) {
      try {
	jspResume = new JScrollPane();
	jspResume.getViewport().add(getJtaResume());
        jspResume.setBounds(5, 30, 400, 84);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jspResume;
  }

  /**
   * This method initializes tmplJTextArea
   *
   * @return pt.inescporto.template.client.design.TmplJTextArea
   */
  private TmplJTextArea getJtaResume() {
    if (jtaResume == null) {
      try {
	jtaResume = new TmplJTextArea();
	jtaResume.setField("resume");
	jtaResume.setLabel("Resumo");
        jtaResume.setLineWrap(true);
        jtaResume.setWrapStyleWord(true);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jtaResume;
  }

  /**
   * This method initializes tmplJDateField1
   *
   * @return pt.inescporto.template.client.design.TmplJDateField
   */
  private TmplJDatePicker getJtfldPublishDate() {
    if (jtfldPublishDate == null) {
      try {
	jtfldPublishDate = new TmplJDatePicker();
	jtfldPublishDate.setField("publishDate");
//	jtfldPublishDate.setLabel("Data de Publicação");
        jtfldPublishDate.setBounds(50, 120, 100, 21);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jtfldPublishDate;
  }

  /**
   * This method initializes tmplJComboBox1
   *
   * @return pt.inescporto.template.client.design.TmplJComboBox
   */
  private TmplJComboBox getCmbxSource() {
    if (cmbxSource == null) {
      try {
	cmbxSource = new TmplJComboBox();
	cmbxSource.setField("fk_sourceId");
	cmbxSource.setUrl("pt.inescporto.siasoft.asq.ejb.session.Source");
	cmbxSource.setShowSave(new Integer[] {new Integer(1), new Integer(0)});
	cmbxSource.setLabel("Fonte de Publicação");
        cmbxSource.setBounds(240, 120, 165, 21);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return cmbxSource;
  }

  /**
   * This method initializes jPanel
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJPanel() {
    if (jPanel == null) {
      try {
	jlblApplicability = new TmplJLabel();
	jlblApplicability.setText("Aplicabilidade");
	jPanel = new FW_JPanelBasic("APLICABILITY_PANEL");
	jPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
	jPanel.add(getJchkbDirect());
	jPanel.add(getJchkbIndirect());
	jPanel.add(getJchkbInformative());
        jPanel.setBounds(5, 145, 300, 40);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jPanel;
  }

  /**
   * This method initializes tmplJButton1
   *
   * @return pt.inescporto.template.client.design.TmplJButton
   */
  private HTMLViewerButton getJbtnViewHTML() {
    if (jbtnViewHTML == null) {
      try {
	jbtnViewHTML = new HTMLViewerButton();
	jbtnViewHTML.setField("documentName");
        jbtnViewHTML.setBounds(315, 145, 40, 40);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jbtnViewHTML;
  }

  /**
   * This method initializes tmplJButton2
   *
   * @return pt.inescporto.template.client.design.TmplJButton
   */
 /* private PDFViewerButton getJbtnViewPDF() {
    try {
      jbtnViewPDF = new PDFViewerButton();
      jbtnViewPDF.setBounds(365, 145, 40, 40);
      jbtnViewPDF.setField("documentName");
      jbtnViewPDF.setUrl("http://localhost:8080/SIASoft/pdfs");
    }
    catch (java.lang.Throwable e) {
      // TODO: Something
      e.printStackTrace();
    }
    return jbtnViewPDF;
  }*/

 /* private TmplJButton getJbtnAddPDFtoList() {
    try {
      jbtnAddPDFtoList = new TmplJButton();
      jbtnAddPDFtoList.setActionCommand("ADD_PDF");
      jbtnAddPDFtoList.addActionListener(this);
      jbtnAddPDFtoList.setBounds(315, 145, 40, 40);
      jbtnAddPDFtoList.setIcon(new ImageIcon(PDFViewerButton.class.getResource("bookmark.png")));

    }
    catch (java.lang.Throwable e) {
      // TODO: Something
    }
    return jbtnAddPDFtoList;
  }*/

  /**
   * This method initializes tmplJCheckBoxBit1
   *
   * @return pt.inescporto.template.client.design.TmplJCheckBoxBit
   */
  private TmplJCheckBox getJchkbDirect() {
    if (jchkbDirect == null) {
      try {
        jchkbDirect = new TmplJCheckBox();
        jchkbDirect.setText("Directamente");
        jchkbDirect.setEnabled(false);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jchkbDirect;
  }

  /**
   * This method initializes tmplJCheckBoxBit2
   *
   * @return pt.inescporto.template.client.design.TmplJCheckBoxBit
   */
  private TmplJCheckBox getJchkbIndirect() {
    if (jchkbIndirect == null) {
      try {
        jchkbIndirect = new TmplJCheckBox();
        jchkbIndirect.setText("Indirectamente");
        jchkbIndirect.setEnabled(false);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jchkbIndirect;
  }

  /**
   * This method initializes tmplJCheckBoxBit3
   *
   * @return pt.inescporto.template.client.design.TmplJCheckBoxBit
   */
  private TmplJCheckBox getJchkbInformative() {
    if (jchkbInform == null) {
      try {
        jchkbInform = new TmplJCheckBox();
        jchkbInform.setText("Informativo");
        jchkbInform.setEnabled(false);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jchkbInform;
  }
} //  @jve:decl-index=0:visual-constraint="10,10"

class Applicability implements DataSourceListener {
  String field = "regID";
  TplString link = null;
  AsqClientApplicability aca = null;

  TmplJCheckBox jchkbDirect = null;
  TmplJCheckBox jchkbIndirect = null;
  TmplJCheckBox jchkbInform = null;

  public Applicability(TmplJCheckBox jchkbDirect, TmplJCheckBox jchkbIndirect, TmplJCheckBox jchkbInform) {
    this.jchkbDirect = jchkbDirect;
    this.jchkbIndirect = jchkbIndirect;
    this.jchkbInform = jchkbInform;
  }

  public void tmplInitialize(TemplateEvent e) {
    try {
      aca = (AsqClientApplicability)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability");
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
      AsqClientApplicabilityDao acaDao = new AsqClientApplicabilityDao();

      // if user profile does not exist then get applicability for enterprise
      if (curProfile == null) {
//        System.out.println("Using enterprise profile");
        acaDao.enterpriseID.setValue(MenuSingleton.getEnterprise());
        acaDao.regID.setValue(link.getValue());
      }
      else {
        acaDao.enterpriseID.setValue(MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser());
        acaDao.regID.setValue(link.getValue());
        acaDao.userID.setValue(curProfile.userID.getValue());
        acaDao.profileID.setValue(curProfile.profileID.getValue());
//        System.out.println("Using user profile <" + curProfile.profileID.getValue() + "> with enterprise <" + MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser() + ">");
      }

      Vector<AsqClientApplicabilityDao> dataList = aca.findExactWithNulls(acaDao);
      AsqClientApplicabilityDao data = dataList.elementAt(0);

      jchkbDirect.setSelected(data.addDir.getValue());
      jchkbIndirect.setSelected(data.addInd.getValue());
      jchkbInform.setSelected(data.addInf.getValue());

      if (dataList.size() > 1)
        System.err.println("PANIC! got more than one record!");
    }
    catch (FinderException ex) {
    }
    catch (RowNotFoundException ex) {
      jchkbDirect.setSelected(false);
      jchkbIndirect.setSelected(false);
      jchkbInform.setSelected(false);
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }

  public void tmplSave(TemplateEvent e) {

  }

  public void tmplLink(TemplateEvent e) {
    if (e.getLink().getField().equalsIgnoreCase(field))
      link = (TplString)e.getLink();
  }
}
