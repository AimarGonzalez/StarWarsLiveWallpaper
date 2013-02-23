package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartOut;

import com.mel.entityframework.Game;
import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Shooter;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class ShootLaserCommand extends MoveCommand
{
	public LaserBeam laser;
	private Game game;
	private Shooter shooter;
	
	public ShootLaserCommand(Shooter walker, Game game) {
		super(walker, 1.4f, EaseLinear.getInstance());

		//this.easeFunction = EaseStrongOut.getInstance();
		//this.easeFunction = EaseExponentialOut.getInstance();
		//this.easeFunction = EaseSineOut.getInstance();
		
		this.shooter = walker;

		Point laserPosition = new Point(walker.position.getX(), walker.position.getY()-1); //esto es solo para que el laser aparezca por detras del arma, no por delante
		this.laser = new LaserBeam(laserPosition, MathUtils.random(0, 2));
		this.movable = laser;
		game.addEntity(laser);
	}
	
	@Override
	public void execute(Map p) {
		//increase destination
		this.destination = MathUtil.getPuntDesti(this.laser.position.toPoint(), this.destination, 2000f);
		
		super.execute(p);

		shooter.forceStopMovement();
		shooter.animateShootAndStartCooldowns(this.destination.clone());
		
	}
	
}
