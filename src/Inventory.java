
public class Inventory {
	int size;
	Item[] itemArray;


	public Inventory(int size)
	{ 
		itemArray = new Item[size];
		this.size = size;
	}

	public void AddItem(Item item)
	{
		for(int i = 0; i < size; i++)
		{
			if(itemArray[i] != null)
			{
				itemArray[i] = item;
			}
		}
	}


}
