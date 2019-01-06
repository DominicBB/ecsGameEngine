package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.drawers.fill.Rasterfi;
import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.Bitmaps.BitmapBGR;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import util.IntBuffer;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Vec4f;
import util.Mathf.Mathf3D.Vec4fi;

final class ShaderUtil {

    // -------------- SPECULAR --------------------------------------------

    /**
     * Expensive :)
     *
     * @param n_worldSpace
     * @param lightDir
     * @param viewDir_worldSpace
     * @param specFactor
     * @param specPower
     * @param attenuation
     * @return
     */
    static float specular(Vec4f n_worldSpace, Vec4f lightDir, Vec4f viewDir_worldSpace,
                          float specFactor, float specPower, float attenuation) {
        Vec4f halfWayDir;
        (halfWayDir = lightDir.plus(viewDir_worldSpace)).normalise();

        return (specFactor *
                attenuation *
                (float) Math.pow(Math.max(0f, n_worldSpace.dotProduct(halfWayDir)), specPower));
    }

    static float calculateSpecular(Vec4f n_ws, Vec4f pos_ws, Material material) {
        Vec4f viewDir;
        (viewDir = RenderState.camera.transform.getPosition().minus(pos_ws)).normalise();

        float spec = specular(n_ws,
                RenderState.lightingState.lightDir,
                viewDir,
                material.getSpecularFactor(),
                material.getSpecularPower(),
                RenderState.lightingState.attenuation);

        return Mathf.unsafeMax(0f, spec);
    }

    static Vec4fi calcSpecularAtFrag(int u, int v, int spec, int w, Material material) {
        Vec4fi specColor;
        if (material.hasSpecularMap()) {
             specColor = sample_persp(u, v, material.getSpecularMap(), w);
        } else {
           specColor = new Vec4fi(material.getDefualtSpecularColor(), Rasterfi.D_SHIFT);//TODO: should store in mat
        }
        specColor.mul_unsafe(spec, 18);
        return specColor ;

    }

    // -------------- DIFFUSE --------------------------------------------

    static Vec4f diffuse(Vec4f n, Material material) {
        return diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());
    }

    /*static Vec4fi diffuse(Vec4fi n, Material material) {
        return diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());
    }*/


    /**
     * cheaper
     *
     * @param lightColor
     * @param lightDir
     * @param norm
     * @param attenuation
     * @param diffuseFactor
     * @return
     */
    static Vec4f diffuse(Vec4f lightColor, Vec4f lightDir, Vec4f norm, float attenuation, float diffuseFactor) {
        float cosTheta = (norm.dotProduct(lightDir));

        return lightColor.mul(Mathf.clamp(0f, cosTheta * attenuation * diffuseFactor, 1f));
    }

    /*static Vec4fi diffuse(Vec4f lightColor, Vec4f lightDir, Vec4fi norm, float attenuation, float diffuseFactor) {
        float cosTheta = (norm.dotProduct(lightDir));

        return lightColor.mul(Mathf.clamp(0f, cosTheta * attenuation * diffuseFactor, 1f));
    }*/

    // -------------- AMBIENT --------------------------------------------

    /**
     * super cheap
     *
     * @param ambientColor
     * @param ambientFactor
     * @return
     */
    static Vec4f ambient(Vec4f ambientColor, float ambientFactor) {
        return ambientColor.mul(ambientFactor);
    }

    // -------------- SAMPLING --------------------------------------------

    static Vec4fi sample_persp(int u, int v, BitmapABGR bitmapABGR, int z) {
        return bitmapABGR.getPixel_vInt(Rasterfi.toInt(Rasterfi.multiply(u, z)), Rasterfi.toInt(Rasterfi.multiply(v, z)),
                Rasterfi.D_SHIFT);
    }

    static void sample_persp_NonAlloc(int u, int v, BitmapABGR bitmapABGR, int z, Vec4fi out) {
        bitmapABGR.getPixelNonAlloc_vInt(Rasterfi.toInt(Rasterfi.multiply(u, z)), Rasterfi.toInt(Rasterfi.multiply(v, z)),
                out);
    }

    static Vec2f scaleToBitmap(Vec2f in, BitmapABGR bitmapABGR) {
        return new Vec2f(
                in.x * (bitmapABGR.getWidth() - 1) /*+ 0.5f*/,
                in.y * (bitmapABGR.getHeight() - 1) /*+ 0.5f*/
        );
    }

    static Vec4f sample(Vec2f coord, BitmapBGR bitmapBGR) {
        return bitmapBGR.getPixel((int) coord.x,
                (int) coord.y);
    }

    static Vec4f sample(Vec2f coord, BitmapABGR bitmapABGR) {
        return bitmapABGR.getPixel((int) coord.x,
                (int) coord.y);
    }

    // -------------- CREATING VERTEXOUTS --------------------------------------------

    static VertexOut transformVIn(Vertex vertex, Material material) {
        VertexOut vertexOut = new VertexOut(Vec4f.newZeros(), Vec2f.newZeros(), Vec2f.newZeros(),
                0f, /*Vec4f.newCopy(material.getColor())*/Vec4f.newZeros(), Vec4f.newZeros(), Vec4f.newZeros(), 0f);
        setVOut(vertex, material, vertexOut);
        return vertexOut;
    }

    static void setVOut(Vertex vIn, Material material, VertexOut out) {
        out.p_proj.set(RenderState.mvp.multiply4x4(vIn.vec));
        out.invW = 1f / out.p_proj.w;

        if (material.hasTexture()) {
            out.texCoord.set(scaleToBitmap(vIn.texCoord, material.getTexture().texture));
            out.texCoord.scale(out.invW);
        }
        if (material.hasSpecularMap()) {
            out.specCoord.set(scaleToBitmap(vIn.specCoord, material.getSpecularMap()));
            out.specCoord.scale(out.invW);
        }
        out.spec = 1f;
        out.surfaceColor.set(/*material.getColor()*/Vec4f.ZERO);
        out.n_ws.set(RenderState.transform.getRotation().rotate(vIn.normal));
        out.p_ws.set(RenderState.world.multiply4x4(vIn.vec));
    }

    // -------------- MISC --------------------------------------------

    static boolean zBufferTest(IntBuffer zBuffer, int zVal, int x, int y) {
        if (zBuffer.getInt(x, y) > zVal) {// if pixel is further away
            zBuffer.setInt(x, y, zVal);
            return true;
        }
        return false;
    }
}
