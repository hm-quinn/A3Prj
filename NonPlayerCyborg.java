package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class NonPlayerCyborg extends Cyborg{
	IStrategy strategy;
	private int size;
	private int id;
	private GameWorld gw;
	
	public NonPlayerCyborg(int i, GameWorld gw) {
		this.gw = gw;
		super.setSize(100);
		this.size = super.getSize();
		this.id = i;
		super.setSpeed(3);
		super.setEnergyLevel(20000);
		super.setBaseReach(0);
		super.setColor(ColorUtil.rgb(255, 0, 0));
		super.setLocation((float)rand.nextInt(900),(float)rand.nextInt(900));
		if(i == 1) 
			this.setStrategy(new CyborgCollisionStrategy());
		else if(i == 2)
			this.setStrategy(new BaseCollisionStrategy());
		else if(i == 3)
			this.setStrategy(new CyborgCollisionStrategy());
	}

	public void resetEnergy() {
		super.setEnergyLevel(2000);
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.size;
	}

	public void setStrategy(IStrategy strategy) {
		// TODO Auto-generated method stub
		this.strategy = strategy;
	}

	public void invokeStrategy() {
		// TODO Auto-generated method stub
		this.strategy.apply();
	}
	
	public IStrategy getStrategy() {
		return this.strategy;
	}
	
	public String toString() {
		String parentDes = super.toString();
		String myDes = 
				" Size = " + this.size + " Speed = " + this.getSpeed() +
				" Steering Direction = " + this.getSteerDir() + " Energy Level = " + this.getEnergyLevel() +
				" Strategy = " + strategy.getName();
		return ("Cyborg " + this.id + ": " + parentDes + myDes);
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		int x = (int)this.getPosX()+ (int)pCmpRelPrnt.getX();
		int y = (int)this.getPosY()+(int)pCmpRelPrnt.getY();
		g.drawRect(x, y, this.size, this.size);

	}
	
	@Override
	public void handleCollision(ICollider otherObj) {
		gw.createSound('c');
		// TODO Auto-generated method stub
		if(otherObj instanceof PlayerCyborg) {
			PlayerCyborg player = (PlayerCyborg)otherObj;
			System.out.println("Player collides with an NPC.");
			player.setEnergyLevel(-20);
			player.setDamageLevel(5);
			this.setEnergyLevel(-20);
			if(this.getEnergyLevel() <= 0) 
				this.resetEnergy();
			if(player.getEnergyLevel() <= 0 || player.getEnergyLevel() == player.getMaxDamageLevel()) {
				player.setEnergyLevel(0);
				player.setDamageLevel(player.getMaxDamageLevel());
				System.out.println("Player has lost a live.");
				gw.resetCyborg();
				
			}
			else {
				this.setColor(ColorUtil.rgb(255, this.getEnergyLevel() * 10, this.getEnergyLevel() * 10));
				gw.changeSpeed('c');
				
			}
		}
		else if(otherObj instanceof NonPlayerCyborg) {
			NonPlayerCyborg npc = (NonPlayerCyborg)otherObj;
			System.out.println("An NPC collides with another NPC.");
			npc.setEnergyLevel(-20);
			npc.setColor(ColorUtil.rgb(255, this.getEnergyLevel() * 10, this.getEnergyLevel() * 10));
			if(npc.getEnergyLevel() <= 0) 
				npc.resetEnergy();
			this.setEnergyLevel(-20);
			this.setColor(ColorUtil.rgb(255, this.getEnergyLevel() * 10, this.getEnergyLevel() * 10));
			if(this.getEnergyLevel() <= 0) 
				this.resetEnergy();
			
		}
	}

}
