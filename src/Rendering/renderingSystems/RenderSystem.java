package Rendering.renderingSystems;

import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Renderer;
import components.Camera;
import components.Component;
import components.RenderableMesh;
import components.TransformComponent;
import core.Window;
import core.coreSystems.EntityListnerSystem;
import core.coreSystems.SystemCommunicator;
import util.Mathf.Mathf3D.Matrix4x4;
import util.Mathf.Mathf3D.Transform;

import java.util.Arrays;

public class RenderSystem extends EntityListnerSystem {

    private MeshRenderSystem meshRenderSystem;
    private WireFrameRenderSystem wireFrameRenderSystem;

    private Matrix4x4 normalisePlus1;
    Matrix4x4 moveToView;

    final Renderer renderer;


    public RenderSystem() {
        super(Arrays.asList(RenderableMesh.class, TransformComponent.class));
        constructMoveToViewMatrix();

        renderer = new Renderer(Window.defaultWidth, Window.defaultHeight);

        initRenderers();

        SystemCommunicator.registerRenderSystem(this);
    }

    private void initRenderers() {
        meshRenderSystem = new MeshRenderSystem(this);
        wireFrameRenderSystem = new WireFrameRenderSystem(this);
    }

    @Override
    public void update() {
        renderer.getzBuffer().resetPositiveInf();
        renderer.colorBuffer.clear((byte) 0);//make screen black


        RenderState.mvp = constructProjMatrix(RenderState.camera);
        RenderState.projectionToWorld = constructProjToWorld(RenderState.camera);
        for (int entityID : entityListner.getEntityIDsOfInterest()) {
            //grab components
            Component[] relevantComponents = entityListner.getRelevantComponents(entityID);
            RenderableMesh renderableMesh = (RenderableMesh) relevantComponents[0];
            TransformComponent transformComponent = (TransformComponent) relevantComponents[1];

            switch (renderableMesh.renderMode) {
                case MESH:
                    meshRenderSystem.render(renderableMesh, transformComponent, renderer);
                case WIREFRAME:
                    wireFrameRenderSystem.render(renderableMesh, transformComponent, renderer);
            }

        }
    }

    private Matrix4x4 constructProjToWorld(Camera camera) {
        Transform camTran = camera.transform;
        Matrix4x4 projToView = moveToView.compose(Matrix4x4.newProjectionToView(camera.fFov_AR, camera.fFov,
                camera.zNear, camera.zFar));
        Matrix4x4 viewToWOrld = Matrix4x4.newViewToWorld(camTran.getForwardDir(), camTran.getRightDir(),
                camTran.getUpDir(), camTran.position);
        return viewToWOrld.compose(projToView);
    }

    protected Matrix4x4 constructProjMatrix(Camera camera) {
        Transform camTran = camera.transform;
        Matrix4x4 projection = Matrix4x4.newProjectionMatrix(camera.fFov_AR, camera.fFov, camera.zNear,
                camera.zFar).compose(moveToView);
        Matrix4x4 lookAt = Matrix4x4.newLookAt(camTran.getForwardDir(), camTran.getRightDir(),
                camTran.getUpDir(), camTran.position);
        return (projection).compose(lookAt);
    }

    protected void constructMoveToViewMatrix() {
        normalisePlus1 = Matrix4x4.newTranslation(1, 1, 0);
        moveToView = (normalisePlus1).compose(
                Matrix4x4.newScale(0.5f * Window.defaultWidth, 0.5f * Window.defaultHeight, 1));
    }

    public Renderer getRenderer() {
        return renderer;
    }


}




