package UI.Frames.AbstractFrames;

import java.awt.Color;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONException;

import Customer.CreateCustomerException;
import ServerFunctions.FromServerException;
import UI.Popups;
import UI.Decorators.superButton;
import Workers.CreateWorkerException;

@SuppressWarnings("serial")
public abstract class allFramesBase extends JFrame{
	private JPanel mainPanel;
	
	public allFramesBase(){
		try{
			this.setTitle(getFrameTitle());
			this.setSize(getFrameWidth(), getFrameHeight());
			setComponentsSizes();
			this.setBackground(Color.BLACK);
	
			mainPanel = createMainPanel();
	
			this.setContentPane(mainPanel);
			
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setLocationRelativeTo(rootPane);
			this.setResizable(false);
			this.setVisible(true);
		}
		catch (FromServerException fsex){
			fsex.ShowPopup();
		}
		catch (JSONException | CreateCustomerException | CreateWorkerException dataex) {
			Popups.ShowError("wrong data from server" + dataex.getMessage());
		}
		catch(IOException ioex){
			Popups.ServerConnectionError(ioex.getMessage());
		}
	}
	
	protected abstract String getFrameTitle();
	protected abstract JPanel createMainPanel()  throws UnknownHostException, JSONException, IOException,
				FromServerException, CreateCustomerException, CreateWorkerException;
	
	private void setComponentsSizes(){
		if(superButton.allButtonsWidth == 0){
			superButton.allButtonsWidth = getFrameWidth() / 5;
			superButton.allButtonsHeight = getFrameWidth() /25;
		}
	}
	
	protected JPanel getMainPanel(){
		return mainPanel;
	}
	
	protected int getFrameWidth(){ //determined by Frame.pack() method
		return 0;
	}
	protected int getFrameHeight(){ //determined by Frame.pack() method
		return 0;
	}	
}//end of AllFrameBase
