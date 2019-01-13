package rendering.renderingSystems;

import rendering.clipping.ClippingSystem;
import rendering.renderUtil.RenderState;
import rendering.renderers.RendererWireFrame;
import components.Camera;
import components.Component;
import components.RenderableMesh;
import components.TransformComponent;
import core.EntityFactory;
import core.display.Window;
import core.coreSystems.EntityGrabberSystem;
import core.coreSystems.SystemCommunicator;
import util.FloatBuffer;
import util.mathf.Mathf3D.Bounds.AABoundingBox;
import util.mathf.Mathf3D.Matrix4x4;
import util.mathf.Mathf3D.Transform;

import java.util.Arrays;

public class RenderSystem extends EntityGrabberSystem {

    private MeshRenderSystem meshRenderSystem;
    private WireFrameRenderSystem wireFrameRenderSystem;

    final RendererWireFrame rendererWireFrame;

    public RenderSystem() {
        super(Arrays.asList(RenderableMesh.class, TransformComponent.class));
        initRenderState();
        rendererWireFrame = new RendererWireFrame();
        initRenderers();
        SystemCommunicator.registerRenderSystem(this);
    }

    private void initRenderState(){
        RenderState.camera = EntityFactory.createCamera(new Camera(), new Transform());
        RenderState.createLightingState();
        RenderState.zBuffer = new FloatBuffer(Window.defaultWidth, Window.defaultHeight);
    }

    private void initRenderers() {
        meshRenderSystem = new MeshRenderSystem(this);
        wireFrameRenderSystem = new WireFrameRenderSystem(this);
    }


    private long timeStamp;

    @Override
    public void update() {
//        timeStamp = System.currentTimeMillis();
        RenderState.zBuffer.resetPositiveInf();
        RenderState.colorBuffer.clearToBlack();

        Matrix4x4 projMatrix = constructProjMatrix(RenderState.camera);
//        RenderState.projectionToWorld = constructProjToWorld(RenderState.camera);
        for (int entityID : entityGrabber.getEntityIDsOfInterest()) {
            //grab components
            Component[] relevantComponents = entityGrabber.getRelevantComponents(entityID);
            RenderableMesh renderableMesh = (RenderableMesh) relevantComponents[0];
            TransformComponent transformComponent = (TransformComponent) relevantComponents[1];

            setRenderState(renderableMesh, transformComponent, projMatrix);
            decideClipping(renderableMesh.aaBoundingBox);
            renderableMesh.indexedMesh.vertexShadeAllVertices();
            sendToRenderSystem(renderableMesh, transformComponent);
        }
//        System.out.println("Done raster: " + (System.currentTimeMillis() - timeStamp));

    }

    private void setRenderState(RenderableMesh renderableMesh, TransformComponent transformComponent, Matrix4x4 projMatrix) {
        Matrix4x4 worldTransform = transformComponent.transform.compose();
        RenderState.mvp = worldTransform.compose(projMatrix);

        RenderState.world = worldTransform;
        RenderState.transform = transformComponent.transform;
        RenderState.material = renderableMesh.material;
    }

    private void decideClipping(AABoundingBox aaBoundingBox) {
        switch (ClippingSystem.decideClippingMode(aaBoundingBox)) {
            case ALLOUTSIDE:
                return;
            case CLIPPING:
                ClippingSystem.needsClipping = true;
                break;
            case ALLINSIDE:
                ClippingSystem.needsClipping = false;
                break;
        }
    }

    private void sendToRenderSystem(RenderableMesh renderableMesh, TransformComponent transformComponent) {
        switch (renderableMesh.renderMode) {
            case MESH:
                meshRenderSystem.render(renderableMesh, transformComponent);
                break;
            case WIREFRAME:
                wireFrameRenderSystem.render(renderableMesh, transformComponent, rendererWireFrame);
                break;
        }
    }

    private Matrix4x4 constructProjMatrix(Camera camera) {
        Transform camTran = camera.transform;
        Matrix4x4 projection = Matrix4x4.newProjection(camera.fFov_AR, camera.fFov, camera.zNear,
                camera.zFar);

        Matrix4x4 lookAt = Matrix4x4.newLookAt(camTran.getForwardDir(), camTran.getRightDir(),
                camTran.getUpDir(), camTran.getPosition());
//        return projection.compose(lookAt);
        return lookAt.compose(projection);
    }

   /* private Matrix4x4 constructProjToWorld(Camera camera) {
        Transform camTran = camera.transform;
        Matrix4x4 projToView = moveToView.compose(Matrix4x4.newProjectionToView(camera.fFov_AR, camera.fFov,
                camera.zNear, camera.zFar));
        Matrix4x4 viewToWOrld = Matrix4x4.newViewToWorld(camTran.getForwardDir(), camTran.getRightDir(),
                camTran.getUpDir(), camTran.getPosition());
        return viewToWOrld.compose(projToView);
    }*/
}




