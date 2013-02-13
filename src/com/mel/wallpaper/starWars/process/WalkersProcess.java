package com.mel.wallpaper.starWars.process;

import java.util.Collections;
import java.util.List;

import org.andengine.util.debug.Debug;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Shooter;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.entity.commands.Command;
import com.mel.wallpaper.starWars.entity.commands.MoveCommand;
import com.mel.wallpaper.starWars.entity.commands.ShootLaserCommand;

public class WalkersProcess extends Process
{
	private Map map;
	private Game game;
	private List<Walker> walkers;
	
	
	public WalkersProcess(Game game, Map map) {
		this.map = map;
		this.game = game;
	}

	@Override
	public void onAddToGame(Game game){
		getEntitiesFromGame(game);
	}
	
	public void getEntitiesFromGame(Game game) {
		this.map = (Map)game.getEntity(Map.class);

		this.walkers = (List<Walker>) game.getEntities(Jumper.class);
		this.walkers.addAll(game.getEntities(Walker.class));
		this.walkers.addAll(game.getEntities(Shooter.class));		
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(walkers != null){
			walkers.clear();
			walkers = null;
		}
		
		this.map = null;
	}
	
	@Override
	public void update(){

		getEntitiesFromGame(game);
		
		for(Walker walker : walkers) {
			
			if(walker.hasDestination()||walker.isBusy())
				continue;
			
			MoveCommand move = new MoveCommand(walker);
			move.setMovable(walker);
			move.destination = map.walls.getRandomPoint();
			walker.addCommand(move);
		}
	
		Shooter shooter = (Shooter)walkers.get(0);

		if(!shooter.isBusy())
		{
			ShootLaserCommand laserCmd = new ShootLaserCommand(shooter,game);
			laserCmd.destination = map.walls.getRandomPoint();
			shooter.addCommand(laserCmd);
		}
		
		executeCommandsByRandomPlayer();	
	}

	private void executeCommandsByRandomPlayer(){
		Collections.shuffle(this.walkers);
		for(Walker player:this.walkers){
			for(Command c:player.pendingCommands){
				c.execute(this.map);
			}
			player.clearPendingCommands();
		}
	}
	
	
	
}
