package com.mycompany.a3;

public abstract class Cyborg extends Movable implements ISteerable {
	private int energyLevel, lastBaseReached;
	private int i = 0;
	private double steeringDirection;
	
	public void setEnergyLevel(int i) {
		this.energyLevel += i;
	}
	
	public int getEnergyLevel() {
		return this.energyLevel;
	}
	
	public void setBaseReach(int i) {
		this.lastBaseReached = i;
	}
	
	public int getBaseReach() {
		return this.lastBaseReached;
	}
	
	@Override
	public void setDir(double angle) {
		this.steeringDirection = angle;
	}
	
	@Override
	public void setSteerDir(char x) { //set steering direction
		//left turn
		if(x == 'l') {
			this.steeringDirection -= 5;
			this.i+= 5;
			if(this.i > 40) {
				System.out.println("Error! Cyborg can only turn left  40 degrees at a time.");
			}
		}
		else { 
			this.i -= Math.abs(this.steeringDirection);
		}
		
		//right turn
		if(x == 'r') {
			this.steeringDirection += 5;
			this.i += 5;
			if (this.i > 40) {
				System.out.println("Error! Cyborg can only turn right 40 degrees at a time.");
			}
		}
		else { 
			this.i -= Math.abs(this.steeringDirection);
		}
	}
	
	public double getSteerDir() {
		return this.steeringDirection;
	}
	

}
