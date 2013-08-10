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
public class VersionTag extends AnalizerTag {
  private String sourceMajorVersion;
  private String sourceMinorVersion;
  private String targetMajorVersion;
  private String targetMinorVersion;

  public VersionTag(int tagType, String tagName) {
    super(tagType, tagName);
  }

  public void setSourceMajorVersion(String sourceMajorVersion) {
    this.sourceMajorVersion = sourceMajorVersion;
  }

  public void setSourceMinorVersion(String sourceMinorVersion) {
    this.sourceMinorVersion = sourceMinorVersion;
  }

  public void setTargetMajorVersion(String targetMajorVersion) {
    this.targetMajorVersion = targetMajorVersion;
  }

  public void setTargetMinorVersion(String targetMinorVersion) {
    this.targetMinorVersion = targetMinorVersion;
  }

  public String getSourceMajorVersion() {
    return sourceMajorVersion;
  }

  public String getSourceMinorVersion() {
    return sourceMinorVersion;
  }

  public String getTargetMajorVersion() {
    return targetMajorVersion;
  }

  public String getTargetMinorVersion() {
    return targetMinorVersion;
  }
}
