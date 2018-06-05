package UI.Decorators.TableCellRenderes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class DataTableCellRender extends DefaultTableCellRenderer{ //implements TableCellRenderer

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		c.setFont(new Font("Lucida Sans Unicode", Font.LAYOUT_LEFT_TO_RIGHT, 10));
		
		if(row % 2 == 0){
			c.setBackground(Color.GRAY);
		}
		else{
			c.setBackground(Color.LIGHT_GRAY);
		}
		return c;
	}
}//end of DataTableCellRender
