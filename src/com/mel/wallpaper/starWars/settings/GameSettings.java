package com.mel.wallpaper.starWars.settings;

public class GameSettings
{
	
	public static final String MUSIC_KEY = "music_enabled";
	public static final String GODSFINGER_KEY = "godsFinger_enabled";
	
	public static String PREFERENCES_ID = "mel.starWars.preferences";

	public boolean godsFingerEnabled;
	public boolean musicEnabled;
	public boolean loadingScreenEnabled = false;
	
	private static GameSettings instance;
	
	private GameSettings(){	
	}
	
	public static GameSettings getInstance(){
		if(instance == null){
			instance = new GameSettings();
		}
		return instance;
	}
}
