package io.github.danielthedev.graphicsengine.graphics;

import io.github.danielthedev.graphicsengine.math.vector.Vector3D;
import io.github.danielthedev.graphicsengine.math.vector.Vector4D;

public class CacheVertex {
	
	public final int object;
	public final Vector3D[] vertexList;
	public final Vector4D color;
	
	public CacheVertex(int object, Vector4D color, Vector3D... vertexList) {
		this.object = object;
		this.vertexList = vertexList;
		this.color = color;
	}

	public int getObject() {
		return object;
	}

	public Vector3D[] getVertexList() {
		return vertexList;
	}

	public Vector4D getColor() {
		return color;
	}
	
	
}
