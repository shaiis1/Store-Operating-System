package UI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UI.Decorators.superButton;
import UI.Frames.Purchase.discountFrame;
import UI.Frames.Purchase.purchaseFrame;

@SuppressWarnings("serial")
public class CashierPanel extends WorkerPanel{
	
	private superButton changePurchseDiscountButton;
	private superButton updatePurchaseButton;

	public CashierPanel(int panelWidth,int panelHeight){
		super(panelWidth,panelHeight);
	}
	
	@Override
	protected void createComponents(){
		super.createComponents();
		changePurchseDiscountButton = new superButton("New discount",c_Orange);
		updatePurchaseButton = new superButton("New purchase",c_Orange);
	}
	
	@Override
	protected void attachToPanels(){
		super.attachToPanels();
		rightPanel.add(changePurchseDiscountButton);
		rightPanel.add(updatePurchaseButton);
	}
	
	@Override
	protected void addEvents(){
		super.addEvents();
		addOnChangePurchaseDiscountButtonClicked();
	}
	
	private void addOnChangePurchaseDiscountButtonClicked(){
		changePurchseDiscountButton.addActionListener(new ActionListener(){
			
	      public void actionPerformed(ActionEvent e){
	    	  
	    	  Thread discountThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
	    				new discountFrame();
	    		  }	  
	    	  });
	    	  discountThread.start();
	      }
	    });
		
		updatePurchaseButton.addActionListener(new ActionListener(){
			
	      public void actionPerformed(ActionEvent e){
	    	  
	    	  Thread purchaseThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
	    					new purchaseFrame();
	    		  }	  
	    	  });
	    	  purchaseThread.start();
	      }
	    });
	}
}//end of CashierPanel
