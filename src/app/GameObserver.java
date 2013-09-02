
package app;

import event.GameStateEvent;
import event.PauseEvent;


public interface GameObserver {

	public void gamePaused(PauseEvent pauseEvent);


	public void gameStateChanged(GameStateEvent gameStateEvent);
}
