package ki;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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

		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc.setFont(theFont);
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

		Sprite smiley = new Sprite();
		smiley.setImage("/ki/smiley.png");
		smiley.setPosition(50, 50);

		ArrayList<Sprite> wallList = new ArrayList<Sprite>();

		// for (int i = 0; i < 15; i++) {
		// Sprite moneybag = new Sprite();
		// moneybag.setImage("/ki/moneybag.png");
		// double px = 942 * Math.random() + 50;
		// double py = 942 * Math.random() + 50;
		// moneybag.setPosition(px, py);
		// wallList.add(moneybag);
		// }

		// horizontal walls
		int countX = -32;
		while (countX < X) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/wall.png");
			countX = countX + 32;
			double px = countX;
			wall.setPosition(px, -32);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/wall.png");
			wall2.setPosition(px, 992);
			wallList.add(wall2);
		}

		// vertical walls
		int countY = -32;
		while (countY < Y) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/wall.png");
			countY = countY + 32;
			double py = countY;
			wall.setPosition(-32, py);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/wall.png");
			wall2.setPosition(992, py);
			wallList.add(wall2);
		}

		// horizontal walls (2)
		int countXX = 96;
		while (countXX < (X - 192)) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/wall.png");
			countXX = countXX + 32;
			double px = countXX;
			wall.setPosition(px, 128);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/wall.png");
			wall2.setPosition(px, 864);
			wallList.add(wall2);
		}

		// vertical walls (2)
		int countYY = 100;
		while (countYY < (Y - 192)) {
			Sprite wall = new Sprite();
			wall.setImage("/ki/wall.png");
			countYY = countYY + 32;
			double py = countYY;
			wall.setPosition(128, py);
			wallList.add(wall);

			Sprite wall2 = new Sprite();
			wall2.setImage("/ki/wall.png");
			wall2.setPosition(832, py);
			wallList.add(wall2);
		}

		LongValue lastNanoTime = new LongValue(System.nanoTime());

		IntValue score = new IntValue(0);

		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				// calculate time since last update.
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
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

				Random random = new Random();

				smiley.setVelocity(0, 0);
				int x = random.nextInt(50);
				int y = random.nextInt(50);

				if (smiley.getBoundary().getMaxX() < 972 && smiley.getBoundary().getMaxY() < 972) {
					smiley.addVelocity(x, y);
					smiley.update(elapsedTime);
				}

				// collision detection

				Iterator<Sprite> wallListIter = wallList.iterator();
				while (wallListIter.hasNext()) {
					Sprite currentWall = wallListIter.next();
					if (smiley.intersects(currentWall)) {

						if (smiley.getBoundary().getMaxX() < 972 && smiley.getBoundary().getMaxY() < 972) {
							if (smiley.getBoundary().getMaxX() > 900) {
								smiley.addVelocity((-1.) * x, y);
								smiley.update(elapsedTime);
							} else if (smiley.getBoundary().getMaxY() > 900) {
								smiley.addVelocity(x, (-1.) * y);
								smiley.update(elapsedTime);
							}
						}
						// moneybagIter.remove();
						// score.value++;
					}
				}

				// Iterator<Sprite> moneybagIter = wallList.iterator();
				// while (moneybagIter.hasNext()) {
				// Sprite moneybag = moneybagIter.next();
				// if (smiley.intersects(moneybag)) {
				//
				// smiley.addVelocity(random.nextInt(50),
				// random.nextInt(50));
				//
				//// moneybagIter.remove();
				//// score.value++;
				// }
				// }

				// render

				gc.clearRect(0, 0, X, Y);
				smiley.render(gc);

				for (Sprite moneybag : wallList)
					moneybag.render(gc);

				// String pointsText = "Cash: $" + (100 * score.value);
				// gc.fillText(pointsText, 360, 36);
				// gc.strokeText(pointsText, 360, 36);
			}
		}.start();

		theStage.show();
	}
}