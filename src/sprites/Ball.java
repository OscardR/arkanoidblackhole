
package sprites;


import java.awt.Graphics2D;
import java.awt.Rectangle;

import utils.Defaults;
import utils.Vector2D;


public class Ball extends Sprite {

	public Ball() {
		this(0, 0, Defaults.BALL_RADIUS);
	}


	public Ball(double x, double y) {
		this(x, y, Defaults.BALL_RADIUS);
	}


	public Ball(double x, double y, double radius) {
		this.x = x;
		this.y = y;
		this.w = radius * 2;
		this.h = radius * 2;
		this.speed = new Vector2D();
	}


	@Override
	public boolean checkCollisionWithBounds(Rectangle bounds) {
		boolean collided = false;
		double ballAngle = this.speed.getAngle();

		// Check ball collision with left
		if (this.x < 0 && this.speed.getX() < 0) {
			this.speed.setAngle(Math.PI - ballAngle);
			collided = true;
		}

		// Check ball collision with right
		if (this.x + this.w > Defaults.WINDOW_WIDTH - 1
				&& this.speed.getX() > 0) {
			this.speed.setAngle(Math.PI - ballAngle);
			collided = true;
		}

		// Check ball collision with botton
		if (this.y + this.h > Defaults.WINDOW_HEIGHT - 1
				&& this.speed.getY() > 0)
			if (Defaults.FREE_PLAY) {
				this.speed.setAngle(2 * Math.PI - ballAngle);
				collided = true;
			} else {
				notifyOutOfBounds();
			}

		// Check ball collision with top
		if (this.y < 0 && this.speed.getY() < 0) {
			this.speed.setAngle(2 * Math.PI - ballAngle);
			collided = true;
		}
		return collided;
	}


	public boolean checkCollisionWithPad(Sprite pad) {
		if (this.getBounds().intersects(pad.getBounds())) {
			double offset = ((this.x - pad.x) + this.w / 2.0) / pad.w;
			if (this.speed.getY() > 0) {
				if (this.y < pad.y)
					this.speed.setAngle(Math.PI * 1.2 + Math.PI * .8 * offset);
				else this.speed.setAngle(-Math.PI * 1.2 - Math.PI * .8 * offset);
			} else {
				if (this.y > pad.y + pad.h)
					this.speed.setAngle(Math.PI * 1.2 - Math.PI * .8 * offset);
				else this.speed.setAngle(-Math.PI * 1.2 + Math.PI * .8 * offset);
			}
			return true;
		}
		return false;
	}


	public boolean checkCollisionWithBlock(Block block) {
		if (this.getBounds().intersects(block.getBounds())) {

			block.hitCount++;

			double ballAngle = this.speed.getAngle();

			// Check ball collision with left side of block
			if (this.speed.getX() > 0) {
				if (this.x < block.x)
					this.speed.setAngle(Math.PI - ballAngle);
			} else {

				// Check ball collision with right side of
				// block
				if (this.x + this.w > block.x + block.w)
					this.speed.setAngle(Math.PI - ballAngle);
			}

			// Check ball collision with botton side of
			// block
			if (this.speed.getY() > 0) {
				if (this.y < block.y)
					this.speed.setAngle(2 * Math.PI - ballAngle);
			} else {

				// Check ball collision with top side of
				// block
				if (this.y + this.h > block.y + block.h)
					this.speed.setAngle(2 * Math.PI - ballAngle);
			}

			return true;
		}
		return false;
	}


	@Override
	public void draw(Graphics2D graphics) {
		graphics.setPaint(Defaults.BALL_COLOR);
		graphics.fillOval((int) x, (int) y, (int) w, (int) h);
	}
}
