package Rendering.renderingSystems;


import Rendering.renderUtil.RenderContext;
import components.*;
import core.coreSystems.EntityListnerSystem;
import util.Mathf.Mathf3D.Matrix4x4;

import java.util.Arrays;

public class WireFrameRenderSystem extends EntityListnerSystem {

    private final RenderSystem renderSystem;

    public WireFrameRenderSystem(RenderSystem renderSystem) {
        super(Arrays.asList(RenderableMesh.class, TransformComponent.class));
        this.renderSystem = renderSystem;
    }


    public void render(RenderContext renderContext) {

        for (int entityID : entityListner.getEntityIDsOfInterest()) {
            //grab components
            Component[] relevantComponents = entityListner.getRelevantComponents(entityID);
            RenderableMesh renderableMesh = (RenderableMesh) relevantComponents[0];
            TransformComponent transformComponent = (TransformComponent) relevantComponents[1];

//            wireframeMesh(transformComponent, renderableMesh, MVP);
        }
    }

    @Override
    public void update() {

    }

    /*private void wireframeMesh(TransformComponent transform, RenderableMesh renderableMesh, Matrix4x4 MVP) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        Matrix4x4 matrix = (Transform.compose(transform));
        boolean needsClipping = needsClipping(transform.aaBoundingBox, camera, MVP);

        for (Triangle t : indexedMesh.triangles) {
            t = matrix.multiply4x4(t, 1);
            if (Pipeline.isHidden(t, scene, camera))
                continue;

            t = lookAt.multiply4x4(t, 1);
            List<Triangle> zClippedTriangles = Clipper.clipTriangle(zPlanes, t);
            if (zClippedTriangles.isEmpty()) continue;
            for (Triangle zClipped : zClippedTriangles) {

                zClipped = MVP.multiplyProjection(zClipped);
                if (true) {//TODO bounding box being weird
                    List<Triangle> clipTriangles = clipTriangles = Clipper.clipTriangle(fustrumClipPlanes, zClipped);
                    if (!clipTriangles.isEmpty()) {
                        clipTriangles.forEach(clipTriangle -> Draw.wireframePolygon(clipTriangle, renderContext));
                    }
                } else {
                    Draw.wireframePolygon(zClipped, renderContext);
                }
            }
        }
    }*/


}
