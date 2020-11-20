// 	CSE 205 Honors Contract Project: Space Invaders
//         Name: Lienna Tieu
//    StudentID: 1214554627
//      Lecture: MW 4:35PM
//  Description: Program helps SpaceInvaders.java create sprites
//	Inspired by youtube tutorials by Almas Baimagambetov


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
	
	//sets boolean to see if sprite is dead initally false (alive)	
	boolean dead = false;
	final String TYPE;
	
	//creates a sprite
	Sprite(int x, int y, int width, int height, String type, Color color)
	{
		//sets width, height and color of the sprite
		super(width, height, color);
		TYPE = type;
		
		//to move sprites
		setTranslateX(x);
		setTranslateY(y);
	}
	
	//methods moveLeft and moveRight are reserved for the user's character sprite
	void moveLeft()
	{
		setTranslateX(getTranslateX() - 5);
	}
		
	void moveRight()
	{
		setTranslateX(getTranslateX() + 5);
	}
		
	//methods moveUp and moveDown are reserved for the bullets
	void moveUp()
	{
		setTranslateY(getTranslateY() - 5);
	}
		
	void moveDown()
	{
		setTranslateY(getTranslateY() + 5);
	}
}
