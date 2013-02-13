package com.mel.wallpaper.starWars.view;

import com.mel.util.Point;

public interface IWalkerAnimator extends IAnimator
{
	
	void animateWalk(Point walkerPosition, Point destination);
	void animateWalk(Position walkerPosition, Point destination);

	void animateStop();
	
}
