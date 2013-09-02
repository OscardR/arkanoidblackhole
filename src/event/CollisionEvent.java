
package event;


import java.util.ArrayList;
import java.util.Collection;

import sprites.Sprite;


public class CollisionEvent {

	private ArrayList<Sprite>	sprites;


	public CollisionEvent(Collection<Sprite> theSprites) {
		this.sprites = new ArrayList<Sprite>();
		sprites.addAll(theSprites);
	}


	public ArrayList<Sprite> getSprite() {
		return this.sprites;
	}

}
