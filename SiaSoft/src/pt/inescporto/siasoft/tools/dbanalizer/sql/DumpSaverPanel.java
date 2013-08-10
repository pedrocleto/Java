package pt.inescporto.siasoft.tools.dbanalizer.sql;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import com.jgoodies.forms.layout.CellConstraints;

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
public class DumpSaverPanel extends JPanel implements ActionListener {
  JLabel jlblMajorVersion = new JLabel("Major version");
  JLabel jlblMinorVersion = new JLabel("Minor version");

  public JTextField jtfldSourceMajorVersion = new JTextField();
  public JTextField jtfldSourceMinorVersion = new JTextField();

  public JButton jbtnFileChooser = new JButton("Save to file ...");
  public JTextField jtfldFileChooser = new JTextField();
  JFileChooser jfcFile = new JFileChooser();

  public DumpSaverPanel() {
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
    jpnlSourceTag.add(jlblMajorVersion, cc.xy(2, 2));
    jpnlSourceTag.add(jtfldSourceMajorVersion, cc.xy(4, 2));
    jpnlSourceTag.add(jlblMinorVersion, cc.xy(6, 2));
    jpnlSourceTag.add(jtfldSourceMinorVersion, cc.xy(8, 2));

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
                                           "5px, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {2}
    });

    jpnlContent.add(jpnlSourceTag, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(jpnlFile, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.FILL));

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

  public String getFileName() {
    return jtfldFileChooser.getText();
  }
}
