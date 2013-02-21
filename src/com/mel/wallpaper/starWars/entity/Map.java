package com.mel.wallpaper.starWars.entity;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.sprite.Sprite;

import com.mel.entityframework.IEntity;
import com.mel.wallpaper.starWars.entity.Walker.Rol;
import com.mel.wallpaper.starWars.view.Animation;
import com.mel.wallpaper.starWars.view.Position;
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
		
//		//walkers.add(new Walker(-300, 00, SpriteFactory.MP_WHITE,Animation.STOP_S, Rol.JEDI));
//		walkers.add(new JediKnight(-300, 00, SpriteFactory.LUKE,Animation.STOP_S, Rol.JEDI));
//		walkers.add(new JediKnight(300, 00, SpriteFactory.LUKE,Animation.STOP_S, Rol.JEDI));
//		walkers.add(new Shooter(200, 200, SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
//		walkers.add(new Shooter(-200, 200, SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
//		walkers.add(new Shooter(-200, 200, SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
//		walkers.add(new Shooter(-200, 200, SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));

		walkers.add(new JediKnight(walls.getRandomPoint(), 35, SpriteFactory.LUKE,Animation.STOP_S, Rol.JEDI));
		walkers.add(new JediKnight(walls.getRandomPoint(), 35, SpriteFactory.LUKE,Animation.STOP_S, Rol.JEDI));
		walkers.add(new JediKnight(walls.getRandomPoint(), 30, SpriteFactory.DARTH_VADER,Animation.STOP_S, Rol.JEDI));
		walkers.add(new JediKnight(walls.getRandomPoint(), 30, SpriteFactory.DARTH_VADER,Animation.STOP_S, Rol.JEDI));
		walkers.add(new Shooter(walls.getRandomPoint(), SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
		walkers.add(new Shooter(walls.getRandomPoint(), SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
		walkers.add(new Shooter(walls.getRandomPoint(), SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
		walkers.add(new Shooter(walls.getRandomPoint(), SpriteFactory.STORM_TROOPER, Animation.STOP_S, Rol.STORM_TROOPER));
	}
	
	
	public void recycle(){
		this.status = Map.Status.INITIAL_STATE;
		
		//mover los jugadores a posicion inicial
	}


	public Sprite getSprite() {
		return null;
	}


	public Position getPosition() {
		return null;
	}
	
	
		
}
