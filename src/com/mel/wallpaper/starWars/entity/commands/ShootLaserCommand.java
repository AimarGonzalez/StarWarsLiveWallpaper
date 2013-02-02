package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartOut;

import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;

public class ShootLaserCommand extends MoveCommand
{
	public Walker shooter;
	public LaserBeam laser;
	
	
	public ShootLaserCommand(Walker walker) {
		super(walker, 1.4f, EaseLinear.getInstance());
		movable = laser;
		//this.easeFunction = EaseStrongOut.getInstance();
		//this.easeFunction = EaseExponentialOut.getInstance();
		//this.easeFunction = EaseSineOut.getInstance();
		
		this.shooter = walker;
		this.laser = laser;
	}
	
	@Override
	public void execute(Map p) {
		
		if(shooter.canShoot() && laser.isBusy()==false && shooter.getPosition().distance(movable.getPosition()) < LaserBeam.HIT_DISTANCE){
			super.execute(p);
		}
		
		shooter.forceStopMovement();
		startShootAnimation();
	}
	
	public void startShootAnimation(){
		shooter.shootAt(this.destination.clone());//consiga darle a la bola o no, se ve que el jugador chuta
	}
	
}
