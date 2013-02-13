package com.mel.wallpaper.starWars.process;

import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

import android.content.Context;
import android.widget.Toast;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.InvisibleWalls;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Map.Status;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.settings.GameSettings;
import com.mel.wallpaper.starWars.timer.TimerHelper;



public class GameProcess extends Process
{
	private Map partido;
//	private LaserBeam ball;
	
	private Context toastBoard;
	private Engine engine;
	private Scene starWarsScene;
	
	
	public GameProcess(Game game, Engine engine, Scene starWarsScene){
		this.engine = engine;
		this.starWarsScene = starWarsScene;
	}
	
	@Override
	public void onAddToGame(Game game){
		this.partido = (Map)game.getEntity(Map.class);
		

	}
	
	@Override
	public void onRemoveFromGame(Game game){
		this.partido = null;
	}
	
	@Override
	public void update(){
		
		switch(this.partido.status){
			
			
			case INITIAL_STATE:
				break;
				
			case INTRO:
				if(this.engine.getScene()!=this.starWarsScene){
					this.engine.setScene(this.starWarsScene);
				}
				
				partido.status = Map.Status.WAIT_A_SECOND;
				TimerHelper.startTimer(this.engine.getScene(), 0.5f,  new ITimerCallback() {                      
		            public void onTimePassed(final TimerHandler pTimerHandler){
		            	partido.status = Map.Status.RESUME_GAME;
		            }
		        });
				break;
			
			case PAUSE:
				
				//teleportPlayersToInitialPositions();
				//forceBallToCenter();
				break;
				
			case RESUME_GAME:
				if(this.engine.getScene()!=this.starWarsScene){
					this.engine.setScene(this.starWarsScene);
				}
				
				//disimula el tiempo de carga de recursos a GPU
				partido.status = Map.Status.WAIT_A_SECOND;
				TimerHelper.startTimer(this.engine.getScene(), 1.5f,  new ITimerCallback() {                      
		            public void onTimePassed(final TimerHandler pTimerHandler){
		            	partido.status = Map.Status.PLAYING;
		            }
		        });
				//map.status = Map.Status.PLAYING;
				break;
			
			case PLAYING:
				
//				if(isGol()){
//					this.partido.status = Map.Status.GOAL_CINEMATIC;
//					break;
//				}
				
//				if(isBallOutOfBounds()){
//					this.partido.status = Map.Status.PERFORM_OUTSIDE;
//					break;
//				}
				
				break;
				
			case GOAL_CINEMATIC:
				this.partido.status = Map.Status.GOTO_INITIAL_POS;
				break;
				
			case PERFORM_OUTSIDE:
				this.partido.status = Map.Status.GOTO_INITIAL_POS;
				break;
				
			case GOTO_INITIAL_POS:
//				if(isBallStopped() && areAllPlayersAtInitialPosition()){
//					forceBallToCenter();
//					partido.status = Map.Status.RESUME_GAME;
//				}
				break;
			
			
			case WAIT_A_SECOND:
			default:
		}
		
		
	}
	
	
}
