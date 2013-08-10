package pt.inescporto.template.client.util;

import java.util.*;
import pt.inescporto.template.elements.TplObject;
import java.lang.reflect.Field;

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
public class DataViewDB {
  public String name = null;
  public Hashtable<String, DataSourceDB> ds = null;
  public Hashtable<String, DSRelation> relations = null;
  public Hashtable<String, Vector<String>> masterRelations = null;

  public DataViewDB() {
    ds = new Hashtable<String,DataSourceDB>();
    relations = new Hashtable<String,DSRelation>();
    masterRelations = new Hashtable<String,Vector<String>>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addDataSource(DataSourceDB dsDB) {
    ds.put(dsDB.getName(), dsDB);
  }

  public void addRelation(DSRelation dsRel) {
    relations.put(dsRel.getName(), dsRel);

    // update master relations
/*    String masterTable = dsRel.getMaster().getName();
    if (masterRelations.containsKey(masterTable)) {
      Vector<String> details = masterRelations.get(masterTable);
      details.add(dsRel.getDetail().getName());
      masterRelations.remove(masterTable);
      masterRelations.put(masterTable, details);
    }
    else {
      Vector<String> details = new Vector<String>();
      details.add(dsRel.getDetail().getName());
      masterRelations.put(masterTable, details);
    }*/
  }

  public void buildSelectAll() {
    StatementsAgregature stmts = new StatementsAgregature();
    String currentTable = name;
    boolean stop = false;


    while (!stop) {
      stmts.build(currentTable, ds.get(currentTable).getAttrs(), null, null);

      System.out.println("SELECT " + stmts.getSelectPart() + " FROM " + stmts.getTablePart() + " WHERE " + stmts.getWherePart());

      System.out.println("Processing relation of :");
      for (String s : masterRelations.keySet()) {
	for (String v : masterRelations.get(s)) {
	  System.out.println(s + " relation to " + v);
          stmts.build(ds.get(v).getName(), ds.get(v).getAttrs(), "regId", "regId");
          System.out.println("SELECT " + stmts.getSelectPart() + " FROM " + stmts.getTablePart() + " WHERE " + stmts.getWherePart() + stmts.getLinkPart());
	}
      }
      stop = true;
    }
  }

  public void listContent() {
    System.out.println(name + " is watching the following DataSources");
    for (DataSourceDB e:ds.values())
      System.out.println(e);

    System.out.println("With the following Relations:");
    for (DSRelation e:relations.values())
      System.out.println(e);
  }
}
