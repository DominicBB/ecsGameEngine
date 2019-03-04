package rendering.renderUtil;

import util.mathf.Mathf;
import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Vec4f;

public class VertexOut {
    public final Vec4f p_proj;
    public final Vec2f texCoord;
    public final Vec2f specCoord;

    public final Vec4f surfaceColor;

    public final Vec4f p_ws;
    public final Vec4f n_ws;

    public float spec;
    public float invW;

    public VertexOut(Vec4f p_proj, Vec2f texCoord, Vec2f specCoord, float spec, Vec4f surfaceColor,
                     Vec4f n_ws, Vec4f p_ws, float invW) {
        this.p_proj = p_proj;
        this.texCoord = (texCoord == null) ? Vec2f.newOnes() : texCoord;
        this.specCoord = (specCoord == null) ? Vec2f.newOnes() : specCoord;
        this.spec = spec;
        this.surfaceColor = (surfaceColor == null) ? Vec4f.newOnes() : surfaceColor;
        this.p_ws = (p_ws == null) ? Vec4f.newOnes() : p_ws;
        this.n_ws = (n_ws == null) ? Vec4f.newOnes() : n_ws;
        this.invW = invW;

    }

    public void set(VertexOut vertexOut) {
        this.p_proj.set(vertexOut.p_proj);
        this.texCoord.set((vertexOut.texCoord == null) ? Vec2f.ZERO : vertexOut.texCoord);
        this.specCoord.set((vertexOut.specCoord == null) ? Vec2f.ZERO : vertexOut.specCoord);
        this.spec = vertexOut.spec;
        this.surfaceColor.set(vertexOut.surfaceColor);
        this.p_ws.set((vertexOut.p_ws == null) ? Vec4f.ZERO : vertexOut.p_ws);
        this.n_ws.set((vertexOut.n_ws == null) ? Vec4f.ZERO : vertexOut.n_ws);
        this.invW = vertexOut.invW;
    }

    public void wDivide() {
        if (p_proj.w == 0)
            return;
        float w = 1f / p_proj.w;
        p_proj.x = p_proj.x * w;
        p_proj.y = p_proj.y * w;
        p_proj.z = p_proj.z * w;
    }

    public VertexOut wDivideNew() {

        if (p_proj.w == 0)
            return this;


        Vec4f vec = new Vec4f(
                p_proj.x * invW,
                p_proj.y * invW,
                p_proj.z * invW,
                p_proj.w);

        return new VertexOut(vec, texCoord, specCoord, spec, surfaceColor, n_ws, p_ws, invW);
    }


    public VertexOut lerp(VertexOut destination, float lerpAmt) {
        return new VertexOut(
                p_proj.lerp(destination.p_proj, lerpAmt),
                texCoord.lerp(destination.texCoord, lerpAmt),
                specCoord.lerp(destination.specCoord, lerpAmt),
                Mathf.lerp(spec, destination.spec, lerpAmt),
                surfaceColor.lerp(destination.surfaceColor, lerpAmt),
                n_ws.lerp(destination.n_ws, lerpAmt),
                p_ws.lerp(destination.p_ws, lerpAmt),
                Mathf.lerp(invW, destination.invW, lerpAmt)
        );
    }

    public static VertexOut newZeros() {
        return new VertexOut(Vec4f.newZeros(), Vec2f.newZeros(), Vec2f.newZeros(), 0f, Vec4f.newZeros(),
                Vec4f.newZeros(), Vec4f.newZeros(), 0f);
    }


}
