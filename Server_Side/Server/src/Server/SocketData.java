package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketData
{
	private Socket m_Socket = null;
	private DataInputStream  m_FromNetStream= null;
	private PrintStream m_ToNetStream = null;
	
	public SocketData (Socket i_Socket) throws UnknownHostException, IOException
	{
		m_Socket = i_Socket;
		m_FromNetStream= new DataInputStream(m_Socket.getInputStream());
		m_ToNetStream = new PrintStream(m_Socket.getOutputStream());
	}
	
	public void CloseConnection() throws IOException
	{
		m_Socket.close();
	}
	
	public Socket GetSocket()
	{
		return m_Socket;
	}
	
	public DataInputStream GetFromNetStream()
	{
		return m_FromNetStream;
	}
	
	public PrintStream GetToNetStream()
	{
		return m_ToNetStream;
	}
	
	public void SkipFromNetStream(int i_Skip) throws IOException
	{
		m_FromNetStream.skip(i_Skip);
	}
	
}
