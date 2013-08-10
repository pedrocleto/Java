package pt.inescporto.siasoft.asq.client.rmi.forms;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class SearchRegulatoryPane extends JPanel {
  JPanel jpnlContent = new JPanel();
  TmplJLabel jlblName = new TmplJLabel();
  TmplJLabel jlblResume = new TmplJLabel();
  TmplJButton jbtnSearch = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/lookup.png")));
  TmplJButton jbtnMoreOptions = new TmplJButton();

  RegulatoryTreeForm frm = null;

  public TmplJTextField jtfldResume = new TmplJTextField();
  public TmplJTextField jtfldName = new TmplJTextField();

  public SearchRegulatoryPane(TmplJButton jbtnSearch, TmplJButton jbtnMoreOptions) {
    this.jbtnSearch = jbtnSearch;
    this.jbtnMoreOptions = jbtnMoreOptions;

    try {
      initialize();
    }
    catch (Exception ex) {
    }
  }

  private void initialize() throws Exception {
    jlblName.setText("Nome:");
    jlblResume.setText("Sumário:");
    jbtnMoreOptions.setText("Mais Opções");

    //jbtnSearch.addActionListener(this);

    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, right:pref, 4dlu, 150dlu:grow, 35dlu,4dlu, center:pref, 5px",
					   "5px, pref, 4dlu, pref,4dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    // formLayout.setRowGroups(new int[][] {{2, 4}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblName, cc.xy(2, 2));
    jpnlContent.add(jtfldName, cc.xyw(4, 2, 2));
    jpnlContent.add(jlblResume, cc.xy(2, 4));
    jpnlContent.add(jtfldResume, cc.xyw(4, 4, 2));
    jpnlContent.add(jbtnSearch, cc.xywh(7, 2, 1, 3, CellConstraints.FILL, CellConstraints.FILL));
    jpnlContent.add(jbtnMoreOptions, cc.xyw(5, 6, 4, CellConstraints.FILL, CellConstraints.FILL));

    add(jpnlContent, BorderLayout.CENTER);
  }

}
