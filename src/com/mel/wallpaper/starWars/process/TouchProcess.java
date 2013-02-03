package com.mel.wallpaper.starWars.process;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.shape.Shape;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import android.content.Context;
import android.widget.Toast;

import com.mel.entityframework.Game;
import com.mel.entityframework.Process;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.StarWarsLiveWallpaper;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.settings.GameSettings;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class TouchProcess extends Process implements IOnSceneTouchListener
{
	private Map partido;
	private List<Walker> jedis;
	private Scene scene;
	private Context toastBoard;
	private RectangularShape touchMarker;
	private TouchEvent lastTouch;
	
	private int TOUCH_RATIO = 45;
	
	public TouchProcess(Game game, Scene scene, Context context){
		this.scene = scene;
		this.toastBoard = context;
		this.touchMarker = SpriteFactory.getInstance().newBall(6, 6);
		this.touchMarker.setColor(Color.RED);
	}
	
	
	@Override
	public void onAddToGame(Game game){
		//inicializar listeners (touch, accelerometer?, keyboard?)
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setOnSceneTouchListener(this);
		
		this.partido = (Map)game.getEntity(Map.class);

		this.jedis = (List<Walker>) game.getEntities(Jumper.class);
		this.jedis.addAll(game.getEntities(Walker.class));
	}
	
	@Override
	public void onRemoveFromGame(Game game){
		scene.setTouchAreaBindingOnActionDownEnabled(false);
		scene.setOnSceneTouchListener(null);
		
	}
	
	@Override
	public void update(){
		
		if(this.lastTouch != null){
			processLastTouch(this.lastTouch);
			this.lastTouch = null;
		}
		
	}
	
	
	public boolean processLastTouch(TouchEvent touchEvent) {
		StarWarsLiveWallpaper flWalpaper = StarWarsLiveWallpaper.getSharedInstance();
		
		//printTouchDebuger(touchEvent);		

		if(GameSettings.getInstance().godsFingerEnabled){
			List<Walker> touchedPlayers = getPlayersUnderTouch(touchEvent, TOUCH_RATIO);
		
			if(touchedPlayers.size() > 0){
				for(Walker p:touchedPlayers){
					p.aplastar();
				}
				return true;
			}
		}
		
		flWalpaper.onTapFromGame(touchEvent);
		return true;
	}
	
	private List<Walker> getPlayersUnderTouch(TouchEvent pSceneTouchEvent, int touchRatio){
		ArrayList<Walker> touchedPlayers = new ArrayList<Walker>();
		Point spriteCenter = null;
		for(Walker p:this.jedis){
			spriteCenter = new Point(p.sprite.getSceneCenterCoordinates());
			if(spriteCenter.distance(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()) < TOUCH_RATIO){
				touchedPlayers.add(p);
			}
		}
		
		return touchedPlayers;
	}
	
	
	private void printTouchDebuger(TouchEvent touchEvent){
		if(this.touchMarker != null){
			if(!this.touchMarker.hasParent()){
				//map.field.background.attachChild(this.touchMarker);
				this.scene.attachChild(this.touchMarker);
			}
			
			//float[] pointOnField = map.field.background.convertSceneToLocalCoordinates(touchEvent.getX(), touchEvent.getY());
			float[] pointOnField = {touchEvent.getX(), touchEvent.getY()};
			
			this.touchMarker.setPosition(pointOnField[0]-touchMarker.getRotationCenterX(), pointOnField[1]-touchMarker.getRotationCenterY());
		}
		
	}
	
	
	
	
	/* TOUCH LISTENERS */
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		pSceneTouchEvent.set(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()+10);
		if(this.partido.status == Map.Status.PLAYING){
			this.lastTouch = pSceneTouchEvent;
			return true;
		}else{
			//Toast.makeText(this.toastBoard, "Be gentle, let the players catch their breath!", Toast.LENGTH_LONG).show();
			//HAY QUE ENCONTRAR COMO PINTAR EL TEXTO EN PANTALLA!!
			return false;
		}
	}
	
	
	

}
