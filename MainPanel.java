import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{ 
   private CardLayout cardLayout;
   private JPanel mainContainer;
   Menu menu;
   GameBoard game;
   public MainPanel(){
      /*
       CardLayout manages the working of changing panels and maincontainer stores all the cards with keys.
       we have passed 
       all panels are plassed in a stack one above another and it searches the appropriate panel
      */ 
      cardLayout = new CardLayout();
      mainContainer = new JPanel(cardLayout);  /* we can use this too . mainContainer = new JPanel();
                                                  mainContainer.setLayout(cardLayout);*/
      menu = new Menu(this);
      game = new GameBoard();
      
      mainContainer.add(menu, "MENU"); /* add is used to add elements in JPanel. In this method always have to pass awt component like JFrame,
                                          JButton, JPanel etc. */  
      mainContainer.add(game, "GAME_BOARD");
      this.add(mainContainer);
   }
   public void goToGame() {
        cardLayout.show(this, "GAME_SCREEN"); // we are passing this because we are still writing code in class for modular approach.
        
        // Critical for Games: Transfer keyboard focus to the game area
        game.requestFocusInWindow();
    }
}