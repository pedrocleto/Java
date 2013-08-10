package pt.inescporto.siasoft.tools;

import javax.swing.*;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import pt.inescporto.siasoft.tools.dbanalizer.sql.SqlBuilderPanel;
import pt.inescporto.siasoft.tools.dbanalizer.io.AnalizarSaver;
import pt.inescporto.siasoft.tools.dbanalizer.sql.BackupSaverPanel;

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
public class BackupWizard extends JDialog implements ActionListener {
  private CardLayout cardL = new CardLayout();
  private JPanel pnlContent = new JPanel(cardL);
  private JLabel jlblTitle = new JLabel("Configure source database connection.");
  private JButton btnPrev = new JButton("<< Previous");
  private JButton btnNext = new JButton("Next >>");
  private OpenDSPanel pnlDSsource = new OpenDSPanel();
  private OpenDSPanel pnlDStarget = new OpenDSPanel();
  private JPanel pnlShowConnectSource = new JPanel(new BorderLayout());
  private JTextArea jtaMsgSource = new JTextArea();
  private JPanel pnlShowConnectTarget = new JPanel(new BorderLayout());
  private JTextArea jtaMsgTarget = new JTextArea();

  private Timer timer = null;

  private DBTreeBuilderPanel dbPanelSource = null;
  private DBTreeBuilderPanel dbPanelTarget = null;
  private DBStructComparePanel dbStructDiff = null;
  private SqlBuilderPanel sqlBuilder = null;
  private DBDataBuilderPanel dataBuilder = null;
  private BackupSaverPanel bsp = null;

  Connection dbConnSource = null;
  Connection dbConnTarget = null;
  int pos = 0;

