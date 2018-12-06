package Rendering.Materials;

import Rendering.renderUtil.Bitmap;
import Rendering.renderUtil.Texture;
import Rendering.shaders.ShaderType;
import Rendering.shaders.interfaces.IShader;
import util.Mathf.Mathf;
import util.Mathf.Mathf3D.Vector3D;

public abstract class Material {
    private String name;

    private IShader shader;
    public ShaderType shaderType;
    private Texture texture;
    private Bitmap specularMap;
    private Bitmap normalMap;

    private float ambientFactor = 1f;
    private float diffuseFactor = 1f;
    private float specularFactor = 1f;
    private float specularPower = 100f;
    private Vector3D defualtSpecularColor = Vector3D.newOnes();

    private Vector3D color;

    private boolean isSpecular;
    private boolean hasSpecularMap;
    private boolean isTextured;
    private boolean hasTexture;
    private boolean isDiffuse;
    private boolean isAmbient;
    private boolean hasNormalMap;

    public Material(MaterialCoef materialCoef) {

    }

    public Material(String name, IShader shader, float ambientFactor, float diffuseFactor, float specularFactor) {
        this.name = name;
        this.shader = shader;
        this.ambientFactor = ambientFactor;
        this.diffuseFactor = diffuseFactor;
        this.specularFactor = specularFactor;

        this.isSpecular = true;
        this.isTextured = true;
        this.isDiffuse= true;
        this.isAmbient = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IShader getShader() {
        return shader;
    }

    public void setShader(IShader shader) {
        this.shader = shader;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.hasTexture = true;
    }

    public Bitmap getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(Bitmap specularMap) {
        this.specularMap = specularMap;
        this.hasSpecularMap = true;
    }

    public Bitmap getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Bitmap normalMap) {
        this.normalMap = normalMap;
        hasNormalMap = true;
    }

    public float getAmbientFactor() {
        return ambientFactor;
    }

    public void setAmbientFactor(float ambientFactor) {
        this.ambientFactor = Mathf.clamp(0f, ambientFactor, 1f);
    }

    public float getDiffuseFactor() {
        return diffuseFactor;
    }

    public void setDiffuseFactor(float diffuseFactor) {
        this.diffuseFactor = Mathf.clamp(0f, diffuseFactor, 1f);
    }

    public float getSpecularFactor() {
        return specularFactor;
    }

    public void setSpecularFactor(float specularFactor) {
        this.specularFactor = Mathf.clamp(0f, specularFactor, 1f);
    }

    public float getSpecularPower() {
        return specularPower;
    }

    public void setSpecularPower(float specularPower) {
        this.specularPower = specularPower;
    }

    public Vector3D getDefualtSpecularColor() {
        return defualtSpecularColor;
    }

    public void setDefualtSpecularColor(Vector3D defualtSpecularColor) {
        this.defualtSpecularColor = defualtSpecularColor;
    }

    public Vector3D getColor() {
        return color;
    }

    public void setColor(Vector3D color) {
        this.color = color;
    }

    public boolean isAmbient() {
        return isAmbient;
    }

    public boolean isDiffuse() {
        return isDiffuse;
    }

    public boolean isSpecular() {
        return isSpecular;
    }

    public boolean hasSpecularMap() {
        return hasSpecularMap;
    }

    public boolean hasTexture() {
        return hasTexture;
    }

    public boolean isTextured() {
        return isTextured;
    }

    public boolean hasNormalMap() {
        return hasNormalMap;
    }


    public void setAmbient(boolean ambient) {
        isAmbient = ambient;
    }

    public void setDiffuse(boolean diffuse) {
        isDiffuse = diffuse;
    }

    public void setSpecular(boolean specular) {
        isSpecular = specular;
    }

    public void setTextured(boolean textured) {
        isTextured = textured;
    }

}
