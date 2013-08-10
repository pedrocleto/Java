package pt.inescporto.siasoft.asq.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import pt.inescporto.template.client.rmi.TmplBasicPanel;
import java.util.Vector;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.asq.ejb.session.TreeBuilder;
import javax.naming.*;
import java.rmi.RemoteException;
import pt.inescporto.template.elements.TplString;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import java.util.Enumeration;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JToggleButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import pt.inescporto.siasoft.asq.ejb.session.Regulatory;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import java.util.Collection;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.FinderException;
import java.util.Date;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.elements.TplObject;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class RegulatoryListPanel extends TmplBasicPanel implements RegulatoryListInterface, ListSelectionListener, ActionListener {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jpnlHeader = new JPanel(new GridBagLayout());
  JLabel jtfldThemeMajor = new JLabel();
  JLabel jtfldThemeMinor = new JLabel();
  JToggleButton jtbtnHasResume = new JToggleButton("+/-");
  JScrollPane jspRegulatory = new JScrollPane();
  JList jlstRegulatory = new JList(new DefaultListModel());
  RegulatoryListCellRenderer regLCR = new RegulatoryListCellRenderer();
  String currentKeys[] = null;
  int currentOrder = -1;
  String currentName = null;
  String currentResume = null;
  Date currentDataIni = null;
  Date currentDataFim = null;
  String currentScope = null;
  String currentSource = null;
  String currentTheme = null;
  String currentTheme1 = null;
  boolean isLegalReq = false;

  private RegulatoryFinderInterface regFinderForm = null;

  public RegulatoryListPanel(RegulatoryFinderInterface regFinderForm) {
    this.regFinderForm = regFinderForm;

    url = "pt.inescporto.siasoft.asq.ejb.session.TreeBuilder";

    initialize();
    init();
    start();
  }

  public void init() {
    super.init();
  }

  private void initialize() {
    // create header panel
    jpnlHeader.setOpaque(false);
    jtfldThemeMajor.setFont(new Font("Dialog", Font.BOLD, 12));
    jtfldThemeMinor.setFont(new Font("Dialog", Font.BOLD, 10));
    jtbtnHasResume.setSelected(true);
    jpnlHeader.add(jtfldThemeMajor, new GridBagConstraints(0, 0, 1, 1, 300.0, 35.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 100, 0));
    jpnlHeader.add(jtbtnHasResume, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
    jpnlHeader.add(jtfldThemeMinor, new GridBagConstraints(0, 1, 1, 1, 300.0, 30.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 20, 0, 0), 80, 0));

    jtbtnHasResume.setActionCommand("RESUME");
    jtbtnHasResume.addActionListener(this);
    jtbtnHasResume.setToolTipText("Vêr/Esconder Resumo");

    jlstRegulatory.setCellRenderer(regLCR);
    jlstRegulatory.addListSelectionListener(this);
    setLayout(borderLayout1);
    jspRegulatory.getViewport().add(jlstRegulatory);

    add(jpnlHeader, java.awt.BorderLayout.NORTH);
    add(jspRegulatory, java.awt.BorderLayout.CENTER);
  }

  private void addNode(RegulatoryListNode node) {
    ((DefaultListModel)jlstRegulatory.getModel()).addElement(node);
  }

  private void clearAll() {
    ((DefaultListModel)jlstRegulatory.getModel()).removeAllElements();
  }

  public void setLink(String majorThemeHeader, String minorThemeHeader, String keys[], int order) {
    clearAll();

    // set header value
    jtfldThemeMajor.setText(majorThemeHeader);
    jtfldThemeMinor.setText(minorThemeHeader);

    if (keys == null) {
      jtbtnHasResume.setEnabled(false);
      return;
    }

    jtbtnHasResume.setEnabled(true);
    currentKeys = keys;
    currentOrder = order;
    currentName = "";
    currentResume = "";

    TreeBuilder treeBuilder = null;
    try {
      treeBuilder = (TreeBuilder)TmplEJBLocater.getInstance().getEJBRemote(url);

      Vector regList = null;
      if (order == 2) {
	UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
	if (curProfile != null) {
//          System.out.println("Using user profile <"  + MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser() + ", " + curProfile.userID.getValue() + ", " + curProfile.profileID.getValue() + "> for building regulatory list!");
	  regList = treeBuilder.getRegulatoryListApplicable(MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser(), curProfile.userID.getValue(), curProfile.profileID.getValue(),
	      keys[0], keys[1], keys[2], keys[3]);
	}
	else {
//          System.out.println("Using enterprise profile <" + MenuSingleton.getEnterprise() + "> for building regulatory list!");
	  regList = treeBuilder.getRegulatoryListApplicable(MenuSingleton.getEnterprise(), null, null, keys[0], keys[1], keys[2], keys[3]);
	}
      }
      else
	regList = treeBuilder.getRegulatoryList(keys[0], keys[1], keys[2], keys[3], order);

      // populate regulatory list
      for (int i = 0; i < regList.size(); i++) {
	Vector row = (Vector)regList.elementAt(i);
	RegulatoryListNode node = new RegulatoryListNode();
	node.setRegId((String)((TplString)row.elementAt(0)).getValue());
	node.setDescription((String)((TplString)row.elementAt(1)).getValue());
	node.setResume((String)((TplString)row.elementAt(2)).getValue());
	addNode(node);
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
      return;
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
      return;
    }
    jspRegulatory.getViewport().revalidate();
  }

  public void selectRegulatory(String regID, boolean isLegalReq) {
    Enumeration<RegulatoryListNode> regEnum = (Enumeration<RegulatoryListNode>)((DefaultListModel)jlstRegulatory.getModel()).elements();
    this.isLegalReq = isLegalReq;
    jlstRegulatory.clearSelection();
    while (regEnum.hasMoreElements()) {
      RegulatoryListNode regNode = regEnum.nextElement();
      if (regNode.getRegId().equals(regID)) {
	jlstRegulatory.setSelectedIndex(((DefaultListModel)jlstRegulatory.getModel()).indexOf(regNode));
	jlstRegulatory.ensureIndexIsVisible(((DefaultListModel)jlstRegulatory.getModel()).indexOf(regNode));
	break;
      }
    }
  }

  public void findByCriterion(String name, String resume) {
    clearAll();

    jtbtnHasResume.setEnabled(true);
    currentKeys = null;
    currentOrder = 3;
    currentName = name;
    currentResume = resume;

    // set header value
    jtfldThemeMajor.setText("Á procura ...");
    jtfldThemeMinor.setText("");

    try {
      Regulatory regulatory = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");

      RegulatoryDao pattern = new RegulatoryDao();
      pattern.name.setValue(name.equals("") ? null : name);
      pattern.resume.setValue(resume.equals("") ? null : resume);

      regulatory.setLinkCondition(null, null);
      Collection<RegulatoryDao> result = regulatory.find(pattern);

      jtfldThemeMajor.setText("Resultados da pesquisa: " + result.size() + " documento(s).");
      jtfldThemeMinor.setText("Critério: Nome <" + (name.equals("") ? "TODOS" : name) + ">, Resumo <" + (resume.equals("") ? "TODOS" : resume) + ">");

      // populate regulatory list
      for (RegulatoryDao regDao : result) {
	RegulatoryListNode node = new RegulatoryListNode();
	node.setRegId(regDao.regId.getValue());
	node.setDescription(regDao.name.getValue());
	node.setResume(regDao.resume.getValue());
	addNode(node);
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
    catch (FinderException ex) {
      ex.printStackTrace();
    }
    catch (RowNotFoundException ex) {
      jtfldThemeMajor.setText("Resultados da pesquisa: 0 documentos.");
      jtfldThemeMinor.setText("Critério: Nome <" + (name.equals("") ? "TODOS" : name) + ">, Resumo <" + (resume.equals("") ? "TODOS" : resume) + ">");
    }

    jspRegulatory.getViewport().revalidate();
  }

  public void valueChanged(ListSelectionEvent e) {
    if (jlstRegulatory.getSelectedIndex() != -1) {
      RegulatoryListNode node = (RegulatoryListNode)jlstRegulatory.getModel().getElementAt(jlstRegulatory.getSelectedIndex());
      regFinderForm.setRegulatoryID(node.getRegId(), this.isLegalReq);
    }
    else
      regFinderForm.setRegulatoryID(null, false);

  }

  /**
   * Implementation of the ActionListenerInterface
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("RESUME")) {
      regLCR.showResume(!jtbtnHasResume.isSelected());
      jlstRegulatory.invalidate();
      jlstRegulatory.repaint();
      invalidate();
      repaint();
      jspRegulatory.getViewport().revalidate();
      if (currentOrder != 3)
	setLink(jtfldThemeMajor.getText(), jtfldThemeMinor.getText(), currentKeys, currentOrder);
      else
	findByCriterion(currentName, currentResume);
    }
  }

  public void findByCriterionFull(String name, String resume, Date dataIni, Date dataFim,
                                  String scope, String source, String theme, String theme1,
      boolean state, boolean revocate, boolean hasApplicability, boolean hasLR) {
    clearAll();

    // set header value
    jtfldThemeMajor.setText("Á procura ...");
    jtfldThemeMinor.setText("");

    try {
      UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
      Regulatory regulatory = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
      Collection<Vector<TplObject>> result = regulatory.findByFullCriterion(name.equals("") ? null : name,
          resume.equals("") ? null : resume, dataIni, dataFim, scope.equals("") ? null : scope,
          source.equals("") ? null : source, theme.equals("") ? null : theme,
          theme1.equals("") ? null : theme1, state, revocate,
          MenuSingleton.isSupplier() ? (hasApplicability ? MenuSingleton.getSelectedEnterprise() : null) : (hasApplicability ? MenuSingleton.getEnterprise() : null),
          (hasApplicability && curProfile != null) ? curProfile.userID.getValue() : null,
          (hasApplicability && curProfile != null) ? curProfile.profileID.getValue() : null,
          hasLR);

      jtfldThemeMajor.setText("Resultados da pesquisa: " + result.size() /*+otherResults.size()*/ + " documento(s).");
      jtfldThemeMinor.setText("Critério: Nome <" + (name.equals("") ? "TODOS" : name) + ">, Resumo <" + (resume.equals("") ? "TODOS" : resume) + ">");

      // populate regulatory list
      for (Vector<TplObject> regRow : result) {
	RegulatoryListNode node = new RegulatoryListNode();
	node.setRegId((String)regRow.elementAt(0).getValueAsObject());
	node.setDescription((String)regRow.elementAt(1).getValueAsObject());
	node.setResume((String)regRow.elementAt(2).getValueAsObject());
	addNode(node);
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    catch (UserException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
    catch (FinderException ex) {
      ex.printStackTrace();
    }
    catch (RowNotFoundException ex) {
      jtfldThemeMajor.setText("Resultados da pesquisa: 0 documentos.");
      jtfldThemeMinor.setText("Critério: Nome <" + (name.equals("") ? "TODOS" : name) + ">, Resumo <" + (resume.equals("") ? "TODOS" : resume) + ">");
    }

    jspRegulatory.getViewport().revalidate();
  }
}
