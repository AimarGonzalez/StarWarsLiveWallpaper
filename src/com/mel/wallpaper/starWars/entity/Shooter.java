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
import com.mel.wallpaper.starWars.view.GoalKeeperAnimation;
import com.mel.wallpaper.starWars.view.PlayerAnimation;
import com.mel.wallpaper.starWars.view.Position;
import com.mel.wallpaper.starWars.view.ShooterAnimation;
import com.mel.wallpaper.starWars.view.SpriteFactory;


public class Shooter extends Walker implements IMovable
{
	
	private static final float	VERTICAL_CENTER	= 36*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static final float DEFAULT_SPEED = 30;
	public static final float MAX_JUMP_DISTANCE = 30;

	private boolean isOnShootingCooldown = false;
	private boolean isOnRunningCooldown = false;
	private boolean isOnAplastadoCooldown = false;
	private boolean isOnJumpingCooldown = false;
	private boolean isOnStopBallCooldown = false;

	
	/* Getters/Setters */
	public Position getPosition(){
		return this.position;
	}
	
	public float getSpeed(){
		return this.speed;
	}
	
	public boolean getIsOnJumpingCooldown() {
		return this.isOnJumpingCooldown;
	}
	public boolean getIsOnStopBallCooldown() {
		return this.isOnStopBallCooldown;
	}
	
	private void setShootingEnd(){
		this.isOnShootingCooldown = false;
	}
	
	private void setRunningEnd(){
		this.isOnRunningCooldown = false;
	}
	
	private void setAplastadoEnd(){
		this.isOnAplastadoCooldown = false;
	}
	
	private void setJumpingEnd(){
		//Debug.d("gk","setJumpingEnd");
		this.destination = null;
		this.isOnJumpingCooldown = false;
	}
	
	public boolean isBusy(){
		return isOnShootingCooldown || isOnRunningCooldown || isOnAplastadoCooldown || isOnJumpingCooldown;
	}
	
	public boolean canShoot(){
		return !isOnShootingCooldown && !isOnAplastadoCooldown && !isOnJumpingCooldown;
	}
	

	public float getSpriteOffsetX(){
		return this.sprite.getWidth()/2;
	}
	public float getSpriteOffsetY(){
		return this.sprite.getHeight()-VERTICAL_CENTER;
	}
	
	public Point getSpriteOffset(){
		Point spriteCenter = new Point(getSpriteOffsetX(), getSpriteOffsetY());
		return spriteCenter;
	}
	
//	
//	public float getBlockCenterX(){
//		return this.sprite.getWidth()/2;
//	}
//	public float getBlockCenterY(){
//		if(this.isOnJumpingCooldown){
//			switch (lastAnimation) {
//				case JUMP_NE:
//				case JUMP_NW:
//					return this.position.getY()+20; 
//				case JUMP_SE:
//				case JUMP_SW:
//					return this.position.getY()-20; 
//					
//				default:
//					return this.position.getY(); 
//			}
//		}else {
//			return this.position.getY();
//		}
//	}
//	public Point getBlockCenter(){
//		Point spriteCenter = new Point(getBlockCenterX(), getBlockCenterY());
//		return spriteCenter;
//	}
	
	/* Constructor */
	public Shooter(float x, float y, String textureId, PlayerAnimation initialAnimation, Rol r){
		this(new Point(x,y), Shooter.DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public Shooter(float x, float y, float speed, String textureId, PlayerAnimation initialAnimation, Rol r){
		this(new Point(x,y), speed, textureId, initialAnimation, r);
	}
	
	public Shooter(Point p, String textureId, PlayerAnimation initialAnimation, Rol r){
		this(p, Shooter.DEFAULT_SPEED, textureId, initialAnimation, r);
	}
	
	public Shooter(Point p, float speed, String textureId, PlayerAnimation initialAnimation, Rol r){
		super(99, p, speed, textureId, initialAnimation, r);
	
		this.initialPosition = this.defaultPosition.clone();
	}
	
	public float getTextureSize(){
		return 175*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	}
	
	public Point getRotationCenter(){
		return new Point(35,35);
	}
	
	
	/* methods */
	
		
	public void animateMoveAndStartCooldowns(Point destination){
		//Debug.d("gk", "goTo()");
		//Debug.d("goTo(): "+(int)destination.getX()+","+(int)destination.getY());
		this.destination = destination;
		animateRun();
		
		this.isOnRunningCooldown = true;
		TimerHelper.startTimer(this.position, 0.3f,  new ITimerCallback() {                      
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	setRunningEnd();
            }
        });
	}
	
	public void animateShootAndStartCooldowns(Point destination){
		this.shootTarget = destination;
		animateShoot();
		
		// Cuan sels hi dona una ordre player estan "busy" una estona. Aixi la animacio de correr no xafa la de xutar.
		this.isOnShootingCooldown = true;
		TimerHelper.startTimer(this.position, 0.5f,  new ITimerCallback() {                      
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
            	setShootingEnd();
            }
        });
	}
	
	public void forceStopMovement(){
		removeOldMovementOrders();
		animateStop();
	}
	
	public void animateStopAndStartCooldowns(){
		//testing code
		if(this.position.getEntityModifierCount() > 1){
			Debug.d("ball","ALERT: paramos animacion jugador y tenemos MODIFIERS acumulados: "+this.position.getEntityModifierCount());
		}// testing code
		
		this.destination = null;
		animateStop();
	}
	
	public void animateAplastarAndStartCooldowns(){
		if(!GameSettings.getInstance().godsFingerEnabled){
			return;
		}
		
		if(this.isOnAplastadoCooldown){
			return;
		}
		
		Debug.d("aplastando jugador!");
		this.isOnAplastadoCooldown = true;
		
		forceStopMovement();
		
		animate(PlayerAnimation.APLASTADO);
		TimerHelper.startTimer(this.position, 3f,  new ITimerCallback() 
		{                      
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	animate(initialAnimation);
            	
            	TimerHelper.startTimer(position, 1f,  new ITimerCallback() {                      
                    
                    public void onTimePassed(final TimerHandler pTimerHandler){
                    	setAplastadoEnd();
                    }
                });
            }
        });
		
		TimerHelper.startTimer(this.position, 2.5f,  new ITimerCallback()
		{                      
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	final Path path = new Path(10).to(position.getX()+2, position.getY())
            								.to(position.getX()-2, position.getY())
            								.to(position.getX()+2, position.getY())
            								.to(position.getX()-2, position.getY())
            								.to(position.getX()+2, position.getY())
            								.to(position.getX()-2, position.getY())
            								.to(position.getX()+2, position.getY())
            								.to(position.getX()-2, position.getY())
            								.to(position.getX()+2, position.getY())
            								.to(position.getX(), position.getY());
            	
            	PathModifier moveModifier = new PathModifier(0.5f, path);
        		position.registerEntityModifier(moveModifier);
            }
        });
	}
	
	
	
	public void animate(PlayerAnimation a){
		if(this.lastAnimation == a){
			return;
		}
		ShooterAnimation.animate(a, sprite);
		
		this.lastAnimation = a;
	}
	
	public void removeOldMovementOrders(){
		this.destination = null;
		this.isOnJumpingCooldown = false;
		this.isOnRunningCooldown = false;
		super.removeOldMovementOrders();

	}
}
