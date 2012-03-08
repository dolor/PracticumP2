package week4.bounce;

import java.awt.*; //Graphics;
import javax.swing.*;

/**
 * P2 prac wk4.
 * Ball. Represeert een bal die kan stuiteren.
 * Hoort bij de TimedBouncer class.
 * @author  Rieks op den Akker, Arend Rensink en Theo Ruys
 * @version 2005.02.22 
 */
public class Ball {   
    private JPanel panel;
    
    /** @require panel != null */
    public Ball(JPanel panel) {
    	this.panel =panel;
    }

    /** Draw the ball at position x,y on the Canvas box. */
    public void draw(Graphics g) {
        g.fillOval(x, y, BALL_DIAM, BALL_DIAM);
    }

    /**
     * Collision of two balls has the effect that both balls 
     * 'turn around' 180 degrees. Could be better
     */ 
    public void collide(Ball other) {
    	if (this.position().distance(other.position()) < BALL_DIAM) {
            int dx   = this.dx;
            int dy   = this.dy;
            this.dx  = other.dx;
            this.dy  = other.dy;
            other.dx = dx;
            other.dy = dy;
        }
    }
    
    /**
     * Point is handy because we have a method for euclidian 
     * distance (see implementation of collide).
     * @return a Point as the position of the ball
     */
    public Point position() {
    	return new Point(x,y);
    }

    /** Move this ball. */
    public void move(){
        x += dx;
        y += dy;
        Dimension d = panel.getSize();
        if (x < 0) { 
            x  = 0; 
            dx = -dx; 
        } else if (x + BALL_DIAM >= d.width) { 
            x  = d.width - BALL_DIAM; 
            dx = -dx; 
        }
        
        if (y < 0) { 
            y  = 0; 
            dy = -dy; 
        } else if (y + BALL_DIAM >= d.height) { 
            y  = d.height - BALL_DIAM; 
            dy = -dy; 
        }
    }
   
    private static final int BALL_DIAM = 10; // diameter of the ball

    private int x = 0;      // horizontal position of ball
    private int y = 0;      // vertical   position of ball
    private int dx = 2;     // horizontal displacement per move
    private int dy = 2;     // vertical   displacment per move  
}
