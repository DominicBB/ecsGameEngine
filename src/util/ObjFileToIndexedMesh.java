package util;

import Rendering.renderUtil.Meshes.IndexedMesh;
import Rendering.renderUtil.Vertex;
import util.Mathf.Mathf2D.Vector2D;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Vector3D;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjFileToIndexedMesh {
    private static List<Vector3D> vtDict;
    private static List<Vector3D> vnDict;

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
    public static IndexedMesh loadFromPath(String path) {
        File file = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            List<Integer> triIndices = new ArrayList<>();
            List<Vertex> vertices = new ArrayList<>();

            vtDict = new ArrayList<>();
            vnDict = new ArrayList<>();

            Vector3D minExtents = new Vector3D(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
            Vector3D maxExtents = new Vector3D(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

            String line = bufferedReader.readLine();

            Pattern p;
            Matcher m;

            while (line != null) {
                //go to next line if comment
                if (line.startsWith("#")) {
                    line = bufferedReader.readLine();
                }

                //normal
                if (line.startsWith("vn")) {
                    vnDict.add(readVector(line));
                }

                //texturePath
                else if (line.startsWith("vt")) {
                    vtDict.add(readVector(line));
                }

                //vector
                else if (line.startsWith("v")) {
                    Vector3D vector3D = readVector(line);
                    updateBoundingBox(minExtents, maxExtents, vector3D);
                    vertices.add(new Vertex(vector3D));
                }

                if (line.startsWith("f")) {
                    p = Pattern.compile(faceSplit, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(line);
                    if (m.find()) {
                        String[] faceVertices = line.split(" ");

                        int i;
                        int[] vIndexs = new int[4];

                        //start at 1 so skip line start ("f")
                        for (i = 1; i < faceVertices.length; i++) {
                            String[] v_vt_vn = faceVertices[i].split("\\/");
                            vIndexs[i - 1] = Integer.parseInt(v_vt_vn[0])-1;

                            //get vertex
                            Vertex v = vertices.get(vIndexs[i - 1]);

                            //add texturePath to vertex
                            if (v_vt_vn.length > 1) {
                                Vector3D tx = vtDict.get(Integer.parseInt(v_vt_vn[1])-1);
                                if (tx != null) {
                                    v.texCoord = new Vector2D(tx.x, tx.y);
                                }
                            }

                            //add normal to vertex
                            if (v_vt_vn.length > 2) {
                                Vector3D vn = vnDict.get(Integer.parseInt(v_vt_vn[2])-1);
                                if (vn != null) {
                                    v.normal = new Vector3D(vn.x, vn.y, vn.z);
                                    v.normal.normalise();
                                }
                            }
                        }
                        //create triangle
                        addTriIndices(triIndices, vIndexs, i == 5);
                    }
                }

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            Vector3D size = maxExtents.minus(minExtents);
            Vector3D center = minExtents.plus(size.divide(2f));
            return new IndexedMesh(vertices, triIndices, new AABoundingBox(center, size));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            nullLists();
        }
        return null;
    }


    private static void addTriIndices(List<Integer> triIndices, int[] vIndexs, boolean isQuad) {
        triIndices.add(vIndexs[0]);
        triIndices.add(vIndexs[1]);
        triIndices.add(vIndexs[2]);
        if (isQuad) {
            triIndices.add(vIndexs[0]);
            triIndices.add(vIndexs[2]);
            triIndices.add(vIndexs[3]);
        }
    }

    //TODO: does this actually work?????

    private static void updateBoundingBox(Vector3D minExtents, Vector3D maxExtents, Vector3D vector3D) {
        if (vector3D.x > maxExtents.x) maxExtents.x = vector3D.x;
        if (vector3D.x < minExtents.x) minExtents.x = vector3D.x;

        if (vector3D.y > maxExtents.y) maxExtents.y = vector3D.y;
        if (vector3D.y < minExtents.y) minExtents.y = vector3D.y;

        if (vector3D.z > maxExtents.z) maxExtents.z = vector3D.z;
        if (vector3D.z < minExtents.z) minExtents.z = vector3D.z;
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

    private static void nullLists() {
        vnDict = null;
        vtDict = null;
    }
}
