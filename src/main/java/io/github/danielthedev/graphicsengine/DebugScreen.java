package io.github.danielthedev.graphicsengine;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import io.github.danielthedev.graphicsengine.graphics.Window;

public class DebugScreen extends JFrame implements Runnable {

	public final DebugItem focussed = new DebugItem("focussed", 5, 0);
	public final DebugItem fps = new DebugItem("fps", 0, 0);
	public final DebugItem fov = new DebugItem("fov", 5, 0);
	public final DebugItem far = new DebugItem("far");
	public final DebugItem near = new DebugItem("near");
	
	public final DebugItem eyeX = new DebugItem("x", 5, 0);
	public final DebugItem eyeY = new DebugItem("y");
	public final DebugItem eyeZ = new DebugItem("z");
	public final DebugItem pitch = new DebugItem("pitch", 5, 0);
	public final DebugItem yaw = new DebugItem("yaw");
	
	
	public int width = 300;
	public int height = 400;
	
	public final Thread thread = new Thread(this);
	public final Window window;
	
	public DebugScreen(Window window) {
		super("Debug");
		this.window = window;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(width, height);
		this.addComponents();
		this.setVisible(true);
		this.thread.start();
	}
	
	public void addComponents() {
		Container pane = this.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		this.addAll(pane, focussed, fps, fov, far, near, eyeX, eyeY, eyeZ, pitch, yaw);
	}
	
	public void addAll(Container pane, Component...components) {
		for(Component component : components) {
			pane.add(component);
		}
	}
	
	public static class DebugItem extends JLabel {
		
		public static final Font FONT = new Font("Consolas", Font.PLAIN, 20);
		
		public final String name;
		
		
		public DebugItem(String name) {
			this(name, 0, 0);
		}

		public DebugItem(String name, int top, int bottom) {
			this.name = name;
			this.set(null);
			this.setBorder(new EmptyBorder(top, 5, bottom, 0));
			this.setFont(FONT);
		}

		
		public void set(Object t) {
			this.setText(name + ": " + Objects.toString(t));
		}
		
	}

	@Override
	public void run() {
		while(!this.thread.isInterrupted()) {
			this.focussed.set(this.window.isFocussed());
			this.fps.set(this.window.getFps());
			this.near.set(this.window.getGraphicsEngine().getNear());
			this.far.set(this.window.getGraphicsEngine().getFar());
			this.fov.set(this.window.getGraphicsEngine().getFov());
			
			
			this.eyeX.set(this.window.getGraphicsEngine().getEyeLocation().getX());
			this.eyeY.set(this.window.getGraphicsEngine().getEyeLocation().getY());
			this.eyeZ.set(this.window.getGraphicsEngine().getEyeLocation().getZ());
			
			this.pitch.set(this.window.getGraphicsEngine().getEyeDirection().getX());
			this.yaw.set(this.window.getGraphicsEngine().getEyeDirection().getY());
			try {Thread.sleep(1000/60);} catch (InterruptedException e) {}
		}
		
	}
}


