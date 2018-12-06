package Rendering.shaders;

import Rendering.Materials.Material;
import Rendering.renderUtil.RenderContext;
import util.Mathf.Mathf3D.Vector3D;

abstract class ShaderUtil {
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

    static float calculateSpecular(Vector3D n_ws, Vector3D pos_ws, RenderContext renderContext, Material material) {
        Vector3D viewDir;
        (viewDir = renderContext.getCameraPos().minus(pos_ws)).normalise();

        float spec = specular(n_ws,
                renderContext.getLightingState().lightDir,
                viewDir,
                material.getSpecularFactor(),
                material.getSpecularPower(),
                renderContext.getLightingState().attenuation);

        return spec;
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

        return lightColor.mul(cosTheta * attenuation * diffuseFactor);
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


    static boolean zBufferTest(float[] zBuffer, float zVal, int x, int y, int width) {
        int i = y * width + x;
        if (zBuffer[i] > zVal) {
            zBuffer[i] = zVal;
            return true;
        }
        return false;
    }
}
