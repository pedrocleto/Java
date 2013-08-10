package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.util.ResourceBundle;
import pt.inescporto.siasoft.comun.ejb.dao.WarningMessageDao;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplObjRef;

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
public class WarningMsgParser {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.WMsgResources");

  public WarningMsgParser() {
  }

  public String getWMsg(String msg, String moduleID, Object key) {
    String msgParsed = "";

    String[] tags = msg.split(" ");
    for (int i = 0; i < tags.length; i++) {
      String tag = tags[i];

      // is a resource
      if (tag.startsWith("&")) {
        msgParsed += res.getString(tag.substring(1, tag.length()-1));
      }

      // is a key
      if (tag.startsWith("#")) {
        if (moduleID.equals(WarningMessageDao.docModuleHistory)) {
          msgParsed += ((TplString)((Object[])((TplObjRef)key).getValue())[0]).getValue();
        }
        if (moduleID.equals(WarningMessageDao.regModuleRevogated)) {
          msgParsed += ((TplString)((Object[])((TplObjRef)key).getValue())[0]).getValue();
        }
      }
    }

    return msgParsed;
  }
}
