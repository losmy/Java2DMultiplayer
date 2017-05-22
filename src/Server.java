
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {

	//global variable
	static int tick = 5;
	//server Variables
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private int port = 19000;
	private boolean running = false;
	//client variables
	private ServerClientHandler clientH;
	private ServerGameLogic gameLogic;
	private ServerClient[] clientArray;
	
	//Server Settings
	private String gameMode = "teamdeathmatch";
	private int maxClients = 10;
	private int mapSizeX = 350;
	private int mapSizeY = 250;
	




	public Server()
	{
		System.out.println( "Start server on port: " + port );
	}

	public void startServer()
	{
		try
		{
			clientH = new ServerClientHandler(maxClients);
			clientH.start();
			gameLogic = new ServerGameLogic(clientH, new GameMode(maxClients, gameMode,mapSizeX,mapSizeY));
			
			
			serverSocket = new ServerSocket( port );
			this.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stopServer()
	{
		System.out.println("Closing Server: " + serverSocket.getLocalPort());
		running = false;
		this.interrupt();

	}

	@Override
	public void run()
	{
		running = true;
		while( running )
		{
			//skicka runt jävla array som egentligen borde finnas något sätt att ha globalt....
			clientArray = clientH.getClientArray();
			try
			{
				System.out.println( "Listening for a connection" );
				clientSocket = serverSocket.accept();
				// Call accept() to receive the next connection
				int i = 0;
				for (i = 0; i < maxClients; i++) 
				{
					if (clientH.getClient(i) == null) 
					{
						clientH.AddClient(new ServerClient(clientSocket, i));
						clientH.getClient(i).start();
						break;
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main( String[] args )
	{



		Server server = new Server();
		server.startServer();

		/*// Automatically shutdown in 1 minute
		try
		{
			Thread.sleep( 500000 );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		server.stopServer();*/
	}
}

