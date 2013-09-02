
package event;


import sprites.Sprite;


public class OutOfBoundsEvent {

	private Sprite	sprite;


	public OutOfBoundsEvent(Sprite theSprite) {
		this.sprite = theSprite;
	}


	public Sprite getSprite() {
		return this.sprite;
	}
}
