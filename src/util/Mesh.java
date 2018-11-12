package util;

import java.util.ArrayList;
import java.util.List;

import util.geometry.MinimumBoundingBox;
import util.geometry.Triangle;

public class Mesh {

	public List<Triangle> triangles = new ArrayList<>();
	public MinimumBoundingBox minimumBoundingBox = new MinimumBoundingBox();
	public Mesh(Mesh mesh) {
		this.triangles = mesh.triangles;
	}
	public Mesh() {
		
	}
}
