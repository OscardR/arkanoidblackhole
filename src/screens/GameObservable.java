
package screens;

import app.GameObserver;


public interface GameObservable {

	void registerObserver(GameObserver anObserver);


	void notifyPaused();


	void notifyGameStateChanged();
}
