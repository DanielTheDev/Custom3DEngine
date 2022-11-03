package io.github.danielthedev.graphicsengine.math.vector;

public class Vector2D implements Vector<Vector2D> {

	public float x;
	public float y;

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D divide(float factor) {
		this.x /= factor;
		this.y /= factor;
		return this;
	}
	
	public Vector2D multiply(float factor) {
		this.x *= factor;
		this.y *= factor;
		return this;
	}

	public Vector2D add(Vector2D vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector2D substract(Vector2D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public float getX() {
		return x;
	}

	public Vector2D setX(float x) {
		this.x = x;
		return this;
	}
	
	public Vector2D addX(float x) {
		this.x += x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Vector2D setY(float y) {
		this.y = y;
		return this;
	}
	
	public Vector2D addY(float y) {
		this.y += y;
		return this;
	}

	public Vector2D clone() {
		return new Vector2D(x, y);
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}
	
	
}