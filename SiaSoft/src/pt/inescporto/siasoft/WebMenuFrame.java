package pt.inescporto.siasoft;

import javax.swing.*;
import pt.inescporto.template.web.beans.LoggedUser;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.MenuSingleton;
import javax.swing.event.InternalFrameListener;
import java.awt.Container;
import java.awt.event.ActionListener;
import pt.inescporto.siasoft.comun.client.UserData;
import pt.inescporto.template.ejb.session.MenuControl;
import pt.inescporto.template.client.rmi.MenuInterface;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import javax.swing.event.InternalFrameEvent;
import java.util.Vector;
import pt.inescporto.template.web.util.TmplHttpMenuMsg;
import pt.inescporto.template.web.util.TmplHttpMessageSender;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.siasoft.asq.client.rmi.forms.RegulatoryTreeForm;
import java.beans.*;
import pt.inescporto.template.client.rmi.TmplBasicInternalFrame;
import java.awt.Dimension;
import pt.inescporto.siasoft.asq.client.rmi.forms.SearchRegulatoryDlg;
import java.util.Hashtable;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.siasoft.asq.client.rmi.forms.PrintableRegulatoryListFrame;

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
public class WebMenuFrame extends JApplet implements ActionListener, InternalFrameListener, MenuInterface {
  private static final String envType = "WEB";
  LoggedUser usrInfo = null;
  UserData userData = UserData.getInstance();

  private MenuControl menu = null;
  private String urlBase = null;

  private Vector wndCache = new Vector();
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jmbar = new JMenuBar();

  JMenuItem jmenuTree = null;
  JMenuItem jmenuSearch = null;
  JMenuItem jmenuRegulatoryList = null;
  JDesktopPane desktop = null;

  ImageIcon image1 = new ImageIcon(pt.inescporto.siasoft.MenuFrame.class.getResource("profile.png"));
  JLabel statusBar = new JLabel();
  Hashtable<String, RegulatoryDao> regulatoryList = new Hashtable<String, RegulatoryDao>();

  RegulatoryTreeForm frm = null;

  public WebMenuFrame() {
  }

