package io.github.danielthedev.graphicsengine.math;

public class FastMath {

	public static final float PI = 3.14159265f;
	
	
	public static float tan(float x) {
		return sin(x)/cos(x);
	}
	
	public static float cos(float x) {
		return sin(x + 0.5f * PI);
	}
	
	public static float sin(float x) {
		if(Math.abs(x) > (PI * 2)) {
			int times = (int)(x / (PI * 2));
			x = x - (PI * 2 * times);
		}
		
		float sin = 0;
		if (x < -PI) {
			x += 6.28318531f;
		} else if (x > PI) {
			x -= 6.28318531f;
		}
		
		if (x < 0) {
			sin = x * (1.27323954f + 0.405284735f * x);
			if (sin < 0)
				sin = sin * (-0.255f * (sin + 1) + 1);
			else
				sin = sin * (0.255f * (sin - 1) + 1);
		} else {
			sin = x * (1.27323954f - 0.405284735f * x);

			if (sin < 0)
				sin = sin * (-0.255f * (sin + 1) + 1);
			else
				sin = sin * (0.255f * (sin - 1) + 1);
		}

		return sin;
	}

}
