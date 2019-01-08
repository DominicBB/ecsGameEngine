package util.Mathf.Mathf3D;

import util.Mathf.Mathf2D.Vec2f;

import java.util.Arrays;

public class Quad extends Polygon{

	
	public Quad(Vec4f[] vs, Vec2f[] ts) {
		vectors = Arrays.copyOf(vs, vs.length);
		colors = new Vec4f[3];
		textures = new Vec2f[3];

		if(ts != null){
			textures[0] = ts[0];
			textures[1] = ts[1];
			textures[2] = ts[2];
		}

		colors[0] = new Vec4f(0f, 255f, 0f);
		colors[1] = new Vec4f(0f, 0f, 255f);
		colors[2] = new Vec4f(255f, 0f, 0f);

		normal = normal();
	}
	
	public Quad(Vec4f v1, Vec4f v2, Vec4f v3, Vec4f v4, Polygon p) {
		vectors = new Vec4f[4];
		vectors[0] = v1;
		vectors[1] = v2;
		vectors[2] = v3;
		vectors[3] = v4;
		normal = normal();
		
		/*reflectance = p.reflectance;
		shadedColor = p.shadedColor;*/
	}
	
	
	
	public Triangle[] convertToTriangles() {
		Triangle[] triangles = new Triangle[2];
		triangles[0] = new Triangle(vectors[0], vectors[1], vectors[2] , this);
		triangles[1] = new Triangle(vectors[0], vectors[2], vectors[3], this);
		return triangles;
	}

	
	public void patsBrain() {
		return;
	}
	
	
	@Override
	protected Vec4f normal() {
		return  vectors[1].minus(vectors[0]).crossProduct(vectors[2].minus(vectors[0])).normal();
	}

}
