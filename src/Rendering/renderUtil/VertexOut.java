package Rendering.renderUtil;

import Rendering.drawers.fill.Rasterfi;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Vec4f;
import util.Mathf.Mathf3D.Vec4fi;

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
        p_proj.x = p_proj.x * invW;
        p_proj.y = p_proj.y * invW;
        p_proj.z = p_proj.z * invW;
    }

    public VOutfi wDivideNew() {
        if (p_proj.w == 0)
            return new VOutfi(this);

        Vec4fi vec = new Vec4fi(
                Rasterfi.floatToFixed(moveToScreenSpace(p_proj.x * invW, RenderState.HALF_WIDTHf)),
                Rasterfi.floatToFixed(moveToScreenSpace(p_proj.y * invW, RenderState.HALF_HEIGHTf)),
                Rasterfi.floatToFixed(p_proj.z * invW),
                Rasterfi.floatToFixed(p_proj.w),
                Rasterfi.D_SHIFT
        );

        return new VOutfi(vec, texCoord, specCoord, surfaceColor, n_ws, p_ws, spec, invW);
    }

    private float moveToScreenSpace(float f, float half) {
        return (f + 1f) * half;
    }


    public VertexOut lerp(VertexOut destination, float lerpAmt) {
        return new VertexOut(
                p_proj.lerpWithW(destination.p_proj, lerpAmt),
                texCoord.lerp(destination.texCoord, lerpAmt),
                specCoord.lerp(destination.specCoord, lerpAmt),
                Mathf.lerp(spec, destination.spec, lerpAmt),
                surfaceColor.lerpWithW(destination.surfaceColor, lerpAmt),
                n_ws.lerpWithW(destination.n_ws, lerpAmt),
                p_ws.lerpWithW(destination.p_ws, lerpAmt),
                Mathf.lerp(invW, destination.invW, lerpAmt)
        );
    }

    public static VertexOut newZeros() {
        return new VertexOut(Vec4f.newZeros(), Vec2f.newZeros(), Vec2f.newZeros(), 0f, Vec4f.newZeros(),
                Vec4f.newZeros(), Vec4f.newZeros(), 0f);
    }


}
