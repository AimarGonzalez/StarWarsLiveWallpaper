package com.mel.wallpaper.starWars.entity.commands;


import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.mel.util.MathUtil;
import com.mel.util.Point;
import com.mel.wallpaper.starWars.entity.Jumper;
import com.mel.wallpaper.starWars.entity.Map;

public class JumpCommand extends Command
{
	public Point destination;
	protected Point finalDestination;
	public Jumper jumper;
	public IEaseFunction easeFunction = EaseLinear.getInstance();
	
	
	
	public JumpCommand(Jumper jumper) {
		walker = jumper;
		this.jumper = jumper;
	}
	
	@Override
	public void execute(Map p) {
		this.jumper.forceStopMovement();
		
		moveObject();
		
		this.jumper.jump(this.finalDestination);
	}

	protected void moveObject() {
		Point origen = (Point)this.jumper.getPosition().toPoint();
		this.finalDestination = fixDestinationCoordenates(this.destination);
		
		float angle = MathUtil.getAngulo(origen.getX(), origen.getY(), this.finalDestination.getX(), this.finalDestination.getY());
		
		float distance =  origen.distance(this.finalDestination);
		float jumpDistance =  distance/2f;
		if(distance > Jumper.MAX_JUMP_DISTANCE){
			distance = Jumper.MAX_JUMP_DISTANCE;
		}
		
		this.finalDestination.setX(origen.getX()+(float)Math.cos(angle)*jumpDistance-factor*distance/2f); //hacemos que se tire un poco delante de la pelota
		this.finalDestination.setY(origen.getY()+(float)Math.sin(angle)*jumpDistance);
		
		if(origen.distance(this.finalDestination) > Jumper.MAX_JUMP_DISTANCE){
			Debug.d("jump eeeerrooorrr!!");
		}
	}
	
	
	protected Point fixDestinationCoordenates(Point destination){
		//Este metodo solo se puede usar dentro de executeCommand(), NUNCA ANTES pq no tendrias el factor!
		Point fixedDestination = (Point)destination.clone();
		fixedDestination.setLocation(fixedDestination.getX()*factor, fixedDestination.getY()*factor);//aplicamos factor de inversion de campo (-1 o 1)
		return fixedDestination;
	}
	
	
	
}
