package components;

import rendering.materials.Material;
import rendering.renderUtil.meshes.IndexedMesh;
import rendering.materials.RenderMode;
import util.mathf.Mathf3D.Bounds.AABoundingBox;

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
