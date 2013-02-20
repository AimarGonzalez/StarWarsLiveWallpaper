package com.mel.wallpaper.starWars.view;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.math.MathUtils;

import com.mel.util.MathUtil;
import com.mel.util.Point;


//Animación y medidas basadas en Jugador de Futbol
public class WalkerAnimator  extends Animator implements IWalkerAnimator{

	public static float SPRITE_WIDTH = 4f*40f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float SPRITE_HEIGHT = 4f*40f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float VERTICAL_CENTER = 4f*5f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	
	public Animation lastAnimation;
	
	protected String textureId;
	
	public WalkerAnimator(String textureId, float speed){
		super(textureId, speed);
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
		return new Point(25,30);
	}
	
	//ANIMATIONS
	public void animateWalk(Position origin, Point destination){
		animateWalk(origin.toPoint(), destination);
	}
	
	public void animateWalk(Point origen, Point destination){
		Animation a = calculateWalkAnimation(origen, destination);
		animate(a);
	}
	
	public void animateStop(){
		Animation a = calculateStopAnimation();
		animate(a);
	}
	
	
	//ANIMATION MATHEMATICS
	protected Animation calculateWalkAnimation(Point origen, Point destination){
		double angulo = MathUtil.getAngulo(origen.getX(), origen.getY(), destination.getX(), destination.getY());
		
		if(angulo>=0 && angulo<MathUtil.PI_Q){
			return Animation.WALK_E;
		}
		
		if(angulo>=MathUtil.PI_Q && angulo<3*MathUtil.PI_Q){
			return Animation.WALK_N;
		}
		
		if(angulo>=3*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
			return Animation.WALK_W;
		}
		
		if(angulo>=5*MathUtil.PI_Q && angulo<7*MathUtil.PI_Q){
			return Animation.WALK_S;
		}
		
		if(angulo>=7*MathUtil.PI_Q && angulo<MathUtil.PI_TWICE){
			return Animation.WALK_E;
		}
		
		return Animation.STOP_S; //OJO!!
		//throw new RuntimeException("Error calculando angulo!");
	}
	
	protected Animation calculateStopAnimation(){
		if(lastAnimation!=null){
			switch(lastAnimation) {
				case WALK_E: //derecha
				case SHOOT_E: //derecha
				case STOP_E: //derecha
				case FIGHT_E: //derecha
				case PARRY_E: //derecha
					return Animation.STOP_E;
				case WALK_W: //izquierda
				case SHOOT_W: //izquierda
				case STOP_W: //izquierda
				case FIGHT_W: //izquierda
				case PARRY_W: //izquierda
					return Animation.STOP_W; 
				case WALK_N: //arriba
				case SHOOT_N: //arriba
				case STOP_N: //arriba
					return Animation.STOP_N;
				case WALK_S: //abajo
				case SHOOT_S: //abajo
				case STOP_S: //abajo
					return Animation.STOP_S; 
				
				default: //parado_s
					return Animation.STOP_S;
			}
		}else{
			return Animation.STOP_S; 
		}
	}
	


	//ANIMATE
	protected void animate(Animation a, boolean isInfiniteLoop){
		if(a==null){
			a = Animation.STOP_S;
		}
		
		if(isInfiniteLoop && this.lastAnimation == a){
			return;
		}
		
		long tileDuration;
		
		//TODO: este codigo habria que convertirlo en un ImageXXXanimationManager, para poder tener players con distintas imagenes (y a su vez distintas animaciones).
		switch(a) {
			case WALK_E: //derecha
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration},new int[]{3,2,1,0}, true); //fila1
				break;
			case WALK_W: //izquierda
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 8, 11, true);  //fila2 
				break;
			case WALK_N: //arriba
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 16, 19, true); //fila3
				break;
			case WALK_S: //abajo
				tileDuration =  Math.round(10000/speed);
				sprite.animate(new long[]{tileDuration, tileDuration, tileDuration, tileDuration}, 24, 27, true); //fila4 
				break;
			case STOP_S: //abajo
				sprite.stopAnimation(32); //fila5
				break;
			case STOP_N: //arriba
				sprite.stopAnimation(34); 
				break;
			case STOP_W: //izquierda
				sprite.stopAnimation(40);  //fila6 
				break;
			case STOP_E: //derecha
				sprite.stopAnimation(42); 
				break;
			case SHOOT_S: //abajo
				sprite.animate(new long[]{500,100},  new int[]{48, 32}, false); //fila7 
				break;
			case SHOOT_W: //izquierda
				sprite.animate(new long[]{500,100}, new int[]{49,40}, false);
				break;
			case SHOOT_E: //derecha
				sprite.animate(new long[]{500,100},  new int[]{50, 42}, false);
				break;
			case SHOOT_N: //arriba
				sprite.animate(new long[]{500,100},  new int[]{51, 34}, false);
				break;
			case APLASTADO:
//				if(this.textureId == SpriteFactory.MARC){
//					sprite.animate(new long[]{200,200}, new int[]{4, 6}, true);
//				}else{
					sprite.stopAnimation(MathUtils.random(4, 6)); //aqui habra que poner un random, y quizas una rotacion?
					//sprite.setRotation(MathUtils.random(0, 360)); //random
				//}
				break;
			default: //parado_s
				sprite.stopAnimation(32);  //fila5
		}
		
		this.lastAnimation = a;
	}

	



	


}
