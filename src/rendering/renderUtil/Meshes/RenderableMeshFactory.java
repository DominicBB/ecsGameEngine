package rendering.renderUtil.Meshes;

import rendering.Materials.Material;
import components.RenderableMesh;
import util.ObjFileToIndexedMesh;

import java.util.HashMap;
import java.util.Map;

public class RenderableMeshFactory {

    private static Map<String, RenderableMesh> renderableMeshMap = new HashMap<>();

    /**
     * Loads a '.obj' file
     *
     * @param path
     * @return
     */
    public static IndexedMesh loadMesh(String path) {
        return ObjFileToIndexedMesh.loadFromPath(path);
    }

    public static void createRenderableMesh(String indexedMeshPath, Material material, String identifier) {
        if (renderableMeshMap.get(identifier)!= null)
            return ;
        IndexedMesh indexedMesh = loadMesh(indexedMeshPath);
        createRenderableMesh(indexedMesh, material, identifier);
    }

    public static void createRenderableMesh(IndexedMesh indexedMesh, Material material, String identifier) {
        if (renderableMeshMap.get(identifier)!= null)
            return ;
        renderableMeshMap.put(identifier, new RenderableMesh(indexedMesh, material));
    }

    public static RenderableMesh getRenderableMesh(String identifier){
        return renderableMeshMap.get(identifier);
    }
}
