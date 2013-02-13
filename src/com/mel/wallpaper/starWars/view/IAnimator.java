package com.mel.wallpaper.starWars.view;

import org.andengine.entity.sprite.AnimatedSprite;

import com.mel.util.Point;

public interface IAnimator
{
	
	String getTextureId();

	float getSpeed();
	void setSpeed(float s);
	
	Animation getInitialAnimation();
	void setInitialAnimation(Animation a);

	
	AnimatedSprite getSprite();
	Point getSpriteDimensions();	
	float getSpriteOffsetX();
	float getSpriteOffsetY();
	Point getRotationCenter();
	
	void animateInitialAnimation();
	void animateAplastado();
	
}
