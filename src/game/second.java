package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class second extends JPanel implements ActionListener, KeyListener
{
    Timer t = new Timer(5, this);
    double x = 0, y = 0, accX = 0, accY = 0, velX = 0, velY = 0;
    
    public second(){
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D) g;
        g2.fill(new Ellipse2D.Double(x, y, 40, 40));
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if( x < 0 || x > 740 )
        {
            accX = 0;
            x = 739;
        }
        
        if( y < 0 || y > 520 )
        {
            accY = 0;
            y = 519;
        }
        repaint();
        x += velX;
        y += velY;
        velX += accX / 4;
        velY += accY / 4;
        velX *= 0.875;
        velY *= 0.875;
    }
    
    public void up()
    {
        accY = -1.5;
    }
    
    public void down()
    {
        accY = 1.5;   
    }
    
    public void left()
    {
        accX = -1.5;
    }
    
    public void right()
    {
        accX = 1.5;
    }
    
    
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            up();
        }
        
        if(code == KeyEvent.VK_A)
        {
            left();
        }
        
        if(code == KeyEvent.VK_S)
        {
            down();
        }
        
        if(code == KeyEvent.VK_D)
        {
            right();
        }
        
    }
    public void keyTyped(KeyEvent e)
    {
        
    }
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            accY = 0;
        }
        
        if(code == KeyEvent.VK_A)
        {
            accX = 0;
        }
        
        if(code == KeyEvent.VK_S)
        {
            accY = 0;
        }
        
        if(code == KeyEvent.VK_D)
        {
            accX = 0;
        }
    }
}
