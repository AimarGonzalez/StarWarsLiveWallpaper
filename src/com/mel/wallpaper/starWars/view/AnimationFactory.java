package com.mel.wallpaper.starWars.view;

public class AnimationFactory
{
	public static IAnimator newAnimation(String textureId, float speed){
		IAnimator animation = null;
		
		if(textureId == SpriteFactory.STORM_TROOPER){
			animation = new ShooterAnimator(SpriteFactory.STORM_TROOPER, speed);
		}

		if(textureId == SpriteFactory.LUKE){
			animation = new JediAnimator(SpriteFactory.LUKE, speed);
		}
		
		
		if(textureId == SpriteFactory.DARTH_VADER){
			animation = new DarthVaderAnimator(SpriteFactory.DARTH_VADER, speed);
		}
		
//		if(textureId == SpriteFactory.BENJI){
//			animation = new GoalKeeperAnimator(SpriteFactory.BENJI, speed);
//		}
		
		
		return animation;
	}
}
