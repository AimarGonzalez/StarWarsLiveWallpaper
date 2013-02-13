package com.mel.wallpaper.starWars.view;

import org.andengine.util.math.MathUtils;

import com.mel.util.Point;

//Animacion y medidas basadas en Portero de Futbol
public class GoalKeeperAnimator extends ShooterAnimator{
	
	public static float SPRITE_WIDTH = 4f*70f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	public static float SPRITE_HEIGHT = 4f*70f*SpriteFactory.PLAYERS_SPRITE_SCALEFACTOR;
	
	private Animation initialAnimation;
	
	public GoalKeeperAnimator(String textureId, float speed){
		super(textureId, speed);
	}
	
	
	public float getSpriteOffsetX(){
		return SPRITE_WIDTH/2;
	}
	
	public float getSpriteOffsetY(){
		return SPRITE_HEIGHT/2;
	}
	
	public Point getSpriteDimensions() {
		return new Point(SPRITE_WIDTH, SPRITE_HEIGHT);
	}
	
	public Point getRotationCenter(){
		return new Point(35,35);
	}
	
	public Animation getInitialAnimation() {
		return this.initialAnimation;
	}
	
	public void setInitialAnimation(Animation a) {
		//implement only if needed
		this.initialAnimation = a;
	}
	
	//ANIMATIONS
	public void animateRun(){
		animate(this.initialAnimation);
	}
	
	public void animateJump(Point origin, Point destination){
		Animation a = calculateJumpAnimation(origin, destination);
		animate(a);
	}
	
	
	public void animateStop(){
		animate(this.initialAnimation);
	}
	
	
	public void animateShoot(){
		Animation a = calculateShootAnimation();
		animate(a);
	}
	
	//ANIMATION MATHS
	protected Animation calculateShootAnimation(){
		switch (initialAnimation) {
			case STOP_E:
				return Animation.SHOOT_E;
			case STOP_W:
				return Animation.SHOOT_W;
			default:
				return initialAnimation;
		}
		
	}
	
	protected Animation calculateJumpAnimation(Point origin, Point destination){
		float direction = destination.getY() - origin.getY();
		switch (initialAnimation) {
			case STOP_E:
				if(direction > 0){
					return Animation.JUMP_NE;
				}else if(direction < 0){
					return Animation.JUMP_SE;
				}
				break;
			case STOP_W:
				if(direction > 0){
					return Animation.JUMP_NW;
				}else if(direction < 0){
					return Animation.JUMP_SW;
				}
				break;
			default:
				return initialAnimation;
		}
		
		return initialAnimation;
	}
	
	
	protected void animate(Animation a){
		if(this.lastAnimation == a){
			return;
		}
		
		//sprite.setRotation(0);
		
		//TODO: este codigo habria que convertirlo en un ImageXXXanimationManager, para poder tener players con distintas imagenes (y a su vez distintas animaciones).
		switch(a) {
			case STOP_E: //derecha
				sprite.stopAnimation(0); 
				break;
			case STOP_W: //izquierda
				sprite.stopAnimation(8);  //fila6 
				break;
			case SHOOT_E: //derecha
				sprite.animate(new long[]{500,100}, new int[]{3,0}, false);
				break;
			case SHOOT_W: //izquierda
				sprite.animate(new long[]{500,100}, new int[]{11,8}, false);
				break;
			case PAS_E: //derecha
				sprite.animate(new long[]{200,200,100}, new int[]{1,2,0}, false);
				break;
			case PAS_W: //izquierda
				sprite.animate(new long[]{200,200,100}, new int[]{10,9,8}, false);
				break;
				
			case JUMP_SE: //derecha
				sprite.animate(new long[]{200,400,100}, new int[]{4,5,0}, false);
				break;
			case JUMP_NE: //izquierda
				sprite.animate(new long[]{200,400,100}, new int[]{6,7,0}, false);
				break;
			case JUMP_SW: //derecha
				sprite.animate(new long[]{200,400,100}, new int[]{12,13,8}, false);
				break;
			case JUMP_NW: //izquierda
				sprite.animate(new long[]{200,400,100}, new int[]{14,15,8}, false);
				break;
			
			case APLASTADO:
				sprite.stopAnimation(MathUtils.random(16, 17)); //aqui habra que poner un random, y quizas una rotacion?
				//sprite.setRotation(MathUtils.random(0, 360)); //random
				break;
			default: //parado_s
				animate(getInitialAnimation());  //fila5
		}
		this.lastAnimation = a;
	}
	
	
	
	
}
