package com.mycompany.a3;

public class CyborgCollisionStrategy implements IStrategy{
	@Override
	public void apply() {
		System.out.println("Non Player Cyborg is going after a Player Cyborg.");
	}
	@Override
	public String getName() {
		return "Cyborg Collide Strategy";
	}
}