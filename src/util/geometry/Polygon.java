package util.geometry;

import java.awt.Color;

public abstract class Polygon {

	public Vector3D[] vectors;
	public Vector2D[] textures;
	public Vector3D[] colors;

	/*public Color reflectance;*/
	protected Vector3D shade = new Vector3D(1f,1f,1f);
	public Vector3D normal;
	
	protected abstract Vector3D normal();


	public void setShade(Vector3D shade) {
		this.shade = shade;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for(int i = 0; i< vectors.length; i++) {
			if(i == 2) {
				builder.append(vectors[i]);
			}else {
			builder.append(vectors[i]+", ");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	public Vector3D getShade(){
		return shade;
	}
}