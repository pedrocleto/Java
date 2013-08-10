package pt.inescporto.template.client.drmi;

import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplRMILocater {
  private static TmplRMILocater instance;
  private String connectionURI = null;
  private ArrayList remoteObjCache = new ArrayList();

  public static TmplRMILocater getInstance() {
    if (instance == null)
      instance = new TmplRMILocater();
    return instance;
  }

  private TmplRMILocater() {
/*    ClientProperties client = new ClientProperties();
    connectionURI = "//" + client.getProperty("server") + ":" + client.getProperty("rmi_port") + "/";
  }

  private Object getRMIRemoteCache(String url, String methodName,
                                   Class[] parametersType, Object[] parametersValues) {
    try {
      Iterator it = remoteObjCache.iterator();
      while (it.hasNext()) {
        LocaterRemoteNode node = (LocaterRemoteNode) it.next();
        if (node.url.equals(url) && node.methodName.equals(methodName) && node.parametersType == parametersType && node.parametersValues == parametersValues)
          return node.ref;
      }
      return null;
    }
    catch (Exception ex) {
      ex.getMessage();
      return null;
    }*/
  }

  public Object getRMIHome(String url) {
    try {
      return Naming.lookup(connectionURI + url);
    }
    catch (NotBoundException ex) {
      return null;
    }
    catch (MalformedURLException ex) {
      return null;
    }
    catch (RemoteException ex) {
      return null;
    }
  }

  public Object getRMIRemote(String url, String methodName, Class[] parametersType, Object[] parametersValues) {
/*    try {
      Object remote = this.getRMIRemoteCache(url, methodName, parametersType, parametersValues);

      if (remote == null) {
        Method lookup = null;
        remote = Naming.lookup(connectionURI + url);
        remoteObjCache.add(new LocaterRemoteNode(url, methodName, parametersType, parametersValues, remote));
      }
      return remote;
    }
    catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    catch (SecurityException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
    catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    catch (NotBoundException ex) {
      ex.printStackTrace();
    }*/
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
  public String hostName;
  public Object ref;
  String methodName;
  Class[] parametersType;
  Object[] parametersValues;

  public LocaterRemoteNode(String url, String methodName,
                           Class[] parametersType,
                           Object[] parametersValues, Object ref) {
    this.url = url;
    this.methodName = methodName;
    this.parametersType = parametersType;
    this.parametersValues = parametersValues;
    this.ref = ref;
  }

  public LocaterRemoteNode(String url, Object ref) {
    this(url, "create", null, null, ref);
  }
}
