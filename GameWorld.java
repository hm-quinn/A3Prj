package com.mycompany.a3;

import java.util.Observable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.util.MathUtil;
import java.util.Vector;

public class GameWorld extends Observable implements IGameWorld {
	private GameObjectCollection object;
	private int clock;
	private int live;
	private double height, width;
	private boolean sound = true;
	public static final int MAX_BASE = 4;
	public static final int MAX_NPC = 3;
	public static final int MAX_ES = 2;
	public static final int MAX_DRONE = 2;
	private int newSpeed;
	private boolean pause = false;
	private boolean pos = false;
	
	public GameWorld() {
		live = 3;
		init();
		update();
	}
	
	public void setHeight(double i) {
		this.height = i;
	}
	
	public void setWidth(double i) {
		this.width = i;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public double getWidth() {
		return this.width;
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
		this.clock = 0;
		object = new GameObjectCollection();
		//add a player
		PlayerCyborg player = PlayerCyborg.getPlayer();
		object.add(player);
		
		//add 3 NPCs
		for (int i = 1; i <= 3; i++) {
			NonPlayerCyborg npc = new NonPlayerCyborg(i, this);
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
					this.resetCyborg();
					update();
					
				}
				else if(player.getEnergyLevel() == 0) {
					//System.out.println("PlayerCyborg cannot move due to having no energy.");
					System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
					this.resetCyborg();
					update();
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
					update();
				}
			
			}	
			if(x == 'b') {
				if (player.getSpeed() <= 0)
					System.out.println("PlayerCyborg is current not moving.");
				else { 
					System.out.println("Brake is applied.");
					player.setSpeed(-1);
				}
				update();
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

	/*public void CBCollision() {
		System.out.println("Collided with another PlayerCyborg.");
		PlayerCyborg player = findPlayer();
		NonPlayerCyborg npc = findCBCollisionStrategy();
		npc.setEnergyLevel(-20);
		if(npc.getEnergyLevel() <= 0)
			npc.resetEnergy();
		player.setDamageLevel(5);
		player.setEnergyLevel(-20);
		if(player.getEnergyLevel() <= 0) {
			player.setEnergyLevel(0);
			System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
			this.resetCyborg();
			update();
		
		}
		else if( player.getDamageLevel() >= player.getMaxDamageLevel()) {
			player.setDamageLevel(player.getMaxDamageLevel());
			System.out.println("PlayerCyborg has lost a live due to having reached maximum damage level.");
			live -= 1;
			this.resetCyborg();
			update();
			
		}
		else {
			player.setColor(ColorUtil.rgb(255, player.getDamageLevel() * 10, player.getDamageLevel() * 10));
			changeSpeed('c');
			update();
		}
		
	}

	public void DCollision() {
		Drone drone = findDrone();
		System.out.println("Collison with Drone.");
		//same as colliding with another PlayerCyborg, except half the damage
		PlayerCyborg player = findPlayer();
		player.setDamageLevel(2);
		player.setEnergyLevel(-10);
		if(player.getEnergyLevel() <= 0) {
			player.setEnergyLevel(0);
			System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
			this.resetCyborg();
			update();
			
			
		}
		else if( player.getDamageLevel() == player.getMaxDamageLevel()) {
			player.setDamageLevel(player.getMaxDamageLevel());
			System.out.println("PlayerCyborg has lost a live due to having reached maximum damage level.");
			this.resetCyborg();
			update();
		}
		else {
			player.setColor(ColorUtil.rgb(255, player.getDamageLevel() * 10, player.getDamageLevel() * 10));
			changeSpeed('c');
			update();
		}
	}

	public void ESCollision() {
		EnergyStation energyStation = findStation();
		System.out.println("Collision with an energy station.");
		PlayerCyborg player = findPlayer();
		player.setEnergyLevel(energyStation.getCapacity());
		energyStation.setCapacity();
		energyStation.setColor(ColorUtil.rgb(153,255,51));
		object.remove(energyStation);
		addEnergyStation();
		update();
	}
	

	public void BCollision(Base base, ICollision obj) {
		if(obj instanceof PlayerCyborg)
			PlayerCyborg player = (PlayerCyborg)obj;
		else
			NonPlayerCyborg player = (NonPlayerCyborg)obj;
		if((base.getId() - player.getBaseReach()) == 1) {
			//reach the last base (4)
			if(base.getId() == MAX_BASE) {
				System.out.println("PlayerCyborg has successfully reached the last base.");
				player.setBaseReach(base.getId());
				player.setLocation(base.getLocation());
				update();
				gameOver('w');
			}
			//reach the next base
			else {
				System.out.println("PlayerCyborg has successfully reached the next base.");
				player.setBaseReach(base.getId());
				player.setLocation(base.getLocation());
				update();
			}
		}
	}*/
	
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
				//npc.setBaseReach((npc.getBaseReach()) + 1); //NPC move to next base
				update();
			}
		}
		//iterator.resetIndex();
	}
	
	public void soundToggle(boolean sound) {
		this.sound = sound;
		update();
	}

	public void clockTick(double time) {
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
						player.move(time, width, height);
						player.setEnergyLevel(-player.getRate());
						if(player.getEnergyLevel() == 0) {
							//System.out.println("PlayerCyborg cannot move due to having no energy.");
							System.out.println("PlayerCyborg has lost a live due to unsufficient energy level.");
							this.resetCyborg();
							update();
						}
					}
				}
				//Update drone location
				else if(temp instanceof Drone) {
					Drone drone = (Drone) iterator.getNext();
					drone.move(time, width, height);
				}
				//Update NPC location
				else if(temp instanceof NonPlayerCyborg) {
					NonPlayerCyborg npc = (NonPlayerCyborg)temp;
					if(npc.getStrategy() instanceof CyborgCollisionStrategy) {
						computeNPCLocToCB(npc);
						npc.move(time, width, height);
					}
					else if(npc.getStrategy() instanceof BaseCollisionStrategy) {
						if(npc.getBaseReach() == MAX_BASE) {
							System.out.println("NPC reaches the last base first."
									+ "You have lost a life.");
							this.resetCyborg();
							update();
						}
						else {
							computeNPCLocToBase(npc, npc.getBaseReach() + 1);
							npc.move(time, width, height);
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
			if(thisObj instanceof ICollision)
			{
				ICollision thisColliderObj = (ICollision) thisObj;
				IIterator otherIter = object.getIterator();
				Vector<ICollision> collideObj = new Vector<ICollision>(); //Vector of each collidable object
				if(!collidable.contains(collideObj)) {
					collidable.add(collideObj);
				}
				while(otherIter.hasNext())
				{
					GameObject otherObj = otherIter.getNext();
					if(otherObj instanceof ICollision && !(thisObj.equals(otherObj)))
					{
						ICollision otherColliderObj = (ICollision) otherObj;
						if(thisColliderObj.collidesWith(otherColliderObj))
						{
							for(int i = 0; i < collidable.size(); i++) {
								if(!((Vector<ICollision>) collidable.get(i)).contains(otherColliderObj)) {
									((Vector<ICollision>) collidable.get(i)).add(otherColliderObj);
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
		
			double a = Math.abs(player.getPosX() - npc.getPosX());
			double b = Math.abs(player.getPosY() - npc.getPosY());
			double angle = Math.round(Math.toDegrees(MathUtil.atan2(a,b)) * 100.0) / 100.0;
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
		double a = Math.abs(base.getPosX() - npc.getPosX());
		double b = Math.abs(base.getPosY() - npc.getPosY());
		double angle = Math.round(Math.toDegrees(MathUtil.atan2(a,b)) * 100.0) / 100.0;
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
	
	protected void resetCyborg() {
		this.live--;
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
		player.setEnergyLevel(100);
		object.clear();
		this.init();
	}
		
	protected void gameOver(char c) {
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
	protected void update() {
		this.setChanged();
		this.notifyObservers(this);
	}
	
	public void setPause() {
		this.pause = !pause;
	}
	
	public boolean getPause() {
		return this.pause;
	}
	
	public void setPos() {
		this.pos = !pos;
	}
	
	public boolean getPos() {
		return this.pos;
	}
	
	public void createSound(char c) {
		
	}
}
