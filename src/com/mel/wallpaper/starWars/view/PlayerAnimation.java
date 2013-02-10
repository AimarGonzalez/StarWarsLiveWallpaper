package com.mel.wallpaper.starWars.view;

import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Walker;

public enum PlayerAnimation {
	WALK_N, WALK_S, WALK_E, WALK_W, 
	STOP_N, STOP_S, STOP_E, STOP_W,
	SEG_N, SEG_S, SEG_E, SEG_W,
	PAS_N, PAS_S, PAS_E, PAS_W,
	SHOOT_N, SHOOT_S, SHOOT_E, SHOOT_W,
	APLASTADO,
	JUMP_NE,JUMP_NW,JUMP_SE,JUMP_SW
	;
	

	public static PlayerAnimation calculateStopAnimationDirection(PlayerAnimation lastAnimation){
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
	
	public static PlayerAnimation calculatePassAnimationDirection(Walker p, Point destination){
		double angulo = MathUtil.getAngulo(p.position.getX(), p.position.getY(), destination.getX(), destination.getY());
		
		if(angulo>=0 && angulo<MathUtil.PI_Q){
			return PlayerAnimation.PAS_E;
		}
		
		if(angulo>=MathUtil.PI_Q && angulo<3*MathUtil.PI_Q){
			return PlayerAnimation.PAS_N;
		}
		
		if(angulo>=3*MathUtil.PI_Q && angulo<5*MathUtil.PI_Q){
			return PlayerAnimation.PAS_W;
		}
		
		if(angulo>=5*MathUtil.PI_Q && angulo<7*MathUtil.PI_Q){
			return PlayerAnimation.PAS_S;
		}
		
		if(angulo>=7*MathUtil.PI_Q && angulo<MathUtil.PI_TWICE){
			return PlayerAnimation.PAS_E;
		}
		
		
		throw new RuntimeException("Error calculando angulo!");
	}
	
	public static PlayerAnimation calculateShootAnimationDirection(Walker p, Point destination){
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
	
	public static PlayerAnimation calculateRunAnimationDirection(Walker p, Point destination){
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
}
