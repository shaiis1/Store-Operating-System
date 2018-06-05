package UI.Frames.AddRemove;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;

import UI.Decorators.superButton;
import UI.Frames.AbstractFrames.ButtonMenuFrame;

@SuppressWarnings("serial")
public class addOrRemoveFrame extends ButtonMenuFrame{
	
	private superButton addButton;
	private superButton removeButton;
	
	public addOrRemoveFrame(){
		super();
	}
	
	public void addEvents(){
		addButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){
	    	  Thread addThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
	    				new RegisterFrame();
	    		  }	  
	    	  });
	    	  addThread.start();
	      }});
		
		removeButton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  Thread removeThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    				new removeWorkerFrame();
		    		  }	  
		    	  });
		    	  removeThread.start();
		      }});
	}

	@Override
	protected Vector<JButton> getMenuButton(){
		
		Vector<JButton> allButtons = new Vector<JButton>();
		
		addButton = new superButton("Add",Color.ORANGE);
		allButtons.add(addButton);
		removeButton = new superButton("Remove",Color.ORANGE);
		allButtons.add(removeButton);
		
		addEvents();
		return allButtons;
	}

	@Override
	protected String getFrameTitle(){
		return "Add or remove";
	}
}//end of addOrRemoveFrame