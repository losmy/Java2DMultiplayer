



public class ClientUpdate extends Thread{
	//global variables
	static int tick = 5;
	//Thread
	boolean running = true;
	//multiplayer 
	ClientMultiplayerHandler multiplayerH;
	static ClientPlayer[] playerArray = new ClientPlayer[10];
	private int maxClients = 10;
	String ipAdress = "127.0.0.1";

	//Graphics
	private ClientDrawGraphics graphics;
	//Client
	 ClientPlayer player;
	ClientPlayerHandler playerH;
	private ClientInputHandler inputH;
	boolean left,right,up,down;

	//Game Variables
	private int speed = 1;


	public ClientUpdate()
	{
		multiplayerH = new ClientMultiplayerHandler(maxClients);
		player = multiplayerH.getStartInfo();
		inputH = new ClientInputHandler();
		graphics = new ClientDrawGraphics(inputH,maxClients);

		playerH = new ClientPlayerHandler(player, graphics);




		ClientUpdate.playerArray[player.getID()] = player;
		Start();

		//this.playerList.add(new Player(20,20,10));
	}
	private void Start()
	{
		multiplayerH.start();
		playerH.start();


		this.start();
	}


	@Override
	public void run()
	{
		while(true)
		{
			//multiplayer
			ClientUpdate.playerArray = multiplayerH.UpdatePlayerList();

			//Graphics
			graphics.Repaint();


			//Player Movements
			getPlayerInputs();
			playerMovement(left,right,up,down);
			try {
				Thread.sleep(tick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}




	private void playerMovement(boolean left, boolean right, boolean up, boolean down) {
		if(left)
		{
			player.UpdatePos((player.getX() - speed), (player.getY()));
		}
		if(right)
		{
			player.UpdatePos((player.getX()+speed), (player.getY()));
		}
		if(up)
		{
			player.UpdatePos((player.getX()), (player.getY()-speed));
		}
		if(down)
		{
			player.UpdatePos((player.getX()), (player.getY()+speed));
		}

	}
	private void getPlayerInputs()
	{
		left = inputH.isLeft();
		right = inputH.isRight();
		up = inputH.isUp();
		down = inputH.isDown();

	}





}
