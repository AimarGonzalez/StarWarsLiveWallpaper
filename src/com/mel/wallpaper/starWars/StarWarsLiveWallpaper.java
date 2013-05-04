package com.mel.wallpaper.starWars;

import org.andengine.BaseGameWallpaperService;
import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.util.debug.Debug;
import org.andengine.util.debug.Debug.DebugLevel;

import com.mel.util.Point;

import android.content.res.Configuration;

public class StarWarsLiveWallpaper extends BaseGameWallpaperService
{

	public static final int SCREEN_WIDTH = 480;
	public static final int SCREEN_HEIGHT = 800;
	public static final int TEXTURE_WIDTH = 1920;
	public static final int TEXTURE_HEIGHT = 1137;
	public static final int BACKGROUND_HEIGHT = SCREEN_HEIGHT - StarWarsLiveWallpaper.NOTIFICATION_BAR_HEIGHT;
	public static final int BACKGROUND_WIDTH = BACKGROUND_HEIGHT*TEXTURE_WIDTH/TEXTURE_HEIGHT;
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
		
		
		Point cameraDimensions = calcCameraDimensionsByOrientation(getResources().getConfiguration().orientation);
		
		final Camera camera = new Camera(0, 0, cameraDimensions.getX(), cameraDimensions.getY());

		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(SCREEN_WIDTH, SCREEN_HEIGHT), camera);
		//EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		options.getAudioOptions().setNeedsSound(true);
		options.getAudioOptions().setNeedsMusic(true);
		
		//getApplication().getWallpaperDesiredMinimumHeight()
		//DisplayMetrics metrics = getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
		
		return options;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		Point cameraDimensions = calcCameraDimensionsByOrientation(newConfig.orientation);
		
		
		//by changing camera surface (doesn't work)
		//this.getEngine().getCamera().setSurfaceSize(0, 0, cameraDimensions.getX(), cameraDimensions.getY());
		
		//by setting max and mins
		this.getEngine().getCamera().set(0,0, cameraDimensions.getX(), cameraDimensions.getY());
		
		if(this.game != null){
			this.game.onScreenOrientationChanged(newConfig.orientation);
		}
		 
	};
	
	protected Point calcCameraDimensionsByOrientation(int orientation){
		int newWidth = 0;
		int newHeight = 0;
		
		switch(orientation) {
			case Configuration.ORIENTATION_LANDSCAPE:
				//zoom out
				//newWidth = (int)((float)(SCREEN_HEIGHT*SCREEN_HEIGHT)/(float)SCREEN_WIDTH);
				//newHeight = SCREEN_HEIGHT;
				
				//fill screen (fit width)
				//newWidth = BACKGROUND_WIDTH;
				//newHeight = BACKGROUND_WIDTH*SCREEN_WIDTH/SCREEN_HEIGHT;
				
				//fill screen (fit height)
				newWidth = BACKGROUND_HEIGHT*SCREEN_HEIGHT/SCREEN_WIDTH;
				newHeight = BACKGROUND_HEIGHT;
				
				
				//keep size
				//newWidth = SCREEN_HEIGHT;
				//newHeight = SCREEN_WIDTH;
				
				Debug.d("toast", "orientation: LANDSCAPE ("+newWidth+","+newHeight+")");
				break;
				
			case Configuration.ORIENTATION_PORTRAIT:
				newWidth = SCREEN_WIDTH;
				newHeight = SCREEN_HEIGHT;
				Debug.d("toast", "orientation: PORTRAIT ("+newWidth+","+newHeight+")");
				break;
				
			default:
				newWidth = SCREEN_WIDTH;
				newHeight = SCREEN_HEIGHT;
				Debug.d("toast", "orientation: PORTRAIT ("+newWidth+","+newHeight+")");
		}
		
		return new Point(newWidth, newHeight);
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
