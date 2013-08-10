package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import pt.inescporto.template.client.design.*;

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
public class EditorCardLayout implements ActionListener {
    JPanel cards; //a panel that uses CardLayout
    UpdateToolBar updateToolBar = null;

    private ToolBarModule toolBarModule = null;
    TmplJButtonUpdate jbtnUpdate = null;
    public EditorCardLayout(ToolBarModule toolBarModule){
      this.toolBarModule = toolBarModule;
      this.jbtnUpdate = jbtnUpdate;
    }

    public void addComponentToPane(Container pane) {
      updateToolBar = new UpdateToolBar();
      CardLayout cardLayout = new CardLayout();
      cards = new JPanel(cardLayout);
      cards.setPreferredSize(new Dimension(100,95));
      cards.add(toolBarModule, "SELECT");
      cards.add(updateToolBar, "UPDATE");

      pane.add(cards, BorderLayout.WEST);
    }

    public void actionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getActionCommand());
    }

  public JPanel getCards() {
    return cards;
  }

  public UpdateToolBar getUpdateToolBar() {
    return updateToolBar;
  }

}
