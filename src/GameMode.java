
public class GameMode {
	private GameMap gmap;
	private int maxPlayer;
	private String gameMode;
	private int mapSizeX, mapSizeY;
	
	public GameMode(int maxPlayer, String gameMode, int mapSizeX, int mapSizeY)
	{
		this.maxPlayer = maxPlayer;
		this.gameMode = gameMode;
		this.gmap = new GameMap(mapSizeX, mapSizeY);
	}
	
	public GameMap getMapInfo()
	{
		return gmap;
	}

}
