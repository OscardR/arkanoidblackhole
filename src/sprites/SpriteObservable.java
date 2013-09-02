
package sprites;


import java.util.ArrayList;



public interface SpriteObservable {

	public void notifyOutOfBounds();


	public void notifyCollision(ArrayList<Sprite> sprites);


	void registerObserver(SpriteObserver anObserver);
}
