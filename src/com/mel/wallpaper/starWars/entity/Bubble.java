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
		
//		BUBBLE_NOTE_GREEN("bubbles/bubble-nota-green.png", 166, 120, null), // null for random sample
//		BUBBLE_NOTE_WHITE("bubbles/bubble-nota-white.png", 166, 120, null), // null for random sample
//		BUBBLE_NOTE_BLUE("bubbles/bubble-nota-blue.png", 162, 130, null),
//		BUBBLE_MUSIC_BLUE("bubbles/bubble-music-blue.png", 162, 130, null),
		
//		BUBBLE_OLA_K_ASE("bubbles/bubble-ola-k-ase.png", 166, 120, Sample.HOLA_K_ASE),
		
		
		BUBBLE_EWOKS2("bubbles/ewoks_2.png", 175, 143, Sample.EWOKS_2),
		BUBBLE_HOLA_K_ASE("bubbles/hola_k_ase.png", 159, 139, Sample.HOLA_K_ASE),
		BUBBLE_EWOKS_WISTLE1("bubbles/ewoks_wistle1.png", 162, 130, Sample.EWOKS_WISTLE1),
		BUBBLE_SPACEPELOTAS("bubbles/spacepelotas.png", 194, 144, Sample.SPACEPELOTAS),
		BUBBLE_TINTINTIRIRIN("bubbles/tintintiririn.png", 222, 143, Sample.TINTINTIRIRIN),	
		BUBBLE_JUAN("bubbles/juaaan_2.png", 162, 130, Sample.JUAAAN_2),
		BUBBLE_PAYANO("bubbles/payanooo.png", 175, 130, Sample.PAYANOOO),

		BUBBBE_A_PADRE_2("bubbles/a_padre 2.png", 187, 156, Sample.A_PADRE_2),
		BUBBLE_A_OSCURO("bubbles/a_oscuro.png", 187, 156, Sample.A_OSCURO),
		BUBBLE_A_OBI("bubbles/a_obi.png", 199, 130, Sample.A_OBI),
		BUBBLE_VADER("bubbles/vader.png", 187, 156, Sample.VADER),
		
		BUBBLE_A_ORDENES("bubbles/a_ordenes.png", 175, 143, Sample.A_ORDENES),
		BUBBLE_EWOKS3("bubbles/ewoks_3.png", 173, 141, Sample.EWOKS_3),
		BUBBLE_A_EWOK("bubbles/a_ewok scur.png", 211, 170, Sample.A_EWOK_SCUR),
		BUBBLE_A_DUCADOS("bubbles/a_ducados.png", 187, 155, Sample.A_DUCADOS),
		BUBBLE_A_NAPALM("bubbles/a_napalm.png", 232, 167, Sample.A_NAPALM),
		BUBBLE_PEINANDO("bubbles/peinando.png", 199, 156, Sample.PEINANDO),
		BUBBLE_PINYAU("bubbles/pinyau.png", 166, 120, Sample.PINYAU),

		BUBBLE_CHIU("bubbles/chiu.png", 166, 120, Sample.CHIU);
		
		private float BUUBLE_SCALE = 0.5f; 


		
		private final String spritePath;
		private final Sample sound;
		private final int width;
		private final int height;
		
		private BubbleType(String spriteName, int width, int height, Sample sound)
		{
			this.spritePath = spriteName;
			this.width = width;
			this.height = height;
			this.sound = sound;
		}
		
		public Sprite getNewSprite()
		{
			Sprite sprite = SpriteFactory.getMe().newSprite(spritePath,
					   							   width*BUUBLE_SCALE,
					   							   height*BUUBLE_SCALE);
			
			sprite.setRotationCenter(width*BUUBLE_SCALE/2,
					height*BUUBLE_SCALE/2);
			
			return sprite;
		}
		
		public void registerTexture()
		{
			SpriteFactory.getMe().registerTexture(spritePath,spritePath, width, height);
		}
		
		public static BubbleType getRandomBubble()
		{
			BubbleType types[] = values();
			
			return types[(int)(Math.random()*(double)types.length)];
		}
		
		public String toString() {
			return spritePath;
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
		return this.sprite.getWidth()/3;
	}
	public float getSpriteOffsetY(){
		return WalkerAnimator.SPRITE_HEIGHT + this.sprite.getHeight()/2;
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
