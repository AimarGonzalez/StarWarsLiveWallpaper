package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.mel.entityframework.IMovable;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;

public class StopCommand extends Command
{
	public Point destination;
	public IMovable movable;
	public IEaseFunction easeFunction = EaseLinear.getInstance();
	
	public StopCommand(Walker walker) {
		this(walker, walker);
	}
	
	public StopCommand(Walker walker, IMovable movable) {
		walker = walker;
		this.movable = movable;
	}
	
	@Override
	public void execute(Map p) {
		this.movable.forceStopMovement();
	}
	
	
	
}
