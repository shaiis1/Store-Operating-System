package UI.Frames.AbstractFrames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import UI.Popups;
import UI.SendingActionListener;
import UI.Decorators.superButton;

@SuppressWarnings("serial")
public abstract class sendingFrame extends allFramesBase{
	
	protected superButton sendButton;
	
	public sendingFrame(){
		super();
		
		sendButton = new superButton(getSendButtonName(), Color.orange);
		addOnSendButtonClicked();
		
		super.getMainPanel().add(sendButton);
		this.setLocationRelativeTo(null);
	}
	
	protected abstract String getSendButtonName();
	protected abstract String getOnSuccessMessage();
	protected abstract SendingActionListener OnSendButtonClicked();
	
	private void disposeFrame(){
		this.dispose();
	}
	
	public void addOnSendButtonClicked(){
		
		sendButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				SendingActionListener fucntion = OnSendButtonClicked();
				fucntion.actionPerformed(null);
				if(fucntion.isSucceed()){
					Popups.ShowSuccess(getOnSuccessMessage());
					disposeFrame();
				}
			}
		});
	}
}//end of sendingFrame