package com.mel.wallpaper.starWars.process;

import java.util.Collections;
import java.util.List;

import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Shooter;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.entity.commands.Command;
import com.mel.wallpaper.starWars.entity.commands.MoveCommand;
import com.mel.wallpaper.starWars.entity.commands.ShootLaserCommand;
import com.mel.wallpaper.starWars.entity.commands.WaitCommand;

public class WalkersProcess extends Process
{
	private Map map;
	private Game game;
	private List<Walker> walkers;
	
	private List<Shooter> shooters;
	
	
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

		this.shooters = (List<Shooter>) game.getEntities(Shooter.class);

		this.walkers = (List<Walker>) game.getEntities(Jumper.class);
		this.walkers.addAll(game.getEntities(Walker.class));	
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
		
		
		for(Shooter shooter : shooters){
			if(shooter.canShoot()){
				ShootLaserCommand laserCmd = new ShootLaserCommand(shooter,game);
				laserCmd.destination = InvisibleWalls.getRandomPoint();
				shooter.addCommand(laserCmd);
			}else{
				if(shooter.isIdle()){
					if(Math.random()<0.5){
						MoveCommand move = new MoveCommand(shooter);
						move.setMovable(shooter);
						move.destination = InvisibleWalls.getRandomPoint();
						shooter.addCommand(move);
					}else{
						WaitCommand wait = new WaitCommand(shooter, MathUtils.random(2f, 4f));
						shooter.addCommand(wait);
					}
				}
			}
		
		}
		
		executeShooterCommands();
		
		
		for(Walker walker : walkers) {
			
			if(walker.isIdle()){
				if(Math.random()<0.5){
					MoveCommand move = new MoveCommand(walker);
					move.setMovable(walker);
					move.destination = InvisibleWalls.getRandomPoint();
					walker.addCommand(move);
				}else{
					WaitCommand wait = new WaitCommand(walker, MathUtils.random(2f, 4f));
					walker.addCommand(wait);
				}
			}
		}
	
		
		executeWalkerCommands();	
	}

	private void executeShooterCommands(){
		for(Shooter shooter:this.shooters){
			for(Command c : shooter.pendingCommands){
				c.execute(this.map);
			}
			shooter.clearPendingCommands();
		}
	}
	
	private void executeWalkerCommands(){
		for(Walker walker:this.walkers){
			for(Command c : walker.pendingCommands){
				c.execute(this.map);
			}
			walker.clearPendingCommands();
		}
	}
	
	
	
}
