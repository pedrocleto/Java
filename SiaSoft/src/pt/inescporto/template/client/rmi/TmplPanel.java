package pt.inescporto.template.client.rmi;

import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import pt.inescporto.template.client.design.TmplJButtonFind;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.design.TmplJButtonDelete;
import pt.inescporto.template.client.design.TmplJButtonExit;
import pt.inescporto.template.client.design.TmplJButtonInsert;
import pt.inescporto.template.client.design.TmplJButtonNext;
import java.awt.FlowLayout;
import pt.inescporto.template.client.design.TmplJButtonFindResult;
import pt.inescporto.template.client.design.TmplJButtonPrev;
import pt.inescporto.template.client.design.TmplJButtonAll;

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
public class TmplPanel extends TmplNavPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  protected JPanel jpnlMenuNav = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  protected TmplJButtonNext jbtnNext = new TmplJButtonNext();
  protected TmplJButtonDelete jbtnDelete = new TmplJButtonDelete();
  protected TmplJButtonAll jbtnAll = new TmplJButtonAll();
  protected TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  protected TmplJButtonPrev jbtnPrevious = new TmplJButtonPrev();
  protected TmplJButtonFind jbtnFind = new TmplJButtonFind();
  protected TmplJButtonInsert jbtnInsert = new TmplJButtonInsert();
  protected TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();
  protected TmplJButtonSave jbtnSave = new TmplJButtonSave();
  protected TmplJButtonFindResult jbtnFindRes = new TmplJButtonFindResult();

  public TmplPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    // register this listeners
    addTemplateListener(jbtnInsert);
    addTemplateListener(jbtnUpdate);
    addTemplateListener(jbtnDelete);
    addTemplateListener(jbtnSave);
    addTemplateListener(jbtnCancel);
    addTemplateListener(jbtnPrevious);
    addTemplateListener(jbtnNext);
    addTemplateListener(jbtnAll);
    addTemplateListener(jbtnFind);
    addTemplateListener(jbtnFindRes);
  }

  public void init() {
    super.init();
  }

  public void start() {
    super.start();
  }

  private void jbInit() throws Exception {
    setLayout(borderLayout1);

    // add action listeners

    // register this listeners
    jpnlMenuNav.setLayout(flowLayout1);
    add(jpnlMenuNav, BorderLayout.NORTH);
    jpnlMenuNav.add(jbtnInsert, null);
    jpnlMenuNav.add(jbtnUpdate, null);
    jpnlMenuNav.add(jbtnDelete, null);
    jpnlMenuNav.add(jbtnSave, null);
    jpnlMenuNav.add(jbtnCancel, null);
    jpnlMenuNav.add(jbtnPrevious, null);
    jpnlMenuNav.add(jbtnNext, null);
    jpnlMenuNav.add(jbtnFind, null);
    jpnlMenuNav.add(jbtnFindRes, null);
    jpnlMenuNav.add(jbtnAll, null);

    // add action listeners
    jbtnInsert.addActionListener(this);
    jbtnUpdate.addActionListener(this);
    jbtnDelete.addActionListener(this);
    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);
    jbtnPrevious.addActionListener(this);
    jbtnNext.addActionListener(this);
    jbtnAll.addActionListener(this);
    jbtnFind.addActionListener(this);
    jbtnFindRes.addActionListener(this);
  }
}
