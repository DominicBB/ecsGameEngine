package Rendering.renderingSystems;

import Rendering.Clipping.ClippingSystem;
import Rendering.Renderers.Renderer;
import components.*;
import core.coreSystems.EntityGrabberSystem;

import java.util.Arrays;

public class MeshRenderSystem extends EntityGrabberSystem {
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

        //decide frustum clipping
        switch (ClippingSystem.decideClippingMode(renderableMesh.aaBoundingBox)) {
            case ALLOUTSIDE:
                return;
            case CLIPPING:
                ClippingSystem.needsClipping = true;
                break;
            case ALLINSIDE:
                ClippingSystem.needsClipping = false;
                break;
        }
        renderableMesh.indexedMesh.draw(renderer, renderableMesh.material, transformComponent.transform);

    }

    public void setRenderSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

}
