package com.mel.entityframework;

import org.andengine.entity.sprite.Sprite;

import com.mel.wallpaper.starWars.view.Position;

public interface IEntity
{
	public void recycle();
	
	public Sprite getSprite();
	
	public Position getPosition();
}
