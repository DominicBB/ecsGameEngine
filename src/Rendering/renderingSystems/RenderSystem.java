package Rendering.renderingSystems;

import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Renderer;
import components.Camera;
import components.Component;
import components.RenderableMesh;
import components.TransformComponent;
import core.EntityFactory;
import core.Window;
import core.coreSystems.EntityGrabberSystem;
import core.coreSystems.SystemCommunicator;
import util.FloatBuffer;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Transform;

import java.util.Arrays;

public class RenderSystem extends EntityGrabberSystem{

    private MeshRenderSystem meshRenderSystem;
    private WireFrameRenderSystem wireFrameRenderSystem;

    final Renderer renderer;

    public RenderSystem() {
        super(Arrays.asList(RenderableMesh.class, TransformComponent.class));

        RenderState.camera = EntityFactory.createCamera(new Camera(), new Transform());
        RenderState.createLightingState();
        RenderState.zBuffer =  new FloatBuffer(Window.defaultWidth, Window.defaultHeight);
        renderer = new Renderer();
        initRenderers();
        SystemCommunicator.registerRenderSystem(this);
    }

    private void initRenderers() {
        meshRenderSystem = new MeshRenderSystem(this);
        wireFrameRenderSystem = new WireFrameRenderSystem(this);
    }


    @Override
    public void update() {
        RenderState.zBuffer.resetPositiveInf();
        RenderState.colorBuffer.clearToBlack();

        RenderState.mvp = constructProjMatrix(RenderState.camera);
//        RenderState.projectionToWorld = constructProjToWorld(RenderState.camera);
        for (int entityID : entityGrabber.getEntityIDsOfInterest()) {
            //grab components
            Component[] relevantComponents = entityGrabber.getRelevantComponents(entityID);
            RenderableMesh renderableMesh = (RenderableMesh) relevantComponents[0];
            TransformComponent transformComponent = (TransformComponent) relevantComponents[1];

            switch (renderableMesh.renderMode) {
                case MESH:
                    meshRenderSystem.render(renderableMesh, transformComponent, renderer);
                    break;
                case WIREFRAME:
                    wireFrameRenderSystem.render(renderableMesh, transformComponent, renderer);
                    break;
            }
        }
    }


    private Matrix4x4 constructProjMatrix(Camera camera) {
        Transform camTran = camera.transform;
        Matrix4x4 projection = Matrix4x4.newProjection(camera.fFov_AR, camera.fFov, camera.zNear,
                camera.zFar);

        /*Matrix4x4 projection = Matrix4x4.InitPerspective(camera.fFov, camera.fFov_AR, camera.zNear,
                camera.zFar);*/

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



    public Renderer getRenderer() {
        return renderer;
    }


}




