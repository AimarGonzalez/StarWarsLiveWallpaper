package com.mel.wallpaper.starWars.process;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.debug.Debug;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Map;

public class RenderLaserProcess extends Process
{
	private InvisibleWalls field;
	private Game game;
	private List<LaserBeam> beams;
	private Sprite canvas;
	
	public RenderLaserProcess(Game game, Sprite canvas){
		this.game = game;
		this.canvas = canvas;
	}
	
	
	//final Shape miniball = SpriteFactory.getInstance().newBall(3, 3);
	final Shape miniball = null; //disabled
	
	@Override
	public void onAddToGame(Game game){
		getEntitiesFromGame(game);
	}
	
	public void getEntitiesFromGame(Game game) {
		Map partido = (Map)game.getEntity(Map.class);
		this.beams = (List<LaserBeam>) game.getEntities(LaserBeam.class);
		this.field = partido.walls;
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(beams != null){
			beams.clear();
			beams = null;
		}
		
		canvas = null;
		field = null;
	}
	
	@Override
	public void update(){
		
		getEntitiesFromGame(game);
		
//		Debug.d("Number of beams: " + beams.size());		
		
		for(LaserBeam ball : this.beams){
			
			Point ballCenter= InvisibleWalls.cartesianToEngineCoordinates(ball.position);
			Point fixedCoord = new Point(ballCenter.getX()-ball.getSpriteOffsetX(), ballCenter.getY()-ball.getSpriteOffsetY());
			ball.sprite.setPosition(fixedCoord.getX(), fixedCoord.getY());
			//Ordenar profundidad en el campo
			ball.sprite.setZIndex(1000-(int)ball.position.getY());

			if(miniball != null){
				miniball.setPosition(ballCenter.getX()-1, ballCenter.getY()-1);
				miniball.setColor(1, 0, 0);
				miniball.setZIndex(10000);
			}
			
			
		}
		
		this.canvas.sortChildren();
		
	}

}
