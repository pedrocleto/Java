package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.rmi.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

import org.jgraph.event.*;
import org.jgraph.graph.*;
import pt.inescporto.siasoft.proc.ejb.dao.*;
import pt.inescporto.siasoft.proc.ejb.session.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.rmi.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.elements.*;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Rute
 * @version 1.0
 */
public class ProcessEditor extends FW_InternalFrameBasic implements GraphicInterface, ActionListener, ProcessEventInterface, GraphSelectionListener, MouseListener, ModuleListener{
  static final int desktopWidth = 970;
  static final int desktopHeight = 600;
  private int mode = 0;
  private String path = new String();
  private char c = '\\';
  private String parentID = null;
  private String moduleSelected = null;
  private EventListenerList componentListenerList = new EventListenerList();
  private GraphUndoManager undoManager = null;
  private GraphPanel graphPanel = new GraphPanel(this, this);
  private Graph graph = graphPanel.getGraph();
  private PathPane pathPane = new PathPane();
  private ToolBarGraph toolBarGraph = new ToolBarGraph(graph);

  private NavegationPanel navegationPanel = new NavegationPanel(graphPanel, this);
  private EnvAspectPane envAspectPane = navegationPanel.getEnvAspectPane();
  private Hashtable<String, ActivityTypeDao> actTypeList = new Hashtable<String, ActivityTypeDao>();
  private Hashtable<String, EnvAspLayerNode> layers = envAspectPane.getLayers();
  private Hashtable<String, Vector> auditList = null;
  private Hashtable<String, Vector> docList = null;
  private Hashtable<String, Vector> eaList = null;
  private Hashtable<String, Vector> epmList = null;
  private Hashtable<String, Vector> funList = null;
  private Hashtable<String, Vector> monitorList = null;
  private Hashtable<String, Vector> ocEmergList = null;
  private Hashtable<String, Vector> regulatoryList = null;
  private Hashtable<String, Vector> formList = null;
  private JLabel lblName = pathPane.getLblName();
  private String enterpriseID = MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise();

  ActivityHasLinksDao actLinksDao;
  private ToolModulePanel toolModulePane = new ToolModulePanel(this);
  private ToolBarModule toolBarModule = toolModulePane.getToolBarModule();
  private EditorCardLayout cardLayout = new EditorCardLayout(toolBarModule);
  private UpdateToolBar updateToolBar = cardLayout.getUpdateToolBar();

  TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();

