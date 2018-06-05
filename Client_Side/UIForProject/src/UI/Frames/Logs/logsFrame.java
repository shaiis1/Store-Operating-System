package UI.Frames.Logs;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Frames.AbstractFrames.allFramesBase;

@SuppressWarnings("serial")
public class logsFrame extends allFramesBase{
	
	private JPanel logsPanel;
	private JScrollPane logsScrollPane;
	private Vector<String> dataFromServer;
	
	public logsFrame(){
		super();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	@Override
	protected String getFrameTitle(){
		return "Logs";
	}

	@Override
	protected JPanel createMainPanel() throws UnknownHostException, IOException, FromServerException{
		JPanel myMainPanel = new JPanel();
		
		logsPanel = new JPanel();
		logsScrollPane = new JScrollPane(logsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logsScrollPane.setPreferredSize(new Dimension ((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.45),
				(int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.5)));
		setDataToPanel();
		myMainPanel.add(logsScrollPane);
		return myMainPanel;
	}
	
	private void setDataToPanel() throws UnknownHostException, IOException, FromServerException{
		StringBuilder labelString = new StringBuilder();
			labelString.append("<html>");
			dataFromServer = ServerFunctionExecuter.getInstance().GetAllLogs();
			for(String row : dataFromServer){
				labelString.append(String.format("%s<br>", row));
			}
			labelString.append("</html>");
			JLabel currentLabel = new JLabel(labelString.toString());
			logsPanel.setPreferredSize(new Dimension(currentLabel.getPreferredSize().width + 30,
					currentLabel.getPreferredSize().height + 30));
			logsPanel.add(currentLabel);
	}//end of setDataToPanel() method
}//end of logsFrame
