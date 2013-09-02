
package sprites;


import java.awt.Color;
import java.awt.Graphics2D;

import utils.Defaults;


public class Star extends Sprite {

	public Star(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = Defaults.STAR_RADIUS;
		this.h = Defaults.STAR_RADIUS;
	}


	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(new Color(1f, 1f, 1f, (float) Math.random()));
		graphics.fillOval(
			(int) Math.rint(x - w / 2),
			(int) Math.rint(y - h / 2),
			(int) Math.rint(z * w),
			(int) Math.rint(z * h));
	}


	@Override
	public String toString() {
		return "Star: (" + x + ", " + y + ", " + z + ") | " + this.speed;
	}
}
