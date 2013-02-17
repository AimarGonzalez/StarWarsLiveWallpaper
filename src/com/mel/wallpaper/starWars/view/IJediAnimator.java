package com.mel.wallpaper.starWars.view;

import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.JediKnight;

public interface IJediAnimator extends IWalkerAnimator
{
	
	void animateParry(JediKnight jedi,Point destination);
	void animateDuel(JediKnight jedi,Point destination);
	void animateIdleWithSword();
}
