package components;

import rendering.Materials.Material;
import rendering.renderUtil.Meshes.IndexedMesh;
import rendering.Materials.RenderMode;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;

public class RenderableMesh extends Component {
	public IndexedMesh indexedMesh;
	public Material material;
	public AABoundingBox aaBoundingBox;
	public RenderMode renderMode;

	public RenderableMesh(IndexedMesh indexedMesh, Material material) {
		this.indexedMesh = indexedMesh;
		this.material = material;
		renderMode = RenderMode.MESH;
	}
}
