package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import pt.inescporto.template.elements.*;

public class TmplTableColumnText extends TableColumn {
  protected String field;
  protected TplString link = null;
  protected int fieldPerm = 7;

  public TmplTableColumnText(int modelIndex, String field) {
    super(modelIndex);
    this.field = field;
    cellEditor = new TmplStringEditor();
    cellRenderer = new TmplStringRenderer();
  }

  public String getField() {
    return field;
  }
}
