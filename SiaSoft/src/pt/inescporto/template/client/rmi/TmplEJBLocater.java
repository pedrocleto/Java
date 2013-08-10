package pt.inescporto.template.client.rmi;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplEJBLocater {
  private static TmplEJBLocater instance;
  private InitialContext initialContext;
  private ArrayList homeObjCache = new ArrayList();
  private ArrayList remoteObjCache = new ArrayList();

  public static TmplEJBLocater getInstance() throws NamingException {
    if (instance == null)
      instance = new TmplEJBLocater();
    return instance;
  }

  private TmplEJBLocater() throws NamingException {
    try {
      ContextPropertiesClient client = new ContextPropertiesClient();
      Properties prop = client.getInitialContextProperties();
      initialContext = new InitialContext(prop);
    }
    catch (Exception ex) {
//      ex.printStackTrace();
    }
    return ;
  }

  private Object getEJBHomeCache(String url) {
    try {
      Iterator it = homeObjCache.iterator();
      while (it.hasNext()) {
	LocaterHomeNode node = (LocaterHomeNode)it.next();
	if (node.url.equals(url))
	  return node.ref;
      }
      return null;
    }
    catch (Exception ex) {
      ex.getMessage();
      return null;
    }
  }

  private Object getEJBRemoteCache(String url, String methodName, Class[] parametersType, Object[] parametersValues) {
    try {
      Iterator it = remoteObjCache.iterator();
      while (it.hasNext()) {
	LocaterRemoteNode node = (LocaterRemoteNode)it.next();
	if (node.url.equals(url) && node.methodName.equals(methodName) && node.parametersType == parametersType /* && node.parametersValues == parametersValues*/) {
//	  System.out.println("TmplEJBLocater message : Found in cache EJB <" + node.url + ", " + node.methodName + ", " + node.parametersType + ">");
	  return node.ref;
	}
      }
      return null;
    }
    catch (Exception ex) {
      ex.getMessage();
      return null;
    }
  }

  public Object getEJBHome(String url) {
    Object home = getEJBHomeCache(url);

    if (home == null) {
      String s = url.substring(url.lastIndexOf('.') + 1);

      try {
	Object objRef = initialContext.lookup(s);

	Class objClass = Class.forName(url + "Home");
	home = PortableRemoteObject.narrow(objRef, objClass);
	homeObjCache.add(new LocaterHomeNode(url, home));
      }
      catch (NamingException ex) {
	return null;
      }
      catch (ClassNotFoundException ex) {
	return null;
      }
    }
    return home;
  }

  public Object getEJBRemote(String url) {
    return this.getEJBRemote(url, "create", null, null);
  }

  public Object getEJBRemote(String url, String methodName, Class[] parametersType, Object[] parametersValues) {
    try {
      Object home = getEJBHome(url);
      Object remote = this.getEJBRemoteCache(url, methodName, parametersType, parametersValues);

      if (remote == null) {
	Method createMethod;
	createMethod = home.getClass().getMethod(methodName, parametersType);
	remote = createMethod.invoke(home, parametersValues);
	LocaterRemoteNode node = new LocaterRemoteNode(url, methodName, parametersType, parametersValues, remote);
	remoteObjCache.add(node);
//	System.out.println("TmplEJBLocater added to cache " + node.toString());
      }

      return remote;
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    catch (InvocationTargetException ex) {
      ex.printStackTrace();
    }
    catch (SecurityException ex) {
      ex.printStackTrace();
    }
    catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
    return null;
  }
}

class LocaterHomeNode {
  public String url;
  public Object ref;

  public LocaterHomeNode(String url, Object ref) {
    this.url = url;
    this.ref = ref;
  }
}

class LocaterRemoteNode {
  public String url;
  public Object ref;
  String methodName;
  Class[] parametersType;
  Object[] parametersValues;

  public LocaterRemoteNode(String url, String methodName, Class[] parametersType, Object[] parametersValues, Object ref) {
    this.url = url;
    this.methodName = methodName;
    this.parametersType = parametersType;
    this.parametersValues = parametersValues;
    this.ref = ref;
  }

  public LocaterRemoteNode(String url, Object ref) {
    this(url, "create", null, null, ref);
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("URL = <");
    buffer.append(url + ">, ");
    buffer.append("Method <");
    buffer.append(methodName + ">");

    return buffer.toString();
  }
}
