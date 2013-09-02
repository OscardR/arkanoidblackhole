
package utils;


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class GraphicsContext {

	private static GraphicsContext	instance;

	private static Canvas			canvas;

	BufferStrategy					bufferStrategy;
	GraphicsEnvironment				graphicsEnvironment;
	GraphicsDevice					graphicsDevice;
	GraphicsConfiguration			graphicsConfiguration;
	BufferedImage					bufferedImage;

	Graphics						graphics;
	Graphics2D						graphics2D;


	private GraphicsContext() {

		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
		bufferedImage = graphicsConfiguration.createCompatibleImage(
			Defaults.WINDOW_WIDTH,
			Defaults.WINDOW_HEIGHT);

		graphics = null;
		graphics2D = null;
	}


	public static GraphicsContext getInstance() {
		if (instance == null) {
			instance = new GraphicsContext();
		}
		return instance;
	}


	public static void setCanvas(Canvas theCanvas) {
		canvas = theCanvas;
		instance = null;
	}


	public Graphics2D getGraphics2D() {
		return this.graphics2D;
	}


	public Graphics getGraphics() {
		return graphics;
	}


	public void drawAndFlip() {
		graphics = bufferStrategy.getDrawGraphics();
		graphics.drawImage(bufferedImage, 0, 0, null);
		if (!bufferStrategy.contentsLost())
			bufferStrategy.show();
	}


	public void clearBuffer() {
		graphics2D = bufferedImage.createGraphics();
		graphics2D.setColor(Defaults.BACKGROUND_COLOR);
		graphics2D.fillRect(0, 0, Defaults.WINDOW_WIDTH, Defaults.WINDOW_HEIGHT);

		graphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
