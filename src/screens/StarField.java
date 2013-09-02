
package screens;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

import app.*;
import sprites.*;
import utils.*;


public class StarField extends AbstractScreen {

	private Collection<Sprite>	stars;
	private int					numStars;


	public StarField(App theApp, KeyboardInput theKeyboard) {
		super(theApp, theKeyboard);
		this.stars = new ArrayList<Sprite>();
		this.numStars = Defaults.STAR_NUMBER;
		initStars();
	}


	private void initStars() {
		Rectangle bounds = app.getBounds();
		for (int i = 0; i < numStars; i++) {
			Star star = new Star(bounds.width / 2, bounds.width / 2, 0);
			star.speed = new Vector2D(Math.random() * Math.PI * 2, 0.1);
			star.registerObserver(this);
			stars.add(star);
		}
	}


	@Override
	public void processLogic(double delta) {
		for (Sprite star : stars) {
			if (star.z < 1)
				star.z *= 1.05;

			star.speed.scale(1.05);
			star.x = star.x + star.speed.getLength()
					* Math.cos(star.speed.getAngle()) * delta;
			star.y = star.y + star.speed.getLength()
					* Math.sin(star.speed.getAngle()) * delta;

			Rectangle bounds = app.getBounds();
			if (star.checkCollisionWithBounds(bounds)) {
				star.x = bounds.width / 2;
				star.y = bounds.height / 2;
				star.z = 0.01;
				star.speed.setLength(star.z * 1.5);
			}
		}
	}


	@Override
	public void render(GraphicsContext graphicsContext) {

		// Obtain graphics:
		Graphics2D g2d = graphicsContext.getGraphics2D();

		for (Sprite star : stars)
			star.draw(g2d);

	}
}
