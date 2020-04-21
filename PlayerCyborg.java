package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class PlayerCyborg extends Cyborg{
	private static PlayerCyborg pc;
	private int maximumSpeed, energyConsumptionRate, damageLevel, maxDamageLevel;
	private GameWorld gw;

	private PlayerCyborg(GameWorld gw){
		this.gw = gw;
		super.setSize(100);
		this.maximumSpeed = 20;
		super.setEnergyLevel(1000);
		this.energyConsumptionRate = 1;
		this.damageLevel = 0;
		this.maxDamageLevel = 100;
		super.setBaseReach(1);
		super.setColor(ColorUtil.rgb(255,0,0)); //red
		super.setSpeed(0);
		super.setHeading(0);
		
		//Location
		IIterator itr = gw.getCollection().getIterator();
		while(itr.hasNext()) {
			GameObject obj = itr.getNext();
			if(obj instanceof Base) {
				Base base = (Base)obj;
				if(base.getId() == 1) 
					super.setLocation(base.getLocation());
			}
		}
	}
	
	public static PlayerCyborg getPlayer(GameWorld gw) {
		if(pc == null) 
			pc = new PlayerCyborg(gw);
		return pc;
	}
	
	
	public int getMaxSpeed() {
		return this.maximumSpeed;
	}
	
	public int getMaxDamageLevel() {
		return this.maxDamageLevel;
	}
	
	public int getRate() {
		return this.energyConsumptionRate;
	}
	
	public void setDamageLevel(int i) {
		if (this.damageLevel == this.maxDamageLevel)
			System.out.println("Cyborg is at its max damage level.");
		else 
			this.damageLevel += i;
	}
	
	public int getDamageLevel() {
		return this.damageLevel;
	}
	
	@Override
	public void setSteerDir(char x) {
		super.setSteerDir(x);
	}
	
	@Override
	public String toString() {
		String parentDes = super.toString();
		String myDes = 
				" Size = " + super.getSize() + " Max Speed = " + this.maximumSpeed + 
				" Steering Direction = " + this.getSteerDir() + " Energy Level = " + this.getEnergyLevel() +
				" Damage Level = " + this.damageLevel;
		return ("Cyborg: " + parentDes + myDes);
	}
	
	@Override 
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		int x =  (int)this.getPosX()+ (int)pCmpRelPrnt.getX();
		int y =   (int)this.getPosY()+(int)pCmpRelPrnt.getY();
		g.fillRect(x, y, super.getSize(), super.getSize());
		
	}
}
