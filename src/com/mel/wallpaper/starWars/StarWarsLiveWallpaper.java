package com.mel.wallpaper.starWars;

import java.io.IOException;

import org.andengine.BaseGameWallpaperService;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.debug.Debug;
import org.andengine.util.debug.Debug.DebugLevel;

import com.mel.wallpaper.starWars.settings.GameSettings;
import com.mel.wallpaper.starWars.sound.SoundAssets;
import com.mel.wallpaper.starWars.sound.SoundAssets.Sample;

public class StarWarsLiveWallpaper extends BaseGameWallpaperService
{

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
		
//		android.os.Debug.waitForDebugger();
		
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
}
