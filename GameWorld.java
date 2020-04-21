package com.mycompany.a3;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.util.MathUtil;
import com.codename1.ui.geom.Point;

public class GameWorld extends Observable implements IGameWorld {
	public Random rand;
	private GameObjectCollection object;
	private int clock;
	private int live = 3;
	private static int height = 1000;
	private static int width = 1000;
	private boolean sound = false;
	public static final int MAX_BASE = 4;
	public static final int MAX_NPC = 3;
	public static final int MAX_ES = 2;
	public static final int MAX_DRONE = 2;
	private int newSpeed;
	private BGSound bg;
	private Sound base;
	private Sound drone;
	private Sound cyborg;
	private Sound lose;
	private Sound energy;
	private boolean pause = false;
	private boolean rePos = false;

	
	public void setGameWidth(int w) {
		this.width = w;
	}
	
	public void setGameHeight(int h) {
		this.height = h;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getWidth() {
		return width;
	}
	
	@Override
	public GameObjectCollection getCollection() {
		// TODO Auto-generated method stub
		return this.object;
	}
	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return this.clock;
	}
	@Override
	public int getLive() {
		// TODO Auto-generated method stub
		return this.live;
	}
	@Override
	public boolean getSound() {
		// TODO Auto-generated method stub
		return this.sound;
	}
	@Override
	public int getBase() {
		// TODO Auto-generated method stub
		PlayerCyborg player = findPlayer();
		if(player == null) 
			throw new RuntimeException("Player is not found.");
		else
			return player.getBaseReach();
	}
	@Override
	public int getEnergy() {
		// TODO Auto-generated method stub
		PlayerCyborg player = findPlayer();
		if(player == null) 
			throw new RuntimeException("Player is not found.");
		else
			return player.getEnergyLevel();
	}
	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		PlayerCyborg player = findPlayer();
		if(player == null) 
			throw new RuntimeException("Player is not found.");
		else
			return player.getDamageLevel();
	}
	
	public void init() {
		bg = new BGSound("bg.wav");
		base = new Sound("base.wav");
		drone = new Sound("drone.wav");
		energy = new Sound("energy.wav");
		cyborg = new Sound("cyborg.wav");
		lose = new Sound("lose.wav");
		this.clock = 0;
		if(getSound()) 
			createSound('m');
		object = new GameObjectCollection();
		//add a player
		PlayerCyborg player = PlayerCyborg.getPlayer(this);
		object.add(player);
		
		//add 3 NPCs
		for (int i = 1; i <= 3; i++) {
			NonPlayerCyborg npc = new NonPlayerCyborg(i,this);
			object.add(npc);
		}
		
		//add bases
		for(int i = 1; i <= MAX_BASE; i++) {
			Base base = new Base(i, this, MAX_BASE);
			object.add(base);
		}
		
		//add energy station
		for(int i = 1; i <= MAX_ES; i++) {
			addEnergyStation();
		}
		
		//add drone
		for(int i = 1; i <= MAX_DRONE; i++) {
			Drone drone = new Drone(this);
			object.add(drone);
		}
		
		update();
	}
	
	public void addEnergyStation() {
		EnergyStation es = new EnergyStation(this);
		object.add(es);
	}
	
	public void changeSpeed(char x) {
		PlayerCyborg player = findPlayer();
		if(player != null) {
			if(x == 'a') {
				if (player.getSpeed() > player.getMaxSpeed())
					System.out.println("Error! PlayerCyborg's current speed cannot exceed maximum speed.");
				else if (player.getDamageLevel() == player.getMaxDamageLevel()) {
					//System.out.println("PlayerCyborg cannot move as it is at its max damage level.");
					System.out.println("PlayerCyborg has lost a live due to having reached maximum damage level.");
					resetCyborg();
				}
				else if(player.getEnergyLevel() == 0) {
					//System.out.println("PlayerCyborg cannot move due to having no energy.");
					System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
					resetCyborg();
				}
				else {
					System.out.println("Accelerate the speed of PlayerCyborg.");
					/*setting speed according to damage level
					 * damage percent = (max damage - damage level) / max damage
					 * speed = max speed - percent * max speed 
					 */
					double percent = (player.getMaxDamageLevel() - player.getDamageLevel()) / (player.getMaxDamageLevel());
					this.newSpeed = (int) (player.getMaxSpeed() - (player.getMaxSpeed() - (percent * player.getMaxSpeed())));
					player.setSpeed(this.newSpeed);
					if(this.newSpeed > player.getMaxSpeed()) 
						player.setSpeed(player.getMaxSpeed());
				}
			
			}	
			if(x == 'b') {
				if (player.getSpeed() <= 0)
					System.out.println("PlayerCyborg is current not moving.");
				else { 
					System.out.println("Brake is applied.");
					player.setSpeed(-1);
				}
	
			}
			//Reduce speed due to collision
			if(x == 'c') {
				System.out.println("PlayerCyborg's speed is reduced due to a collision.");
				if (player.getSpeed() >= 2) {
					player.setSpeed(-2);
				}
				else {
					player.setSpeed(0);
				}
			}
		}
		update();
	}
	
	public void CBSteer(char x) {
		//steer left
		PlayerCyborg player = findPlayer();
		if (x == 'l') {
			System.out.println("Steer left by 5 degrees.");
			player.setSteerDir('l');
		}
		//steer right
		else {
			System.out.println("Steer right by 5 degrees.");
			player.setSteerDir('r');
		}
		update();
	}

