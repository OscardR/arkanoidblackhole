
package event;

import screens.GameObservable;


public class GameStateEvent {

	private GameState		gameState;
	private GameObservable	origin;


	public GameStateEvent(GameObservable theOrigin, GameState newState) {
		this.origin = theOrigin;
		this.gameState = newState;
	}


	public GameObservable getOrigin() {
		return origin;
	}


	public GameState getGameState() {
		return gameState;
	}
}
