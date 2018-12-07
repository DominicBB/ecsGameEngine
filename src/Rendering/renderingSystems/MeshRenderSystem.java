package Rendering.renderingSystems;

import Rendering.Clipping.ClippingSystem;
import Rendering.renderUtil.Renderer;
import components.*;
import core.coreSystems.EntityListnerSystem;

import java.util.Arrays;

public class MeshRenderSystem extends EntityListnerSystem {
    private RenderSystem renderSystem;

    public MeshRenderSystem(RenderSystem renderSystem) {
        super(Arrays.asList(RenderableMesh.class, TransformComponent.class));
        this.renderSystem = renderSystem;
    }

    @Override
    public void update() {
        //not used
    }

    public final void render(RenderableMesh renderableMesh, TransformComponent transformComponent, Renderer renderer) {

        switch (ClippingSystem.decideClippingMode(renderableMesh.aaBoundingBox)) {
            case ALLOUTSIDE:
                return;
            case CLIPPING:
                ClippingSystem.needsClipping = true;
            case ALLINSIDE:
                ClippingSystem.needsClipping = false;
        }
        renderableMesh.indexedMesh.draw(renderer, renderableMesh.material, transformComponent.transform);

    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

    /*private void renderMesh(TransformComponent transform, RenderableMesh renderableMesh, Matrix4x4 mvp) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        Matrix4x4 transformMatrix = *//*lookAt.compose*//*(Transform.compose(transform));

        boolean needsClipping = needsClipping(transform.aaBoundingBox, camera, mvp);
        for (Triangle t : indexedMesh.triangles) {
            //apply any transforms to indexedMesh
            t = transformMatrix.multiply4x4(t, 1);

            //hide and shadeWhiteLight
            if (Pipeline.isHidden(t, scene, camera))
                continue;
            t = Pipeline.shadeTriangle(t, scene, camera);

            //look at
            t = lookAt.multiply4x4(t, 1);

            List<Triangle> zClippedTriangles = TriangleClipper.clipTriangle(zPlanes, t);
            if (zClippedTriangles.isEmpty()) continue;
            for (Triangle zClipped : zClippedTriangles) {
                //project
                zClipped = mvp.multiplyProjection(zClipped);
                if (true) {
                    //clip against screen boundries
                    List<Triangle> clipTriangles = TriangleClipper.clipTriangle(fustrumClipPlanes, zClipped);
                    if (clipTriangles.isEmpty()) continue;
                    clipTriangles.forEach(clipTriangle -> Draw.fillPolygon(clipTriangle, renderer, renderableMesh.texture));
                } else {
                    Draw.fillPolygon(zClipped, renderer, renderableMesh.texture);
                }
            }
        }
}*/


}