//	public void CBCollision() {
//		System.out.println("Collided with another PlayerCyborg.");
//		PlayerCyborg player = findPlayer();
//		NonPlayerCyborg npc = findCBCollisionStrategy();
//		npc.setEnergyLevel(-20);
//		if(npc.getEnergyLevel() <= 0)
//			npc.resetEnergy();
//		player.setDamageLevel(5);
//		player.setEnergyLevel(-20);
//		if(player.getEnergyLevel() <= 0) {
//			player.setEnergyLevel(0);
//			System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
//			resetCyborg();
//		
//		}
//		else if( player.getDamageLevel() >= player.getMaxDamageLevel()) {
//			player.setDamageLevel(player.getMaxDamageLevel());
//			System.out.println("PlayerCyborg has lost a live due to having reached maximum damage level.");
//			resetCyborg();
//		}
//		else {
//			player.setColor(ColorUtil.rgb(255, player.getDamageLevel() * 10, player.getDamageLevel() * 10));
//			changeSpeed('c');
//			
//		}
//		update();
//	}
//
//	public void DCollision() {
//		Drone drone = findDrone();
//		System.out.println("Collison with Drone.");
//		//same as colliding with another PlayerCyborg, except half the damage
//		PlayerCyborg player = findPlayer();
//		player.setDamageLevel(2);
//		player.setEnergyLevel(-10);
//		if(player.getEnergyLevel() <= 0) {
//			player.setEnergyLevel(0);
//			System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
//			resetCyborg();
//			
//			
//		}
//		else if( player.getDamageLevel() == player.getMaxDamageLevel()) {
//			player.setDamageLevel(player.getMaxDamageLevel());
//			System.out.println("PlayerCyborg has lost a live due to having reached maximum damage level.");
//			resetCyborg();
//			
//		}
//		else {
//			player.setColor(ColorUtil.rgb(255, player.getDamageLevel() * 10, player.getDamageLevel() * 10));
//			changeSpeed('c');
//			
//		}
//		update();
//	}
//
//	public void ESCollision() {
//		EnergyStation energyStation = findStation();
//		System.out.println("Collision with an energy station.");
//		PlayerCyborg player = findPlayer();
//		player.setEnergyLevel(energyStation.getCapacity());
//		energyStation.setCapacity();
//		energyStation.setColor(ColorUtil.rgb(153,255,51));
//		object.remove(energyStation);
//		addEnergyStation();
//		update();
//	}
//	
//
//	public void BCollision(int i) {
//		Base base = findBase(i);
//		PlayerCyborg player = findPlayer();
//		if((base.getId() - player.getBaseReach()) == 1) {
//			//reach the last base (4)
//			if(base.getId() == MAX_BASE) {
//				System.out.println("PlayerCyborg has successfully reached the last base.");
//				player.setBaseReach(i);
//				player.setLocation(base.getLocation());
//				update();
//				gameOver('w');
//			}
//			//reach the next base
//			else {
//				System.out.println("PlayerCyborg has successfully reached the next base.");
//				player.setBaseReach(i);
//				player.setLocation(base.getLocation());
//				update();
//			}
//		}
//	}
	
	//Change Strategies
	public void changeStrategy() {
		//int i = -1;
		IIterator iterator = object.getIterator();
		while(iterator.hasNext()) {
			//i++;
			GameObject temp = iterator.getNext();
			if(temp instanceof NonPlayerCyborg) {
				NonPlayerCyborg npc = (NonPlayerCyborg)temp;
				if(npc.getStrategy() instanceof CyborgCollisionStrategy)
					npc.setStrategy(new BaseCollisionStrategy());
				else
					npc.setStrategy(new CyborgCollisionStrategy());
				npc.setBaseReach((npc.getBaseReach()) + 1); //NPC move to next base
				update();
			}
		}
		//iterator.resetIndex();
	}
	
	public void soundToggle(boolean sound) {
		this.sound = sound;
		if(getSound())
			createSound('m');
		else
			createSound('p');
		update();
	}

	public void clockTick() {
		System.out.println("Game clock has ticked.");
		//int i = -1;
		IIterator iterator = object.getIterator();
		while(iterator.hasNext()) {
			//i++;
			//Update Player Cyborg Location
				GameObject temp = iterator.getNext();
				if(temp instanceof PlayerCyborg) {
					//PlayerCyborg player = (PlayerCyborg)iterator.getNext();
					PlayerCyborg player = (PlayerCyborg)temp;
					if(player.getSpeed() != 0 || player.getEnergyLevel() != 0 || player.getDamageLevel() != player.getMaxDamageLevel()) {
						player.setHeading(player.getSteerDir());
						player.move();
						player.setEnergyLevel(-player.getRate());
						if(player.getEnergyLevel() == 0) {
							//System.out.println("PlayerCyborg cannot move due to having no energy.");
							System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
							resetCyborg();
							
						}
					}
				}
				//Update drone location
				else if(temp instanceof Drone) {
					Drone drone = (Drone)temp;
					drone.move();
				}
				//Update NPC location
				else if(temp instanceof NonPlayerCyborg) {
					NonPlayerCyborg npc = (NonPlayerCyborg)temp;
					if(npc.getStrategy() instanceof CyborgCollisionStrategy) {
						computeNPCLocToCB(npc);
						npc.move();
					}
					else if(npc.getStrategy() instanceof BaseCollisionStrategy) {
						if(npc.getBaseReach() == MAX_BASE) {
							System.out.println("NPC reaches the last base first."
									+ "You have lost a life.");
							resetCyborg();
							
						}
						else {
							computeNPCLocToBase(npc, npc.getBaseReach() + 1);
							npc.move();
						}
					}
				}
			}
		//iterator.resetIndex();
		checkCollision();
		this.clock++;
		update();
	}

	private void checkCollision() {
		// TODO Auto-generated method stub
		IIterator iterator = object.getIterator();
		Vector collidable = new Vector(); //Vector contains all collidable object
		while(iterator.hasNext()) {
			GameObject thisObj = iterator.getNext();
			if(thisObj instanceof ICollider)
			{
				ICollider thisColliderObj = (ICollider) thisObj;
				IIterator otherIter = object.getIterator();
				Vector<ICollider> collideObj = new Vector<ICollider>(); //Vector of each collidable object
				if(!collidable.contains(collideObj)) {
					collidable.add(collideObj);
				}
				while(otherIter.hasNext())
				{
					GameObject otherObj = otherIter.getNext();
					if(otherObj instanceof ICollider && !(thisObj.equals(otherObj)))
					{
						ICollider otherColliderObj = (ICollider) otherObj;
						if(thisColliderObj.collidesWith(otherColliderObj))
						{
							for(int i = 0; i < collidable.size(); i++) {
								if(!((Vector<ICollider>) collidable.get(i)).contains(otherColliderObj)) {
									((Vector<ICollider>) collidable.get(i)).add(otherColliderObj);
									thisColliderObj.handleCollision(otherColliderObj);
								}
							}
						}
					}
				}
			}
		}
		update();
	}


	private void computeNPCLocToCB(NonPlayerCyborg npc) {
		// TODO Auto-generated method stub
		PlayerCyborg player = findPlayer();
		double px = Math.abs(player.getPosX() - npc.getPosX());
		double py = Math.abs(player.getPosY() - npc.getPosY());
		double angle =MathUtil.floor(Math.toDegrees(MathUtil.atan(py/px))) ;
		npc.setDir(angle);
		npc.setHeading(npc.getSteerDir());
	}

	private void computeNPCLocToBase(NonPlayerCyborg npc, int baseReach) {
		// TODO Auto-generated method stub
		Base base = findBase(baseReach);
		/*Compute NPC heading/steering direction
		 * rad = arctan(a,b)
		 * a = |x1 - x0|
		 * b = |y1 - y0|
		 */
		double px = Math.abs(base.getPosX() - npc.getPosX());
		double py = Math.abs(base.getPosY() - npc.getPosY());
		double angle = MathUtil.floor(Math.toDegrees(MathUtil.atan(py/px)));
		npc.setDir(angle);
		npc.setHeading(npc.getSteerDir());
	}

	//Display values of game and PlayerCyborg
	/*public void gameStat() {
		PlayerCyborg player = findPlayer();
		System.out.println("Lives: " + live);
		System.out.println("Elapsed Time: " + clock);
		System.out.println("Last Base Reached: " + player.getBaseReach());
		System.out.println("PlayerCyborg's Energy Level: " + player.getEnergyLevel());
		System.out.println("PlayerCyborg's Damage Level: " + player.getDamageLevel());
	}*/

	//Display the game map
	/*public void map() {
		PlayerCyborg player = findPlayer();
		player.setHeading(player.getSteerDir());
		IIterator iterator = object.getIterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.getNext());
		}
		System.out.println();
	}*/
	
	//Find PlayerCyborg
		public PlayerCyborg findPlayer() {
			IIterator iterator = object.getIterator();
			
			PlayerCyborg player = null;
			while(iterator.hasNext()) {
				
				GameObject temp = iterator.getNext();
				if (temp instanceof PlayerCyborg) {
					player = (PlayerCyborg)temp;
					
					break;
				}
			}
				
			if (player == null) {
				System.out.println("No PlayerCyborg is found.");
				return null;
			}
			return player;
		}
		
	//Find NPC with Cyborg Collision Srategy
		public NonPlayerCyborg findCBCollisionStrategy() {
			IIterator iterator = object.getIterator();
		
			NonPlayerCyborg npc = null;
			while(iterator.hasNext()) {
				
				GameObject temp = iterator.getNext();
				if (temp instanceof NonPlayerCyborg) {
					npc = (NonPlayerCyborg)temp;
					if(npc.getStrategy() instanceof CyborgCollisionStrategy) {
					
						break;
					}
				}
			}
				
			if (npc == null) {
				System.out.println("No NPC is found.");
				return null;
			}
			return npc;
		}
		
		//Find NPC with Base Collision Strategy
		public NonPlayerCyborg findBCollisionStrategy() {
			IIterator iterator = object.getIterator();
			
			NonPlayerCyborg npc = null;
			while(iterator.hasNext()) {
				
				GameObject temp = iterator.getNext();
				if (temp instanceof NonPlayerCyborg) {
					npc = (NonPlayerCyborg)temp;
					if(npc.getStrategy() instanceof BaseCollisionStrategy) {
					
						break;
					}
				}
			}
				
			if (npc == null) {
				System.out.println("No NPC is found.");
				return null;
			}
			return npc;
		}
		
		//Find drone
		public Drone findDrone() {
			IIterator iterator = object.getIterator();
			
			Drone drone = null;
			while(iterator.hasNext()) {
				GameObject temp = iterator.getNext();
				if(temp instanceof Drone) {
					drone = (Drone) temp;
					break;
				}
			}
			if(drone == null) {
				System.out.println("No Drone is found.");
				return null;
			}
			return drone;
		}
		
		//Find energy station
		public EnergyStation findStation() {
			IIterator iterator = object.getIterator();
			
			EnergyStation es = null;
			while(iterator.hasNext()) {
				GameObject temp = iterator.getNext();
				if(temp instanceof EnergyStation) {
					es = (EnergyStation)temp;
					break;
				}
			}
			if(es == null) {
				System.out.println("No energy station is found.");
				return null;
			}
			return es;
		}
		
		//find base with specific number
		public Base findBase(int a) {
			IIterator iterator = object.getIterator();
			
			Base base = null;
			while(iterator.hasNext()) {
				GameObject temp = iterator.getNext();
				if(temp instanceof Base) {
					base = (Base)temp;
					if(base.getId() == a) {
						
						break;
					}
				}
			}
			if(base == null) {
				System.out.println("No base is found.");
				return null;
			}
			
			return base;
		}
	
	public void gameOver(char c) {
		if(c == 'l') {
			System.out.println("PlayerCyborg has lost all his lives. Time to restart the game.");
			object.clear();
			exit();
		}
		else if (c == 'w') {
			System.out.println("Congratulation! You have won the game."
					         + "Total time: " + this.clock);
			object.clear();
			exit();
		}
	}

	public void exit() {
		System.exit(0);
	}
	
	//Notify observers to update
	public void update() {
		this.setChanged();
		this.notifyObservers(this);
	}
	
	public void setPause(boolean b) {
		this.pause = b;
	}
	
	public boolean getPause() {
		return this.pause;
	}
	
	public void setRePos(boolean b) {
		this.rePos = b;
	}
	
	public boolean getRePos() {
		return this.rePos;
	}
	
	public void rePos(Point newPos, Point oldPos) {
		IIterator ite = object.getIterator();
		while(ite.hasNext()) {
			GameObject obj = ite.getNext();
			if(obj instanceof Fixed) {
				Fixed temp = (Fixed)obj;
				if(getRePos() && temp.isSelected()) {
					int newX = newPos.getX() - oldPos.getX();
					int newY = newPos.getY() - oldPos.getY();
					temp.setLocation(newX, newY);
					setRePos(false);
					temp.setSelected(false);
				} else {	
					if(temp.contains(newPos, oldPos)) 
						temp.setSelected(true);
					 else 
						temp.setSelected(false);
				}
			}
		}
		update();
	}
	
	public void resetCyborg() {
		this.live--;
		if(getSound())
			createSound('l');
		if(this.live <= 0) {
			gameOver('l');
		}
		PlayerCyborg player = findPlayer();
		player.setDamageLevel(0);
		player.setBaseReach(1);
		player.setColor(ColorUtil.rgb(255, 0, 0));
		player.setHeading(0);
		player.setSpeed(0);
		player.setDir(0);
		player.setEnergyLevel(1000);
		object.clear();
		this.init();
	}
	public void createSound(char c) {
		switch(c) {
		case 'm':
			bg.play();
			break;
		case 'p':
			bg.pause();
			break;
		case 'b':
			base.play();
			break;
		case 'd':
			drone.play();
			break;
		case 'e':
			energy.play();
			break;
		case 'c':
			cyborg.play();
			break;
		case'l':
			lose.play();
			break;
		}
	}
}
