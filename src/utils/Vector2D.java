
package utils;


public class Vector2D {

	private double	length;
	private double	x;
	private double	y;
	private double	angle;


	public Vector2D() {
		this(0, 0);
	}


	public Vector2D(double theAngle, double theLength) {
		this.angle = theAngle;
		this.length = theLength;
		calculateComponents();
	}


	private void calculateComponents() {
		this.x = Math.cos(angle) * length;
		this.y = Math.sin(angle) * length;
	}


	public void scale(double factor) {
		this.length *= factor;
		calculateComponents();
	}


	public void grow(double increment) {
		this.length += increment;
		if (length < 0)
			length = 0;
		calculateComponents();
	}


	public double getLength() {
		return this.length;
	}


	public void setLength(double length) {
		this.length = length;
		calculateComponents();
	}


	public double getAngle() {
		return this.angle;
	}


	public void setAngle(double angle) {
		this.angle = angle % (2 * Math.PI);
		calculateComponents();
	}


	public double getX() {
		return this.x;
	}


	public double getY() {
		return this.y;
	}


	public String toString() {
		return "Vector: " + Math.toDegrees(this.angle) + "º (" + this.x + ", "
				+ this.y + ")";
	}
}
