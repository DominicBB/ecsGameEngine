package Rendering.renderUtil;

import util.Mathf.Mathf3D.Vector3D;

public class LightingState {
    public float attenuation;
    public Vector3D lightDir;
    public Vector3D lightColor;
    public Vector3D ambientColor;

    public LightingState(float attenuation, Vector3D lightDir, Vector3D lightColor, Vector3D ambientColor) {
        this.attenuation = attenuation;
        this.lightDir = lightDir;
        this.lightColor = lightColor;
        this.ambientColor = ambientColor;
    }

    public void setState(float attenuation, Vector3D lightDir, Vector3D lightColor, Vector3D ambientColor) {
        this.attenuation = attenuation;
        this.lightDir = lightDir;
        this.lightColor = lightColor;
        this.ambientColor = ambientColor;
    }
}
