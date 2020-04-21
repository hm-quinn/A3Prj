package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.charts.models.Point;
import java.util.Random;

public class GameObject implements ICollider, IDrawable {
	public  Random rand = new Random();
	private Point pos;
	private int color;
	private int size;
	
	public GameObject() {
		float x = (Math.round((1000* rand.nextFloat() * 10)) / 10);
		float y = (Math.round((1000* rand.nextFloat() * 10)) / 10);
		
		
		//accounting for edge case of something going off screen
		if( x > 1000)
			x = 1000;
		if(y > 1000)
			y = 1000;
		 
		pos = new Point(x,y);
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public float getPosX()
	{
		
		return pos.getX();
	}
	
	public float getPosY()
	{
		
		return pos.getY();
	}
	
	public Point getLocation()
	{
		return pos;
	}
	
	public void setLocation(float x, float y)
	{
		pos.setX(((Math.round(x)*10)/10));
		pos.setY(((Math.round(y)*10)/10));
		
	}
	
	public void setLocation(Point newLoc)
	{
		pos.setX(((Math.round(newLoc.getX())*10)/10));
		pos.setY(((Math.round(newLoc.getY())*10)/10));
		
	}
	
	public int getColor()
	{
		
		return color;
	}
	
	public void setColor(int newColor)
	{
		color = newColor;
	}
	
	public String toString()
	{
		String retVal = 
				"Location = " + pos.getX() + "," + pos.getY() 
				+ " Color = [" + ColorUtil.red(getColor()) + "," +
				ColorUtil.green(getColor()) +"," +
				ColorUtil.blue(getColor()) + "]";
		return retVal;
		
	}
	
	@Override
	public boolean collidesWith(ICollider otherObject) {
		GameObject obj = (GameObject)otherObject;
		boolean result = false;
		double thisCenterX = this.getPosX() + (obj.getSize()/2);
		double thisCenterY = this.getPosY()+ (obj.getSize()/2);
		
		double otherCenterX = obj.getPosX();
		double otherCenterY = obj.getPosY();
		
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		

		double distBetweenCentersSqr = (dx * dx + dy * dy);
		int thisRadius= this.getSize() / 2;
		int otherRadius= obj.getSize() / 2;
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { 
			result = true;
		}
			return result;
		
	}

	@Override
	public void draw(Graphics g, com.codename1.ui.geom.Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCollision(ICollider otherObj) {
		// TODO Auto-generated method stub
		
	}	
}
