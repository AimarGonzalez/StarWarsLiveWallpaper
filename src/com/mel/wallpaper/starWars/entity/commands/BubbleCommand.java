package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseQuartOut;

import com.mel.entityframework.Game;
import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Bubble;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.view.Position;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class BubbleCommand extends MoveCommand
{
	public Bubble bubble;
	private Game game;
	
	public BubbleCommand(Walker walker, Game game) {
		super(walker, 1.4f, EaseLinear.getInstance());
		
		this.walker = walker;
		this.game = game;
		
		
		bubble = new Bubble(walker.position.clone());
		movable = bubble;
		game.addEntity(bubble);
	}
	
	@Override
	public void execute(Map p) {
		//increase destination
//		this.destination = MathUtil.getPuntDesti(this.bubble.position.toPoint(), this.destination, 2000f);
		destination = new Point(bubble.position.getX(), bubble.position.getY() + 50);
		super.execute(p);

		walker.forceStopMovement();
		//walker.animateTalking();
		
		bubble.getSprite().registerEntityModifier(new AlphaModifier(2f, 1f, 0f, new IEntityModifierListener() {
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity sprite) {	
			}
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity sprite) {
//				game.removeEntity(sprite);
			}
		}));
	}
}
