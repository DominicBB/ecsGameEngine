package rendering.renderUtil;

import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Vec4f;

public class Vertex {
    public Vec4f vec;
    public Vec4f normal;
    public Vec2f texCoord;
    public Vec2f specCoord;


    public Vertex(Vec4f vec) {
        this(vec, Vec2f.newZeros());
    }

    public Vertex(Vec4f vec, Vec2f texCoord) {
        this(vec, texCoord, Vec4f.newZeros());
    }

    public Vertex(Vec4f vec, Vec2f textCoord, Vec4f normal) {
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
