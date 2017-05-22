

import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerClientHandler extends Thread{

	private boolean running;
	static ServerClient[] clientArray;
	private Timestamp timestamp;
	private int tick = 5;
	




	public ServerClientHandler(int maxClients)
	{

		clientArray = new ServerClient[maxClients];
		
	}
	@Override
	public void run()
	{
		running = true;
		while(running)
		{


			ClientUpdate();
			TimeoutCheck();

			try {
				this.sleep(Server.tick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	private void ClientUpdate() {
		for(int i = 0; i < clientArray.length; i++)
		{
			if(clientArray[i] == null)
			{
				
			}
			else
			{
				clientArray[i].UpdateClientList(clientArray);
				if(	clientArray[i].x > 100) //lägg in client logik här
				{
					
				}
			}
			
		}
	}
	public ServerClient getClient(int index)
	{
		return clientArray[index];
	}
	public ServerClient[] getClientArray()
	{
		return clientArray;
	}
	private void TimeoutCheck() 
	{

		for(int i = 0; clientArray.length > i; i++)
		{
			if(clientArray[i] == null)
			{

			}
			else
			{
				timestamp = new Timestamp(System.currentTimeMillis());
				if( clientArray[i].isConnected() == false || (timestamp.getTime() - clientArray[i].timeSinceRecieve()) > 50000) 
				{	
					System.out.println("Removing client: " + i);
					RemoveClient(i);
				}
			}


		}


	}

	public void AddClient(ServerClient client)
	{
		for(int i = 0; i < clientArray.length; i++)
		{
			if(clientArray[i] == null)
			{
				clientArray[i] = client;
				System.out.println("Client Addad at index: " + i);
				break;
			}
		}


	}

	private void RemoveClient(int index)
	{
		clientArray[index].closeClient();
		clientArray[index] = null;
	}

}
