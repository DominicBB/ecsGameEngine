package util;

import rendering.renderUtil.Meshes.IndexedMesh;
import rendering.renderUtil.Vertex;
import util.Mathf.Mathf2D.Vec2f;
import util.Mathf.Mathf3D.Bounds.AABoundingBox;
import util.Mathf.Mathf3D.Vec4f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjFileToIndexedMesh {
    private static List<Vec4f> vtDict;
    private static List<Vec4f> vnDict;

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

            Vec4f minExtents = new Vec4f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
            Vec4f maxExtents = new Vec4f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

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
                    Vec4f vec4F = readVector(line);
                    updateBoundingBox(minExtents, maxExtents, vec4F);
                    vertices.add(new Vertex(vec4F));
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

                            //add_with_w texturePath to vertex
                            if (v_vt_vn.length > 1) {
                                Vec4f tx = vtDict.get(Integer.parseInt(v_vt_vn[1])-1);
                                if (tx != null) {
                                    v.texCoord = new Vec2f(tx.x, tx.y);
                                }
                            }

                            //add_with_w normal to vertex
                            if (v_vt_vn.length > 2) {
                                Vec4f vn = vnDict.get(Integer.parseInt(v_vt_vn[2])-1);
                                if (vn != null) {
                                    v.normal = new Vec4f(vn.x, vn.y, vn.z);
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
            Vec4f size = maxExtents.minus(minExtents);
            Vec4f center = minExtents.plus(size.divide(2f));
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

    private static void updateBoundingBox(Vec4f minExtents, Vec4f maxExtents, Vec4f vec4F) {
        if (vec4F.x > maxExtents.x) maxExtents.x = vec4F.x;
        if (vec4F.x < minExtents.x) minExtents.x = vec4F.x;

        if (vec4F.y > maxExtents.y) maxExtents.y = vec4F.y;
        if (vec4F.y < minExtents.y) minExtents.y = vec4F.y;

        if (vec4F.z > maxExtents.z) maxExtents.z = vec4F.z;
        if (vec4F.z < minExtents.z) minExtents.z = vec4F.z;
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

    private static void nullLists() {
        vnDict = null;
        vtDict = null;
    }
}
