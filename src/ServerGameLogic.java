
public class ServerGameLogic extends Thread {

	private GameMode gm;
	private ServerClientHandler clientH;
	private ServerClient[] clientArray;
	public ServerGameLogic(ServerClientHandler clientH, GameMode gm)
	{
		this.clientH = clientH;
		this.gm = gm;
		
	}



public void run()
{
	while(true)
	{
		
		clientArray = clientH.getClientArray();
		
		//CheckCordinates();  //får ingå sen i cheat detection
		
		
		try {
			Thread.sleep(Server.tick);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	
	
	
	
	@SuppressWarnings("unused")
	private void CheckCordinates() {

		for(int i = 0; i < clientArray.length; i++)
		{
		
		if(clientArray[i].getX() > (gm.getMapInfo().getLength() - clientArray[i].getSize()))
		{
			clientArray[i].setX(0);
		}
		if( 0 > clientArray[i].getX())
		{
			clientArray[i].setX(gm.getMapInfo().getLength() - clientArray[i].getSize());
		}
		if(clientArray[i].getY() < 0)
		{
			clientArray[i].setY(gm.getMapInfo().getHeight() - clientArray[i].getSize());
		}
		if(clientArray[i].getY() > (gm.getMapInfo().getHeight()+clientArray[i].getSize()))
		{
			clientArray[i].setY(0);
		}
		System.out.println(clientArray[i].getX() + ":" + clientArray[i].getY());
		}
	}
}
