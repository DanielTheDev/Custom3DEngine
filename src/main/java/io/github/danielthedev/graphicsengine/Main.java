package io.github.danielthedev.graphicsengine;

import javax.swing.SwingUtilities;

import io.github.danielthedev.graphicsengine.graphics.Window;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			Window window = new Window();
			DebugScreen debug = new DebugScreen(window);
			new Thread(()->window.start()).start();
		});
	}

}
