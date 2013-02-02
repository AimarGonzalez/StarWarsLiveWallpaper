package com.mel.wallpaper.starWars.entity;

import java.util.ArrayList;
import java.util.List;

import com.mel.entityframework.IEntity;
import com.mel.wallpaper.starWars.entity.Walker.Rol;
import com.mel.wallpaper.starWars.view.PlayerAnimation;
import com.mel.wallpaper.starWars.view.SpriteFactory;


public class Map implements IEntity
{
	public List<Walker> walkers = new ArrayList<Walker>();
	public InvisibleWalls walls;
	
	public static enum Status{
		INITIAL_STATE, LOADING,INTRO, GOTO_INITIAL_POS, RESUME_GAME, WAIT_A_SECOND, PLAYING, GOAL_CINEMATIC, PERFORM_OUTSIDE, PAUSE
	}
	public static enum Event{
		PAUSE, RESUME, SHOW_INTRO, START_PLAYING, GOAL, BALL_OUT_OF_BOUNDS
	}
	
	public Status status = Map.Status.INITIAL_STATE;
		
	
	public Map(InvisibleWalls walls){
		
		this.walls = walls;

		walkers.add(new Jumper(walkers.size()+1, -300, 0, SpriteFactory.BENJI, PlayerAnimation.STOP_E));
		walkers.add(new Walker(walkers.size()+1, 0, 200, SpriteFactory.MP_WHITE,PlayerAnimation.STOP_S, Rol.JEDI));
		walkers.add(new Walker(walkers.size()+1, 200, -200, SpriteFactory.MP_WHITE,PlayerAnimation.STOP_S, Rol.JEDI));
		walkers.add(new Walker(walkers.size()+1, 200, 200, SpriteFactory.MP_WHITE,PlayerAnimation.STOP_S, Rol.JEDI));
		walkers.add(new Walker(walkers.size()+1, -200, 200, SpriteFactory.MP_WHITE,PlayerAnimation.STOP_S, Rol.JEDI));
		
	}
	
	
	public void recycle(){
		this.status = Map.Status.INITIAL_STATE;
		
		//mover los jugadores a posicion inicial
	}
	
	
		
}
