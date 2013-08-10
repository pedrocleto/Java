package pt.inescporto.siasoft.tools;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.*;
import java.util.Hashtable;
import pt.inescporto.siasoft.tools.dbanalizer.DBComparator;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.io.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.Cursor;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableActions;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataReader;
import java.util.Vector;
import pt.inescporto.siasoft.tools.dbanalizer.structural.BuildTableLevels;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataNode;
import pt.inescporto.siasoft.tools.dbanalizer.sql.SqlGenerator;
import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import pt.inescporto.siasoft.tools.dbanalizer.sql.query.TableDataWriter;
import pt.inescporto.siasoft.tools.dbanalizer.structural.StructureWriter;
import pt.inescporto.siasoft.tools.dbanalizer.io.AnalizarSaver;
import pt.inescporto.siasoft.tools.dbanalizer.io.AnalizerLoader;

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
public class AnalizerGUI extends JFrame implements ActionListener, MessagePoster, AnalizerEventListener {
  JMenuBar jmbMenu = new JMenuBar();
  JToolBar jtbTools = new JToolBar();

  JTree jtreeSourceStructure = new JTree(new DefaultMutableTreeNode());
  JTree jtreeDestinyStructure = new JTree(new DefaultMutableTreeNode());
  JTextPane jtpMessages = new JTextPane();

  DatabaseSpecs dsSource = new DatabaseSpecs();
  DatabaseSpecs dsDestin = new DatabaseSpecs();

  Hashtable<String, TableNode> sourceTableTree = null;
  Hashtable<String, TableNode> destinyTableTree = null;

  Hashtable<String, TableActions> tableActions = null;

  Vector<StatementNode> diffStmtList = null;

  Hashtable<String, TableDataNode > dbIncrementalData = null;

  AnalizerLoader analizerLoader = null;

  public AnalizerGUI() {
    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      initialize();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void initialize() throws Exception {
    JFrame.setDefaultLookAndFeelDecorated(true);

    setLayout(new BorderLayout());
    setSize(new Dimension(1024, 768));
    setTitle("Database Analizer Tool");

    jtpMessages.setEditable(false);
    jtpMessages.setEnabled(true);
    jtpMessages.setFont(new Font("Dialog", Font.PLAIN, 10));
    jtpMessages.setAutoscrolls(true);

    buildMenu();
    buildToolBar();

    jtreeSourceStructure.setRootVisible(false);
    jtreeSourceStructure.setFont(new Font("dialog", Font.PLAIN, 10));

    jtreeDestinyStructure.setRootVisible(false);
    jtreeDestinyStructure.setFont(new Font("dialog", Font.PLAIN, 10));

    JSplitPane jsplitTables = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(jtreeSourceStructure), new JScrollPane(jtreeDestinyStructure));
    jsplitTables.setDividerLocation(480);

    BorderLayout bl = new BorderLayout();
    JPanel jpnlMessages = new JPanel(bl);
    JLabel jlblMessages = new JLabel("Messages");
    jlblMessages.setFont(new Font("dialog", Font.PLAIN, 10));
    jpnlMessages.add(jlblMessages, BorderLayout.NORTH);
    jpnlMessages.add(new JScrollPane(jtpMessages), BorderLayout.CENTER);

    JSplitPane jsplitMessages = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsplitTables, jpnlMessages);
    jsplitMessages.setDividerLocation(568);

