package pt.inescporto.template.client.rmi;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

import javax.naming.Context;
import java.util.Properties;
import java.util.ResourceBundle;

public class ContextPropertiesClient {
  private static ResourceBundle tmplProp = ResourceBundle.getBundle("pt.inescporto.template.client.rmi.TemplateRMI");

  public Properties getInitialContextProperties() {
    Properties prop = new Properties();

    // check if properties are provided from other file
    if( tmplProp.getString("server.redirect").compareToIgnoreCase("yes") == 0 )
      tmplProp = ResourceBundle.getBundle(tmplProp.getString("server.properties"));

    prop.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                     tmplProp.getString("server.contextFactory"));
    prop.setProperty(Context.PROVIDER_URL,
                     tmplProp.getString("server.protocol") + "//" + tmplProp.getString("server.name") + "/" +
                     tmplProp.getString("server.basedir"));
    prop.setProperty(Context.URL_PKG_PREFIXES,
                     tmplProp.getString("server.pkgPrefixes"));
    prop.setProperty(Context.SECURITY_PRINCIPAL,
                     tmplProp.getString("server.admin"));
    prop.setProperty(Context.SECURITY_CREDENTIALS,
                     tmplProp.getString("server.password"));


    return prop;
  }

  public String getBaseURL() {
    String urlString = null;
    if( tmplProp.getString("server.redirect").compareToIgnoreCase("yes") == 0 )
      tmplProp = ResourceBundle.getBundle(tmplProp.getString("server.properties"));

    urlString = "http://" + tmplProp.getString("server.name") + "/" + tmplProp.getString("server.basedir");

    return urlString;
  }

  public void makePropertiesDefault() {
    System.setProperties( getInitialContextProperties() );
  }
}
