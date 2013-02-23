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
import com.mel.wallpaper.starWars.entity.Bubble.BubbleType;
import com.mel.wallpaper.starWars.entity.LaserBeam;
import com.mel.wallpaper.starWars.entity.Map;
import com.mel.wallpaper.starWars.entity.Walker;
import com.mel.wallpaper.starWars.entity.Walker.Rol;
import com.mel.wallpaper.starWars.sound.SoundAssets;
import com.mel.wallpaper.starWars.sound.SoundAssets.Sample;
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
		
		switch(walker.rol)
		{
		case JEDI:
			switch((int)(Math.random()*9))
			{
			case 1:
				bubble = new Bubble(BubbleType.BUBBLE_HOLA_K_ASE, walker.position);
				break;
			case 2:
				bubble = new Bubble(BubbleType.BUBBLE_EWOKS_WISTLE1, walker.position);
				break;
			case 3:
				bubble = new Bubble(BubbleType.BUBBLE_TINTINTIRIRIN, walker.position);
				break;
			case 4:
				bubble = new Bubble(BubbleType.BUBBLE_JUAN, walker.position);
				break;
			case 5:
				bubble = new Bubble(BubbleType.BUBBLE_PAYANO, walker.position);
				break;
			case 6:
				bubble = new Bubble(BubbleType.BUBBLE_SPACEPELOTAS, walker.position);
				break;
			case 7:
				bubble = new Bubble(BubbleType.BUBBLE_JUAN, walker.position);
				break;
			case 8:
				bubble = new Bubble(BubbleType.BUBBLE_JOSE_EL_MIEDO_Y_LA_IRA, walker.position);
				break;
			default:
				bubble = new Bubble(BubbleType.BUBBLE_JOSE_RECUERDA, walker.position);
			}
			break;
			
		case STORM_TROOPER:
			switch((int)(Math.random()*9))
			{
			case 0:
				bubble = new Bubble(BubbleType.BUBBLE_A_ORDENES, walker.position);
				break;
			case 1:
				bubble = new Bubble(BubbleType.BUBBLE_EWOKS3, walker.position);
				break;
			case 2:
				bubble = new Bubble(BubbleType.BUBBLE_A_EWOK, walker.position);
				break;
			case 3:
				bubble = new Bubble(BubbleType.BUBBLE_A_DUCADOS, walker.position);
				break;
			case 4:
				bubble = new Bubble(BubbleType.BUBBLE_A_NAPALM, walker.position);
				break;
			case 5:
				bubble = new Bubble(BubbleType.BUBBLE_PEINANDO, walker.position);
				break;
			case 6:
				bubble = new Bubble(BubbleType.BUBBLE_PINYAU, walker.position);
				break;
			case 7:
				bubble = new Bubble(BubbleType.BUBBLE_CHIU, walker.position);
				break;
			case 8:
				bubble = new Bubble(BubbleType.BUBBLE_EWOKS2, walker.position);
				break;
			case 9:
				bubble = new Bubble(BubbleType.BUBBLE_JOSE_CHEWAKA, walker.position);
				break;
			default:
				bubble = new Bubble(BubbleType.BUBBLE_CHIU, walker.position);
			}
			break;
			
		case DARTH_VADER:
			switch((int)(Math.random()*9))
			{
			case 0:
				bubble = new Bubble(BubbleType.BUBBLE_A_OBI, walker.position);
				break;
			case 1:
				bubble = new Bubble(BubbleType.BUBBLE_A_OSCURO, walker.position);
				break;
			case 2:
				bubble = new Bubble(BubbleType.BUBBBE_A_PADRE_2, walker.position);
				break;
			case 3:
				bubble = new Bubble(BubbleType.BUBBLE_VADER, walker.position);
				break;
			case 4:
				bubble = new Bubble(BubbleType.BUBBLE_VADER, walker.position);
				break;
			case 5:
				bubble = new Bubble(BubbleType.BUBBLE_ALBA_DARTHVADER, walker.position);
				break;
			case 6:
				bubble = new Bubble(BubbleType.BUBBLE_ALBA_DARTHVADERPOSESO, walker.position);
				break;
			case 7:
				bubble = new Bubble(BubbleType.BUBBLE_JOSE_SOY_TU_PADRE, walker.position);
				break;
			case 8:
				default:
				bubble = new Bubble(BubbleType.BUBBLE_JOSE_TODAVIA_NO_ERES, walker.position);
			}
			break;
			
		case CHUWAKA:
			bubble = new Bubble(BubbleType.getRandomBubble(), walker.position);
			break;
		default:
			bubble = new Bubble(BubbleType.getRandomBubble(), walker.position);
		}
		
		movable = bubble;
		this.game.addEntity(bubble);
	}
	
	@Override
	public void execute(Map p) {
		//increase destination
//		this.destination = MathUtil.getPuntDesti(this.bubble.position.toPoint(), this.destination, 2000f);
		destination = new Point(bubble.position.getX(), bubble.position.getY() + 50);
		super.execute(p);

		bubble.playSound();
		
		walker.forceStopMovement();
		//walker.animateTalking();
		
		bubble.getSprite().registerEntityModifier(new AlphaModifier(2f, 1f, 0f, new IEntityModifierListener() {
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity sprite) {	
			}
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity sprite) {
				bubble.isFinished = true;
			}
		}));
	}
}
