package com.mel.wallpaper.starWars.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.entity.commands.Command;
import com.mel.wallpaper.starWars.entity.commands.MoveCommand;
import com.mel.wallpaper.starWars.entity.commands.StopCommand;



public class WalkersProcess extends Process
{
	private Map partido;
	private List<Walker> jedis;
	
	
	public WalkersProcess(Map partido){
	}
	
	@Override
	public void onAddToGame(Game game){
		this.partido = (Map)game.getEntity(Map.class);

		this.jedis = (List<Walker>) game.getEntities(Jumper.class);
		this.jedis.addAll(game.getEntities(Walker.class));
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(jedis != null){
			jedis.clear();
			jedis = null;
		}
		
		this.partido = null;
	}
	
	@Override
	public void update(){
		
		//TODO:add commands to each player
		
		executeCommandsByRandomPlayer();
		
	}
	

	
	private void executeCommandsByRandomPlayer(){
		Collections.shuffle(this.jedis);
		for(Walker player:this.jedis){
			for(Command c:player.pendingCommands){
				c.execute(this.partido);
			}
			player.clearPendingCommands();
		}
	}
	
	
	
}
