package com.mel.entityframework;

import com.mel.util.Point;
import com.mel.wallpaper.starWars.view.Position;

public interface IMovable
{
	public Position getPosition();
	
	public float getSpeed();
	
	public void animateMoveAndStartCooldowns(Point destination);
	
	public void animateStopAndStartCooldowns();

	public void forceStopMovement();
	
	public void removeOldMovementOrders();
	
}
