package com.mel.wallpaper.starWars.entity.commands;

import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;

public abstract class Command
{
	public Walker player;
	public int factor = 1; //sobreescrito por el Framework (WalkersProcess)
	
	public Command(Walker player){
		this.player = player;
		if(this.player != null){
			this.player.addCommand(this);
		}
	}
	
	public abstract void execute(Map partido);
	
}
