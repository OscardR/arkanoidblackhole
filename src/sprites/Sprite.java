
package sprites;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import event.CollisionEvent;
import event.OutOfBoundsEvent;
import utils.Vector2D;


public abstract class Sprite implements SpriteObservable {

	public double							x;
	public double							y;
	public double							z;
	public double							w;
	public double							h;
	public Vector2D							speed;

	protected Collection<SpriteObserver>	observers;


	public Sprite() {
		this.observers = new ArrayList<SpriteObserver>();
		this.x = this.y = this.w = this.h = 0;
		this.speed = new Vector2D();
	}


	// ha de ser implementada por las subclases.
	public abstract void draw(Graphics2D graphics);


	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(x, y, w, h);
	}


	@Override
	public void registerObserver(SpriteObserver anObserver) {
		observers.add(anObserver);
	}


	@Override
	public void notifyOutOfBounds() {
		for (SpriteObserver observer : observers)
			observer.spriteOutOfBounds(new OutOfBoundsEvent(this));
	}


	@Override
	public void notifyCollision(ArrayList<Sprite> sprites) {
		for (SpriteObserver observer : observers)
			observer.spriteCollision(new CollisionEvent(sprites));
	}


	public boolean checkCollisionWithBounds(Rectangle bounds) {
		if (this.x < -this.w || this.x > bounds.width || this.y < -this.h
				|| this.y > bounds.height) {
			notifyOutOfBounds();
			return true;
		} else return false;
	}
}
