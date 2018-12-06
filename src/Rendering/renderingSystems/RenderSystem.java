package Rendering.renderingSystems;

import Rendering.renderUtil.RenderMode;
import components.*;
import components.Component;
import core.Window;
import core.coreSystems.EntityListnerSystem;
import core.coreSystems.SystemCommunicator;
import listners.EntityListner;
import listners.SingleEntityListner;
import Rendering.drawers.Draw;
import util.Mathf.Mathf3D.*;
import Rendering.renderUtil.RenderContext;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;

import java.util.Arrays;
import java.util.List;

public class RenderSystem extends EntityListnerSystem {
    public static RenderMode renderMode;

    private MeshRenderSystem meshRenderSystem;
    private WireFrameRenderSystem wireFrameRenderSystem;

    final EntityListner cameraListner;
    final EntityListner sceneLister;

    Scene scene;
    Camera camera;

    private Matrix4x4 normalisePlus1;
    Matrix4x4 moveToView;

    final RenderContext renderContext;

    final List<Plane> fustrumClipPlanes = createFustrumClipPlanes();

    public RenderSystem(List<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
        cameraListner = new SingleEntityListner(Arrays.asList(Camera.class), this);
        sceneLister = new SingleEntityListner(Arrays.asList(Scene.class), this);

        constructMoveToViewMatrix();

        renderContext = new RenderContext(Window.defaultWidth, Window.defaultHeight);
        renderContext.setFustrumClipPlanes(fustrumClipPlanes);
        renderContext.initZBuffer(Window.defaultWidth, Window.defaultHeight);

        initRenderers();

        SystemCommunicator.registerRenderSystem(this);
    }

    private void initRenderers() {
        meshRenderSystem = new MeshRenderSystem(this);
        wireFrameRenderSystem = new WireFrameRenderSystem(this);
    }

    @Override
    public void update() {
        renderContext.resetZBuffer();
        renderContext.clear((byte) 0);//make screen black

        scene = (Scene) getFirstComponentFromFirstEntity(sceneLister);
        camera = (Camera) getFirstComponentFromFirstEntity(cameraListner);

        renderContext.MVP = constructProjMatrix(camera);
        renderContext.projectionToWorld = constructProjToWorld(camera);

        if (renderMode.equals(RenderMode.MESH)) {
            meshRenderSystem.render(renderContext);
        } else if (renderMode.equals(RenderMode.WIREFRAME)) {
            wireFrameRenderSystem.render(renderContext);
        }
    }

    private Matrix4x4 constructProjToWorld(Camera camera) {
        Matrix4x4 projToView = moveToView.compose(Matrix4x4.newProjectionToView(camera.fFov_AR, camera.fFov, camera.zNear, camera.zFar));
        Matrix4x4 viewToWOrld = Matrix4x4.newViewToWorld(camera.lookDir, camera.rightDir, camera.upDir, camera.position);
        return viewToWOrld.compose(projToView);
    }

    protected Matrix4x4 constructProjMatrix(Camera camera) {
        Matrix4x4 projection = Matrix4x4.newProjectionMatrix(camera.fFov_AR, camera.fFov, camera.zNear, camera.zFar).compose(moveToView);
        Matrix4x4 lookAt = Matrix4x4.newLookAt(camera.lookDir, camera.rightDir, camera.upDir, camera.position);
        return (projection).compose(lookAt);
    }

    protected void constructMoveToViewMatrix() {
        normalisePlus1 = Matrix4x4.newTranslation(1, 1, 0);
        moveToView = (normalisePlus1).compose(
                Matrix4x4.newScale(0.5f * Window.defaultWidth, 0.5f * Window.defaultHeight, 1));
    }

    protected boolean needsClipping(AABoundingBox aabb, Camera camera, Matrix4x4 projection) {
        AABoundingBox lookedAtBox = AABoundingBox.zeros();
        lookedAtBox.minExtents = projection.multiply4x4(aabb.minExtents);
        lookedAtBox.maxExtents = projection.multiply4x4(aabb.maxExtents);

        if (lookedAtBox.minExtents.z < camera.zNear) {
            return true;
        }

        if (lookedAtBox.minExtents.x < 0 || lookedAtBox.maxExtents.x > Window.defaultWidth - 1.5) {
            return true;
        }
        if (lookedAtBox.minExtents.y < 0 || lookedAtBox.maxExtents.y > Window.defaultHeight - 1.5) {
            return true;

        }
//        drawBoundingBox(lookedAtBox);

        return false;
    }

    protected Vector3D color = new Vector3D(255, 0f, 0);

    protected void drawBoundingBox(AABoundingBox AABoundingBox) {
        Vector3D to = new Vector3D(AABoundingBox.minExtents.x, AABoundingBox.maxExtents.y, AABoundingBox.minExtents.z);
        Draw.drawLine(AABoundingBox.minExtents, to, color, color, renderContext);
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }


    protected List<Plane> createFustrumClipPlanes() {
        return Arrays.asList(

                new Plane(new Vector3D(1.0f, 0, 0), new Vector3D(200f, 0f, 0f)),
                new Plane(new Vector3D(-1.0f, 0, 0), new Vector3D(Window.defaultWidth - 200.5f, 0f, 0f)),

                new Plane(new Vector3D(0f, 1f, 0), new Vector3D(200f, 0, 0f)),
                new Plane(new Vector3D(0f, -1f, 0), new Vector3D(0f, Window.defaultHeight - 200.5f, 0f)),

                new Plane(new Vector3D(0f, 0, 1), new Vector3D(0f, 0f,/*camera.zNear*/.1f))

        );
    }

}


