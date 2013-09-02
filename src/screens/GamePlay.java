
package screens;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import event.*;
import app.App;

import sprites.*;
import utils.*;


public class GamePlay extends AbstractScreen {

	private Sprite				pad;
	private ArrayList<Sprite>	balls;
	private int					ballsOnScreen;
	private ArrayList<Sprite>	ballsOutOfBounds;
	private int					ballNumber;
	private double				ballSpeed;
	private int					ballRadius;
	private Sprite[]			blocks;
	private int					lives;
	private int					score;

	// Used for random calculations;
	Random						rand	= new Random();


	public GamePlay(App theApp, KeyboardInput theKeyboard) {
		super(theApp, theKeyboard);

		// Init the pad
		pad = new Pad(
			Defaults.WINDOW_WIDTH / 2 - Defaults.PAD_WIDTH / 2,
			Defaults.WINDOW_HEIGHT - Defaults.PAD_HEIGHT / 2);

		ballNumber = Defaults.BALL_NUMBER;
		ballRadius = Defaults.BALL_RADIUS;
		ballSpeed = Defaults.BALL_SPEED;

		initBalls();
		initBlocks();

		lives = Defaults.LIVES;
		score = 0;
	}


	private void initBalls() {
		// Init the ball(s)
		balls = new ArrayList<Sprite>();
		ballsOutOfBounds = new ArrayList<Sprite>();
		for (int i = 0; i < ballNumber; i++) {
			Sprite ball = new Ball(pad.x + pad.w / 2 - ballRadius, pad.y
					- pad.h - ballRadius, ballRadius);
			ball.speed = new Vector2D(rand.nextDouble() * -Math.PI * 0.80
					- Math.PI * 0.10, ballSpeed);
			ball.registerObserver(this);
			balls.add(ball);
		}
		ballsOnScreen = balls.size();
	}


	private void initBlocks() {
		// Init the blocks
		blocks = new Block[Defaults.MAX_BLOCKS_V * Defaults.MAX_BLOCKS_H];
		for (int i = 0; i < Defaults.MAX_BLOCKS_V / 2; i++) {
			for (int j = 1; j < Defaults.MAX_BLOCKS_H - 1; j++) {
				blocks[i * Defaults.MAX_BLOCKS_H + j] = new Block(j
						* Defaults.BLOCK_WIDTH, i * Defaults.BLOCK_HEIGHT + 80);
			}
		}
	}


	/**
	 * Método para dibujar la pantalla, con todos los
	 * elementos, usando un doble buffer.
	 * 
	 * @param graphicsContext
	 */
	@Override
	public void render(GraphicsContext graphicsContext) {

		// Obtain graphics:
		Graphics2D g2d = graphicsContext.getGraphics2D();

		// Draw pad
		pad.draw(g2d);

		// Draw blocks
		for (int i = 0; i < blocks.length; i++)
			if (blocks[i] != null)
				blocks[i].draw(g2d);

		// Draw game info
		g2d.setColor(Color.WHITE);
		g2d.drawString("Score: " + score, 250, 32);
		g2d.drawString("Vidas: " + lives, 250, 44);
		g2d.drawString("Bolas en juego: " + ballsOnScreen, 250, 56);

		g2d.drawString("[q / w] Radio: " + ballRadius, 500, 32);
		g2d.drawString("[a / s] Velocidad: " + ballSpeed, 500, 44);
		g2d.drawString("[z / x] Número de Bolas: " + ballNumber, 500, 56);

		// Draw ball(s)
		for (Sprite ball : balls)
			ball.draw(g2d);

	}


	@Override
	public void processInput(double delta) {
		// If moving down
		if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
			pad.speed.setAngle(Math.PI / 2);
			if (pad.speed.getLength() < Defaults.MAX_SPEED)
				pad.speed.grow(3 * delta);
		} else {
			if (pad.speed.getLength() > 0)
				pad.speed.grow(-delta);
		}

		// If moving up
		if (keyboard.keyDown(KeyEvent.VK_UP)) {
			pad.speed.setAngle(3 * Math.PI / 2);
			if (pad.speed.getLength() < Defaults.MAX_SPEED)
				pad.speed.grow(3 * delta);
		} else {
			if (pad.speed.getLength() < 0)
				pad.speed.grow(-delta);
		}

		// If moving left
		if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
			pad.speed.setAngle(Math.PI);
			if (pad.speed.getLength() < Defaults.MAX_SPEED)
				pad.speed.grow(3 * delta);
		} else {
			if (pad.speed.getLength() < 0)
				pad.speed.grow(-delta);
		}

		// If moving right
		if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
			pad.speed.setAngle(0);
			if (pad.speed.getLength() < Defaults.MAX_SPEED)
				pad.speed.grow(3 * delta);
		} else {
			if (pad.speed.getLength() > 0)
				pad.speed.grow(-delta);
		}

		if (keyboard.keyDownOnce(KeyEvent.VK_X))
			ballNumber++;

		if (keyboard.keyDownOnce(KeyEvent.VK_Z) && ballNumber > 1)
			ballNumber--;

		if (keyboard.keyDownOnce(KeyEvent.VK_S))
			ballSpeed++;

		if (keyboard.keyDownOnce(KeyEvent.VK_A) && ballSpeed > 1)
			ballSpeed--;

		if (keyboard.keyDownOnce(KeyEvent.VK_W))
			ballRadius++;

		if (keyboard.keyDownOnce(KeyEvent.VK_Q) && ballRadius > 0.5)
			ballRadius--;
	}


	@Override
	public void processLogic(double delta) {

		// Move pad:
		pad.x += pad.speed.getX();
		pad.y += pad.speed.getY();

		// Collision check
		((Pad) pad).checkCollisionsWithBounds(new Rectangle(
			0,
			0,
			Defaults.WINDOW_WIDTH,
			Defaults.WINDOW_HEIGHT));

		// Move ball(s):
		for (Sprite s : balls) {

			s.x += s.speed.getX();
			s.y += s.speed.getY();

			// Collision check
			Ball ball = (Ball) s;
			ball.checkCollisionWithBounds(app.getBounds());

			for (int i = 0; i < blocks.length; i++) {
				Block currentBlock = (Block) blocks[i];
				if (currentBlock != null) {
					boolean didHit = ball.checkCollisionWithBlock(currentBlock);
					if (didHit)
						score += 100;
					if (currentBlock.hitCount == currentBlock.type.hitCount) {
						blocks[i] = null;
						score += 200;
						break;
					}
				}
			}

			if (ball.checkCollisionWithPad(pad))
				score += 50;
		}
		balls.removeAll(ballsOutOfBounds);
	}


	@Override
	public void spriteOutOfBounds(OutOfBoundsEvent outOfBoundsEvent) {
		ballsOutOfBounds.add(outOfBoundsEvent.getSprite());
		ballsOnScreen--;
		System.out.println("Bolas en juego: " + ballsOnScreen);
		if (ballsOnScreen <= 0) {
			lives -= 1;
			System.out.println("Vidas: " + lives);
			if (lives > 0) {
				gameState = GameState.NEW_BALL;
				initBalls();
				notifyGameStateChanged();
			} else {
				gameState = GameState.GAME_OVER;
				notifyGameStateChanged();
			}
		}
	}
}
