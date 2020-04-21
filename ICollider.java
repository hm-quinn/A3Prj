package com.mycompany.a3;

public interface ICollider {
	public boolean collidesWith(ICollider otherColliderObj);
	public void handleCollision(ICollider otherObj);
}
