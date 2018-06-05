package UI.Frames;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JPanel;

import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import UI.Frames.AbstractFrames.allFramesBase;
import UI.Panels.panelsManager;

@SuppressWarnings("serial")
public class MainFrame extends allFramesBase{
	private int frameWidth;
	private int frameHeight;
	
	public MainFrame(){
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addEvents();
	}
	
	private void addEvents(){	
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				try{
					if(ServerFunctionExecuter.getInstance().GetLoggedInUser()!=null){
						ServerFunctionExecuter.getInstance().SignOut();
					}
				}
				catch (FromServerException fse){
					fse.ShowPopup();
				}
				catch (IOException ioex){
					Popups.ServerConnectionError(ioex.getMessage());
				}
			}
		});
	}

	@Override
	protected String getFrameTitle(){
		return "Store operating system";
	}

	@Override
	protected int getFrameWidth(){
		frameWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.45);
		return frameWidth;
	}

	@Override
	protected int getFrameHeight(){
		frameHeight = (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.8);
		return frameHeight;
	}

	@Override
	protected JPanel createMainPanel(){
		return new panelsManager(frameWidth,frameHeight);
	}
}//end of MainFrame
