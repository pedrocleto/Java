package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import pt.inescporto.template.elements.TplString;

/**
 * <p>Title: Benchmarking</p>
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
public class TmplTableColumnLongText extends TableColumn {
  protected String field;
  protected TplString link = null;
  protected int fieldPerm = 7;

  public TmplTableColumnLongText(int modelIndex, String field) {
    super(modelIndex);
    this.field = field;
    cellEditor = new TmplTextAreaEditor();
    cellRenderer = new TmplTextAreaRender();
  }
}
