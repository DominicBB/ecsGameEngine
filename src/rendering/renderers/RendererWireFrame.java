package rendering.renderers;

import rendering.clipping.ClippingSystem;
import rendering.materials.Material;
import rendering.drawers.Draw;
import rendering.renderUtil.RenderState;
import rendering.renderUtil.Vertex;
import rendering.renderUtil.VertexOut;
import util.mathf.Mathf3D.Vec4f;

import java.util.ArrayList;
import java.util.List;

import static rendering.renderers.Renderer.backFaceCull;
import static rendering.renderers.Renderer.moveToScreenSpaceNew;

public class RendererWireFrame {
    private final ClippingSystem clippingSystem = new ClippingSystem();
    private final List<VertexOut> clippedVertices = new ArrayList<>();
    private final VertexOut[] vertexOuts = new VertexOut[3];

    public void wireFrameTriangle(Vertex v1, Vertex v2, Vertex v3, Material material) {


        VertexOut v1Out = new VertexOut(RenderState.mvp.multiply4x4(v1.vec),
                v1.texCoord, v1.specCoord, 1f, Vec4f.newOnes(), v1.normal, v1.vec, 1f);
        VertexOut v2Out = new VertexOut(RenderState.mvp.multiply4x4(v2.vec),
                v2.texCoord, v2.specCoord, 1f, Vec4f.newOnes(), v2.normal, v2.vec, 1f);
        VertexOut v3Out = new VertexOut(RenderState.mvp.multiply4x4(v3.vec),
                v3.texCoord, v3.specCoord, 1f, Vec4f.newOnes(), v3.normal, v3.vec, 1f);

        //clip
        clippingSystem.clipTriangle(clippedVertices, v1Out, v2Out, v3Out);
        if (clippedVertices.isEmpty()) return;

        /*for(VertexOut vertexOut: clippedVertices){
            moveToScreenSpace(vertexOut);
        }*/
        v1Out = clippedVertices.get(0);
        v1Out = moveToScreenSpaceNew(v1Out);

        int end = clippedVertices.size() - 1;
        for (int i = 1; i < end; i++) {
            v2Out = clippedVertices.get(i);
            v3Out = clippedVertices.get(i + 1);

            //perspective divide
            v2Out = moveToScreenSpaceNew(v2Out);
            v3Out = moveToScreenSpaceNew(v3Out);

            //backface cull
            if (backFaceCull(v1Out, v2Out, v3Out))
                return;

            Draw.wireframePolygon(v1Out, v2Out, v3Out, material);
        }
    }


}
