package com.mel.wallpaper.starWars.entity;


import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.mel.entityframework.IMovable;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.settings.GameSettings;
import com.mel.wallpaper.starWars.timer.TimerHelper;
import com.mel.wallpaper.starWars.view.IShooterAnimator;
import com.mel.wallpaper.starWars.view.Animation;
import com.mel.wallpaper.starWars.view.Position;
import com.mel.wallpaper.starWars.view.SpriteFactory;


public class Shooter extends Walker implements IMovable
{
	
	public static final float DEFAULT_SPEED = 40;
	public static final float MAX_JUMP_DISTANCE = 30;

	protected Point shootTarget;
	protected boolean isOnShootingCooldown = false;
	protected boolean canShootCooldown = false;
	
	
	/* Getters/Setters */
	protected void setShootingEnd(){
		this.isOnShootingCooldown = false;
	}
	

	public boolean isPerformingAnimation(){
		return isOnShootingCooldown || isOnRunningCooldown || isOnAplastadoCooldown;
	}
	
	public boolean canShoot(){
		return !canShootCooldown && !isPerformingAnimation();
	}
	
	

	/* Constructor */
	public Shooter(float x, float y, String textureId, Animation initialAnimation, Rol r){
		this(new Point(x,y), Shooter.DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public Shooter(float x, float y, float speed, String textureId, Animation initialAnimation, Rol r){
		this(new Point(x,y), speed, textureId, initialAnimation, r);
	}
	
	public Shooter(Point p, String textureId, Animation initialAnimation, Rol r){
		this(p, Shooter.DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public Shooter(Point p, float speed, String textureId, Animation initialAnimation, Rol r){
		super(p, speed, textureId, initialAnimation, r);
	
	}
	
		
	public Point getRotationCenter(){
		return new Point(35,35);
	}
	
	
	/* METHODS */
	public void animateShootAndStartCooldowns(Point destination){
		this.shootTarget = destination;
		((IShooterAnimator)this.animator).animateShoot(position, destination);
		
		// Cuan sels hi dona una ordre player estan "busy" una estona. Aixi la animacio de correr no xafa la de xutar.
		this.isOnShootingCooldown = true;
		TimerHelper.startTimer(this.position, 0.5f,  new ITimerCallback() {                      
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	setShootingEnd();
            }
        });
		
		this.canShootCooldown = true;
		TimerHelper.startTimer(this.position, MathUtils.random(0.5f, 20f),  new ITimerCallback() {                      
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	canShootCooldown = false;
            }
        });
	}
	
	
	/* HELPERS */
	public void recycle(){
		this.shootTarget = null;
		this.isOnShootingCooldown = false;
		super.recycle();
	}
	
	public void removeOldMovementOrders(){
		this.destination = null;
		this.isOnRunningCooldown = false;
		super.removeOldMovementOrders();

	}
}
