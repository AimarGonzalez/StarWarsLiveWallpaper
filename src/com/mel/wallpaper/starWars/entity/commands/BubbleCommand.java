package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartOut;

import com.mel.entityframework.Game;
import com.mel.util.MathUtil;
import com.mel.wallpaper.starWars.entity.Bubble;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class BubbleCommand extends MoveCommand
{
	public Bubble bubble;
	private Game game;
	
	public BubbleCommand(Walker walker, Game game) {
		super(walker, 1.4f, EaseLinear.getInstance());

		//this.easeFunction = EaseStrongOut.getInstance();
		//this.easeFunction = EaseExponentialOut.getInstance();
		//this.easeFunction = EaseSineOut.getInstance();
		
		this.walker = walker;

		this.bubble = new Bubble(walker.position);
		this.movable = bubble;
		game.addEntity(bubble);
	}
	
	@Override
	public void execute(Map p) {
		//increase destination
		this.destination = MathUtil.getPuntDesti(this.bubble.position.toPoint(), this.destination, 2000f);
		
		
		if(walker.canShoot()){
			super.execute(p);
		}
		
		walker.forceStopMovement();
		walker.animateShootAndStartCooldowns(this.destination.clone());//consiga darle a la bola o no, se ve que el jugador chuta
	}
	
}
