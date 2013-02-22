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
import com.mel.wallpaper.starWars.sound.SoundAssets;
import com.mel.wallpaper.starWars.sound.SoundAssets.Sample;
import com.mel.wallpaper.starWars.view.Position;
import com.mel.wallpaper.starWars.view.SpriteFactory;
import com.mel.wallpaper.starWars.view.WalkerAnimator;

public class Bubble implements IEntity, IMovable
{
	public enum BubbleType {
		BUBBLE_OLA_K_ASE("bubbles/bubble-ola-k-ase.png", 166, 120, Sample.HOLA_K_ASE),
		BUBBLE_NOTE_GREEN("bubbles/bubble-nota-green.png", 166, 120, null), // null for random sample
		BUBBLE_NOTE_WHITE("bubbles/bubble-nota-white.png", 166, 120, null), // null for random sample
		BUBBBE_A_PADRE_2("bubbles/a_padre 2.png", 187, 156, Sample.A_PADRE_2),
		BUBBLE_A_OSCURO("bubbles/a_oscuro.png", 187, 156, Sample.A_OSCURO),
		BUBBLE_A_ORDENES("bubbles/a_ordenes.png", 175, 143, Sample.A_ORDENES),
		BUBBLE_A_OBI("bubbles/a_obi.png", 199, 130, Sample.A_OBI),
		BUBBLE_A_EWOK("bubbles/a_ewok scur.png", 211, 170, Sample.A_EWOK_SCUR),
		BUBBLE_A_DUCADOS("bubbles/a_ducados.png", 187, 155, Sample.A_DUCADOS);
		
		private final String spriteName;
		private final Sample sound;
		private final int width;
		private final int height;
		
		private BubbleType(String spriteName, int width, int height, Sample sound)
		{
			this.spriteName = spriteName;
			this.width = width;
			this.height = height;
			this.sound = sound;
		}
		
		public Sprite getNewSprite()
		{
			Sprite sprite = SpriteFactory.getMe().newSprite(spriteName,
					   							   BUBBLE_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR,
					   							   BUBBLE_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR);
			
			sprite.setRotationCenter(BUBBLE_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR/2,
									 BUBBLE_SIZE*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR/2);
			
			return sprite;
		}
		
		public void registerTexture()
		{
			SpriteFactory.getMe().registerTexture(spriteName,spriteName, width, height);
		}
		
		public static BubbleType getRandomBubble()
		{
			BubbleType types[] = values();
			
			return types[(int)(Math.random()*(double)types.length)];
		}
		
		public String toString() {
			return spriteName;
		}
	}
	
	BubbleType type;
	
	public static final float DEFAULT_SPEED = 10f;
	public float speed;
	
	public static final float BUBBLE_SIZE = 75f;
	
	public Position position;
	public Sprite sprite;
	
	public Point origin;
	public Point destination;
	
	public boolean isFinished = false;
	
	/* Constructor */
	public Bubble(BubbleType type, float x, float y){
		this(type, new Position(x,y));
	}
	public Bubble(BubbleType type, Position p){
		this.type = type;
		this.position = (Position) p.clone();
		this.speed = Bubble.DEFAULT_SPEED;
		this.sprite = type.getNewSprite();
	}
	
	public static void registerTextures()
	{
		for(BubbleType type : BubbleType.values())
			type.registerTexture();
	}
	
	public void playSound()
	{
		if(type.sound!=null)
			SoundAssets.playSample(type.sound);
		else
			SoundAssets.playRandomSample();
	}
	
	public Point getSpriteOffset(){
		Point spriteCenter = new Point(getSpriteOffsetX(), getSpriteOffsetY());
		return spriteCenter;
	}
	
	public float getSpriteOffsetX(){
		return this.sprite.getWidth()/2;
	}
	public float getSpriteOffsetY(){
		return (WalkerAnimator.SPRITE_HEIGHT+30);
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

	public void recycle(){
		this.speed = Bubble.DEFAULT_SPEED;

		this.sprite.clearEntityModifiers();
		this.sprite.clearUpdateHandlers();
		
		this.position.setLocation(0, 0);
		this.position.clearEntityModifiers();
		this.position.clearUpdateHandlers();

		this.sprite.detachSelf();
	}
	
	/*
	 * IEntity methods
	 */
	
	public Sprite getSprite() {
		return sprite;
	}
	
	/* 
	 * IMovable methods
	 */
	
	public Position getPosition() {
		return this.position;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
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
}
