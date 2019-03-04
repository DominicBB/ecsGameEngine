package rendering.renderUtil;

import util.mathf.Mathf3D.Vec4f;

public class LightingState {
    public float attenuation;
    public Vec4f lightDir;
    public Vec4f lightColor;
    public Vec4f ambientColor;

    public LightingState(float attenuation, Vec4f lightDir, Vec4f lightColor, Vec4f ambientColor) {
        setState(attenuation, lightDir, lightColor, ambientColor);
    }

    public void setState(float attenuation, Vec4f lightDir, Vec4f lightColor, Vec4f ambientColor) {

        this.attenuation = attenuation;
        this.lightDir = lightDir;
        this.lightColor = lightColor;
        this.ambientColor = ambientColor;
    }
}
