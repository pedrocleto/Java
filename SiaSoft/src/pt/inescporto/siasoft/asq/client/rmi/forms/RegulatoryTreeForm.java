package pt.inescporto.siasoft.asq.client.rmi.forms;

import javax.swing.*;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJRadioButton;
import pt.inescporto.template.client.rmi.FW_InternalFrameBasic;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.TmplLookupButton;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class RegulatoryTreeForm extends FW_InternalFrameBasic implements ActionListener{
  JSplitPane jSplit = null;
  JSplitPane jSplitSearch = null;
  JSplitPane jSplitRegulatory = null;
  JPanel jpnlLeft = new JPanel(new BorderLayout());
  JPanel jpnlTheme = new JPanel();
  JPanel jpnlSearch= new JPanel(new BorderLayout());
  ButtonGroup bgTheme = new ButtonGroup();
  TmplJRadioButton jrbList = null;
  TmplJRadioButton jrbTheme = null;
  TmplJRadioButton jrbCron = null;
  TmplJRadioButton jrbAppl = null;
  TmplJButton jbtnSearch = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/pesquisar.png")));
  TmplJButton jbtnSearchFull = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/pesquisar.png")));
  TmplJButton jbtnMoreOptions = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/maisOpcoes.png")));
  TmplJButton jbtnLessOptions = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/menosOpcoes.png")));

  RegulatoryListPanel rlp = null;
  SearchRegulatoryPane srp = null;
  SearchRegulatoryFullPane sr=null;

  public RegulatoryTreeForm() {
    setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    setAccessPermitionIdentifier("TEST");

    addFWComponentListener(jrbList);
    addFWComponentListener(jrbTheme);
    addFWComponentListener(jrbCron);
    addFWComponentListener(jrbAppl);

    start();
    correctSelect();
  }

  public void correctSelect() {
    // determine type of starting selection
    if (jrbList.isVisible()) {
      jrbList.setSelected(true);
      jrbList.doClick();
    }
    else {
      if (jrbTheme.isVisible()) {
        jrbTheme.setSelected(true);
        jrbTheme.doClick();
      }
      else {
        if (jrbCron.isVisible()) {
          jrbCron.setSelected(true);
          jrbCron.doClick();
        }
        else {
          if (jrbAppl.isVisible()) {
            jrbAppl.setSelected(true);
            jrbAppl.doClick();
          }
        }
      }
    }
  }

  public void registerFilterListener(ActionListener al) {
    jrbList.addActionListener(al);
    jrbTheme.addActionListener(al);
    jrbCron.addActionListener(al);
    jrbAppl.addActionListener(al);
  }

  public RegulatoryListInterface getRegulatoryListListener() {
    return rlp;
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    TreeViewerPanel tvp = new TreeViewerPanel(this);
    RegulatoryPanelTree rpt = new RegulatoryPanelTree(tvp);
    rlp = new RegulatoryListPanel(rpt);
    tvp.setPopulateListener(rlp);
    jbtnSearch.addActionListener(this);
    jbtnSearch.setActionCommand("SEARCH");
    jbtnSearchFull.addActionListener(this);
    jbtnSearchFull.setActionCommand("SEARCHFULL");
    jbtnMoreOptions.addActionListener(this);
    jbtnMoreOptions.setActionCommand("MoreOptions");
    jbtnLessOptions.addActionListener(this);
    jbtnLessOptions.setActionCommand("LessOptions");

    srp = new SearchRegulatoryPane(jbtnSearch, jbtnMoreOptions);

    jSplitRegulatory = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rlp, rpt);
    jSplitRegulatory.setOneTouchExpandable(true);
    rpt.setSplitPane(jSplitRegulatory);

    jrbList = new TmplJRadioButton("Listagem", false);
    jrbList.setAccessPermissionID("REGTREE_LIST_BTN");
    jrbList.setActionCommand("LIST");
    jrbList.addActionListener(tvp);

    jrbTheme = new TmplJRadioButton("Temático", false);
    jrbTheme.setAccessPermissionID("REGTREE_THEME_BTN");
    jrbTheme.setActionCommand("THEME");
    jrbTheme.addActionListener(tvp);

    jrbCron = new TmplJRadioButton("Cronológico", false);
    jrbCron.setAccessPermissionID("REGTREE_CRON_BTN");
    jrbCron.setActionCommand("CRON");
    jrbCron.addActionListener(tvp);

    jrbAppl = new TmplJRadioButton("Aplicáveis", false);
    jrbAppl.setAccessPermissionID("REGTREE_APPL_BTN");
    jrbAppl.setActionCommand("APPL");
    jrbAppl.addActionListener(tvp);

    bgTheme.add(jrbList);
    bgTheme.add(jrbTheme);
    bgTheme.add(jrbCron);
    bgTheme.add(jrbAppl);

    jpnlTheme.add(jrbList);
    jpnlTheme.add(jrbTheme);
    jpnlTheme.add(jrbCron);
    jpnlTheme.add(jrbAppl);

    jpnlLeft.add(tvp, BorderLayout.CENTER);
    jpnlSearch.add(srp,BorderLayout.CENTER);

    jpnlLeft.add(jpnlTheme, BorderLayout.NORTH);
    jSplitSearch = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jpnlSearch,jpnlLeft );
    //jSplitSearch.setOneTouchExpandable(true);
    jSplitSearch.getLastDividerLocation();


    jSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jSplitSearch, jSplitRegulatory);
    add(jSplit, BorderLayout.CENTER);
    jSplit.resetToPreferredSizes();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("SEARCH")) {
      if (!srp.jtfldName.getText().equals("") || !srp.jtfldResume.getText().equals("")) {
	this.getRegulatoryListListener().findByCriterion(srp.jtfldName.getText(), srp.jtfldResume.getText());
      }
    }
    if (e.getActionCommand().equals("SEARCHFULL")) {
      getRegulatoryListListener().findByCriterionFull(sr.jtfldName.getText(), sr.jtfldResume.getText(),
	  sr.jdpckDataIni.getSelectedDate(true), sr.jdpckDataFim.getSelectedDate(true),
	  sr.jtfldScope.getText(), sr.jtfldSource.getText(), sr.jtfldTheme.getText(),
	  sr.jtfldTheme1.getText(), sr.jcheckEffective.isSelected(), sr.jcheckOther.isSelected(),
          sr.jcheckAplicability.isSelected(), sr.jcheckHasLegReq.isSelected());
    }
    if (e.getActionCommand().equals("MoreOptions")) {
      sr = new SearchRegulatoryFullPane(jbtnSearchFull, jbtnLessOptions);
      jpnlSearch.removeAll();
      jpnlSearch.add(sr, BorderLayout.CENTER);
      jSplitSearch.remove(jpnlSearch);
      jSplitSearch.add(jpnlSearch);
    }
    if (e.getActionCommand().equals("LessOptions")) {
      srp = new SearchRegulatoryPane(jbtnSearch, jbtnMoreOptions);
      jpnlSearch.removeAll();
      jpnlSearch.add(srp, BorderLayout.CENTER);
      jSplitSearch.remove(jpnlSearch);
      jSplitSearch.add(jpnlSearch);
    }
  }
}
