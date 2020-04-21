package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class EnergyStation extends Fixed {
	private int size, capacity;
	private GameWorld gw;
	
	public EnergyStation(GameWorld gw) {
		this.gw = gw;
		super.setSize(rand.nextInt(51) + 150);
		this.size = this.capacity = super.getSize();
		super.setColor(ColorUtil.GREEN); //green
		super.setLocation((float)rand.nextInt(900),(float)rand.nextInt(900));
	}
	
	public int getSize() {
		return this.size;
	}
	
	//set capacity after collision
	public void setCapacity() {
		this.capacity = 0;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public String toString() {
		String parentDes = super.toString();
		String myDes = " Size = " + size + " Capacity = " + capacity;
		return ("Energy Station: " + parentDes + myDes);
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		int stringX = (int)Math.round(this.getPosX() +15) + pCmpRelPrnt.getX();
		int stringY = (int)Math.round(this.getPosY() +10) + pCmpRelPrnt.getY();

		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX() ;
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();	
		if (super.isSelected())
			g.drawArc(xLoc, yLoc, this.getSize(),this.getSize(), 0, 360);
		else
			g.fillArc(xLoc, yLoc, this.getSize(),this.getSize(), 0, 360);
	
		g.setColor(ColorUtil.BLACK);
		g.drawString("" + this.capacity, stringX, stringY);
		
	}
	
	@Override
	public void handleCollision(ICollider otherObj) {
		// TODO Auto-generated method stub
		gw.createSound('e');
		if(otherObj instanceof Cyborg) {
			if(this.getCapacity() != 0) {
				System.out.println("Colide with an energy station.");
				((Cyborg) otherObj).setEnergyLevel(this.getCapacity());
				this.setCapacity();
				this.setColor(ColorUtil.rgb(153,255,51));
				gw.getCollection().remove(this);
				gw.addEnergyStation();
			}
		}
		
		
	}

}
