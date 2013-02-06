package com.mel.wallpaper.starWars.entity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.util.math.MathUtils;

import com.mel.util.Point;
import com.mel.wallpaper.starWars.view.Position;

public class InvisibleWalls
{
	public Sprite background;
	public float width;
	public float height;
	
	
	public float paddingLeft;
	public float paddingRight;
	public float paddingTop;
	public float paddingBottom;
	public static  float correccionCampoX;
	public static  float correccionCampoY;
	public static float rightWall;
	public static float leftWall;
	public static float topWall;
	public static float bottomWall;
	
	public static float mapWidth;
	public static float mapHeight;
	
	public static float outOfScreenMargin = 100f;
	
	

	public InvisibleWalls(float paddingLeft, float paddingRight, float paddingTop, float paddingBottom, Sprite background){
		this.background = background;

		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.paddingTop = paddingTop;
		this.paddingBottom = paddingBottom;
		
		this.mapWidth = this.background.getWidth();
		this.mapHeight = this.background.getHeight();
		
		this.width = background.getWidth()-(paddingLeft+paddingRight);
		this.height = background.getHeight()-(paddingTop+paddingBottom);
		InvisibleWalls.correccionCampoX = paddingLeft+this.width/2;
		InvisibleWalls.correccionCampoY = paddingTop+this.height/2;
		
		InvisibleWalls.rightWall = this.width/2;
		InvisibleWalls.leftWall = -this.width/2;
		InvisibleWalls.topWall = this.height/2;
		InvisibleWalls.bottomWall = -this.height/2;
	}
	
	public static Point getRandomPoint() {
		return getRandomPoint(0);
	}
	
	public static Point getRandomPoint(float margin) {
		return getRandomPoint(margin, margin, margin, margin);
	}
	
	public static Point getRandomPoint(float leftMargin, float rightMargin, float topMargin, float bottomMargin) {
		return new Point(MathUtils.random(leftWall+leftMargin, rightWall-rightMargin), MathUtils.random(bottomWall+topMargin, topWall-bottomMargin));
	}
	
	public Point getRandomPointOut() {
		return new Point(MathUtils.random(leftWall,  rightWall), MathUtils.random(topWall, topWall+100));
	}
	
	public static  boolean isOutTouchLine(Point posicio) {
		return (posicio.getY() > topWall || posicio.getY() < bottomWall);
	}
	
	public static boolean isOutGoalLine(Point posicio) {
		return (posicio.getX() > rightWall || posicio.getX() < leftWall);
	}
	
	
	public static  boolean isOutField(Point posicio) {
		return (isOutTouchLine(posicio) || isOutGoalLine(posicio));
	}
	
	
	
	public static boolean isOutOfScreen(Point posicio){
		if(posicio.getX() > mapWidth/2 + outOfScreenMargin){
			return true;
		}
		
		if(posicio.getX() < -(mapWidth/2 + outOfScreenMargin)){
			return true;
		}
		
		if(posicio.getY() > mapHeight/2 + outOfScreenMargin){
			return true;
		}
		
		if(posicio.getY() < -(mapHeight/2 + outOfScreenMargin)){
			return true;
		}
		
		return false;
	}
	
	
	/* helpers */
	public static  Point cartesianToEngineCoordinates(Position coordenadas){
		return cartesianToEngineCoordinates(coordenadas.toPoint());
	}
	public static  Point cartesianToEngineCoordinates(Point cartesianCoords){
		Point fixedCoord;
		
		float x = correccionCampoX+cartesianCoords.getX();
		float y = correccionCampoY-cartesianCoords.getY();
		
		fixedCoord = new Point(x, y);
		return fixedCoord;
	}
	
	public Point engineToCartesianCoordinates(Point engineCoords){
		Point fixedCoord;
		
		float x = engineCoords.getX()-correccionCampoX;
		float y = correccionCampoY-engineCoords.getY();
		
		fixedCoord = new Point(x, y);
		return fixedCoord;
	}
	
	
}
