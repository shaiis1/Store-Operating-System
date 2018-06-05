package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;

import Customer.CreateCustomerException;
import ServerFunctions.FromServerException;
import UI.InputCheck.inputException;

public abstract class SendingActionListener implements ActionListener{
	
	private boolean isSucceed = false;
	
	public abstract void sendAction(ActionEvent e) throws  UnknownHostException, IOException, FromServerException,
				inputException, JSONException, CreateCustomerException;
	
	@Override
	public final void actionPerformed(ActionEvent e){
		isSucceed = false;
		try{
			sendAction(e);
			isSucceed = true;
		}
		catch(inputException inputex){
			Popups.ShowWarning(inputex.getMessage());
		}
		
		catch(FromServerException fse){
			fse.ShowPopup();
		}
		catch (IOException ioex){
			Popups.ServerConnectionError(ioex.getMessage());
		}
		catch(Exception ex){
			Popups.ShowError(ex.getMessage());
		}
	}
	
	public boolean isSucceed(){
		return isSucceed;
	}
}//end of SendingActionListener
