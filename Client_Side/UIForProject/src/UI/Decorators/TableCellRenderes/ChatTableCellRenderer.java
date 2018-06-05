package UI.Decorators.TableCellRenderes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;

@SuppressWarnings("serial")
public class ChatTableCellRenderer extends DefaultTableCellRenderer{ //implements TableCellRenderer

	private Color c_Sender;
	private Color c_Reciver;
	
	public ChatTableCellRenderer(Color i_Sender,Color i_Reciver){
		c_Sender = i_Sender;
		c_Reciver = i_Reciver;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		c.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 10));
		
		if(row == 0){
			c.setBackground(Color.BLACK);
			c.setForeground(Color.WHITE);
		}
		else if(row % 2 == 0){
			c.setForeground(Color.BLACK);
		}
		else{
			try	{
				c.setForeground(Color.BLACK);
				if(table.getValueAt(row, col).toString().contains(ServerFunctionExecuter.getInstance().GetLoggedInUser().getFirstName()
						+ " " + ServerFunctionExecuter.getInstance().GetLoggedInUser().getLastName())){
					c.setBackground(c_Sender);
				}
				else{
					c.setBackground(c_Reciver);
				}
			} 
			catch (IOException e)	{
				Popups.ServerConnectionError(e.getMessage());
			}
		}
		return c;
	}
}//end of ChatTableCellRenderer