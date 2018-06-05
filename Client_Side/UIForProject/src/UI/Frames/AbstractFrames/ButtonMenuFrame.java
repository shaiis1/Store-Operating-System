package UI.Frames.AbstractFrames;

import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class ButtonMenuFrame extends allFramesBase{
	
	public ButtonMenuFrame(){
		super();
		createMainPanel();
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	protected abstract Vector<JButton> getMenuButton();
	
	@Override
	protected final JPanel createMainPanel(){
		JPanel myMainPanel = new JPanel();
		
		for(JButton button : getMenuButton()){
			myMainPanel.add(button);
		}
		return myMainPanel;
	}
}//end of ButtonMenuFrame
