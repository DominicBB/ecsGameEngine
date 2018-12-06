package util.Mathf.Mathf3D;

import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf2D.Vector2D;

import java.util.Arrays;

public class Triangle extends Polygon {

    public Triangle(Vector3D[] vs, Vector2D[] ts) {
        vectors = Arrays.copyOf(vs, 3);
        colors = new Vector3D[3];
        textures = new Vector2D[3];

        if (ts != null) {
            textures[0] = ts[0];
            textures[1] = ts[1];
            textures[2] = ts[2];

        }

        colors[0] = new Vector3D(0f, 255f, 0f);
        colors[1] = new Vector3D(0f, 0f, 255f);
        colors[2] = new Vector3D(255f, 0f, 0f);


//		reflectance = new Color(85, 146, 127);
/*
        reflectance = Color.magenta;
*/
//		shadedColor = reflectance;
        normal = normal();
    }

    public Triangle(Vector3D[] vs, Vector2D[] ts, Polygon p) {
        vectors = vs;
        textures = ts;

        this.shade = p.shade;

        normal = normal();
    }

    public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
        vectors = new Vector3D[3];
        vectors[0] = new Vector3D(x1, y1, z1);
        vectors[1] = new Vector3D(x2, y2, z2);
        vectors[2] = new Vector3D(x3, y3, z3);
/*
        reflectance = Color.CYAN;
*/
        normal = normal();
    }


    public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Polygon p) {
        vectors = new Vector3D[3];
        textures = new Vector2D[3];

        vectors[0] = v1;
        vectors[1] = v2;
        vectors[2] = v3;

        textures[0] = p.textures[0];
        textures[1] = p.textures[1];
        textures[2] = p.textures[2];


        this.shade = p.shade;

        normal = normal();
    }

    public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Vector2D t1, Vector2D t2, Vector2D t3, Polygon p) {
        vectors = new Vector3D[3];
        textures = new Vector2D[3];

        vectors[0] = v1;
        vectors[1] = v2;
        vectors[2] = v3;

        textures[0] = p.textures[0];
        textures[1] = p.textures[1];
        textures[2] = p.textures[2];

        this.shade = p.shade;

        this.normal = normal();
    }

    public Triangle(Triangle t) {
        vectors = t.vectors;
        textures = t.textures;

        this.shade = t.shade;

        normal = normal();
    }


    @Override
    public Vector3D normal() {
        return vectors[1].minus(vectors[0]).crossProduct(vectors[2].minus(vectors[0])).normal();
    }

    public static VertexOut[] orderCW(VertexOut[] vertices) {
        float z_n = z_normal(vertices[0].p_proj, vertices[1].p_proj, vertices[2].p_proj);
        if (z_n < 0) {
            return vertices;
        }

        VertexOut temp = vertices[1];
        vertices[1] = vertices[2];
        vertices[2] = temp;

        VertexOut temp2 = vertices[1];
        vertices[1] = vertices[2];
        vertices[2] = temp2;

        return vertices;
    }

    public static float z_normal(Vector3D v1, Vector3D v2, Vector3D v3) {
        float e1X = v2.x - v1.x;
        float e1Y = v2.y - v1.y;

        float e2X = v3.x - v1.x;
        float e2Y = v3.y - v1.y;

        return e1X * e2Y + e2X * e1Y;
    }

    public static Vector3D normal(Vertex[] vertices) {
        return vertices[1].vec.minus(vertices[0].vec).crossProduct(vertices[2].vec.minus(vertices[0].vec)).normal();
    }
}

