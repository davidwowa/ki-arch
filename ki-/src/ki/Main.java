package ki;

import java.util.ArrayList;
import java.util.Iterator;

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

		ArrayList<Sprite> wallList = new ArrayList<Sprite>();

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

		LongValue lastNanoTime = new LongValue(System.nanoTime());

		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				// calculate time since last update.
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 100000000.0;
				lastNanoTime.value = currentNanoTime;

				// game logic

				// smiley.setVelocity(0, 0);
				// if (input.contains("LEFT"))
				// smiley.addVelocity(-50, 0);
				// if (input.contains("RIGHT"))
				// smiley.addVelocity(50, 0);
				// if (input.contains("UP"))
				// smiley.addVelocity(0, -50);
				// if (input.contains("DOWN"))
				// smiley.addVelocity(0, 50);

				smiley.setVelocity(0, 0);
				smiley.addVelocity(50, 0);
				smiley.update(elapsedTime);

				// collision detection
				boolean left = false, right = false, up = false, down = false;

				Sprite ghost = new Sprite();
				ghost.setImage("/ki/pic/smiley2.png");
				ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());

				// show left
				ghost.addVelocity(-50, 0);
				ghost.update(elapsedTime);
				Iterator<Sprite> wallListIterLeft = wallList.iterator();
				while (wallListIterLeft.hasNext()) {
					Sprite currentWallLeft = wallListIterLeft.next();
					if (ghost.intersects(currentWallLeft)) {
						left = true;
					}
				}

				// show right
				ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
				ghost.addVelocity(50, 0);
				ghost.update(elapsedTime);
				Iterator<Sprite> wallListIterRight = wallList.iterator();
				while (wallListIterRight.hasNext()) {
					Sprite currentWallRight = wallListIterRight.next();
					if (ghost.intersects(currentWallRight)) {
						right = true;
					}
				}

				// show up
				ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
				ghost.addVelocity(0, -50);
				ghost.update(elapsedTime);
				Iterator<Sprite> wallListIterUp = wallList.iterator();
				while (wallListIterUp.hasNext()) {
					Sprite currentWallUp = wallListIterUp.next();
					if (ghost.intersects(currentWallUp)) {
						up = true;
					}
				}

				// show down
				ghost.setPosition(smiley.getPositionX(), smiley.getPositionY());
				ghost.addVelocity(0, 50);
				ghost.update(elapsedTime);
				Iterator<Sprite> wallListIterDown = wallList.iterator();
				while (wallListIterDown.hasNext()) {
					Sprite currentWallDown = wallListIterDown.next();
					if (ghost.intersects(currentWallDown)) {
						down = true;
					}
				}

				if (left) {
					smiley.addVelocity(-50, 0);
				} else if (right) {
					smiley.addVelocity(50, 0);
				} else if (up) {
					smiley.addVelocity(0, -50);
				} else if (down) {
					smiley.addVelocity(0, 50);
				}

				smiley.update(elapsedTime);

				// render
				gc.clearRect(0, 0, X, Y);
				smiley.render(gc);

				for (Sprite currentObj : wallList)
					currentObj.render(gc);
			}
		}.start();
		theStage.show();
	}
}