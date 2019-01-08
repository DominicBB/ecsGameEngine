package util.Mathf.Mathf3D;
import util.Mathf.Mathf2D.Vec2f;

public abstract class Polygon {

	public Vec4f[] vectors;
	public Vec2f[] textures;
	public Vec4f[] colors;

	/*public Color reflectance;*/
	protected Vec4f shade = new Vec4f(1f,1f,1f);
	public Vec4f normal;
	
	protected abstract Vec4f normal();


	public void setShade(Vec4f shade) {
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

	public Vec4f getShade(){
		return shade;
	}
}