package pt.inescporto.template.email.ejb.session;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 * For an official listing of defined MIME types, take a look in the
 * ftp://ftp.isi.edu/in-notes/iana/assignments/media-types
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class CustomEmailAttachment implements java.io.Serializable {
  private String type;	// content-type
  private String fileName;// File-name
  private Object object; //Object to be sent

  public CustomEmailAttachment(String type, String fileName, Object object) {
    this.type = type;
    this.fileName = fileName;
    this.object = object;
  }

  public String getType() {
    return type;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Object getObject() {
    return object;
  }

  public String getFileName() {
    return fileName;
  }

}
