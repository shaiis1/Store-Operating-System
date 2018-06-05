package ProgramMain;

import java.io.IOException;

import Server.Server;

public class Program {

	public static void main(String[] args) throws IOException
	{	
		Server theServer = new Server();
		theServer.StartServer();
	}
}
