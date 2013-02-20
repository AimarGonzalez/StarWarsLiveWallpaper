package com.mel.wallpaper.starWars.process;

import java.util.ArrayList;
import java.util.List;

import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.mel.entityframework.Game;
import com.mel.entityframework.IEntity;
import com.mel.entityframework.Process;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.JediKnight;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Shooter;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.entity.commands.Command;
import com.mel.wallpaper.starWars.entity.commands.MoveCommand;
import com.mel.wallpaper.starWars.entity.commands.ParryLaserCommand;
import com.mel.wallpaper.starWars.entity.commands.ShootLaserCommand;
import com.mel.wallpaper.starWars.entity.commands.WaitCommand;

public class WalkersProcess extends Process
{
	private Map map;
	private Game game;
	
	private List<Shooter> shooters;
	private List<JediKnight> jedis;
	private List<LaserBeam> lasers;
	
	
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
		this.jedis = (List<JediKnight>) game.getEntities(JediKnight.class);
		this.lasers = (List<LaserBeam>) game.getEntities(LaserBeam.class);
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(shooters != null){
			shooters.clear();
			shooters = null;
		}
		
		if(jedis != null){
			jedis.clear();
			jedis = null;
		}

		if(lasers != null){
			lasers.clear();
			lasers = null;
		}
		
		this.map = null;
	}
	
	@Override
	public void update(){

		getEntitiesFromGame(game);
		
		
		for(Shooter shooter : shooters){
			if(shooter.canShoot()){
				shootSomeJedi(shooter);
			}else{
				if(shooter.isIdle()){
					if(Math.random()<0.5){
						moveSomewhere(shooter);
					}else{
						waitSomeTime(shooter);
					}
				}
			}
		
		}
		executeShooterCommands();
		
		
		for(JediKnight jedi : jedis) {
			
			List<LaserBeam> closeLasers = getCloseLasers(jedi, 50);
			if(closeLasers.size()>0 && jedi.canParry()){
				parryLasers(jedi, closeLasers);
			}else if(jedi.isIdle()){
				if(Math.random()<0.8){
					moveSomewhere(jedi);
				}else{
					waitSomeTime(jedi);
				}
			}
		}
		executeJediCommands();	
		
	}

	private void waitSomeTime(Walker walker) {
		WaitCommand wait = new WaitCommand(walker, MathUtils.random(2f, 4f));
		walker.addCommand(wait);
	}

	private void moveSomewhere(Walker walker) {
		MoveCommand move = new MoveCommand(walker);
		move.setMovable(walker);
		move.destination = InvisibleWalls.getRandomPoint();
		walker.addCommand(move);
	}

	private void shootSomeJedi(Shooter shooter) {
		ShootLaserCommand laserCmd = new ShootLaserCommand(shooter,game);
		IEntity jedi =  game.getRandomEntity(JediKnight.class);
		if(jedi!=null){
			laserCmd.destination = jedi.getPosition().toPoint();
		}else{
			laserCmd.destination = InvisibleWalls.getRandomPoint();
		}
		shooter.addCommand(laserCmd);
	}
	
	
	private List<LaserBeam> getCloseLasers(JediKnight jedi, int radius){
		List<LaserBeam> closeLasers = new ArrayList<LaserBeam>();
		
		for(LaserBeam laser : this.lasers){
			if(laser.lastParringJedi != jedi &&	 laser.getPosition().distance(jedi.position)<50){
				
				closeLasers.add(laser);
			}
		}
		
		return closeLasers;
	}
	
	private void parryLasers(JediKnight jedi, List<LaserBeam> closeLasers) {
		ParryLaserCommand parryCmd;
		for(LaserBeam laser : closeLasers){
			parryCmd = new ParryLaserCommand(jedi, laser);
			
			IEntity targetJedi = null;
			if(laser.jumps > 0){
				targetJedi = game.getRandomEntity(JediKnight.class, jedi);
			}
			
			if(targetJedi!=null){
				parryCmd.destination = targetJedi.getPosition().toPoint();
			}else{
				parryCmd.destination = InvisibleWalls.getRandomPoint();
			}
			jedi.addCommand(parryCmd);
		}
	}

	private void executeShooterCommands(){
		for(Shooter shooter:this.shooters){
			for(Command c : shooter.pendingCommands){
				c.execute(this.map);
			}
			shooter.clearPendingCommands();
		}
	}

	private void executeJediCommands(){
		for(JediKnight jedi:this.jedis){
			for(Command c : jedi.pendingCommands){
				c.execute(this.map);
			}
			jedi.clearPendingCommands();
		}
	}
	
}
