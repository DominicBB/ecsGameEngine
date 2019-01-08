package util.Mathf.Mathf3D;

public class Line3D {
	public Vec4f v1;
	public Vec4f v2;
	
	public float deltaX;
	public float deltaY;
	
//	public int deltaXInt;
	public int deltaYInt;
	

//	public Vec4f vPerYStep;
	
	
	public Line3D(Vec4f v1, Vec4f v2) {
		this.v1 = v1;
		this.v2 = v2;
		
		
		deltaX = (v2.x -v1.x);
		deltaY= (v2.y -v1.y);
		
//		deltaXInt = (int)v2.x -(int) v1.x; //(int)deltaX;
		deltaYInt = (int)v2.y -(int) v1.y; //(int)deltaY;
		
//		vPerYStep = normal.mul(mag/deltaYInt); //used in paramCalculate
	}
	
	public Vec4f unit() {
		return v2.minus(v1).normal();
	}
	
	public float magnitude() {
		return (float) v2.minus(v1).magnitude();
	}
	
	public float getDeltaXabs() {
		return Math.abs(deltaX);
	}
	
	public float getDeltaYabs() {
		return Math.abs(deltaY);
	}
	
	public float getSlope() {
		return deltaY / deltaX;
	}
	
	
}
