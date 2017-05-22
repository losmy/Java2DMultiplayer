
public class GameMap {

	private int height = 250;
	private int length = 350;
	
	public GameMap(int length, int height)
	{
		this.length = length;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public int getLength() {
		return length;
	}
}
