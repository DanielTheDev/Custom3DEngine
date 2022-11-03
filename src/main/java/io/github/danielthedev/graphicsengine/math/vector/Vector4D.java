package io.github.danielthedev.graphicsengine.math.vector;

import io.github.danielthedev.graphicsengine.math.FastMath;

public class Vector4D implements Vector<Vector4D> {

	public float x;
	public float y;
	public float z;
	public float w;

	public Vector4D(Vector2D vector, float z, float w) {
		this(vector.getX(), vector.getY(), z, w);
	}
	
	public Vector4D(Vector3D vector, float w) {
		this(vector.getX(), vector.getY(), vector.getZ(), w);
	}
	
	public Vector4D(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4D rotateXAxis(float theta) {
		float sin = (float) FastMath.sin(theta);
		float cos = (float) FastMath.cos(theta);
		float dY = this.getY() * cos - this.getZ() * sin;
		float dZ = this.getY() * sin + this.getZ() * cos;
		this.y = dY;
		this.z = dZ;
		return this;
	}
	
	public Vector4D rotateYAxis(float theta) {
		float sin = (float) FastMath.sin(theta);
		float cos = (float) FastMath.cos(theta);
		float dX = this.getX() * cos + this.getZ() * sin;
		float dZ = this.getZ() * cos - this.getX() * sin;
		this.x = dX;
		this.z = dZ;
		return this;
	}
	
	public Vector4D rotateZAxis(float theta) {
		float sin = (float) FastMath.sin(theta);
		float cos = (float) FastMath.cos(theta);
		float dX = this.getX() * cos - this.getY() * sin;
		float dY = this.getX() * sin + this.getY() * cos;
		this.x = dX;
		this.y = dY;
		return this;
	}
	
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4D divide(float factor) {
		this.x /= factor;
		this.y /= factor;
		this.z /= factor;
		this.w /= factor;
		return this;
	}
	
	public Vector4D multiply(float factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		this.w *= factor;
		return this;
	}

	public Vector4D add(Vector4D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		this.w += vector.w;
		return this;
	}
	
	public Vector4D add(Vector3D vector) {
		this.x += vector.getX();
		this.y += vector.getY();
		this.z += vector.getZ();	
		return this;
	}
	
	public Vector4D add(Vector2D vector) {
		this.x += vector.getX();
		this.y += vector.getY();
		return this;
	}

	public Vector4D substract(Vector4D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		this.w -= vector.w;
		return this;
	}
	
	public Vector4D substract(Vector3D vector) {
		this.x -= vector.getX();
		this.y -= vector.getY();
		this.z -= vector.getZ();	
		return this;
	}

	public float getX() {
		return x;
	}

	public Vector4D setX(float x) {
		this.x = x;
		return this;
	}
	
	public Vector4D addX(float x) {
		this.x += x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Vector4D setY(float y) {
		this.y = y;
		return this;
	}
	
	public Vector4D addY(float y) {
		this.y += y;
		return this;
	}

	public float getZ() {
		return z;
	}

	public Vector4D setZ(float z) {
		this.z = z;
		return this;
	}
	
	public Vector4D addZ(float z) {
		this.z += z;
		return this;
	}

	public float getW() {
		return w;
	}

	public Vector4D setW(float w) {
		this.w = w;
		return this;
	}
	
	public Vector4D addW(float w) {
		this.w += w;
		return this;
	}
	
	public Vector4D clone() {
		return new Vector4D(x, y, z, w);
	}

	@Override
	public String toString() {
		return "Vector4D [x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + "]";
	}
}
