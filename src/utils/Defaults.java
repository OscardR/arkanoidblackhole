
package utils;


import java.awt.Color;


public class Defaults {

	// General settings
	public static final int		MAX_SPEED			= 12;

	public static final int		WINDOW_WIDTH		= 800;
	public static final int		WINDOW_HEIGHT		= 800;

	public static final int		PAD_WIDTH			= 80;
	public static final int		PAD_HEIGHT			= 15;
	public static final Color	PAD_COLOR			= Color.getHSBColor(
														212f / 360,
														50f / 100,
														85f / 100);
	public static final int		BLOCK_WIDTH			= 60;
	public static final int		BLOCK_HEIGHT		= 20;

	public static final Color	BACKGROUND_COLOR	= Color.BLACK;
	// 60fps is optimal
	public static final long	OPTIMAL_TIME		= 1000000000 / 60;

	public static final int		MAX_BLOCKS_H		= WINDOW_WIDTH
															/ BLOCK_WIDTH;
	public static final int		MAX_BLOCKS_V		= WINDOW_HEIGHT
															/ BLOCK_HEIGHT;

	public static final int		BALL_RADIUS			= 5;
	public static final Color	BALL_COLOR			= Color.YELLOW;
	public static final double	BALL_SPEED			= 4;

	public static final int		BALL_NUMBER			= 1;

	// Game options
	public static final boolean	DEBUG_MODE			= false;
	public static final boolean	FREE_PLAY			= false;

	public static final int		LIVES				= 10;

	public static final int		STAR_NUMBER			= 200;

	public static final int		STAR_RADIUS			= 3;

}
