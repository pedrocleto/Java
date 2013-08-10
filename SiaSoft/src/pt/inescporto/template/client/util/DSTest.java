package pt.inescporto.template.client.util;

import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryAsLegislationDao;

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
public class DSTest {
  public DSTest() {
  }

  public DataViewDB buildView() {
    DataViewDB dvDB = new DataViewDB();
    dvDB.setName("regulatory");

    DataSourceDB dsReg = new DataSourceDB();
    dsReg.setName("regulatory");
    dsReg.setAttrs(new RegulatoryDao());

    DataSourceDB dsRasl = new DataSourceDB();
    dsRasl.setName("regulatoryAsLegislation");
    dsRasl.setAttrs(new RegulatoryAsLegislationDao());

//    DSRelation dsrRasl = new DSRelation();
//    dsrRasl.setName("regToRasl");
/*    dsrRasl.setMaster(dsReg);
    dsrRasl.setDetail(dsRasl);*/
//    dsrRasl.addKey(new RelationKey("regId", "regId"));

    dvDB.addDataSource(dsReg);
    dvDB.addDataSource(dsRasl);

//    dvDB.addRelation(dsrRasl);

    return dvDB;
  }

  public static void main(String[] args) {
    DSTest dstest = new DSTest();

    DataViewDB dvDB = dstest.buildView();
/*    dvDB.listContent();
    dvDB.buildSelectAll();*/
  }
}
