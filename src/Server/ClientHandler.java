package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientHandler extends Thread{

	private boolean running;
	private ArrayList<Client> clientList = new ArrayList<Client>();
	
	public ClientHandler()
	{
	}
	@Override
	public void run()
	{
		running = true;
		while(running)
		{
		
			if(clientList.isEmpty() == false)
			{
				TimeoutCheck();
			}
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void TimeoutCheck() 
	{
		ArrayList<Integer> clientsToRemove = new ArrayList<Integer>();
		for(int i = 0; clientList.size() > i; i++)
		{
			if(clientList.get(i).isConnected() == false)
			{		
				clientsToRemove.add(i);
				
			}
		}
		if(clientsToRemove.isEmpty() == false)
		{
			RemoveClient(clientsToRemove);
		}
		
	}
	
	public void AddClient(Client client)
	{
		clientList.add(client);
		System.out.println("added client. ClientList Size: " + clientList.size());
	}

	private void RemoveClient(ArrayList<Integer> index)
	{
		Iterator<Integer> i = index.iterator();
		while(i.hasNext())
			{
			int s = i.next();
			clientList.get(s).isConnected();
			clientList.remove(s);
			System.out.println("dropping client " + index.get(s) + " clientlist size = " + clientList.size());
			}
	
	}
}
