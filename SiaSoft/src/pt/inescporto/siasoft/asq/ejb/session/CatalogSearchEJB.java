package pt.inescporto.siasoft.asq.ejb.session;

import java.util.Vector;
import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import java.io.*;
import pt.inescporto.siasoft.asq.ejb.dao.StatHits;
import pt.inescporto.template.dao.UserException;
import javax.rmi.PortableRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ejb.*;
import javax.naming.*;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.elements.TplString;
/*
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.*;


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
public class CatalogSearchEJB implements SessionBean {
  private final String index = "applications/SIASoft/SIASoft/index"; //this value must be read from XML!
  private final String field = "contents";
/*
  protected SessionContext ctx;

  transient protected IndexReader reader = null;
  transient protected Searcher searcher = null;
  transient protected Analyzer analyzer = null;

  protected transient GenericQuery gQuery;*/

  public CatalogSearchEJB() {
    System.out.println("New CatalogSearch Session Bean created by EJB container ...");

   // initialize();
   // getEJBReferences();
  }
  /*
  private void getEJBReferences() {
    // get Generic Query
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome gQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      gQuery = gQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Generic Query Home");
      re.printStackTrace();
    }
  }

  private void initialize() {
    try {
      reader = IndexReader.open(index);
      searcher = new IndexSearcher(reader);
      analyzer = new StandardAnalyzer();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * EJB Required methods


  public void ejbActivate() throws RemoteException {
    initialize();
    getEJBReferences();
  }

  public void ejbPassivate() throws RemoteException {
    reader = null;
    searcher = null;
    analyzer = null;
    gQuery = null;
  }

  public void ejbRemove() throws RemoteException {
    reader = null;
    searcher = null;
    analyzer = null;
    gQuery = null;
  }

  public void ejbCreate() {
  }

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  /**
   * Public methods
   */

  public Vector queryCatalog(String queryStmt) throws UserException {
    Vector result = null;/*

    QueryParser parser = new QueryParser(field, analyzer);

    if (queryStmt == null || queryStmt.length() <= 0)
      throw new UserException("Não posso pesquisar nada!");

    Query query = null;
    try {
      query = parser.parse(queryStmt);
    }
    catch (ParseException ex) {
      ex.printStackTrace();
      throw new UserException(ex.getMessage(), ex);
    }

    Hits hits = null;
    try {
      hits = searcher.search(query);

      result = new Vector<StatHits>(hits.length());

      for (int i = 0; i < hits.length(); i++) {
        Integer docID = new Integer(hits.id(i));
        Float score = new Float(hits.score(i));
        String path = null;
        String title = null;

	Document doc = null;
	doc = hits.doc(i);
	path = doc.get("path");
	if (path != null) {
          boolean isWindowsPath = path.lastIndexOf("\\") != -1;
          String docName = path.substring(path.lastIndexOf(isWindowsPath ? "\\" : "/") + 1, path.length());
          Vector binds = new Vector();
          binds.add(new TplString(docName.replaceAll(".pdf", "")));
          Vector resume = gQuery.doQuery("SELECT resume FROM regulatory WHERE documentName = ?", binds);
	  title = (resume.size() != 0) ? (((TplString)((Vector)resume.elementAt(0)).elementAt(0)).getValue()) : "Sem registo!";
	}

        StatHits hit = new StatHits(docID, path, title, score);
        result.add(hit);
      }
    }
    catch (IOException ex) {
      ex.printStackTrace();
      throw new UserException(ex.getMessage(), ex);
    }
*/
    return result;
  }

  public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
  }

  public void ejbRemove() throws EJBException, RemoteException {
  }

  public void ejbActivate() throws EJBException, RemoteException {
  }

  public void ejbPassivate() throws EJBException, RemoteException {
  }
}
