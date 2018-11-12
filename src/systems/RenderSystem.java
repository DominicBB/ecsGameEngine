package systems;

import components.*;
import components.Component;
import core.Window;
import listners.EntityListner;
import listners.SingleEntityListner;
import systems.drawSystems.Draw;
import util.RenderContext;
import util.Mesh;
import util.geometry.*;

import java.util.Arrays;
import java.util.List;

public class RenderSystem extends GameSystem {

    protected EntityListner cameraListner;
    protected EntityListner sceneLister;
    private Matrix4x4 normalisePlus1;
    protected Matrix4x4 moveToView;

    protected Scene scene;
    protected Camera camera;

    protected final RenderContext renderContext;

    private final List<Plane> fustrumClipPlanes = createFustrumClipPlanes();

    public RenderSystem(boolean addToUpdateList) {
        super(Arrays.asList(RenderableMesh.class, Transform.class), addToUpdateList);
        cameraListner = new SingleEntityListner(Arrays.asList(Camera.class), this);
        sceneLister = new SingleEntityListner(Arrays.asList(Scene.class), this);
        constructMoveToViewMatrix();
        renderContext = new RenderContext(Window.defaultWidth, Window.defaultHeight);

        Draw.setZBuffer(Window.defaultWidth, Window.defaultHeight);
    }

    private void constructMoveToViewMatrix() {
        normalisePlus1 = Matrix4x4.newTranslation(1, 1, 0);
        moveToView = (normalisePlus1).compose(
                Matrix4x4.newScale(0.5f * Window.defaultWidth, 0.5f * Window.defaultHeight, 1));
    }

    @Override
    public void update(float deltaTime) {
        Draw.resetZBuffer(Window.defaultWidth, Window.defaultHeight);
        renderContext.clear((byte) 0);//make screen black
        scene = (Scene) getFirstComponentFromFirstEntity(sceneLister);
        camera = (Camera) getFirstComponentFromFirstEntity(cameraListner);
//        Matrix4x4 projection = constructProjMatrix(scene, camera);
        Matrix4x4 projection = constructProjMatrix(scene, camera);
        for (int entityID : entityListner.getEntityIDsOfInterest()) {
            //grab components
            Component[] relevantComponents = entityListner.getComponentsOnEntity(entityID,
                    entityListner.getComponentIndexsOfInterest().get(entityID));
            RenderableMesh renderableMesh = (RenderableMesh) relevantComponents[0];
            Transform transform = (Transform) relevantComponents[1];

//            renderMesh(transform, renderableMesh, projection);
            wireframeMesh(transform, renderableMesh, projection);
        }
//        renderContext.drawPixels();

    }

    private Matrix4x4 lookAt;

    private Matrix4x4 constructProjMatrix(Scene scene, Camera camera) {
        Matrix4x4 projection = Matrix4x4.newProjectionMatrix(scene, camera).compose(moveToView);
        lookAt = Matrix4x4.newLookAt(camera.lookDir, camera.rightDir, camera.upDir, camera.position);
        return lookAt.compose(projection);
    }


    private void renderMesh(Transform transform, RenderableMesh renderableMesh, Matrix4x4 projection) {
        Mesh mesh = renderableMesh.mesh;
        Matrix4x4 matrix = /*lookAt.compose*/(TransformUtil.compose(transform));
        boolean needsClipping = needsClipping(transform.minimumBoundingBox, camera, projection);

        for (Triangle t : mesh.triangles) {
            t = matrix.multiply4x4(t, 1);
            if (Pipeline.isHidden(t, scene, camera))
                continue;

            t = Pipeline.shadeTriangle(t, scene, camera);
            t = projection.multiplyProjection(t);
            if (true) {
                List<Triangle> clipTriangles = Clipper.clipTriangle(fustrumClipPlanes,t);
                if (clipTriangles.isEmpty()) continue;
                clipTriangles.forEach(clipTriangle -> Draw.fillPolygon(clipTriangle, renderContext, renderableMesh.texture));
            } else {
                Draw.fillPolygon(t, renderContext, renderableMesh.texture);
            }
        }
    }

    private void wireframeMesh(Transform transform, RenderableMesh renderableMesh, Matrix4x4 projection) {
        Mesh mesh = renderableMesh.mesh;
        Matrix4x4 matrix = (TransformUtil.compose(transform));
        boolean needsClipping = needsClipping(transform.minimumBoundingBox, camera, projection);

        for (Triangle t : mesh.triangles) {
            t = matrix.multiply4x4(t, 1);
            if (Pipeline.isHidden(t, scene, camera))
                continue;
            t = projection.multiplyProjection(t);
            if (true) {
                List<Triangle> clipTriangles = Clipper.clipTriangle(fustrumClipPlanes,t);
                if (clipTriangles.isEmpty()) continue;
                clipTriangles.forEach(clipTriangle -> Draw.wireframePolygon(clipTriangle, renderContext));
            } else {
                Draw.wireframePolygon(t, renderContext);
            }
        }
    }


    private boolean needsClipping(MinimumBoundingBox minimumBoundingBox, Camera camera, Matrix4x4 projection) {
        MinimumBoundingBox prjectedBox = new MinimumBoundingBox();
        prjectedBox.min = projection.multiplyProjection(minimumBoundingBox.min);
        prjectedBox.max = projection.multiplyProjection(minimumBoundingBox.max);

        drawBoundingBox(prjectedBox);
        if (prjectedBox.min.x < 0 || prjectedBox.max.x > Window.defaultWidth - 1) {
            return true;
        }
        if (prjectedBox.min.y < 0 || prjectedBox.max.y > Window.defaultHeight - 1) {
            return true;
        }
        if (prjectedBox.min.z < camera.fNear) {
            return true;
        }

        return false;
    }

    private Vector3D color = new Vector3D(255, 0f, 0);

    private void drawBoundingBox(MinimumBoundingBox minimumBoundingBox) {
        Vector3D to = new Vector3D(minimumBoundingBox.min.x, minimumBoundingBox.max.y, minimumBoundingBox.min.z);
        Draw.drawLine(minimumBoundingBox.min, to, color, color, renderContext);
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }

    private List<Plane> createFustrumClipPlanes(){
        return Arrays.asList(
                new Plane(new Vector3D(1.0f,0,0), new Vector3D(100f,0f,0f)),
                new Plane(new Vector3D(-1.0f,0,0), new Vector3D(Window.defaultWidth - 100.5f,0f,0f)),

                new Plane(new Vector3D(0f,1f,0), new Vector3D(100f,0,0f)),
                new Plane(new Vector3D(0f,-1f,0), new Vector3D(0f,Window.defaultHeight - 100.5f,0f)),

                new Plane(new Vector3D(0f,0,1), new Vector3D(0f,0f,/*camera.fNear*/0.1f))

        );
    }
}


