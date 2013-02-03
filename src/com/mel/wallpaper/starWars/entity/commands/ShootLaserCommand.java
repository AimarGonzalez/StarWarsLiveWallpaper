package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartOut;

import com.mel.entityframework.Game;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class ShootLaserCommand extends MoveCommand
{
	public LaserBeam laser;
	private Game game;
	
	public ShootLaserCommand(Walker player, Game game) {
		super(player, 1.4f, EaseLinear.getInstance());

		//this.easeFunction = EaseStrongOut.getInstance();
		//this.easeFunction = EaseExponentialOut.getInstance();
		//this.easeFunction = EaseSineOut.getInstance();
		
		this.player = player;

		this.laser = new LaserBeam(player.position);
		this.movable = laser;
		game.addEntity(laser);
	}
	
	@Override
	public void execute(Map p) {
		
		if(player.canShoot()){
			super.execute(p);
		}
		
		player.forceStopMovement();
		player.animateShootAt(this.destination.clone());//consiga darle a la bola o no, se ve que el jugador chuta
	}
	
}
