package pt.inescporto.siasoft.asq.ejb.dao;

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
public class StatHits implements Serializable {
  Integer docID = null;
  String path = null;
  String title = null;
  Float score = null;

  public StatHits(Integer docID, String path, String title, Float score) {
    this.docID = docID;
    this.path = path;
    this.title = title;
    this.score = score;
  }

  public Integer getDocID() {
    return docID;
  }

  public String getPath() {
    return path;
  }

  public Float getScore() {
    return score;
  }

  public String getTitle() {
    return title;
  }
}
