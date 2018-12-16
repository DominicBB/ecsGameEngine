package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.Bitmaps.BitmapABGR;
import Rendering.renderUtil.RenderState;
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
    private static float specular(Vector3D n_worldSpace, Vector3D lightDir, Vector3D viewDir_worldSpace,
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

    static Vector3D calcSpecularAtFrag(Vector2D specCoord, float spec, float z, Material material) {
        if (material.hasSpecularMap()) {
            Vector3D specColor = perspectiveCorrectBitmap(specCoord, material.getSpecularMap(), z);
            return specColor.mul(spec);
        } else {
            return material.getDefualtSpecularColor().mul(spec);
        }
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
        if (zBuffer.getFloat(x, y) > zVal) {
            zBuffer.setFloat(x, y, zVal);
            return true;
        }
        return false;
    }

    static Vector3D perspectiveCorrectBitmap(Vector2D coord, BitmapABGR bitmapABGR, float z) {
        return bitmapABGR.getPixel((int) (coord.x * z), (int) (coord.y * z));
    }

    static void perspectiveCorrectBitmapNonAlloc(Vector2D coord, BitmapABGR bitmapABGR, float z, Vector3D out) {
        bitmapABGR.getPixelNonAlloc((int) (coord.x * z), (int) (coord.y * z), out);
    }

    static Vector2D scaleToBitmap(Vector2D in, BitmapABGR bitmapABGR) {
        return new Vector2D(
                in.x * (bitmapABGR.getWidth() - 1) /*+ 0.5f*/,
                in.y * (bitmapABGR.getHeight() - 1) /*+ 0.5f*/
        );
    }
}
