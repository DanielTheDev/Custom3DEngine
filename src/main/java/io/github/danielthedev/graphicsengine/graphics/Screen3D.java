package io.github.danielthedev.graphicsengine.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

import io.github.danielthedev.graphicsengine.math.FastMath;
import io.github.danielthedev.graphicsengine.math.vector.Vector3D;

public class Screen3D implements Screen {

	public Vector3D[] unitCube = new Vector3D[] {
			new Vector3D(0.2f, 0.2f, 0.2f),
			new Vector3D(-0.2f, 0.2f, 0.2f),
			new Vector3D(0.2f, -0.2f, 0.2f),
			new Vector3D(0.2f, 0.2f, -0.2f),
			new Vector3D(-0.2f, -0.2f, 0.2f),
			new Vector3D(0.2f, -0.2f, -0.2f),
			new Vector3D(-0.2f, 0.2f, -0.2f),
			new Vector3D(-0.2f, -0.2f, -0.2f)
	};
	
	public void onInit(GraphicsEngine3D graphics) {
		graphics.setBackgroundColor(0.529f, 0.808f, 0.922f, 1);
	}
	
	double pi = 0;
	
	public void draw(GraphicsEngine3D graphics) {

		pi+=0.01f;
		if(pi > FastMath.PI*2) {
			pi = 0;
		}
		
		graphics.setColor(1f, 1f, 1f, 0.5f);
		
		{
			graphics.drawRect(v3d(1f, -10f, 1f).add(graphics.getEyeLocation()), v3d(-1f, -10f, 1f).add(graphics.getEyeLocation()), v3d(-1f, -10f, -1f).add(graphics.getEyeLocation()), v3d(1f, -10f, -1f).add(graphics.getEyeLocation()));
		}
		
		for(int x = 0; x <= 50; x++) {
			for(int z = 0; z <= 50; z++) {
				this.drawCube(graphics, x, ThreadLocalRandom.current().nextFloat(), z);
			}
		}

	}
	
	public void drawCube(GraphicsEngine3D graphics, float x, float y, float z) {
		Vector3D v = v3d(x/2.5f,y/4,z/2.5f);
		
		Vector3D pos0 = v.clone().add(unitCube[0]);
		Vector3D pos1 = v.clone().add(unitCube[1]);
		Vector3D pos2 = v.clone().add(unitCube[2]);
		Vector3D pos3 = v.clone().add(unitCube[3]);
		Vector3D pos4 = v.clone().add(unitCube[4]);
		Vector3D pos5 = v.clone().add(unitCube[5]);
		Vector3D pos6 = v.clone().add(unitCube[6]);
		Vector3D pos7 = v.clone().add(unitCube[7]);
		
		graphics.setColor(0,1,0,1);
		
		graphics.drawRect(pos2, pos4, pos7, pos5);
		graphics.setColor(0.63f, 0.32f, 0.18f, 1);
		graphics.drawRect(pos0, pos3, pos6, pos1);
		graphics.drawRect(pos3, pos6, pos7, pos5);
		graphics.drawRect(pos0, pos1, pos4, pos2);
		graphics.drawRect(pos2, pos5, pos3, pos0);
		graphics.drawRect(pos4, pos7, pos6, pos1);
		
	}
	
	public Vector3D v3d(float x, float y, float z) {
		return new Vector3D(x, y, z);
	}
	
	public class Group {
		
		public CalcThread[] group;
		
		public int i = 0;
		
		public Group(int count) {
			this.group = new CalcThread[count];
			for(int t = 0; t < count; t++) {
				group[t] = new CalcThread();
				group[t].start();
			}
		}
		
		public void add(Vector3D result) {
			i++;
			if(i == group.length) i = 0;
			this.group[i].add(result);
		}
		
		public void calculate() {
			for(int t = 0; t < group.length; t++) {
				group[t].calculate();
			}
			for(int t = 0; t < group.length; t++) {
				while(!group[t].isDone()) {};
			}
		}
		
		public void get(Consumer<Vector3D> callback) {
			for(int t = 0; t < group.length; t++) {
				group[t].get().forEach(callback);
			}
		}
		
		public void reset() {
			for(int t = 0; t < group.length; t++) {
				group[t].reset();
			}
		}
		
	}
	
	public record Result(float x, float y, float z) {};
	
	
	public class CalcThread extends Thread {
		
		public List<Vector3D> result = new ArrayList<Vector3D>();
		public final Object lock = new Object();
		public boolean done = false;
		
		@Override
		public void run() {
			while(true) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					done = false;
					for(Vector3D item : this.result) {
						item.setY(FastMath.cos((float) (FastMath.PI/10 * item.getX() + pi)) + FastMath.cos((float) (FastMath.PI/10 * item.getZ() + pi)) + FastMath.sin(FastMath.PI/30 * (item.getZ()+item.getX()))*3);
					}
					done = true;
				}
			}
		}
		
		public void add(Vector3D result) {
			this.result.add(result);
		}
		
		public List<Vector3D> get() {
			return this.result;
		}
		
		public void reset() {
			this.result.clear();
		}
		
		public void calculate() {
			synchronized (lock) {
				lock.notify();
			}
		}
		
		public boolean isDone() {
			return this.done;
		}
	}

	
}
