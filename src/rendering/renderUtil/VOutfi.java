package rendering.renderUtil;

import rendering.drawers.fill.Rasterfi;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf2D.Vec2fi;
import util.Mathf.Mathf3D.Vec4f;
import util.Mathf.Mathf3D.Vec4fi;

public class VOutfi {
    public final Vec4fi p_proj;

    public final Vec2fi texCoord;
    public final Vec2fi specCoord;

    public final Vec4fi surfaceColor;

    public final Vec4fi p_ws;
    public final Vec4fi n_ws;

    public int specularity;
    public int invW;

    //TODO: may not need to new up if null
    public VOutfi(VertexOut v) {
        this(new Vec4fi(v.p_proj, Rasterfi.D_SHIFT_POS),v.texCoord,v.specCoord,v.surfaceColor,v.p_ws,v.n_ws,v.spec,v.invW);
    }

    public VOutfi(Vec4fi p_proj, Vec2f texCoord, Vec2f specCoord, Vec4f surfaceColor, Vec4f p_ws, Vec4f n_ws,
                  float specularity, float invW) {
        this.p_proj = p_proj;
        this.texCoord = createVec2fp(texCoord, Rasterfi.D_SHIFT_COORD);
        this.specCoord = createVec2fp(specCoord, Rasterfi.D_SHIFT_COORD);
        this.specularity = Rasterfi.floatToFixed(specularity);
        this.surfaceColor = createVec4fp(surfaceColor, Rasterfi.D_SHIFT_COLOR);
        this.p_ws = createVec4fp(p_ws, Rasterfi.D_SHIFT_INV);
        this.n_ws = createVec4fp(n_ws, Rasterfi.D_SHIFT_INV);
        this.invW = Rasterfi.floatToFixed(invW);

    }

    private Vec2fi createVec2fp(Vec2f v, int D_SHIFT) {
        return (v == null) ? new Vec2fi(D_SHIFT) : new Vec2fi(v, D_SHIFT);
    }

    private Vec4fi createVec4fp(Vec4f v, int D_SHIFT) {
        return (v == null) ? new Vec4fi(D_SHIFT) : new Vec4fi(v,D_SHIFT);
    }

    public void set(VOutfi vOutfi) {
        this.p_proj.set(vOutfi.p_proj);
        this.texCoord.set_safe((vOutfi.texCoord == null) ? Vec2fi.ZERO : vOutfi.texCoord);
        this.specCoord.set_safe((vOutfi.specCoord == null) ? Vec2fi.ZERO : vOutfi.specCoord);
        this.specularity = vOutfi.specularity;
        this.surfaceColor.set(vOutfi.surfaceColor);
        this.p_ws.set((vOutfi.p_ws == null) ? Vec4fi.ZERO : vOutfi.p_ws);
        this.n_ws.set((vOutfi.n_ws == null) ? Vec4fi.ZERO : vOutfi.n_ws);
        this.invW = vOutfi.invW;
    }

    public void set(VertexOut vOut) {
        this.p_proj.set(vOut.p_proj);
        this.texCoord.set((vOut.texCoord == null) ? Vec2f.ZERO : vOut.texCoord);
        this.specCoord.set((vOut.specCoord == null) ? Vec2f.ZERO : vOut.specCoord);
        this.specularity = Rasterfi.floatToFixed(vOut.spec);
        this.surfaceColor.set(vOut.surfaceColor);
        this.p_ws.set((vOut.p_ws == null) ? Vec4f.ZERO : vOut.p_ws);
        this.n_ws.set((vOut.n_ws == null) ? Vec4f.ZERO : vOut.n_ws);
        this.invW = Rasterfi.floatToFixed(vOut.invW);
    }




    /*public VOutfi lerp(VOutfi destination, int lerpAmt) {
        return new Vec4fi(
                p_proj.lerp(destination.p_proj, lerpAmt),
                texCoord.lerp(destination.texCoord, lerpAmt),
                specCoord.lerp(destination.specCoord, lerpAmt),
                Mathf.lerp(specularity, destination.specularity, lerpAmt),
                surfaceColor.lerp(destination.surfaceColor, lerpAmt),
                n_ws.lerp(destination.n_ws, lerpAmt),
                p_ws.lerp(destination.p_ws, lerpAmt),
                Mathf.lerp(invW, destination.invW, lerpAmt)
        );
    }*/
}
