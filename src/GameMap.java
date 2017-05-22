
public class GameMap {

	private int height = 250;
	private int length = 350;
	private int dropRate;
	
	public GameMap(int length, int height, int dropRate)
	{
		this.length = length;
		this.height = height;
		this.dropRate = dropRate;
	}

	public int getHeight() {
		return height;
	}

	public int getLength() {
		return length;
	}
}
