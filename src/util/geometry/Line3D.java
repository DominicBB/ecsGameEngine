package util.geometry;

public class Line3D {
	public Vector3D v1;
	public Vector3D v2;
	
	public float deltaX;
	public float deltaY;
	
//	public int deltaXInt;
	public int deltaYInt;
	

//	public Vector3D vPerYStep;
	
	
	public Line3D(Vector3D v1, Vector3D v2) {
		this.v1 = v1;
		this.v2 = v2;
		
		
		deltaX = (v2.x -v1.x);
		deltaY= (v2.y -v1.y);
		
//		deltaXInt = (int)v2.x -(int) v1.x; //(int)deltaX;
		deltaYInt = (int)v2.y -(int) v1.y; //(int)deltaY;
		
//		vPerYStep = unit.scale(mag/deltaYInt); //used in paramCalculate
	}
	
	public Vector3D unit() {
		return v2.minus(v1).unit();
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
