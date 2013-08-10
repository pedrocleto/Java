package pt.inescporto.siasoft.asq.client.rmi.forms;

/**
 * <p>Title: SIASoft</p>
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
public class RegulatoryListNode {
  private String regId;
  private String description;
  private String resume;
  public RegulatoryListNode() {
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setResume(String resume) {
    this.resume = resume;
  }

  public void setRegId(String regId) {
    this.regId = regId;
  }

  public String getDescription() {
    return description;
  }

  public String getResume() {
    return resume;
  }

  public String getRegId() {
    return regId;
  }

}
