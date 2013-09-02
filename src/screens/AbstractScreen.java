
package screens;


import java.util.ArrayList;
import java.util.Collection;

import utils.GraphicsContext;
import utils.KeyboardInput;
import app.App;
import app.GameObserver;
import event.*;


public abstract class AbstractScreen implements Screen {

	protected Collection<GameObserver>	observers;
	protected event.GameState			gameState;
	protected App						app;
	protected KeyboardInput				keyboard;


	public AbstractScreen(App theApp, KeyboardInput theKeyboard) {
		this.observers = new ArrayList<GameObserver>();
		this.app = theApp;
		this.keyboard = theKeyboard;
	}


	@Override
	public void registerObserver(GameObserver anObserver) {
		observers.add(anObserver);
	}


	@Override
	public void notifyPaused() {
		for (GameObserver observer : observers)
			observer.gamePaused(new PauseEvent(this));
	}


	@Override
	public void notifyGameStateChanged() {
		for (GameObserver observer : observers)
			observer.gameStateChanged(new GameStateEvent(this, gameState));

	}


	@Override
	public void spriteOutOfBounds(OutOfBoundsEvent outOfBoundsEvent) {
		// TODO Auto-generated method stub

	}


	@Override
	public void spriteCollision(CollisionEvent collisionEvent) {
		// TODO Auto-generated method stub

	}


	@Override
	public void processInput(double delta) {
		// TODO Auto-generated method stub

	}


	@Override
	public void processLogic(double delta) {
		// TODO Auto-generated method stub

	}


	@Override
	public void render(GraphicsContext graphicsContext) {
		// TODO Auto-generated method stub

	}


	@Override
	public void nextFrame(double delta) {
		GraphicsContext graphicsContext = GraphicsContext.getInstance();
		processInput(delta);
		processLogic(delta);
		render(graphicsContext);
	}
}
