package com.mel.wallpaper.starWars.view;

import com.mel.util.Point;

public interface IShooterAnimator extends IWalkerAnimator
{
	
	void animateShoot(Point playerPosition, Point destination);
	void animateShoot(Position playerPosition, Point destination);
}
