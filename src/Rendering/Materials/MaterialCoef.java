package Rendering.Materials;

import Rendering.renderUtil.Bitmaps.Bitmap;

public class MaterialCoef {
    private String name;
    private Bitmap texture;
    private Bitmap specularMap;



    private float ambientFactor = 1f;
    private float diffuseFactor = 1f;
    private float specularFactor = 1f;
    private float specularPower = 100f;

    public MaterialCoef(String name, Bitmap specularMap, float ambientFactor,
                        float diffuseFactor, float specularFactor, float specularPower) {
        this.name = name;
        this.specularMap = specularMap;
        this.ambientFactor = ambientFactor;
        this.diffuseFactor = diffuseFactor;
        this.specularFactor = specularFactor;
        this.specularPower = specularPower;
    }


    //TODO: create static known materials
}
