package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.Bitmaps.BitmapBGR;
import Rendering.renderUtil.RenderState;
import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import util.FloatBuffer;
import util.Mathf.Mathf;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Vector3D;

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
    static float specular(Vector3D n_worldSpace, Vector3D lightDir, Vector3D viewDir_worldSpace,
                          float specFactor, float specPower, float attenuation) {
        Vector3D halfWayDir;
        (halfWayDir = lightDir.plus(viewDir_worldSpace)).normalise();

        return (specFactor *
                attenuation *
                (float) Math.pow(Math.max(0f, n_worldSpace.dotProduct(halfWayDir)), specPower));
    }

    static float calculateSpecular(Vector3D n_ws, Vector3D pos_ws, Material material) {
        Vector3D viewDir;
        (viewDir = RenderState.camera.transform.getPosition().minus(pos_ws)).normalise();

        float spec = specular(n_ws,
                RenderState.lightingState.lightDir,
                viewDir,
                material.getSpecularFactor(),
                material.getSpecularPower(),
                RenderState.lightingState.attenuation);

        return Mathf.unsafeMax(0f, spec);
    }

    static Vector3D calcSpecularAtFrag(float u, float v, float spec, float z, Material material) {
        if (material.hasSpecularMap()) {
            Vector3D specColor = sample_persp(u, v, material.getSpecularMap(), z);
            return specColor.mul(spec);
        } else {
            return material.getDefualtSpecularColor().mul(spec);
        }
    }

    static Vector3D diffuse(Vector3D n, Material material) {
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
    static Vector3D diffuse(Vector3D lightColor, Vector3D lightDir, Vector3D norm, float attenuation, float diffuseFactor) {
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
    static Vector3D ambient(Vector3D ambientColor, float ambientFactor) {
        return ambientColor.mul(ambientFactor);
    }


    static boolean zBufferTest(FloatBuffer zBuffer, float zVal, int x, int y) {
        if (zBuffer.getFloat(x, y) > zVal) {// if pixel is further away
            zBuffer.setFloat(x, y, zVal);
            return true;
        }
        return false;
    }

    static Vector3D sample_persp(float u, float v, BitmapABGR bitmapABGR, float z) {
        return bitmapABGR.getPixel((int) (u * z), (int) (v * z));
    }

    static void sample_persp_NonAlloc(float u, float v, BitmapABGR bitmapABGR, float z, Vector3D out) {
        bitmapABGR.getPixelNonAlloc((int) (u * z), (int) (v * z), out);
    }

    static Vector2D scaleToBitmap(Vector2D in, BitmapABGR bitmapABGR) {
        return new Vector2D(
                in.x * (bitmapABGR.getWidth() - 1) /*+ 0.5f*/,
                in.y * (bitmapABGR.getHeight() - 1) /*+ 0.5f*/
        );
    }

    static Vector3D sample(Vector2D coord, BitmapBGR bitmapBGR) {
        return bitmapBGR.getPixel((int) coord.x,
                (int) coord.y);
    }

    static Vector3D sample(Vector2D coord, BitmapABGR bitmapABGR) {
        return bitmapABGR.getPixel((int) coord.x,
                (int) coord.y);
    }

    static VertexOut transformVIn(Vertex vertex, Material material) {
        VertexOut vertexOut = new VertexOut(Vector3D.newZeros(), Vector2D.newZeros(), Vector2D.newZeros(),
                0f, /*Vector3D.newCopy(material.getColor())*/Vector3D.newZeros(), Vector3D.newZeros(), Vector3D.newZeros(), 0f);
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
        out.surfaceColor.set(/*material.getColor()*/Vector3D.ZERO);
        out.n_ws.set(RenderState.transform.getRotation().rotate(vIn.normal));
        out.p_ws.set(RenderState.world.multiply4x4(vIn.vec));
    }

}
