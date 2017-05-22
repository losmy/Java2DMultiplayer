
public class ClientPlayer {
	private int x,y,id;
	int size = 10;

	
	public ClientPlayer(int id, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.id = id;
	}
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getID()
	{
		return this.id;
	}
	
	public void UpdatePos(int x, int y)
	{
		this.x = x;
		this.y = y;
		System.out.println(x+":" + y);
	}
	
	
	
}
