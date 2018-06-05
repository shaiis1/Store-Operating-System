package Server;

import Workers.Worker;

@SuppressWarnings("serial")
public class AllReadyConnectedException extends Exception
{
	public AllReadyConnectedException(Worker i_Worker)
	{
		super(String.format("The user %s is all ready connected!", i_Worker.toString()));
	}

}
