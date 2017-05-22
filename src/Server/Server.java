package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Server extends Thread {

	private ServerSocket serverSocket;
	private ClientHandler clientH;
	private int port = 19000;
	private boolean running = false;
	
	

	public Server()
	{
		System.out.println( "Start server on port: " + port );
	}

	public void startServer()
	{
		try
		{
			clientH = new ClientHandler();
			clientH.start();
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
		System.out.println("Closing Client: " + serverSocket.getLocalPort());
		running = false;
		this.interrupt();
		
	}

	@Override
	public void run()
	{
		running = true;
		while( running )
		{
			try
			{
				System.out.println( "Listening for a connection" );

				// Call accept() to receive the next connection
				Socket socket = serverSocket.accept();

				// Pass the socket to the RequestHandler thread for processing
				Client client = new Client(socket);
				clientH.AddClient(client);
				
				
				client.start();
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

		// Automatically shutdown in 1 minute
		try
		{
			Thread.sleep( 50000 );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		server.stopServer();
	}
}

class Client extends Thread
{
	private DataInputStream in;
	private Socket socket;
	private boolean connected = true;
	Client( Socket socket )
	{
		this.socket = socket;
	}
	// count the available bytes form the input stream

	public boolean isConnected()
	{
		return connected;
	}

	public void closeClient()
	{
		connected = false;
		this.interrupt();
	}

	@Override
	public void run()
	{
		try
		{   
			System.out.println( "Received a connection" );
			in = new DataInputStream(socket.getInputStream());
			Timestamp timestamp = null;
			
			byte[] bs = new byte[50];
			while(connected)
			{
				if(in.available() > 0)
				{
				
				in.read(bs);
				String s = new String(bs);
				System.out.println(s);
				timestamp = new Timestamp(System.currentTimeMillis());
				}
				else
				{
					if(System.currentTimeMillis() - timestamp.getTime() > 5000 && timestamp != null)
					{
						System.out.println("Timeout after 5000ms");
						connected = false;
						
					}
				}
				
				
			}




		}
		catch( Exception e )
		{ 
			System.out.println( "Connection closed" );
			e.printStackTrace();
		}
	}
}
