
package app;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import event.*;
import screens.*;
import utils.*;


public class ArkanoidBlackhole extends App {

	private static final long	serialVersionUID	= 4685864111640324069L;

	// Keyboard polling
	KeyboardInput				keyboard			= new KeyboardInput();

	// Our drawing component
	Canvas						canvas;

	// Usado para la pantalla actual (no confundir con
	// nivel):
	Screen						screen;
	Collection<Screen>			screens;

	// Variables para depuración:
	private long				lastFpsTime;
	private int					currentFps;
	private int					fps;

	// Variables de la lógica de juego:
	public boolean				gameRunning;
	private GameState			gameState;


	public ArkanoidBlackhole() {

		setIgnoreRepaint(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(Defaults.WINDOW_WIDTH, Defaults.WINDOW_HEIGHT);
		add(canvas);
		pack();

		// Hookup keyboard polling
		addKeyListener(keyboard);
		canvas.addKeyListener(keyboard);

		screens = new ArrayList<Screen>();
//		screen = new StarField(this, keyboard);
//		screen.registerObserver(this);
//		screens.add(screen);
		screen = new GamePlay(this, keyboard);
		screen.registerObserver(this);
		screens.add(screen);
	}


	public void run() {
		GraphicsContext.setCanvas(canvas);
		GraphicsContext context = GraphicsContext.getInstance();

		Graphics2D g2d = context.getGraphics2D();

		long lastLoopTime = System.nanoTime();

		gameRunning = true;
		while (gameRunning) {

			// Poll the keyboard
			keyboard.poll();
			// Should we exit?
			if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
				gameState = GameState.PAUSED;
				gameRunning = false;
			}

			// work out how long its been since the last
			// update, this will be used to calculate how
			// far the entities should move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) Defaults.OPTIMAL_TIME);

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			// update our FPS counter if a second has passed
			// since we last recorded
			if (lastFpsTime >= 1000000000) {
				currentFps = fps;
				lastFpsTime = 0;
				fps = 0;
			}

			// Cálculos y render para la pantalla actual:

			// Borramos el buffer en el background:
			context.clearBuffer();

			for (Screen screen : screens)
				screen.nextFrame(delta);

			if (g2d == null)
				g2d = context.getGraphics2D();

			// Draw help
			g2d.setColor(Color.WHITE);
			g2d.drawString("Flechas para mover", 20, 32);
			g2d.drawString("ESC para salir", 20, 44);
			g2d.drawString("FPS: " + currentFps, 20, 56);

			// Y dibujamos el total en el contexto actual:
			context.drawAndFlip();

			// We want each frame to take 10 milliseconds,
			// to do this we've recorded when we started the
			// frame.
			// We add 10 milliseconds to this and then
			// factor in the current time to give us our
			// final value to wait for.
			// Remember this is in ms, whereas our
			// lastLoopTime etc. vars are in ns.
			try {
				long sleepTime = (lastLoopTime - System.nanoTime()) / 1000000 + 10;
				if (sleepTime > 0)
					Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {
		App app = new ArkanoidBlackhole();
		app.setTitle("Arkanoid Blackhole");
		app.setVisible(true);
		app.run();
		System.exit(0);
	}


	@Override
	public void gamePaused(PauseEvent pe) {
		gameRunning = false;
		screen = new PauseScreen();
	}


	@Override
	public void gameStateChanged(GameStateEvent gse) {
		gameRunning = false;
		gameState = gse.getGameState();
		switch (gameState) {
		case NEW_BALL:
			System.out.println("Nueva bola! Quedan: "
					+ gse.getOrigin().getClass());
			gameRunning = true;
			break;
		case GAME_OVER:
			System.out.println("Game Over!");
			break;
		case LEVEL_SELECTION:
		case WELCOME_SCREEN:
		default:
			break;
		}
	}
}
