// 	CSE 205 Honors Contract Project: Space Invaders
//         Name: Lienna Tieu
//    StudentID: 1214554627
//      Lecture: MW 4:35PM
//  Description: Program creates a playable clone of arcade game Space Invaders.
//	Inspired by youtube tutorials by Almas Baimagambetov

import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SpaceInvaders extends Application
{
	//creates pane for the game
	Pane pane = new Pane();
	
	//timer for enemies to shoot
	private double t = 0;
	int deadCount = 0;
	
	//creates text objects to show up on pane
	Text welcome = new Text();
	Text instruct = new Text();
	GridPane paneLarge = new GridPane();
	
	//creates sprite for the player
	private Sprite character = new Sprite(300, 750, 50, 50, "player", Color.DEEPSKYBLUE);
	
	// **************************************************** //
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	private Parent createContent() 
	{
		//sets size of game window
		pane.setPrefSize(700, 900);
		//adds player to the window
		pane.getChildren().add(character);
		
		//sets animation time so that game can 'update' during a time period
		AnimationTimer timer = new AnimationTimer() 
		{
			public void handle(long now)
			{
				update();
			}
		};
		//starts time
		timer.start();
		//creates the level and enemies
		level();
		return paneLarge;
	}
	
	//allows the player to move around on the screen
	//sets up screen for player
		public void start(Stage stage) throws Exception
		{
			Scene scene = new Scene(createContent());
//			Group root = new Group();
			
			//sets style/color of pane
			paneLarge.setStyle("-fx-background-color: black;");
			paneLarge.getChildren().addAll(pane);
			
			//sets text for game
			welcome.setText("Welcome to Space Invaders!");
			instruct.setText("A = Move Left, D = Move Right, SPACE = Shoot, EXIT to restart");
			
			//creates style for title
			welcome.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
			welcome.setFill(Color.RED);
			welcome.setStrokeWidth(2);
			welcome.setStroke(Color.LIGHTSKYBLUE);
			//creates style of instructions
			instruct.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
			instruct.setFill(Color.WHITE);
			
			//formats and adds where text should be
			paneLarge.add(welcome, 0, 2);
			paneLarge.add(instruct, 0, 1);
			
			//allows the user to use controls to move character sprite
			scene.setOnKeyPressed(e -> 
			{
				switch (e.getCode())
				{
				case D:
					character.moveRight();
					break;
				case A:
					character.moveLeft();
					break;
				case SPACE:
					shoot(character);
					break;
				}
			});
			
			//shows and creates canvas for user
			stage.setScene(scene);
			stage.show();
		}
	
		
	//creates level for the user
	private void level()
	{
		for (int i = 0; i < 6; i++)
		{
			//creates 6 enemy sprites and adds to pane
			Sprite enemySprites = new Sprite(90 + i*100, 150, 45, 45, "enemy", Color.ORANGERED);
			pane.getChildren().add(enemySprites);
		}
	}
	
	//creates a list of sprites
	private List<Sprite> sprites() 
	{
		return pane.getChildren().stream().map(n -> 
		(Sprite)n).collect(Collectors.toList());
	}
	
	private void update()
	{
		//sets timer for enemies to shoot every 30 millisec
		t += 0.06;
		//for each sprite...
		sprites().forEach(enemySprites ->
		{
			switch (enemySprites.TYPE) {
				case "enemylazer":
					enemySprites.moveDown();
				
					//is the enemy bullet hits the player
					//player dies
					if(enemySprites.getBoundsInParent().intersects(character.getBoundsInParent()))
					{
						character.dead = true;
						enemySprites.dead = true;
					}
				break;
				
				case "playerlazer":
					enemySprites.moveUp();
				
					sprites().stream().filter(e -> e.TYPE.equals("enemy")).forEach(enemy ->
					{
						//if the player's bullet hits the enemies
						//enemies disappear
						if (enemySprites.getBoundsInParent().intersects(enemy.getBoundsInParent()))
							{
								enemy.dead = true;
								enemySprites.dead = true;
							}
					});
					break;
				
				//case for the enemy to shoot at random at the player
				case "enemy":
					if (t > 2) {
						if (Math.random() < 0.3)
						{
							shoot(enemySprites);
						}
					}
					break;
				} //end of switch case
			});//end of lambda expression
		
		//removes sprites (ene
		pane.getChildren().removeIf(n ->
		{
			//declares sprite dead
			Sprite s = (Sprite) n;
			return s.dead;
		});
		
		//timer
		if (t>2)
		{
			t = 0;
		}
	}
	
	//creates shoot method for sprites
	private void shoot(Sprite shooter)
	{
		//creates sprite for laser
		Sprite lazer = new Sprite( (int)shooter.getTranslateX() + 20, (int) shooter.getTranslateY(), 5, 20, shooter.TYPE + "lazer", Color.ORANGE);
		pane.getChildren().add(lazer);
	}
	
}
