import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{ 
   public CardLayout cardLayout;
   public JPanel mainContainer;
   private JLabel highScoreLabel;
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
      highScoreLabel = new JLabel("High Score: " + ScoreManager.getHighScore());
      this.add(highScoreLabel);
      mainContainer.add(menu, "MENU"); /* add is used to add elements in JPanel. In this method always have to pass awt component like JFrame,
                                          JButton, JPanel etc. */  
      mainContainer.add(game, "GAME_BOARD");
      this.add(mainContainer);
   }
   
   public void goToGame() {
        cardLayout.show(mainContainer, "GAME_BOARD");
        mainContainer.revalidate();
        mainContainer.repaint();

        // Critical for Games: Transfer keyboard focus to the game area
        SwingUtilities.invokeLater(() -> game.requestFocusInWindow());
   }
   
   // we have to seperately call this method as Jlabel object is alerady stored in memiry and we have to chnage it.
   public void updateHighScoreDisplay() {
        highScoreLabel.setText("High Score: " + ScoreManager.getHighScore());
   }
}
