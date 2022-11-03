package io.github.danielthedev.graphicsengine.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.glfw.*;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import io.github.danielthedev.graphicsengine.math.FastMath;
import io.github.danielthedev.graphicsengine.math.vector.Vector2D;

public class Window implements KeyCallback, MouseCallback {

	public long handle;
	
	public final String title = "Custom 3D Engine - By DanielTheDev";
	public final int width = 1280;
	public final int heigth = 1280;
	public final Screen screen = new Screen3D();
	public final Vector2D mouseLocation = new Vector2D(-1, -1);
	public final GraphicsEngine3D graphicsEngine = new GraphicsEngine3D(0.01f, 100.0f, 90.0f);
	public boolean focussed = true;
	public int fps;
	
	public Set<Integer> keyMap = new HashSet<Integer>();
	
	public void start() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		handle = glfwCreateWindow(this.width, this.heigth, this.title, NULL, NULL);
		if (handle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(handle, this::onKeyInput);
		glfwSetCursorPosCallback(handle, this::onMouseInput);

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(handle, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(handle, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		glfwMakeContextCurrent(handle);
		glfwSwapInterval(1);
		glfwShowWindow(handle);
		glfwSetInputMode(handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

		this.loop();
	}

	public void loop() {
		GL.createCapabilities();
		this.screen.onInit(this.graphicsEngine);

		final long fpsDelay = 100;
		long time = System.currentTimeMillis() + fpsDelay;
		short fps = 0;
		
		while (!glfwWindowShouldClose(handle)) {
			glClear(GL_COLOR_BUFFER_BIT);
			this.checkKeys();
			this.screen.draw(graphicsEngine);
			graphicsEngine.drawAll();
			fps++;
			if(time <= System.currentTimeMillis()) {
				this.fps = (int) (fps/((float)(System.currentTimeMillis()-time+fpsDelay)/1000));
				time = System.currentTimeMillis() + fpsDelay;
				fps = 0;
			}
			glfwSwapBuffers(handle);
			glfwPollEvents();
		}
	}
	
	public void checkKeys() {
		this.keyMap.forEach(key->{
			switch (key) {
				case GLFW_KEY_ESCAPE: {
					System.exit(0);
					break;
				}
				case GLFW_KEY_W: {
					this.move('W');
					break;
				}
				case GLFW_KEY_E: {
					this.focussed = !this.focussed;	
					glfwSetInputMode(handle, GLFW_CURSOR, this.focussed ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
					break;
				}
				case GLFW_KEY_S: {
					this.move('S');
					break;
				}
				case GLFW_KEY_A: {
					this.move('A');
					break;
				}
				case GLFW_KEY_D: {
					this.move('D');
					break;
				}
				case GLFW_KEY_LEFT_SHIFT: {
					this.graphicsEngine.getEyeLocation().addY(+0.1f);
					break;
				}
				case GLFW_KEY_SPACE: {
					this.graphicsEngine.getEyeLocation().addY(-0.1f);
					break;
				}
			}
		});
	}
	
	public void move(char c) {
		float addZ = 0;
		float addX = 0;
		float moveSpeed = 5f;
		
		if(c == 'W') {
			addX+=90;
		} else if(c == 'S') {
			addZ+=180;
			addX-=90;
		} else if(c == 'A') {
			addZ-=90;
		} else if(c == 'D') {
			addZ+=90;
			addX+=180;
		}
		
		float yaw = this.graphicsEngine.getEyeDirection().getY();
		float dZ = (float) FastMath.cos((float) Math.toRadians(yaw + addZ))/(100/moveSpeed);
		float dX = (float) FastMath.cos((float) Math.toRadians(yaw + addX))/(100/moveSpeed);
		this.graphicsEngine.getEyeLocation().addX(dX);
		this.graphicsEngine.getEyeLocation().addZ(dZ);
	}

	@Override
	public void onKeyInput(long window, int key, int scancode, int action, int mods) {
		if(action == GLFW_PRESS) {
			keyMap.add(key);
		} else if(action == GLFW_RELEASE) {
			keyMap.remove(key);
		}
	}

	@Override
	public void onMouseInput(long window, double xpos, double ypos) {
		if(focussed) {
			float dX = (float) (xpos - this.mouseLocation.getX()) * 1/5;
			float dY = (float) (ypos - this.mouseLocation.getY()) * 1/5;
			
			this.mouseLocation.set((float)xpos, (float)ypos);
			
			this.graphicsEngine.getEyeDirection().add(new Vector2D(dY, dX));
		}
	}

	public boolean isFocussed() {
		return focussed;
	}

	public Screen getScreen() {
		return screen;
	}

	public GraphicsEngine3D getGraphicsEngine() {
		return graphicsEngine;
	}

	public int getFps() {
		return fps;
	}

}
