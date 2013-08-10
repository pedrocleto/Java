package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import pt.inescporto.template.elements.*;

public class TmplTableColumnInteger extends TableColumn {
  protected String field;
  protected TplInt link = null;
  protected int fieldPerm = 7;

  public TmplTableColumnInteger( int modelIndex, String field ) {
    super( modelIndex );
    this.field = field;
    cellEditor = new TmplIntegerEditor();
    cellRenderer = new TmplIntegerRenderer();
  }

  public String getField() {
    return field;
  }
}
