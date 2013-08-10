package pt.inescporto.siasoft.asq.client.rmi.forms;

import javax.swing.DefaultListModel;
import java.util.Date;

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
public interface RegulatoryListInterface {
  public void setLink(String majorThemeHeader, String minorThemeHeader, String keys[], int order);

  public void selectRegulatory(String regID, boolean isLegalReq);

  public void findByCriterion(String name, String resume);

  public void findByCriterionFull(String name, String resume, Date dataIni, Date dataFim,
				  String scope, String source, String Theme, String Theme1,
      boolean state, boolean revocate, boolean hasApplicability, boolean hasLR);
}
