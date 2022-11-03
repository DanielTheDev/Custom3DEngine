package io.github.danielthedev.graphicsengine.math.matrix;

public interface Matrix {

	public static void verifyDimension(float[][] arr, int size) {
		if(arr.length != size) {
			throw new NullPointerException("Incorrect matrix size");
		} else {
			for(float[] subArr : arr) {
				if(subArr.length != size) {
					throw new NullPointerException("Incorrect matrix size");
				}
			}
		}
	}
	
	public static float[] row(float... row) {
		return row;
	}
	
	
}
