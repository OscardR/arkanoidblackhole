
package sprites;


import java.awt.Graphics2D;
import java.awt.Rectangle;

import utils.Defaults;


public class Pad extends Sprite {

	public Pad(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.w = Defaults.PAD_WIDTH;
		this.h = Defaults.PAD_HEIGHT;
	}


	public void checkCollisionsWithBounds(Rectangle bounds) {
		// Check pad collision with left
		if (this.x < 0)
			this.x = 0;
		// Check pad collision with right
		if (this.x + this.w > Defaults.WINDOW_WIDTH - 1)
			this.x = Defaults.WINDOW_WIDTH - this.w - 1;
		// Check pad collision with botton
		if (this.y + this.h > Defaults.WINDOW_HEIGHT - 1)
			this.y = Defaults.WINDOW_HEIGHT - this.h - 1;
		// Check pad collision with top
		if (this.y < 0)
			this.y = 0;
	}


	public void draw(Graphics2D graphics) {
		graphics.setColor(Defaults.PAD_COLOR);
		graphics.fillRoundRect(
			(int) x,
			(int) y,
			(int) w,
			(int) h,
			(int) (w / 5),
			(int) (w / 5));
	}
}
