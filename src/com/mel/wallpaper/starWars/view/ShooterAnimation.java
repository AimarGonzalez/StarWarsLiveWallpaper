package com.mel.wallpaper.starWars.view;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.math.MathUtils;

import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Walker;

public class ShooterAnimation {
	
	
	
	
	public static PlayerAnimation calculateStopAnimation(PlayerAnimation lastAnimation){
		switch(lastAnimation) {
			case WALK_E: //derecha
				return PlayerAnimation.STOP_E;
			case WALK_W: //izquierda
				return PlayerAnimation.STOP_W; 
			case WALK_N: //arriba
				return PlayerAnimation.STOP_N;
			case WALK_S: //abajo
				return PlayerAnimation.STOP_S; 
			
			default: //parado_s
				return PlayerAnimation.STOP_S;
		}
	}
	
	
	public static PlayerAnimation calculateShootAnimation(Walker p, Point destination){
		double angulo = MathUtil.getAngulo(p.position.getX(), p.position.getY(), destination.getX(), destination.getY());
		
		if(angulo>=0 && angulo<MathUtil.PI_Q){
			return PlayerAnimation.SHOOT_E;
		}
		
		if(angulo>=MathUtil.PI_Q && angulo<3*MathUtil.PI_Q){
			return PlayerAnimation.SHOOT_N;
		}
		
		if(angulo>=3*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
			return PlayerAnimation.SHOOT_W;
		}
		
		if(angulo>=5*MathUtil.PI_Q && angulo<7*MathUtil.PI_Q){
			return PlayerAnimation.SHOOT_S;
		}
		
		if(angulo>=7*MathUtil.PI_Q && angulo<MathUtil.PI_TWICE){
			return PlayerAnimation.SHOOT_E;
		}
		
		
		throw new RuntimeException("Error calculando angulo!");
	}
	
	public static PlayerAnimation calculateRunAnimation(Walker p, Point destination){
		double angulo = MathUtil.getAngulo(p.position.getX(), p.position.getY(), destination.getX(), destination.getY());
		
		if(angulo>=0 && angulo<MathUtil.PI_Q){
			return PlayerAnimation.WALK_E;
		}
		
		if(angulo>=MathUtil.PI_Q && angulo<3*MathUtil.PI_Q){
			return PlayerAnimation.WALK_N;
		}
		
		if(angulo>=3*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
			return PlayerAnimation.WALK_W;
		}
		
		if(angulo>=5*MathUtil.PI_Q && angulo<7*MathUtil.PI_Q){
			return PlayerAnimation.WALK_S;
		}
		
		if(angulo>=7*MathUtil.PI_Q && angulo<MathUtil.PI_TWICE){
			return PlayerAnimation.WALK_E;
		}
		
		return PlayerAnimation.STOP_S; //OJO!!
		//throw new RuntimeException("Error calculando angulo!");
	}
	
	public static int INITIAL_ANIMATION = 13;
	public static void animate(PlayerAnimation a, AnimatedSprite sprite){
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
				//sprite.animate(new long[]{500,100},  new int[]{48, 32}, false); //fila7 
				break;
			case SHOOT_W: //izquierda
				//sprite.animate(new long[]{500,100}, new int[]{49,40}, false);
				break;
			case SHOOT_E: //derecha
				//sprite.animate(new long[]{500,100},  new int[]{50, 42}, false);
				break;
			case SHOOT_N: //arriba
				//sprite.animate(new long[]{500,100},  new int[]{51, 34}, false);
				break;
		
			case APLASTADO:
//				if(this.textureId == SpriteFactory.MARC){
//					sprite.animate(new long[]{200,200}, new int[]{4, 6}, true);
//				}else{
					//sprite.stopAnimation(MathUtils.random(4, 6)); //aqui habra que poner un random, y quizas una rotacion?
				//}
				break;
			default: //parado_s
				sprite.stopAnimation(INITIAL_ANIMATION);  //fila5
		}
	}
	
	
	
}
