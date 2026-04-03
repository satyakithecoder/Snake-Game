//write code adhering to OOPs principles like abstraction, polymorphism etc.
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameBoard extends JPanel implements ActionListener
{ 
   private final int CELL = 25; 
    private final int WIDTH = 800;
    private final int HEIGHT = 700;
    private final int ALL_TILES = (WIDTH * HEIGHT) / (CELL * CELL);
    private int appleX, appleY;
    private Random random;
   /* we are imagining the screen of the gameboard to be like grid of cells. So each cell will have it's own x and y co-ordinates and total 
      number of cells will be :- area of screen(w * h)/area of cell
   */
   /* we have done this to not to decide rows and columns but to acess each cell x and y co-ordinates 
      this array of x and y are all the coordinats the  snake can access.
   */
   private final int[] x = new int[ALL_TILES];
   private final int[] y = new int[ALL_TILES];
   private int bodyParts = 1;
   private int count = 0;
   private boolean running = false;
   private char direction = 'R';
   private final Timer timer;
   
   public GameBoard(){
      this.setBackground(Color.BLACK);
      this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      this.setFocusable(true);
      this.random = new Random();
      this.timer = new Timer(120, this);
      setupWASDControls();
      initGame();
   }
   
   private void initGame(){
       count = 0;
       bodyParts = 1;
       running = true;
       direction = 'R';
       spawnApple();
       spawnSnake();
       timer.start();
   }

   private void setupWASDControls() {
       InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
       ActionMap actionMap = getActionMap();

       inputMap.put(KeyStroke.getKeyStroke('W'), "moveUp");
       inputMap.put(KeyStroke.getKeyStroke('w'), "moveUp");
       inputMap.put(KeyStroke.getKeyStroke('A'), "moveLeft");
       inputMap.put(KeyStroke.getKeyStroke('a'), "moveLeft");
       inputMap.put(KeyStroke.getKeyStroke('S'), "moveDown");
       inputMap.put(KeyStroke.getKeyStroke('s'), "moveDown");
       inputMap.put(KeyStroke.getKeyStroke('D'), "moveRight");
       inputMap.put(KeyStroke.getKeyStroke('d'), "moveRight");

       actionMap.put("moveUp", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (direction != 'D') {
                   direction = 'U';
               }
           }
       });

       actionMap.put("moveLeft", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (direction != 'R') {
                   direction = 'L';
               }
           }
       });

       actionMap.put("moveDown", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (direction != 'U') {
                   direction = 'D';
               }
           }
       });

       actionMap.put("moveRight", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (direction != 'L') {
                   direction = 'R';
               }
           }
       });
   }
   
   private void spawnApple(){
       appleX = random.nextInt((int)(WIDTH / CELL)) * CELL; /*1. The Division: (WIDTH / TILE_SIZE)This tells us how many
       columns exist in your grid.800 / 25 = 32 columns.We are essentially saying: "Our world is 32 tiles wide."
       2. The Random: random.nextInt(32)The nextInt(max) 
       method picks a random whole number between 0 and 31.It chooses a Tile Index, not a pixel coordinate.Imagine picking
       a random slot in a ice cube tray.
       3. The Multiplication: * TILE_SIZE This converts the "Tile Index" back into Pixels so the computer knows where to 
       draw it on the screen.If the random number was 0, the position is 0 * 25.If the random number was 31, the position 
       is 31, 31 * 25 = 77*/
       appleY = random.nextInt((int)(HEIGHT / CELL)) * CELL; // same explanation foe this.
   }
   
   private void spawnSnake(){
       x[count] = CELL * 5;/* it says our snake should be 5 steps away from the top-left corner. Multiplying with 
                              CEll converts it into coordinates*/
       y[count] = CELL * 5;
   }
   
   private void move(){
      for(int i = bodyParts; i > 0; i--){
          x[i] = x[i - 1];
          y[i] = y[i - 1];
      }

      switch (direction) {
          case 'U':
              y[0] -= CELL;
              break;
          case 'D':
              y[0] += CELL;
              break;
          case 'L':
              x[0] -= CELL;
              break;
          default:
              x[0] += CELL;
              break;
      }
     
   }
   //this method is part of JPanel and compulsory to add before drawing graphics.
   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g is the paojn brush to pain the canvas which is the JPanel of JFrame.
        /*we will set color and use predefined method to draw shapes, write something etc.*/
        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, CELL, CELL);
        //snake spawning of parts
        g.setColor(Color.GREEN);
        for(int i = 0; i < bodyParts; i++){
           if(i == 0) {
           g.setColor(Color.GREEN); // Bright Green for the Head
           } else {
           g.setColor(new Color(45, 180, 0)); // Darker Green for the Body
           }
           g.fillRect(x[i], y[i], CELL, CELL);
        }
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
       if (!running) {
           return;
       }
       move();
       repaint();
   }
    
}
