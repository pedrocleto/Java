package pt.inescporto.template.client.rmi;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.design.*;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplFrame extends TmplNavFrame {
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
  protected TmplJButtonExit jbtnExit = new TmplJButtonExit();

  public TmplFrame() {
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
    addTemplateListener(jbtnExit);
  }

  public void init() {
    super.init();
  }

  public void start() {
    super.start();
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);

    // add action listeners

    // register this listeners
    jpnlMenuNav.setLayout(flowLayout1);
    this.getContentPane().add(jpnlMenuNav, BorderLayout.NORTH);
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
    jpnlMenuNav.add(jbtnExit, null);

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
    jbtnExit.addActionListener(this);
  }
}
