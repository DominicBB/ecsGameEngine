package rendering.renderingSystems;


import rendering.renderers.RendererWireFrame;
import components.RenderableMesh;
import components.TransformComponent;

public class WireFrameRenderSystem {

    private final RenderSystem renderSystem;

    public WireFrameRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }


    public void render(RenderableMesh renderableMesh, TransformComponent transformComponent, RendererWireFrame renderer) {

//            wireframeMesh(transformComponent, renderableMesh, mvp);
        renderableMesh.indexedMesh.drawWireframe(renderer, renderableMesh.material, transformComponent.transform);
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
