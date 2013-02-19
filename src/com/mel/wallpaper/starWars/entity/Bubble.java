package com.mel.wallpaper.starWars.entity;

import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;

import com.mel.entityframework.IEntity;
import com.mel.entityframework.IMovable;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.view.Position;
import com.mel.wallpaper.starWars.view.SpriteFactory;
import com.mel.wallpaper.starWars.view.WalkerAnimator;

public class Bubble implements IEntity, IMovable
{		
	public static final float DEFAULT_SPEED = 10f;
	public float speed;
	
	public static final float BEAM_SIZE = 50f;
	
	public Position position;
	public Sprite sprite;
	
	public Point origin;
	public Point destination;
	
	public boolean isFinished = false;
	
	/* Constructor */
	public Bubble(float x, float y){
		this(new Position(x,y));
	}
	public Bubble(Position p){
		this.position = (Position) p.clone();
		this.speed = Bubble.DEFAULT_SPEED;
		this.sprite = (Sprite) SpriteFactory.getMe().newSprite(SpriteFactory.BUBBLE1,
															   BEAM_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR,
															   BEAM_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR);
		this.sprite.setRotationCenter(BEAM_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR/2,
									  BEAM_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR/2);
	}
	
	/* Getters/Setters */
	public Position getPosition() {
		return this.position;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Point getSpriteOffset(){
		Point spriteCenter = new Point(getSpriteOffsetX(), getSpriteOffsetY());
		return spriteCenter;
	}
	
	public float getSpriteOffsetX(){
		return this.sprite.getWidth()/2;
	}
	public float getSpriteOffsetY(){
		return (WalkerAnimator.SPRITE_HEIGHT);
	}
	
	public float getTotalDistance(){
		return this.origin.distance(this.destination);
	}

	public float getTraveledDistance(){
		return this.position.distance(this.origin);
	}
	
	public boolean isBusy(){
		return false;
	}
	
	public boolean isMoving(){
		return this.destination != null;
	}
	
	/* Methods */
	public void animateMoveAndStartCooldowns(Point destination) {
		this.origin = position.toPoint();
		this.destination = destination;
	}
	
	public void movementEnd(){
		this.destination = null;
	}
	
	public void animateStopAndStartCooldowns(){
		//testing code
		if(this.position.getEntityModifierCount() > 1){
			Debug.d("ball","ALERT: paramos animacion jugador y tenemos MODIFIERS acumulados: "+this.position.getEntityModifierCount());
		}// testing code
		
		this.destination = null;
		//animateStop(); //no aplica
	}
	
	public void forceStopMovement(){
		removeOldMovementOrders();
	}
	
	public void removeOldMovementOrders(){
		this.destination = null;
		this.position.unregisterEntityModifiers(new IEntityModifier.IEntityModifierMatcher()
		{
			public boolean matches(IModifier<org.andengine.entity.IEntity> pObject) {
				boolean matches = pObject.getClass().equals(PathModifier.class) || pObject.getClass().equals(MoveModifier.class);
				return matches;
			}
		});
	}
	
	public void recycle(){
		this.speed = Bubble.DEFAULT_SPEED;

		this.sprite.clearEntityModifiers();
		this.sprite.clearUpdateHandlers();
		
		this.position.setLocation(0, 0);
		this.position.clearEntityModifiers();
		this.position.clearUpdateHandlers();

		this.sprite.detachSelf();
	}
}
