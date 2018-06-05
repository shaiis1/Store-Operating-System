package UI.Frames.AbstractFrames;

import java.awt.Color;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import Customer.CreateCustomerException;
import ServerFunctions.FromServerException;
import UI.Decorators.TableCellRenderes.DataTableCellRender;
import Workers.CreateWorkerException;

@SuppressWarnings("serial")
public abstract class tableFrame extends allFramesBase{
	
	private Vector<String> columnNames;
	protected Vector<Vector<Object>> data;
	private JScrollPane myMainPanel;
	private JTable table;
	
	public tableFrame(){
		super();
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	protected abstract Vector<Vector<Object>> createTableData()  throws UnknownHostException, IOException,
				FromServerException, JSONException, CreateCustomerException, CreateWorkerException;
	protected abstract Vector<String> getColumnsNames();

	@Override
	protected JPanel createMainPanel() throws UnknownHostException, JSONException, IOException,
				FromServerException, CreateCustomerException, CreateWorkerException{
		JPanel toReturn = new JPanel();

		createAndFillTable();
		myMainPanel = new JScrollPane(table);
		toReturn.add(myMainPanel);
		return toReturn;
	}
	
	private void createAndFillTable() throws UnknownHostException, JSONException, IOException,
				FromServerException, CreateCustomerException, CreateWorkerException{
		table = new JTable();
		table.setDefaultRenderer(Object.class, new DataTableCellRender());
		table.getTableHeader().setBackground(Color.ORANGE);
		columnNames = getColumnsNames();
		data = createTableData();
		UpdateTableData();
	}
	
	protected void UpdateTableData(){
		table.setModel(new DefaultTableModel(data, columnNames){
				public boolean isCellEditable(int row,int column){
					return false;
				}
			});
	}	
} //end of tableFrame class
