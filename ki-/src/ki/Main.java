package ki;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * 
 * @see https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development
 *
 */
public class Main extends Application {

	private int X = 1024;
	private int Y = 1024;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("TEST");

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(X, Y);
		root.getChildren().add(canvas);

		ArrayList<String> input = new ArrayList<String>();

		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if (!input.contains(code))
					input.add(code);
			}
		});

		theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				input.remove(code);
			}
		});

		GraphicsContext gc = canvas.getGraphicsContext2D();

		Sprite smiley = new Sprite();
		smiley.setImage("/ki/pic/smiley.png");
		smiley.setPosition(50, 50);

		Sprite ghost = new Sprite();
		ghost.setImage("/ki/pic/smiley2.png");
		ghost.setPosition(50, 50);

		ArrayList<Sprite> wallList = new ArrayList<Sprite>();

		createWorldQuadratic(wallList);

		LongValue lastNanoTime = new LongValue(System.nanoTime());

		new AnimationTimer() {

			private Movement movement = Movement.DOWN;
			private Movement lastMovement = Movement.DOWN;

			public void handle(long currentNanoTime) {
				// calculate time since last update.
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 100000000.0;
				lastNanoTime.value = currentNanoTime;

				// game logic
				smiley.setVelocity(0, 0);
				smiley.setTrend(movement);
				smiley.update(elapsedTime);

				ghost.setVelocity(0, 0);
				ghost.setTrend(movement);
				ghost.update(elapsedTime);

				lastMovement = movement;

				// collision detection
				String pointsText = "";
				Iterator<Sprite> wallListIter = wallList.iterator();
				while (wallListIter.hasNext()) {
					Sprite currentWall = wallListIter.next();
					if (smiley.intersects(currentWall)) {
						pointsText = "smiley wall found!";

						boolean down = false, up = false, left = false, right = false;

						// show down
						ghost.setTrend(Movement.DOWN);
						ghost.update(elapsedTime);
						ghost.render(gc);
						Iterator<Sprite> wallListIterGhostDown = wallList.iterator();
						while (wallListIterGhostDown.hasNext()) {
							Sprite currentWallGhostDown = wallListIterGhostDown.next();
							if (ghost.intersects(currentWallGhostDown)) {
								pointsText = "ghost wall found!";
								down = true;
							}
						}
						ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
						// set velocity back from smiley

						// show up
						ghost.setTrend(Movement.UP);
						ghost.update(elapsedTime);
						ghost.render(gc);
						Iterator<Sprite> wallListIterGhostUp = wallList.iterator();
						while (wallListIterGhostUp.hasNext()) {
							Sprite currentWallGhostUp = wallListIterGhostUp.next();
							if (ghost.intersects(currentWallGhostUp)) {
								pointsText = "ghost wall found!";
								up = true;
							}
						}
						ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
						// set velocity back from smiley

						// show right
						ghost.setTrend(Movement.RIGHT);
						ghost.update(elapsedTime);
						ghost.render(gc);
						Iterator<Sprite> wallListIterGhostRight = wallList.iterator();
						while (wallListIterGhostRight.hasNext()) {
							Sprite currentWallGhostRight = wallListIterGhostRight.next();
							if (ghost.intersects(currentWallGhostRight)) {
								pointsText = "ghost wall found!";
								right = true;
							}
						}
						ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
						// set velocity back from smiley

						// show left
						ghost.setTrend(Movement.LEFT);
						ghost.update(elapsedTime);
						ghost.render(gc);
						Iterator<Sprite> wallListIterGhostLeft = wallList.iterator();
						while (wallListIterGhostLeft.hasNext()) {
							Sprite currentWallGhostLeft = wallListIterGhostLeft.next();
							if (ghost.intersects(currentWallGhostLeft)) {
								pointsText = "ghost wall found!";
								left = true;
							}
						}
						ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
						// set velocity back from smiley

//						if (right == false) {
//							movement = Movement.RIGHT;
//						} else if (left == false) {
//							movement = Movement.LEFT;
//						} else if (down == false) {
//							movement = Movement.DOWN;
//						} else if (up == false) {
//							movement = Movement.UP;
//						}

						 switch (lastMovement) {
						 case DOWN:
						 movement = Movement.LEFT;
						 break;
						 case LEFT:
						 movement = Movement.UP;
						 break;
						 case RIGHT:
						 movement = Movement.DOWN;
						 break;
						 case UP:
						 movement = Movement.RIGHT;
						 break;
						 }
					}
				}

				smiley.update(elapsedTime);
				ghost.update(elapsedTime);

				// render
				gc.clearRect(0, 0, X, Y);
				smiley.render(gc);
				ghost.render(gc);

				gc.fillText(pointsText, 360, 200);
				gc.strokeText(pointsText, 360, 200);

				for (Sprite currentObj : wallList)
					currentObj.render(gc);
			}
		}.start();
		theStage.show();
	}

	private void createWorldQuadratic(ArrayList<Sprite> wallList) {
		// horizontal walls
		int countX = -32;
		while (countX < X) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/pic/wall.png");
			countX = countX + 32;
			double px = countX;
			wall.setPosition(px, -32);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/pic/wall.png");
			wall2.setPosition(px, 992);
			wallList.add(wall2);
		}

		// vertical walls
		int countY = -32;
		while (countY < Y) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/pic/wall.png");
			countY = countY + 32;
			double py = countY;
			wall.setPosition(-32, py);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/pic/wall.png");
			wall2.setPosition(992, py);
			wallList.add(wall2);
		}

		// horizontal walls (2)
		int countXX = 96;
		while (countXX < (X - 192)) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/pic/wall.png");
			countXX = countXX + 32;
			double px = countXX;
			wall.setPosition(px, 128);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/pic/wall.png");
			wall2.setPosition(px, 864);
			wallList.add(wall2);
		}

		// vertical walls (2)
		int countYY = 100;
		while (countYY < (Y - 192)) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/pic/wall.png");
			countYY = countYY + 32;
			double py = countYY;
			wall.setPosition(128, py);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/pic/wall.png");
			wall2.setPosition(832, py);
			wallList.add(wall2);
		}
	}
}