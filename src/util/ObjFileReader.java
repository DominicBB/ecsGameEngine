package util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.geometry.*;

public class ObjFileReader {

    private static Vector3D[] vecterDict;
    private static Vector3D[] vtDict;
    private static Vector3D[] vnDict;

    //	private static String vectorLine = "v\\s(-?[0-9]+\\.?[0-9]+\\s){3}";
    private static String vectorSplit = "(-?[0-9]+\\.?[0-9]+\\s)";

    //	private static String faceLine = "f\\s([0-9]+(\\/[0-9]+)?\\s){3}";
    private static String faceSplit = "([0-9]+(\\/[0-9]+)?\\s)";


    /**
     * Reads a obj file whose faces are triangles
     *
     * @param path
     * @return
     */
    public static Mesh loadOBJFromPath(String path) {
        File file = new File(path);
        try {
            Mesh mesh = new Mesh();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            vecterDict = new Vector3D[(int) file.length()];
            vtDict = new Vector3D[(int) file.length()];
            vnDict = new Vector3D[(int) file.length()];
            String line = bufferedReader.readLine();
            int vCount = 1;
            int vtCount = 1;
            int vnCount = 1;

            Pattern p;
            Matcher m;

            while (line != null) {
                if (line.startsWith("#")) {
                    line = bufferedReader.readLine();
                }

                //normals
                if (line.startsWith("vn")) {
                    vnDict[vnCount++] = readVector(line);
                }
                //texture mappings
                else if (line.startsWith("vt")) {
                    vtDict[vtCount++] = readVector(line);
                }
                //vector
                else if (line.startsWith("v")) {
                    Vector3D vector3D = readVector(line);
                    updateBoundingBox(mesh, vector3D);
                    vecterDict[vCount++] = vector3D;

                }

                if (line.startsWith("f")) {
                    p = Pattern.compile(faceSplit, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(line);
                    if (m.find()) {
                        String[] face = line.split(" ");

                        Vector3D[] vectors = new Vector3D[4];//enough room for quad
                        Vector2D[] textures = new Vector2D[4];

                        //start at 1 as first index is "f"
                        int i;
                        for (i = 1; i < face.length; i++) {
                            String[] fvtvn = face[i].split("\\/"); //split into f vt vn
                            //take f
                            vectors[i - 1] = vecterDict[Integer.parseInt(fvtvn[0])];
                            //texture
                            if (fvtvn.length > 2) {
                                Vector3D tx = vtDict[Integer.parseInt(fvtvn[1])];
                                if (tx != null) {
                                    textures[i - 1] = new Vector2D(tx.x, tx.y);
                                }
                            }

                        }

                        //split quad into triangles
                        if (i == 5) {
                            Triangle[] tris = new Quad(vectors, textures).convertToTriangles();
                            mesh.triangles.add(tris[0]);
                            mesh.triangles.add(tris[1]);
                        } else {
                            mesh.triangles.add(new Triangle(vectors, textures));
                        }
                    }
                }

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return mesh;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static void updateBoundingBox(Mesh mesh, Vector3D vector3D) {
        MinimumBoundingBox minimumBoundingBox = mesh.minimumBoundingBox;
        if (vector3D.x > minimumBoundingBox.max.x) minimumBoundingBox.max.x = vector3D.x;
        if (vector3D.x < minimumBoundingBox.min.x) minimumBoundingBox.min.x = vector3D.x;

        if (vector3D.y > minimumBoundingBox.max.y) minimumBoundingBox.max.y = vector3D.y;
        if (vector3D.y < minimumBoundingBox.min.y) minimumBoundingBox.min.y = vector3D.y;

        if (vector3D.z > minimumBoundingBox.max.z) minimumBoundingBox.max.z = vector3D.z;
        if (vector3D.z < minimumBoundingBox.min.z) minimumBoundingBox.min.z = vector3D.z;

    }

    private static Vector3D readVector(String line) {
        float x, y, z;

        Pattern p = Pattern.compile(vectorSplit, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(line);
        if (m.find()) {
            String[] xyz = line.split(" ");
            int i = 1;
            while (xyz[i].equals("")) {
                i++;
            }
            x = Float.parseFloat(xyz[i]);
            y = Float.parseFloat(xyz[i + 1]);
            z = Float.parseFloat(xyz[i + 2]);

            return new Vector3D(x, y, z);
        }
        return null;
    }

    private void createPolygons() {

    }
}
