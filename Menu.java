//write code adhering to OOPs principles like abstraction, polymorphism etc.
import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel
{
   /*
   Code should always wriiten like a class whose object is going to be created in other class.
   */
    private final int pw, ph, btnWidth, btnHeight, x, y;
    public Menu(MainPanel switcher){
       this.pw = 800;
       this.ph = 700;
       this.btnWidth = 175;
       this.btnHeight = 50;
       this.setBackground(Color.RED);
       this.setPreferredSize(new Dimension(pw, ph)); // w * h (int input)
       JButton start = new JButton("START");
       start.setForeground(Color.WHITE);
       start.setBackground(Color.GREEN);
       start.setFont(new Font("Cascadia Code", Font.BOLD, 20));
       // Optional: Prevents the button from being 'focusable' at all
       // (Useful for games where you want the keyboard to control the player, not the UI)
       start.setFocusable(false);
      /* 
       Centering Button
       Every JPanel window requires a layout manager to position the elments in it as well as itself if there are multiple Panels. By default 
       all the elements are arranged in a row from left to right in a panel.
       Java has a default layout manager which is this.setLayout(). Either we can disable it or use Layouts like 
       this.setLayout(new GridBagLayout());
       we will use the formula to calculate it as given below.
      */
      this.setLayout(null);
      x = (pw - btnWidth)/2;
      y = (ph - btnHeight)/2;
      start.setBounds(x, y, btnWidth, btnHeight); // x and y set the position and other set dimensions of the button.
      start.addActionListener(e -> switcher.goToGame());
      this.add(start);
    }
}