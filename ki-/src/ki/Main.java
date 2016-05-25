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

		Sprite RL = new Sprite();
		RL.setImage("/ki/pic/smiley2.png");
		RL.setPosition(50 - 32, 50);

		Sprite RR = new Sprite();
		RR.setImage("/ki/pic/smiley2.png");
		RR.setPosition(50 + 32, 50);

		Sprite RD = new Sprite();
		RD.setImage("/ki/pic/smiley2.png");
		RD.setPosition(50, 50 - 32);

		Sprite RU = new Sprite();
		RU.setImage("/ki/pic/smiley2.png");
		RU.setPosition(50, 50 + 32);

		ArrayList<Sprite> wallList = new ArrayList<Sprite>();

		createWorldQuadratic(wallList);

		LongValue lastNanoTime = new LongValue(System.nanoTime());

		new AnimationTimer() {

			private Movement movement = Movement.DOWN;

			public void handle(long currentNanoTime) {
				// calculate time since last update.
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 100000000.0;
				lastNanoTime.value = currentNanoTime;

				// game logic
				smiley.setVelocity(0, 0);
				smiley.setTrend(movement);
				smiley.update(elapsedTime);

				RL.setVelocity(0, 0);
				RL.setTrend(movement);
				RL.update(elapsedTime);

				RR.setVelocity(0, 0);
				RR.setTrend(movement);
				RR.update(elapsedTime);

				RD.setVelocity(0, 0);
				RD.setTrend(movement);
				RD.update(elapsedTime);

				RU.setVelocity(0, 0);
				RU.setTrend(movement);
				RU.update(elapsedTime);

				// collision detection
				String pointsText = "";

				// show down
				Iterator<Sprite> wallListIter = wallList.iterator();
				while (wallListIter.hasNext()) {
					Sprite currentWall = wallListIter.next();

					if (RD.intersects(currentWall)) {
						pointsText = "RD ghost wall found!";
						movement = Movement.LEFT;
					} else if (RU.intersects(currentWall)) {
						pointsText = "RU ghost wall found!";
						movement = Movement.UP;
					} else if (RR.intersects(currentWall)) {
						pointsText = "RR ghost wall found!";
						movement = Movement.DOWN;
					} else if (RL.intersects(currentWall)) {
						pointsText = "RL ghost wall found!";
						movement = Movement.RIGHT;
					}
				}

				moveGhosts(RL, RR, RD, RU, movement);
				smiley.setTrend(movement);

				smiley.update(elapsedTime);
				RL.update(elapsedTime);
				RR.update(elapsedTime);
				RD.update(elapsedTime);
				RU.update(elapsedTime);

				// render
				gc.clearRect(0, 0, X, Y);
				smiley.render(gc);
				// RL.render(gc);
				// RR.render(gc);
				// RD.render(gc);
				// RU.render(gc);

				gc.fillText(pointsText, 360, 200);
				gc.strokeText(pointsText, 360, 200);

				for (Sprite currentObj : wallList)
					currentObj.render(gc);
			}

			private void moveGhosts(Sprite RL, Sprite RR, Sprite RD, Sprite RU, Movement movement) {
				RL.setTrend(movement);
				RR.setTrend(movement);
				RD.setTrend(movement);
				RU.setTrend(movement);
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