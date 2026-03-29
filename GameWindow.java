//write code adhering to OOPs principles like abstraction, polymorphism etc.
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameWindow extends JFrame{
   public GameWindow(){
      URL iconUrl = GameWindow.class.getResource("/icon.png");
      
      if (iconUrl != null) {
          Image icon = new ImageIcon(iconUrl).getImage();
          this.setIconImage(icon);
      } else {
          System.err.println("Window icon not found: /icon.png");
      }
      
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Snake Game");
      MainPanel mainPanel = new MainPanel();
      this.add(mainPanel);
      this.pack(); /* This calculates the title bar size automatically and other decorations accordingly
      as everything is together includeded. Earliner frame.setSize() we have to manage title bar, panel and other elemnts
      in the fixed size
      */
      this.setResizable(false);
      this.setVisible(true);
   }
   public static void main(String[] args){
     /*
    SwingUtilities.invokeLater(...): This is a static method that tells Java: "Wait until the GUI thread is free, and then run this 
    specific piece of code."
    
    The Lambda () -> new GameWindow(): This is an anonymous function that initializes your main game class.Thread Safety: By using
    invokeLater, you ensure that your GameWindow (and all its buttons, canvases, or labels) like MainPanel, Menu, GameBoard etc. is created 
    on the EDT rather than the "Main" thread.
    */  
     SwingUtilities.invokeLater(() -> new GameWindow());
   }
}