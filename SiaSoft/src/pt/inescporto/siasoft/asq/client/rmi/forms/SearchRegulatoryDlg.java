package pt.inescporto.siasoft.asq.client.rmi.forms;

import javax.swing.JFrame;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplLookupButton;
import javax.swing.ImageIcon;
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
public class SearchRegulatoryDlg extends JDialog implements ActionListener {
  JPanel jpnlContent = new JPanel();
  TmplJLabel jlblName = new TmplJLabel();
  public TmplJTextField jtfldName=  new TmplJTextField();
  TmplJLabel jlblResume = new TmplJLabel();
  public TmplJTextField jtfldResume=  new TmplJTextField();
  TmplJButton jbtnSearch = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/lookup.png")));

  public SearchRegulatoryDlg(JFrame frame) {
    super(frame, true);

    setTitle("Pesquisa");
    setLayout(new BorderLayout());

    try {
      initialize();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getPreferredSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

  private void initialize() throws Exception {
    jlblName.setText("Nome:");
    jlblResume.setText("Sumário:");

    jbtnSearch.addActionListener(this);

    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, right:pref, 4dlu, 150dlu:grow, 4dlu, center:pref, 5px",
                                           "5px, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblName, cc.xy(2, 2));
    jpnlContent.add(jtfldName, cc.xy(4, 2));
    jpnlContent.add(jlblResume, cc.xy(2, 4));
    jpnlContent.add(jtfldResume, cc.xy(4, 4));
    jpnlContent.add(jbtnSearch, cc.xywh(6,2,1,3,CellConstraints.FILL,CellConstraints.FILL));

    add(jpnlContent, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {

  }
}
