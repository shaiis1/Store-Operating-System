package UI.Frames.AddRemove;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;

import UI.Decorators.superButton;
import UI.Frames.AbstractFrames.ButtonMenuFrame;

@SuppressWarnings("serial")
public class RegisterFrame extends ButtonMenuFrame{
	
	private superButton addNewWorkerButton;
	private superButton addNewCustomerButton;
	
	public RegisterFrame(){
		super();
	}
	
	public void addEvents(){
		//addNewWorkerButton On Click
		addNewWorkerButton.addActionListener(new ActionListener(){
			
	      public void actionPerformed(ActionEvent e){
	    	  
	    	  Thread newWorkerThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
	    				new addNewWorkerFrame();
	    		  }	  
	    	  });
	    	  newWorkerThread.start();
	      }});
		
		//addNewCustomerButton On Click
		addNewCustomerButton.addActionListener(new ActionListener(){
			
		      public void actionPerformed(ActionEvent e){
		    	  
		    	  Thread newCustomerThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    				new addNewCustomerFrame();
		    		  }	  
		    	  });
		    	  newCustomerThread.start();
		      }});
	}

	@Override
	protected Vector<JButton> getMenuButton(){
		
		Vector<JButton> allButtons = new Vector<JButton>();

		addNewWorkerButton = new superButton("New worker",Color.ORANGE);
		allButtons.add(addNewWorkerButton);
		addNewCustomerButton = new superButton("New customer",Color.ORANGE);
		allButtons.add(addNewCustomerButton);

		addEvents();
		return allButtons;
	}

	@Override
	protected String getFrameTitle() {
		return "New Register";
	}
}//end of RegisterFrame