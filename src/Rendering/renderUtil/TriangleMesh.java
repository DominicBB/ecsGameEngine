package Rendering.renderUtil;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Triangle;

import java.util.ArrayList;
import java.util.List;

public class TriangleMesh {
    public List<Triangle> triangles = new ArrayList<>();
    public AABoundingBox aaBoundingBox = AABoundingBox.zeros();

}
