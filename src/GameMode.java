
public class GameMode {
	private GameMap gmap;
	private int maxPlayer;
	private String gameMode;
	private int mapSizeX, mapSizeY;
	private int dropRate;
	
	public GameMode(int maxPlayer, String gameMode, int mapSizeX, int mapSizeY)
	{
		this.maxPlayer = maxPlayer;
		this.gameMode = gameMode;
		this.gmap = new GameMap(mapSizeX, mapSizeY,dropRate);
	}
	
	public GameMap getMapInfo()
	{
		return gmap;
	}

}
