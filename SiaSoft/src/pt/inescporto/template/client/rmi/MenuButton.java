package pt.inescporto.template.client.rmi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import java.awt.*;
import java.awt.event.*;

import pt.inescporto.template.client.beans.MenuNode;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class MenuButton extends JButton {
  BorderLayout borderLayout1 = new BorderLayout();
  DefaultMutableTreeNode node = null;
  JPopupMenu popup = new JPopupMenu();
  ShortcutInterface impl = null;
  MenuButton btn = this;

  public MenuButton(ShortcutInterface impl) {
    this.impl = impl;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setMaximumSize(new Dimension(3500, 60));
    this.setMinimumSize(new Dimension(35, 60));
    this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    this.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    this.setLayout(borderLayout1);
    addMouseListener( new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 1) {
          singleClick(e);
        }
      }
      public void mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 1) {
          singleClick(e);
        }
      }
    });

    // create popup menu
    JMenuItem item;
    popup.add( item = new JMenuItem( "Remove Shortcut" ) );
    item.setActionCommand(TmplResourceSingleton.getString("popup.label.removeShortcut"));
    item.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        impl.removeShortcut(btn);
      }
    });
  }

  public void singleClick( MouseEvent e ) {
    if (e.isPopupTrigger()) {
      popup.show(this, e.getX(), e.getY());
    }
  }

  public void setMenuNode( DefaultMutableTreeNode node ) {
    this.node = node;
    this.setText(((MenuNode)node.getUserObject()).getMenuDescription());
    if (((MenuNode)node.getUserObject()).getScIcon() != null)
      setIcon(((MenuNode)node.getUserObject()).getScIcon());
  }

  public DefaultMutableTreeNode getMenuNode() {
    return node;
  }
}
