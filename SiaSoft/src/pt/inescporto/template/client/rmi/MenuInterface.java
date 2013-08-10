package pt.inescporto.template.client.rmi;

import pt.inescporto.siasoft.comun.client.UserData;
import java.awt.Container;

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
public interface MenuInterface {
  public String getEnvironmentType();

  public String getRole();

  public String getName();

  public String getEnterprise();

  public String getSelectedEnterprise();

  public UserData getUserData();

  public boolean isSupplier();

  public void addContainerFW(Container container);

  public boolean loadForm(String formID, Object keyValue);

  public void addItemToMenu(String regID, Object regRef);
}
