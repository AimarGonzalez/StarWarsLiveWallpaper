package com.mel.wallpaper.starWars.process;

import java.util.List;

import org.andengine.entity.sprite.Sprite;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Shooter;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.view.PlayerAnimation;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class RenderWalkersProcess extends Process
{
	private InvisibleWalls field;
	private Game game;
	private List<Walker> walkers;
	private Sprite canvas;
	
	public RenderWalkersProcess(Game game, Sprite canvas){
		this.game = game;
		this.canvas = canvas;
	}
	
	@Override
	public void onAddToGame(Game game){
		getEntitiesFromGame(game);
	}

	public void getEntitiesFromGame(Game game)
	{
		this.field = ((Map)game.getEntity(Map.class)).walls;

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
		
		canvas = null;
		field = null;
	}
	
	@Override
	public void update(){

		getEntitiesFromGame(game);
		
		for(Walker player : this.walkers){
			Point playerCenter = InvisibleWalls.cartesianToEngineCoordinates(player.position);
			Point fixedCoord = new Point(playerCenter.getX()-player.getSpriteOffsetX(), playerCenter.getY()-player.getSpriteOffsetY());
			player.sprite.setPosition(fixedCoord.getX(), fixedCoord.getY());
			
			//Ordenar jugadores segun profundidad en el campo
			//if(player.isAplastado() && player.lastAnimation==PlayerAnimation.APLASTADO && player.textureId!=SpriteFactory.MARC){
			//	player.sprite.setZIndex(1);
			//}else{
				player.sprite.setZIndex(1000-(int)player.position.getY());
			//}
		}
		
		this.canvas.sortChildren();
		
	}
	
}
