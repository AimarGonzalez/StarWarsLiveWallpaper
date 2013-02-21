package com.mel.wallpaper.starWars.entity;


import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.mel.entityframework.IMovable;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.timer.TimerHelper;
import com.mel.wallpaper.starWars.view.Animation;
import com.mel.wallpaper.starWars.view.IJediAnimator;


public class JediKnight extends Walker implements IMovable
{
	
	public static final float DEFAULT_SPEED = 35;
	public static final float MAX_JUMP_DISTANCE = 30;

	protected Point duelTarget;
	protected boolean isOnFightingCooldown = false;
	protected boolean isOnParringCooldown = false;
	protected boolean canDuelCooldown = false;
	
	
	/* Getters/Setters */
	protected void setFightingEnd(){
		this.isOnFightingCooldown = false;
	}
	

	public boolean isPerformingAnimation(){
		return isOnParringCooldown || isOnFightingCooldown || isOnRunningCooldown || isOnAplastadoCooldown;
	}
	
	public boolean canDuel(){
		return !canDuelCooldown && !isPerformingAnimation();
	}
	
	public boolean canParry(){
		return !isOnFightingCooldown && !isOnAplastadoCooldown;
	}
	
	

	/* Constructor */
	public JediKnight(float x, float y, String textureId, Animation initialAnimation, Rol r){
		this(new Point(x,y), JediKnight.DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public JediKnight(float x, float y, float speed, String textureId, Animation initialAnimation, Rol r){
		this(new Point(x,y), speed, textureId, initialAnimation, r);
	}
	
	public JediKnight(Point p, String textureId, Animation initialAnimation, Rol r){
		this(p, JediKnight.DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public JediKnight(Point p, float speed, String textureId, Animation initialAnimation, Rol r){
		super(p, speed, textureId, initialAnimation, r);
	
	}
	
		
	public Point getRotationCenter(){
		return new Point(35,35);
	}
	
	
	/* METHODS */
	public void animateParryLaser(Point destination){
		
		if(!isOnParringCooldown){
			((IJediAnimator)this.animator).animateParry(this, destination);

			this.isOnParringCooldown = true;
			
			//TODO AG: Podriamos mejorar coordinacion de animacion/cooldown, si al llamar
			//animateParry, pasaramos por parametro un IAnimationListener, para recivir
			//el evento de "onAnimationFinished"
			TimerHelper.startTimer(this.position, 0.6f,  new ITimerCallback() {                      
				public void onTimePassed(final TimerHandler pTimerHandler)
				{
					isOnParringCooldown = false;
				}
			});
		}
	}
	
	
	public void animateDuelAndStartCooldowns(Point destination){
		this.duelTarget = destination;
		((IJediAnimator)this.animator).animateDuel(this, destination);
		
		// Cuan sels hi dona una ordre player estan "busy" una estona. Aixi la animacio de correr no xafa la de xutar.
		this.isOnFightingCooldown = true;
		TimerHelper.startTimer(this.position, 0.5f,  new ITimerCallback() {                      
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	setFightingEnd();
            }
        });
		
		this.canDuelCooldown = true;
		TimerHelper.startTimer(this.position, MathUtils.random(10f, 30f),  new ITimerCallback() {                      
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	canDuelCooldown = false;
            }
        });
	}
	
	
	/* HELPERS */
	public void recycle(){
		this.duelTarget = null;
		this.isOnFightingCooldown = false;
		super.recycle();
	}
	
	public void removeOldMovementOrders(){
		this.destination = null;
		this.isOnRunningCooldown = false;
		super.removeOldMovementOrders();

	}
}
