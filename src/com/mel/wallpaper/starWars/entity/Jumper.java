package com.mel.wallpaper.starWars.entity;


import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseExponentialOut;

import com.mel.entityframework.IMovable;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.settings.GameSettings;
import com.mel.wallpaper.starWars.timer.TimerHelper;
import com.mel.wallpaper.starWars.view.GoalKeeperAnimator;
import com.mel.wallpaper.starWars.view.Animation;
import com.mel.wallpaper.starWars.view.Position;
import com.mel.wallpaper.starWars.view.SpriteFactory;


public class Jumper extends Shooter implements IMovable
{
	
	public static final float GOLAKEEPER_DEFAULT_SPEED = 50;
	public static final float MAX_JUMP_DISTANCE = 30;

	protected boolean isOnJumpingCooldown = false;

	
	
	
	/* Getters/Setters */
	public boolean getIsOnJumpingCooldown() {
		return this.isOnJumpingCooldown;
	}
	
	protected void setJumpingEnd(){
		//Debug.d("gk","setJumpingEnd");
		this.destination = null;
		this.isOnJumpingCooldown = false;
	}
	
	public boolean isPerformingAnimation(){
		return isOnShootingCooldown || isOnRunningCooldown || isOnAplastadoCooldown || isOnJumpingCooldown;
	}
	
	public boolean canShoot(){
		return !isOnShootingCooldown && !isOnAplastadoCooldown && !isOnJumpingCooldown;
	}
	
	
	/* Constructor */
	public Jumper(float x, float y, String textureId, Animation initialAnimation, Rol r){
		this(new Point(x,y), Jumper.GOLAKEEPER_DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public Jumper(float x, float y,  float speed, String textureId, Animation initialAnimation, Rol r){
		this(new Point(x,y), speed, textureId, initialAnimation, r);
	}

	public Jumper(Point p, String textureId, Animation initialAnimation, Rol r){
		this(p, Jumper.GOLAKEEPER_DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public Jumper(Point p, float speed, String textureId, Animation initialAnimation, Rol r){
		super(p, speed, textureId, initialAnimation, Rol.JEDI);

		this.animator.setInitialAnimation(initialAnimation);
	}

	
	/* methods */
	public void jump(Point destination){
		this.destination = destination;
		this.isOnJumpingCooldown = true;
		 
		Debug.d("gk", "jump: "+destination);
		
		
		
		if(this.position.distance(this.destination) > 0 ){
			((GoalKeeperAnimator)this.animator).animateJump(this.position.toPoint(), this.destination);
			
			Path jumpPath = new Path(2).to(this.position.getX(), this.position.getY()).to(destination.getX(), destination.getY());
			PathModifier moveModifier = new PathModifier(1f, jumpPath, new IPathModifierListener()
			{
				
				public void onPathWaypointStarted(PathModifier pPathModifier, IEntity pEntity, int pWaypointIndex) {
				}
				public void onPathWaypointFinished(PathModifier pPathModifier, IEntity pEntity, int pWaypointIndex) {
				}
				public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) {
				}
				public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
					setJumpingEnd();
				}
			},EaseExponentialOut.getInstance());
			this.position.registerEntityModifier(moveModifier);
			
		}else{
			TimerHelper.startTimer(this.position, 1f,  new ITimerCallback() {                      
	            public void onTimePassed(final TimerHandler pTimerHandler)
	            {
	            	setJumpingEnd();
	            }
	        });
		}
	}
	

	
	
	/* HELPERS */
	public void recycle(){
		this.isOnJumpingCooldown = false;
		super.recycle();
	}
	
	public void removeOldMovementOrders(){
		this.destination = null;
		this.isOnJumpingCooldown = false;
		this.isOnRunningCooldown = false;
		super.removeOldMovementOrders();

	}
}
