package pt.inescporto.template.client.design;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.sql.Timestamp;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.utils.AttrsToVector;
import pt.inescporto.template.web.util.*;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.drmi.TmplRMILocater;

public class TmplDlgLookup extends JDialog {
  private int commType = 0;
  private Object remote = null;
  private String url;
  private String[] headers;
  private Object attrs = null;
  private Collection all;
  private Collection list;
  private JTextField[] fill = null;
  private ArrayList listFill = null;
  private TmplQuery query = null;
  private JButton jbtnOk = new JButton();
  private JButton jbtnCancel = new JButton();
  private TableColumnModel cm = new DefaultTableColumnModel() {
    public void addColumn(TableColumn tc) {
      tc.setMinWidth(150);
      super.addColumn(tc);
    }
  };
  private TmplDefaultTableModel tm = new TmplDefaultTableModel();
  private JTable jt = null;
  private TmplHttpMessageSender httpSender = null;
  private String staticLinkCondition = null;
  private JComponent detailLink[] = null;
  private boolean isMaster = true;

  // public constructor
  public TmplDlgLookup(JFrame owner, String title, String url, String[] headers, JTextField[] fill) {
    this(owner, title, url, headers, fill, null, null);
  }

  /**
   * Used to set up lookup views with link conditions (static and/or dinamic).
   * @param title
   * @param url
   * @param headers
   * @param fill
   * @param staticlinkCondition
   * @param detailLink
   */
  public TmplDlgLookup(JFrame owner, String title, String url, String[] headers, JTextField[] fill, String staticLinkCondition, JComponent detailLink[]) {
    super(owner);
    this.url = url;
    this.list = null;
    this.headers = headers == null ? new String[] {}
         : headers;
    this.setTitle(title);
    this.fill = fill;
    this.staticLinkCondition = staticLinkCondition;
    this.detailLink = detailLink;
    this.setModal(true);
    this.setFont(new Font("Dialog", 0, 10));

    if (url.startsWith("http")) {
      commType = 1;
      this.httpSender = new TmplHttpMessageSender(this.url);
    }
    else {
      commType = 2;
      try {
        remote = TmplEJBLocater.getInstance().getEJBRemote(url);
      }
      catch (Exception ex) {
        remote = TmplRMILocater.getInstance().getRMIHome(url);
        if (remote == null)
          ex.printStackTrace();
      }
    }

    try {
      initialize();
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setVisible(true);
  }

  /** ***
   * Used for listAll from TmplAppletForm
   ** ***/
  public TmplDlgLookup(JFrame owner, String title, String url, String[] headers, ArrayList listFill, boolean isMaster) {
    super(owner);
    this.url = url;
    this.list = null;
    this.headers = headers == null ? new String[] {} : headers;
    this.setTitle(title);
    this.listFill = listFill;
    this.isMaster = isMaster;
    this.setModal(true);
    this.setFont(new Font("Dialog", 0, 10));

    if (url.startsWith("http")) {
      commType = 1;
      this.httpSender = new TmplHttpMessageSender(this.url);
    }
    else {
      commType = 2;
      try {
        remote = TmplEJBLocater.getInstance().getEJBRemote(url);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    try {
      initialize();
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setVisible(true);
  }

  /**
   * Used for listAll from TmplAppletForm and TmplNavFrame (with link condition)
   * */
  public TmplDlgLookup(JFrame owner, String title, String url, String[] headers, ArrayList listFill, Object remote) {
    super(owner);
    this.url = url;
    this.list = null;
    this.headers = headers == null ? new String[] {} : headers;
    this.setTitle(title);
    this.listFill = listFill;
    this.setModal(true);
    this.setFont(new Font("Dialog", 0, 10));

    if (url.startsWith("http")) {
      commType = 1;
      this.httpSender = new TmplHttpMessageSender(this.url);
    }
    else {
      commType = 2;
      this.remote = remote;
    }

    try {
      initialize();
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setVisible(true);
  }

  /** ***
   * Used for doFindRes from TmplAppletForm
   ** ***/
  public TmplDlgLookup(JFrame owner, String title, Collection list, String[] headers, ArrayList listFill) {
    super(owner);
    this.url = null;
    this.list = list;
    this.headers = headers == null ? new String[] {} : headers;
    this.setTitle(title);
    this.listFill = listFill;
    this.setModal(true);
    this.setFont(new Font("Dialog", 0, 10));

    this.httpSender = new TmplHttpMessageSender(this.url);

    try {
      initialize();
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setVisible(true);
  }

  public TmplDlgLookup(JFrame owner, String title, TmplQuery query, String[] headers, JTextField[] fill) {
    super(owner);
    this.url = null;
    this.list = null;
    this.headers = headers == null ? new String[] {} : headers;
    this.setTitle(title);
    this.fill = fill;
    this.query = query;
    this.setModal(true);
    this.setFont(new Font("Dialog", 0, 10));

    this.httpSender = null;

    try {
      initialize();
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setVisible(true);
  }

  // do table fill
  protected void initialize() throws Exception {
    if (this.url != null) { // case url
      // adjust columns
      if (this.headers.length == 0)
        this.doGetAttrs();
      else {
        for (int i = 0; i < this.headers.length; i++)
          tm.addColumn(this.headers[i]);
      }

      // populate table contents
      doSend(TmplMessages.MSG_LISTALL);
    }
    else if (this.list != null) {
      // adjust columns
      if (this.headers.length == 0) {
        this.attrs = this.list.iterator().next();
        this.createColumns();
      }
      else {
        for (int i = 0; i < this.headers.length; i++)
          tm.addColumn(this.headers[i]);
      }

      // populate table contents
      this.all = AttrsToVector.ListConvert(this.list);
      this.populate();
    }
    else {
      Vector result = this.query.doQuery();
      if (this.headers.length == 0) {
        this.createColumns((Vector)result.firstElement());
      }
      else {
        for (int i = 0; i < this.headers.length; i++)
          tm.addColumn(this.headers[i]);
      }

      // populate table contents
      this.all = AttrsToVector.ListConvert(result);
      this.populate();
    }

    // create table
    jt = new JTable(tm, cm);
    jt.createDefaultColumnsFromModel();

    // adjust table
    jt.setSelectionMode(0);
    jt.setFont(new Font("Dialog", 0, 10));
    jt.getTableHeader().setFont(new Font("Dialog", 0, 10));

    // adjust dialog to center of screen
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - 450) / 2;
    int y = (screen.height - 500) / 2;
    this.setBounds(x, y, 450, 500);

    //hook actions of Ok and Cancel buttons
    this.jbtnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okActionPerformed(e);
      }
    });
    this.jbtnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelActionPerformed(e);
      }
    });

    // get mouse clicks in table
    jt.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
          okActionPerformed(new ActionEvent(this, 0, "DOUBLE"));
        }
      }

      public void mouseEntered(MouseEvent me) {
      }

      public void mouseExited(MouseEvent me) {
      }

      public void mousePressed(MouseEvent me) {
      }

      public void mouseReleased(MouseEvent me) {
      }
    });
  }

  // initialize GUI
  private void jbInit() throws Exception {
    if (jt.getModel().getColumnCount() >= 3)
      jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    JScrollPane jsp = new JScrollPane(jt);
    jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp.setBounds(new Rectangle(0, 0, 450, 450));
    this.getContentPane().add(jsp, null);

    this.jbtnOk.setText(TmplResourceSingleton.getString("button.ok"));
    this.jbtnCancel.setText(TmplResourceSingleton.getString("button.cancel"));

    JPanel jpButtons = new JPanel();
    jpButtons.add(jbtnOk);
    jpButtons.add(jbtnCancel);
    jpButtons.setBounds(new Rectangle(0, 450, 450, 500));

    this.getContentPane().add(jpButtons, BorderLayout.SOUTH);
  }

  // send initialization messages
  protected int doGetAttrs() {
    int errorCode = TmplMessages.MSG_OK;

    // process HTTP source
    if (commType == 1) {
      TmplHttpMessage msg = new TmplHttpMessage(TmplMessages.MSG_INITIALIZE, this.attrs);

      try {
        msg = (TmplHttpMessage)httpSender.doSendObject((Object)msg);
        this.attrs = msg.getAttrs();

        createColumns();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

      errorCode = msg.getReturnCode();
    }

    // process RMI source
    if (commType == 2) {
      Method[] methodList = remote.getClass().getMethods();
      Method execMethod = null;
      Class parametersType[] = null;

      for (int i = methodList.length - 1; i >= 0; i--) {
        Method curMethod = methodList[i];
        if (curMethod.getName().equals("setData")) {
          parametersType = curMethod.getParameterTypes();
          break;
        }
      }
      if (parametersType != null) {
        try {
          attrs = parametersType[0].newInstance();
          createColumns();
        }
        catch (InstantiationException ex) {
          ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
          ex.printStackTrace();
        }
      }
    }

    return errorCode;
  }

  /**
   * @todo test for link condition on the rmi case.
   * @todo make the link condition for the http case.
   * @param toDo
   * @return
   */
  protected int doSend(int toDo) {
    int errorCode = TmplMessages.MSG_OK;

    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

    // HTTP
    if (commType == 1) {
      TmplHttpMessage msg = new TmplHttpMessage(toDo, this.attrs);
      msg.setAsMaster(isMaster);
      try {
        msg = (TmplHttpMessage)httpSender.doSendObject((Object)msg);
        errorCode = msg.getReturnCode();
        this.attrs = msg.getAttrs();
        this.all = AttrsToVector.ListConvert(msg.getAll());
        populate();
      }
      catch (Exception ex) {
        this.showInformationMessage(TmplResourceSingleton.getString("error.msg.connection"));
      }
    }
    // RMI
    if (commType == 2) {
      try {
        Method execMethod = null;

        if (staticLinkCondition != null || detailLink != null) {
          if (detailLink != null) {
            execMethod = remote.getClass().getMethod("setLinkCondition", new Class[] {String.class, Vector.class});
            execMethod.invoke(remote, new Object[] {getLinkConditionStmt(detailLink), getLinkConditionBinds(detailLink)});
          }
          else if (detailLink == null) {
            //Alteracao 30-9-03
            Class c = remote.getClass();
            Class[] ParameterTypes = new Class[] {String.class, Vector.class};
            Object[] args = new Object[] {this.staticLinkCondition, new Vector()};

            try {
              execMethod = c.getMethod("setLinkCondition", ParameterTypes);
              execMethod.invoke(remote, args);
            }
            catch (Exception ex) {
              execMethod = null;
            }
            //Fim Alteracao 30-9-03
          }
        }

        execMethod = remote.getClass().getMethod("listAll");
        this.all = AttrsToVector.ListConvert((Collection)execMethod.invoke(remote));

        populate();
      }
      catch (Exception ex) {
        this.showInformationMessage(TmplResourceSingleton.getString("error.msg.connection"));
        errorCode = TmplMessages.NOT_DEFINED;
      }
    }

    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    return errorCode;
  }

  protected void createColumns() {
    Field[] fld;
    int i;

    fld = this.attrs.getClass().getDeclaredFields();

    // process any field
    for (i = 0; i < fld.length; i++) {
      try {
        Object test = fld[i].get(attrs);
        // process template objects
        if (test instanceof pt.inescporto.template.elements.TplObject) {
          TplObject myObject = null;
          myObject = (TplObject)test;
          this.tm.addColumn(myObject.getField());
        }
      }
      catch (Exception e) {
        System.out.println(this.getClass().getName() + " : " + e.toString());
      }
    }
  }

  protected void createColumns(Vector row) {
    Iterator i = row.iterator();
    // process any field
    while (i.hasNext()) {
      try {
        Object test = i.next();
        // process template objects
        if (test instanceof pt.inescporto.template.elements.TplObject) {
          TplObject myObject = null;
          myObject = (TplObject)test;
          this.tm.addColumn(myObject.getField());
        }
      }
      catch (Exception e) {
        System.out.println(this.getClass().getName() + " : " + e.toString());
      }
    }
  }

  protected void populate() {
    Iterator it = all.iterator();

    while (it.hasNext())
      tm.addRow((Vector)it.next());
  }

  protected void okActionPerformed(ActionEvent e) {
    if (fill == null && listFill == null) {
      this.dispose();
      return;
    }

    if (jt.getSelectedRow() == -1)
      this.showErrorMessage(TmplResourceSingleton.getString("error.msg.notSelected"));
    else {
      if (fill != null) {
        for (int j = 0; j < fill.length; j++) {
          Object value = tm.getValueAt(jt.getSelectedRow(), j);
          if (value instanceof java.lang.String)
            fill[j].setText((String)value);
          else if (value instanceof java.lang.Integer)
            fill[j].setText(((Integer)value).toString());
          else if (value instanceof java.lang.Long)
            fill[j].setText(((Long)value).toString());
          else if (value instanceof java.sql.Timestamp)
            fill[j].setText(((Timestamp)value).toString());
          fill[j].postActionEvent();
        }
      }
      else {
        for (int j = 0; j < listFill.size(); j++) {
          Object value = tm.getValueAt(jt.getSelectedRow(), j);
          TmplJTextField myField = (TmplJTextField)listFill.get(j);
          if (myField.getLink() instanceof pt.inescporto.template.elements.TplString)
            ((TplString)myField.getLink()).setValue((String)value);
          if (myField.getLink() instanceof pt.inescporto.template.elements.TplInt)
            ((TplInt)myField.getLink()).setValue((Integer)value);
          if (myField.getLink() instanceof pt.inescporto.template.elements.TplLong)
            ((TplLong)myField.getLink()).setValue((Long)value);
          if (myField.getLink() instanceof pt.inescporto.template.elements.TplFloat)
            ((TplFloat)myField.getLink()).setValue((Float)value);
          if (myField.getLink() instanceof pt.inescporto.template.elements.TplTimestamp)
            ((TplTimestamp)myField.getLink()).setValue((Timestamp)value);
        }
      }
      this.dispose();
    }
  }

  protected void cancelActionPerformed(ActionEvent e) {
    this.dispose();
  }

  /**
   *
   * @param links
   * @return
   */
  protected String getLinkConditionStmt(JComponent[] links) {
    String dinamicLinkCondition = "";

    if (links == null)
      return null;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField)
        dinamicLinkCondition += ((TmplJTextField)links[i]).getField() + " = ? ";
      dinamicLinkCondition += " AND ";
    }

    // trim string to a valid where statment
    dinamicLinkCondition = dinamicLinkCondition.substring(0, dinamicLinkCondition.length() - 5);

    return dinamicLinkCondition;
  }

  protected Vector getLinkConditionBinds(JComponent[] links) {
    Vector binds = new Vector();

    if (links == null)
      return null;

    // construct where statment and set binds
    for (int i = 0; i < links.length; i++) {
      if (links[i] instanceof pt.inescporto.template.client.design.TmplJTextField) {
        if (((TmplJTextField)links[i]).getLink().getValueAsObject() != null)
          binds.add(((TmplJTextField)links[i]).getLink());
        else
          binds.add(new TplString(((TmplJTextField)links[i]).getText()));
      }
    }

    return binds;
  }

  void showErrorMessage(String msg) {
    JOptionPane.showMessageDialog(this.getContentPane(),
                                  msg, TmplResourceSingleton.getString("error.dialog.header"),
                                  JOptionPane.ERROR_MESSAGE);
  }

  void showInformationMessage(String msg) {
    JOptionPane.showMessageDialog(this.getContentPane(),
                                  msg, TmplResourceSingleton.getString("info.dialog.header"),
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  int showOkCancelMessage(String title, String msg) {
    return JOptionPane.showConfirmDialog(this.getContentPane(),
                                         msg, title, JOptionPane.OK_CANCEL_OPTION);
  }
}
