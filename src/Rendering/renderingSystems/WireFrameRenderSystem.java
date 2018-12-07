package Rendering.renderingSystems;


import Rendering.renderUtil.Renderer;
import components.*;
import core.coreSystems.EntityListnerSystem;

import java.util.Arrays;

public class WireFrameRenderSystem extends EntityListnerSystem {

    private final RenderSystem renderSystem;

    public WireFrameRenderSystem(RenderSystem renderSystem) {
        super(Arrays.asList(RenderableMesh.class, TransformComponent.class));
        this.renderSystem = renderSystem;
    }


    public void render(RenderableMesh renderableMesh, TransformComponent transformComponent, Renderer renderer) {

//            wireframeMesh(transformComponent, renderableMesh, mvp);

    }

    @Override
    public void update() {

    }

    /*private void wireframeMesh(TransformComponent transform, RenderableMesh renderableMesh, Matrix4x4 mvp) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        Matrix4x4 matrix = (Transform.compose(transform));
        boolean needsClipping = needsClipping(transform.aaBoundingBox, camera, mvp);

        for (Triangle t : indexedMesh.triangles) {
            t = matrix.multiply4x4(t, 1);
            if (Pipeline.isHidden(t, scene, camera))
                continue;

            t = lookAt.multiply4x4(t, 1);
            List<Triangle> zClippedTriangles = TriangleClipper.clipTriangle(zPlanes, t);
            if (zClippedTriangles.isEmpty()) continue;
            for (Triangle zClipped : zClippedTriangles) {

                zClipped = mvp.multiplyProjection(zClipped);
                if (true) {//TODO bounding box being weird
                    List<Triangle> clipTriangles = clipTriangles = TriangleClipper.clipTriangle(fustrumClipPlanes, zClipped);
                    if (!clipTriangles.isEmpty()) {
                        clipTriangles.forEach(clipTriangle -> Draw.wireframePolygon(clipTriangle, renderer));
                    }
                } else {
                    Draw.wireframePolygon(zClipped, renderer);
                }
            }
        }
    }*/


}
