package io.github.danielthedev.graphicsengine.math.vector;

import io.github.danielthedev.graphicsengine.math.FastMath;

public class Vector3D implements Vector<Vector3D> {

	public float x;
	public float y;
	public float z;

	public Vector3D(Vector2D vector, float z) {
		this(vector.getX(), vector.getY(), z);
	}
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3D divide(float factor) {
		this.x /= factor;
		this.y /= factor;
		this.z /= factor;
		return this;
	}
	
	public Vector3D multiply(float factor) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}
	
	public Vector3D multiplyAll(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	public Vector3D add(Vector3D vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}

	public Vector3D add(Vector2D vector) {
		this.x += vector.getX();
		this.y += vector.getY();
		return this;
	}
	
	public Vector3D substract(Vector3D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}
	
	public float distance(Vector3D vector) {
		return (float) Math.sqrt(Math.pow(this.x - vector.x, 2) + Math.pow(this.y - vector.y, 2) + Math.pow(this.z - vector.z, 2));
	}
	

	public Vector3D rotateXAxis(float theta) {
		float sin = (float) FastMath.sin(theta);
		float cos = (float) FastMath.cos(theta);
		float dY = this.getY() * cos - this.getZ() * sin;
		float dZ = this.getY() * sin + this.getZ() * cos;
		this.y = dY;
		this.z = dZ;
		return this;
	}
	
	public Vector3D rotateYAxis(float theta) {
		float sin = (float) FastMath.sin(theta);
		float cos = (float) FastMath.cos(theta);
		float dX = this.getX() * cos + this.getZ() * sin;
		float dZ = this.getZ() * cos - this.getX() * sin;
		this.x = dX;
		this.z = dZ;
		return this;
	}
	
	public Vector3D rotateZAxis(float theta) {
		float sin = (float) FastMath.sin(theta);
		float cos = (float) FastMath.cos(theta);
		float dX = this.getX() * cos - this.getY() * sin;
		float dY = this.getX() * sin + this.getY() * cos;
		this.x = dX;
		this.y = dY;
		return this;
	}
	
	public Vector3D rotateXYZAxis(float thetaA, float thetaB, float thetaC) {	
		float sinA = (float) FastMath.sin(thetaA);
		float cosA = (float) FastMath.cos(thetaA);
		float sinB = (float) FastMath.sin(thetaB);
		float cosB = (float) FastMath.cos(thetaB);
		float sinC = (float) FastMath.sin(thetaC);
		float cosC = (float) FastMath.cos(thetaC);
		float dX = (cosA * cosB) * x + (cosA * sinB * sinC - sinA * cosC) * y + (cosA * sinB * cosC + sinA * sinC) * z;
		float dY = (sinA * cosB) * x + (sinA * sinB * sinC + cosA * cosC) * y + (sinA * sinB * cosC - cosA * sinC) * z;
		float dZ = (-sinB) * x + (cosB * sinC) * y + (cosB * cosC) * z; 
		return this.set(dX, dY, dZ);
	}
	
	public float getX() {
		return x;
	}

	public Vector3D setX(float x) {
		this.x = x;
		return this;
	}
	
	public Vector3D addX(float x) {
		this.x += x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Vector3D setY(float y) {
		this.y = y;
		return this;
	}
	
	public Vector3D addY(float y) {
		this.y += y;
		return this;
	}

	public float getZ() {
		return z;
	}

	public Vector3D setZ(float z) {
		this.z = z;
		return this;
	}
	
	public Vector3D addZ(float z) {
		this.z += z;
		return this;
	}
	
	public Vector3D clone() {
		return new Vector3D(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		return "Vector3D [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}