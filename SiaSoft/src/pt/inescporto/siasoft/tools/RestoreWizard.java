package pt.inescporto.siasoft.tools;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Dimension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import pt.inescporto.siasoft.tools.dbanalizer.sql.RestoreLoaderPanel;
import pt.inescporto.siasoft.tools.dbanalizer.io.BackupLoaderPanel;
import pt.inescporto.siasoft.tools.dbanalizer.structural.StructDiffWriterPanel;
import pt.inescporto.siasoft.tools.dbanalizer.structural.DataDiffWriterPanel;

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
public class RestoreWizard extends JDialog implements ActionListener {
  private CardLayout cardL = new CardLayout();
  private JPanel pnlContent = new JPanel(cardL);
  private JLabel jlblTitle = new JLabel("Configure source database connection.");
  private JButton btnPrev = new JButton("<< Previous");
  private JButton btnNext = new JButton("Next >>");
  private OpenDSPanel pnlDStarget = new OpenDSPanel();
  private JPanel pnlShowConnectTarget = new JPanel(new BorderLayout());
  private JTextArea jtaMsgTarget = new JTextArea();

  private RestoreLoaderPanel rlp = null;
  private BackupLoaderPanel backupDiffLoader = null;
  private StructDiffWriterPanel structDiffWriter = null;
  private DBTreeBuilderPanel dbPanelTarget = null;

  Connection dbConnTarget = null;
  int pos = 0;

  public RestoreWizard(Frame owner, String title) {
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

    jtaMsgTarget.setEditable(false);

    setLayout(new BorderLayout());

    JPanel pnlAll = new JPanel(new BorderLayout());
    ImageIcon imgIcon = new ImageIcon();

    pnlContent.add(pnlDStarget, "OPEN_DS_TARGET");

    pnlShowConnectTarget.add(jtaMsgTarget, BorderLayout.CENTER);
    pnlContent.add(pnlShowConnectTarget, "DS_TARGET_MSG");

    rlp = new RestoreLoaderPanel();
    pnlContent.add(rlp, "DB_OPEN_FILE");

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
      case 0:
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
	break;

      case 1:
	if (e.getActionCommand().equals("NEXT")) {
	  cardL.show(pnlContent, "DB_OPEN_FILE");
	  pos++;
	}
	else {

	}
	break;

      case 2:
        if (e.getActionCommand().equals("NEXT")) {
          backupDiffLoader = new BackupLoaderPanel(rlp.getFileName());
          pnlContent.add(backupDiffLoader, "DB_BACKUP_LOADER");
          cardL.show(pnlContent, "DB_BACKUP_LOADER");
          pos++;
          backupDiffLoader.actionPerformed(null);
        }
        else {

        }
        break;


      case 3:
        if (e.getActionCommand().equals("NEXT")) {
          structDiffWriter = new StructDiffWriterPanel(dbConnTarget, backupDiffLoader.getDiffStmtList());
          pnlContent.add(structDiffWriter, "DB_STRUCT_DIFF");
          cardL.show(pnlContent, "DB_STRUCT_DIFF");
          pos++;
          structDiffWriter.actionPerformed(null);
        }
        else {

        }
        break;

      case 4 :
        if (e.getActionCommand().equals("NEXT")) {
          dbPanelTarget = new DBTreeBuilderPanel(dbConnTarget, null, "public");
          pnlContent.add(dbPanelTarget, "DB_BUILDER_TARGET");
          cardL.show(pnlContent, "DB_BUILDER_TARGET");
          pos++;
          dbPanelTarget.actionPerformed(null);
        }
        else {

        }
        break;

      case 5 :
        if (e.getActionCommand().equals("NEXT")) {
          DataDiffWriterPanel dataDiffWriter = new DataDiffWriterPanel(dbConnTarget,
              dbPanelTarget.getTableList(), backupDiffLoader.getDbIncrementalData());
          pnlContent.add(dataDiffWriter, "DB_DATA_DIFF");
          cardL.show(pnlContent, "DB_DATA_DIFF");
          pos++;
          dataDiffWriter.actionPerformed(null);
        }
        else {

        }
        dbPanelTarget.getTableList();
        break;
    }
  }
}
