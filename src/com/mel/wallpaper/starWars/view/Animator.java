package com.mel.wallpaper.starWars.view;

import org.andengine.entity.sprite.AnimatedSprite;

public abstract class Animator implements IAnimator{

	
	protected String textureId;
	protected AnimatedSprite sprite;
	protected float speed;
	
	
	public Animator(String textureId, float speed){
		this.textureId = textureId;
		this.speed = speed;
		this.sprite = (AnimatedSprite)SpriteFactory.getMe().newSprite(getTextureId(), getSpriteDimensions().getX(), getSpriteDimensions().getY());
	}
	
	public String getTextureId() {
		return textureId;
	}
	
	public Animation getInitialAnimation() {
		return Animation.STOP_S;
	}
	
	public float getSpeed(){
		return this.speed;
	}
	
	public void setSpeed(float s){
		this.speed = s;
	}
	
	public void setInitialAnimation(Animation a) {
		//implement only if needed
	}
	
	public AnimatedSprite getSprite() {
		return sprite;
	}
	
	//ANIMATE
	public void animateInitialAnimation() {
		animate(getInitialAnimation());
	}
	
	public void animateAplastado(){
		animate(Animation.APLASTADO);
	}
	
	
	protected void animateOnce(Animation a){
		animate(a, false);
	}
	
	protected void animate(Animation a){
		animate(a, true);
	}
	
	protected void animate(Animation a, boolean isInfiniteLoop){
		//must override
	}
	


}
