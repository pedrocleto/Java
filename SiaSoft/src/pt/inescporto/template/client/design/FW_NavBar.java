package pt.inescporto.template.client.design;

import javax.swing.JToolBar;
import pt.inescporto.template.client.util.DataSource;
import java.awt.event.ActionListener;

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
public class FW_NavBar extends JToolBar {
  protected TmplJButtonInsert jbtnInsert = new TmplJButtonInsert();
  protected TmplJButtonUpdate jbtnUpdate = new TmplJButtonUpdate();
  protected TmplJButtonDelete jbtnDelete = new TmplJButtonDelete();
  protected TmplJButtonSave jbtnSave = new TmplJButtonSave();
  protected TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  protected TmplJButtonPrev jbtnPrevious = new TmplJButtonPrev();
  protected TmplJButtonNext jbtnNext = new TmplJButtonNext();
  protected TmplJButtonAll jbtnAll = new TmplJButtonAll();
  protected TmplJButtonFind jbtnFind = new TmplJButtonFind();
  protected TmplJButtonFindResult jbtnFindRes = new TmplJButtonFindResult();
  protected TmplJButtonReport jbtnReport = new TmplJButtonReport();
  protected TmplJButtonExit jbtnExit = new TmplJButtonExit();

  public FW_NavBar() {
    setFloatable(false);
    add(jbtnInsert);
    add(jbtnUpdate);
    add(jbtnDelete);
    add(jbtnSave);
    add(jbtnCancel);
    add(jbtnPrevious);
    add(jbtnNext);
    add(jbtnFind);
    add(jbtnFindRes);
    add(jbtnAll);
    add(jbtnReport);
    add(jbtnExit);
  }

  public void setFW_ComponentListener(FW_ComponentListener fwCL) {
    // register Framework component listeners
    if (fwCL != null) {
      fwCL.addFWComponentListener(jbtnInsert);
      fwCL.addFWComponentListener(jbtnUpdate);
      fwCL.addFWComponentListener(jbtnDelete);
      fwCL.addFWComponentListener(jbtnSave);
      fwCL.addFWComponentListener(jbtnCancel);
      fwCL.addFWComponentListener(jbtnPrevious);
      fwCL.addFWComponentListener(jbtnNext);
      fwCL.addFWComponentListener(jbtnAll);
      fwCL.addFWComponentListener(jbtnFind);
      fwCL.addFWComponentListener(jbtnFindRes);
      fwCL.addFWComponentListener(jbtnReport);
      fwCL.addFWComponentListener(jbtnExit);
    }
  }

  public void setDatasourceListener(DataSource dsl) {
    // register datasource listeners
    if (dsl != null) {
      dsl.addDatasourceListener(jbtnInsert);
      dsl.addDatasourceListener(jbtnUpdate);
      dsl.addDatasourceListener(jbtnDelete);
      dsl.addDatasourceListener(jbtnSave);
      dsl.addDatasourceListener(jbtnCancel);
      dsl.addDatasourceListener(jbtnPrevious);
      dsl.addDatasourceListener(jbtnNext);
      dsl.addDatasourceListener(jbtnAll);
      dsl.addDatasourceListener(jbtnFind);
      dsl.addDatasourceListener(jbtnFindRes);
      dsl.addDatasourceListener(jbtnReport);
      dsl.addDatasourceListener(jbtnExit);
    }
  }

  public void setActionListener(ActionListener al) {
    jbtnInsert.addActionListener(al);
    jbtnUpdate.addActionListener(al);
    jbtnDelete.addActionListener(al);
    jbtnSave.addActionListener(al);
    jbtnCancel.addActionListener(al);
    jbtnPrevious.addActionListener(al);
    jbtnNext.addActionListener(al);
    jbtnAll.addActionListener(al);
    jbtnFind.addActionListener(al);
    jbtnFindRes.addActionListener(al);
    jbtnReport.addActionListener(al);
    jbtnExit.addActionListener(al);
  }
}
