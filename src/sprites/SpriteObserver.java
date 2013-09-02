
package sprites;

import event.CollisionEvent;
import event.OutOfBoundsEvent;


public interface SpriteObserver {

	public void spriteOutOfBounds(OutOfBoundsEvent outOfBoundsEvent);


	public void spriteCollision(CollisionEvent collisionEvent);
}
