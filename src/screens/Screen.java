
package screens;


import sprites.SpriteObserver;
import utils.GraphicsContext;


public interface Screen extends GameObservable, SpriteObserver {

	public void processInput(double delta);


	public void processLogic(double delta);


	void render(GraphicsContext graphicsContext);


	public void nextFrame(double delta);

}
