package util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rendering.renderUtil.meshes.TriangleMesh;
import util.mathf.Mathf2D.Vec2f;
import util.mathf.Mathf3D.Quad;
import util.mathf.Mathf3D.Triangle;
import util.mathf.Mathf3D.Vec4f;

public class ObjFileToTriangleMesh {

    private static Vec4f[] vecterDict;
    private static Vec4f[] vtDict;
    private static Vec4f[] vnDict;

    //	private static String vectorLine = "v\\s(-?[0-9]+\\.?[0-9]+\\s){3}";
    private static String vectorSplit = "(-?[0-9]+\\.?[0-9]+\\s)";

    //	private static String faceLine = "f\\s([0-9]+(\\/[0-9]+)?\\s){3}";
    private static String faceSplit = "([0-9]+(\\/[0-9]+)?\\s)";
    
    public static TriangleMesh loadFromPath(String path) {
        File file = new File(path);
        try {
            TriangleMesh triangleMesh = new TriangleMesh();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            vecterDict = new Vec4f[(int) file.length()];
            vtDict = new Vec4f[(int) file.length()];
            vnDict = new Vec4f[(int) file.length()];
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
                //texturePath mappings
                else if (line.startsWith("vt")) {
                    vtDict[vtCount++] = readVector(line);
                }
                //vector
                else if (line.startsWith("v")) {
                    Vec4f vec4f = readVector(line);
                    updateBoundingBox(triangleMesh, vec4f);
                    vecterDict[vCount++] = vec4f;

                }

                if (line.startsWith("f")) {
                    p = Pattern.compile(faceSplit, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(line);
                    if (m.find()) {
                        String[] face = line.split(" ");

                        Vec4f[] vectors = new Vec4f[4];//enough room for quad
                        Vec2f[] textures = new Vec2f[4];

                        //start at 1 as first index is "f"
                        int i;
                        for (i = 1; i < face.length; i++) {
                            String[] f_vt_vn = face[i].split("\\/");
                            //take f
                            vectors[i - 1] = vecterDict[Integer.parseInt(f_vt_vn[0])];
                            //texturePath
                            if (f_vt_vn.length > 2) {
                                Vec4f tx = vtDict[Integer.parseInt(f_vt_vn[1])];
                                if (tx != null) {
                                    textures[i - 1] = new Vec2f(tx.x, tx.y);
                                }
                            }
                        }

                        //split quad into triangles
                        if (i == 5) {
                            Triangle[] tris = new Quad(vectors, textures).convertToTriangles();
                            triangleMesh.triangles.add(tris[0]);
                            triangleMesh.triangles.add(tris[1]);
                        } else {
                            triangleMesh.triangles.add(new Triangle(vectors, textures));
                        }
                    }
                }

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return triangleMesh;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static Vec4f minExtents;
    private static Vec4f maxExtents;
    private static void updateBoundingBox(TriangleMesh triangleMesh, Vec4f vec4f) {
        if (vec4f.x > maxExtents.x) maxExtents.x = vec4f.x;
        if (vec4f.x < minExtents.x) minExtents.x = vec4f.x;

        if (vec4f.y > maxExtents.y) maxExtents.y = vec4f.y;
        if (vec4f.y < minExtents.y) minExtents.y = vec4f.y;

        if (vec4f.z > maxExtents.z) maxExtents.z = vec4f.z;
        if (vec4f.z < minExtents.z) minExtents.z = vec4f.z;
    }

    private static Vec4f readVector(String line) {
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

            return new Vec4f(x, y, z);
        }
        return null;
    }
}
