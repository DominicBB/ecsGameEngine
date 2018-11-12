package components;

import util.Bitmap;
import util.Mesh;

public class RenderableMesh extends Component {
	public Mesh mesh;
	public Bitmap texture;

	public RenderableMesh(Mesh mesh, Bitmap texture) {
		this.mesh = mesh;
		this.texture = texture;
	}

	public RenderableMesh() {
	}


}
