package com.mel.wallpaper.starWars.process;

import java.util.Collections;
import java.util.List;

import org.andengine.util.debug.Debug;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Shooter;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.entity.commands.Command;
import com.mel.wallpaper.starWars.entity.commands.MoveCommand;
import com.mel.wallpaper.starWars.entity.commands.ShootLaserCommand;

public class LaserBeamsProcess extends Process
{
	private Map map;
	private Game game;
	private List<LaserBeam> lasers;
	
	
	public LaserBeamsProcess(Game game, Map map) {
		this.map = map;
		this.game = game;
	}

	@Override
	public void onAddToGame(Game game){
		getEntitiesFromGame(game);
	}
	
	public void getEntitiesFromGame(Game game) {
		this.map = (Map)game.getEntity(Map.class);

		this.lasers = (List<LaserBeam>) game.getEntities(LaserBeam.class);		
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(lasers != null){
			lasers.clear();
			lasers = null;
		}
		
		this.map = null;
	}
	
	@Override
	public void update(){

		getEntitiesFromGame(game);
		
		//Debug.d("laser", "lasers: "+this.lasers.size());
		
		for(LaserBeam laser : lasers) {
			if(InvisibleWalls.isOutOfScreen(laser.position.toPoint())){
				game.removeEntity(laser);
			}else{
				//Debug.d("laser", "las_coor: "+laser.position.toPoint());
			}
		}
	
	}

	
	
	
	
}
