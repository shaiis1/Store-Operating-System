package UI;

import java.awt.TrayIcon.MessageType;
import javax.swing.JOptionPane;

public class Popups{
	
	public static void ShowError(String i_Message){
		JOptionPane.showMessageDialog(null, i_Message, "Error!", MessageType.ERROR.ordinal());
	}
	
	public static void ServerConnectionError(String i_Details){
		ShowError("Problem with connection to server!\n" + i_Details);
	}
	
	public static void ShowWarning(String i_Message){
		JOptionPane.showMessageDialog(null, i_Message, "Warning!", MessageType.WARNING.ordinal());
	}
	
	public static void ShowSuccess(String i_Message){
		JOptionPane.showMessageDialog(null, i_Message, "Success!", MessageType.NONE.ordinal());
	}
}//end of Popups
