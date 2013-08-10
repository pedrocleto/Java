package pt.inescporto.siasoft.tools.dbanalizer.io;

import java.io.*;

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
public class AnalizerTag implements Serializable {
  public static final int NULL = 0;
  public static final int START_HEADER = 100;
  public static final int VERSION = 110;
  public static final int END_HEADER = 101;
  public static final int START_BODY = 200;
  public static final int END_BODY = 201;
  public static final int START_STRUCT = 300;
  public static final int END_STRUCT = 301;
  public static final int START_DATA = 400;
  public static final int END_DATA = 401;
  /*  public static final int HEADER = 50;
    public static final int HEADER = 60;
    public static final int HEADER = 70;
    public static final int HEADER = 80;
    public static final int HEADER = 90;
    public static final int HEADER = 100;
    public static final int HEADER = 110;*/

  private int tagType = NULL;
  private String tagName = null;
  private StringBuffer tagDescription = null;

  public AnalizerTag(int tagType, String tagName) {
    this.tagType=  tagType;
    this.tagName = tagName;
  }

  public StringBuffer getTagDescription() {
    return tagDescription;
  }

  public void setTagDescription(StringBuffer tagDescription) {
    this.tagDescription = tagDescription;
  }

  public String getTagName() {
    return tagName;
  }

  public int getTagType() {
    return tagType;
  }
}