  TmplJButtonDelete jbtnDelete = new TmplJButtonDelete() {
    public void tmplMode(TemplateEvent e) {
      setEnabled(!(e.getMode() == TmplFormModes.MODE_UPDATE || e.getMode() == TmplFormModes.MODE_INSERT || e.getMode() == TmplFormModes.MODE_LOCK && (int)(formPerms & TmplPerms.PERM_DELETE) != 0));
    }
  };
  TmplJButtonSave jbtnSave = new TmplJButtonSave();
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButtonPrev jbtnPrev = new TmplJButtonPrev();
  TmplJButtonNext jbtnNext = new TmplJButtonNext();
  TmplJButton jbtnUndo = new TmplJButton(new ImageIcon(ProcessEditor.class.getResource("icons/undo.png")));
  TmplJButton jbtnRedo = new TmplJButton(new ImageIcon(ProcessEditor.class.getResource("icons/redo.png")));
  TmplJButtonExit jbtnExit = new TmplJButtonExit();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ProcessEditor() {
    setTitle("processEditor.label.title");
    if (MenuSingleton.isSupplier() && MenuSingleton.getSelectedEnterprise() == null) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
				    res.getString("label.chooseEnterprise"),
				    res.getString("label.warning"),
				    JOptionPane.WARNING_MESSAGE);
      dispose();
      return;
    }

    Box box = Box.createHorizontalBox();
    box.add(toolBarGraph);
    box.createHorizontalStrut(2);
    JScrollPane jspPath = new JScrollPane(pathPane);
    jspPath.setPreferredSize(new Dimension(500, 20));
    box.add(jspPath);

    cardLayout.addComponentToPane(getContentPane());
    getContentPane().add(box, BorderLayout.NORTH);
    getContentPane().add(graphPanel, BorderLayout.CENTER);
    getContentPane().add(navegationPanel, BorderLayout.EAST);
    navegationPanel.setVisible(false);
    hasVisualPerms = true;
    undoManager = new GraphUndoManager() {
      public void undoableEditHappened(UndoableEditEvent e) {
	// First Invoke Superclass
	super.undoableEditHappened(e);
	// Then Update Undo/Redo Buttons
	updateButtons();
      }
    };
    graph.getModel().addUndoableEditListener(undoManager);
    graph.getSelectionModel().addGraphSelectionListener(this);
    graph.addMouseListener(this);
    setPreferredSize(new Dimension(desktopWidth, desktopHeight));
    setMinimumSize(new Dimension(desktopWidth / 2, desktopHeight / 2));

    toolBarGraph.add(jbtnPrev);
    toolBarGraph.add(jbtnNext);

    toolBarGraph.add(jbtnUpdate);
    toolBarGraph.add(jbtnSave);
    toolBarGraph.add(jbtnDelete);

    jbtnUpdate.setVisible(false);
    jbtnDelete.setVisible(false);
    jbtnSave.setVisible(false);

    toolBarGraph.add(jbtnCancel);

    toolBarGraph.add(jbtnUndo);
    jbtnUndo.setVisible(false);
    toolBarGraph.add(jbtnRedo);
    jbtnRedo.setVisible(false);
    toolBarGraph.add(jbtnExit);

    // register this template component listeners
    addTemplateComponentListener(jbtnUpdate);
    addTemplateComponentListener(jbtnDelete);
    addTemplateComponentListener(jbtnSave);
    addTemplateComponentListener(jbtnCancel);
    addTemplateComponentListener(jbtnPrev);
    addTemplateComponentListener(jbtnNext);
    addTemplateComponentListener(jbtnExit);
    addTemplateComponentListener(updateToolBar);

    // add action listener
    jbtnUpdate.addActionListener(this);
    jbtnDelete.addActionListener(this);
    jbtnDelete.setMnemonic(KeyEvent.VK_DELETE);
    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);
    jbtnPrev.addActionListener(this);
    jbtnNext.addActionListener(this);
    jbtnUndo.addActionListener(this);
    jbtnUndo.setActionCommand("UNDO");
    jbtnUndo.setEnabled(false);
    jbtnUndo.setText("undo");
    jbtnUndo.setBorderPainted(false);
    jbtnUndo.setOpaque(false);
    jbtnUndo.setFocusable(false);
    jbtnRedo.addActionListener(this);
    jbtnRedo.setActionCommand("REDO");
    jbtnRedo.setText("redo");
    jbtnRedo.setBorderPainted(false);
    jbtnRedo.setOpaque(false);
    jbtnRedo.setFocusable(false);
    jbtnExit.addActionListener(this);

    setModuleSelected("MProc");
    // load activity types list
    loadActivityTypes();
    // start processing modes
    fireTemplateFormPermission(15);
    setMode(TmplFormModes.MODE_SELECT);
    if (!loadProcess(parentID)) {
      setMode(TmplFormModes.MODE_INSERT);
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
    setOpaque(true);
    setVisible(true);
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    loadActivityHasLinks();
  }

  private void loadActivityTypes() {
    DataSourceRMI datasource = new DataSourceRMI("ActivityTypeClass");
    datasource.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityType");
    try {
      datasource.initialize();
      for (ActivityTypeDao atDao : (Collection<ActivityTypeDao>)datasource.listAll()) {
	actTypeList.put(atDao.activityTypeID.getValue(), atDao);
      }
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("UPDATE")) {
      doUpdate();
    }
    if (e.getActionCommand().equals("SAVE")) {
      doSave();
    }
    if (e.getActionCommand().equals("CANCEL")) {
      doCancel();
    }

    if (e.getActionCommand().equals("NEXT")) {
      doNext();
    }

    if (e.getActionCommand().equals("PREV")) {
      doPrevious();
    }
    if (e.getActionCommand().equals("EXIT")) {
      dispose();
    }
    if (e.getActionCommand().equals("DELETE")) {
      try {
	int pass = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this),
						 res.getString("processEditor.label.deletemessage"), res.getString("processEditor.label.deletetitle"), JOptionPane.OK_CANCEL_OPTION);
	if (pass == JOptionPane.OK_OPTION) {
	  doDelete();
	}
      }
      catch (Exception ex) {
	ex.printStackTrace();
      }
    }
    if (e.getActionCommand().equals("UNDO")) {
      undo();
    }
    if (e.getActionCommand().equals("REDO")) {
      redo();
    }
  }

  public void loadActivityHasLinks() {
    try {
      ActivityHasLinks actLinks = (ActivityHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.ActivityHasLinks");
      try {
	actLinksDao = actLinks.getLinks(parentID, enterpriseID);
      }
      catch (UserException ex) {
	ex.printStackTrace();
      }
      catch (RemoteException ex) {
	ex.printStackTrace();
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
  }

  protected void doUpdate() {
    setMode(TmplFormModes.MODE_UPDATE);
    // clear all components from every layer
    for (EnvAspLayerNode node : layers.values()) {
      node.getJpnlLayer().removeAll();
      node.getJpnlLayer().revalidate();
      node.getJpnlLayer().repaint();
    }
  }

  protected void doSave() {
    if (saveProcess(parentID))
      setMode(TmplFormModes.MODE_SELECT);
    else
      setMode(TmplFormModes.MODE_UPDATE);
  }

  protected void doCancel() {
    graph.clearGraph();
    loadProcess(parentID);
    setMode(TmplFormModes.MODE_SELECT);
  }

  protected void doNext() {
    // clear all components from every layer
    for (EnvAspLayerNode node : layers.values()) {
      node.getJpnlLayer().removeAll();
      node.getJpnlLayer().revalidate();
      node.getJpnlLayer().repaint();
    }
    DefaultGraphCell cell = (DefaultGraphCell)graph.getSelectionCell();
    String name = ((GraphNode)cell.getUserObject()).getName();
    path = path + c + name;
    lblName.setText(path);
//    System.out.println("Caminho" + path);
    if (cell != null && !(cell instanceof DefaultEdge) && !((GraphNode)cell.getUserObject()).isNoChildren()) {
      processChanged(((GraphNode)cell.getUserObject()).getId());
      undoManager.discardAllEdits();
      jbtnUndo.setEnabled(false);
    }
    loadActivityHasLinks();
  }

  protected void doPrevious() {
    int i = path.length() - 1;
    while (i >= 0 && path.charAt(i) != c) {
      i--;
    }
    path = path.substring(0, i);
    lblName.setText(path);
//    System.out.println("Caminho" + path);

    // clear all components from every layer
    for (EnvAspLayerNode node : layers.values()) {
      node.getJpnlLayer().removeAll();
      node.getJpnlLayer().revalidate();
      node.getJpnlLayer().repaint();
    }
    Activity activities = null;
    if (parentID == null) {
      return;
    }
    try {
      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
    }
    catch (NamingException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "Não encontro Activity EJB!!!", "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    // get all activities for this parent
    try {
      activities.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }
    ActivityDao act = new ActivityDao();
    act.activityID.setValue(parentID);

    try {
      activities.findByPrimaryKey(act);
      act = activities.getData();
    }
    catch (UserException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
      return;
    }
    catch (RowNotFoundException ex) {
      return;
    }
    catch (RemoteException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
      return;
    }
    parentID = act.activityFatherID.getValue();
    graph.clearGraph();
    loadProcess(parentID);
    loadActivityHasLinks();
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
    return;
  }

  public void processChanged(String processID) {
    parentID = processID;
    graph.clearGraph();
    if (!loadProcess(parentID)) {
      setMode(TmplFormModes.MODE_INSERT);
    }
  }

  public String getModuleID() {
    return moduleSelected;
  }

  public String getProcessID() {
    return parentID;
  }

  // ***************************************************************************
  //                        Template listener control
  // ***************************************************************************

  public synchronized void addTemplateComponentListener(TemplateComponentListener l) {
    componentListenerList.add(TemplateComponentListener.class, l);
  }

  public synchronized void removeTemplateComponentListener(TemplateComponentListener l) {
    componentListenerList.remove(TemplateComponentListener.class, l);
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> mode events.
   * Creates the event based on current <code>mode</code>.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateMode() {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
	TemplateEvent templateEvent = new TemplateEvent(this);
	templateEvent.setMode(mode);
	((TemplateComponentListener)listeners[i + 1]).tmplMode(templateEvent);
      }
    }
  }

  /**
   * Notify all listeners that have registered interest for
   * notification on <code>TemplateEvent</code> form permissions events.
   * Lazily creates the event.
   * Stops on first false retun.
   *
   * @see TemplateEvent
   * @see TemplateListener
   */
  protected void fireTemplateFormPermission(int permission) {
    // Guaranteed to return a non-null array
    Object[] listeners = componentListenerList.getListenerList();

    // Process the listeners first to last, notifying
    // those that are interested in this event
    for (int i = 0; i <= listeners.length - 2; i += 2) {
      if (listeners[i] == TemplateComponentListener.class) {
	TemplateEvent templateEvent = new TemplateEvent(this, permission);
	((TemplateComponentListener)listeners[i + 1]).tmplPermissions(templateEvent);
      }
    }
  }

  /** **************************************************************************
   * Manage graphic storage
   *
   ** **************************************************************************/
  public boolean saveProcess(String parentID) {
    Activity activities = null;
    ActivityConnection connections = null;

    try {
      // get remote interface for Activity EJB and Connection EJB
      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
      connections = (ActivityConnection)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.ActivityConnection");
    }
    catch (NamingException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message1"), "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    // get all cells (edges and graph cells)
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      // is a Graph cell ?
      if (cellList[i] instanceof DefaultGraphCell && !(cellList[i] instanceof DefaultEdge)) {
	boolean newCell = false;
	DefaultGraphCell cell = (DefaultGraphCell)cellList[i];

	// is a new cell?
	if (((GraphNode)cell.getUserObject()).getId() == null) {
	  newCell = true;
	}

	// instanciate a new activity dao
	ActivityDao act = new ActivityDao();
        //set parent activity
	act.activityFatherID.setValue(parentID);
	act.posX.setValue(new Integer((int)graph.getCellBounds(cell).getX()));
	act.posY.setValue(new Integer((int)graph.getCellBounds(cell).getY()));
	act.dx.setValue(new Integer((int)graph.getCellBounds(cell).getWidth()));
	act.dy.setValue(new Integer((int)graph.getCellBounds(cell).getHeight()));
	act.activityDescription.setValue(((GraphNode)cell.getUserObject()).getName());
	act.enterpriseID.setValue(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise());
	act.activityTypeID.setValue(((GraphNode)cell.getUserObject()).getType());
	act.fkActivityID.setValue(((GraphNode)cell.getUserObject()).getLinkActivityID());

	try {
	  if (newCell) {
	    // INSERT
	    activities.insert(act);
	    act = (ActivityDao)activities.getData();
	    ((GraphNode)cell.getUserObject()).setId(act.activityID.getValue());
	    ((GraphNode)cell.getUserObject()).setIdSequence(act.activityPkSequence.getValue());

	  }
	  else {
	    // UPDATE
	    act.activityID.setValue(((GraphNode)cell.getUserObject()).getId());
	    act.activityPkSequence.setValue(((GraphNode)cell.getUserObject()).getIdSequence());
	    activities.update(act);
	  }
	}
	catch (UserException ex) {
	  JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
	}
	catch (DupKeyException ex) {
	  JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
	}
	catch (RemoteException ex) {
	  JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
	}
      }
    }
    // now save connections
    try {
      for (int i = 0; i < cellList.length; i++) {
	// is it an Edge (arrow) ?
	if (cellList[i] instanceof DefaultGraphCell && cellList[i] instanceof DefaultEdge) {
	  DefaultEdge conn = (DefaultEdge)cellList[i];

	  ActivityConnectionDao acDao = new ActivityConnectionDao();
	  java.util.List list = GraphConstants.getPoints(conn.getAttributes());
	  DefaultPort port = (DefaultPort)conn.getSource();
	  if (list.size() > 4) {
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "As ligações só podem ter 0 ou 2 pontos.", "ERRO", JOptionPane.ERROR_MESSAGE);
	  }

	  DefaultGraphCell cell = (DefaultGraphCell)port.getParent();
	  DefaultPort targetPort = (DefaultPort)conn.getTarget();
          DefaultPort sourcePort = (DefaultPort)conn.getSource();

          String startActivityID = ((GraphNode)((DefaultGraphCell)sourcePort.getParent()).getUserObject()).getId();
          String endActivityID = ((GraphNode)((DefaultGraphCell)targetPort.getParent()).getUserObject()).getId();

	  if (cell != null && targetPort != null) {
	    cell = cell;
	    acDao.startActivityID.setValue(startActivityID);
	    acDao.endActivityID.setValue(endActivityID);

	    // test on server if this connection is new
	    boolean newEdge = false;
	    try {
	      connections.findByPrimaryKey(acDao);
	    }
	    catch (UserException ex2) {
	    }
	    catch (RowNotFoundException ex2) {
	      newEdge = true;
	    }
	    catch (RemoteException ex2) {
	    }

	    if (list.size() == 3 || list.size() == 4) {
	      acDao.x1.setValue(new Integer((int)((Point2D)list.get(1)).getX()));
	      acDao.y1.setValue(new Integer((int)((Point2D)list.get(1)).getY()));
	      if (list.size() == 4) {
                acDao.x2.setValue(new Integer((int)((Point2D)list.get(2)).getX()));
                acDao.y2.setValue(new Integer((int)((Point2D)list.get(2)).getY()));
	      }
	    }
            String startActivityType = ((GraphNode)cell.getUserObject()).getElementType();
             String endActivityType = ((GraphNode)((DefaultGraphCell)((DefaultPort)conn.getTarget()).getParent()).getUserObject()).getElementType();
             if (startActivityType.equals("I") || endActivityType.equals("I")) {
               acDao.type.setValue("L");
             }
             else {
               acDao.type.setValue("A");
             }
	    if (newEdge) {

	      try {
		connections.insert(acDao);
	      }
	      catch (UserException ex) {
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "ERRO", "ERRO", JOptionPane.ERROR_MESSAGE);
	      }
	      catch (DupKeyException ex) {
		// ignore
	      }
	      catch (RemoteException ex) {
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "ERRO", "ERRO", JOptionPane.ERROR_MESSAGE);
	      }
	    }
	    else {
              try {
                connections.update(acDao);
              }
              catch (UserException ex) {
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "ERRO", "ERRO", JOptionPane.ERROR_MESSAGE);
              }
              catch (RemoteException ex) {
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), "ERRO", "ERRO", JOptionPane.ERROR_MESSAGE);
              }
	    }
	  }
	}
      }
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.edgemessage"), "ERRO", JOptionPane.ERROR_MESSAGE);
      setMode(TmplFormModes.MODE_UPDATE);
      return false;
    }
    return true;
  }

  public boolean loadProcess(String parentID) {
    Activity activities = null;
    ActivityConnection connections = null;

    try {
      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
      connections = (ActivityConnection)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.ActivityConnection");
    }
    catch (NamingException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message1"), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    // get all activities for this parent
    ActivityDao act = new ActivityDao();
    Vector binds = new Vector();
    binds.add(new TplString(parentID));
    if (MenuSingleton.isSupplier()) {
      binds.add(new TplString(MenuSingleton.getSelectedEnterprise()));
    }
    else {
      binds.add(new TplString(MenuSingleton.getEnterprise()));
    }

    try {
      if (parentID != null) {
	activities.setLinkCondition("activityFatherID = ? AND fkEnterpriseID = ?", binds);
      }
      else {
	binds.remove(0);
	activities.setLinkCondition("activityFatherID is NULL AND fkEnterpriseID = ?", binds);
      }
    }
    catch (RemoteException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    Iterator<ActivityDao> actList = null;
    try {
      actList = (Iterator<ActivityDao>)activities.listAll().iterator();
    }
    catch (RemoteException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    catch (UserException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    catch (RowNotFoundException ex) {
    }

    if (!actList.hasNext()) {
      return false;
    }
    // process every activity found
    while (actList != null && actList.hasNext()) {
      ActivityDao aDao = actList.next();
      GraphNode node = new GraphNode();
      node.setId(aDao.activityID.getValue());
      node.setName(aDao.activityDescription.getValue());
      node.setIdSequence(aDao.activityPkSequence.getValue());
      node.setType(aDao.activityTypeID.getValue());
      node.setLinkActivityID(aDao.fkActivityID.getValue());

      // get activity type properties for this activity
      ActivityTypeDao actTypeDao = null;
      actTypeDao = actTypeList.get(aDao.activityTypeID.getValue());

      node.setNeadsParent(actTypeDao.neadsParent.getValue());
      node.setNoChildren(actTypeDao.noChildren.getValue());
      node.setAllLevels(actTypeDao.allLevels.getValue());
      node.setElementType(actTypeDao.elementType.getValue());
      node.setHasProperties(actTypeDao.hasLinks.getValue() == null ? false : actTypeDao.hasLinks.getValue());

      aDao.posX.getValue().intValue();
      aDao.posY.getValue().intValue();
      aDao.dx.getValue().intValue();
      aDao.dy.getValue().intValue();

      // add this activity instance to the graph
      graph.addNode(new Point(aDao.posX.getValue().intValue(), aDao.posY.getValue().intValue()), node, aDao.dx.getValue().intValue(), aDao.dy.getValue().intValue(),
		    (ImageIcon)actTypeDao.graphGraph.getValueAsObject());
    }
      // get connections for existing activity's
      Object[] cellList = graph.getRoots();
      for (int i = 0; i < cellList.length; i++) {
	if (cellList[i] instanceof DefaultGraphCell && !(cellList[i] instanceof DefaultEdge)) {
	  DefaultGraphCell sourceCell = (DefaultGraphCell)cellList[i];
	  ActivityConnectionDao partternAcDao = new ActivityConnectionDao();

	  partternAcDao.startActivityID.setValue(((GraphNode)sourceCell.getUserObject()).getId());

          // starting arrows list for this activity
	  Iterator<ActivityConnectionDao> conList = null;
	  try {
	    conList = (Iterator<ActivityConnectionDao>)connections.find(partternAcDao).iterator();
	  }
	  catch (RowNotFoundException ex) {
	  }
	  catch (FinderException ex) {
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
	  }
	  catch (RemoteException ex) {
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
	  }

	  while (conList != null && conList.hasNext()) {
	    ActivityConnectionDao connDao = conList.next();

	    for (int j = 0; j < cellList.length; j++) {
	      if (cellList[j] instanceof DefaultGraphCell && !(cellList[j] instanceof DefaultEdge)) {
		DefaultGraphCell targetCell = (DefaultGraphCell)cellList[j];

		if (((GraphNode)targetCell.getUserObject()).getId().equals(connDao.endActivityID.getValue())) {
                  if (connDao.type.getValue().equals("A")) {
		    Edge edge = graph.addEdge((DefaultPort)sourceCell.getChildAt(0), (DefaultPort)targetCell.getChildAt(0));

		    ArrayList points = new ArrayList();
		    EdgeView ev = (EdgeView)graph.getGraphLayoutCache().getMapping(edge, false);

		    if (connDao.x1.getValue() != null && connDao.y1.getValue() != null) {
		      ev.addPoint(1, new Point2D.Double(connDao.x1.getValue(), connDao.y1.getValue()));
		    }

		    if (connDao.x2.getValue() != null && connDao.y2.getValue() != null) {
		      ev.addPoint(2, new Point2D.Double(connDao.x2.getValue(), connDao.y2.getValue()));
		    }
		  points.addAll(ev.getPoints());
		  GraphConstants.setPoints(edge.getAttributes(), points);
		}

                if (connDao.type.getValue().equals("L")) {
		   InterlevelEdge levelEdge = graph.addInterlevelEdge((DefaultPort)sourceCell.getChildAt(0), (DefaultPort)targetCell.getChildAt(0));
		   ArrayList points = new ArrayList();
		   EdgeView ev = (EdgeView)graph.getGraphLayoutCache().getMapping(levelEdge, false);

		   if (connDao.x1.getValue() != null && connDao.y1.getValue() != null) {
		     ev.addPoint(1, new Point2D.Double(connDao.x1.getValue(), connDao.y1.getValue()));
		   }
		   if (connDao.x2.getValue() != null && connDao.y2.getValue() != null) {
		     ev.addPoint(2, new Point2D.Double(connDao.x2.getValue(), connDao.y2.getValue()));
		   }
		   points.addAll(ev.getPoints());
		   GraphConstants.setPoints(levelEdge.getAttributes(), points);
		  }
                  break;
		}
	      }
	    }
	  }
	}
      }

    return true;
  }

  public int getMode() {
    return mode;
  }

  /**
   *
   * @return boolean
   */
  public void setMode(int mode) {
    this.mode = mode;
    fireTemplateMode();
    switch (mode) {

      case TmplFormModes.MODE_SELECT:
	graph.setMoveable(false);
	graph.setSizeable(false);
	undoManager.discardAllEdits();
	jbtnUndo.setEnabled(false);
	jbtnUpdate.setVisible(true);
	disableButtons();
	navegationPanel.setVisible(true);
        navegationPanel.getTabbedPane().add(navegationPanel.getTabbedPane().getActHasPartPane(), res.getString("processEditor.label.part"));
	CardLayout cl = (CardLayout)(cardLayout.getCards().getLayout());
	cl.show(cardLayout.getCards(), "SELECT");

	break;

      case TmplFormModes.MODE_UPDATE:
	graph.setMoveable(true);
	graph.setSizeable(true);
	enableButtons();
	navegationPanel.setVisible(false);
	cl = (CardLayout)(cardLayout.getCards().getLayout());
	cl.show(cardLayout.getCards(), "UPDATE");

	break;

      case TmplFormModes.MODE_INSERT:
	graph.setMoveable(true);
	graph.setSizeable(true);
	enableButtons();
	navegationPanel.setVisible(false);
	cl = (CardLayout)(cardLayout.getCards().getLayout());
	cl.show(cardLayout.getCards(), "UPDATE");

	break;

      case TmplFormModes.MODE_LOCK:
	undoManager.discardAllEdits();
	jbtnUndo.setEnabled(false);
	graph.setMoveable(false);
	graph.setSizeable(false);
        navegationPanel.getTabbedPane().remove(navegationPanel.getTabbedPane().getActHasPartPane());
	break;
    }
    undoManager.discardAllEdits();
    graph.clearSelection();
  }

  public void enableButtons() {
    jbtnUpdate.setVisible(false);
    jbtnSave.setVisible(true);
    jbtnDelete.setVisible(false);
    jbtnUndo.setVisible(true);
    jbtnRedo.setVisible(true);
    jbtnCancel.setVisible(true);
  }

  public void disableButtons() {
    jbtnCancel.setVisible(false);
    jbtnSave.setVisible(false);
    jbtnDelete.setVisible(true);
    jbtnUndo.setVisible(false);
    jbtnRedo.setVisible(false);
  }

  public GraphPanel getGraphPanel() {
    return graphPanel;
  }

  public String getParentID() {
    return parentID;
  }

  public String getModuleSelected() {
    return moduleSelected;
  }

  public Hashtable getActTypeList() {
    return actTypeList;
  }

  public void setGraphPanel(GraphPanel graphPanel) {
    this.graphPanel = graphPanel;
  }

  public void setModuleSelected(String moduleID) {
    moduleSelected = moduleID;
  }

  public void undo() {
    try {
      undoManager.undo(graph.getGraphLayoutCache());
    }
    catch (Exception ex) {
      System.err.println(ex);
    }
    finally {
      updateButtons();
    }
  }

  public void redo() {
    try {
      undoManager.redo(graph.getGraphLayoutCache());
    }
    catch (Exception ex) {
      System.err.println(ex);
    }
    finally {
      updateButtons();
    }
  }

  public void updateButtons() {
    jbtnUndo.setEnabled(undoManager.canUndo(graph.getGraphLayoutCache()));
    jbtnRedo.setEnabled(undoManager.canRedo(graph.getGraphLayoutCache()));
  }

  public void mousePressed(MouseEvent e) {

    DefaultGraphCell cell = (DefaultGraphCell)graph.getFirstCellForLocation(e.getX(), e.getY());
    if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {

      if (mode == TmplFormModes.MODE_UPDATE || mode == TmplFormModes.MODE_INSERT) {
	if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	  ActivityPopupMenu actPopupMenu = new ActivityPopupMenu(graph);
	  actPopupMenu.getEnvAspListItem().setEnabled(false);
	  actPopupMenu.getActHasPartItem().setEnabled(false);
	  actPopupMenu.getActHasPartListItem().setEnabled(false);
	  actPopupMenu.getActListItem().setEnabled(false);
	  actPopupMenu.show(graphPanel, e.getX(), e.getY());
	}
	if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).getElementType().equals("I")) {
	  InterlevelActivityPopupMenu popupMenu = new InterlevelActivityPopupMenu(graph, this);
	  popupMenu.show(graphPanel, e.getX(), e.getY());
	}
      }
      else {
	if (mode == TmplFormModes.MODE_SELECT) {
	  if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	    ActivityPopupMenu actPopupMenu = new ActivityPopupMenu(graph);
	    actPopupMenu.getActNameItem().setEnabled(false);
	    actPopupMenu.show(graphPanel, e.getX(), e.getY());
	  }
	  if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).getElementType().equals("I")) {
	    InterlevelActivityPopupMenu popupMenu = new InterlevelActivityPopupMenu(graph, this);
	    popupMenu.getLinkItem().setEnabled(false);
	    popupMenu.show(graphPanel, e.getX(), e.getY());
	  }
	}
      if( mode == TmplFormModes.MODE_LOCK && getModuleSelected().equals("smAA")){
        if (cell != null && !(cell instanceof DefaultEdge) && !((GraphNode)cell.getUserObject()).getElementType().equals("I") &&((GraphNode)cell.getUserObject()).isHasProperties()) {
          EnvironmentAspectPopupMenu envAspPopupMenu = new EnvironmentAspectPopupMenu(graph);
          envAspPopupMenu.show(graphPanel, e.getX(), e.getY());
        }
       }
       if( mode == TmplFormModes.MODE_LOCK && getModuleSelected().equals("smAuditor")){
	 if (cell != null && !(cell instanceof DefaultEdge) && !((GraphNode)cell.getUserObject()).getElementType().equals("I") && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	   AuditorPopupMenu auditorPopupMenu = new AuditorPopupMenu(graph);
	   auditorPopupMenu.show(graphPanel, e.getX(), e.getY());
	 }
       }
       if( mode == TmplFormModes.MODE_LOCK && getModuleSelected().equals("smMonitor")){
         if (cell != null && !(cell instanceof DefaultEdge) && !((GraphNode)cell.getUserObject()).getElementType().equals("I") && ((GraphNode)cell.getUserObject()).isHasProperties()) {
           MonitorPopupMenu monitorPopupMenu = new MonitorPopupMenu(graph);
           monitorPopupMenu.show(graphPanel, e.getX(), e.getY());
         }
       }
       if( mode == TmplFormModes.MODE_LOCK && getModuleSelected().equals("MASQ")){
         MasqPopupMenu masqPopupMenu = new MasqPopupMenu(graph);
         masqPopupMenu.show(graphPanel, e.getX(), e.getY());
       }
      }
    }

    if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1 && graph.isSelectionEmpty()) {

      if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_UPDATE) {
	LevelPopupMenu levelPopupMenu = new LevelPopupMenu(parentID, graph);
	levelPopupMenu.getActListItem().setEnabled(false);
	levelPopupMenu.show(graphPanel, e.getX(), e.getY());
      }
      if (mode == TmplFormModes.MODE_SELECT) {
	LevelPopupMenu levelPopupMenu = new LevelPopupMenu(parentID, graph);
	levelPopupMenu.show(graphPanel, e.getX(), e.getY());
      }
    }
  }

  public void mouseClicked(MouseEvent e) {
    DefaultGraphCell cell = (DefaultGraphCell) graph.getFirstCellForLocation(e.getX(), e.getY());
    if (cell != null && e.getClickCount() == 2 && mode == TmplFormModes.MODE_SELECT) {
      // clear all components from every layer
      for (EnvAspLayerNode node : layers.values()) {
	node.getJpnlLayer().removeAll();
	node.getJpnlLayer().revalidate();
	node.getJpnlLayer().repaint();
      }
      if (cell != null && !(cell instanceof DefaultEdge) && !((GraphNode)cell.getUserObject()).isNoChildren()) {
	graph.clearGraph();
	String name = ((GraphNode)cell.getUserObject()).getName();
	path = path + c + name;
//	System.out.println("Caminho" + path);
	lblName.setText(path);

        String processID = ((GraphNode)cell.getUserObject()).getId();
	processChanged(processID);
	loadActivityHasLinks();
//	System.out.println("Selected cell named " + name);
	undoManager.discardAllEdits();
	jbtnUndo.setEnabled(false);
      }
    }
    if (cell != null && cell.getUserObject() != null && cell.getUserObject() instanceof GraphNode && ((GraphNode)cell.getUserObject()).getElementType().equals("I") &&
	((GraphNode)cell.getUserObject()).getLinkActivityID() != null && e.getClickCount() == 2 && mode == TmplFormModes.MODE_SELECT) {
      String linkActivityID = ((GraphNode)cell.getUserObject()).getLinkActivityID();
      if (!linkActivityID.equals("")) {
	graph.clearGraph();
	lblName.setText("");
	loadActivityParentForInterLevelProcess(((GraphNode)cell.getUserObject()));
      }
    }

    if (moduleSelected.equals("MASQ")) {

      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	regulatoryList = (Hashtable<String, Vector>)actLinksDao.regulatoryList;
	if (regulatoryList.containsKey(activityID)) {
	  Vector v = actLinksDao.regulatoryList.get(activityID);
	  navegationPanel.getTabbedPane().refresh(v);
	}
	else
	  removeRows();
      }
      else
	removeRows();
    }
    if (moduleSelected.equals("MDoc")) {
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	docList = (Hashtable<String, Vector>)actLinksDao.docList;
	if (docList.containsKey(activityID)) {
	  Vector v = actLinksDao.docList.get(activityID);
	  navegationPanel.getTabbedPane().refresh(v);
	}
	else
	  removeRows();
      }
      else
	removeRows();
    }
    if (moduleSelected.equals("MForm")) {
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	formList = (Hashtable<String, Vector>)actLinksDao.formList;
	if (formList.containsKey(activityID)) {
	  Vector v = actLinksDao.formList.get(activityID);
	  navegationPanel.getTabbedPane().refresh(v);
	}
	else
	  removeRows();
      }
      else
	removeRows();
    }

    if (moduleSelected.equals("smAA")) {

	  if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
	    String activityID = ((GraphNode)cell.getUserObject()).getId();
	    eaList = (Hashtable<String, Vector>)actLinksDao.eaList;
	    if (eaList.containsKey(activityID)) {
	      Vector v = actLinksDao.eaList.get(activityID);
	      navegationPanel.getTabbedPane().refresh(v);
	    }
	    else
	      removeRows();
	  }
	  else
	    removeRows();
    }
    if (moduleSelected.equals("smFun")) {
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
        String activityID = ((GraphNode)cell.getUserObject()).getId();
	funList = (Hashtable<String, Vector>)actLinksDao.funList;
	if (funList.containsKey(activityID)) {
          Vector v = actLinksDao.funList.get(activityID);
	  navegationPanel.getTabbedPane().refresh(v);
        }
	else
          removeRows();
        }
	else
          removeRows();
   }
   if (moduleSelected.equals("smMonitor")) {
     if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
       String activityID = ((GraphNode)cell.getUserObject()).getId();
       monitorList = (Hashtable<String, Vector>)actLinksDao.monitorList;
       if (monitorList.containsKey(activityID)) {
         Vector v = actLinksDao.monitorList.get(activityID);
	 navegationPanel.getTabbedPane().refresh(v);
      }
      else
        removeRows();
      }
      else
        removeRows();
  }
  if (moduleSelected.equals("smAuditor")) {
    if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
      String activityID = ((GraphNode)cell.getUserObject()).getId();
      auditList = (Hashtable<String, Vector>)actLinksDao.auditList;
      if (auditList.containsKey(activityID)) {
        Vector v = actLinksDao.auditList.get(activityID);
	navegationPanel.getTabbedPane().refresh(v);
      }
      else
        removeRows();
      }
      else
        removeRows();
  }
  if (moduleSelected.equals("smPGA")) {
    if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
      String activityID = ((GraphNode)cell.getUserObject()).getId();
      epmList = (Hashtable<String, Vector>)actLinksDao.epmList;
      if (epmList.containsKey(activityID)) {
        Vector v = actLinksDao.epmList.get(activityID);
	navegationPanel.getTabbedPane().refresh(v);
      }
      else
        removeRows();
      }
      else
        removeRows();
  }
  if (moduleSelected.equals("smCO_EMERG")) {
    if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties() && e.getClickCount() == 1) {
      String activityID = ((GraphNode)cell.getUserObject()).getId();
      ocEmergList = (Hashtable<String, Vector>)actLinksDao.ocEmergList;
      if (ocEmergList.containsKey(activityID)) {
        Vector v = actLinksDao.ocEmergList.get(activityID);
	navegationPanel.getTabbedPane().refresh(v);
      }
      else
        removeRows();
      }
      else
        removeRows();
      }
  }

  public void removeRows() {
    TableModuleModel tableModel = navegationPanel.getTabbedPane().getActivityHasLinksPane().getTableModel();
    tableModel.setDataVector(new Vector());
  }

  public void mouseReleased(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void valueChanged(GraphSelectionEvent graphSelectionEvent) {
    boolean enabled = !graph.isSelectionEmpty();
    DefaultGraphCell cell = (DefaultGraphCell) graph.getSelectionCell();

    if (mode == TmplFormModes.MODE_SELECT) {
      jbtnNext.setEnabled(enabled);
      if (cell != null && !(cell instanceof DefaultEdge) && !((GraphNode)cell.getUserObject()).isNoChildren())
	jbtnNext.setEnabled(true);
      else
	jbtnNext.setEnabled(false);

      if (parentID != null)
	jbtnPrev.setEnabled(true);
      else
	jbtnPrev.setEnabled(false);
    }
  }

  public void modelChanged(String moduleID) {
    moduleSelected = moduleID;
    for (EnvAspLayerNode node : layers.values()) {
      node.getJpnlLayer().removeAll();
      node.getJpnlLayer().validate();
      node.getJpnlLayer().repaint();
    }

    if (moduleSelected.equals("MProc")) {
       navegationPanel.getTabbedPane().getActHasPartPane().setVisible(true);
      clearBorder();
      removeRows();
      setMode(TmplFormModes.MODE_SELECT);
    }
    if (moduleSelected.equals("MASQ")) {
       navegationPanel.getTabbedPane().getActHasPartPane().setVisible(false);
      clearBorder();
      Object[] cellList = graph.getRoots();
      for (int i = 0; i < cellList.length; i++) {
	DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
	if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	  String activityID = ((GraphNode)cell.getUserObject()).getId();
	  regulatoryList = (Hashtable<String, Vector>)actLinksDao.regulatoryList;
	  if (regulatoryList.containsKey(activityID)) {
	    Map nested = new Hashtable();
	    Map attributeMap = new Hashtable();
	    GraphConstants.setBorderColor(cell.getAttributes(), new Color(107, 142, 35));
	    GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	    nested.put(cell, attributeMap);
	    graph.getGraphLayoutCache().edit(nested, null, null, null);
	  }
	}
      }
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }

    if (moduleSelected.equals("MDoc")) {
      clearBorder();
      Object[] cellList = graph.getRoots();
      for (int i = 0; i < cellList.length; i++) {
	DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
	if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	  String activityID = ((GraphNode)cell.getUserObject()).getId();
	  docList = (Hashtable<String, Vector>)actLinksDao.docList;
	  if (docList.containsKey(activityID)) {
	    Map nested = new Hashtable();
	    Map attributeMap = new Hashtable();
	    GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 182, 193)); //255, 230, 225
	    GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	    nested.put(cell, attributeMap);
	    graph.getGraphLayoutCache().edit(nested, null, null, null);
	  }
	}
      }
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if (moduleSelected.equals("MCom")) {
      clearBorder();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if (moduleSelected.equals("MForm")) {
      clearBorder();
      Object[] cellList = graph.getRoots();
      for (int i = 0; i < cellList.length; i++) {
	 DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
	if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	  String activityID = ((GraphNode)cell.getUserObject()).getId();
	  formList = (Hashtable<String, Vector>)actLinksDao.formList;
	  if (formList.containsKey(activityID)) {
	    Map nested = new Hashtable();
	    Map attributeMap = new Hashtable();
	    GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 218, 185));
	    GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	    nested.put(cell, attributeMap);
	    graph.getGraphLayoutCache().edit(nested, null, null, null);
	  }
	}
      }
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if(moduleSelected.equals("smAA")){
      clearBorder();
      updateAA();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if (moduleSelected.equals("smAuditor")) {
      clearBorder();
      updateAuditor();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if (moduleSelected.equals("smCO_EMERG")) {
      clearBorder();
      updateEmerg();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if(moduleSelected.equals("smMonitor")){
      clearBorder();
      updateMonitor();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if(moduleSelected.equals("smPGA")) {
      clearBorder();
      updatePGA();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
    if (moduleSelected.equals("smFun")) {
      clearBorder();
      updateFUN();
      removeRows();
      setMode(TmplFormModes.MODE_LOCK);
    }
  }

  public void updateAA() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	eaList = (Hashtable<String, Vector>)actLinksDao.eaList;
	if (eaList.containsKey(activityID)) {
	  Map nested = new Hashtable();
	  Map attributeMap = new Hashtable();
	  GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 215, 0));
	  GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	  nested.put(cell, attributeMap);
	  graph.getGraphLayoutCache().edit(nested, null, null, null);
	}
      }
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
  }

  public void updateAuditor() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	auditList = (Hashtable<String, Vector>)actLinksDao.auditList;
	if (auditList.containsKey(activityID)) {
	  Map nested = new Hashtable();
	  Map attributeMap = new Hashtable();
	  GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 215, 0));
	  GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	  nested.put(cell, attributeMap);
	  graph.getGraphLayoutCache().edit(nested, null, null, null);
	}
      }
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
  }

  public void updateEmerg() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	ocEmergList = (Hashtable<String, Vector>)actLinksDao.ocEmergList;
	if (ocEmergList.containsKey(activityID)) {
	  Map nested = new Hashtable();
	  Map attributeMap = new Hashtable();
	  GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 215, 0));
	  GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	  nested.put(cell, attributeMap);
	  graph.getGraphLayoutCache().edit(nested, null, null, null);
	}
      }
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
  }

  public void updateMonitor() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	monitorList = (Hashtable<String, Vector>)actLinksDao.monitorList;
	if (monitorList.containsKey(activityID)) {
	  Map nested = new Hashtable();
	  Map attributeMap = new Hashtable();
	  GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 215, 0));
	  GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	  nested.put(cell, attributeMap);
	  graph.getGraphLayoutCache().edit(nested, null, null, null);
	}
      }
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
  }

  public void updateFUN() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	funList = (Hashtable<String, Vector>)actLinksDao.funList;
	if (funList.containsKey(activityID)) {
	  Map nested = new Hashtable();
	  Map attributeMap = new Hashtable();
	  GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 215, 0));
	  GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	  nested.put(cell, attributeMap);
	  graph.getGraphLayoutCache().edit(nested, null, null, null);
	}
      }
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
  }

  public void updatePGA() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
     DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	String activityID = ((GraphNode)cell.getUserObject()).getId();
	epmList = (Hashtable<String, Vector>)actLinksDao.epmList;
	if (epmList.containsKey(activityID)) {
	  Map nested = new Hashtable();
	  Map attributeMap = new Hashtable();
	  GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 215, 0));
	  GraphConstants.setLineWidth(cell.getAttributes(), 3.0f);
	  nested.put(cell, attributeMap);
	  graph.getGraphLayoutCache().edit(nested, null, null, null);
	}
      }
    }
    undoManager.discardAllEdits();
    jbtnUndo.setEnabled(false);
  }

  public void clearBorder() {
    Object[] cellList = graph.getRoots();
    for (int i = 0; i < cellList.length; i++) {
      DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
      if (cell != null && !(cell instanceof DefaultEdge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {
	Map nested = new Hashtable();
	Map attributeMap = new Hashtable();
	GraphConstants.setBorderColor(cell.getAttributes(), new Color(255, 250, 250));
	GraphConstants.setLineWidth(cell.getAttributes(), 2.0f);
	nested.put(cell, attributeMap);
	graph.getGraphLayoutCache().edit(nested, null, null, null);
      }
    }
  }

  public void addNode(Point2D point, GraphNode userObject, Integer w, Integer h, ImageIcon icon) {}

  public Edge addEdge(Port source, Port target) {
    return null;
  }

  public InterlevelEdge addInterlevelEdge(Port source, Port target) {
    return null;
  }

  public void doDelete() {
    if (!graph.isSelectionEmpty()) {
      Vector<String> actListID = new Vector<String>();

      Object[] cells = graph.getSelectionCells();
      cells = graph.getDescendants(cells);

      Activity activities = null;
      ActivityConnection connections = null;

      try {
	activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
	connections = (ActivityConnection)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.ActivityConnection");
	for (int i = 0; i < cells.length; i++) {
	  if (cells[i] instanceof DefaultGraphCell && !(cells[i] instanceof DefaultEdge) && ((DefaultGraphCell)cells[i]).getUserObject() != null)
	    actListID.add(((GraphNode)((DefaultGraphCell)cells[i]).getUserObject()).getId());
	}

	for (int i = 0; i < cells.length; i++) {
	  if (cells[i] instanceof DefaultGraphCell && cells[i] instanceof DefaultEdge) {
	    DefaultEdge conn = (DefaultEdge)cells[i];
	    DefaultPort targetPort = (DefaultPort)conn.getTarget();
	    DefaultPort sourcePort = (DefaultPort)conn.getSource();

            String startActivityID = ((GraphNode)((DefaultGraphCell)sourcePort.getParent()).getUserObject()).getId();
            String endActivityID = ((GraphNode)((DefaultGraphCell)targetPort.getParent()).getUserObject()).getId();

            ActivityConnectionDao activityConnectionDao = new ActivityConnectionDao();
	    activityConnectionDao.startActivityID.setValue(startActivityID);
	    activityConnectionDao.endActivityID.setValue(endActivityID);

	    try {
	      connections.delete(activityConnectionDao);
	    }
	    catch (UserException ex2) {
              ex2.printStackTrace();
	    }
	    catch (ConstraintViolatedException ex2) {
              ex2.printStackTrace();
	    }
	    catch (RemoteException ex2) {
              ex2.printStackTrace();
	    }
	  }
	}

	try {
	  activities.delete(actListID);
	  graph.getModel().remove(cells);

	}
	catch (UserException ex1) {
	  ex1.printStackTrace();
	}
	catch (ConstraintViolatedException ex1) {
	  JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message"), "ERRO", JOptionPane.ERROR_MESSAGE);
	}
	catch (RemoteException ex1) {
	  ex1.printStackTrace();
	}
      }
      catch (NamingException ex) {
	JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message1"), "Informação", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  private void jbInit() throws Exception {
  }

  public String loadActivityDesc(GraphNode node) {
    String linkActivityId = node.getLinkActivityID();
    String activityDesc = null;
    Activity activities = null;

    try {
      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
    }
    catch (NamingException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message1"), "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    ActivityDao activityDao = new ActivityDao();
    activityDao.activityID.setValue(linkActivityId);

    try {
      activities.findByPrimaryKey(activityDao);
      activityDao = activities.getData();
    }
    catch (RowNotFoundException ex) {
    }
    catch (UserException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    catch (RemoteException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    activityDesc = activityDao.activityDescription.getValue();
    return activityDesc;
  }

  public void loadActivityParentForInterLevelProcess(GraphNode node) {
    String linkActivityId = node.getLinkActivityID();

    Activity activities = null;

    try {
      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
    }
    catch (NamingException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message1"), "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    try {
      activities.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }

    ActivityDao activityDao = new ActivityDao();
    activityDao.activityID.setValue(linkActivityId);

    try {
      activities.findByPrimaryKey(activityDao);
      activityDao = activities.getData();
    }
    catch (RowNotFoundException ex) {
    }
    catch (UserException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    catch (RemoteException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    parentID = activityDao.activityFatherID.getValue();
    String activitySequence = activityDao.activityPkSequence.getValue();
    path = " ";
    if(activitySequence.length() != 0){


    while(activitySequence.indexOf(".") != -1)
    {
      int pos= activitySequence.indexOf(".");
      String activityID = activitySequence.substring(0,pos);
      activitySequence = activitySequence.substring (pos + 1, activitySequence.length());
      String activityDesc = getActivityDescription(activityID);
      path = path + c + activityDesc;
    }

    lblName.setText(path);
    }

    loadProcess(parentID);
    loadActivityHasLinks();

    jbtnUndo.setEnabled(false);
    jbtnRedo.setEnabled(false);
  }

  public String getActivityDescription(String activityID){
    Activity activities = null;

    try {
      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
    }
    catch (NamingException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), res.getString("processEditor.label.message1"), "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    //get each activity name
    ActivityDao actDao = new ActivityDao();
    actDao.activityID.setValue(activityID);
    try {
      activities.findByPrimaryKey(actDao);
      actDao = activities.getData();

    }
    catch (RowNotFoundException ex) {
    }
    catch (UserException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
    catch (RemoteException ex) {
      JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
    }
   return actDao.activityDescription.getValue();
  }

}
