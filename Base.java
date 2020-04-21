package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Base extends Fixed{
	
	private int size, seqNum;
	private GameWorld gw;
	private int max;
	
	public Base(int i, GameWorld gw, int max) {
		this.gw = gw;
		this.max = max;
		seqNum = i;
		super.setSize(50);
		this.size = super.getSize();
		super.setColor(ColorUtil.BLUE); //blue 
		
		//set Location of each base
		super.setLocation((float)rand.nextInt(900),(float)rand.nextInt(900));
		
	}
	
	public int getId() {
		return seqNum;
	}
	
	public int getSize() {
		return size;
	}
	
	public String toString() {
		String parentDes = super.toString();
		String myDes = " Size = " + size + " Sequence Number = " + seqNum;
		return ("Base: " + parentDes + myDes);
	}
	
	 @Override
	  public void draw(Graphics g, Point pCmpRelPrnt) {
		  int stringX = (int)Math.round(this.getId() -10) + pCmpRelPrnt.getX();
		  int stringY = (int)Math.round(this.getId() -10) + pCmpRelPrnt.getY();
		  g.setColor(super.getColor());
		  int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		  int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		  int[] xPoints = { xLoc, (xLoc - this.getSize()), (xLoc + this.getSize()), xLoc };
		  int[] yPoints = { (yLoc + this.getSize()), (yLoc - this.getSize()), (yLoc - this.getSize()), (yLoc + this.getSize()) };
		  int nPoints = 3;
		  //g.drawPolygon(xPoints, yPoints, nPoints);
		  if (super.isSelected())
			  g.drawPolygon(xPoints, yPoints, nPoints);
		  else
			  g.fillPolygon(xPoints, yPoints, nPoints);
		  g.setColor(ColorUtil.BLACK);
		  g.drawString("" + this.seqNum, stringX, stringY);
		  
	  }
	 
	 @Override
		public void handleCollision(ICollider otherObj) {
			// TODO Auto-generated method stub
			//gw.createSound('b');
			if(otherObj instanceof PlayerCyborg) {
				gw.createSound('b');
				PlayerCyborg player = (PlayerCyborg)otherObj;
				if(player.getBaseReach() == (this.getId() - 1)) {
					System.out.println("Player has reached the next base.");
					player.setBaseReach(this.getId());
					if(player.getBaseReach() == max) {
						System.out.println("Player has successfully reached the last base.");
						gw.gameOver('w');
				}
			}
			else if(otherObj instanceof NonPlayerCyborg) {
				NonPlayerCyborg npc = (NonPlayerCyborg)otherObj;
				if(npc.getBaseReach() == (this.getId() - 1)) {
					System.out.println("NPC has reached the next base.");
					npc.setBaseReach(this.getId());
					if(npc.getBaseReach() == max) {
						System.out.println("NPC has reached the last base before the Player.");
						gw.resetCyborg();
						
				}
			}
		}
}
	 }
}
