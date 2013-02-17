package com.mel.wallpaper.starWars.view;

import org.andengine.entity.sprite.AnimatedSprite;

import com.mel.util.MathUtil;
import com.mel.util.Point;

//Animacion y medidas basadas en STORM_TROOPER
public class ShooterAnimator extends WalkerAnimator implements IShooterAnimator{
	
	public static float SPRITE_WIDTH= 175f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float SPRITE_HEIGHT = 175f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float VERTICAL_CENTER = 36f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static String TEXTURE_ID = SpriteFactory.STORM_TROOPER;
	
	
	public ShooterAnimator(String textureID, float speed){
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
	public void animateShoot(Position origin, Point destination){
		animateShoot(origin.toPoint(), destination);
	}
	
	public void animateShoot(Point origin, Point destination){
		Animation a = calculateShootAnimation(origin, destination);
		animate(a);
	}
	
	
	//ANIMATION MATHS
	protected Animation calculateShootAnimation(Point origin, Point destination){
		double angulo = MathUtil.getAngulo(origin.getX(), origin.getY(), destination.getX(), destination.getY());
		
		if(angulo>=0 && angulo<MathUtil.PI_Q){
			return Animation.SHOOT_E;
		}
		
		if(angulo>=MathUtil.PI_Q && angulo<3*MathUtil.PI_Q){
			return Animation.SHOOT_N;
		}
		
		if(angulo>=3*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
			return Animation.SHOOT_W;
		}
		
		if(angulo>=5*MathUtil.PI_Q && angulo<7*MathUtil.PI_Q){
			return Animation.SHOOT_S;
		}
		
		if(angulo>=7*MathUtil.PI_Q && angulo<MathUtil.PI_TWICE){
			return Animation.SHOOT_E;
		}
		
		
		throw new RuntimeException("Error calculando angulo!");
	}
	
	
	
	protected void animate(Animation a){
		if(this.lastAnimation == a){
			return;
		}
		
		long tileDuration = 200;
		switch(a) {
			case WALK_E: //derecha
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration},new int[]{0,1,2,3}, true); //fila1
				break;
			case WALK_W: //izquierda
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 5, 8, true);  //fila2 
				break;
			case WALK_N: //arriba
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 10, 13, true); //fila3
				break;
			case WALK_S: //abajo
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 10, 13, true); //fila4 
				break;
			case STOP_S: //abajo
				sprite.stopAnimation(13); //fila5
				break;
			case STOP_N: //arriba
				sprite.stopAnimation(13); 
				break;
			case STOP_W: //izquierda
				sprite.stopAnimation(5);  //fila6 
				break;
			case STOP_E: //derecha
				sprite.stopAnimation(1); 
				break;
			case SHOOT_S: //abajo
				sprite.animate(new long[]{100,300},  new int[]{15, 16}, false); //fila invisibles
				break;
			case SHOOT_W: //izquierda
				sprite.animate(new long[]{100,300},  new int[]{15, 16}, false); //fila invisibles
				break;
			case SHOOT_E: //derecha
				sprite.animate(new long[]{100,300},  new int[]{15, 16}, false); //fila invisibles
				break;
			case SHOOT_N: //arriba
				sprite.animate(new long[]{100,300},  new int[]{15, 16}, false); //fila invisibles
				break;
		
			case APLASTADO:
//				if(this.textureId == SpriteFactory.MARC){
//					sprite.animate(new long[]{200,200}, new int[]{4, 6}, true);
//				}else{
					//sprite.stopAnimation(MathUtils.random(4, 6)); //aqui habra que poner un random, y quizas una rotacion?
				//}
				break;
			default: //parado_s
				sprite.stopAnimation(13);  //fila5
		}
		
		this.lastAnimation = a;
	}


	
	
}
