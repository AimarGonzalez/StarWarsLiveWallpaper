package com.mel.wallpaper.starWars.view;

import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.JediKnight;

//Animacion y medidas basadas en JEDI
public class DarthVaderAnimator extends WalkerAnimator implements IJediAnimator{
	
	public static float SPRITE_WIDTH= 175f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float SPRITE_HEIGHT = 175f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float VERTICAL_CENTER = 0.13f*SPRITE_HEIGHT*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static String TEXTURE_ID = SpriteFactory.STORM_TROOPER;
	
	
	public DarthVaderAnimator(String textureID, float speed){
		super(textureID, speed);
	}
	
	
	public float getSpriteOffsetX(){
		return SPRITE_WIDTH/2;
	}
	public float getSpriteOffsetY(){
		return SPRITE_HEIGHT-VERTICAL_CENTER;
	}
	
	public Point getSpriteDimensions() {
		return new Point(SPRITE_WIDTH, SPRITE_HEIGHT);
	}
	
	public Point getRotationCenter(){
		return new Point(35,35);
	}
	
	//ANIMATIONS
	public void animateDuel(JediKnight jedi, Point destination){
		Animation a = calculateFightAnimation(jedi.getPosition().toPoint(), destination);
		animateOnce(a);
	}

	public void animateParry(JediKnight jedi, Point destination){
		Animation a = calculateParryAnimation(jedi.getPosition().toPoint(), destination);
		animateOnce(a);
	}
	
	public void animateIdleWithSword(){
		
	}
	
	
	//ANIMATION MATHS
	protected Animation calculateFightAnimation(Point origin, Point destination){
		double angulo = MathUtil.getAngulo(origin.getX(), origin.getY(), destination.getX(), destination.getY());
		
//		if(angulo>=2*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
//			return Animation.FIGHT_W;
//		}
//		
//		if(angulo>=5*MathUtil.PI_Q && angulo<=7*MathUtil.PI_Q){
//			return Animation.FIGHT_N;
//		}
		
		if(angulo>=MathUtil.PI_HALF && angulo<3*MathUtil.PI_HALF){
			return Animation.FIGHT_W;
		}
		
		return Animation.FIGHT_E;
		
	}
	
	protected Animation calculateParryAnimation(Point origin, Point destination){
		double angulo = MathUtil.getAngulo(origin.getX(), origin.getY(), destination.getX(), destination.getY());
		
//		if(angulo>=2*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
//			return Animation.PARRY_W;
//		}
//		
//		if(angulo>=5*MathUtil.PI_Q && angulo<=7*MathUtil.PI_Q){
//			return Animation.PARRY_N;
//		}
		
		if(angulo>=MathUtil.PI_HALF && angulo<3*MathUtil.PI_HALF){
			return Animation.PARRY_W;
		}
		
		return Animation.PARRY_E;
		
	}
	
	
	protected void animate(Animation a, boolean isInfiniteLoop){
		if(a==null){
			a = Animation.STOP_S;
		}
		
		if(isInfiniteLoop && this.lastAnimation == a){
			return;
		}
		
		long tileDuration = 200;
		long swordCutDuration = 100;
		switch(a) {
			case WALK_E: //derecha
				tileDuration =  Math.round(8000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 10, 13, true);  //fila2 
				break;
			case WALK_W: //izquierda
				tileDuration =  Math.round(8000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration},new int[]{3,2,1,0}, true); //fila1
				break;
			case WALK_N: //arriba
				tileDuration =  Math.round(8000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 30, 33, true); //fila3
				break;
			case WALK_S: //abajo
				tileDuration =  Math.round(8000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 20, 23, true); //fila4 
				break;
			case STOP_S: //abajo
				sprite.stopAnimation(24);
				break;
			case STOP_N: //arriba
				sprite.stopAnimation(34);
				break;
			case STOP_W: //izquierda
				sprite.stopAnimation(4);
				break;
			case STOP_E: //derecha
				sprite.stopAnimation(14);
				break;
				
			case FIGHT_W: //izquierda
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,30}, new int[]{53,52,51,50,53, 4}, false); //mariposa
				break;
			case FIGHT_E: //derecha
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,30}, new int[]{40,41,42,43,40, 14}, false); //mariposa
				break;
			case PARRY_W: //izquierda
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,30}, new int[]{53,52,51,50,53, 4}, false); //mariposa
				break;
			case PARRY_E: //derecha
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,30}, new int[]{40,41,42,43,40, 14}, false); //mariposa
				break;
				
			default: //parado_s
				Debug.d("default", "animate default!");
				sprite.stopAnimation(24);
		}
		
		this.lastAnimation = a;
	}
	


	
	
}
