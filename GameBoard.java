//write code adhering to OOPs principles like abstraction, polymorphism etc.
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent;   

public class GameBoard extends JPanel implements ActionListener
{ 
    private final int CELL = 25; 
    private final int WIDTH = 800;
    private final int HEIGHT = 700;
    private final int ALL_TILES = (WIDTH * HEIGHT) / (CELL * CELL);
    private int appleX, appleY;
    private Random random;
    private Timer timer;
    private final int DELAY;
    /*
     The entire archeticture of the Timer and actionPerformed and paint is alerady pre-defined and system decide when to
     execute what, just write code to fit.
     */
    private boolean running;
    /* we are imagining the screen of the gameboard to be like grid of cells. So each cell will have it's own x and y co-ordinates and total 
      number of cells will be :- area of screen(w * h)/area of cell
    */
    /* we have done this to not to decide rows and columns but to acess each cell x and y co-ordinates 
      this array of x and y are all the coordinats the  snake can access.
    */
   private final int[] x = new int[ALL_TILES];
   private final int[] y = new int[ALL_TILES];
   private int bodyParts = 1;
   private char direction = 'R';
   private MainPanel parent;
   
   public GameBoard(){
      this.setBackground(Color.BLACK);
      this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      this.setFocusable(true);
      this.random = new Random();
      this.addKeyListener(new MyKeyAdapter());
      this.DELAY = 155;
      this.running = false;
      initGame();
   }
   
   private void initGame(){
       bodyParts = 1;
       spawnApple();
       spawnSnake();
       this.running = true;
       // Create the timer: 'this' means the ActionListener is in this class
       timer = new Timer(DELAY, this); 
       timer.start();
   }
   
   private void spawnApple(){
       boolean onSnake;

       do {
       onSnake = false;
       appleX = random.nextInt((int)(WIDTH / CELL)) * CELL; /*1. The Division: (WIDTH / TILE_SIZE)This tells us how many
       columns exist in your grid.800 / 25 = 32 columns.We are essentially saying: "Our world is 32 tiles wide."
       2. The Random: random.nextInt(32)The nextInt(max) 
       method picks a random whole number between 0 and 31.It chooses a Tile Index, not a pixel coordinate.Imagine picking
       a random slot in a ice cube tray.
       3. The Multiplication: * TILE_SIZE This converts the "Tile Index" back into Pixels so the computer knows where to 
       draw it on the screen.If the random number was 0, the position is 0 * 25.If the random number was 31, the position 
       is 31, 31 * 25 = 77*/
       appleY = random.nextInt((int)(HEIGHT / CELL)) * CELL; // same explanation foe this.

        // 2. Check this position against every part of the snake
       for (int i = 0; i < bodyParts; i++) {
            if (appleX == x[i] && appleY == y[i]) {
                onSnake = true; 
                break; // No need to check the rest of the body, just retry
            }
       }
        
       } while (onSnake);
   }
   
   private void spawnSnake(){
       x[0] = CELL * 5;/* it says our snake should be 5 steps away from the top-left corner. Multiplying with 
                              CEll converts it into coordinates*/
       y[0] = CELL * 5;
   }
   
   /*
    1. The Screen Coordinate SystemIn standard math, (0,0) is in the center, and Y goes up for positive numbers. In computer
    graphics:The Origin (0,0) is the Top-Left corner of your window.
    Think of it like the !st quadrant of the cartesian plane. upside down.
    X-axis: Increases as you move Right.Y-axis: Increases as you move Down.
    2. Breaking Down the switch LogicThis code tells the Head of the snake (x[0], y[0]) which neighbor cell to jump
    into based on the current direction.
    */
    public void move(){
        for (int i = bodyParts; i > 0; i--) {
        x[i] = x[i - 1]; // the head at 0 is moved at 1 and so are the other parts.
        y[i] = y[i - 1];
        }
        // 2. MOVE THE HEAD
        // Update the head (index 0) based on the 'direction' variable
        switch (direction) {
        case 'U' -> y[0] -= CELL; //It's like CELL * 5 - 25
        case 'D' -> y[0] += CELL; 
        case 'L' -> x[0] -= CELL; 
        case 'R' -> x[0] += CELL; 
        }
    }
    
    public void checkCollision() {
    /*
     The reason we always check x[0] is in the move -> switch case as we always keep an track of the head.
     */
    //CHECK APPLE
    if ((x[0] == appleX) && (y[0] == appleY)) {
        bodyParts++;  
        //as bodyParts increases paintComponent is automatically called and one extra segment is added.
        spawnApple();
    }
    //CHECK COLLISON
    if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
        running = false; 
    }
    for (int i = bodyParts; i > 0; i--) {
        if (x[0] == x[i] && y[0] == y[i]) {
            running = false; 
        }
    }
    if (!running) {
        int currentScore = bodyParts - 1; // head is alerady there and more apples we eat more is the score.
        ScoreManager.saveHighScore(currentScore);
        gameOver(); 
    }
    }
   //this method is part of JPanel and compulsory to add before drawing graphics. Calls itself automatically.
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
   
   public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') direction = 'L';
                    /*
                     That specific if statement is what prevents your snake from committing "instant suicide." 
                     It enforces the physical rules of the game.In a classic Snake game, the snake has momentum. 
                     If it is currently moving Right, its body is trailing behind it to the Left. If the player were 
                     allowed to immediately switch the direction to Left, the head would collide with the very first neck 
                     segment instantly. Preventing 180-Degree TurnsThe logic if(direction != 'R') direction = 'L'; 
                     translates to:"If I am NOT currently moving Right, then it is safe to turn Left."If you are moving 
                     Right and press Left, the code simply ignores you. This forces the player to make a "U-turn" 
                     (Right -> Up -> Left) rather than folding back on themselves.
                     */
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') direction = 'D';
                    break;
            }
        }
    }
    
   @Override
   public void actionPerformed(ActionEvent e) {
    if (running) {
        move();
    }
    repaint(); /*It is a built-in method in JPanel which again calls the paintComponent as it requires Graphics object which
                 only systems provide*/
   }
   
   public void gameOver(){
      timer.stop(); //stopping the heartbeat of the game.
      running = false;
      //POP-UP
      JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + (bodyParts - 1));
      //return to menu screen.
      parent.updateHighScoreDisplay();
      parent.cardLayout.show(parent.mainContainer, "MENU");
   }
}