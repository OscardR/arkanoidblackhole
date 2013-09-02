
package sprites;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import utils.Defaults;


public class Block extends Sprite {

	private static final Color	BASIC_COLOR			= new Color(0xC00020);
	private static final Color	DOUBLE_COLOR		= new Color(0x0020C0);
	private static final Color	TRIPLE_COLOR		= new Color(0x20A000);
	private static final Color	METAL_COLOR			= new Color(.5f, .5f, .4f);
	private static final Color	FIXED_COLOR			= new Color(.3f, .3f, .3f);
	private static final float	HIGHLIGHT_STROKE	= 1.5f;
	public Type					type;
	public int					hitCount;


	public enum Type {
		BASIC(1, BASIC_COLOR),
		DOUBLE(2, DOUBLE_COLOR),
		TRIPLE(3, TRIPLE_COLOR),
		METAL(10, METAL_COLOR),
		FIXED(Double.POSITIVE_INFINITY, FIXED_COLOR);

		public double	hitCount;
		public Color	color;


		private Type(double hits, Color c) {
			this.hitCount = hits;
			this.color = c;
		}
	}


	public Block(double x, double y) {
		this.x = x;
		this.y = y;
		this.w = Defaults.BLOCK_WIDTH;
		this.h = Defaults.BLOCK_HEIGHT;
		this.type = Type.values()[(int) (Math.random() * Type.values().length)];
		this.hitCount = 0;
	}


	@Override
	public void draw(Graphics2D graphics) {
		graphics.setPaint(type.color);
		graphics.fillRect((int) x, (int) y, (int) w, (int) h);
		graphics.setStroke(new BasicStroke(HIGHLIGHT_STROKE));
		graphics.setColor(new Color(1, 1, 1, .15f));
		graphics.drawLine(
			(int) x,
			(int) y,
			(int) (x + w - HIGHLIGHT_STROKE),
			(int) y);
		graphics.drawLine(
			(int) x,
			(int) y,
			(int) x,
			(int) (y + h - HIGHLIGHT_STROKE));
		graphics.setColor(new Color(0, 0, 0, .15f));
		graphics.drawLine((int) x, (int) (y + h - HIGHLIGHT_STROKE), (int) (x
				+ w - HIGHLIGHT_STROKE), (int) (y + h - HIGHLIGHT_STROKE));
		graphics.drawLine(
			(int) (x + w - HIGHLIGHT_STROKE),
			(int) (y + h - HIGHLIGHT_STROKE),
			(int) (x + w - HIGHLIGHT_STROKE),
			(int) y);

		if (Defaults.DEBUG_MODE)
			graphics.drawString(hitCount + " / " + type.hitCount, (float) (x
					+ w / 2 - 10), (float) (y + h / 2 + 4));
	}
}
