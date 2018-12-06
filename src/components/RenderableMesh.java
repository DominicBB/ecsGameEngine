package components;

import Rendering.Materials.Material;
import Rendering.renderUtil.Bitmap;
import Rendering.renderUtil.IndexedMesh;
import Rendering.renderUtil.Texture;

public class RenderableMesh extends Component {
	public IndexedMesh indexedMesh;
	public Material material;

	public RenderableMesh(IndexedMesh indexedMesh, Material material) {
		this.indexedMesh = indexedMesh;
		this.material = material;
	}

	public RenderableMesh() {
	}


}
