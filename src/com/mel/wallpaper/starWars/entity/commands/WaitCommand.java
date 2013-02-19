package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.mel.entityframework.IMovable;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;

public class WaitCommand extends Command
{
	private Walker walker;
	private float duration;
	
	public WaitCommand(Walker walker, float duration) {
		this.walker = walker;
		this.duration = duration;
	}
	
	@Override
	public void execute(Map p) {
		this.walker.forceStopMovement();
		this.walker.animateWaitAndStartCooldowns(this.duration);
	}
	
}
