package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.Bitmaps.BitmapBGR;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import util.FloatBuffer;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Vec4f;

final class ShaderUtil {
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

    static Vec4f calcSpecularAtFrag(float u, float v, float spec, float z, Material material) {
        if (material.hasSpecularMap()) {
            Vec4f specColor = sample_persp(u, v, material.getSpecularMap(), z);
            return specColor.mul(spec);
        } else {
            return material.getDefualtSpecularColor().mul(spec);
        }
    }

    static Vec4f diffuse(Vec4f n, Material material) {
        return diffuse(RenderState.lightingState.lightColor,
                RenderState.lightingState.lightDir,
                n,
                RenderState.lightingState.attenuation,
                material.getDiffuseFactor());
    }


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


    static boolean zBufferTest(FloatBuffer zBuffer, float zVal, int x, int y) {
        if (zBuffer.getFloat(x, y) > zVal) {// if pixel is further away
            zBuffer.setFloat(x, y, zVal);
            return true;
        }
        return false;
    }

    static Vec4f sample_persp(float u, float v, BitmapABGR bitmapABGR, float z) {
        return bitmapABGR.getPixel((int) (u * z), (int) (v * z));
    }

    static void sample_persp_NonAlloc(float u, float v, BitmapABGR bitmapABGR, float z, Vec4f out) {
        bitmapABGR.getPixelNonAlloc((int) (u * z), (int) (v * z), out);
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

}
