import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ClientMultiplayerHandler extends Thread
{
	//connection variables
	private int port = 19000;
	private String ipAdress = "127.0.0.1";	
	
	Socket socket;
	Send send;
	Recieve recieve;
	private boolean connected;
	//Client
	private ClientPlayer[] playerArray;

	//server variables
	private int maxClients;




	public ClientMultiplayerHandler( int maxClients)
	{
		this.maxClients = maxClients;
		try {
			playerArray = new ClientPlayer[maxClients];
			socket = new Socket(ipAdress, port);
			connected = true;
			send = new Send(socket);
			recieve = new Recieve(socket);
			recieve.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	public ClientPlayer getStartInfo()
	{
		return recieve.getStartInfo();
	}

	public ClientPlayer[] UpdatePlayerList()
	{

		return playerArray;
	}

	public void run()
	{
		while(connected && recieve.gotStartInfo == true)
		{	
			playerArray = recieve.getPlayerArray();
			for(int i = 0; i < maxClients; i++)
			{
				System.out.println(playerArray[i]);
			}
			String message = new String(playerArray[recieve.id].getX()+","+playerArray[recieve.id].getY() + ",");
			if(!send.SendMessage(message))
			{
				System.out.println("Error, could not send message: " + message);
				connected = false;

			}
			try {
				Thread.sleep(ClientUpdate.tick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		send.interrupt();
		recieve.interrupt();

	}



}

class Send extends Thread
{
	private DataOutputStream out;

	public Send(Socket socket) 
	{
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean SendMessage(String message)
	{
		try {				
			byte[] msg =  new String(message).getBytes();

			out.write(msg);
			out.flush();	
			return true;

		}			
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
class Recieve extends Thread
{
	DataInputStream in;
	private ClientPlayer[] playerArray;
	private int maxClients = 10;

	Timestamp timestamp;
	Long timeSinceRecived;
	boolean receiving = false;
	boolean connected = true;
	boolean gotStartInfo = false;

	//player start info
	int id,x,y;


	public Recieve(Socket socket)
	{
		try {
			playerArray = new ClientPlayer[maxClients]; //
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ClientPlayer getStartInfo() {

		byte[] bs = new byte[50];				
		try {
			in.read(bs);
			String msg = new String(bs);
			String serverMsg[] = msg.split("\\;");
			String playerInfo[] = serverMsg[0].split("\\,");
			id = Integer.parseInt(playerInfo[0]);
			x = Integer.parseInt(playerInfo[1]);
			y = Integer.parseInt(playerInfo[2]);
			System.out.println(id);
			System.out.println(playerArray.length);
			System.out.println(x);
			if(playerArray[Integer.parseInt(playerInfo[0])] == null)
			{
				
				
			}
			gotStartInfo = true;
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ClientPlayer(id,x,y);
	}

	public ClientPlayer[] getPlayerArray()
	{
		return playerArray;
	}
	public void run()
	{
		try
		{


			while(connected)
			{
				byte[] bs = new byte[50];				
				in.read(bs);
				String tempMsg = new String(bs);
				String msg[] = tempMsg.split("\\;");
				for(int i = 0; i < (msg.length-1); i++)
				{

					if(msg[i].isEmpty() != true)
					{
						String playerMsg[] = msg[i].split("\\,");
						if(playerArray[Integer.parseInt(playerMsg[0])] == null)
						{
							playerArray[Integer.parseInt(playerMsg[0])] = new ClientPlayer(Integer.parseInt(playerMsg[0]),Integer.parseInt(playerMsg[1]), Integer.parseInt(playerMsg[2]));
						}
						else
						{
							if(Integer.parseInt(playerMsg[0]) == -1) //ifall den skickar -1 som id så är x = id clienten som har tappats bort
							{
								playerArray[Integer.parseInt(playerMsg[1])] = null;
							}
							else
							{
								playerArray[Integer.parseInt(playerMsg[0])].setId(Integer.parseInt(playerMsg[0]));
								playerArray[Integer.parseInt(playerMsg[0])].setX(Integer.parseInt(playerMsg[1]));
								playerArray[Integer.parseInt(playerMsg[0])].setY(Integer.parseInt(playerMsg[2]));
							}

						}

					}
				}

				timestamp = new Timestamp(System.currentTimeMillis());
				timeSinceRecived = timestamp.getTime();

			}
		}
		catch( Exception e )
		{ 
			System.out.println( "Connection closed" );
			e.printStackTrace();
			connected = false;
		}

	}

}





