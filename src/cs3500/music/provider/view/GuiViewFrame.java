package cs3500.music.provider.view;

import java.awt.Dimension;
import java.util.Set;

import cs3500.music.provider.model.INote;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorView {

  private final ConcreteGuiViewPanel displayPanel;

  /**
   * Creates new GuiView.
   */
  public GuiViewFrame() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
  }

  @Override
  public void start() {
    this.setVisible(true);
  }

  @Override
  public void createView(Set<INote> notes) {
    this.displayPanel.setNotes(notes);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }

}

