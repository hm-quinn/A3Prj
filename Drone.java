package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Drone extends Movable {
	private int size;
	private GameWorld gw;
	
	public Drone(GameWorld gw) {
		this.gw = gw;
		super.setSize(50);
		this.size = super.getSize();
		super.setColor(ColorUtil.GRAY); //grey
		super.setHeading(rand.nextInt(360));
		super.setSpeed(rand.nextInt(6) + 5);
	}
	
	@Override
	public String toString() {
		String parentDes = super.toString();
		String myDes = " Size = " + size;
		return ("Drone: " + parentDes + myDes);
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		  g.setColor(super.getColor());
		  int x = (int)this.getPosX() + pCmpRelPrnt.getX();
		  int y = (int)this.getPosY() + pCmpRelPrnt.getY();
		  int[] xPoints = { x, (x - this.getSize()), (x + this.getSize()), x };
		  int[] yPoints = { (y + this.getSize()), (y - this.getSize()), (y- this.getSize()), (y + this.getSize()) };
		  int nPoints = 3;
		  g.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	@Override
	public void handleCollision(ICollider otherObj) {
		// TODO Auto-generated method stub
		//gw.createSound('d');
		if(otherObj instanceof PlayerCyborg) {
			gw.createSound('d');
			PlayerCyborg npc = (PlayerCyborg)otherObj;
			System.out.println("Collison with Drone.");
			//same as colliding with another PlayerCyborg, except half the damage
			npc.setDamageLevel(2);
			npc.setEnergyLevel(-10);
			if(npc.getEnergyLevel() <= 0 || npc.getEnergyLevel() == npc.getMaxDamageLevel()) {
				npc.setEnergyLevel(0);
				npc.setDamageLevel(npc.getMaxDamageLevel());
				System.out.println("Player has lost a live.");
				gw.resetCyborg();
			}
			else {
				npc.setColor(ColorUtil.rgb(255, npc.getEnergyLevel() * 10, npc.getEnergyLevel() * 10));
				gw.changeSpeed('c');
			}
		}
		else if(otherObj instanceof NonPlayerCyborg) {
			NonPlayerCyborg npc = (NonPlayerCyborg)otherObj;
			System.out.println("Collison with Drone.");
			//same as colliding with another PlayerCyborg, except half the damage
			npc.setEnergyLevel(-10);
			npc.setColor(ColorUtil.rgb(255, npc.getEnergyLevel() * 10, npc.getEnergyLevel() * 10));
		}
	
	}

}