  public BackupWizard(Frame owner, String title) {
    super(owner, title, true);

    try {
      initializeUI();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    // adjust dialog to center of screen
    Dimension screen = owner.getSize();
    int x = (owner.getX() + screen.width - (int)getPreferredSize().getWidth()) / 2;
    int y = (owner.getY() + screen.height - (int)getPreferredSize().getHeight()) / 2;
    setLocation(x, y);

    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void initializeUI() throws Exception {
    // initialize components
    btnPrev.setActionCommand("PREVIOUS");
    btnPrev.setFont(new Font("dialog", Font.PLAIN, 10));
    btnNext.setActionCommand("NEXT");
    btnNext.setFont(new Font("dialog", Font.PLAIN, 10));
    btnPrev.setEnabled(false);
    btnPrev.addActionListener(this);
    btnNext.addActionListener(this);

    jtaMsgSource.setEditable(false);

    setLayout(new BorderLayout());

    JPanel pnlAll = new JPanel(new BorderLayout());
    ImageIcon imgIcon = new ImageIcon();

    pnlContent.add(pnlDSsource, "OPEN_DS_SOURCE");

    pnlShowConnectSource.add(jtaMsgSource, BorderLayout.CENTER);
    pnlContent.add(pnlShowConnectSource, "DS_SOURCE_MSG");

    pnlContent.add(pnlDStarget, "OPEN_DS_TARGET");

    pnlShowConnectTarget.add(jtaMsgTarget, BorderLayout.CENTER);
    pnlContent.add(pnlShowConnectTarget, "DS_TARGET_MSG");

    JPanel pnlButtons = new JPanel();
    pnlButtons.add(btnPrev);
    pnlButtons.add(btnNext);
    pnlButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, pnlButtons.getForeground()));

    jlblTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, jlblTitle.getForeground()));

    // add title no NORTH
    pnlAll.add(jlblTitle, BorderLayout.NORTH);
    pnlAll.add(pnlContent, BorderLayout.CENTER);
    pnlAll.add(new JLabel(imgIcon), BorderLayout.WEST);
    pnlAll.add(pnlButtons, BorderLayout.SOUTH);

    add(pnlAll, BorderLayout.CENTER);
    pnlAll.setPreferredSize(new Dimension(500, 300));
    pnlAll.setMinimumSize(new Dimension(500, 300));
  }

  public void actionPerformed(ActionEvent e) {
    switch (pos) {
      case 0 :
	try {
          btnNext.setEnabled(false);
          btnPrev.setEnabled(true);
          Class.forName(pnlDSsource.getDriver());
	  dbConnSource = DriverManager.getConnection(pnlDSsource.getUrl(), pnlDSsource.getUser(), pnlDSsource.getPasswd());

	  cardL.show(pnlContent, "DS_SOURCE_MSG");
	  DatabaseMetaData dbMeta = dbConnSource.getMetaData();

          jtaMsgSource.setText(null);
	  jtaMsgSource.append("Connected to " + dbMeta.getDatabaseProductName() + " version " + dbMeta.getDatabaseProductVersion() + "\r\n");
	  jtaMsgSource.append("Using driver " + dbMeta.getDriverName() + " version " + dbMeta.getDriverVersion() + "\r\n");
	  jtaMsgSource.append("Catalog term is " + dbMeta.getCatalogTerm() + "\r\n");
          jtaMsgSource.append("Schema term is " + dbMeta.getSchemaTerm() + "\r\n");
          jtaMsgSource.append("Connection to datasource successfully!");
          btnNext.setEnabled(true);
          btnPrev.setEnabled(true);
	}
        catch (SQLException ex) {
          ex.printStackTrace();
          return;
        }
        catch (ClassNotFoundException ex) {
          ex.printStackTrace();
          return;
        }
        pos++;
        break;

      case 1 :
        jtaMsgSource.setText(null);
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
          pos++;
        }
        else {
          pos--;
	  try {
	    dbConnSource.close();
	  }
	  catch (SQLException ex1) {
            ex1.printStackTrace();
	  }
          dbConnSource = null;
          cardL.show(pnlContent, "OPEN_DS_SOURCE");
          btnPrev.setEnabled(false);
        }
        break;

      case 2 :
        if (e.getActionCommand().equals("NEXT")) {
          try {
            btnNext.setEnabled(false);
            btnPrev.setEnabled(true);
            Class.forName(pnlDStarget.getDriver());
            dbConnTarget = DriverManager.getConnection(pnlDStarget.getUrl(), pnlDStarget.getUser(), pnlDStarget.getPasswd());

            cardL.show(pnlContent, "DS_TARGET_MSG");
            DatabaseMetaData dbMeta = dbConnTarget.getMetaData();

            jtaMsgTarget.setText(null);
            jtaMsgTarget.append("Connected to " + dbMeta.getDatabaseProductName() + " version " + dbMeta.getDatabaseProductVersion() + "\r\n");
            jtaMsgTarget.append("Using driver " + dbMeta.getDriverName() + " version " + dbMeta.getDriverVersion() + "\r\n");
            jtaMsgTarget.append("Catalog term is " + dbMeta.getCatalogTerm() + "\r\n");
            jtaMsgTarget.append("Schema term is " + dbMeta.getSchemaTerm() + "\r\n");
            jtaMsgTarget.append("Connection to datasource successfully!");
            btnNext.setEnabled(true);
            btnPrev.setEnabled(true);
          }
          catch (SQLException ex) {
            ex.printStackTrace();
            return;
          }
          catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return;
          }
          pos++;
        }
        else {
          pos = 0;
          try {
            dbConnSource.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          cardL.show(pnlContent, "OPEN_DS_SOURCE");
        }
        break;

      case 3 :
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Reading table tree from source database.");
          dbPanelSource = new DBTreeBuilderPanel(dbConnSource, null, "public");
          pnlContent.add(dbPanelSource, "DB_BUILDER_SOURCE");
          cardL.show(pnlContent, "DB_BUILDER_SOURCE");
          pos++;

          // stop wizard prev and next buttons
          btnPrev.setEnabled(false);
          btnNext.setEnabled(false);
          //Create a timer.
          timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              if (dbPanelSource.isDone()) {
                timer.stop();
                btnPrev.setEnabled(true);
                btnNext.setEnabled(true);
              }
            }
          });
          timer.start();

          // start task
          dbPanelSource.actionPerformed(null);
        }
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

      case 4 :
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Reading table tree from target database.");
          dbPanelTarget = new DBTreeBuilderPanel(dbConnTarget, null, "public");
          pnlContent.add(dbPanelTarget, "DB_BUILDER_TARGET");
          cardL.show(pnlContent, "DB_BUILDER_TARGET");
          pos++;

          // stop wizard prev and next buttons
          btnPrev.setEnabled(false);
          btnNext.setEnabled(false);
          //Create a timer.
	  timer = new Timer(100, new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
	      if (dbPanelTarget.isDone()) {
		timer.stop();
                btnPrev.setEnabled(true);
                btnNext.setEnabled(true);
	      }
	    }
	  });
          timer.start();

          // start task
          dbPanelTarget.actionPerformed(null);
        }
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          pnlContent.remove(dbPanelSource);
          dbPanelSource = null;
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

      case 5 :
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Build structural difference.");
          dbStructDiff = new DBStructComparePanel(dbPanelSource.getTableList(), dbPanelTarget.getTableList());
          pnlContent.add(dbStructDiff, "DB_STRUCT_DIFF");
          cardL.show(pnlContent, "DB_STRUCT_DIFF");
          pos++;

          // stop wizard prev and next buttons
          btnPrev.setEnabled(false);
          btnNext.setEnabled(false);
          //Create a timer.
          timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              if (dbStructDiff.isDone()) {
                timer.stop();
                btnPrev.setEnabled(true);
                btnNext.setEnabled(true);
              }
            }
          });
          timer.start();

          // start task
          dbStructDiff.actionPerformed(null);
        }
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          pnlContent.remove(dbPanelSource);
          pnlContent.remove(dbPanelTarget);
          dbPanelSource = null;
          dbPanelTarget = null;
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

      case 6 :
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Build SQL statement difference.");
          sqlBuilder = new SqlBuilderPanel(dbPanelSource.getTableList(), dbStructDiff.getTableActions());
          pnlContent.add(sqlBuilder, "DB_SQL_DIFF");
          cardL.show(pnlContent, "DB_SQL_DIFF");
          pos++;

          // stop wizard prev and next buttons
          btnPrev.setEnabled(false);
          btnNext.setEnabled(false);
          //Create a timer.
          timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              if (sqlBuilder.isDone()) {
                timer.stop();
                btnPrev.setEnabled(true);
                btnNext.setEnabled(true);
              }
            }
          });
          timer.start();

          // start task
          sqlBuilder.actionPerformed(null);
        }
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          pnlContent.remove(dbPanelSource);
          pnlContent.remove(dbPanelTarget);
          dbPanelSource = null;
          dbPanelTarget = null;
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

      case 7 :
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Build data difference.");
          dataBuilder = new DBDataBuilderPanel(dbConnSource, dbConnTarget, dbPanelSource.getTableList(), dbPanelTarget.getTableList());
          pnlContent.add(dataBuilder, "DB_DATA_DIFF");
          cardL.show(pnlContent, "DB_DATA_DIFF");
          pos++;

          // stop wizard prev and next buttons
          btnPrev.setEnabled(false);
          btnNext.setEnabled(false);
          //Create a timer.
          timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              if (dataBuilder.isDone()) {
                timer.stop();
                btnPrev.setEnabled(true);
                btnNext.setEnabled(true);
              }
            }
          });
          timer.start();

          // start task
          dataBuilder.actionPerformed(null);
        }
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          pnlContent.remove(dbPanelSource);
          pnlContent.remove(dbPanelTarget);
          dbPanelSource = null;
          dbPanelTarget = null;
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

      case 8 :
        if (e.getActionCommand().equals("NEXT")) {
          jlblTitle.setText("Save backup file.");
	  bsp = new BackupSaverPanel();
          pnlContent.add(bsp, "DB_VERSION_TAG");
          cardL.show(pnlContent, "DB_VERSION_TAG");
          pos++;
	}
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          pnlContent.remove(dbPanelSource);
          pnlContent.remove(dbPanelTarget);
          dbPanelSource = null;
          dbPanelTarget = null;
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

      case 9 :
        if (e.getActionCommand().equals("NEXT")) {
          if (bsp.getFileName() != null && !bsp.getFileName().equals("")) {
            jlblTitle.setText("Done.");
	    AnalizarSaver analizerSaver = new AnalizarSaver(bsp.getFileName(),
		bsp.getSourceMajorVersion(), bsp.getSourceMinorVersion(),
		bsp.getTargetMajorVersion(), bsp.getTargetMinorVersion(),
		dbPanelTarget.getTableList(), sqlBuilder.getStmtList(), dataBuilder.getIncrementalDataList());
	    analizerSaver.save2file();
            btnPrev.setEnabled(false);
            bsp.jtfldSourceMajorVersion.setEnabled(false);
            bsp.jtfldSourceMinorVersion.setEnabled(false);
            bsp.jtfldTargetMajorVersion.setEnabled(false);
            bsp.jtfldTargetMinorVersion.setEnabled(false);
            btnNext.setText("Finish");
            pos++;
          }
          else {
            JOptionPane.showMessageDialog(this, "Must define a file name.", "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
        else {
          pos = 2;
          try {
            dbConnTarget.close();
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
          pnlContent.remove(dbPanelSource);
          pnlContent.remove(dbPanelTarget);
          dbPanelSource = null;
          dbPanelTarget = null;
          jlblTitle.setText("Configure target database connection.");
          cardL.show(pnlContent, "OPEN_DS_TARGET");
        }
        break;

        case 10:
          dispose();
          break;
    }
  }

  public void dispose() {
    try {
      if (dbConnSource != null && !dbConnSource.isClosed()) {
	dbConnSource.close();
      }
    }
    catch (SQLException ex) {
    }
    try {
      if (dbConnTarget != null && !dbConnTarget.isClosed()) {
	dbConnTarget.close();
      }
    }
    catch (SQLException ex1) {
    }

    super.dispose();
  }
}
