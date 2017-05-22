
public class ClientPlayerHandler extends Thread{
	ClientPlayer player;
	ClientDrawGraphics graphics;
	public ClientPlayerHandler(ClientPlayer player, ClientDrawGraphics graphics)
	{
		this.player = player;
		this.graphics = graphics;
	}
	
	public void run()
	{
		while(true)
		{
			
			CheckCordinates();
		}
	}

	private void CheckCordinates() {
		
		
		if(player.getX() > (graphics.getScreenX() - player.getSize()))
		{
			player.setX(0);
		}
		if( 0 > player.getX())
		{
			player.setX(graphics.getScreenX() - player.getSize());
		}
		if(player.getY() < 0)
		{
			player.setY(graphics.getScreenY() - player.getSize());
		}
		if(player.getY() > (graphics.getScreenY()+player.getSize()))
		{
			player.setY(0);
		}
	
	}
}
