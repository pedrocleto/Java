package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.awt.*;
import javax.swing.*;

import com.jgoodies.forms.layout.*;
import java.io.FileFilter;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
public class BackupSaverPanel extends JPanel implements ActionListener {
  private JLabel jlblSourceMajorVersion = new JLabel("Major version");
  private JLabel jlblSourceMinorVersion = new JLabel("Minor version");
  private JLabel jlblTargetMajorVersion = new JLabel("Major version");
  private JLabel jlblTargetMinorVersion = new JLabel("Minor version");

  public JTextField jtfldSourceMajorVersion = new JTextField();
  public JTextField jtfldSourceMinorVersion = new JTextField();
  public JTextField jtfldTargetMajorVersion = new JTextField();
  public JTextField jtfldTargetMinorVersion = new JTextField();

  JButton jbtnFileChooser = new JButton("Save to file ...");
  JTextField jtfldFileChooser = new JTextField();
  JFileChooser jfcFile = new JFileChooser();

  public BackupSaverPanel() {
    super(new BorderLayout());
    try {
      initializeUI();
    }
    catch (Exception ex) {
    }
  }

  public void initializeUI() throws Exception {
    setOpaque(false);

    CellConstraints cc = new CellConstraints();

    // source database version
    JPanel jpnlSourceTag = new JPanel();
    jpnlSourceTag.setBorder(BorderFactory.createTitledBorder("Source Database"));
    jpnlSourceTag.setOpaque(false);
    FormLayout flSource = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu:grow, left:pref, 4dlu, 70dlu, 5px",
					 "5px, pref, 5px");
    jpnlSourceTag.setLayout(flSource);
    jpnlSourceTag.add(jlblSourceMajorVersion, cc.xy(2, 2));
    jpnlSourceTag.add(jtfldSourceMajorVersion, cc.xy(4, 2));
    jpnlSourceTag.add(jlblSourceMinorVersion, cc.xy(6, 2));
    jpnlSourceTag.add(jtfldSourceMinorVersion, cc.xy(8, 2));

    // target database version
    JPanel jpnlTargetTag = new JPanel();
    jpnlTargetTag.setBorder(BorderFactory.createTitledBorder("Target Database"));
    jpnlTargetTag.setOpaque(false);
    FormLayout flTarget = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu:grow, left:pref, 4dlu, 70dlu, 5px",
					 "5px, pref, 5px");
    jpnlTargetTag.setLayout(flTarget);
    jpnlTargetTag.add(jlblTargetMajorVersion, cc.xy(2, 2));
    jpnlTargetTag.add(jtfldTargetMajorVersion, cc.xy(4, 2));
    jpnlTargetTag.add(jlblTargetMinorVersion, cc.xy(6, 2));
    jpnlTargetTag.add(jtfldTargetMinorVersion, cc.xy(8, 2));

    // save to selected file
    jbtnFileChooser.addActionListener(this);
    JPanel jpnlFile = new JPanel();
    jpnlFile.setBorder(BorderFactory.createTitledBorder("File Name"));
    jpnlFile.setOpaque(false);
    FormLayout flFile = new FormLayout("5px, left:pref, 4dlu, 70dlu:grow, 5px",
				       "5px, pref, 5px");
    jpnlFile.setLayout(flFile);
    jpnlFile.add(jbtnFileChooser, cc.xy(2, 2));
    jpnlFile.add(jtfldFileChooser, cc.xy(4, 2));

    // lets group all
    JPanel jpnlContent = new JPanel();
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, center:pref, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {2, 4}
    });

    jpnlContent.add(jpnlSourceTag, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(jpnlTargetTag, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(jpnlFile, cc.xy(2, 6, CellConstraints.FILL, CellConstraints.FILL));

    add(jpnlContent, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent ev) {
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      jtfldFileChooser.setText(chooser.getSelectedFile().getName());
    }
  }

  public String getSourceMajorVersion() {
    return jtfldSourceMajorVersion.getText();
  }

  public String getSourceMinorVersion() {
    return jtfldSourceMinorVersion.getText();
  }

  public String getTargetMajorVersion() {
    return jtfldTargetMajorVersion.getText();
  }

  public String getTargetMinorVersion() {
    return jtfldTargetMinorVersion.getText();
  }

  public String getFileName() {
    return jtfldFileChooser.getText();
  }
}

class ExampleFileFilter implements FileFilter {
  public boolean accept(File pathname) {
    return pathname.getName().endsWith(".aet");
  }
};
