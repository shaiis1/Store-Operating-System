package UI.Decorators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class superButton extends JButton{
    
    private Color c_Color;
    
    public static int allButtonsWidth = 0;
    public static int allButtonsHeight = 0;
    
    public superButton(String title, Color i_Color){
        super(title);
        c_Color = i_Color;
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(allButtonsWidth, allButtonsHeight));
        setMargin(new Insets(6, 6, 6, 6));
        setBackground(c_Color);
        setBorder(BorderFactory.createLineBorder(c_Color, 3));
        setFont(new Font("Tahoma", 1, 12));
    }
    
    @Override
    protected void paintComponent(Graphics g){
        if(getModel().isPressed()){
            setBackground(c_Color.darker());
        }
        else if(getModel().isRollover()){
            setBackground(c_Color.brighter());
        }
        
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0), 
                getBackground(), 
                new Point(0, getHeight()), 
                getBackground()));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight() / 3), 
                getBackground(), 
                new Point(0, getHeight()), 
                getBackground().brighter()));
        g2.fillRect(0, getHeight() / 3, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
        setBackground(c_Color);
    }
}//end of superButton