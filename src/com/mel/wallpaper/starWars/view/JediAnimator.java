package com.mel.wallpaper.starWars.view;

import org.andengine.util.math.MathUtils;

import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.JediKnight;

//Animacion y medidas basadas en JEDI
public class JediAnimator extends WalkerAnimator implements IJediAnimator{
	
	public static float SPRITE_WIDTH= 175f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float SPRITE_HEIGHT = 175f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float VERTICAL_CENTER = 0.2f*SPRITE_HEIGHT*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static String TEXTURE_ID = SpriteFactory.STORM_TROOPER;
	
	
	public JediAnimator(String textureID, float speed){
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
		animate(a);
	}

	public void animateParry(JediKnight jedi, Point destination){
		Animation a = calculateParryAnimation(jedi.getPosition().toPoint(), destination);
		animate(a);
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
	
	
	
	protected void animate(Animation a){
		if(a==null){
			a = Animation.STOP_S;
		}
		
		if(this.lastAnimation == a){
			return;
		}
		
		long tileDuration = 200;
		long swordCutDuration = 110;
		switch(a) {
			case WALK_E: //derecha
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration},new int[]{3,2,1,0}, true); //fila1
				break;
			case WALK_W: //izquierda
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 15, 18, true);  //fila2 
				break;
			case WALK_N: //arriba
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 45, 48, true); //fila3
				break;
			case WALK_S: //abajo
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 30, 33, true); //fila4 
				break;
			case STOP_S: //abajo
				sprite.animate(new long[]{300, 300}, 19, 20, true);
				break;
			case STOP_N: //arriba
				sprite.animate(new long[]{300, 300}, 4, 5, true);
				break;
			case STOP_W: //izquierda
				sprite.animate(new long[]{300, 300}, 19, 20, true);
				break;
			case STOP_E: //derecha
				sprite.animate(new long[]{300, 300}, 4, 5, true);
				break;
			case FIGHT_W: //izquierda
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration}, new int[]{22,23,21,25,26, 19}, false); //mariposa?
				break;
			case FIGHT_E: //derecha
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration}, new int[]{7,8,6,10,11, 4}, false); //mariposa?
				break;
//			case FIGHT_N: //arriba
//				sprite.animate(new long[]{500,100},  new int[]{51, 34}, false);
//				break;
			case PARRY_W: //izquierda
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration}, new int[]{22,23,21,25,26, 19}, false); //mariposa?
				break;
			case PARRY_E: //derecha
				sprite.animate(new long[]{swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration,swordCutDuration}, new int[]{7,8,6,10,11, 4}, false); //mariposa?
				break;
//			case PARRY_N: //arriba
//				sprite.animate(new long[]{500,100},  new int[]{51, 34}, false);
//				break;
				
			case APLASTADO:
//				if(this.textureId == SpriteFactory.MARC){
//					sprite.animate(new long[]{200,200}, new int[]{4, 6}, true);
//				}else{
					//sprite.stopAnimation(MathUtils.random(4, 6)); //aqui habra que poner un random, y quizas una rotacion?
				//}
				break;
			default: //parado_s
				sprite.animate(new long[]{400, 400}, 19, 20, true);
		}
		
		this.lastAnimation = a;
	}


	
	
}
