package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JButton;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Font;

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
public class RestoreLoaderPanel extends JPanel implements ActionListener {
  JButton jbtnFileChooser = new JButton("Open file ...");
  JTextField jtfldFileChooser = new JTextField();
  JFileChooser jfcFile = new JFileChooser();

  public RestoreLoaderPanel() {
    super(new BorderLayout());
    try {
      initializeUI();
    }
    catch (Exception ex) {
    }
  }

  public void initializeUI() throws Exception {
    // handle UI components
    jbtnFileChooser.setFont(new Font("dialog", Font.PLAIN, 10));
    jtfldFileChooser.setFont(new Font("dialog", Font.PLAIN, 10));

    // handle panels and layout
    setOpaque(false);

    CellConstraints cc = new CellConstraints();

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

    jpnlContent.add(jpnlFile, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(jpnlContent, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent ev) {
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      jtfldFileChooser.setText(chooser.getSelectedFile().getName());
    }
  }

  public String getFileName() {
    return jtfldFileChooser.getText();
  }
}
