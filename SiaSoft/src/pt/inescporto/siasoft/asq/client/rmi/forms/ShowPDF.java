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
 * @author not attributable
 * @version 0.1
 */
class ShowPDF {
  public void showPdf(String url) throws Exception {
    String os = System.getProperty("os.name");
    if (os.equalsIgnoreCase("linux")) {
      String[] cmd = new String[] {"/bin/sh", "-c", "/usr/bin/evince " + url};
      Runtime.getRuntime().exec(cmd);
    }
    else {
      Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + url);
    }
  }
}
