package com.mel.wallpaper.starWars;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.BaseGameWallpaperService;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.debug.Debug;
import org.andengine.util.debug.Debug.DebugLevel;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseSineOut;

import android.util.DisplayMetrics;
import android.widget.Toast;

import com.mel.wallpaper.starWars.entity.Partido;
import com.mel.wallpaper.starWars.settings.GameSettings;
import com.mel.wallpaper.starWars.timer.TimerHelper;

public class StarWarsLiveWallpaper extends BaseGameWallpaperService implements IUpdateHandler
{
	//android.os.Debug.waitForDebugger(); 

	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 800;
	public static final int NOTIFICATION_BAR_HEIGHT = 0;
	
	public static String PREFERENCES_ID = "mel.starWars.preferences";

	
	// ===========================================================
	// Fields
	// ===========================================================
	private static StarWarsLiveWallpaper instance;
	
	
	private StarWarsGame game;
	Scene starWarsScene;
	
	int fadeOutSpeed = 1;
	
	Sound benjiBSO;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public StarWarsLiveWallpaper()
	{
		instance = this;
		
		// mPrefs = this.getSharedPreferences(PREFERENCES_ID, 0); //poner esto en StarWarsGameSettings
		// mPrefs.registerOnSharedPreferenceChangeListener(this); //poner esto en StarWarsGameSettings
        // onSharedPreferenceChanged(mPrefs, null); //poner esto en StarWarsGameSettings
        
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public static StarWarsLiveWallpaper getSharedInstance()
	{	
		return instance;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public EngineOptions onCreateEngineOptions() {
		
		//android.os.Debug.waitForDebugger(); 
		Debug.setDebugLevel(DebugLevel.NONE);
		//Toast.makeText(this, "You move my sprite right round, right round...", Toast.LENGTH_LONG).show();
		Debug.d("toast", "onCreateEngineOptions");

		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		//EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		options.getAudioOptions().setNeedsSound(true);
		options.getAudioOptions().setNeedsMusic(true);
		
		//getApplication().getWallpaperDesiredMinimumHeight()
		//DisplayMetrics metrics = getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
		
		return options;
	}
	

	@Override
	protected synchronized void onResume() {
		Debug.d("toast", "onResume");
		super.onResume();
	}

	
	public void onCreateResources() {
		Debug.d("toast", "onCreateResources");
		this.game = new StarWarsGame(this.getEngine(), this);
		this.game.onCreateResources();
		
		createMediaResources();
	}
	
	private void createMediaResources() {

		try {
			//MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "sfx/benji-small.ogg").getMediaPlayer().
			benjiBSO = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "sfx/benji-small.ogg");
			benjiBSO.setVolume(0); //AG: lo uso para saber si estoy reproduciendo, sino no se comprobarlo :/

			this.mEngine.registerUpdateHandler(this);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Scene onCreateScene() {
		Debug.d("toast", "onCreateScene");
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
		Scene initialScene = this.game.onCreateScene();
		
		this.starWarsScene = this.game.starWarsScene;
		
		return initialScene;
	}

	
	protected void onPopulatedScene() throws Exception {
		Debug.d("toast", "onPopulatedScene");
		this.game.initialize();
	}
	
	
	public void onGameCreated() {
		Debug.d("toast", "onGameCreated");
		super.onGameCreated();
		Debug.d("GameCreated: start StarWarsGame");
		this.game.onGameCreated();
	}
	
	
	protected void onOffsetsChanged(float pXOffset, float pYOffset, float pXOffsetStep, float pYOffsetStep, int pXPixelOffset, int pYPixelOffset) {
		super.onOffsetsChanged(pXOffset, pYOffset, pXOffsetStep, pYOffsetStep, pXPixelOffset, pYPixelOffset);
		if(this.game != null){
			//Debug.d("offsetX: "+pXOffset);
			//Debug.d("pixelOffsetX: "+pXPixelOffset);
			this.game.onOffsetsChanged(pXOffset);
		}
	}
	
	@Override
	public void onPause() {
		Debug.d("toast", "onPause");
		super.onPause();
	}
	
	@Override
	public void onPauseGame()
    {
		Debug.d("toast", "onPauseGame");
		game.onPauseGame();
		super.onPauseGame();
    }
	
	@Override
	public void onReloadResources(){
		//Toast.makeText(this, "loading resources...", Toast.LENGTH_LONG).show(); //no que peta!
		Debug.d("toast", "onReloadResources");
		super.onReloadResources();
	}
	
	
	@Override
	public synchronized void onResumeGame(){ //esto viene despues de reload resources
		//Toast.makeText(this, "onResumeGame", Toast.LENGTH_LONG).show();
		Debug.d("toast", "onResumeGame");
		
		game.onResumeGame();
		
		super.onResumeGame();
//		TimerHelper.startTimer(mEngine.getScene(), 2f,  new ITimerCallback() {                      
//			public void onTimePassed(final TimerHandler pTimerHandler){
//				restartEngine();
//			}
//		});
	}
	
	public void restartEngine(){
		super.onResumeGame();
	}
	

//	
//	protected void onTap(int pX, int pY) {
//		//super.onTap(pX, pY);
//		if(splashEnabled){
//			Debug.d("starWars", "tap on " + pX + "x" + pY);
//			launchSplash();
//		}
//	}
	
	public void onTapFromGame(TouchEvent event) {
		Debug.d("tap on " + event.getX() + "x" + event.getY());
		
//		playMusic();
	}
	
	void playMusic(){
		if(GameSettings.getInstance().musicEnabled){
			benjiBSO.setVolume(1.0f);
			benjiBSO.play();
		}
	}
	
	void stopMusic(){
		stopMusic(true, 1);
	}
	void stopMusic(int fadeOutSpeed){
		stopMusic(true, fadeOutSpeed);
	}
	void stopMusic(boolean fadeOut){
		stopMusic(fadeOut, 1);
	}
	void stopMusic(boolean fadingOut, int fadeOutSpeed){
		this.fadeOutSpeed = fadeOutSpeed;
		if(fadingOut){
			this.doFadeOut = true;
			//Debug.d("fadeOut-start");
		}else{
			benjiBSO.stop();
		}
	}

	boolean doFadeOut = false;
	
	public void onUpdate(float pSecondsElapsed) {
		if(doFadeOut){
			benjiBSO.setVolume(Math.max(0f, benjiBSO.getVolume()-pSecondsElapsed/2*this.fadeOutSpeed));
			if(benjiBSO.getVolume() == 0f){
				//Debug.d("fadeOut-end");
				benjiBSO.stop();
				doFadeOut = false;
			}
		}
	}
	
	public void reset() {
		// TODO Auto-generated method stub
		
	}	
}
