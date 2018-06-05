package UI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UI.Decorators.superButton;
import UI.Frames.AddRemove.addNewCustomerFrame;
import UI.Frames.AddRemove.addNewWorkerFrame;
import UI.Frames.AddRemove.removeWorkerFrame;
import UI.Frames.All.AllEmployeesFrame;
import UI.Frames.Invetory.addToInventoryFrame;
import UI.Frames.Logs.logsFrame;
import UI.Frames.Statistics.StoreStatisticsFrame;

@SuppressWarnings("serial")
public class ManagerPanel extends WorkerPanel{

	private superButton storeStatisticsButton;
	private superButton allEmployeesButton;
	private superButton addToInventoryButton;
	private superButton newLogsButton;
	private superButton addNewWorkerButton;
	private superButton addNewCustomerButton;
	private superButton removeWorkerButton;
	
	public ManagerPanel(int panelWidth,int panelHeight){
		super(panelWidth,panelHeight);
	}
	
	@Override
	protected void createComponents(){
		super.createComponents();
		
		storeStatisticsButton = new superButton("Store statistics",c_Orange); //frame with information about all statistics
		allEmployeesButton = new superButton("Employees",c_Orange); //frame with information about all workers
		addToInventoryButton = new superButton("Order items", c_Orange);
		newLogsButton = new superButton("Logs",c_Orange);
		addNewWorkerButton = new superButton("New worker", c_Orange);
		addNewCustomerButton = new superButton("New customer", c_Orange);
		removeWorkerButton = new superButton("Remove worker", c_Orange);
	}
	
	@Override
	protected void attachToPanels(){
		super.attachToPanels();
		
		rightPanel.add(storeStatisticsButton);
		rightPanel.add(allEmployeesButton);
		rightPanel.add(addNewCustomerButton);
		rightPanel.add(addNewWorkerButton);
		rightPanel.add(removeWorkerButton);
		rightPanel.add(addToInventoryButton);
		rightPanel.add(newLogsButton);
	}
	
	@Override
	protected void addEvents(){
		super.addEvents();

		addOnAllEmployeesButtonClicked();
		addStoreStatisticsButtonClicked();
		addOnAddToInventoryButtonClicked();
		addOnLogsButtonClicked();
		addOnAddNewWorkerButtonClicked();
		addOnAddNewCustomerButtonClicked();
		OnRemoveWorkerButtonClicked();
	}
	
	private void addOnAllEmployeesButtonClicked(){
		allEmployeesButton.addActionListener(new ActionListener(){
			
	      public void actionPerformed(ActionEvent e){
	    	  
	    	  Thread employeesThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
	    				new AllEmployeesFrame();
	    		  }	  
	    	  });
	    	  employeesThread.start();
	      }
	    });
	}

	private void addStoreStatisticsButtonClicked(){
		
		storeStatisticsButton.addActionListener(new ActionListener(){
			
	      public void actionPerformed(ActionEvent e){
	    	  
	    	  Thread inventoryThread = new Thread(new Runnable(){
	    		  @Override
	    		  public void run(){
							new StoreStatisticsFrame();
	    		  }	  
	    	  });
	    	  inventoryThread.start();
	      }
	    });
	}
	
	private void addOnAddToInventoryButtonClicked(){
		
		addToInventoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				
				 Thread addToInventoryThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    			  new addToInventoryFrame();
		    		  }	  
		    	  });
				 addToInventoryThread.start();
			}
		});
	}
	
	private void addOnLogsButtonClicked(){
		newLogsButton.addActionListener(new ActionListener(){
			
		      public void actionPerformed(ActionEvent e){
		    	  
		    	  Thread logsThread = new Thread(new Runnable(){
		    		  @Override
		    		  public void run(){
		    					new logsFrame();
		    		  }	 
		    	  }); 
		    	  logsThread.start();
		      } 
		    });	
	}
	
	private void addOnAddNewWorkerButtonClicked(){
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
	}
		
	private void addOnAddNewCustomerButtonClicked(){
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
	
	private void OnRemoveWorkerButtonClicked(){
		removeWorkerButton.addActionListener(new ActionListener(){
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
}//end of ManagerPanel
