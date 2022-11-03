package io.github.danielthedev.graphicsengine.graphics;

import io.github.danielthedev.graphicsengine.math.FastMath;
import io.github.danielthedev.graphicsengine.math.matrix.Matrix4D;
import io.github.danielthedev.graphicsengine.math.vector.Vector2D;
import io.github.danielthedev.graphicsengine.math.vector.Vector3D;
import io.github.danielthedev.graphicsengine.math.vector.Vector4D;

import static io.github.danielthedev.graphicsengine.math.matrix.Matrix.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class GraphicsEngine3D {

	public float near;
	public float far;
	public float fov;
	public final Vector3D eyeLocation = new Vector3D(0.5f,-0.5f,0.5f);
	public final Vector2D eyeDirection = new Vector2D(0,0);
	public Matrix4D projectionMatrix;
	public Vector4D color = new Vector4D(1,1,1,1);
	
	public List<CacheVertex> cache = new ArrayList<>();


	public GraphicsEngine3D(float near, float far, float fov) {
		this.near = near;
		this.far = far;
		this.fov = fov;
		this.updateProjectionMatrix();
	}

	public void updateProjectionMatrix() {
		float s = (float) (1 / (FastMath.tan((float) ((this.fov / 2) * (FastMath.PI / 180)))));
		float f = -(this.far / (this.far - this.near));
		float n = f * this.near;
		this.projectionMatrix = new Matrix4D(row(s, 0, 0, 0), row(0, s, 0, 0), row(0, 0, f, -1), row(0, 0, n, 0));
	}

	public void setBackgroundColor(float red, float green, float blue, float alpha) {
		glClearColor(red, green, blue, alpha);
	}

	public void setColor(float red, float green, float blue, float alpha) {
		this.color.set(red, green, blue, alpha);
	}

	public void drawLineStrip(Vector3D... points) {
		//if(calculate(points)) {
		//	this.cache.add(new CacheVertex(GL_LINE_LOOP, this.color.clone(), points));
		//}
	}

	public void drawRect(Vector3D p1, Vector3D p2, Vector3D p3, Vector3D p4) {
		this.cache.add(new CacheVertex(GL_QUADS, this.color.clone(), p1, p2, p3, p4));
	}
	
	public void drawTriangles(Vector3D p1, Vector3D p2, Vector3D p3) {
		//if(calculate(p1, p2, p3)) {
		//	this.cache.add(new CacheVertex(GL_TRIANGLES, this.color.clone(), p1, p2, p3));
		//}
	}

	public void drawAll() {
		this.cache.sort((p1, p2)->{
			float d1 = 0;
			float d2 = 0;
			for(Vector3D v : p1.getVertexList()) {
				d1+=this.eyeLocation.distance(v);
			}
			for(Vector3D v : p2.getVertexList()) {
				d2+=this.eyeLocation.distance(v);
			}
			return Float.compare(d2/p2.getVertexList().length, d1/p1.getVertexList().length);
		});
		
		this.cache.forEach(obj->{
			
			glColor4f(obj.getColor().getX(), obj.getColor().getY(), obj.getColor().getZ(), obj.getColor().getW());
			glBegin(obj.getObject());
			for(Vector3D v : obj.getVertexList()) {
				drawVertex(v.clone());
			}
			glEnd();
		});
		
		this.cache.clear();
	}
	
	public void drawVertex(Vector3D vector) {
		Vector4D fixedLocation = new Vector4D(vector, -vector.getZ()).substract(this.eyeLocation);
		
		float thetaX = (float) ((FastMath.PI/180) * this.eyeDirection.getX());
		float thetaY = (float) ((FastMath.PI/180) * this.eyeDirection.getY());
		fixedLocation.rotateYAxis(thetaY).rotateXAxis(thetaX);
		if(fixedLocation.getZ() <= 0) {
			return;
		}
		
		Vector4D point = projectionMatrix.multiply(fixedLocation);
		if (fixedLocation.getZ() != 1) {
			point.divide(-fixedLocation.getZ());			
		}
		glVertex2f(point.getX(), point.getY());
	}

	public float getNear() {
		return near;
	}

	public void setNear(float near) {
		this.near = near;
		this.updateProjectionMatrix();
	}

	public float getFar() {
		return far;
	}

	public void setFar(float far) {
		this.far = far;
		this.updateProjectionMatrix();
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
		this.updateProjectionMatrix();
	}

	public Vector3D getEyeLocation() {
		return eyeLocation;
	}

	public Matrix4D getProjectionMatrix() {
		return projectionMatrix;
	}

	public void setProjectionMatrix(Matrix4D projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}

	public Vector2D getEyeDirection() {
		return eyeDirection;
	}
	
	
}
