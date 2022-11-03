package io.github.danielthedev.graphicsengine.math.vector;

public interface Vector<T extends Vector<?>> {
	
	public T clone();
	
	public T divide(float factor);
	
	public T multiply(float factor);
	
	public T add(T vector);
	
	public T substract(T vector);
}
