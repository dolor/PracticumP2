package week4.bounce;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * P2 prac wk4.
 * Bounce. Weergave van een stuiterende bal.
 * @see BallPanel
 * @see Ball 
 * @author  Cay Horstmann, Rieks op den Akker, Arend Rensink en Theo Ruys
 * @version 2005.02.22
 */
public class Bounce extends JFrame {  
   private JButton      start;
   private BallPanel    ballPanel;
   	
   public Bounce() {
      setTitle("Bounce");
      ballPanel = new BallPanel();
      getContentPane().add("Center", ballPanel);
      JPanel controlPanel = new JPanel();
      start = new JButton("Start");
      controlPanel.add(start);

      start.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent evt){
                  ballPanel.addNewBall();
              }
      });
      getContentPane().add("South", controlPanel);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   public static void main(String[] args){
      JFrame f = new Bounce();
      f.setLocation(200,200);
      f.setSize(300, 200);
      f.setVisible(true);  
   }
}
