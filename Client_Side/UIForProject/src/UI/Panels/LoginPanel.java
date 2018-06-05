package UI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import UI.Decorators.superButton;

@SuppressWarnings("serial")
public class LoginPanel extends MainPanel{
	
	private superButton loginButton;
	private JTextField userText;
	private JPasswordField userPass;
	private JLabel userNameLabel;
	private JLabel passWordLabel;
	
	public LoginPanel(int panelWidth,int panelHeight){
		super(panelWidth,panelHeight);
	}
	
	public superButton getLogInButton(){
		return loginButton;
	}
	
	public JTextField getUserName(){
		return userText;
	}
	
	public JPasswordField getPassword(){
		return userPass;
	}
	
	@Override
	protected void createComponents(){
		 userNameLabel = new JLabel("Worker Number");
		 userNameLabel.setForeground(Color.white);
		 userNameLabel.setPreferredSize(new Dimension(130, 30));
		 userText = new JTextField(10);
		 

		 passWordLabel = new JLabel("Password ");
		 passWordLabel.setPreferredSize(new Dimension(130, 30));
		 passWordLabel.setForeground(Color.white);
		 userPass = new JPasswordField(10);
		 
		 loginButton = new superButton("Login",c_Green);
		 loginButton.setBackground(c_Orange);
		 loginButton.setForeground(c_Gray);
	}
	
	@Override
	protected void attachToPanels(){
		centerPanel.add(userNameLabel);
		centerPanel.add(userText);
		centerPanel.add(passWordLabel);
		centerPanel.add(userPass);
		centerPanel.add(loginButton);
	}
	
	@Override
	protected void addEvents() {
		//no Events
	}
}//end of LoginPanel