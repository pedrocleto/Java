package pt.inescporto.template.client.web;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.*;

public abstract class TmplAppletForm extends TmplAppletNavForm {
  BorderLayout borderLayout1 = new BorderLayout();
  public JPanel jpnlMenuNav = new JPanel();
  protected JPanel jpnlMenu1 = new JPanel(new BorderLayout());
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

  public TmplAppletForm() {
    super();

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
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

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jpnlMenuNav.setOpaque(false);
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
    jpnlMenu1.add(jpnlMenuNav, java.awt.BorderLayout.WEST);
    add(jpnlMenu1, java.awt.BorderLayout.NORTH);

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
