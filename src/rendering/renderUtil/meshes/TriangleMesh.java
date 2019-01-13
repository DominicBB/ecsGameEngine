package rendering.renderUtil.meshes;

import util.mathf.Mathf3D.Bounds.AABoundingBox;
import util.mathf.Mathf3D.Triangle;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class TriangleMesh {
    public List<Triangle> triangles = new ArrayList<>();
    public AABoundingBox aaBoundingBox = AABoundingBox.zeros();

}
