//write code adhering to OOPs principles like abstraction, polymorphism etc.
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameBoard {
  public static void main(String[] args) {
      JFrame frame = new JFrame();
      URL iconUrl = GameBoard.class.getResource("/icon.png");
      GamePanel panel = new GamePanel();
      
      if (iconUrl != null) {
          Image icon = new ImageIcon(iconUrl).getImage();
          frame.setIconImage(icon);
      } else {
          System.err.println("Window icon not found: /icon.png");
      }
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Snake Game");
      // frame can have multiple panels
      frame.add(panel);
      frame.pack(); /* This calculates the title bar size automatically and other decorations accordingly
      as everything is together includeded. Earliner frame.setSize() we have to manage title bar, panel and other elemnts
      in the fixed size
      */
      frame.setResizable(false);
      frame.setVisible(true);
  }
}