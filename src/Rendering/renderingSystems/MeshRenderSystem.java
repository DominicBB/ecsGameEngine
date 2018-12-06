package Rendering.renderingSystems;

import Rendering.renderUtil.RenderContext;
import components.*;
import core.coreSystems.EntityListnerSystem;
import util.Mathf.Mathf3D.Matrix4x4;

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

    public final void render(RenderContext renderContext) {
//        Matrix4x4 MVP = constructProjMatrix(scene, camera);
        for (int entityID : entityListner.getEntityIDsOfInterest()) {
            //grab components
            Component[] relevantComponents = entityListner.getRelevantComponents(entityID);
            RenderableMesh renderableMesh = (RenderableMesh) relevantComponents[0];
            TransformComponent transformComponent = (TransformComponent) relevantComponents[1];

            renderableMesh.indexedMesh.draw(renderContext, renderableMesh.material, transformComponent.transform);
        }
//        renderContext.drawPixels();

    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

    /*private void renderMesh(TransformComponent transform, RenderableMesh renderableMesh, Matrix4x4 MVP) {
        IndexedMesh indexedMesh = renderableMesh.indexedMesh;
        Matrix4x4 transformMatrix = *//*lookAt.compose*//*(Transform.compose(transform));

        boolean needsClipping = needsClipping(transform.aaBoundingBox, camera, MVP);
        for (Triangle t : indexedMesh.triangles) {
            //apply any transforms to indexedMesh
            t = transformMatrix.multiply4x4(t, 1);

            //hide and shadeWhiteLight
            if (Pipeline.isHidden(t, scene, camera))
                continue;
            t = Pipeline.shadeTriangle(t, scene, camera);

            //look at
            t = lookAt.multiply4x4(t, 1);

            List<Triangle> zClippedTriangles = Clipper.clipTriangle(zPlanes, t);
            if (zClippedTriangles.isEmpty()) continue;
            for (Triangle zClipped : zClippedTriangles) {
                //project
                zClipped = MVP.multiplyProjection(zClipped);
                if (true) {
                    //clip against screen boundries
                    List<Triangle> clipTriangles = Clipper.clipTriangle(fustrumClipPlanes, zClipped);
                    if (clipTriangles.isEmpty()) continue;
                    clipTriangles.forEach(clipTriangle -> Draw.fillPolygon(clipTriangle, renderContext, renderableMesh.texture));
                } else {
                    Draw.fillPolygon(zClipped, renderContext, renderableMesh.texture);
                }
            }
        }
}*/


}
