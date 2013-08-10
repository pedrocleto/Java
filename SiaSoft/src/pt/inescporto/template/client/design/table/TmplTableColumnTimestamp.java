package pt.inescporto.template.client.design.table;

import javax.swing.table.*;
import pt.inescporto.template.elements.*;

public class TmplTableColumnTimestamp extends TableColumn {
  public static int EDITOR_NORMAL = 1;
  public static int EDITOR_PICKER = 2;
  protected String field;
  protected TplTimestamp link = null;
  protected int fieldPerm = 7;

  public TmplTableColumnTimestamp(int modelIndex, String field) {
    this (modelIndex, field, EDITOR_NORMAL);
  }

  public TmplTableColumnTimestamp(int modelIndex, String field, int editorType) {
    super(modelIndex);
    this.field = field;
    cellEditor = editorType == EDITOR_NORMAL ? new TmplTimestampEditor() : new TmplTimestampPickerEditor();
    cellRenderer = new TmplTimestampRenderer();
  }

  public String getField() {
    return field;
  }
}
