package UI.Frames.Statistics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;

import UI.Decorators.superButton;
import UI.Frames.AbstractFrames.ButtonMenuFrame;

@SuppressWarnings("serial")
public class StoreStatisticsFrame extends ButtonMenuFrame{

	private superButton byBranch;
	private superButton byProduct;
	private superButton byCategory;
	private superButton showVIP;
		
	public StoreStatisticsFrame(){
		super();
	}
	
	@Override
	protected String getFrameTitle(){
		return "Store Statistics";
	}	

	@Override
	protected Vector<JButton> getMenuButton(){
		Vector<JButton> allButtons = new Vector<JButton>();
		
		byBranch = new superButton("By branch",Color.ORANGE);
		allButtons.add(byBranch);
		byProduct = new superButton("By product",Color.ORANGE);
		allButtons.add(byProduct);
		byCategory = new superButton("By category",Color.ORANGE);
		allButtons.add(byCategory);
		showVIP = new superButton("Show VIP",Color.ORANGE);
		allButtons.add(showVIP);
		
		addEvents();
		
		return allButtons;
	}
	
	public void addEvents(){
		byCategory.addActionListener(new ActionListener(){
			
	      public void actionPerformed(ActionEvent e){
	    	  Thread byCategoryThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
	    				new byCategoryFrame();
	    		  }	  
	    	  });
	    	  byCategoryThread.start();
	      }});
		
		byProduct.addActionListener(new ActionListener(){
			
		      public void actionPerformed(ActionEvent e){
		    	  Thread byItemThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    				new byItemFrame();
		    		  }	  
		    	  });
		    	  byItemThread.start();
		      }});
		
		byBranch.addActionListener(new ActionListener(){
			
		      public void actionPerformed(ActionEvent e){
		    	  Thread byBranchThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    				new byBranchFrame();
		    		  }	  
		    	  });
		    	  byBranchThread.start();
		      }});
		
		showVIP.addActionListener(new ActionListener(){
			
		      public void actionPerformed(ActionEvent e){
		    	  Thread showVIPThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    				new AllVipCustomersFrame();
		    		  }	  
		    	  });
		    	  showVIPThread.start();
		      }});
	}
}//end of StoreStatisticsFrame