package util.Mathf.Mathf3D;

import util.Mathf.Mathf2D.Vector2D;

import java.util.Arrays;

public class Quad extends Polygon{

	
	public Quad(Vector3D[] vs, Vector2D[] ts) {
		vectors = Arrays.copyOf(vs, vs.length);
		colors = new Vector3D[3];
		textures = new Vector2D[3];

		if(ts != null){
			textures[0] = ts[0];
			textures[1] = ts[1];
			textures[2] = ts[2];
		}

		colors[0] = new Vector3D(0f, 255f, 0f);
		colors[1] = new Vector3D(0f, 0f, 255f);
		colors[2] = new Vector3D(255f, 0f, 0f);

		normal = normal();
	}
	
	public Quad(Vector3D v1, Vector3D v2, Vector3D v3, Vector3D v4, Polygon p) {
		vectors = new Vector3D[4];
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
	protected Vector3D normal() {
		return  vectors[1].minus(vectors[0]).crossProduct(vectors[2].minus(vectors[0])).normal();
	}

}
