package Rendering.renderUtil;

import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Vector3D;

public class Vertex {
    public Vector3D vec;
    public Vector3D normal;
    public Vector2D texCoord;
    public Vector2D specCoord;


    public Vertex(Vector3D vec) {
        this(vec, Vector2D.newZeros());
    }

    public Vertex(Vector3D vec, Vector2D texCoord) {
        this(vec, texCoord, Vector3D.newZeros());
    }

    public Vertex(Vector3D vec, Vector2D textCoord, Vector3D normal) {
        this.vec = vec;
        this.normal = normal;
        this.texCoord = textCoord;
    }

    public Vertex mulMatrix(Matrix4x4 vecMatrix, Matrix4x4 normMatrix) {
        return new Vertex(
                vecMatrix.multiply4x4(vec),
                texCoord,
                normMatrix.multiply4x4(normal));
    }

    public final void wDivide() {
        vec.divide(vec.w);
        texCoord.divide(vec.w);
    }
}
