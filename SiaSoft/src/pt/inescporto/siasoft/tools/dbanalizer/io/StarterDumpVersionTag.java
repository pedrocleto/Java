package pt.inescporto.siasoft.tools.dbanalizer.io;

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
public class StarterDumpVersionTag extends AnalizerTag {
  private String majorVersion;
  private String minorVersion;

  public StarterDumpVersionTag(int tagType, String tagName) {
    super(tagType, tagName);
  }

  public void setMajorVersion(String majorVersion) {
    this.majorVersion = majorVersion;
  }

  public void setMinorVersion(String minorVersion) {
    this.minorVersion = minorVersion;
  }

  public String getMajorVersion() {
    return majorVersion;
  }

  public String getMinorVersion() {
    return minorVersion;
  }
}
