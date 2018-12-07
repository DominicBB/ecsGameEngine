package components;

import Rendering.Materials.Material;
import Rendering.renderUtil.Bitmap;
import Rendering.renderUtil.IndexedMesh;
import Rendering.renderUtil.RenderMode;
import Rendering.renderUtil.Texture;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;

public class RenderableMesh extends Component {
	public IndexedMesh indexedMesh;
	public Material material;
	public AABoundingBox aaBoundingBox;
	public RenderMode renderMode;


	public RenderableMesh(IndexedMesh indexedMesh, Material material) {
		this.indexedMesh = indexedMesh;
		this.material = material;
	}

	public RenderableMesh() {
	}


}
