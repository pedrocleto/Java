package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import pt.inescporto.template.elements.*;

public class TmplTableColumnLong extends TableColumn {
  protected String field;
  protected TplLong link = null;
  protected int fieldPerm = 7;

  public TmplTableColumnLong( int modelIndex, String field ) {
    super( modelIndex );
    this.field = field;
    cellEditor = new TmplLongEditor();
    cellRenderer = new TmplLongRenderer();
  }

  public String getField() {
    return field;
  }
}
