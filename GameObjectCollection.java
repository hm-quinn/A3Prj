package com.mycompany.a3;
import java.util.ArrayList;

public class GameObjectCollection implements ICollection{
	private ArrayList<GameObject> gameObj;
	
	public GameObjectCollection() {
		gameObj = new ArrayList<GameObject>();
	}
	
	@Override
	public void add(GameObject object) {
		// TODO Auto-generated method stub
		gameObj.add(object);
	}

	@Override
	public  IIterator getIterator() {
		// TODO Auto-generated method stub
		return new ObjectIterator();
	}

	@Override
	public void remove(GameObject object) {
		// TODO Auto-generated method stub
		gameObj.remove(object);
	}
	
	@Override
	public int getSize() {
		return gameObj.size();
	}
	
	public void clear() {
		// TODO Auto-generated method stub
		gameObj.clear();
	}
	
	private class ObjectIterator implements IIterator {
		private int index;
		
		public ObjectIterator() {
			index = -1;
		}
		
		@Override
		public boolean hasNext() 
		{
			if (gameObj.size() <= 0) 
				return false;
			if (index == gameObj.size() - 1) 
			{
				return false;
			}
			return true;
		}

		@Override
		public void remove() 
		{
			gameObj.remove(index);
			index--;
		}

		@Override
		public GameObject getNext() {
			// TODO Auto-generated method stub
			index++;
			return gameObj.get(index);
		}

	}


}