  public void init() {
    // get initialization parameters
    try {
      if (getParameter("urlBase") != null) {
	urlBase = getParameter("urlBase");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void start() {
    TmplHttpMessageSender msgSender = new TmplHttpMessageSender(urlBase + "/MenuProxy");
    TmplHttpMenuMsg msg = new TmplHttpMenuMsg(TmplMessages.MENU_GET_USERINFO, null);
    msg = (TmplHttpMenuMsg)msgSender.doSendObject((Object)msg);
    usrInfo = msg.getUsrInfo();
    MenuSingleton.setMenu(this);

    try {
      menu = (MenuControl)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    jmenuTree.doClick();
  }

  /**
   * Component initialization.
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    setLayout(borderLayout1);
    statusBar.setText(" ");

    jmenuTree = new JMenuItem("Árvore");
    jmenuTree.setActionCommand("TREE");
    jmenuTree.addActionListener(this);
    jmenuTree.setMaximumSize(new Dimension(100, 22));
    jmenuTree.setMinimumSize(new Dimension(100, 22));
    jmenuTree.setPreferredSize(new Dimension(100, 22));

    jmenuSearch = new JMenuItem("Pesquisa");
    jmenuSearch.setMaximumSize(new Dimension(100, 22));
    jmenuSearch.setMinimumSize(new Dimension(100, 22));
    jmenuSearch.setPreferredSize(new Dimension(100, 22));
    jmenuSearch.setActionCommand("SEARCH");
    jmenuSearch.addActionListener(this);

    jmenuRegulatoryList = new JMenuItem("Lista");
    jmenuRegulatoryList.setMaximumSize(new Dimension(100, 22));
    jmenuRegulatoryList.setMinimumSize(new Dimension(100, 22));
    jmenuRegulatoryList.setPreferredSize(new Dimension(100, 22));
    jmenuRegulatoryList.setActionCommand("LIST");
    jmenuRegulatoryList.addActionListener(this);

//    jmbar.add(jmenuTree);
    jmbar.add(jmenuSearch);
    jmbar.add(jmenuRegulatoryList);

    desktop = new JDesktopPane();

    add(jmbar, BorderLayout.NORTH);
    add(desktop, BorderLayout.CENTER);
  }

  /**
   *
   * @param ev ActionEvent
   */
  public void actionPerformed(ActionEvent ev) {
    if (ev.getActionCommand().equals("LIST")) {
      PrintableRegulatoryListFrame printableRegFrame = new PrintableRegulatoryListFrame();
      desktop.add(printableRegFrame);
      printableRegFrame.show();
      printableRegFrame.setVisible(true);
      try {
	printableRegFrame.setMaximum(true);
	printableRegFrame.setClosable(true);
      }
      catch (PropertyVetoException ex) {
      }
      StringBuffer sBuffer = new StringBuffer();

      // write header
      sBuffer.append("<html>\r\n<body>\r\n");
      sBuffer.append("<h1>Lista de Diplomas</h1><hr><br>");

      // write content
      for (RegulatoryDao regDao : regulatoryList.values()) {
	sBuffer.append("<tt><b>");
	sBuffer.append(regDao.name.getValue());
	sBuffer.append("</b></tt><br><tt>");
	sBuffer.append(regDao.resume.getValue());
	sBuffer.append("</tt><br><br><hr><br>");
      }

      // write footer
      sBuffer.append("</body>\r\n</html>\r\n");

      printableRegFrame.setHTMLText(sBuffer.toString());
      try {
	printableRegFrame.setSelected(true);
      }
      catch (PropertyVetoException ex1) {
      }
    }
    if (ev.getActionCommand().equals("TREE")) {
      if (frm == null) {
	frm = new RegulatoryTreeForm();
	desktop.add(frm);
	frm.show();
	frm.setVisible(true);
	try {
	  frm.setMaximum(true);
	}
	catch (PropertyVetoException ex) {
	}
	frm.registerFilterListener(this);
	frm.correctSelect();
      }
      try {
	frm.setSelected(true);
      }
      catch (PropertyVetoException ex1) {
      }
    }
    if (ev.getActionCommand().equals("SEARCH")) {
      SearchRegulatoryDlg dlg = new SearchRegulatoryDlg(null);
      dlg.pack();
      dlg.setVisible(true);

      if (!dlg.jtfldName.getText().equals("") || !dlg.jtfldResume.getText().equals("")) {
	frm.getRegulatoryListListener().findByCriterion(dlg.jtfldName.getText(), dlg.jtfldResume.getText());
      }

      return;
    }
  }

  protected void addFrameToCache(WindowNode node) {
    wndCache.add(node);
  }

  protected void removeFrameFromCache(JInternalFrame wnd) {
    for (int i = 0; i < wndCache.size(); i++) {
      WindowNode node = (WindowNode)wndCache.elementAt(i);
      if (node.frmWindow.equals(wnd)) {
	wndCache.remove(i);
	break;
      }
    }
  }

  protected JInternalFrame getFrameFromCache(String menuId) {
    for (int i = 0; i < wndCache.size(); i++) {
      WindowNode node = (WindowNode)wndCache.elementAt(i);
      if (node.menuId.compareTo(menuId) == 0)
	return node.frmWindow;
    }
    return null;
  }

  public void internalFrameActivated(InternalFrameEvent e) {}

  public void internalFrameClosed(InternalFrameEvent e) {
    removeFrameFromCache(e.getInternalFrame());
    desktop.remove(e.getInternalFrame());
  }

  public void internalFrameClosing(InternalFrameEvent e) {}

  public void internalFrameDeactivated(InternalFrameEvent e) {}

  public void internalFrameDeiconified(InternalFrameEvent e) {}

  public void internalFrameIconified(InternalFrameEvent e) {}

  public void internalFrameOpened(InternalFrameEvent e) {}

  /**
   * Implementation of the MenuInterface
   */

  public String getEnvironmentType() {
    return envType;
  }

  /**
   *
   * @return String
   */
  public String getRole() {
    return usrInfo.getRoleId();
  }

  public String getName() {
    return usrInfo.getUsrId();
  }

  public boolean isSupplier() {
    return usrInfo.isSupplier();
  }

  public String getEnterprise() {
    return usrInfo.getEnterpriseId();
  }

  public String getSelectedEnterprise() {
    return usrInfo.getEnterpriseId();
  }

  public UserData getUserData() {
    return userData;
  }

  public void addContainerFW(Container container) {
    JInternalFrame newWindow = (JInternalFrame)container;
    newWindow.setClosable(true);
    newWindow.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
    newWindow.addInternalFrameListener(this);
    if (newWindow instanceof pt.inescporto.template.client.rmi.TmplBasicInternalFrame)
      ((TmplBasicInternalFrame)newWindow).MinimumPack();
    else {
      newWindow.pack();
      if (newWindow.getHeight() > desktop.getHeight()) {
	newWindow.setPreferredSize(new Dimension(desktop.getHeight(), newWindow.getWidth()));
	newWindow.invalidate();
      }
    }
    newWindow.setVisible(true);
    desktop.add(newWindow);
    try {
      newWindow.setMaximum(true);
      newWindow.setSelected(true);
    }
    catch (java.beans.PropertyVetoException e) {
      e.printStackTrace();
    }
  }

  public void addItemToMenu(String regID, Object regRef) {
    if (!regulatoryList.containsKey(regID)) {
      regulatoryList.put(regID, (RegulatoryDao)regRef);
    }
  }

  public boolean loadForm(String formID, Object keyValue) {
    return false;
  }

  private void buildAndShowList() {
    StringBuffer sBuffer = new StringBuffer();

    // write header
    sBuffer.append("<html>\r\n<body>\r\n");
    sBuffer.append("<h1>Lista de Diplomas</h1><hr><br>");

    // write content
    for (RegulatoryDao regDao : regulatoryList.values()) {
      sBuffer.append("<tt><b>");
      sBuffer.append(regDao.name.getValue());
      sBuffer.append("</b></tt><br><tt>");
      sBuffer.append(regDao.resume.getValue());
      sBuffer.append("</tt><br><br><hr><br>");
    }

    // write footer
    sBuffer.append("</body>\r\n</html>\r\n");

    PrintableRegulatoryListFrame printableRegFrame = new PrintableRegulatoryListFrame();
    printableRegFrame.setClosable(true);
    printableRegFrame.setHTMLText(sBuffer.toString());
    desktop.add(printableRegFrame);
    printableRegFrame.show();
    printableRegFrame.setVisible(true);
    try {
      printableRegFrame.setMaximum(true);
      printableRegFrame.setSelected(true);
    }
    catch (PropertyVetoException ex) {
    }
  }
}

