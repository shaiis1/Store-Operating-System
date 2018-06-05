package ServerFunctions;

import javax.swing.JOptionPane;
import NetworkMessage.ErrorMessage;

public class FromServerException extends Exception{

	private static final long serialVersionUID = 1L;
	ErrorMessage m_ErrorMessage;
	
	public FromServerException (ErrorMessage i_ErrorMessage){
		super(i_ErrorMessage.getData());
		m_ErrorMessage = i_ErrorMessage;
	}
	
	public void ShowPopup(){
		JOptionPane.showMessageDialog(null, m_ErrorMessage.getData(), m_ErrorMessage.getErrorType().name() + "Fault",
				JOptionPane.ERROR_MESSAGE);
	}
}//end of FromServerException