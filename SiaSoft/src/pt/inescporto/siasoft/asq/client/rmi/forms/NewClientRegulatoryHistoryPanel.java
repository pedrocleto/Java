package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.rmi.FW_JPanelBasic;
import java.awt.Color;
import pt.inescporto.template.client.util.DataSourceException;
import java.util.Vector;
import java.awt.BorderLayout;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHistoryDao;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import java.util.Collection;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.TextLayout;
import java.awt.Font;
import pt.inescporto.template.elements.TplString;
import javax.swing.ToolTipManager;
import java.awt.event.MouseEvent;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.asq.ejb.session.Regulatory;
import javax.naming.*;
import java.rmi.*;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.dao.*;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class NewClientRegulatoryHistoryPanel extends FW_JPanelBasic {
  private DataSource datasource = null;
  private FW_ComponentListener fwCListener = null;
  private java.util.List<Rectangle2D> rectangleList = new LinkedList<Rectangle2D>();
  private Map<Rectangle2D, String> tooltipMap = new HashMap<Rectangle2D, String>();
  Dimension d =null;
  double var=300;
  JPanel jPanel1 = new JPanel();

  public NewClientRegulatoryHistoryPanel(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    setAutoscrolls(true);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    fwCListener.addFWComponentListener(this);
    ToolTipManager.sharedInstance().registerComponent(this);
  }

  private void jbInit() throws Exception {
    setBackground(Color.white);
    setLayout(new BorderLayout());
    setOpaque(false);
  }

  public String getToolTipText(MouseEvent event) {
    String result = null;
    // Search from last (topmost) to first (undermost) rectangle.
    for (ListIterator<Rectangle2D> it = rectangleList.listIterator(rectangleList.
	size()); it.hasPrevious(); ) {
      Rectangle2D rectangle = it.previous();
      if (rectangle.contains(event.getX(), event.getY())) {
	result = tooltipMap.get(rectangle);
	break;
      }
    }
    return result;
  }


  public void historicPicture(int w, int h, Graphics2D g2) {
    Shape shape = null;

    BasicStroke dotted = new BasicStroke(1, BasicStroke.CAP_ROUND,
					 BasicStroke.JOIN_ROUND, 0,
					 new float[] {0, 6, 0, 6}, 0);
    g2.setStroke(dotted);
    g2.drawLine(160, 0, 160, 1000);
    g2.setStroke(dotted);

    g2.drawLine(320, 0, 320, 1000);

    shape = new Rectangle2D.Double(190, 20, 100, 150);

    g2.setStroke(new BasicStroke(2));
    g2.setPaint(Color.white);
    g2.fill(shape);
    g2.setPaint(Color.black);
    g2.draw(shape);

    Collection<RegulatoryHistoryDao> c = null;
    Collection<RegulatoryHistoryDao> c1 = null;
    try {
      c = datasource.getDataSourceByName("HistPassiveRef").listAll();
      c1 = datasource.getDataSourceByName("HistPassiveRef").listAll();
    }
    catch (DataSourceException ex) {
    }


    double crescActive = 20;
    double med1 = crescActive;
    double med2 = crescActive + 10;
    double med3 = 25;
    int j = 0;
    Vector comp = new Vector();

    for (RegulatoryHistoryDao hist : c) {
      boolean t = false;
      if (j == 0) {
	shape = new Rectangle2D.Double(340, crescActive, 25, 40);
	g2.setStroke(new BasicStroke(1));
	g2.setPaint(Color.white);
	g2.fill(shape);
	g2.setPaint(Color.black);
	g2.draw(shape);

	RegulatoryDao regDao = new RegulatoryDao();
	Regulatory reg = null;
	try {
	  reg = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
	  reg.setLinkCondition(null, null);
	  regDao.regId.setValue(hist.regIdFather.getValue());
	  reg.findByPrimaryKey(regDao);
	  regDao = reg.getData();

	  rectangleList.add((Rectangle2D)shape);
	  String tooltip = regDao.name.getValue();
	  tooltipMap.put((Rectangle2D)shape, tooltip);

	}
	catch (UserException ex3) {
	}
	catch (RowNotFoundException ex3) {
	}

	catch (NamingException ex1) {
	}
	catch (RemoteException ex2) {
	}


	TextLayout tl = new TextLayout(hist.regIdFather.getValue(), new Font("Dialog", Font.PLAIN, 10), g2.getFontRenderContext());
	tl.draw(g2, 380, (float)crescActive + 20);
	t = true;

      }
      if (j > 0 && !comp.contains(hist.regIdFather.getValue())) {
	shape = new Rectangle2D.Double(340, crescActive, 25, 40);
	g2.setStroke(new BasicStroke(1));
	g2.setPaint(Color.white);
	g2.fill(shape);
	g2.setPaint(Color.black);
	g2.draw(shape);

	RegulatoryDao regDao = new RegulatoryDao();
	Regulatory reg = null;
	try {
	  reg = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
	  reg.setLinkCondition(null, null);
	  regDao.regId.setValue(hist.regIdFather.getValue());
	  reg.findByPrimaryKey(regDao);
	  regDao = reg.getData();

	  rectangleList.add((Rectangle2D)shape);
	  String tooltip = regDao.name.getValue();
	  tooltipMap.put((Rectangle2D)shape, tooltip);

	}
	catch (UserException ex3) {
	}
	catch (RowNotFoundException ex3) {
	}

	catch (NamingException ex1) {
	}
	catch (RemoteException ex2) {
	}

	TextLayout tl = new TextLayout(hist.regIdFather.getValue(), new Font("Dialog", Font.PLAIN, 10), g2.getFontRenderContext());
	tl.draw(g2, 380, (float)crescActive + 20);
	t = true;
      }
      if (comp.size() <= 0 || !comp.contains(hist.regIdFather.getValue())) {
	for (RegulatoryHistoryDao hist2 : c1) {
	  if (hist2.regIdFather.getValue().equals(hist.regIdFather.getValue())) {
	    if (hist2.actionType.getValue().equals("A")) {
	      Point p1 = new Point(340, (int)med1);
	      Point p2 = new Point(330, (int)med3);
	      Point p3 = new Point(340, (int)med2);

	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(new Color(255, 215, 0));
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med1 = med1 + 15;
	      med3 = med3 + 15;
	      med2 = med2 + 15;

	    }

	    if (hist2.actionType.getValue().equals("R")) {
	      Point p1 = new Point(340, (int)med1);
	      Point p2 = new Point(330, (int)med3);
	      Point p3 = new Point(340, (int)med2);
	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.red);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med1 = med1 + 15;
	      med3 = med3 + 15;
	      med2 = med2 + 15;

	    }
	    if (hist2.actionType.getValue().equals("T")) {
	      Point p1 = new Point(340, (int)med1);
	      Point p2 = new Point(330, (int)med3);
	      Point p3 = new Point(340, (int)med2);
	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.BLUE);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med1 = med1 + 15;
	      med3 = med3 + 15;
	      med2 = med2 + 15;

	    }

	    if (hist2.actionType.getValue().equals("RP")) {
	      Point p1 = new Point(340, (int)med1);
	      Point p2 = new Point(330, (int)med3);
	      Point p3 = new Point(340, (int)med2);
	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.GREEN);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med1 = med1 + 15;
	      med3 = med3 + 15;
	      med2 = med2 + 15;

	    }

	    if (hist2.actionType.getValue().equals("TP")) {
	      Point p1 = new Point(340, (int)med1);
	      Point p2 = new Point(330, (int)med3);
	      Point p3 = new Point(340, (int)med2);
	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(new Color(147, 112, 219));
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med1 = med1 + 15;
	      med3 = med3 + 15;
	      med2 = med2 + 15;
	    }

	    if (hist2.actionType.getValue().equals("RG")) {
	      Point p1 = new Point(340, (int)med1);
	      Point p2 = new Point(330, (int)med3);
	      Point p3 = new Point(340, (int)med2);
	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.magenta);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med1 = med1 + 15;
	      med3 = med3 + 15;
	      med2 = med2 + 15;
	    }
	  }
	}
      }
      if (t == true) {
	crescActive = crescActive + 50;
	med3 = crescActive + 5;
	med1 = crescActive;
	med2 = crescActive + 10;
	comp.add(hist.regIdFather.getValue());
      }
      j++;
    }

    try {
      c = datasource.getDataSourceByName("HistActiveRef").listAll();
      c1 = datasource.getDataSourceByName("HistActiveRef").listAll();
    }
    catch (DataSourceException ex) {
    }

    double crescPassive = 20;
    double med4 = crescPassive;
    double med5 = crescPassive + 10;
    double med6 = 25;

    int i = 0;
    Vector s = new Vector();
    for (RegulatoryHistoryDao hist : c) {
      boolean t = false;
      if (i == 0) {
	shape = new Rectangle2D.Double(115, crescPassive, 25, 40);
	g2.setStroke(new BasicStroke(1));
	g2.setPaint(Color.white);
	g2.fill(shape);
	g2.setPaint(Color.black);
	g2.draw(shape);

	RegulatoryDao regDao = new RegulatoryDao();
	Regulatory reg = null;
	try {
	  reg = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
	  reg.setLinkCondition(null, null);
	  regDao.regId.setValue(hist.regIdSon.getValue());
	  reg.findByPrimaryKey(regDao);
	  regDao = reg.getData();

	  rectangleList.add((Rectangle2D)shape);
	  String tooltip = regDao.name.getValue();
	  tooltipMap.put((Rectangle2D)shape, tooltip);

	}
	catch (RowNotFoundException ex4) {
	}
	catch (UserException ex4) {
	}

	catch (NamingException ex1) {
	}
	catch (RemoteException ex2) {
	}

	TextLayout tl = new TextLayout(hist.regIdSon.getValue(), new Font("Dialog", Font.PLAIN, 10), g2.getFontRenderContext());
	tl.draw(g2, (float)10, (float)crescPassive + 20);
	t = true;
      }
      if (i > 0 && !s.contains(hist.regIdSon.getValue())) {
	shape = new Rectangle2D.Double(115, crescPassive, 25, 40);
	g2.setStroke(new BasicStroke(1));
	g2.setPaint(Color.white);
	g2.fill(shape);
	g2.setPaint(Color.black);
	g2.draw(shape);

	RegulatoryDao regDao = new RegulatoryDao();
	Regulatory reg = null;
	try {
	  reg = (Regulatory)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.Regulatory");
	  reg.setLinkCondition(null, null);
	  regDao.regId.setValue(hist.regIdSon.getValue());
	  reg.findByPrimaryKey(regDao);
	  regDao = reg.getData();

	  rectangleList.add((Rectangle2D)shape);
	  String tooltip = regDao.name.getValue();
	  tooltipMap.put((Rectangle2D)shape, tooltip);

	}
	catch (RowNotFoundException ex4) {
	}
	catch (UserException ex4) {
	}

	catch (NamingException ex1) {
	}
	catch (RemoteException ex2) {
	}

	TextLayout tl = new TextLayout(hist.regIdSon.getValue(), new Font("Dialog", Font.PLAIN, 10), g2.getFontRenderContext());
	tl.draw(g2, (float)10, (float)crescPassive + 20);
	t = true;
      }
      if (s.size() <= 0 || !s.contains(hist.regIdSon.getValue())) {
	for (RegulatoryHistoryDao hist2 : c1) {
	  if (hist2.regIdSon.getValue().equals(hist.regIdSon.getValue())) {
	    if (hist2.actionType.getValue().equals("A")) {
	      Point p1 = new Point(140, (int)med4);
	      Point p2 = new Point(150, (int)med6);
	      Point p3 = new Point(140, (int)med5);
	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(new Color(255, 215, 0));
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med4 = med4 + 15;
	      med6 = med6 + 15;
	      med5 = med5 + 15;

	    }

	    if (hist2.actionType.getValue().equals("R")) {
	      Point p1 = new Point(140, (int)med4);
	      Point p2 = new Point(150, (int)med6);
	      Point p3 = new Point(140, (int)med5);

	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.red);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med4 = med4 + 15;
	      med6 = med6 + 15;
	      med5 = med5 + 15;

	    }

	    if (hist2.actionType.getValue().equals("T")) {
	      Point p1 = new Point(140, (int)med4);
	      Point p2 = new Point(150, (int)med6);
	      Point p3 = new Point(140, (int)med5);

	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.blue);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med4 = med4 + 15;
	      med6 = med6 + 15;
	      med5 = med5 + 15;

	    }

	    if (hist2.actionType.getValue().equals("RP")) {
	      Point p1 = new Point(140, (int)med4);
	      Point p2 = new Point(150, (int)med6);
	      Point p3 = new Point(140, (int)med5);

	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.magenta);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med4 = med4 + 15;
	      med6 = med6 + 15;
	      med5 = med5 + 15;

	    }

	    if (hist2.actionType.getValue().equals("TP")) {
	      Point p1 = new Point(140, (int)med4);
	      Point p2 = new Point(150, (int)med6);
	      Point p3 = new Point(140, (int)med5);

	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(new Color(147, 112, 219));
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med4 = med4 + 15;
	      med6 = med6 + 15;
	      med5 = med5 + 15;

	    }

	    if (hist2.actionType.getValue().equals("RG")) {
	      Point p1 = new Point(140, (int)med4);
	      Point p2 = new Point(150, (int)med6);
	      Point p3 = new Point(140, (int)med5);

	      Polygon poly = Triangle(p1, p2, p3);
	      g2.setPaint(Color.green);
	      g2.fillPolygon(poly);
	      g2.drawPolygon(poly);
	      med4 = med4 + 15;
	      med6 = med6 + 15;
	      med5 = med5 + 15;

	    }
	  }
	}
      }
      if (t == true) {
	crescPassive = crescPassive + 50;
	med6 = crescPassive + 5;
	med4 = crescPassive;
	med5 = crescPassive + 10;
	s.add(hist.regIdSon.getValue());
      }
      i++;
    }
    if(crescActive>=crescPassive){
      var=crescActive+10;
      setPreferredSize(new Dimension(400,(int)var));
    }
    else if(crescActive<crescPassive){
      var = crescPassive + 10;
      setPreferredSize(new Dimension(400, (int)var));
    }
    else {
      setPreferredSize(new Dimension(400, (int)var));
    }
  }

  public Polygon Triangle(Point p1, Point p2, Point p3) {
    Polygon poly = new Polygon();
    poly.addPoint(p1.x, p1.y);
    poly.addPoint(p2.x, p2.y);
    poly.addPoint(p3.x, p3.y);
    return poly;
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    Dimension d = getSize();
    g2.setBackground(getBackground());
    g2.clearRect(0, 0, d.width, d.height);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    historicPicture(d.width, d.height, g2);
  }
}
