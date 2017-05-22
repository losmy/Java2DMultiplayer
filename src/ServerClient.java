import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Random;

class ServerClient extends Thread
{
	//Connection variables
	private Socket socket;
	private boolean connected = true;

	//timeOut Variables
	public boolean notRecieved = false;
	private Timestamp timestamp = null;
	private long timeSinceRecived = 0;

	//recieve variables
	private DataOutputStream out;
	byte[] recivMsg = new byte[50];


	//send Variables
	private DataInputStream in;

	//player variables
	int startHp = 100;
	int startSpeed = 1;
	int startAmmo = 50;
	int x,y,size, ammo, hp, speed;



	final int id;
	private ServerClient[] clientArray;


	ServerClient( Socket socket, int id )
	{
		Random rand = new Random();
		this.socket = socket;
		this.id = id;
		this.x = rand.nextInt(350) + 1;
		this.y = rand.nextInt(250) + 1;
		this.hp = startHp;
		this.ammo = startAmmo;
		this.speed = startSpeed;
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
	public long timeSinceRecieve()
	{
		return timeSinceRecived;
	}

	public void UpdateClientList(ServerClient[] clientArray)
	{
		this.clientArray = clientArray;		
	}
	//region getters and setters
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getID()
	{
		return this.id;
	}	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}	
	public int getAmmo() {
		return ammo;
	}
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	//endregion	
	@Override
	public void run()
	{
		try
		{   
			Setup();
			while(connected)
			{
				SendInfo(GetClientArrayMessage());
				RecieveInfo();
				
			}
		}
		catch( Exception e )
		{ 
			System.out.println( "Connection closed" );
			e.printStackTrace();
		}
	}

	private void Setup() 
	{
		try
		{  
			System.out.println( "Received a connection" );
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			timestamp = new Timestamp(System.currentTimeMillis());
			timeSinceRecived = timestamp.getTime();

			SendStartInfo(); 

		}
		catch( Exception e )
		{ 
			System.out.println( "Connection closed" );
			e.printStackTrace();
		}
	}
	private void SendStartInfo()
	{

		byte[] msg =  new String(id + "," + x + ","+ y + ";").getBytes();
		try {
			out.write(msg);
			System.out.println("Sent start message to player:" + id + " message:" + new String(msg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private String GetClientArrayMessage() 
	{
		StringBuilder sb = new StringBuilder();
		if(clientArray != null)
		{
			for(int i = 0; i < clientArray.length; i++)
			{
				if(clientArray[i] != null)
				{
					if(!clientArray[i].isConnected())
					{
						sb.append("-1");
						sb.append(",");
						sb.append(clientArray[i].getID());
						sb.append(",");
						sb.append("0");
						sb.append(";");
					}
					else
					{
						sb.append(clientArray[i].getID());
						sb.append(",");
						sb.append(clientArray[i].getX());
						sb.append(",");
						sb.append(clientArray[i].getY());
						sb.append(";");
					}

				}

			}

		}


		return sb.toString();
	}

	private void SendInfo(String message) 
	{
		if(connected)
		{
			try{
				byte[] msg =  new String(message).getBytes();
				out.write(msg);
				out.flush();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				connected = false;
			}
		}

	}

	private void RecieveInfo()
	{
		if(connected)
		{
			try
			{   				
				in.read(recivMsg);
				String tempMsg = new String(recivMsg);
				String msg[] = tempMsg.split("\\,");
				x = Integer.parseInt(msg[0]);
				y = Integer.parseInt(msg[1]);
				timestamp = new Timestamp(System.currentTimeMillis());
				timeSinceRecived = timestamp.getTime();
			}
			catch( Exception e )
			{ 
				System.out.println( "Connection closed" );
				connected = false;
				e.printStackTrace();
			}
		}
	}
}


