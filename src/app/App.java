
package app;


import javax.swing.JFrame;


public abstract class App extends JFrame implements GameObserver {

	private static final long	serialVersionUID	= -5065506283617067229L;


	public abstract void run();
}
