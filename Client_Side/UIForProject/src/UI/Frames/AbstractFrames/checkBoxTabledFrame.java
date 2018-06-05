package UI.Frames.AbstractFrames;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.json.JSONException;
import Customer.CreateCustomerException;
import ServerFunctions.FromServerException;
import Workers.CreateWorkerException;

@SuppressWarnings("serial")
public abstract class checkBoxTabledFrame extends tableFrame
{
	protected Vector<JCheckBox> myCheckBoxes;
	
	public checkBoxTabledFrame(){
		super();
		this.pack();
	}

	protected abstract String [] getCheckboxValues();
	protected abstract ActionListener setCheckboxActionListener(JCheckBox checkBox);
	
	@Override
	protected JPanel createMainPanel() throws UnknownHostException, JSONException, IOException, 
				FromServerException, CreateCustomerException, CreateWorkerException{
		JPanel tablePanel = super.createMainPanel();
		JPanel myMainPanel = new JPanel();
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		for(JCheckBox checkBox : myCheckBoxes){
			checkBoxPanel.add(checkBox);
			checkBox.addActionListener(setCheckboxActionListener(checkBox));
		}
		myMainPanel.add(checkBoxPanel);
		myMainPanel.add(tablePanel);
		
		return myMainPanel;
	}

	@Override
	protected Vector<Vector<Object>> createTableData(){
		createMyCheckBoxes(getCheckboxValues());
		return new Vector<Vector<Object>>();
	}
	
	private void createMyCheckBoxes(String [] values){
		myCheckBoxes =  new Vector<JCheckBox>();
		for(int i = 0; i < values.length; i++){
			myCheckBoxes.add(new JCheckBox(values[i]));
		}
	}
}//end of checkBoxTabledFrame
