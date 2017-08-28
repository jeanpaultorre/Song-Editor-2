package cs3500.music.view;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.Note;
import cs3500.music.model.NoteValue;
import cs3500.music.model.Pitch;
import cs3500.music.model.ReadOnlyMusicEditorModel;


/**
 * View for a Piano.
 * Has 120 keys
 */
public class PianoGuiView extends JPanel {

  private final int PIANO_KEY_HEIGHT = 120;
  private final int PIANO_KEY_WIDTH = PIANO_KEY_HEIGHT / 10;     // 12
  private final int BLACK_PIANO_KEY_LENGTH = (PIANO_KEY_HEIGHT / 5) * 2;    // 48
  private final int BLACK_PIANO_KEY_WIDTH = PIANO_KEY_WIDTH / 2;  // 6
  private final int KEY_COUNT = 120;
  private ConcreteGuiViewPanel concreteGuiViewPanel;

  private LinkedHashMap<NoteValue, Rectangle> hashMap = new LinkedHashMap<NoteValue, Rectangle>();
  private ReadOnlyMusicEditorModel readModel;
  private Rectangle practiceCurrentRectangle = null;
  private final int PIANOY = 368;
  private List<Rectangle> loRect = new ArrayList<>();


  /**
   * Constructs the Piano.
   *
   * @param concrete the concrete view.
   */
  PianoGuiView(ConcreteGuiViewPanel concrete, ReadOnlyMusicEditorModel readModel) {
    super();
    this.concreteGuiViewPanel = concrete;
    this.readModel = readModel;
  }

  /**
   * paints the Components.
   *
   * @param g graphics.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawPiano(g);
    paintCurrentKeys(g);
    practice(g);
  }

  /**
   * Draws the piano.
   *
   * @param g the Graphics.
   */
  public void drawPiano(Graphics g) {

    hashMap = new LinkedHashMap<NoteValue, Rectangle>();

    Rectangle currentRectangle;
    int currentPitch = 0;
    int currentOctave;
    int currentPitch2 = 0;

    int temp = -1;
    for (int i = 0; i < 70; i++) {
      temp++;
      currentPitch = temp % 12 ;
      currentPitch2 = i % 7;
      currentOctave = (temp / 12) + 1;

      currentRectangle = new Rectangle((PIANO_KEY_WIDTH * i) + 20, 0,
              PIANO_KEY_WIDTH, PIANO_KEY_HEIGHT);
     // System.out.println(currentRectangle);
      hashMap.put(new NoteValue(Pitch.values()[currentPitch], currentOctave), currentRectangle);
      g.setColor(Color.WHITE);
      if (currentRectangle.x == 20) {
        g.setColor(Color.WHITE);
        g.fillRect(20, currentRectangle.y, PIANO_KEY_WIDTH, PIANO_KEY_HEIGHT);
      }
      else {
        g.fillRect(currentRectangle.x, currentRectangle.y, PIANO_KEY_WIDTH, PIANO_KEY_HEIGHT);
      }
      g.setColor(Color.BLACK);
      g.drawRect(currentRectangle.x, currentRectangle.y, PIANO_KEY_WIDTH, PIANO_KEY_HEIGHT);
      // }

      if (currentPitch2 == 0 || currentPitch2 == 1 || currentPitch2 == 3
              || currentPitch2 == 4 || currentPitch2 == 5) {
        temp++;
        currentPitch = temp % 12;
        currentOctave = (temp / 12) + 1;

        currentRectangle = new Rectangle((PIANO_KEY_WIDTH * i) + 27, 0, BLACK_PIANO_KEY_WIDTH,
                BLACK_PIANO_KEY_LENGTH);

        hashMap.put(new NoteValue(Pitch.values()[currentPitch], currentOctave), currentRectangle);

        g.setColor(Color.BLACK);
        g.fillRect(currentRectangle.x, currentRectangle.y, currentRectangle.width,
                currentRectangle.height);
      }
    }

  ///  System.out.println(this.getHashMap().keySet());
  }

  /**
   * gets the Hash map.
   * @return the hasMap for the given NoteValue.
   */
  public HashMap<NoteValue, Rectangle> getHashMap() {
    return hashMap;
  }

  public Note currentNoteAtPoint(Point mouse) {
    int index = testing(mouse);
    boolean pixelRange = mouse.getX() > 20
            && mouse.getY() < 3000;
    if (pixelRange) {
      Object currentObject = hashMap.keySet().toArray()[index];
      NoteValue currentNote = (NoteValue) currentObject;
      int octave = currentNote.getNoteOctave();
      Pitch p = currentNote.getNotePitch();
      return new Note(1, 1, 64, p, octave, concreteGuiViewPanel.getRedLineBeat());
    } if (index == -1) {
      throw new IllegalArgumentException("try again");
    }
    else {
      throw new IllegalArgumentException("Out of bounds");
    }
  }


  public void paintCurrentKeys(Graphics g) {
    ArrayList<NoteValue> array = new ArrayList<>();
    ArrayList<Integer> indexArray = new ArrayList<>();

    for (Note n : concreteGuiViewPanel.getNotesAtRedLineLocation()) {
      for (NoteValue nv : this.getHashMap().keySet()) {
        if (n.getPitch() == nv.getNotePitch() && n.getOctave() == nv.getNoteOctave()) {
          array.add(nv);
        }

      }
    }
    for (int j = 0; j < hashMap.size(); j++) {
      for (int i = 0; i < array.size(); i++) {
        if (array.get(i).equals(hashMap.keySet().toArray()[j])) {
          indexArray.add(j);
        }
      }
    }
    for (int n : indexArray) {
      Rectangle rect = (Rectangle) hashMap.values().toArray()[n];
      g.setColor(Color.ORANGE);
      g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
  }

  public void practice(Graphics g) {
    NoteValue array = null;
    ArrayList<Integer> indexArray = new ArrayList<>();
    if (practiceCurrentRectangle == null) {
    }else {
        Rectangle rect = practiceCurrentRectangle;
        g.setColor(Color.BLUE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
  }

  public void updatePracticeNote(Point mouse) {
    int index = testing(mouse);
    practiceCurrentRectangle = (Rectangle) hashMap.values().toArray()[index];
    loRect.add(practiceCurrentRectangle);
  }

  public boolean completePractice() {
    return loRect.size() == concreteGuiViewPanel.getNotesAtRedLineLocation().size();
  }

  public void clear() {
    loRect.clear();
  }

  public int testing(Point mouse) {
    int count = -1;
    int correctIndex = -1;
    for (Object n : hashMap.values().toArray()) {
      count++;
      Rectangle currentRectangle = (Rectangle) n;
      int currentRectangleX = currentRectangle.x;
      if (mouse.y - PIANOY < BLACK_PIANO_KEY_LENGTH
              && mouse.x > currentRectangleX
              && mouse.x < currentRectangleX + BLACK_PIANO_KEY_WIDTH) {
        correctIndex = count;
        break;
      } else if (mouse.y - PIANOY < PIANO_KEY_HEIGHT && mouse.x > currentRectangleX && mouse.x < currentRectangleX + PIANO_KEY_WIDTH) {
        correctIndex = count;
      }
    }
    return correctIndex;
  }
}

