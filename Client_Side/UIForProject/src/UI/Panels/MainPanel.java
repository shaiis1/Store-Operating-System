package UI.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class MainPanel  extends JPanel{
	protected int centerWidth;
	protected int leftWidth;
	protected int rightWidth;
	protected int centerHeight;
	protected int upHeight;
	protected int downHeight;
	
	protected JPanel centerPanel;
	protected JPanel leftPanel;
	protected JPanel rightPanel;
	protected JPanel downPanel;
	private JPanel upPanel;
	protected JPanel upLeftPanel;
	protected JPanel upCenterPanel;
	protected JPanel upRightPanel;
	
	private JLabel imageLabel;
	
	protected Color c_Blue;
	protected Color c_Red;
	protected Color c_Gray;
	protected Color c_Orange;
	protected Color c_LightGray;
	protected Color c_Green;
	
	public MainPanel(int panelWidth,int panelHeight){
		super(new BorderLayout());
		
		setWidthAndHight(panelWidth,panelHeight);

		setColors();
		createPanels();
		attachPanelsToFame();
		
		//protected abstract - injection point
		createComponents();
		attachToPanels();
		addEvents();
	}
	
	protected abstract void createComponents();
	protected abstract void attachToPanels();
	protected abstract void addEvents();
	
	private void setWidthAndHight(int panelWidth,int panelHeight){
		rightWidth = (int) (panelWidth * 0.25);
		leftWidth = (int) (panelWidth * 0.25);
		centerWidth = (int) (panelWidth * 0.5);
		
		centerHeight = (int) (panelHeight * 0.7);
		upHeight = (int) (panelHeight * 0.2);
		downHeight = (int)(panelHeight * 0.1);
	}
	private void setColors(){
		float [] hsb = new float[3];
		
		Color.RGBtoHSB(146, 188, 222, hsb);
		c_Blue = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		
		Color.RGBtoHSB(244, 96, 92, hsb);
		c_Red = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		
		Color.RGBtoHSB(67, 73, 73, hsb);
		c_Gray = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		
		Color.RGBtoHSB(100, 108, 108, hsb);
		c_LightGray = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		
		Color.RGBtoHSB(252, 187, 0, hsb);
		c_Orange = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
		
		Color.RGBtoHSB(95, 169, 107, hsb);
		c_Green = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);	
	}
	
	protected void createPanels(){
		centerPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		downPanel = new JPanel();
		upPanel = new JPanel();
		upLeftPanel = new JPanel();
		upRightPanel = new JPanel();
		upCenterPanel = new JPanel();
		
		upPanel.setPreferredSize( new Dimension(leftWidth + centerWidth + rightWidth, upHeight ) );
		centerPanel.setPreferredSize( new Dimension( centerWidth, centerHeight + 15) );
		upCenterPanel.setPreferredSize(new Dimension(centerWidth, upHeight));
		leftPanel.setPreferredSize( new Dimension( leftWidth, centerHeight ) );
		upLeftPanel.setPreferredSize(new Dimension(leftWidth - 10, upHeight));
		rightPanel.setPreferredSize( new Dimension( rightWidth, centerHeight ) );
		upRightPanel.setPreferredSize(new Dimension(rightWidth - 10, upHeight));
		downPanel.setPreferredSize( new Dimension(leftWidth + centerWidth + rightWidth, downHeight ) );
		
		imageLabel = new JLabel();
        imageLabel.setIcon(getSOSImage());
        upCenterPanel.add(imageLabel);

        upRightPanel.setBackground(c_Gray);
        upCenterPanel.setBackground(c_Gray);
        upLeftPanel.setBackground(c_Gray);
		
		upPanel.setBackground(c_Gray);
		leftPanel.setBackground(c_LightGray);
		rightPanel.setBackground(c_LightGray);
		downPanel.setBackground(c_LightGray);
		centerPanel.setBackground(c_Gray);
	}
	
	private ImageIcon getSOSImage(){
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/SOS.jpg")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance((int)(centerWidth * 0.65),(int)(upHeight * 0.9), 
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    return new ImageIcon(newimg);
	}
	
	private void attachPanelsToFame(){
		upPanel.add(upLeftPanel, BorderLayout.WEST);
		upPanel.add(upCenterPanel,  BorderLayout.CENTER);
		upPanel.add(upRightPanel, BorderLayout.EAST);
		
		this.add(upPanel, BorderLayout.NORTH);
		this.add(downPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
	}
}//end of MainPanel