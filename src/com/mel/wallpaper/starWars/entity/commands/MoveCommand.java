package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.mel.entityframework.IMovable;
import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;

public class MoveCommand extends Command
{
	public Point destination;
	public IMovable movable;
	public IMovable getMovable() {
		return movable;
	}

	public void setMovable(IMovable movable) {
		this.movable = movable;
	}

	public IEaseFunction easeFunction;
	public float speedFactor;
	
	
	private static final IEaseFunction DEFAULT_EASE_FUNCTION = EaseLinear.getInstance();
	private static final float DEFAULT_SPEED_FACTOR = 1f;
	protected float MAXIMUM_DISTANCE = 0f;;
	
	public MoveCommand(Walker player) {
		this(player, DEFAULT_SPEED_FACTOR, DEFAULT_EASE_FUNCTION);
	}
	
	public MoveCommand(Walker walker, float speedFactor, IEaseFunction easeFunction) {
		this.walker = walker;
		this.speedFactor = speedFactor;
		this.easeFunction = easeFunction;
	}

	@Override
	public void execute(Map p) {
		moveObject(this.movable, this.speedFactor);
	}

	protected void moveObject(final IMovable currentMovable, float currentSpeedFactor) {
		currentMovable.removeOldMovementOrders();
		
		Point origien = (Point)currentMovable.getPosition().toPoint();
		Point destino = this.destination.clone();
		
		float distance =  origien.distance(destino);
		// Protegim el PathModifier de que generi NaN quan origen = desti
		if (distance > 0) {
			float duration = distance/(currentMovable.getSpeed()*currentSpeedFactor);
			
			Path path = new Path(2).to(origien.getX(), origien.getY()).to(destino.getX(), destino.getY()); 
			PathModifier moveModifier = new PathModifier(duration, path, new IPathModifierListener()
			{
				public void onPathWaypointStarted(PathModifier pPathModifier, IEntity pEntity, int pWaypointIndex) {
				}
				
				public void onPathWaypointFinished(PathModifier pPathModifier, IEntity pEntity, int pWaypointIndex) {
				}
				
				public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) {
				}
				
				public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
					currentMovable.movementEnd(); 
					//ejemplo de codigo seguro para autoquitarse un modifier al terminar;
	//			    engine.runOnUpdateThread(new Runnable(){
	//                    @Override
	//                    public void run(){
	//                    		movable.stopMovement();
	//                    }
	//				});
				}
			},easeFunction);
			
			currentMovable.getPosition().registerEntityModifier(moveModifier);
			
			currentMovable.animateMoveAndStartCooldowns(this.destination.clone());
		}
		
	}
	
}
