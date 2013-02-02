package com.mel.wallpaper.starWars.process;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Map;

public class RenderLaserProcess extends Process
{
	private InvisibleWalls field;

	private List<LaserBeam> balls;
	private Sprite canvas;
	
	public RenderLaserProcess(Sprite canvas){
		this.canvas = canvas;
	}
	
	
	//final Shape miniball = SpriteFactory.getInstance().newBall(3, 3);
	final Shape miniball = null; //disabled
	
	@Override
	public void onAddToGame(Game game){
		Map partido = (Map)game.getEntity(Map.class);
		this.field = partido.walls;
		
		//this.balls = (List<LaserBeam>) game.getEntities(LaserBeam.class);
		this.balls = new ArrayList<LaserBeam>();
		for(LaserBeam b : this.balls){
			this.canvas.attachChild(b.position);
			this.canvas.attachChild(b.sprite);
		}
		
		if(miniball != null){
			this.canvas.attachChild(miniball);
		}
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(balls != null){
			balls.clear();
			balls = null;
		}
		
		canvas = null;
		field = null;
		
	}
	
	@Override
	public void update(){
		
		
		for(LaserBeam ball : this.balls){
			
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
