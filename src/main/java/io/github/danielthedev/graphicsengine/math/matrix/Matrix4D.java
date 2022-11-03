package io.github.danielthedev.graphicsengine.math.matrix;

import io.github.danielthedev.graphicsengine.math.vector.Vector4D;

public class Matrix4D implements Matrix {

	public final float[][] matrix;
	
	public Matrix4D(float[]... matrix) {
		Matrix.verifyDimension(matrix, 4);
		this.matrix = matrix;
	}
	
	public Vector4D multiply(Vector4D vector) {
		float dX = vector.getX() * this.matrix[0][0] + vector.getY() * this.matrix[0][1] + vector.getZ() * this.matrix[0][2] + vector.getW() * this.matrix[0][3];
		float dY = vector.getX() * this.matrix[1][0] + vector.getY() * this.matrix[1][1] + vector.getZ() * this.matrix[1][2] + vector.getW() * this.matrix[1][3];
		float dZ = vector.getX() * this.matrix[2][0] + vector.getY() * this.matrix[2][1] + vector.getZ() * this.matrix[2][2] + vector.getW() * this.matrix[2][3];
		float dW = vector.getX() * this.matrix[3][0] + vector.getY() * this.matrix[3][1] + vector.getZ() * this.matrix[3][2] + vector.getW() * this.matrix[3][3];
		return new Vector4D(dX, dY, dZ, dW);
	}

}
