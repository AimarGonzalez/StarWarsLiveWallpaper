package com.mel.wallpaper.starWars.view;

public class AnimationFactory
{
	public static IAnimator newAnimation(String textureId, float speed){
		IAnimator animation = null;
		
		if(textureId == SpriteFactory.BENJI){
			animation = new GoalKeeperAnimator(SpriteFactory.BENJI, speed);
		}
		
		if(textureId == SpriteFactory.STORM_TROOPER){
			animation = new ShooterAnimator(SpriteFactory.STORM_TROOPER, speed);
		}

		if(textureId == SpriteFactory.MP_WHITE){
			animation = new WalkerAnimator(SpriteFactory.MP_WHITE, speed);
		}
		
		
		return animation;
	}
}
