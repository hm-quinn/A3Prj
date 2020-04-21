package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Movable extends GameObject implements IMovable {
	private int speed;
	private double heading;
	
	
	@Override
	public void move() {	
		double theta = 90 - this.heading;
		float newX = this.getPosX() + (float)Math.cos( Math.toRadians(theta)  )* this.speed;
		float newY = this.getPosY() + (float)Math.sin( Math.toRadians(theta)  )* this.speed;		
 		
		//check bounding
		double px = MapView.getMapPoint().getX();
		double py = MapView.getMapPoint().getY();
		if((px + newX >= MapView.getMapWidth() + px) || (px + newX  <= px))
			this.setHeading(360 - this.heading);
		if((py + newY >= GameWorld.getHeight() + py) || (py + newY <= py) )
			this.setHeading((360 - this.heading + 180) % 360);
		
		super.setLocation(newX, newY);
	}
	
	public void setSpeed(int x)
	{
		this.speed += x;
	}
	
	public int getSpeed()
	{
		return this.speed;
	}
	
	public double getHeading()
	{
		return this.heading;
		
	}
	
	public void setHeading(double x)
	{
		this.heading = x;
	}
	
	public String toString()
	{
		String parentString = super.toString();
		String str = " Heading = " + this.heading + " Speed = " + this.speed;	
		String retval = parentString + str;
		return retval;
	}
}
