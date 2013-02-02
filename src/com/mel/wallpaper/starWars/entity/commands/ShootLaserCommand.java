package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartOut;

import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class ShootLaserCommand extends MoveCommand
{
	public LaserBeam laser;
	
	public ShootLaserCommand(Walker player) {
		super(player, 1.4f, EaseLinear.getInstance());

		//this.easeFunction = EaseStrongOut.getInstance();
		//this.easeFunction = EaseExponentialOut.getInstance();
		//this.easeFunction = EaseSineOut.getInstance();
		
		this.player = player;

		this.laser = new LaserBeam(player.position);
		this.movable = laser;
	}
	
	@Override
	public void execute(Map p) {
		
		if(player.canShoot()){
			super.execute(p);
		}
		
		player.forceStopMovement();
		startShootAnimation();
	}
	
	public void startShootAnimation(){
		player.animateShootAt(this.destination.clone());//consiga darle a la bola o no, se ve que el jugador chuta
	}
	
}