    add(jtbTools, BorderLayout.NORTH);
    add(jsplitMessages, BorderLayout.CENTER);
  }

  private void buildMenu() {
    JMenu jmenuOpen = new JMenu("Open");
    jmenuOpen.setFont(new Font("Dialog", Font.PLAIN, 10));

    JMenuItem jmiSource = new JMenuItem("Define source");
    jmiSource.setFont(new Font("Dialog", Font.PLAIN, 10));
    jmiSource.setActionCommand("OpenSource");
    jmiSource.addActionListener(this);

    JMenuItem jmiDestiny = new JMenuItem("Define destiny");
    jmiDestiny.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiDestiny.setActionCommand("OpenDestiny");
    jmiDestiny.addActionListener(this);

    JMenuItem jmiSaveSource = new JMenuItem("Save Source");
    jmiSaveSource.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiSaveSource.setActionCommand("SaveSource");
    jmiSaveSource.addActionListener(this);

    JMenuItem jmiOpenSource = new JMenuItem("Open Source");
    jmiOpenSource.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiOpenSource.setActionCommand("OpenSourceFile");
    jmiOpenSource.addActionListener(this);

    JMenuItem jmiSaveDestiny = new JMenuItem("Save Destiny");
    jmiSaveDestiny.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiSaveDestiny.setActionCommand("SaveDestiny");
    jmiSaveDestiny.addActionListener(this);

    JMenuItem jmiOpenDestiny = new JMenuItem("Open Destiny");
    jmiOpenDestiny.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiOpenDestiny.setActionCommand("OpenDestinyFile");
    jmiOpenDestiny.addActionListener(this);

    JMenuItem jmiCompare = new JMenuItem("Compare DB's");
    jmiCompare.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiCompare.setActionCommand("COMPARE");
    jmiCompare.addActionListener(this);

    JMenuItem jmiStructuralDiff = new JMenuItem("Structural diff");
    jmiStructuralDiff.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiStructuralDiff.setActionCommand("SDIFF");
    jmiStructuralDiff.addActionListener(this);

    JMenuItem jmiDataDiff = new JMenuItem("Data diff");
    jmiDataDiff.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiDataDiff.setActionCommand("DDIFF");
    jmiDataDiff.addActionListener(this);

    JMenuItem jmiLoadDiff = new JMenuItem("Load diff");
    jmiLoadDiff.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiLoadDiff.setActionCommand("LOADDIFF");
    jmiLoadDiff.addActionListener(this);

    JMenuItem jmiSaveDiff = new JMenuItem("Save diff");
    jmiSaveDiff.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiSaveDiff.setActionCommand("SAVEDIFF");
    jmiSaveDiff.addActionListener(this);

    JMenuItem jmiStructDiffApp = new JMenuItem("Aplly Structural diff");
    jmiStructDiffApp.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiStructDiffApp.setActionCommand("APPSDIFF");
    jmiStructDiffApp.addActionListener(this);

    JMenuItem jmiDataDiffApp = new JMenuItem("Aplly Data diff");
    jmiDataDiffApp.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiDataDiffApp.setActionCommand("APPDDIFF");
    jmiDataDiffApp.addActionListener(this);

    JMenuItem jmiExit = new JMenuItem("Exit");
    jmiExit.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiExit.setActionCommand("EXIT");
    jmiExit.addActionListener(this);

    jmenuOpen.add(jmiSource);
    jmenuOpen.add(jmiDestiny);
    jmenuOpen.addSeparator();
    jmenuOpen.add(jmiOpenSource);
    jmenuOpen.add(jmiSaveSource);
    jmenuOpen.addSeparator();
    jmenuOpen.add(jmiOpenDestiny);
    jmenuOpen.add(jmiSaveDestiny);
    jmenuOpen.addSeparator();
    jmenuOpen.add(jmiCompare);
    jmenuOpen.add(jmiStructuralDiff);
    jmenuOpen.add(jmiDataDiff);
    jmenuOpen.addSeparator();
    jmenuOpen.add(jmiLoadDiff);
    jmenuOpen.add(jmiSaveDiff);
    jmenuOpen.addSeparator();
    jmenuOpen.add(jmiStructDiffApp);
    jmenuOpen.add(jmiDataDiffApp);
    jmenuOpen.addSeparator();
    jmenuOpen.add(jmiExit);

    jmbMenu.add(jmenuOpen);

    JMenu jmenuWizard = new JMenu("Wizard's");
    jmenuWizard.setFont(new Font("Dialog", Font.PLAIN, 10));

    JMenuItem jmiStarterDatabaseDumpWizard = new JMenuItem("Starter database dump");
    jmiStarterDatabaseDumpWizard.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiStarterDatabaseDumpWizard.setActionCommand("SDBD_WIZARD");
    jmiStarterDatabaseDumpWizard.addActionListener(this);

    JMenuItem jmiIncrementalDatabaseDumpWizard = new JMenuItem("Incremental database dump");
    jmiIncrementalDatabaseDumpWizard.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiIncrementalDatabaseDumpWizard.setActionCommand("IDBD_WIZARD");
    jmiIncrementalDatabaseDumpWizard.addActionListener(this);

    JMenuItem jmiWizardBackup = new JMenuItem("Backup");
    jmiWizardBackup.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiWizardBackup.setActionCommand("BACKUP_WIZARD");
    jmiWizardBackup.addActionListener(this);

    JMenuItem jmiWizardRestore = new JMenuItem("Restore dump");
    jmiWizardRestore.setFont(new Font("Dialog", Font.PLAIN, 10));;
    jmiWizardRestore.setActionCommand("RESTORE_WIZARD");
    jmiWizardRestore.addActionListener(this);

    jmenuWizard.add(jmiStarterDatabaseDumpWizard);
    jmenuWizard.add(jmiIncrementalDatabaseDumpWizard);
    jmenuWizard.addSeparator();
    jmenuWizard.add(jmiWizardBackup);
    jmenuWizard.add(jmiWizardRestore);

    jmbMenu.add(jmenuWizard);

    setJMenuBar(jmbMenu);
  }

  private void buildToolBar() {

  }

  public void setSourceStructure(Hashtable<String, TableNode> tableStructure) {
    sourceTableTree = tableStructure;
    DatabaseTableTree tt = new DatabaseTableTree(sourceTableTree);
    ((DefaultTreeModel)jtreeSourceStructure.getModel()).setRoot(tt.getTree());
    jtreeSourceStructure.invalidate();
    jtreeSourceStructure.repaint();
  }

  public void setTargetStructure(Hashtable<String, TableNode> tableStructure) {
    destinyTableTree = tableStructure;
    DatabaseTableTree tt = new DatabaseTableTree(destinyTableTree);
    ((DefaultTreeModel)jtreeDestinyStructure.getModel()).setRoot(tt.getTree());
    jtreeDestinyStructure.invalidate();
    jtreeDestinyStructure.repaint();
  }

  public void setTableActions(Hashtable<String, TableActions> tableActions) {
    this.tableActions = tableActions;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("OpenSource")) {
      OpenDialogDS openDS = new OpenDialogDS(this, "Open Datasource");
      openDS.pack();
      openDS.setVisible(true);

      dsSource.setDbConnection(connectSource(openDS.getDriver(), openDS.getUrl(), openDS.getUser(), openDS.getPasswd()));

      if (dsSource.getDbConnection() == null)
        return;

      dsSource.setDriver(openDS.getDriver());
      dsSource.setUrl(openDS.getUrl());
      dsSource.setUser(openDS.getUser());
      dsSource.setPasswd(openDS.getPasswd());
      dsSource.setCatalog("SIASoft");
      dsSource.setSchema("public");

      if (JOptionPane.showConfirmDialog(this, "Read table structure ?", "Open target Datasource", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
	DBStructureReader tblReader = new DBStructureReader(this, this, this, dsSource.getDbConnection(), dsSource.getCatalog(), dsSource.getSchema(), DBStructureReader.SOURCE);
	tblReader.start();
      }
    }
    if (e.getActionCommand().equals("OpenDestiny")) {
      OpenDialogDS openDS = new OpenDialogDS(this, "Open Datasource");
      openDS.pack();
      openDS.setVisible(true);

      dsDestin.setDbConnection(connectSource(openDS.getDriver(), openDS.getUrl(), openDS.getUser(), openDS.getPasswd()));

      dsDestin.setDriver(openDS.getDriver());
      dsDestin.setUrl(openDS.getUrl());
      dsDestin.setUser(openDS.getUser());
      dsDestin.setPasswd(openDS.getPasswd());
      dsDestin.setCatalog("SIASoft");
      dsDestin.setSchema("public");

      if (JOptionPane.showConfirmDialog(this, "Read table structure ?", "Open target Datasource", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
	DBStructureReader tblReader = new DBStructureReader(this, this, this, dsDestin.getDbConnection(), dsDestin.getCatalog(), dsDestin.getSchema(), DBStructureReader.DESTINY);
        tblReader.start();
      }
    }
    if (e.getActionCommand().equals("COMPARE")) {
      if (sourceTableTree != null && destinyTableTree != null) {
	DBComparator dbComparator = new DBComparator(this, this, sourceTableTree, destinyTableTree);
        dbComparator.start();
      }
      else
        JOptionPane.showMessageDialog(this, "Source and Target table structure must be presente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    if (e.getActionCommand().equals("SaveSource")) {
      try {
        if (sourceTableTree != null) {
	  FileOutputStream fos = new FileOutputStream("source.ser");
	  ObjectOutputStream oos = new ObjectOutputStream(fos);
	  oos.writeObject(sourceTableTree);
	  oos.close();
	}
        else
          JOptionPane.showMessageDialog(this, "Source table structure must be presente.", "Error", JOptionPane.ERROR_MESSAGE);
      }
      catch (FileNotFoundException ex1) {
        ex1.printStackTrace();
      }
      catch (IOException ex1) {
        ex1.printStackTrace();
      }
    }
    if (e.getActionCommand().equals("OpenSourceFile")) {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      try {
	FileInputStream fis = new FileInputStream("source.ser");
	ObjectInputStream ois = new ObjectInputStream(fis);
	sourceTableTree = (Hashtable<String, TableNode>)ois.readObject();
	ois.close();
        DatabaseTableTree tt = new DatabaseTableTree(sourceTableTree);
        ((DefaultTreeModel)jtreeSourceStructure.getModel()).setRoot(tt.getTree());
        jtreeSourceStructure.invalidate();
        jtreeSourceStructure.repaint();
      }
      catch (ClassNotFoundException ex2) {
        ex2.printStackTrace();
      }
      catch (FileNotFoundException ex2) {
        ex2.printStackTrace();
      }
      catch (IOException ex2) {
        ex2.printStackTrace();
      }
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    if (e.getActionCommand().equals("SaveDestiny")) {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      try {
        if (destinyTableTree != null) {
          FileOutputStream fos = new FileOutputStream("destiny.ser");
          ObjectOutputStream oos = new ObjectOutputStream(fos);
          oos.writeObject(destinyTableTree);
          oos.close();
        }
        else
          JOptionPane.showMessageDialog(this, "Target table structure must be presente.", "Error", JOptionPane.ERROR_MESSAGE);
      }
      catch (FileNotFoundException ex1) {
        ex1.printStackTrace();
      }
      catch (IOException ex1) {
        ex1.printStackTrace();
      }
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    if (e.getActionCommand().equals("OpenDestinyFile")) {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      try {
        FileInputStream fis = new FileInputStream("destiny.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        destinyTableTree = (Hashtable<String, TableNode>)ois.readObject();
        ois.close();
        DatabaseTableTree tt = new DatabaseTableTree(destinyTableTree);
        ((DefaultTreeModel)jtreeDestinyStructure.getModel()).setRoot(tt.getTree());
        jtreeDestinyStructure.invalidate();
        jtreeDestinyStructure.repaint();
      }
      catch (ClassNotFoundException ex2) {
        ex2.printStackTrace();
      }
      catch (FileNotFoundException ex2) {
        ex2.printStackTrace();
      }
      catch (IOException ex2) {
        ex2.printStackTrace();
      }
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    if (e.getActionCommand().equals("SDIFF")) {
      if (sourceTableTree == null) {
        JOptionPane.showMessageDialog(this, "Source table structure must be presente.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (destinyTableTree == null) {
        JOptionPane.showMessageDialog(this, "Destiny table structure must be presente.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (tableActions == null) {
        JOptionPane.showMessageDialog(this, "Must compare DB's first.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      setCursor(new Cursor(Cursor.WAIT_CURSOR));

      try {
        SqlGenerator sqlGen = new SqlGenerator(sourceTableTree, tableActions);
        postSeparator();
        diffStmtList = sqlGen.generateSQL(this);
        postSeparator();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      finally {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }
    if (e.getActionCommand().equals("DDIFF")) {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));

      try {
        // get table order sequence
        BuildTableLevels btl = new BuildTableLevels(null);
        Vector<TableNode> tblOrder = btl.build(sourceTableTree);

        dbIncrementalData = new Hashtable<String,TableDataNode>();
        for (TableNode table : tblOrder) {
          TableDataNode data = TableDataReader.loadData(dsSource.getDbConnection(), dsDestin.getDbConnection(), table, destinyTableTree.containsKey(table.getTableName()) ? destinyTableTree.get(table.getTableName()) : null);
          if (data != null && data.isSomething2do())
            dbIncrementalData.put(table.getTableName(), data);
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      finally {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }
    if (e.getActionCommand().equals("APPSDIFF")) {
      StructureWriter.applyStructDiff(this, dsDestin.getDbConnection(), diffStmtList);
    }
    if (e.getActionCommand().equals("APPDDIFF")) {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));

      try {
        // get table order sequence
        BuildTableLevels btl = new BuildTableLevels(null);
        Vector<TableNode> tblOrder = btl.build(destinyTableTree);

        for (TableNode table : tblOrder) {
          if (dbIncrementalData.containsKey(table.getTableName())) {
            TableDataWriter.saveData(this, dsDestin.getDbConnection(), destinyTableTree.get(table.getTableName()), dbIncrementalData.get(table.getTableName()));
          }
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      finally {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }
    if (e.getActionCommand().equals("LOADDIFF")) {
      AnalizerLoader analizerLoader = new AnalizerLoader("teste.aet");
      analizerLoader.loadDiffFile(this);
      diffStmtList = analizerLoader.getDiffStmtList();
      dbIncrementalData = analizerLoader.getDbIncrementalData();
      setTargetStructure(analizerLoader.getTableTree());
    }
    if (e.getActionCommand().equals("SAVEDIFF")) {
      AnalizarSaver analizerSaver = new AnalizarSaver("teste.aet",
	  "0", "1",
	  "0", "0",
	  destinyTableTree, diffStmtList, dbIncrementalData);
      analizerSaver.save2file();
    }
    if (e.getActionCommand().equals("SDBD_WIZARD")) {
      starterDatabaseDumpWizard();
    }
    if (e.getActionCommand().equals("IDBD_WIZARD")) {
      incrementalDatabaseDumpWizard();
    }
    if (e.getActionCommand().equals("BACKUP_WIZARD")) {
      backupWizard();
    }
    if (e.getActionCommand().equals("RESTORE_WIZARD")) {
      restoreWizard();
    }
    if (e.getActionCommand().equals("EXIT")) {
      dispose();
    }
  }

  public void postMessage(final String msg) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        jtpMessages.setText(jtpMessages.getText() + msg + "\r\n");
        jtpMessages.repaint();
      }
    });
  }

  public void postMessageNL(final String msg) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        jtpMessages.setText(jtpMessages.getText() + msg);
        jtpMessages.repaint();
      }
    });
  }

  public void postSeparator() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        jtpMessages.setText(jtpMessages.getText() + "<--------------------------------------------------------------------------->\r\n");
        jtpMessages.repaint();
      }
    });
  }

  private Connection connectSource(String driver, String url, String user, String passwd) {
    postMessageNL("Connecting...");
    try {
      Class.forName(driver);
      Connection dbConnection = DriverManager.getConnection(url, user, passwd);

      postMessage("Done!");
      DatabaseMetaData dbMeta = dbConnection.getMetaData();

      postMessage("Connected to " + dbMeta.getDatabaseProductName() + " version " + dbMeta.getDatabaseProductVersion());
      postMessage("Using driver " + dbMeta.getDriverName() + " version " + dbMeta.getDriverVersion());
      postMessage("Catalog term is " + dbMeta.getCatalogTerm());
      postMessage("Schema term is " + dbMeta.getSchemaTerm());

      return dbConnection;
    }
    catch (SQLException ex) {
      postMessage(ex.getMessage());
      return null;
    }
    catch (ClassNotFoundException ex) {
      postMessage(ex.getMessage());
      return null;
    }
    finally {
      postSeparator();
    }
  }

  private void starterDatabaseDumpWizard() {
    final AnalizerGUI ag = this;
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        StarterDatabaseDumpWizard sdbd = new StarterDatabaseDumpWizard(ag, "Starter Database Dump Wizard");
        sdbd.pack();
        sdbd.setVisible(true);
      }
    });
  }

  private void incrementalDatabaseDumpWizard() {
    final AnalizerGUI ag = this;
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        IncrementalDatabaseDumpWizard idbd = new IncrementalDatabaseDumpWizard(ag, "Incremental Database Dump Wizard");
        idbd.pack();
        idbd.setVisible(true);
      }
    });
  }

  private void backupWizard() {
    final AnalizerGUI ag = this;
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        BackupWizard bw = new BackupWizard(ag, "Backup Wizard");
        bw.pack();
        bw.setVisible(true);
      }
    });
  }

  private void restoreWizard() {
    final AnalizerGUI ag = this;
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        RestoreWizard rw = new RestoreWizard(ag, "Restore Wizard");
        rw.pack();
        rw.setVisible(true);
      }
    });
  }
}
