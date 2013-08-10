package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import pt.inescporto.template.elements.*;

public class TmplTableColumnFloat extends TableColumn {
  protected String field;
  protected TplFloat link = null;
  protected int fieldPerm = 7;

  public TmplTableColumnFloat( int modelIndex, String field ) {
    super( modelIndex );
    this.field = field;
    cellEditor = new TmplFloatEditor();
    cellRenderer = new TmplFloatRenderer();
  }

  public String getField() {
    return field;
  }
}
