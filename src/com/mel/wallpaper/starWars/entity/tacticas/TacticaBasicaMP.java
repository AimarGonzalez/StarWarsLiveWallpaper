package com.mel.wallpaper.starWars.entity.tacticas;

import java.util.ArrayList;
import java.util.List;

import com.mel.wallpaper.starWars.entity.Field;
import com.mel.wallpaper.starWars.entity.GoalKeeper;
import com.mel.wallpaper.starWars.entity.Player;
import com.mel.wallpaper.starWars.entity.Player.Rol;
import com.mel.wallpaper.starWars.view.PlayerAnimation;
import com.mel.wallpaper.starWars.view.SpriteFactory;

public class TacticaBasicaMP extends TacticaBasica
{
	

	public TacticaBasicaMP(Field field) {
		super(field);
	}

	public List<Player> getInitialPlayers(int factor){
		List<Player> players = new ArrayList<Player>();
		
		PlayerAnimation porteroInitialAnimation = null;
		if(factor == 1){
			porteroInitialAnimation = PlayerAnimation.STOP_E;
		}else{
			porteroInitialAnimation = PlayerAnimation.STOP_W;
		}
		
		players.add(new GoalKeeper(players.size()+1, -500, 0, SpriteFactory.EDWARNER, porteroInitialAnimation));

		players.add(new Player(players.size()+1, -50, 0, SpriteFactory.MARC, PlayerAnimation.STOP_S, Rol.DAVANTER));
		players.add(new Player(players.size()+1, -50, 0, SpriteFactory.MP_BLACK, PlayerAnimation.STOP_S, Rol.DEFENSA));
		players.add(new Player(players.size()+1, -50, 0, SpriteFactory.MP_BLACK2, PlayerAnimation.STOP_S, Rol.MIGCAMPISTA));
		players.add(new Player(players.size()+1, -50, -20, SpriteFactory.MP_WHITE, PlayerAnimation.STOP_S, Rol.DEFENSA));
		players.add(new Player(players.size()+1, -50, 20, SpriteFactory.MP_WHITE, PlayerAnimation.STOP_S, Rol.DAVANTER));
		
		return players;
	}

	
}
