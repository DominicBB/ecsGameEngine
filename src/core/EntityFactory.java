package core;

import Rendering.Materials.Material;
import Rendering.Materials.MaterialPresets;
import Rendering.renderUtil.Bitmaps.Bitmap;
import Rendering.renderUtil.Bitmaps.Texture;
import Rendering.renderUtil.Meshes.IndexedMesh;
import components.*;
import core.coreSystems.EntitySystem;

import util.Mathf.Mathf3D.Transform;
import util.ObjFileToIndexedMesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityFactory {

    public static EntitySystem entitySystem;

    public static Entity createEntity(String modelFilePath, String textureFilePath, RenderableMesh renderableMesh,
                                      List<Component> components) {

        IndexedMesh mesh = ObjFileToIndexedMesh.loadFromPath(modelFilePath);

        if (textureFilePath != null) {
            try {
                Bitmap texture = new Bitmap(textureFilePath);
                renderableMesh.material.setTexture(new Texture(texture));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        renderableMesh.indexedMesh = mesh;

        return entitySystem.createEntity(components);
    }

    public static Camera createCamera(Camera camera, Transform transform) {
        entitySystem.createEntity(new ArrayList<>(Arrays.asList(camera, new TransformComponent(transform))));
        return camera;
    }
}
