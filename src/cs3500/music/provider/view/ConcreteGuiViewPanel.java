package cs3500.music.provider.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.JPanel;

import cs3500.music.provider.model.INote;
import cs3500.music.provider.model.Pitch;
import cs3500.music.provider.util.NoteOperations;

/**
 * A representation of the notes as an image.
 */
public class ConcreteGuiViewPanel extends JPanel implements KeyListener {
  private NoteOperations no;
  private int totalBeats;
  private final int beatWidth = 20;
  private final int lineHeight = 12;
  private int currentBeat = 0;

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    // draw the notes, black and green
    for (INote n : no.getNotes()) {
      int x = (n.getStartBeat() + 2) * beatWidth;
      int y = ((no.highestNote().getNoteNumber() - n.getNoteNumber()) * lineHeight)
              + (lineHeight * 2);

      g.setColor(Color.BLACK);
      g.fillRect(x, y, beatWidth, lineHeight);

      for (int ii = 1; ii < n.getDuration(); ii++) {
        g.setColor(Color.GREEN);
        g.fillRect(x + (ii * beatWidth), y, beatWidth, lineHeight);
      }
    }

    g.setColor(Color.BLACK);

    //draw the row labels and lines
    int y1 = (lineHeight * 3) / 2;
    int y2 = lineHeight;

    g.drawLine((beatWidth * 2) - 1, (lineHeight * 2) - 1,
            (totalBeats + 2) * beatWidth, (lineHeight * 2) - 1);

    for (String row : this.rows()) {
      g.drawString(row, 0, y1 + g.getFontMetrics().getHeight());
      y1 += lineHeight;
      g.drawLine(beatWidth * 2, y2 + lineHeight, (totalBeats + 2) * beatWidth, y2 + lineHeight);
      y2 += lineHeight;
      g.drawLine(beatWidth * 2, y2 + lineHeight - 1,
              (totalBeats + 2) * beatWidth, y2 + lineHeight - 1);
    }
    g.drawLine((beatWidth * 2) - 1, y2 + lineHeight,
            (totalBeats + 2) * beatWidth, y2 + lineHeight);

    //draw the column labels and lines
    int x1 = -(beatWidth * 2);
    int x2 = -(beatWidth * 2);

    g.drawLine((beatWidth * 2) - 1, lineHeight * 2, (beatWidth * 2) - 1, y2 + lineHeight - 1);

    for (int value : this.columns()) {
      g.drawString(Integer.toString(value), x1 + (beatWidth * 4), g.getFontMetrics().getHeight());
      x1 += (beatWidth * 4);
      g.drawLine(x2 + (beatWidth * 4), lineHeight * 2, x2 + (beatWidth * 4), y2 + lineHeight - 1);
      x2 += (beatWidth * 4);
      g.drawLine(x2 + (beatWidth * 4) - 1, lineHeight * 2,
              x2 + (beatWidth * 4) - 1, y2 + lineHeight - 1);
    }
    g.drawLine(x2 + (beatWidth * 4), lineHeight, x2 + (beatWidth * 4), y2 + lineHeight - 1);

    // useful numbers or drawing the piano
    int leftMargin = (beatWidth * 4) / 5;
    int keyWidth = leftMargin / 2;
    int pianoY = y2 + lineHeight;

    //draw the gray piano background
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, pianoY,
            (totalBeats + 2) * beatWidth, pianoY + (lineHeight * 15) + leftMargin);

    //draw white piano base
    g.setColor(Color.WHITE);
    g.fillRect(leftMargin, pianoY, keyWidth * 80, lineHeight * 15);

    //draw white piano keys
    g.setColor(Color.BLACK);
    for (int ii = leftMargin; ii < leftMargin + (keyWidth * 80); ii += keyWidth) {
      g.drawRect(ii, pianoY, keyWidth, lineHeight * 15);
    }

    // draw black piano keys
    for (int ii = leftMargin; ii < leftMargin + (keyWidth * 80); ii += keyWidth * 8) {
      g.fillRect(ii + (keyWidth / 2) + 1, pianoY, keyWidth / 2, lineHeight * 7);
      g.fillRect(ii + keyWidth + (keyWidth / 2) + 1, pianoY, keyWidth / 2, lineHeight * 7);
      g.fillRect(ii + (keyWidth * 3) + (keyWidth / 2) + 1, pianoY, keyWidth / 2, lineHeight * 7);
      g.fillRect(ii + (keyWidth * 4) + (keyWidth / 2) + 1, pianoY, keyWidth / 2, lineHeight * 7);
      g.fillRect(ii + (keyWidth * 5) + (keyWidth / 2) + 1, pianoY, keyWidth / 2, lineHeight * 7);
    }

    // draw line at beat
    g.setColor(Color.RED);
    g.drawLine((beatWidth * 2) - 1 + (beatWidth * currentBeat), lineHeight * 2,
            (beatWidth * 2) - 1 + (beatWidth * currentBeat), y2 + lineHeight - 1);

  }

  /**
   * Set up the notes in the piece.
   *
   * @param notes the notes in the piece.
   */
  public void setNotes(Set<INote> notes) {
    this.no = new NoteOperations(notes);

    // the total beats onscreen.
    this.totalBeats = no.pieceLength();

    this.setPreferredSize(getPreferredSize());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension((totalBeats + 2) * beatWidth,
            ((no.highestNote().getNoteNumber() - no.lowestNote().getNoteNumber()) * lineHeight)
                    + (lineHeight * 2) // top margin
                    + (lineHeight * 15) // piano keys height
                    + (beatWidth * 4) / 5);
  }

  /**
   * Create a list of the note labels for each row.
   *
   * @return a list of strings which will label the rows.
   */
  private ArrayList<String> rows() {
    ArrayList<String> noteLabels = new ArrayList<>();

    //row labels
    for (int ii = this.no.highestNote().getNoteNumber();
         ii >= this.no.lowestNote().getNoteNumber();
         ii--) {
      noteLabels.add((Pitch
              .values()[Math.floorMod(ii - 1, 12)]
              .toString())
              + Integer.toString((ii - 1 - Math.floorMod(ii - 1, 12)) / 12));
    }
    return noteLabels;
  }

  /**
   * Returns a list of integers to label the beats, starting at 0 and increasing by 4 each time.
   *
   * @return a list of integers to label beats.
   */
  private ArrayList<Integer> columns() {
    ArrayList<Integer> header = new ArrayList<>();
    for (int ii = 0; ii < totalBeats; ii += 4) {
      header.add(ii);
    }
    return header;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //inherited from keyListener
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
      case KeyEvent.VK_LEFT:
        if (currentBeat >= 0) {
          currentBeat--;
        }
        break;
      case KeyEvent.VK_RIGHT :
        if (currentBeat <= totalBeats) {
          currentBeat++;
        }
        break;
      default:
    }
    this.repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //Inherited from KeyListener
  }
}