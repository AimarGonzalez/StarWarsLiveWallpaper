package com.mel.wallpaper.starWars.process;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.debug.Debug;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Bubble;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Map;

public class RenderBubbleProcess extends Process
{
	private InvisibleWalls walls;
	private Game game;
	private List<Bubble> bubbles;
	private Sprite canvas;
	
	public RenderBubbleProcess(Game game, Sprite canvas){
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
		this.bubbles = (List<Bubble>) game.getEntities(Bubble.class);
		this.walls = partido.walls;
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		if(bubbles != null){
			bubbles.clear();
			bubbles = null;
		}
		
		canvas = null;
		walls = null;
	}
	
	@Override
	public void update(){
		
		getEntitiesFromGame(game);
		
		//Debug.d("Number of bubbles: " + bubbles.size());		
		
		for(Bubble bubble : this.bubbles){
		
			if(bubble.isFinished)
				game.removeEntity(bubble);
			
			Point bubbleCenter= InvisibleWalls.cartesianToEngineCoordinates(bubble.position);
			Point fixedCoord = new Point(bubbleCenter.getX()-bubble.getSpriteOffsetX(), bubbleCenter.getY()-bubble.getSpriteOffsetY());	
			bubble.sprite.setPosition(fixedCoord.getX(), fixedCoord.getY());
			
			// Ordenar profundidad en el campo
			bubble.sprite.setZIndex(1000-(int)bubble.position.getY());
			
//			Debug.d("Bubble fixedCoor: " + fixedCoord);	
//			Debug.d("Bubble bubbleCenter: " + bubbleCenter);	
//			Debug.d("Bubble position: " + bubble.position);		
			
			if(miniball != null){
				miniball.setPosition(bubbleCenter.getX()-1, bubbleCenter.getY()-1);
				miniball.setColor(1, 0, 0);
				miniball.setZIndex(10000);
			}
		}
		
		this.canvas.sortChildren();
		
	}

}
