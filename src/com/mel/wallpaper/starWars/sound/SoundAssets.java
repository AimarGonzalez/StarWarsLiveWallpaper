package com.mel.wallpaper.starWars.sound;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.debug.Debug;

import com.mel.wallpaper.starWars.settings.GameSettings;

import android.content.Context;

public class SoundAssets implements IUpdateHandler {

	static SoundManager soundManager;
	static Context context;

	boolean doFadeOut = false;
	
	public enum Sample {
		
		LASER("sfx/The_Yellow_Dart-Saberdown.wav"),
		CHEWAKA("sfx/chewbacca.wav"),

		// From Sele!!
		AUCH("sfx/auch.mp3"),
		CHIU("sfx/chiu.mp3"),
		EWOK_FRITO("sfx/ewok_frito.mp3"),
		EWOKS_2("sfx/ewoks_2.mp3"),
		EWOKS_3("sfx/ewoks_3.mp3"),
		EWOKS_WISTLE1("sfx/ewoks_wistle1.mp3"),
		HOLA_K_ASE("sfx/hola_k_ase.mp3"),
		JUAAAN("sfx/juaaan.mp3"),
		JUAAAN_2("sfx/juaaan_2.mp3"),
		PAYANOOO("sfx/payanooo.mp3"),
		PEINANDO("sfx/peinando.mp3"),
		PINYAU("sfx/pinyau.mp3"),
		SPACEPELOTAS("sfx/spacepelotas.mp3"),
		TINTINTIRIRIN("sfx/tintintiririn.mp3"),
		VADER("sfx/vader.mp3");
		
		private Sound sound;
		private String path;
		
		private boolean fadingOut = false;
		private int fadeOutSpeed;
		
		private Sample(String path)
		{
			this.path = path;
		}
		
		public void createSoundFromAsset()
		{
			try {
				sound = SoundFactory.createSoundFromAsset(soundManager, context, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public SoundAssets(SoundManager soundManager, Context context)
	{
		this.soundManager = soundManager;
		this.context = context;
		
		for(Sample sample : Sample.values() )
			sample.createSoundFromAsset();
	}

	public static void setMasterVolume(float pMasterVolume)
	{
		soundManager.setMasterVolume(pMasterVolume);
	}
	
	public static float getMasterVolume()
	{
		return soundManager.getMasterVolume();
	}
	
	public static void playSample(Sample sample)
	{
		Debug.d("SoundAssets","playing " + sample);
		
		if(GameSettings.getInstance().musicEnabled)
		{
			sample.sound.setVolume(1.0f);
			sample.sound.play();
		}
	}
	
	public static void stopSample(Sample sample)
	{
		stopSample(sample, false, 0);
	}
	
	public static void stopSample(Sample sample, boolean doFadeOut)
	{
		stopSample(sample, doFadeOut, 1);
	}
	
	public static void stopSample(Sample sample, boolean doFadeOut, int fadeOutSpeed)
	{
		if(!GameSettings.getInstance().musicEnabled)
			return;
		
		if(doFadeOut)
		{
			sample.fadeOutSpeed = fadeOutSpeed;
			sample.fadingOut = true;
		}
		else
		{
			sample.sound.stop();
		}
	}
	
	public void onUpdate(float pSecondsElapsed) {
		//Debug.d("SoundAssets","onUpdate handler " + pSecondsElapsed);
		
		for(Sample sample : Sample.values() )
		{
			if(sample.fadingOut)
			{
				Debug.d("SoundAssets","fadeOut " + sample + ", old volume: " + sample.sound.getVolume());
				
				sample.sound.setVolume(Math.max(0f,sample.sound.getVolume()-pSecondsElapsed/2*sample.fadeOutSpeed));
				
				Debug.d("SoundAssets","fadeOut " + sample + ", new volume: " + sample.sound.getVolume());
				
				if(sample.sound.getVolume() == 0f)
				{
					Debug.d("SoundAssets","fadeOut " + sample + " end");
					sample.sound.stop();
					sample.fadingOut = false;
				}
			}
		}
	}

	public void reset() {
		Debug.d("SoundAssets","reset update handler");
	}
}
