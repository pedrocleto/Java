package pt.inescporto.siasoft.tools.cbuilder;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

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
public class CatalogBuilder extends JFrame implements ActionListener, CatalogBuilderInterface {
  private JFileChooser jfcBaseDir = new JFileChooser();
  private JButton jbtnBaseDir = new JButton("Directoria base dos documentos");
  private JLabel jlblBaseDir = new JLabel("Directoria base dos documentos");
  private JTextField jtfldBaseDir = new JTextField("pdf");
  private JLabel jlblCatalogNameDir = new JLabel("Nome do Calálogo");
  private JTextField jtfldCatalogNameDir = new JTextField("index");

  public CatalogBuilder() {
    super("[SIASoft] Ferramenta de Geração de Catálogos");

    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    catch (Exception exception) {
      exception.printStackTrace();
      System.exit(1);
    }

    try {
      initialize();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      System.exit(2);
    }

    validate();
    pack();

    // Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    setVisible(true);
  }

  private void initialize() throws Exception {
    jbtnBaseDir.setEnabled(false);
    jtfldBaseDir.setEditable(false);
    jtfldCatalogNameDir.setEditable(false);

    jbtnBaseDir.setActionCommand("FILE_CHOOSER");
    jbtnBaseDir.addActionListener(this);

    setLayout(new BorderLayout());

    JPanel jpnlInput = new JPanel();
    jpnlInput.setOpaque(false);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 150dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 5px");
    formLayout.setRowGroups(new int[][] { {2, 4} });
    CellConstraints cc = new CellConstraints();

    jpnlInput.setLayout(formLayout);

    jpnlInput.add(jlblBaseDir, cc.xy(2, 2));
    jpnlInput.add(jtfldBaseDir, cc.xy(4, 2));
    jpnlInput.add(jlblCatalogNameDir, cc.xy(2, 4));
    jpnlInput.add(jtfldCatalogNameDir, cc.xy(4, 4));

    add(jpnlInput, BorderLayout.NORTH);

   // CBPanel cbPanel = new CBPanel(this);
    //add(cbPanel, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("FILE_CHOOSER")) {
      int result;

      jfcBaseDir.setCurrentDirectory(new java.io.File("."));
      jfcBaseDir.setDialogTitle("Selecção de Directoria");
      jfcBaseDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      if (jfcBaseDir.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	//System.out.println("getCurrentDirectory(): " + jfcBaseDir.getCurrentDirectory());
	//System.out.println("getSelectedFile() : " + jfcBaseDir.getSelectedFile());
        jtfldBaseDir.setText(jfcBaseDir.getSelectedFile().getPath().replace(jfcBaseDir.getCurrentDirectory().getAbsolutePath() + "/", ""));
      }
      else {
	//System.out.println("No Selection ");
      }
    }
  }

  public String getBaseDirectory() {
    return jtfldBaseDir.getText();
  }

  public String getCatalogName() {
    return jtfldCatalogNameDir.getText();
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (InstantiationException ex) {
      ex.printStackTrace();
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }

    CatalogBuilder cn = new CatalogBuilder();
  }
}
